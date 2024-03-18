package com.blackgear.geologicexpansion.common.entity.bear;

import com.blackgear.geologicexpansion.common.entity.duck.Duck;
import com.blackgear.geologicexpansion.common.registries.GEEntities;
import com.mojang.serialization.Dynamic;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.AbstractSchoolingFish;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CaveVines;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public class GrizzlyBear extends Animal implements NeutralMob {
    private static final EntityDataAccessor<Boolean> DATA_STANDING_ID = SynchedEntityData.defineId(GrizzlyBear.class, EntityDataSerializers.BOOLEAN);
    private static final float STAND_ANIMATION_TICKS = 6.0F;
    private float clientSideStandAnimationO;
    private float clientSideStandAnimation;
    private int warningSoundTicks;
    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
    private int remainingPersistentAngerTime;
    @Nullable private UUID persistentAngerTarget;
    private int ticksSinceEaten;

    public static final Predicate<ItemEntity> GRIZZLY_ITEMS = item -> {
        ItemStack stack = item.getItem();
        return (stack.is(Items.SALMON) || stack.is(Items.HONEYCOMB) || stack.is(Items.SWEET_BERRIES) || stack.is(Items.GLOW_BERRIES)) && item.isAlive() && !item.hasPickUpDelay();
    };

    public GrizzlyBear(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(BlockPathTypes.DANGER_OTHER, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_OTHER, 0.0F);
        this.setCanPickUpLoot(true);
    }

    @Override
    public boolean canTakeItem(ItemStack stack) {
        EquipmentSlot slot = Mob.getEquipmentSlotForItem(stack);
        if (!this.getItemBySlot(slot).isEmpty()) {
            return false;
        } else {
            return slot == EquipmentSlot.MAINHAND && super.canTakeItem(stack);
        }
    }

    @Override
    public boolean canHoldItem(ItemStack stack) {
        Item item = stack.getItem();
        ItemStack handHeld = this.getItemBySlot(EquipmentSlot.MAINHAND);
        return handHeld.isEmpty() || this.ticksSinceEaten > 0 && item.isEdible() && !handHeld.getItem().isEdible();
    }

    @Override
    protected void pickUpItem(ItemEntity itemEntity) {
        ItemStack stack = itemEntity.getItem();
        if (this.canHoldItem(stack)) {
            int i = stack.getCount();
            if (i > 1) {
                this.dropItem(stack.split(i - 1));
            }

            this.onItemPickup(itemEntity);
            this.setItemSlot(EquipmentSlot.MAINHAND, stack.split(1));
            this.setGuaranteedDrop(EquipmentSlot.MAINHAND);
            this.take(itemEntity, stack.getCount());
            itemEntity.discard();
            this.ticksSinceEaten = 0;
        }
    }

    private void dropItem(ItemStack stack) {
        ItemEntity item = new ItemEntity(this.level, this.getX(), this.getY(), this.getZ(), stack);
        this.level.addFreshEntity(item);
    }

    @Override
    public void aiStep() {
        if (!this.level.isClientSide && this.isAlive() && this.isEffectiveAi()) {
            ++this.ticksSinceEaten;
            ItemStack stack = this.getItemBySlot(EquipmentSlot.MAINHAND);
            if (this.canEat(stack)) {
                if (this.ticksSinceEaten > 600) {
                    ItemStack result = stack.finishUsingItem(this.level, this);
                    if (!result.isEmpty()) {
                        this.setItemSlot(EquipmentSlot.MAINHAND, result);
                    }

                    this.ticksSinceEaten = 0;
                } else if (this.ticksSinceEaten > 560 && this.random.nextFloat() < 0.1F) {
                    this.playSound(this.getEatingSound(stack), 1.0F, 1.0F);
                    this.level.broadcastEntityEvent(this, (byte) 45);
                }
            }

//            LivingEntity target = this.getTarget();
//            if (target != null && target.isAlive()) {
//
//            }
        }

        if (this.isSleeping() || this.isImmobile()) {
            this.xxa = 0.0F;
            this.zza = 0.0F;
        }

        super.aiStep();
//        if (this.isDefending() && this.random.nextFloat() < 0.05F) {
//            this.playSound(SoundEvents.POLAR_BEAR_WARNING, 1.0F, 1.0F);
//        }
    }

    private boolean canEat(ItemStack stack) {
        return stack.isEdible() && this.getTarget() == null && this.onGround && !this.isSleeping();
    }

    @Override
    protected void dropAllDeathLoot(DamageSource source) {
        ItemStack stack = this.getItemBySlot(EquipmentSlot.MAINHAND);
        if (!stack.isEmpty()) {
            this.spawnAtLocation(stack);
            this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
        }

        super.dropAllDeathLoot(source);
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 45) {
            ItemStack stack = this.getItemBySlot(EquipmentSlot.MAINHAND);
            if (!stack.isEmpty()) {
                for (int i = 0; i < 8; i++) {
                    Vec3 pos = new Vec3(((double) this.random.nextFloat() - 0.5) * 0.1, Math.random() * 0.1 + 0.1, 0.0)
                        .xRot(-this.getXRot() * ((float) Math.PI / 180F))
                        .yRot(-this.getYRot() * ((float) Math.PI / 180F));
                    this.level.addParticle(
                        new ItemParticleOption(ParticleTypes.ITEM, stack),
                        this.getX() + this.getLookAngle().x / 2.0,
                        this.getY(),
                        this.getZ() + this.getLookAngle().z / 2.0,
                        pos.x,
                        pos.y + 0.05,
                        pos.z
                    );
                }
            }
        } else {
            super.handleEntityEvent(id);
        }
    }

    @Nullable @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        return GEEntities.GRIZZLY_BEAR.get().create(level);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return false;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this)); //
        this.goalSelector.addGoal(1, new BearMeleeAttackGoal());
        this.goalSelector.addGoal(1, new BearPanicGoal()); //
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25)); //
        this.goalSelector.addGoal(5, new RandomStrollGoal(this, 1.0)); //
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F)); //
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this)); //
        this.goalSelector.addGoal(8, new BearEatBerriesGoal(1.2F, 12, 1));
        this.goalSelector.addGoal(8, new BearReachesForBeehive(this));
        this.goalSelector.addGoal(9, new BearSearchForItemsGoal());
        this.targetSelector.addGoal(1, new BearHurtByTargetGoal());
        this.targetSelector.addGoal(2, new BearAttackPlayersGoal());
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngryAt));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, AbstractFish.class, 20, false, false, living -> {
            if (living instanceof AbstractSchoolingFish fish) {
                int y = fish.level.getChunkAt(fish.blockPosition()).getHeight(Heightmap.Types.WORLD_SURFACE, fish.blockPosition().getX(), fish.blockPosition().getZ());
                return fish.getY() - y <= 1.5;
            }

            return false;
        }) {
            @Override
            public boolean canContinueToUse() {
                if (this.target instanceof AbstractSchoolingFish fish) {
                    int y = fish.level.getChunkAt(fish.blockPosition()).getHeight(Heightmap.Types.WORLD_SURFACE, fish.blockPosition().getX(), fish.blockPosition().getZ());
                    return fish.getY() - y <= 1.5;
                }

                return false;
            }
        });
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Animal.class, 10, false, false, living -> {
            return !this.isBaby() && (living instanceof Chicken || living instanceof Duck || living instanceof Fox || living instanceof Rabbit);
        }));
        this.targetSelector.addGoal(5, new ResetUniversalAngerTargetGoal<>(this, false));
    }

    @Override
    protected Brain.Provider<GrizzlyBear> brainProvider() {
        return Brain.provider(GrizzlyBrain.MEMORIES, GrizzlyBrain.SENSORS);
    }

    @Override
    protected Brain<?> makeBrain(Dynamic<?> dynamic) {
        return super.makeBrain(dynamic);
    }

    @Override @SuppressWarnings("unchecked")
    public Brain<GrizzlyBear> getBrain() {
        return (Brain<GrizzlyBear>) super.getBrain();
    }

    @Override
    protected void customServerAiStep() {
        this.level.getProfiler().push("grizzlyBrain");
        this.getBrain().tick((ServerLevel)this.level, this);
        this.level.getProfiler().popPush("grizzlyActivityUpdate");
        GrizzlyBrain.updateActivity(this);
        this.level.getProfiler().pop();
        super.customServerAiStep();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return AgeableMob.createMobAttributes()
            .add(Attributes.MAX_HEALTH, 30.0D)
            .add(Attributes.FOLLOW_RANGE, 20.0D)
            .add(Attributes.MOVEMENT_SPEED, 0.25D)
            .add(Attributes.ATTACK_DAMAGE, 6.0D);
    }

    public static boolean checkGrizzlyBearSpawnRules(EntityType<GrizzlyBear> entityType, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return checkAnimalSpawnRules(entityType, level, spawnType, pos, random);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.readPersistentAngerSaveData(this.level, compound);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        this.addPersistentAngerSaveData(compound);
    }

    @Override
    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
    }

    @Override
    public void setRemainingPersistentAngerTime(int remainingPersistentAngerTime) {
        this.remainingPersistentAngerTime = remainingPersistentAngerTime;
    }

    @Override
    public int getRemainingPersistentAngerTime() {
        return this.remainingPersistentAngerTime;
    }

    @Override
    public void setPersistentAngerTarget(@Nullable UUID persistentAngerTarget) {
        this.persistentAngerTarget = persistentAngerTarget;
    }

    @Override @Nullable
    public UUID getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return this.isBaby() ? SoundEvents.POLAR_BEAR_AMBIENT_BABY : SoundEvents.POLAR_BEAR_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.POLAR_BEAR_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.POLAR_BEAR_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.POLAR_BEAR_STEP, 0.15F, 1.0F);
    }

    protected void playWarningSound() {
        if (this.warningSoundTicks <= 0) {
            this.playSound(SoundEvents.POLAR_BEAR_WARNING, 1.0F, this.getVoicePitch());
            this.warningSoundTicks = 40;
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_STANDING_ID, false);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level.isClientSide) {
            if (this.clientSideStandAnimation != this.clientSideStandAnimationO) {
                this.refreshDimensions();
            }

            this.clientSideStandAnimationO = this.clientSideStandAnimation;
            if (this.isStanding()) {
                this.clientSideStandAnimation = Mth.clamp(this.clientSideStandAnimation + 1.0F, 0.0F, STAND_ANIMATION_TICKS);
            } else {
                this.clientSideStandAnimation = Mth.clamp(this.clientSideStandAnimation - 1.0F, 0.0F, STAND_ANIMATION_TICKS);
            }
        }

        if (this.warningSoundTicks > 0) {
            --this.warningSoundTicks;
        }

        if (!this.level.isClientSide) {
            this.updatePersistentAnger((ServerLevel)this.level, true);
        }
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        if (this.clientSideStandAnimation > 0.0F) {
            float f = this.clientSideStandAnimation / STAND_ANIMATION_TICKS;
            float g = 1.0F + f;
            return super.getDimensions(pose).scale(1.0F, g);
        } else {
            return super.getDimensions(pose);
        }
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        boolean bl = target.hurt(DamageSource.mobAttack(this), (float)((int)this.getAttributeValue(Attributes.ATTACK_DAMAGE)));
        if (bl) {
            this.doEnchantDamageEffects(this, target);
        }

        return bl;
    }

    public boolean isStanding() {
        return this.entityData.get(DATA_STANDING_ID);
    }

    public void setStanding(boolean standing) {
        this.entityData.set(DATA_STANDING_ID, standing);
    }

    public float getStandingAnimationScale(float partialTick) {
        return Mth.lerp(partialTick, this.clientSideStandAnimationO, this.clientSideStandAnimation) / STAND_ANIMATION_TICKS;
    }

    @Override
    protected float getWaterSlowDown() {
        return 0.98F;
    }

    @Override
    public double getMeleeAttackRangeSqr(LivingEntity entity) {
        return 4.0F + entity.getBbWidth();
    }

    @Override
    public SpawnGroupData finalizeSpawn(
        ServerLevelAccessor level,
        DifficultyInstance difficulty,
        MobSpawnType reason,
        @Nullable SpawnGroupData spawnData,
        @Nullable CompoundTag dataTag
    ) {
        if (spawnData == null) {
            spawnData = new AgeableMob.AgeableMobGroupData(1.0F);
        }

        return super.finalizeSpawn(level, difficulty, reason, spawnData, dataTag);
    }

    class BearAttackPlayersGoal extends NearestAttackableTargetGoal<Player> {
        public BearAttackPlayersGoal() {
            super(GrizzlyBear.this, Player.class, 20, true, true, null);
        }

        @Override
        public boolean canUse() {
            if (!GrizzlyBear.this.isBaby()) {
                if (super.canUse()) {
                    for (GrizzlyBear bear : GrizzlyBear.this.level.getEntitiesOfClass(GrizzlyBear.class, GrizzlyBear.this.getBoundingBox().inflate(8.0, 4.0, 8.0))) {
                        if (bear.isBaby()) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }

        @Override
        protected double getFollowDistance() {
            return super.getFollowDistance() * 0.5;
        }
    }

    class BearHurtByTargetGoal extends HurtByTargetGoal {
        public BearHurtByTargetGoal() {
            super(GrizzlyBear.this);
        }

        @Override
        public void start() {
            super.start();
            if (GrizzlyBear.this.isBaby()) {
                this.alertOthers();
                this.stop();
            }

        }

        @Override
        protected void alertOther(Mob mob, LivingEntity target) {
            if (mob instanceof GrizzlyBear && !mob.isBaby()) {
                super.alertOther(mob, target);
            }

        }
    }

    class BearMeleeAttackGoal extends MeleeAttackGoal {
        public BearMeleeAttackGoal() {
            super(GrizzlyBear.this, 1.25, true);
        }

        @Override
        protected void checkAndPerformAttack(LivingEntity enemy, double distToEnemySqr) {
            double d = this.getAttackReachSqr(enemy);
            if (distToEnemySqr <= d && this.isTimeToAttack()) {
                this.resetAttackCooldown();
                this.mob.doHurtTarget(enemy);
                GrizzlyBear.this.setStanding(false);
            } else if (distToEnemySqr <= d * 2.0) {
                if (this.isTimeToAttack()) {
                    GrizzlyBear.this.setStanding(false);
                    this.resetAttackCooldown();
                }

                if (this.getTicksUntilNextAttack() <= 10) {
                    GrizzlyBear.this.setStanding(true);
                    GrizzlyBear.this.playWarningSound();
                }
            } else {
                this.resetAttackCooldown();
                GrizzlyBear.this.setStanding(false);
            }

        }

        @Override
        public void stop() {
            GrizzlyBear.this.setStanding(false);
            super.stop();
        }

        @Override
        protected double getAttackReachSqr(LivingEntity attackTarget) {
            return 4.0F + attackTarget.getBbWidth();
        }
    }

    class BearPanicGoal extends PanicGoal {
        public BearPanicGoal() {
            super(GrizzlyBear.this, 2.0);
        }

        @Override
        protected boolean shouldPanic() {
            return this.mob.getLastHurtByMob() != null && this.mob.isBaby() || this.mob.isOnFire();
        }
    }

    class BearEatBerriesGoal extends MoveToBlockGoal {
        private static final int WAIT_TICKS = 40;
        protected int ticksWaited;

        public BearEatBerriesGoal(double speedModifier, int horizontalSearchRange, int verticalSearchRange) {
            super(GrizzlyBear.this, speedModifier, horizontalSearchRange, verticalSearchRange);
        }

        @Override
        public double acceptedDistance() {
            return 2.0D;
        }

        @Override
        public boolean shouldRecalculatePath() {
            return this.tryTicks % 100 == 0;
        }

        @Override
        protected boolean isValidTarget(LevelReader level, BlockPos pos) {
            BlockState state = level.getBlockState(pos);
            return state.is(Blocks.SWEET_BERRY_BUSH) && state.getValue(SweetBerryBushBlock.AGE) >= 2 || CaveVines.hasGlowBerries(state);
        }

        @Override
        public void tick() {
            if (this.isReachedTarget()) {
                if (this.ticksWaited >= WAIT_TICKS) {
                    this.onReachedTarget();
                } else {
                    ++this.ticksWaited;
                }
            } else if (!this.isReachedTarget() && GrizzlyBear.this.random.nextFloat() < 0.05F) {
                GrizzlyBear.this.playSound(SoundEvents.FOX_SNIFF, 1.0F, 1.0F);
            }

            super.tick();
        }

        private void onReachedTarget() {
            if (GrizzlyBear.this.level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
                BlockState state = GrizzlyBear.this.level.getBlockState(this.blockPos);
                if (state.is(Blocks.SWEET_BERRY_BUSH)) {
                    this.pickSweetBerries(state);
                } else if (CaveVines.hasGlowBerries(state)) {
                    this.pickGlowBerries(state);
                }
            }
        }

        private void pickGlowBerries(BlockState state) {
            CaveVines.use(state, GrizzlyBear.this.level, this.blockPos);
        }

        private void pickSweetBerries(BlockState state) {
            int i = state.getValue(SweetBerryBushBlock.AGE);
            state.setValue(SweetBerryBushBlock.AGE, 1);
            int size = 1 + GrizzlyBear.this.level.random.nextInt(2) + (i == 3 ? 1 : 0);
            ItemStack stack = GrizzlyBear.this.getItemBySlot(EquipmentSlot.MAINHAND);
            if (stack.isEmpty()) {
                GrizzlyBear.this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.SWEET_BERRIES, size));
                --size;
            }

            if (size > 0) {
                Block.popResource(GrizzlyBear.this.level, this.blockPos, new ItemStack(Items.SWEET_BERRIES, size));
            }

            GrizzlyBear.this.playSound(SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, 1.0F, 1.0F);
            GrizzlyBear.this.level.setBlock(this.blockPos, state.setValue(SweetBerryBushBlock.AGE, 1), 2);
        }

        @Override
        public boolean canUse() {
            return !GrizzlyBear.this.isSleeping() && GrizzlyBear.this.isBaby() && super.canUse();
        }

        @Override
        public void start() {
            this.ticksWaited = 0;
            super.start();
        }
    }

    class BearSearchForItemsGoal extends Goal {
        public BearSearchForItemsGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            if (!GrizzlyBear.this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty()) {
                return false;
            } else if (GrizzlyBear.this.getTarget() != null || GrizzlyBear.this.getLastHurtByMob() != null) {
                return false;
            } else if (GrizzlyBear.this.isSleeping()) {
                return false;
            } else if (GrizzlyBear.this.getRandom().nextInt(reducedTickDelay(10)) != 0) {
                return false;
            } else {
                List<ItemEntity> items = GrizzlyBear.this.level.getEntitiesOfClass(ItemEntity.class, GrizzlyBear.this.getBoundingBox().inflate(8.0, 8.0, 8.0), GRIZZLY_ITEMS);
                return !items.isEmpty() && GrizzlyBear.this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty();
            }
        }

        @Override
        public void tick() {
            List<ItemEntity> items = GrizzlyBear.this.level.getEntitiesOfClass(ItemEntity.class, GrizzlyBear.this.getBoundingBox().inflate(8.0, 8.0, 8.0), GRIZZLY_ITEMS);
            ItemStack stack = GrizzlyBear.this.getItemBySlot(EquipmentSlot.MAINHAND);
            if (stack.isEmpty() && !items.isEmpty()) {
                GrizzlyBear.this.getNavigation().moveTo(items.get(0), 1.2F);
            }
        }

        @Override
        public void start() {
            List<ItemEntity> items = GrizzlyBear.this.level.getEntitiesOfClass(ItemEntity.class, GrizzlyBear.this.getBoundingBox().inflate(8.0, 8.0, 8.0), GRIZZLY_ITEMS);
            if (!items.isEmpty()) {
                GrizzlyBear.this.getNavigation().moveTo(items.get(0), 1.2F);
            }
        }
    }

    static class BearReachesForBeehive extends Goal {
        private final GrizzlyBear bear;
        private BlockPos hivePos;

        public BearReachesForBeehive(GrizzlyBear bear) {
            this.bear = bear;
        }

        @Override
        public boolean canUse() {
            BlockPos pos = this.bear.blockPosition();
            for (int y = 0; y <= 3; y++) {
                for (int x = -3; x <= 3; x++) {
                    for (int z = -3; z <= 3; z++) {
                        BlockPos checkPos = pos.offset(x, y, z);
                        if (this.bear.level.getBlockState(checkPos).is(Blocks.BEEHIVE)) {
                            this.hivePos = checkPos;
                            return true;
                        }
                    }
                }
            }

            return false;
        }

        @Override
        public void start() {
            this.bear.getNavigation().moveTo(this.hivePos.getX(), this.hivePos.getY(), this.hivePos.getZ(), 1.0);
        }

        @Override
        public void tick() {
            if (this.bear.blockPosition().equals(this.hivePos)) {
                this.bear.level.setBlock(this.hivePos, Blocks.BEEHIVE.defaultBlockState().setValue(BeehiveBlock.HONEY_LEVEL, 0), 3);
                this.bear.level.addFreshEntity(new ItemEntity(this.bear.level, this.hivePos.getX(), this.hivePos.getY(), this.hivePos.getZ(), new ItemStack(Items.HONEYCOMB)));
            }
        }
    }
}
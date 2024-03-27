package com.blackgear.geologicexpansion.common.entity.duck;

import com.blackgear.geologicexpansion.client.events.EntityEvents;
import com.blackgear.geologicexpansion.common.entity.duck.goals.DuckFishingGoal;
import com.blackgear.geologicexpansion.common.entity.duck.goals.DuckFollowFlockLeaderGoal;
import com.blackgear.geologicexpansion.common.entity.resource.EntityState;
import com.blackgear.geologicexpansion.common.entity.resource.FluidWalker;
import com.blackgear.geologicexpansion.common.registries.GEEntities;
import com.blackgear.geologicexpansion.common.registries.GEItems;
import com.blackgear.geologicexpansion.common.registries.GESounds;
import com.blackgear.geologicexpansion.common.registries.entities.GEEntityDataSerializers;
import com.blackgear.geologicexpansion.core.data.GEItemTags;
import net.minecraft.advancements.CriteriaTriggers;
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
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class Duck extends Animal implements FluidWalker {
    // ---------- ENTITY DATA ----------
    private static final EntityDataAccessor<Boolean> ATE_RECENTLY = SynchedEntityData.defineId(Duck.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<EntityState> STATE = SynchedEntityData.defineId(Duck.class, GEEntityDataSerializers.ENTITY_STATE);

    // ---------- ENTITY ANIMATIONS ----------
    public final AnimationState fallingAnimationState = new AnimationState();
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState fishingAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;
    private int fishingAnimationTimeout = 0;

    // ---------- ENTITY ATTRIBUTES ----------
    private static final Ingredient FOOD_ITEMS = Ingredient.of(
        Items.COD,
        Items.SALMON,
        Items.WHEAT_SEEDS,
        Items.MELON_SEEDS,
        Items.PUMPKIN_SEEDS,
        Items.BEETROOT_SEEDS
    );
    private int ateCooldownTicks = 6000;
    private int ticksSinceEaten;
    // ---------- ENTITY BEHAVIOR ----------
    private Duck leader;
    private int flockSize = 1;

    // ========== CONSTRUCTOR ==========

    public Duck(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
    }

    // ========== BEHAVIOR ==========

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
            .add(Attributes.MAX_HEALTH, 4.0)
            .add(Attributes.MOVEMENT_SPEED, 0.25)
            .add(Attributes.LUCK, 2.0);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.4));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.0, FOOD_ITEMS, false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1));
        this.goalSelector.addGoal(4, new DuckFollowFlockLeaderGoal(this));
        this.goalSelector.addGoal(5, new RandomStrollGoal(this, 1.0, 60));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(7, new DuckFishingGoal(this, 1.2F, 12, 1));
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack playerStack = player.getItemInHand(hand);
        ItemStack stack = this.getItemBySlot(EquipmentSlot.MAINHAND);

        if (this.isFood(playerStack) && !stack.is(GEItemTags.DUCK_TRADEABLES) && !stack.isEmpty()) {
            ItemStack result = ItemUtils.createFilledResult(playerStack, player, stack);
            player.setItemInHand(hand, result);

            this.setItemSlot(EquipmentSlot.MAINHAND, playerStack);
            this.ticksSinceEaten = 0;
            return InteractionResult.sidedSuccess(this.level.isClientSide);
        }

        return super.mobInteract(player, hand);
    }

    @Override
    public void aiStep() {
        if (!this.level.isClientSide && this.isAlive() && this.isEffectiveAi()) {

            ItemStack stack = this.getItemBySlot(EquipmentSlot.MAINHAND);
            if (!stack.isEmpty()) {
                ++this.ticksSinceEaten;
                // Check if the item is edible and not a pufferfish
                if (stack.isEdible() && !stack.is(Items.PUFFERFISH)) {
                    // If the duck has been eating for 30 seconds, finish the item
                    if (this.ticksSinceEaten > 600) {
                        ItemStack result = stack.finishUsingItem(this.level, this);
                        if (!result.isEmpty()) {
                            this.setItemSlot(EquipmentSlot.MAINHAND, result);
                        }

                        this.setAteRecently(true);
                        this.ticksSinceEaten = 0;
                    // If the duck has been eating for 28 seconds, play the eating sound
                    } else if (this.ticksSinceEaten > 560 && this.random.nextFloat() < 0.1F) {
                        this.playSound(SoundEvents.FOX_EAT, 1.0F, 1.0F);
                        this.level.broadcastEntityEvent(this, EntityEvents.SPAWN_EATING_PARTICLES);
                    }
                } else {
                    // If the duck has been holding an item for 30 seconds, finish the item
                    if (this.ticksSinceEaten > 3000) {
                        this.setState(EntityState.IDLE);
                        this.clearItem();
                        // If the duck has been holding an item for 26 seconds, play the eating sound
                    } else if (this.ticksSinceEaten > 2940) {
                        this.setState(EntityState.FISHING);
                    }
                }
            }

            if (this.ateRecently() && --this.ateCooldownTicks <= 0) {
                this.setAteRecently(false);
                this.ateCooldownTicks = 6000;
            }
        }

        super.aiStep();

        Vec3 movement = this.getDeltaMovement();
        if (!this.onGround && movement.y < 0.0) {
            this.setDeltaMovement(movement.multiply(1.0, 0.6, 1.0));
        }
    }

    private void clearItem() {
        this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
        this.ticksSinceEaten = 0;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level.isClientSide) {
            this.setupAnimationStates();
        }

        if (this.hasFollowers() && this.level.random.nextInt(200) == 1) {
            List<Duck> followers = this.level.getEntitiesOfClass(Duck.class, this.getBoundingBox().inflate(8.0, 8.0, 8.0));
            if (followers.size() <= 1) {
                this.flockSize = 1;
            }
        }

        this.checkInsideBlocks();
        this.floatDuck();
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return FOOD_ITEMS.test(stack);
    }

    @Override
    public void spawnChildFromBreeding(ServerLevel level, Animal mate) {
        ItemStack stack = new ItemStack(GEItems.DUCK_EGG.get());
        ItemEntity itemEntity = new ItemEntity(level, this.position().x, this.position().y, this.position().z, stack);
        itemEntity.setDefaultPickUpDelay();

        Optional.ofNullable(this.getLoveCause())
            .or(() -> Optional.ofNullable(mate.getLoveCause()))
            .ifPresent(player -> {
                player.awardStat(Stats.ANIMALS_BRED);
                CriteriaTriggers.BRED_ANIMALS.trigger(player, this, mate, null);
            });

        this.setAge(6000);
        mate.setAge(6000);
        this.resetLove();
        mate.resetLove();
        level.broadcastEntityEvent(this, (byte)18);
        if (level.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
            level.addFreshEntity(new ExperienceOrb(level, this.getX(), this.getY(), this.getZ(), this.getRandom().nextInt(7) + 1));
        }

        this.playSound(SoundEvents.CHICKEN_EGG, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
        level.addFreshEntity(itemEntity);
    }

    @Nullable @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        return GEEntities.DUCK.get().create(level);
    }

    // ========== ENTITY EVENTS ==========

    @Override
    public void handleEntityEvent(byte id) {
        if (id == EntityEvents.SPAWN_EATING_PARTICLES) {
            ItemStack stack = this.getItemBySlot(EquipmentSlot.MAINHAND);
            if (!stack.isEmpty()) {
                for (int i = 0; i < 8; i++) {
                    Vec3 motion = new Vec3(((double) this.random.nextFloat() - 0.5) * 0.1, Math.random() * 0.1 + 0.1, 0.0)
                        .xRot(-this.getXRot() * (float) (Math.PI / 180.0))
                        .yRot(-this.getYRot() * (float) (Math.PI / 180.0));
                    this.level.addParticle(
                        new ItemParticleOption(ParticleTypes.ITEM, stack),
                        this.getX() + this.getLookAngle().x / 2.0,
                        this.getY(),
                        this.getZ() + this.getLookAngle().z / 2.0,
                        motion.x,
                        motion.y + 0.05,
                        motion.z
                    );
                }
            }
        }

        super.handleEntityEvent(id);
    }

    // ========== DATA CONTROL ==========

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setAteRecently(compound.getBoolean("AteRecently"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("AteRecently", this.ateRecently());
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ATE_RECENTLY, false);
        this.entityData.define(STATE, EntityState.IDLE);
    }

    public void setState(EntityState state) {
        if (state != this.getState()) this.entityData.set(STATE, state);
    }

    public EntityState getState() {
        return this.entityData.get(STATE);
    }

    public void setAteRecently(boolean ate) {
        if (ate != this.ateRecently()) this.entityData.set(ATE_RECENTLY, ate);
    }

    public boolean ateRecently() {
        return this.entityData.get(ATE_RECENTLY);
    }

    // ========== ANIMATION ==========

    private void setupAnimationStates() {
        if (!this.isOnGround()) {
            this.fallingAnimationState.startIfStopped(this.tickCount);
        } else {
            this.fallingAnimationState.stop();
        }

        if (this.isInWaterOrBubble()) {
            if (this.idleAnimationTimeout <= 0) {
                this.idleAnimationTimeout = this.random.nextInt(40) + 80;
                this.idleAnimationState.start(this.tickCount);
            } else {
                --this.idleAnimationTimeout;
            }
        } else {
            this.idleAnimationState.stop();
        }

        if (this.getState() == EntityState.FISHING && this.fishingAnimationTimeout <= 0) {
            this.fishingAnimationTimeout = 40;
            this.fishingAnimationState.start(this.tickCount);
        } else {
            --this.fishingAnimationTimeout;
        }

        if (this.getState() != EntityState.FISHING) {
            this.fishingAnimationState.stop();
        }
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
        return this.isBaby() ? dimensions.height * 0.85F : dimensions.height * 0.92F;
    }

    // ========== FLOCK BEHAVIOR ==========
    @Override
    public int getMaxSpawnClusterSize() {
        return this.getMaxFlockSize();
    }

    public int getMaxFlockSize() {
        return 4;
    }

    public boolean isFollower() {
        return this.leader != null && this.leader.isAlive();
    }

    public void startFollowing(Duck leader) {
        this.leader = leader;
        leader.addFollower();
    }

    public void stopFollowing() {
        this.leader.removeFollower();
        this.leader = null;
    }

    private void addFollower() {
        ++this.flockSize;
    }

    private void removeFollower() {
        --this.flockSize;
    }

    public boolean canBeFollowed() {
        return this.hasFollowers() && this.flockSize < this.getMaxFlockSize();
    }

    public boolean hasFollowers() {
        return this.flockSize > 1;
    }

    public boolean inRangeOfLeader() {
        return this.distanceToSqr(this.leader) <= 400.0;
    }

    public void pathToLeader() {
        if (this.isFollower()) {
            Vec3 position = new Vec3(
                this.leader.getX() - this.getX(),
                this.leader.getY() - this.getY(),
                this.leader.getZ() - this.getZ()
            )
            .normalize()
            .scale(Math.max(this.distanceToSqr(this.leader) - 2.0, 0.0));
            this.getNavigation()
                .moveTo(
                    this.getX() + position.x,
                    this.getY() + position.y,
                    this.getZ() + position.z,
                    1.0
                );
        }
    }

    public void addFollowers(Stream<? extends Duck> followers) {
        followers.limit(this.getMaxFlockSize() - this.flockSize)
            .filter(duck -> duck != this)
            .forEach(duck -> duck.startFollowing(this));
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag) {
        super.finalizeSpawn(level, difficulty, reason, spawnData, dataTag);
        if (spawnData == null) {
            spawnData = new FlockSpawnGroupData(this, true);
        } else {
            this.startFollowing(((FlockSpawnGroupData)spawnData).leader);
        }

        return spawnData;
    }

    // ========== MOVEMENT ==========

    private void floatDuck() {
        if (this.isInWater()) {
            CollisionContext context = CollisionContext.of(this);
            if (context.isAbove(this.getStableLiquidShape(), this.blockPosition(), true) && !this.level.getFluidState(this.blockPosition().above()).is(FluidTags.WATER)) {
                this.onGround = true;
            } else {
                this.setDeltaMovement(this.getDeltaMovement().scale(0.5D).add(0.0D, 0.05D, 0.0D));
            }
        }
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return new DuckPathNavigation(this, level);
    }

    @Override
    public float getWalkTargetValue(BlockPos pos, LevelReader level) {
        if (level.getBlockState(pos).getFluidState().is(FluidTags.WATER)) {
            return 10.0F;
        } else {
            return super.getWalkTargetValue(pos, level);
        }
    }

    @Override
    public boolean canStandOnFluid(FluidState fluidState) {
        return fluidState.is(FluidTags.WATER);
    }

    @Override
    public VoxelShape getStableLiquidShape() {
        return Block.box(0.0, 0.0, 0.0, 16.0, 12.0, 16.0);
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float multiplier, DamageSource source) {
        return false;
    }

    // ========== RIDING BEHAVIOR ==========

    @Override
    public void positionRider(Entity passenger) {
        super.positionRider(passenger);
        float xRotationAngle = Mth.sin(this.yBodyRot * (float) (Math.PI / 180.0));
        float zRotationAngle = Mth.cos(this.yBodyRot * (float) (Math.PI / 180.0));
        passenger.setPos(
            this.getX() + (double)(0.1F * xRotationAngle),
            this.getY(0.5) + passenger.getMyRidingOffset() + 0.0,
            this.getZ() - (double)(0.1F * zRotationAngle)
        );
        if (passenger instanceof LivingEntity living) {
            living.yBodyRot = this.yBodyRot;
        }
    }

    // ========== SOUNDS ==========

    @Nullable @Override
    protected SoundEvent getAmbientSound() {
        return GESounds.DUCK_AMBIENT.get();
    }

    @Nullable @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return GESounds.DUCK_HURT.get();
    }

    @Nullable @Override
    protected SoundEvent getDeathSound() {
        return GESounds.DUCK_DEATH.get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(GESounds.DUCK_STEP.get(), 0.025F, 1.0F);
    }

    public static class FlockSpawnGroupData extends AgeableMobGroupData {
        public final Duck leader;

        public FlockSpawnGroupData(Duck leader, boolean shouldSpawnBaby) {
            super(shouldSpawnBaby);
            this.leader = leader;
        }
    }
}
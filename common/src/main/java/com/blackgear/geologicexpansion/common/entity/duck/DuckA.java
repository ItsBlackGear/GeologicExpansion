package com.blackgear.geologicexpansion.common.entity.duck;

import com.blackgear.geologicexpansion.client.events.EntityEvents;
import com.blackgear.geologicexpansion.common.entity.resource.FluidWalker;
import com.blackgear.geologicexpansion.common.registries.GEEntities;
import com.blackgear.geologicexpansion.common.registries.GEItems;
import com.blackgear.geologicexpansion.common.registries.GESounds;
import com.blackgear.geologicexpansion.common.registries.entities.GEEntityDataSerializers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DuckA extends AbstractDuck implements FluidWalker {
    private static final EntityDataAccessor<Boolean> ATE_RECENTLY = SynchedEntityData.defineId(DuckA.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<DuckA.State> STATE = SynchedEntityData.defineId(DuckA.class, GEEntityDataSerializers.DUCK_STATE);
    private static final Ingredient FOOD_ITEMS = Ingredient.of(Items.COD, Items.SALMON, Items.WHEAT_SEEDS, Items.MELON_SEEDS, Items.PUMPKIN_SEEDS, Items.BEETROOT_SEEDS);
    public final AnimationState swimmingAnimation = new AnimationState();
//    public final AnimationState fishingAnimationState = new AnimationState();
    public float flap, oFlap, flapSpeed, oFlapSpeed;
    public float flapping, nextFlap = 1.0F;
    public int eggTime = this.random.nextInt(6000) + 6000;
    private int ateCooldownTicks = 6000;
    private int ticksSinceEaten;

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
            .add(Attributes.MAX_HEALTH, 4.0)
            .add(Attributes.MOVEMENT_SPEED, 0.25);
    }

    public DuckA(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        this.entityData.define(STATE, DuckA.State.IDLING);
        this.entityData.define(ATE_RECENTLY, false);
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
    }

    // ========== DATA CONTROL ==========

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setAteRecently(compound.getBoolean("AteRecently"));
        if (compound.contains("EggLayTime")) {
            this.eggTime = compound.getInt("EggLayTime");
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("AteRecently", this.ateRecently());
        compound.putInt("EggLayTime", this.eggTime);
    }

    public void setAteRecently(boolean ate) {
        this.entityData.set(ATE_RECENTLY, ate);
    }

    public boolean ateRecently() {
        return this.entityData.get(ATE_RECENTLY);
    }

    public void setState(DuckA.State state) {
        this.entityData.set(STATE, state);
    }

    public DuckA.State getState() {
        return this.entityData.get(STATE);
    }

    public void transitionToState(DuckA.State state) {
        if (state == this.getState()) {
            return;
        }

        switch (state) {
            case FISHING -> {
                this.setState(DuckA.State.FISHING);
                this.setPose(Pose.CROAKING);
                this.level.broadcastEntityEvent(this, EntityEvents.DUCK_FISHING_ANIMATION);
            }
            case IDLING -> {
                this.setState(DuckA.State.IDLING);
                this.setPose(Pose.STANDING);
            }
        }
    }

    // ========== ANIMATION ==========

//    @Override
//    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
//        if (STATE.equals(key)) {
//            State state = this.getState();
//            if (state == State.FISHING) {
//                this.fishingAnimationState.startIfStopped(this.tickCount);
//            } else {
//                this.fishingAnimationState.stop();
//            }
//        }
//
//        if (DATA_POSE.equals(key)) {
//            if (this.getPose() == Pose.CROAKING) {
//                this.fishingAnimationState.startIfStopped(this.tickCount);
//            } else {
//                this.fishingAnimationState.stop();
//            }
//        }
//
//        super.onSyncedDataUpdated(key);
//    }

    // ========== BEHAVIOR ==========

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.4));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.0, FOOD_ITEMS, false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1));
        this.goalSelector.addGoal(5, new RandomStrollGoal(this, 1.0, 60));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(8, new DuckA.DuckStartFishingGoal(1.2F, 12, 1));
    }

    @Override
    public void aiStep() {
        if (!this.level.isClientSide && this.isAlive() && this.isEffectiveAi()) {
            ItemStack stack = this.getItemBySlot(EquipmentSlot.MAINHAND);
            if (!stack.isEmpty()) {
                ++this.ticksSinceEaten;
                if (stack.isEdible()) {
                    if (this.ticksSinceEaten > 600) {
                        ItemStack result = stack.finishUsingItem(this.level, this);
                        if (!result.isEmpty()) {
                            this.setItemSlot(EquipmentSlot.MAINHAND, result);
                        }

                        this.ticksSinceEaten = 0;
                        this.setAteRecently(true);
                    } else if (this.ticksSinceEaten > 560 && this.random.nextFloat() < 0.1F) {
                        this.playSound(SoundEvents.FOX_EAT, 1.0F, 1.0F);
                        this.level.broadcastEntityEvent(this, EntityEvents.DUCK_FISHING_PARTICLES);
                    }
                } else {
                    if (this.ticksSinceEaten > 600) {
                        this.level.broadcastEntityEvent(this, EntityEvents.DUCK_FISHING_ANIMATION);
                        this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
                        this.ticksSinceEaten = 0;
                    }
                }
            }

            if (this.ateRecently() && --this.ateCooldownTicks <= 0) {
                this.setAteRecently(false);
                this.ateCooldownTicks = 6000;
            }
        }

        super.aiStep();
        this.oFlap = this.flap;
        this.oFlapSpeed = this.flapSpeed;
        this.flapSpeed += (this.onGround ? -1.0F : 4.0F) * 0.3F;
        this.flapSpeed = Mth.clamp(this.flapSpeed, 0.0F, 1.0F);
        if (!this.onGround && this.flapping < 1.0F) {
            this.flapping = 1.0F;
        }

        this.flapping *= 0.9F;
        Vec3 movement = this.getDeltaMovement();
        if (!this.onGround && movement.y < 0.0) {
            this.setDeltaMovement(movement.multiply(1.0, 0.6, 1.0));
        }

        this.flap += this.flapping * 2.0F;
        if (!this.isInWaterOrBubble()) {
            if (!this.level.isClientSide && this.isAlive() && !this.isBaby() && --this.eggTime <= 0) {
                this.playSound(SoundEvents.CHICKEN_EGG, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
                this.spawnAtLocation(GEItems.DUCK_EGG.get());
                this.gameEvent(GameEvent.ENTITY_PLACE);
                this.eggTime = this.random.nextInt(6000) + 6000;
            }
        }
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == EntityEvents.DUCK_FISHING_ANIMATION) {
            this.fishingAnimationState.startIfStopped(this.tickCount);
        }

        if (id == EntityEvents.DUCK_FISHING_PARTICLES) {
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

    @Override
    public void tick() {
        if (this.level.isClientSide) {
            if (this.getPose() == Pose.CROAKING) {
                this.fishingAnimationState.startIfStopped(this.tickCount);
            }
        }

        super.tick();
        this.checkInsideBlocks();
        this.floatDuck();
    }

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
    protected float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
        return this.isBaby() ? dimensions.height * 0.85F : dimensions.height * 0.92F;
    }

    @Override
    protected boolean isFlapping() {
        return this.flyDist > this.nextFlap;
    }

    @Override
    protected void onFlap() {
        this.nextFlap = this.flyDist + this.flapSpeed / 2.0F;
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float multiplier, DamageSource source) {
        return false;
    }

    @Override @Nullable
    protected SoundEvent getAmbientSound() {
        return GESounds.DUCK_AMBIENT.get();
    }

    @Override @Nullable
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return GESounds.DUCK_HURT.get();
    }

    @Override @Nullable
    protected SoundEvent getDeathSound() {
        return GESounds.DUCK_DEATH.get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(GESounds.DUCK_STEP.get(), 0.025F, 1.0F);
    }

    @Nullable @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        return GEEntities.DUCK.get().create(level);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return FOOD_ITEMS.test(stack);
    }

    @Override
    public void positionRider(Entity passenger) {
        super.positionRider(passenger);
        float xRotationAngle = Mth.sin(this.yBodyRot * (float) (Math.PI / 180.0));
        float zRotationAngle = Mth.cos(this.yBodyRot * (float) (Math.PI / 180.0));
        passenger.setPos(this.getX() + (double)(0.1F * xRotationAngle), this.getY(0.5) + passenger.getMyRidingOffset() + 0.0, this.getZ() - (double)(0.1F * zRotationAngle));
        if (passenger instanceof LivingEntity living) {
            living.yBodyRot = this.yBodyRot;
        }
    }

    // ========== OPEN WATER TYPE ==========

    public boolean calculateOpenWater(BlockPos pos) {
        DuckA.OpenWaterType currentType = DuckA.OpenWaterType.INVALID;

        for (int i = -1; i <= 2; i++) {
            DuckA.OpenWaterType newType = this.getOpenWaterTypeForArea(pos.offset(-2, i, -2), pos.offset(2, i, 2));
            switch (newType) {
                case INVALID -> {
                    return false;
                }
                case ABOVE_WATER -> {
                    if (currentType == DuckA.OpenWaterType.INVALID) {
                        return false;
                    }
                }
                case INSIDE_WATER -> {
                    if (currentType == DuckA.OpenWaterType.ABOVE_WATER) {
                        return false;
                    }
                }
            }

            currentType = newType;
        }

        return true;
    }

    private DuckA.OpenWaterType getOpenWaterTypeForArea(BlockPos posA, BlockPos posB) {
        return BlockPos.betweenClosedStream(posA, posB)
            .map(this::getOpenWaterTypeForBlock)
            .reduce(
                (aType, bType) -> {
                    return aType == bType ? aType : DuckA.OpenWaterType.INVALID;
                }
            )
            .orElse(DuckA.OpenWaterType.INVALID);
    }

    private DuckA.OpenWaterType getOpenWaterTypeForBlock(BlockPos pos) {
        BlockState state = this.level.getBlockState(pos);
        if (!state.isAir()) {
            FluidState fluidState = state.getFluidState();
            return fluidState.is(FluidTags.WATER) &&
                fluidState.isSource() &&
                state.getCollisionShape(this.level, pos).isEmpty() ?
                DuckA.OpenWaterType.INSIDE_WATER : DuckA.OpenWaterType.INVALID;
        } else {
            return DuckA.OpenWaterType.ABOVE_WATER;
        }
    }

    // ========== PATHFINDING ==========

    @Override
    protected PathNavigation createNavigation(Level level) {
        return new DuckA.DuckPathNavigation(this, level);
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

    static class DuckPathNavigation extends GroundPathNavigation {
        public DuckPathNavigation(Mob mob, Level level) {
            super(mob, level);
        }

        @Override
        protected boolean hasValidPathType(BlockPathTypes pathType) {
            return pathType == BlockPathTypes.WATER || super.hasValidPathType(pathType);
        }

        @Override
        public boolean isStableDestination(BlockPos pos) {
            return this.level.getBlockState(pos).is(Blocks.WATER) || super.isStableDestination(pos);
        }
    }

    class DuckStartFishingGoal extends MoveToBlockGoal {
        protected int ticksWaited;

        public DuckStartFishingGoal(double speedModifier, int searchRange, int verticalSearchRange) {
            super(DuckA.this, speedModifier, searchRange, verticalSearchRange);
        }

        @Override
        public boolean canUse() {
            return (!DuckA.this.ateRecently() || DuckA.this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty()) && !DuckA.this.isBaby() && super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            return DuckA.this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty() && super.canContinueToUse();
        }

        @Override
        public void start() {
            this.ticksWaited = 0;
            DuckA.this.transitionToState(DuckA.State.FISHING);
            super.start();
        }

        @Override
        public boolean shouldRecalculatePath() {
            return this.tryTicks % 100 == 0;
        }

        @Override
        protected boolean isValidTarget(LevelReader level, BlockPos pos) {
            return DuckA.this.calculateOpenWater(pos);
        }

        @Override
        public void tick() {
            if (this.isReachedTarget()) {
                DuckA.this.getNavigation().stop();
                if (this.ticksWaited == 60) {
                    this.startFishing();
                } else {
                    ++this.ticksWaited;
                }
            }

            super.tick();
        }

        private void startFishing() {
            ItemStack stack = DuckA.this.getItemBySlot(EquipmentSlot.MAINHAND);
            if (stack.isEmpty()) {
                DuckA.this.playSound(SoundEvents.FISHING_BOBBER_SPLASH, 1.0F, 1.0F);
                this.retrieveItemFromFishing();
            }
        }

        private void retrieveItemFromFishing() {
            MinecraftServer server = DuckA.this.level.getServer();
            if (!DuckA.this.level.isClientSide && server != null) {
                // Get an item from the fishing loot table
                LootContext.Builder builder = new LootContext.Builder((ServerLevel) DuckA.this.level)
                    .withParameter(LootContextParams.ORIGIN, DuckA.this.position())
                    .withParameter(LootContextParams.TOOL, new ItemStack(Items.FISHING_ROD))
                    .withParameter(LootContextParams.THIS_ENTITY, DuckA.this)
                    .withRandom(DuckA.this.random)
                    .withLuck((float) DuckA.this.getAttributeValue(Attributes.LUCK));
                LootTable lootTable = server.getLootTables().get(BuiltInLootTables.FISHING);
                List<ItemStack> loot = lootTable.getRandomItems(builder.create(LootContextParamSets.FISHING));

                // For each item found, hold it in the beak and set it as guaranteed drop
                for (ItemStack stack : loot) {
                    DuckA.this.setGuaranteedDrop(EquipmentSlot.MAINHAND);
                    DuckA.this.setItemSlot(EquipmentSlot.MAINHAND, stack);
                    break;
                }
            }
        }

        @Override
        public void stop() {
            DuckA.this.transitionToState(DuckA.State.IDLING);
        }
    }

    enum OpenWaterType {
        ABOVE_WATER,
        INSIDE_WATER,
        INVALID
    }

    public enum State {
        IDLING,
        FISHING
    }
}
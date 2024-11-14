package com.blackgear.geologicexpansion.common.entity.duck;

import com.blackgear.geologicexpansion.common.entity.duck.behavior.DuckFishGoal;
import com.blackgear.geologicexpansion.common.entity.duck.behavior.DuckGoToOpenWaterGoal;
import com.blackgear.geologicexpansion.common.entity.duck.behavior.DuckGoToWaterGoal;
import com.blackgear.geologicexpansion.common.entity.resource.FluidWalker;
import com.blackgear.geologicexpansion.common.registries.GEEntities;
import com.blackgear.geologicexpansion.common.registries.GEItems;
import com.blackgear.geologicexpansion.common.registries.GESounds;
import com.blackgear.geologicexpansion.core.platform.common.resource.TimeValue;
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
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Duck extends Animal implements FluidWalker {
    private static final EntityDataAccessor<Boolean> DATA_CAN_FISH = SynchedEntityData.defineId(Duck.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DATA_HUNTS_REMAINING = SynchedEntityData.defineId(Duck.class, EntityDataSerializers.INT);
    public final AnimationState floatTransformationState = new AnimationState();

    // Constants
    public static final IntProvider NON_FOOD_DISCARD_COOLDOWN = UniformInt.of(TimeValue.minutes(3), TimeValue.minutes(5));

    // Entity Events
    public static final byte DUCK_FISHING_ANIMATION = (byte)10;
    public static final byte DUCK_FISHING_PARTICLES = (byte)45;

    // Flap related variables
    public float flap;
    public float flapSpeed;
    public float oFlapSpeed;
    public float oFlap;

    // Flapping behavior variables
    private float flapping = 1.0F;
    private float nextFlap = 1.0F;

    // Eating related variables
    private int ticksSinceEaten;
    private int discardCooldown;
    private int eatAnimationTick;
    private DuckFishGoal duckFishGoal;

    private int eggTime;

    public Duck(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        this.eggTime = this.random.nextInt(6000) + 6000;
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        this.discardCooldown = NON_FOOD_DISCARD_COOLDOWN.sample(this.random);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 4.0).add(Attributes.MOVEMENT_SPEED, 0.25).add(Attributes.LUCK, 2.0D);
    }

    // ========= DATA CONTROL ==========================================================================================

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_CAN_FISH, false);
        this.entityData.define(DATA_HUNTS_REMAINING, 5);
    }

    public void setHuntsRemaining(int huntsRemaining) {
        this.entityData.set(DATA_HUNTS_REMAINING, huntsRemaining);
    }

    public int getHuntsRemaining() {
        return this.entityData.get(DATA_HUNTS_REMAINING);
    }

    public void setCanFish(boolean canFish) {
        this.entityData.set(DATA_CAN_FISH, canFish);
    }

    public boolean canFish() {
        return this.entityData.get(DATA_CAN_FISH);
    }

    public boolean shouldFish() {
        return !this.isBaby() && this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty() && this.getHuntsRemaining() != 0;
    }

    // ========== BEHAVIOR =============================================================================================

    @Override
    protected void registerGoals() {
        this.duckFishGoal = new DuckFishGoal(this);
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.4D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.1, Ingredient.of(Items.COD, Items.SALMON, Items.WHEAT_SEEDS), false));
        this.goalSelector.addGoal(3, new DuckGoToOpenWaterGoal(this, 1.0));
        this.goalSelector.addGoal(3, new DuckGoToWaterGoal(this, 1.0));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(5, this.duckFishGoal);
        this.goalSelector.addGoal(6, new RandomStrollGoal(this, 1.0D, 60));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Duck.class, 6.0F));
    }

    @Override
    public void tick() {
        if (this.level().isClientSide()) {
            if (this.isInWaterOrBubble()) {
                this.floatTransformationState.startIfStopped(this.tickCount);
            } else {
                this.floatTransformationState.stop();
            }
        }

        super.tick();

        this.setCanFish(this.isInOpenWater() && this.shouldFish());
        this.floatDuck();
        this.checkInsideBlocks();

        // Resets the hunts per day each morning
        float timeOfDay = this.level().getTimeOfDay(1.0F);
        if ((double)timeOfDay < 0.79 && (double)timeOfDay > 0.76 && this.getHuntsRemaining() < 5) {
            this.setHuntsRemaining(5);
        }
    }

    @Override
    protected void customServerAiStep() {
        this.eatAnimationTick = this.duckFishGoal.getEatAnimationTick();
        super.customServerAiStep();
    }

    @Override
    public void aiStep() {
        if (!this.level().isClientSide && this.isAlive() && this.isEffectiveAi()) {
            this.postFishing();
        }

        if (this.level().isClientSide) {
            this.eatAnimationTick = Math.max(0, this.eatAnimationTick - 1);
        }

        super.aiStep();
        this.oFlap = this.flap;
        this.oFlapSpeed = this.flapSpeed;
        this.flapSpeed += (this.onGround() ? -1.0F : 4.0F) * 0.3F;
        this.flapSpeed = Mth.clamp(this.flapSpeed, 0.0F, 1.0F);

        if (!this.onGround() && this.flapping < 1.0F) {
            this.flapping = 1.0F;
        }

        this.flapping *= 0.9F;
        Vec3 movement = this.getDeltaMovement();
        if (!this.onGround() && movement.y < 0.0) {
            this.setDeltaMovement(movement.multiply(1.0, 0.6, 1.0));
        }

        this.flap += this.flapping * 2.0F;
        if (!this.level().isClientSide && this.onGround() && !this.isInWaterOrBubble() && this.isAlive() && !this.isBaby() && --this.eggTime <= 0) {
            this.playSound(SoundEvents.CHICKEN_EGG, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            this.spawnAtLocation(GEItems.DUCK_EGG.get());
            this.gameEvent(GameEvent.ENTITY_PLACE);
            this.eggTime = this.random.nextInt(6000) + 6000;
        }
    }

    private void postFishing() {
        ItemStack stack = this.getItemBySlot(EquipmentSlot.MAINHAND);
        if (!stack.isEmpty()) {
            if (this.canEat(stack)) {
                ++this.ticksSinceEaten;
                if (this.ticksSinceEaten > TimeValue.seconds(30)) {
                    // Starts consuming the item, applying effects if possible
                    ItemStack consumedStack = stack.finishUsingItem(this.level(), this);
                    if (!consumedStack.isEmpty()) {
                        this.setItemSlot(EquipmentSlot.MAINHAND, consumedStack);
                    }

                    this.ticksSinceEaten = 0;

                    // Decreases the hunts remaining per day
                    postFishingProgress();
                } else if (this.ticksSinceEaten > TimeValue.seconds(28) && this.random.nextFloat() < 0.1F) {
                    this.playSound(SoundEvents.FOX_EAT, 1.0F, 1.0F);
                    this.level().broadcastEntityEvent(this, DUCK_FISHING_PARTICLES);

                    postFishingProgress();
                }
            } else {
                // Discard Item
                if (--this.discardCooldown == 0) {
                    this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
                    this.level().broadcastEntityEvent(this, DUCK_FISHING_ANIMATION);
                    this.discardCooldown = NON_FOOD_DISCARD_COOLDOWN.sample(this.random);

                    // Decreases the hunts remaining per day
                    postFishingProgress();
                }
            }
        }
    }

    private void postFishingProgress() {
        // Decreases the hunts remaining per day
        if (this.getHuntsRemaining() != 0) {
            this.setHuntsRemaining(this.getHuntsRemaining() - 1);
        }
    }

    private boolean canEat(ItemStack stack) {
        return stack.getItem().isEdible() && this.onGround() && !stack.is(Items.PUFFERFISH);
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == DUCK_FISHING_ANIMATION) {
            this.eatAnimationTick = 40;
        } else if (id == DUCK_FISHING_PARTICLES) {
            ItemStack stack = this.getItemBySlot(EquipmentSlot.MAINHAND);
            if (!stack.isEmpty()) {
                for(int i = 0; i < 8; ++i) {
                    Vec3 vec3 = new Vec3(((double)this.random.nextFloat() - 0.5) * 0.1, Math.random() * 0.1 + 0.1, 0.0)
                            .xRot(-this.getXRot() * (float) (Math.PI / 180.0))
                            .yRot(-this.getYRot() * (float) (Math.PI / 180.0));
                    this.level().addParticle(
                            new ItemParticleOption(ParticleTypes.ITEM, stack),
                            this.getX() + this.getLookAngle().x / 2.0,
                            this.getY(),
                            this.getZ() + this.getLookAngle().z / 2.0,
                            vec3.x,
                            vec3.y + 0.05,
                            vec3.z
                    );
                }
            }
        } else {
            super.handleEntityEvent(id);
        }
    }

    public float getHeadEatPositionScale(float partialTick) {
        if (this.eatAnimationTick <= 0) {
            return 0.0F;
        } else if (this.eatAnimationTick >= 4 && this.eatAnimationTick <= 36) {
            return 1.0F;
        } else {
            return this.eatAnimationTick < 4 ? ((float)this.eatAnimationTick - partialTick) / 4.0F : -((float)(this.eatAnimationTick - 40) - partialTick) / 4.0F;
        }
    }

    public float getHeadEatAngleScale(float partialTick) {
        if (this.eatAnimationTick > 4 && this.eatAnimationTick <= 36) {
            float f = ((float)(this.eatAnimationTick - 4) - partialTick) / 32.0F;
            return (float) (Math.PI / 5) + 0.21991149F * Mth.sin(f * 28.7F);
        } else {
            return this.eatAnimationTick > 0 ? (float) (Math.PI / 5) : this.getXRot() * (float) (Math.PI / 180.0);
        }
    }

    @Override
    public void ate() {
        super.ate();
        this.retrieve();
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
        return this.isBaby() ? dimensions.height * 0.85F : dimensions.height * 0.92F;
    }

    protected boolean isFlapping() {
        return this.flyDist > this.nextFlap;
    }

    protected void onFlap() {
        this.nextFlap = this.flyDist + this.flapSpeed / 2.0F;
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float multiplier, DamageSource source) {
        return false;
    }

    @Override @Nullable
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        return GEEntities.DUCK.get().create(level);
    }

    @Override
    protected void dropAllDeathLoot(DamageSource damageSource) {
        ItemStack stack = this.getItemBySlot(EquipmentSlot.MAINHAND);
        if (!stack.isEmpty()) {
            this.spawnAtLocation(stack);
            this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
        }

        super.dropAllDeathLoot(damageSource);
    }

    @Override
    public boolean canHoldItem(ItemStack stack) {
        Item item = stack.getItem();
        ItemStack itemStack = this.getItemBySlot(EquipmentSlot.MAINHAND);
        return itemStack.isEmpty() || this.ticksSinceEaten > 0 && item.isEdible() && !stack.getItem().isEdible();
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack playerHeld = player.getItemInHand(hand);
        ItemStack duckHeld = this.getItemBySlot(EquipmentSlot.MAINHAND);
        if (this.isFood(playerHeld) && !duckHeld.isEmpty()) {
            // play sound duck trade
            this.setItemSlot(EquipmentSlot.MAINHAND, playerHeld);
            ItemStack tradeResult = ItemUtils.createFilledResult(playerHeld, player, duckHeld);
            player.setItemInHand(hand, tradeResult);
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        } else {
            return super.mobInteract(player, hand);
        }
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(Items.WHEAT_SEEDS);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("EggLayTime")) {
            this.eggTime = tag.getInt("EggLayTime");
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("EggLayTime", this.eggTime);
    }

    // ========== FLOAT BEHAVIOR =======================================================================================

    private void floatDuck() {
        if (this.isInWater()) {
            CollisionContext context = CollisionContext.of(this);
            if (context.isAbove(this.getStableLiquidShape(), this.blockPosition(), true) && !this.level().getFluidState(this.blockPosition().above()).is(FluidTags.WATER)) {
                this.setOnGround(true);
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
            return this.isInWater() ? Float.NEGATIVE_INFINITY : 0.0F;
        }
    }

    @Override
    public boolean canStandOnFluid(FluidState fluidState) {
        return fluidState.is(FluidTags.WATER);
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    @Override
    public VoxelShape getStableLiquidShape() {
        return Block.box(0.0, 0.0, 0.0, 16.0, 12.0, 16.0);
    }

    // ========== SPAWNS ===============================================================================================

    public static boolean checkDuckSpawnRules(EntityType<Duck> duck, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        BlockState state = level.getBlockState(pos.below());
        return (state.is(BlockTags.ANIMALS_SPAWNABLE_ON) || state.is(Blocks.WATER)) && isBrightEnoughToSpawn(level, pos);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag) {
        if (spawnData == null) {
            spawnData = new AgeableMobGroupData(0.75F);
        }

        return super.finalizeSpawn(level, difficulty, reason, spawnData, dataTag);
    }

    // ========== SOUNDS ===============================================================================================

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

    // ========== FISHING BEHAVIOR =====================================================================================

    private void retrieve() {
        MinecraftServer server = this.level().getServer();
        if (!this.level().isClientSide && server != null) {
            // Get an item from the fishing loot table
            LootParams builder = new LootParams.Builder((ServerLevel)this.level())
                    .withParameter(LootContextParams.ORIGIN, this.position())
                    .withParameter(LootContextParams.TOOL, new ItemStack(Items.FISHING_ROD))
                    .withParameter(LootContextParams.THIS_ENTITY, this)
                    .withLuck((float)this.getAttributeValue(Attributes.LUCK))
                    .create(LootContextParamSets.FISHING);
            LootTable lootTable = server.getLootData().getLootTable(BuiltInLootTables.FISHING);
            List<ItemStack> items = lootTable.getRandomItems(builder);

            // For each item found, hold it in the beak and set it as guaranteed drop
            for (ItemStack item : items) {
                this.setItemSlot(EquipmentSlot.MAINHAND, item);
                this.setGuaranteedDrop(EquipmentSlot.MAINHAND);
                break;
            }
        }
    }

    public boolean isInOpenWater() {
        return this.calculateOpenWater(this.blockPosition());
    }

    public boolean calculateOpenWater(BlockPos pos) {
        OpenWaterType currentType = OpenWaterType.INVALID;

        for (int i = -1; i <= 2; i++) {
            OpenWaterType newType = this.getOpenWaterTypeForArea(pos.offset(-2, i, -2), pos.offset(2, i, 2));
            switch (newType) {
                case INVALID -> {
                    return false;
                }
                case ABOVE_WATER -> {
                    if (currentType == OpenWaterType.INVALID) {
                        return false;
                    }
                }
                case INSIDE_WATER -> {
                    if (currentType == OpenWaterType.ABOVE_WATER) {
                        return false;
                    }
                }
            }

            currentType = newType;
        }

        return true;
    }

    private OpenWaterType getOpenWaterTypeForArea(BlockPos posA, BlockPos posB) {
        return BlockPos.betweenClosedStream(posA, posB).map(this::getOpenWaterTypeForBlock).reduce((openWaterTypeA, openWaterTypeB) -> {
            return openWaterTypeA == openWaterTypeB ? openWaterTypeA : OpenWaterType.INVALID;
        }).orElse(OpenWaterType.INVALID);
    }

    private OpenWaterType getOpenWaterTypeForBlock(BlockPos pos) {
        BlockState state = this.level().getBlockState(pos);
        if (!state.isAir() && !state.is(Blocks.LILY_PAD)) {
            FluidState fluidState = state.getFluidState();
            return fluidState.is(FluidTags.WATER) &&
                    fluidState.isSource() &&
                    state.getCollisionShape(this.level(), pos).isEmpty() ?
                    OpenWaterType.INSIDE_WATER : OpenWaterType.INVALID;
        } else {
            return OpenWaterType.ABOVE_WATER;
        }
    }

    enum OpenWaterType {
        ABOVE_WATER,
        INSIDE_WATER,
        INVALID
    }

    static class DuckPathNavigation extends GroundPathNavigation {
        public DuckPathNavigation(Mob mob, Level level) {
            super(mob, level);
        }

        @Override
        protected PathFinder createPathFinder(int maxVisitedNodes) {
            this.nodeEvaluator = new WalkNodeEvaluator();
            this.nodeEvaluator.setCanPassDoors(true);
            return new PathFinder(this.nodeEvaluator, maxVisitedNodes);
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
}
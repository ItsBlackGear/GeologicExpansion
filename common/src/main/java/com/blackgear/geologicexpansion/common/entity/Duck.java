package com.blackgear.geologicexpansion.common.entity;

import com.blackgear.geologicexpansion.common.entity.resource.FluidWalker;
import com.blackgear.geologicexpansion.common.registries.GEEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class Duck extends Animal implements FluidWalker {
    public final AnimationState floatTransformationState = new AnimationState();
    public float flap;
    public float flapSpeed;
    public float oFlapSpeed;
    public float oFlap;
    private float flapping = 1.0F;
    private float nextFlap = 1.0F;

    public Duck(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.4D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(6, new RandomStrollGoal(this, 1.0D, 60));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Duck.class, 6.0F));
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
        return this.isBaby() ? dimensions.height * 0.85F : dimensions.height * 0.92F;
    }

    @Override
    public void aiStep() {
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
    protected SoundEvent getAmbientSound() {
        return super.getAmbientSound();
    }

    @Override @Nullable
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return super.getHurtSound(damageSource);
    }

    @Override @Nullable
    protected SoundEvent getDeathSound() {
        return super.getDeathSound();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        super.playStepSound(pos, state);
    }

    @Override @Nullable
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        return GEEntities.DUCK.get().create(level);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.isInWaterOrBubble()) {
            this.floatTransformationState.startIfStopped(this.tickCount);
        } else {
            this.floatTransformationState.stop();
        }

        this.floatDuck();
        this.checkInsideBlocks();
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
}
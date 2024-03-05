package com.blackgear.geologicexpansion.common.entity.duck.goals;

import com.blackgear.geologicexpansion.common.entity.duck.ODuck;
import com.blackgear.geologicexpansion.core.config.ConfigEntries;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

import java.util.EnumSet;

public class DuckFishGoal extends Goal {
    private static final int EAT_ANIMATION_TICKS = 40;
    private final ODuck duck;
    private final Level level;
    private int eatAnimationTick;

    public DuckFishGoal(ODuck duck) {
        this.duck = duck;
        this.level = duck.level;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
    }

    @Override
    public boolean canUse() {
        if (!ConfigEntries.canDucksFish()) {
            return false;
        }

        if (this.duck.getRandom().nextInt(1000) != 0) {
            return false;
        } else {
            BlockPos pos = this.duck.blockPosition();
            return this.level.getBlockState(pos.below()).is(Blocks.WATER) && this.duck.canFish() && this.duck.shouldFish();
        }
    }

    @Override
    public void start() {
        this.eatAnimationTick = this.adjustedTickDelay(EAT_ANIMATION_TICKS);
        this.level.broadcastEntityEvent(this.duck, ODuck.DUCK_FISHING_ANIMATION);
        this.duck.getNavigation().stop();
    }

    @Override
    public void stop() {
        this.eatAnimationTick = 0;
    }

    @Override
    public boolean canContinueToUse() {
        return this.eatAnimationTick > 0;
    }

    public int getEatAnimationTick() {
        return this.eatAnimationTick;
    }

    @Override
    public void tick() {
        this.eatAnimationTick = Math.max(0, this.eatAnimationTick - 1);
        if (this.eatAnimationTick == this.adjustedTickDelay(4)) {
            BlockPos pos = this.duck.blockPosition().below();
            if (this.level.getBlockState(pos).is(Blocks.WATER) && this.duck.canFish() && this.duck.shouldFish()) {
                this.duck.playSound(SoundEvents.FISHING_BOBBER_SPLASH, 0.25F, 1.0F + (this.level.random.nextFloat() - this.level.random.nextFloat()) * 0.4F);
                this.duck.ate();
            }
        }
    }
}
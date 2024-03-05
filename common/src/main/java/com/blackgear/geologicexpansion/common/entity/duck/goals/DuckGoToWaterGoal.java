package com.blackgear.geologicexpansion.common.entity.duck.goals;

import com.blackgear.geologicexpansion.common.entity.duck.ODuck;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.PathComputationType;

public class DuckGoToWaterGoal extends MoveToBlockGoal {
    final ODuck duck;

    public DuckGoToWaterGoal(ODuck duck, double speed) {
        super(duck, speed, 8, 2);
        this.duck = duck;
    }

    @Override
    protected BlockPos getMoveToTarget() {
        return this.blockPos;
    }

    @Override
    public boolean canContinueToUse() {
        return (this.shouldUse() || this.duck.getFeetBlockState().is(Blocks.LILY_PAD)) && this.isValidTarget(this.duck.level, this.blockPos);
    }

    @Override
    public boolean canUse() {
        return (this.shouldUse() || this.duck.getFeetBlockState().is(Blocks.LILY_PAD)) && super.canUse();
    }

    protected boolean shouldUse() {
        return !this.duck.isInWaterOrBubble();
    }

    @Override
    public boolean shouldRecalculatePath() {
        return this.tryTicks % 20 == 0;
    }

    @Override
    protected boolean isValidTarget(LevelReader level, BlockPos pos) {
        return level.getBlockState(pos).is(Blocks.WATER)
                && !level.getBlockState(pos).is(Blocks.LILY_PAD)
                && level.getBlockState(pos.above()).isPathfindable(level, pos, PathComputationType.LAND);
    }
}
package com.blackgear.geologicexpansion.common.entity.duck.goals;

import com.blackgear.geologicexpansion.common.entity.duck.ODuck;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;

public class DuckGoToOpenWaterGoal extends DuckGoToWaterGoal {
    public DuckGoToOpenWaterGoal(ODuck duck, double speed) {
        super(duck, speed);
    }

    @Override
    protected boolean shouldUse() {
        return this.duck.isInWaterOrBubble() && this.duck.shouldFish() && !this.duck.isInOpenWater();
    }

    @Override
    protected boolean isValidTarget(LevelReader level, BlockPos pos) {
        return super.isValidTarget(level, pos) && this.duck.calculateOpenWater(pos);
    }
}
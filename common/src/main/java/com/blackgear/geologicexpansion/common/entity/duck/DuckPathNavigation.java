package com.blackgear.geologicexpansion.common.entity.duck;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.BlockPathTypes;

public class DuckPathNavigation extends GroundPathNavigation {
    public DuckPathNavigation(Mob mob, Level level) {
        super(mob, level);
    }

    @Override
    protected boolean hasValidPathType(BlockPathTypes pathType) {
        return pathType == BlockPathTypes.WATER || super.hasValidPathType(pathType);
    }

    @Override
    public boolean isStableDestination(BlockPos pos) {
        return this.level.getBlockState(pos).is(Blocks.WATER) && this.level.getBlockState(pos.above()).isAir() || super.isStableDestination(pos);
    }
}
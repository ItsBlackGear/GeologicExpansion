package com.blackgear.geologicexpansion.common.entity.resource;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

public enum OpenWaterType {
    ABOVE_WATER, INSIDE_WATER, INVALID;

    public static boolean calculateOpenWater(LevelReader level, BlockPos pos) {
        OpenWaterType type = INVALID;

        for (int i = -1; i <= 2; i++) {
            OpenWaterType typeForArea = getOpenWaterTypeForArea(level, pos.offset(-2, i, -2), pos.offset(2, i, 2));
            switch (typeForArea) {
                case INVALID -> {
                    return false;
                }
                case ABOVE_WATER -> {
                    if (type == INVALID) {
                        return false;
                    }
                }
                case INSIDE_WATER -> {
                    if (type == ABOVE_WATER) {
                        return false;
                    }
                }
            }

            type = typeForArea;
        }

        return true;
    }

    private static OpenWaterType getOpenWaterTypeForArea(LevelReader level, BlockPos posA, BlockPos posB) {
        return BlockPos.betweenClosedStream(posA, posB)
            .map((pos) -> getOpenWaterTypeForBlock(level, pos))
            .reduce(
                (typeA, typeB) -> {
                    return typeA == typeB ? typeA : INVALID;
                }
            )
            .orElse(INVALID);
    }

    private static OpenWaterType getOpenWaterTypeForBlock(LevelReader level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        if (!state.isAir()) {
            FluidState fluidState = state.getFluidState();
            if (fluidState.is(FluidTags.WATER) && fluidState.isSource() && state.getCollisionShape(level, pos).isEmpty()) {
                return INSIDE_WATER;
            }

            return INVALID;
        } else {
            return ABOVE_WATER;
        }
    }
}
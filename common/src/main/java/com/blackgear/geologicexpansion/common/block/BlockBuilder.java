package com.blackgear.geologicexpansion.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class BlockBuilder {
    // ========== BLOCK CREATION =======================================================================================
    public static RotatedPillarBlock log(MaterialColor topColor, MaterialColor barkColor) {
        return new RotatedPillarBlock(
            BlockBehaviour.Properties.of(Material.WOOD, blockState -> blockState.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? topColor : barkColor)
                .strength(2.0F)
                .sound(SoundType.WOOD)
        );
    }

    public static LeavesBlock leaves(SoundType type) {
        return new LeavesBlock(
            BlockBehaviour.Properties.of(Material.LEAVES)
                .strength(0.2F)
                .randomTicks()
                .sound(type)
                .noOcclusion()
                .isValidSpawn(BlockBuilder::ocelotOrParrot)
                .isSuffocating(BlockBuilder::never)
                .isViewBlocking(BlockBuilder::never)
        );
    }

    // ========== STATE PREDICATE ======================================================================================
    public static boolean never(BlockState state, BlockGetter level, BlockPos pos, EntityType<?> entity) {
        return false;
    }

    public static boolean always(BlockState state, BlockGetter level, BlockPos pos, EntityType<?> entity) {
        return true;
    }

    public static boolean ocelotOrParrot(BlockState state, BlockGetter level, BlockPos pos, EntityType<?> entity) {
        return entity == EntityType.OCELOT || entity == EntityType.PARROT;
    }

    public static boolean always(BlockState state, BlockGetter level, BlockPos pos) {
        return true;
    }

    public static boolean never(BlockState state, BlockGetter level, BlockPos pos) {
        return false;
    }
}
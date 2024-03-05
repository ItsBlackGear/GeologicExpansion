package com.blackgear.geologicexpansion.common.worldgen.features;

import com.blackgear.geologicexpansion.common.registries.GEBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.DripstoneUtils;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PondFeature extends Feature<NoneFeatureConfiguration> {
    private static final List<Vec3i> POSITIONS = List.of(
        new Vec3i(-1, 0, -1),
        new Vec3i(-1, 0, 0),
        new Vec3i(-1, 0, 1),
        new Vec3i(0, 0, -1),
        new Vec3i(1, 0, -1),
        new Vec3i(1, 0, 0),
        new Vec3i(1, 0, 1),
        new Vec3i(0, 0, 1)
    );

    public PondFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        BlockPos pos = context.origin();
        int x = UniformInt.of(4, 7).sample(random) + 1;
        int z = UniformInt.of(4, 7).sample(random) + 1;
        Set<BlockPos> positions = this.placeGroundPatch(level, random, pos, x, z);
        return !positions.isEmpty();
    }

    protected Set<BlockPos> placeGroundPatch(WorldGenLevel world, RandomSource random, BlockPos blockPos, int xRadius, int zRadius) {
        BlockPos.MutableBlockPos currentPos = blockPos.mutable();
        BlockPos.MutableBlockPos nextPos = currentPos.mutable();
        Direction surfaceDirection = Direction.DOWN;
        Direction oppositeDirection = surfaceDirection.getOpposite();
        Set<BlockPos> possiblePositions = new HashSet<>();

        for(int x = -xRadius; x <= xRadius; ++x) {
            boolean isXEdge = x == -xRadius || x == xRadius;

            for(int z = -zRadius; z <= zRadius; ++z) {
                boolean isZEdge = z == -zRadius || z == zRadius;
                boolean isEdge = isXEdge || isZEdge;
                boolean isCorner = isXEdge && isZEdge;
                boolean isEdgeNotCorner = isEdge && !isCorner;

                if (!isCorner && (!isEdgeNotCorner || random.nextFloat() <= 0.3F)) {
                    currentPos.setWithOffset(blockPos, x, 0, z);

                    for (int y = 0; world.isStateAtPosition(currentPos, DripstoneUtils::isEmptyOrWater) && y < 5; ++y) {
                        currentPos.move(surfaceDirection);
                    }

                    for (int y = 0; world.isStateAtPosition(currentPos, (state) -> !(state.isAir() || state.is(Blocks.WATER))) && y < 5; ++y) {
                        currentPos.move(oppositeDirection);
                    }

                    nextPos.setWithOffset(currentPos, surfaceDirection);
                    BlockState nextState = world.getBlockState(nextPos);
                    if (world.isStateAtPosition(currentPos, DripstoneUtils::isEmptyOrWater) && nextState.isFaceSturdy(world, nextPos, oppositeDirection)) {
                        int depth = 3;
                        BlockPos position = nextPos.immutable();
                        boolean isPlaced = this.placeGround(world, nextPos, depth);
                        if (isPlaced) {
                            possiblePositions.add(position);
                        }
                    }
                }
            }
        }

        return possiblePositions;
    }

    protected boolean placeGround(WorldGenLevel world, BlockPos.MutableBlockPos mutable, int depth) {
        List<Block> borderReplaceables = List.of(
            GEBlocks.ORANGE_PRISMATIC_STONE.get(),
            GEBlocks.RED_PRISMATIC_STONE.get(),
            GEBlocks.BROWN_PRISMATIC_STONE.get(),
            Blocks.AIR,
            Blocks.LAVA
        );
        List<Block> lakeReplaceables = List.of(
            GEBlocks.YELLOW_PRISMATIC_STONE.get(),
            Blocks.MAGMA_BLOCK
        );
        List<Block> floorReplaceables = List.of(
            GEBlocks.YELLOW_PRISMATIC_STONE.get(),
            GEBlocks.ORANGE_PRISMATIC_STONE.get(),
            GEBlocks.RED_PRISMATIC_STONE.get(),
            GEBlocks.BROWN_PRISMATIC_STONE.get(),
            GEBlocks.PURPLE_PRISMATIC_STONE.get(),
            GEBlocks.PRISMATIC_STONE.get(),
            Blocks.GRASS_BLOCK,
            Blocks.DIRT,
            Blocks.COARSE_DIRT,
            Blocks.WATER,
            Blocks.LAVA
        );

        BlockPredicate borderBlockPredicate = BlockPredicate.anyOf(
            POSITIONS.stream().map(vector -> {
                return BlockPredicate.matchesBlocks(vector, borderReplaceables);
            }).toArray(BlockPredicate[]::new)
        );

        BlockPredicate floorBlockPredicate = BlockPredicate.anyOf(
            POSITIONS.stream().map(vector -> {
                return BlockPredicate.matchesBlocks(vector.above(), floorReplaceables);
            }).toArray(BlockPredicate[]::new)
        );

        BlockPredicate replace = BlockPredicate.allOf(
            BlockPredicate.not(borderBlockPredicate),
            BlockPredicate.matchesBlocks(Vec3i.ZERO, lakeReplaceables),
            BlockPredicate.not(floorBlockPredicate)
        );

        Block block = Blocks.WATER;
        for (int i = 0; i < depth; ++i) {
            BlockState groundState = block.defaultBlockState();
            BlockState currentState = world.getBlockState(mutable);
            if (!groundState.is(currentState.getBlock())) {
                if (!replace.test(world, mutable)) {
                    return i != 0;
                }

                world.setBlock(mutable, groundState, 2);
                mutable.move(Direction.DOWN);
            }
        }

        return true;
    }
}
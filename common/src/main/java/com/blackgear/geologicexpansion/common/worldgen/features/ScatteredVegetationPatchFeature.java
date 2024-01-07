package com.blackgear.geologicexpansion.common.worldgen.features;

import com.blackgear.geologicexpansion.common.registries.GEBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

public class ScatteredVegetationPatchFeature extends Feature<VegetationPatchConfiguration> {
    public ScatteredVegetationPatchFeature(Codec<VegetationPatchConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<VegetationPatchConfiguration> context) {
        WorldGenLevel level = context.level();
        VegetationPatchConfiguration config = context.config();
        RandomSource random = context.random();
        BlockPos pos = context.origin();

        Predicate<BlockState> replaceable = blockState -> blockState.is(GEBlocks.PRISMATIC_STONE.get());
//        Predicate<BlockState> replaceable = blockState -> blockState.is(config.replaceable);

        int xRadius = config.xzRadius.sample(random) + 1;
        int zRadius = config.xzRadius.sample(random) + 1;

        Set<BlockPos> placedPositions = this.placeGroundPatch(level, config, random, pos, replaceable, xRadius, zRadius);
        this.distributeVegetation(context, level, config, random, placedPositions);

        return !placedPositions.isEmpty();
    }

    protected Set<BlockPos> placeGroundPatch(WorldGenLevel level, VegetationPatchConfiguration config, RandomSource random, BlockPos pos, Predicate<BlockState> state, int xRadius, int zRadius) {
        BlockPos.MutableBlockPos currentPos = pos.mutable();
        BlockPos.MutableBlockPos surfacePos = currentPos.mutable();

        Direction surfaceDire = config.surface.getDirection();
        Direction oppositeSurfacePosition = surfaceDire.getOpposite();

        Set<BlockPos> placedPositions = new HashSet<>();

        for(int xOffset = -xRadius; xOffset <= xRadius; ++xOffset) {
            boolean isXEdge = xOffset == -xRadius || xOffset == xRadius;

            for(int zOffset = -zRadius; zOffset <= zRadius; ++zOffset) {
                boolean isZEdge = zOffset == -zRadius || zOffset == zRadius;
                boolean isEdge = isXEdge || isZEdge;
                boolean isCorner = isXEdge && isZEdge;
                boolean isMiddle = isEdge && !isCorner;

                if (!isCorner && (!isMiddle || config.extraEdgeColumnChance != 0.0F && random.nextFloat() <= config.extraEdgeColumnChance)) {
                    currentPos.setWithOffset(pos, xOffset, 0, zOffset);

                    for(int yOffset = 0; level.isStateAtPosition(currentPos, BlockBehaviour.BlockStateBase::isAir) && yOffset < config.verticalRange; ++yOffset) {
                        currentPos.move(surfaceDire);
                    }

                    for(int yOffset = 0; level.isStateAtPosition(currentPos, blockStatex -> !blockStatex.isAir()) && yOffset < config.verticalRange; ++yOffset) {
                        currentPos.move(oppositeSurfacePosition);
                    }

                    surfacePos.setWithOffset(currentPos, config.surface.getDirection());
                    BlockState surfaceState = level.getBlockState(surfacePos);

                    if (level.isEmptyBlock(currentPos) && surfaceState.isFaceSturdy(level, surfacePos, config.surface.getDirection().getOpposite())) {
                        int maxDistance = config.depth.sample(random) + (config.extraBottomBlockChance > 0.0F && random.nextFloat() < config.extraBottomBlockChance ? 1 : 0);
                        BlockPos placedPos = surfacePos.immutable();
                        boolean success = this.placeGround(level, config, state, random, surfacePos, maxDistance);

                        if (success) {
                            placedPositions.add(placedPos);
                        }
                    }
                }
            }
        }

        return placedPositions;
    }

    protected void distributeVegetation(FeaturePlaceContext<VegetationPatchConfiguration> context, WorldGenLevel level, VegetationPatchConfiguration config, RandomSource random, Set<BlockPos> possiblePositions) {
        for(BlockPos pos : possiblePositions) {
            if (config.vegetationChance > 0.0F && random.nextFloat() < config.vegetationChance) {
                this.placeVegetation(level, config, context.chunkGenerator(), random, pos);
            }
        }
    }

    protected void placeVegetation(WorldGenLevel level, VegetationPatchConfiguration config, ChunkGenerator chunkGenerator, RandomSource random, BlockPos pos) {
        config.vegetationFeature.value().place(level, chunkGenerator, random, pos.relative(config.surface.getDirection().getOpposite()));
    }

    protected boolean placeGround(WorldGenLevel level, VegetationPatchConfiguration config, Predicate<BlockState> replaceableBlocks, RandomSource random, BlockPos.MutableBlockPos mutablePos, int maxDistance) {
        for(int i = 0; i < maxDistance; ++i) {
            BlockState groundState = config.groundState.getState(random, mutablePos);
            BlockState currentState = level.getBlockState(mutablePos);
            if (!groundState.is(currentState.getBlock())) {
                if (!replaceableBlocks.test(currentState)) {
                    return i != 0;
                }

                if (random.nextBoolean()) {
                    level.setBlock(mutablePos, groundState, 2);
                    mutablePos.move(config.surface.getDirection());
                }
            }
        }

        return true;
    }
}

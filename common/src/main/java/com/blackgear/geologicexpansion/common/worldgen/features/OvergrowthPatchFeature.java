package com.blackgear.geologicexpansion.common.worldgen.features;

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

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

public class OvergrowthPatchFeature extends Feature<OvergrowthPatchConfiguration> {
    public OvergrowthPatchFeature(Codec<OvergrowthPatchConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<OvergrowthPatchConfiguration> context) {
        WorldGenLevel level = context.level();
        OvergrowthPatchConfiguration config = context.config();
        RandomSource random = context.random();
        BlockPos pos = context.origin();

        Predicate<BlockState> replaceable = state -> !config.shouldReplace() || state.is(config.replaceable());

        int xRadius = config.xzRadius().sample(random) + 1;
        int zRadius = config.xzRadius().sample(random) + 1;

        Set<BlockPos> placedPositions = this.placeGroundPatch(level, config, random, pos, replaceable, xRadius, zRadius);
        this.distributeVegetation(context, level, config, random, placedPositions, xRadius, zRadius);
        return !placedPositions.isEmpty();
    }

    private Set<BlockPos> placeGroundPatch(WorldGenLevel level, OvergrowthPatchConfiguration config, RandomSource random, BlockPos pos, Predicate<BlockState> replaceable, int xRadius, int zRadius) {
        BlockPos.MutableBlockPos currentPos = pos.mutable();
        BlockPos.MutableBlockPos surfacePos = currentPos.mutable();

        Direction surfaceDirection = config.surface().getDirection();
        Direction oppositeSurfaceDirection = surfaceDirection.getOpposite();

        Set<BlockPos> placedPositions = new HashSet<>();

        for (int xOffset = -xRadius; xOffset <= xRadius; xOffset++) {
            boolean isXEdge = xOffset == -xRadius || xOffset == xRadius;

            for (int zOffset = -zRadius; zOffset <= zRadius; zOffset++) {
                boolean isZEdge = zOffset == -zRadius || zOffset == zRadius;
                boolean isEdge = isXEdge || isZEdge;
                boolean isCorner = isXEdge && isZEdge;
                boolean isMiddle = isEdge && !isCorner;

                if (!isCorner && (!isMiddle || config.extraEdgeColumnChance() != 0.0F && random.nextFloat() <= config.extraEdgeColumnChance())) {
                    currentPos.setWithOffset(pos, xOffset, 0, zOffset);

                    for (int yOffset = 0; level.isStateAtPosition(currentPos, BlockBehaviour.BlockStateBase::isAir) && yOffset < config.verticalRange(); yOffset++) {
                        currentPos.move(surfaceDirection);
                    }

                    for (int yOffset = 0; level.isStateAtPosition(currentPos, state -> !state.isAir()) && yOffset < config.verticalRange(); yOffset++) {
                        currentPos.move(oppositeSurfaceDirection);
                    }

                    surfacePos.setWithOffset(currentPos, surfaceDirection);
                    BlockState surfaceBlockState = level.getBlockState(surfacePos);

                    if (level.isEmptyBlock(currentPos) && surfaceBlockState.isFaceSturdy(level, surfacePos, config.surface().getDirection().getOpposite())) {
                        int depth = config.depth().sample(random) + (config.extraBottomBlockChance() > 0.0F && random.nextFloat() < config.extraBottomBlockChance() ? 1 : 0);
                        BlockPos placedPos = surfacePos.immutable();

                        boolean success = this.placeGround(level, config, replaceable, surfacePos, depth);

                        if (success) {
                            placedPositions.add(placedPos);
                        }
                    }
                }
            }
        }

        return placedPositions;
    }

    private void distributeVegetation(FeaturePlaceContext<OvergrowthPatchConfiguration> context, WorldGenLevel level, OvergrowthPatchConfiguration config, RandomSource random, Set<BlockPos> placedPositions, int xRadius, int zRadius) {
        for (BlockPos position : placedPositions) {
            if (config.vegetationChance() > 0.0F && random.nextFloat() < config.vegetationChance()) {
                this.placeVegetation(level, config, context.chunkGenerator(), random, position);
            }
        }
    }

    private void placeVegetation(WorldGenLevel level, OvergrowthPatchConfiguration config, ChunkGenerator chunkGenerator, RandomSource random, BlockPos position) {
        config.vegetationFeature().value().place(level, chunkGenerator, random, position.relative(config.surface().getDirection().getOpposite()));
    }

    private boolean placeGround(WorldGenLevel level, OvergrowthPatchConfiguration config, Predicate<BlockState> replaceable, BlockPos.MutableBlockPos surfacePos, int maxDistance) {
        for (int i = 0; i < maxDistance; i++) {
            BlockState existingState = level.getBlockState(surfacePos);

            if (!replaceable.test(existingState)) {
                return i != 0;
            }

            surfacePos.move(config.surface().getDirection());
        }

        return true;
    }
}
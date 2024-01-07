package com.blackgear.geologicexpansion.common.worldgen.features;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

public class MultiVegetationPatchFeature extends Feature<MultiVegetationPatchConfiguration> {
    public MultiVegetationPatchFeature(Codec<MultiVegetationPatchConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<MultiVegetationPatchConfiguration> context) {
        WorldGenLevel level = context.level();
        MultiVegetationPatchConfiguration config = context.config();
        RandomSource random = context.random();
        BlockPos origin = context.origin();
        Predicate<BlockState> replaceableBlocks = state -> state.is(config.replaceable);
        int xRadius = config.xzRadius.sample(random) + 1;
        int zRadius = config.xzRadius.sample(random) + 1;
        Set<BlockPos> possiblePositions = this.placeGroundPatch(level, config, random, origin, replaceableBlocks, xRadius, zRadius);
        this.distributeVegetation(context, level, config, random, possiblePositions, xRadius, zRadius);
        return !possiblePositions.isEmpty();
    }

    protected Set<BlockPos> placeGroundPatch(WorldGenLevel level, MultiVegetationPatchConfiguration config, RandomSource random, BlockPos origin, Predicate<BlockState> replaceableBlocks, int xRadius, int zRadius) {
        BlockPos.MutableBlockPos originPos = origin.mutable();
        BlockPos.MutableBlockPos mutablePos = originPos.mutable();
        Direction surfaceDirection = config.surface.getDirection();
        Direction oppositeDirection = surfaceDirection.getOpposite();
        Set<BlockPos> possiblePositions = new HashSet<>();

        for(int x = -xRadius; x <= xRadius; ++x) {
            boolean isXAtEdge = x == -xRadius || x == xRadius;

            for(int z = -zRadius; z <= zRadius; ++z) {
                boolean isZAtEdge = z == -zRadius || z == zRadius;
                boolean isAtEdge = isXAtEdge || isZAtEdge;
                boolean isAtCorner = isXAtEdge && isZAtEdge;
                boolean isEdgeButNoCorner = isAtEdge && !isAtCorner;
                if (!isAtCorner && (!isEdgeButNoCorner || config.extraEdgeColumnChance != 0.0F && !(random.nextFloat() > config.extraEdgeColumnChance))) {
                    originPos.setWithOffset(origin, x, 0, z);

                    for(int i = 0; level.isStateAtPosition(originPos, BlockBehaviour.BlockStateBase::isAir) && i < config.verticalRange; ++i) {
                        originPos.move(surfaceDirection);
                    }

                    for(int i = 0; level.isStateAtPosition(originPos, state -> !state.isAir()) && i < config.verticalRange; ++i) {
                        originPos.move(oppositeDirection);
                    }

                    mutablePos.setWithOffset(originPos, config.surface.getDirection());
                    BlockState state = level.getBlockState(mutablePos);
                    if (level.isEmptyBlock(originPos) && state.isFaceSturdy(level, mutablePos, config.surface.getDirection().getOpposite())) {
                        int depth = config.depth.sample(random) + (config.extraBottomBlockChance > 0.0F && random.nextFloat() < config.extraBottomBlockChance ? 1 : 0);
                        BlockPos pos = mutablePos.immutable();
                        boolean isGroundPlaced = this.placeGround(level, config, replaceableBlocks, random, mutablePos, depth);
                        if (isGroundPlaced) {
                            possiblePositions.add(pos);
                        }
                    }
                }
            }
        }

        return possiblePositions;
    }

    protected void distributeVegetation(FeaturePlaceContext<MultiVegetationPatchConfiguration> context, WorldGenLevel level, MultiVegetationPatchConfiguration config, RandomSource random, Set<BlockPos> possiblePositions, int xRadius, int zRadius) {
        for(BlockPos pos : possiblePositions) {
            if (config.vegetationChance > 0.0F && random.nextFloat() < config.vegetationChance) {
                this.placeVegetation(level, config, context.chunkGenerator(), random, pos);
            }
        }
    }

    protected boolean placeVegetation(WorldGenLevel level, MultiVegetationPatchConfiguration config, ChunkGenerator chunkGenerator, RandomSource random, BlockPos pos) {
        boolean placed = false;

        for (Holder<PlacedFeature> feature : config.vegetationFeatures) {
            if (feature.value().place(level, chunkGenerator, random, pos.relative(config.surface.getDirection().getOpposite()))) {
                placed = true;
            }
        }

        return placed;
    }

    protected boolean placeGround(WorldGenLevel level, MultiVegetationPatchConfiguration config, Predicate<BlockState> replaceableblocks, RandomSource random, BlockPos.MutableBlockPos mutable, int maxDistance) {
        for(int i = 0; i < maxDistance; ++i) {
            BlockState groundState = config.groundState.getState(random, mutable);
            BlockState currentState = level.getBlockState(mutable);
            if (!groundState.is(currentState.getBlock())) {
                if (!replaceableblocks.test(currentState)) {
                    return i != 0;
                }

                level.setBlock(mutable, groundState, 2);
                mutable.move(config.surface.getDirection());
            }
        }

        return !config.onlyOnAirExposure || isAdjacentToAir(level::getBlockState, mutable);
    }
}
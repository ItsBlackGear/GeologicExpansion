package com.blackgear.geologicexpansion.common.worldgen.features;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class FallenTreeFeature extends Feature<FallenTreeConfiguration> {
    public FallenTreeFeature(Codec<FallenTreeConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<FallenTreeConfiguration> context) {
        return generate(context.level(), context.random(), context.origin(), context.config());
    }

    private boolean generate(WorldGenLevel level, RandomSource random, BlockPos origin, FallenTreeConfiguration config) {
        // Sample the trunk length and distance from stump
        int size = config.trunkLength().sample(random);
        int distance = config.distanceFromStump().sample(random);

        // Get the top position of the stump.
        BlockPos stumpPosition = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, origin);

        // Set the block at the top of the stump.
        level.setBlock(stumpPosition, config.trunkState().getState(random, origin).setValue(RotatedPillarBlock.AXIS,Direction.UP.getAxis()),3);

        // Generate the fallen trunk.
        Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(random);
        return generateFallenTrunk(level, direction, size, distance + 1, stumpPosition,4, random, config);
    }

    private boolean generateFallenTrunk(WorldGenLevel level, Direction direction, int size, int distance, BlockPos pos, int tries, RandomSource random, FallenTreeConfiguration config) {
        // If we have no tries left, return false.
        if (tries <= 0) {
            return false;
        }

        // Get the top position of the fallen trunk.
        BlockPos start = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, pos.relative(direction, distance));
        int maxY = start.getY();
        // Get the highest position of the fallen trunk.
        for(int i = 0; i < size; i++){
            BlockPos temp = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, start.relative(direction, i));
            if (temp.getY() > maxY) {
                maxY = temp.getY();
            }
        }

        // Generate the trunk.
        for(int i = 0; i < size; i++){
            BlockPos.MutableBlockPos temp = start.relative(direction, i).mutable();
            temp.setY(maxY);
            // If the trunk cannot be placed, try generating it in a different direction.
            if(((maxY - start.getY()) > 3 || (pos.getY() - maxY) > 3 || !level.getBlockState(temp).getMaterial().isReplaceable() || !level.getBlockState(temp.below()).getMaterial().isSolidBlocking() || level.getBlockState(temp.below()).getMaterial().isLiquid() || level.getBlockState(temp.below()).is(BlockTags.LOGS) || level.getBlockState(temp.below()).is(Blocks.RED_MUSHROOM_BLOCK) || level.getBlockState(temp.below()).is(Blocks.BROWN_MUSHROOM_BLOCK)) && i < 2) {
                return generateFallenTrunk(level, direction.getClockWise(Direction.Axis.Y), size, distance, pos, tries - 1, random, config);
            }
        }

        // Place the trunk blocks.
        for(int i = 0; i < size; i++){
            BlockPos.MutableBlockPos temp = start.relative(direction, i).mutable();
            temp.setY(maxY);
            // If the block can be replaced, place the trunk block.
            if (level.getBlockState(temp).getMaterial().isReplaceable()) {
                level.setBlock(temp, config.trunkState().getState(random, pos).setValue(RotatedPillarBlock.AXIS,direction.getAxis()),3);
                // Replace the block above is possible.
                if (level.getBlockState(temp.above()).getMaterial().isReplaceable()) {
                    level.setBlock(temp.above(), Blocks.AIR.defaultBlockState(),3);
                }

                // Set vines on the trunk.
                setVines(level, temp, config);
            } else {
                break;
            }
        }

        return true;
    }

    private void setVines(WorldGenLevel level, BlockPos pos, FallenTreeConfiguration config) {
        if (level.getBlockState(pos.above()).getMaterial().isReplaceable() && level.getRandom().nextFloat() <= config.vegetationChance()) {
            level.setBlock(pos.above(), config.vegetationState().getState(level.getRandom(), pos), 2);
        }
    }
}
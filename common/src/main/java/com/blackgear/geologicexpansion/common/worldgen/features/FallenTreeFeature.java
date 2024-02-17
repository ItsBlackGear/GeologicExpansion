package com.blackgear.geologicexpansion.common.worldgen.features;

import com.blackgear.geologicexpansion.core.data.GEBlockTags;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        BlockPos origin = context.origin();
        FallenTreeConfiguration config = context.config();

        // Sample the trunk length and distance from stump
        int size = config.trunkLength().sample(random);
        int distance = config.distanceFromStump().sample(random);

        // Get the top position of the stump.
        BlockPos stumpPosition = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, origin);

        // Generate the fallen trunk.
        Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(random);
        boolean success = generateFallenTrunk(level, direction, size, distance + 1, stumpPosition, 4, random, config);

        if (success) {
            // Set the block at the top of the stump.
            level.setBlock(stumpPosition, config.trunkState().getState(random, origin).setValue(RotatedPillarBlock.AXIS,Direction.UP.getAxis()),3);
            return true;
        }

        return false;
    }

    private boolean generateFallenTrunk(WorldGenLevel level, Direction direction, int size, int distance, BlockPos origin, int tries, RandomSource random, FallenTreeConfiguration config) {
        // If we have no tries left, return false.
        if (tries <= 0) {
            return false;
        }

        // Get the top position of the fallen trunk.
        BlockPos pos = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, origin.relative(direction, distance));
        int maxY = pos.getY();
        // Get the highest position of the fallen trunk.
        for(int i = 0; i < size; i++){
            BlockPos temp = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, pos.relative(direction, i));
            if (temp.getY() > maxY) {
                maxY = temp.getY();
            }
        }

        for(int i = 0; i < size; i++) {
            BlockPos.MutableBlockPos mutable = pos.relative(direction, i).mutable();
            mutable.setY(maxY);
            // If the trunk cannot be placed, try generating it in a different direction.
            if(shouldRotateTrunk(level, origin, pos, maxY, mutable, i)) {
                return generateFallenTrunk(level, direction.getClockWise(Direction.Axis.Y), size, distance, origin, tries - 1, random, config);
            }
        }

        // Place the trunk blocks.
        for(int i = 0; i < size; i++){
            BlockPos.MutableBlockPos mutable = pos.relative(direction, i).mutable();
            mutable.setY(maxY);
            // If the block can be replaced, place the trunk block.
            if (level.getBlockState(mutable).getMaterial().isReplaceable()) {
                level.setBlock(mutable, config.trunkState().getState(random, origin).setValue(RotatedPillarBlock.AXIS,direction.getAxis()),3);
                // Replace the block above is possible.
                if (level.getBlockState(mutable.above()).getMaterial().isReplaceable()) {
                    level.setBlock(mutable.above(), Blocks.AIR.defaultBlockState(),3);
                }

                // Set vines on the trunk.
                placeVegetation(level, mutable, config);
            } else {
                break;
            }
        }

        return true;
    }

    private static boolean shouldRotateTrunk(WorldGenLevel level, BlockPos origin, BlockPos pos, int maxY, BlockPos.MutableBlockPos mutable, int i) {
        boolean bl = (maxY - pos.getY()) > 3 || (origin.getY() - maxY) > 3;
        boolean bl2 = !level.getBlockState(mutable).getMaterial().isReplaceable();
        boolean bl3 = !level.getBlockState(mutable.below()).is(GEBlockTags.CAN_FALLEN_TREES_GENERATE_ON);
        boolean bl4 = i < 2;

        return (bl || bl2 || bl3) && bl4;
    }


    private void placeVegetation(WorldGenLevel level, BlockPos pos, FallenTreeConfiguration config) {
        if (level.getBlockState(pos.above()).getMaterial().isReplaceable() && level.getRandom().nextFloat() <= config.vegetationChance()) {
            level.setBlock(pos.above(), config.vegetationState().getState(level.getRandom(), pos), 2);
        }
    }
}
package com.blackgear.geologicexpansion.common.worldgen.features;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.BulkSectionAccess;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.BitSet;
import java.util.function.Function;

public class ExposedOreFeature extends Feature<ExposedOreConfiguration> {
    public ExposedOreFeature(Codec<ExposedOreConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<ExposedOreConfiguration> context) {
        RandomSource random = context.random();
        BlockPos origin = context.origin();
        WorldGenLevel level = context.level();
        ExposedOreConfiguration config = context.config();

        // Calculate the angle for the ore vein.
        float angle = random.nextFloat() * (float) Math.PI;
        // Calculate the radius of the ore vein.
        float radius = (float)config.size / 8.0F;
        // Calculate the size of the ore vein.
        int size = Mth.ceil(((float)config.size / 16.0F * 2.0F + 1.0F) / 2.0F);
        // Calculate the minimum and maximum X coordinates for the ore vein.
        double minX = (double)origin.getX() + Math.sin(angle) * (double)radius;
        double maxX = (double)origin.getX() - Math.sin(angle) * (double)radius;
        // Calculate the minimum and maximum Z coordinates for the ore vein.
        double minZ = (double)origin.getZ() + Math.cos(angle) * (double)radius;
        double maxZ = (double)origin.getZ() - Math.cos(angle) * (double)radius;
        // Calculate the minimum and maximum Y coordinates for the ore vein.
        double minY = origin.getY() + random.nextInt(3) - 2;
        double maxY = origin.getY() + random.nextInt(3) - 2;
        // Calculate the bounding box for the ore vein.
        int x = origin.getX() - Mth.ceil(radius) - size;
        int y = origin.getY() - 2 - size;
        int z = origin.getZ() - Mth.ceil(radius) - size;
        int width = 2 * (Mth.ceil(radius) + size);
        int height = 2 * (2 + size);

        // Iterate over the bounding box and attempt to place the ore.
        for(int localX = x; localX <= x + width; ++localX) {
            for(int localZ = z; localZ <= z + width; ++localZ) {
                // Check if the current position is below the ocean floor
                if (y <= level.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, localX, localZ)) {
                    // Attempt to place the ore and return the result.
                    return this.doPlace(level, random, context, config, minX, maxX, minZ, maxZ, minY, maxY, x, y, z, width, height);
                }
            }
        }

        // If the ore could not be placed, return false.
        return false;
    }

    protected boolean doPlace(WorldGenLevel level, RandomSource random, FeaturePlaceContext<ExposedOreConfiguration> context, ExposedOreConfiguration config, double minX, double maxX, double minZ, double maxZ, double minY, double maxY, int x, int y, int z, int width, int height) {
        // Initialize the counter for the number of ore blocks placed.
        int blockCount = 0;
        // Create a bit set for tracking placed ore blocks.
        BitSet placedOreBlocks = new BitSet(width * height * width);
        // Create a mutable block position to be used in the placement process.
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        // Get the size of the ore vein from the configuration.
        int size = config.size;
        // Create an array to store the coordinates of the ore blocks in the vein.
        double[] positions = new double[size * 4];

        // Calculate the position and size of each ore in the vein.
        for (int i = 0; i < size; ++i) {
            // Calculate the fraction of the current ore in the vein.
            float fraction = (float)i / (float)size;
            // Calculate the X, Y, and Z coordinates for the current ore in the vein.
            double oreX = Mth.lerp(fraction, minX, maxX);
            double oreY = Mth.lerp(fraction, minY, maxY);
            double oreZ = Mth.lerp(fraction, minZ, maxZ);
            double randomFactor = random.nextDouble() * (double)size / 16.0;
            double sizeFactor = ((double)(Mth.sin((float) Math.PI * fraction) + 1.0F) * randomFactor + 1.0) / 2.0;
            // Store the calculated values in the array.
            positions[i * 4] = oreX;
            positions[i * 4 + 1] = oreY;
            positions[i * 4 + 2] = oreZ;
            positions[i * 4 + 3] = sizeFactor;
        }

        // Eliminate overlapping ores in the vein.
        for (int i = 0; i < size - 1; ++i) {
            if (positions[i * 4 + 3] > 0.0) {
                for (int j = i + 1; j < size; ++j) {
                    if (positions[j * 4 + 3] > 0.0) {
                        // Calculate the distance between the two ores.
                        double distanceX = positions[i * 4] - positions[j * 4];
                        double distanceY = positions[i * 4 + 1] - positions[j * 4 + 1];
                        double distanceZ = positions[i * 4 + 2] - positions[j * 4 + 2];
                        double difference = positions[i * 4 + 3] - positions[j * 4 + 3];
                        // If the ores overlap, eliminate the smaller one.
                        if (difference * difference > distanceX * distanceX + distanceY * distanceY + distanceZ * distanceZ) {
                            if (difference > 0.0) {
                                positions[j * 4 + 3] = -1.0;
                            } else {
                                positions[i * 4 + 3] = -1.0;
                            }
                        }
                    }
                }
            }
        }

        // Attempt to place the ores in the world.
        try (BulkSectionAccess sectionAccess = new BulkSectionAccess(level)) {
            for(int i = 0; i < size; ++i) {
                double sizeFactor = positions[i * 4 + 3];
                if (sizeFactor >= 0.0) {
                    // Calculate the position and size of the current ore.
                    double oreX = positions[i * 4];
                    double oreY = positions[i * 4 + 1];
                    double oreZ = positions[i * 4 + 2];
                    int minXBound = Math.max(Mth.floor(oreX - sizeFactor), x);
                    int minYBound = Math.max(Mth.floor(oreY - sizeFactor), y);
                    int minZBound = Math.max(Mth.floor(oreZ - sizeFactor), z);
                    int maxXBound = Math.max(Mth.floor(oreX + sizeFactor), minXBound);
                    int maxYBound = Math.max(Mth.floor(oreY + sizeFactor), minYBound);
                    int maxZBound = Math.max(Mth.floor(oreZ + sizeFactor), minZBound);

                    // Iterate over the bounding box of the current ore.
                    for(int currentX = minXBound; currentX <= maxXBound; ++currentX) {
                        double xFactor = ((double)currentX + 0.5 - oreX) / sizeFactor;
                        if (xFactor * xFactor < 1.0) {
                            for(int currentY = minYBound; currentY <= maxYBound; ++currentY) {
                                double yFactor = ((double)currentY + 0.5 - oreY) / sizeFactor;
                                if (xFactor * xFactor + yFactor * yFactor < 1.0) {
                                    for(int currentZ = minZBound; currentZ <= maxZBound; ++currentZ) {
                                        double zFactor = ((double)currentZ + 0.5 - oreZ) / sizeFactor;
                                        // If the current position is within the bounding box of the ore and is not outside the build height.
                                        if (xFactor * xFactor + yFactor * yFactor + zFactor * zFactor < 1.0 && !level.isOutsideBuildHeight(currentY)) {
                                            // Calculate the index of the current position in the bit set.
                                            int bitIndex = currentX - x + (currentY - y) * width + (currentZ - z) * width * height;
                                            // If the current position has not already been placed, attempt to place it.
                                            if (!placedOreBlocks.get(bitIndex)) {
                                                // Mark the current position as placed.
                                                placedOreBlocks.set(bitIndex);
                                                // Set the position of the mutable block position to the current position.
                                                mutable.set(currentX, currentY, currentZ);
                                                // Check if the current position can be written to.
                                                if (level.ensureCanWrite(mutable)) {
                                                    // Get the section containing the current position.
                                                    LevelChunkSection section = sectionAccess.getSection(mutable);
                                                    if (section != null) {
                                                        // Get the relative position of the current position within the section.
                                                        int relativeX = SectionPos.sectionRelative(currentX);
                                                        int relativeY = SectionPos.sectionRelative(currentY);
                                                        int relativeZ = SectionPos.sectionRelative(currentZ);
                                                        // Get the current block state.
                                                        BlockState state = section.getBlockState(relativeX, relativeY, relativeZ);

                                                        // Iterate over the target block states.
                                                        for(ExposedOreConfiguration.TargetBlockState targetStates : config.targetStates) {
                                                            // Check if the current block state matches the target block state.
                                                            if (canPlaceOre(state, sectionAccess::getBlockState, random, targetStates, mutable)) {
                                                                // Set the block state of the current position to the target block state.
                                                                section.setBlockState(relativeX, relativeY, relativeZ, targetStates.state.getState(random, mutable), false);
                                                                // Increment the counter for the number of ore blocks placed.
                                                                ++blockCount;
                                                                break;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Return true if at least a block was placed in the ore vein.
        return blockCount > 0;
    }

    public static boolean canPlaceOre(BlockState state, Function<BlockPos, BlockState> adjacentState, RandomSource random, ExposedOreConfiguration.TargetBlockState targetState, BlockPos.MutableBlockPos mutable) {
        // Check if the current block state matches the target block state.
        if (targetState.target.test(state, random)) {
            // Check if the current block state is adjacent to air.
            return isAdjacentToAir(adjacentState, mutable);
        }

        // If the current block state does not match the target block state, return false.
        return false;
    }
}
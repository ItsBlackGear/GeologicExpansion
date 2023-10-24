package com.blackgear.geologicexpansion.common.worldgen.placements;

import com.blackgear.geologicexpansion.common.registries.GEBlocks;
import com.blackgear.geologicexpansion.core.GeologicExpansion;
import com.blackgear.geologicexpansion.core.platform.common.WorldGenRegistry;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RandomOffsetPlacement;
import net.minecraft.world.level.material.Fluids;

public class CalderaPlacements {
    public static final WorldGenRegistry FEATURES = WorldGenRegistry.of(GeologicExpansion.MOD_ID);

    // ========== PRISMATIC LAKE =======================================================================================
    public static final Holder<PlacedFeature> PRISMATIC_LAKE = FEATURES.create(
            "prismatic_lake",
            CalderaFeatures.PRISMATIC_LAKE,
            CountPlacement.of(20),
            CountPlacement.of(30),
            InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
            CountPlacement.of(5),
            RandomOffsetPlacement.horizontal(UniformInt.of(-4, 4)),
            EnvironmentScanPlacement.scanningFor(
                    Direction.DOWN,
                    getPrismaticLakeBlockPredicates(),
                    6
            ),
            BiomeFilter.biome()
    );

    private static BlockPredicate getPrismaticLakeBlockPredicates() {
        Block[] borderReplaceables = {
                GEBlocks.ORANGE_PRISMATIC_STONE.get(),
                GEBlocks.BROWN_PRISMATIC_STONE.get(),
                GEBlocks.RED_PRISMATIC_STONE.get(),
                Blocks.AIR,
                Blocks.LAVA
        };

        Block[] lakeBlocks = {
                GEBlocks.YELLOW_PRISMATIC_STONE.get(),
                Blocks.MAGMA_BLOCK
        };

        Block[] floorReplaceables = {
                GEBlocks.YELLOW_PRISMATIC_STONE.get(),
                GEBlocks.ORANGE_PRISMATIC_STONE.get(),
                GEBlocks.PURPLE_PRISMATIC_STONE.get(),
                GEBlocks.BROWN_PRISMATIC_STONE.get(),
                GEBlocks.RED_PRISMATIC_STONE.get(),
                GEBlocks.PRISMATIC_STONE.get(),
                Blocks.LAVA,
                Blocks.CALCITE,
                Blocks.GRASS_BLOCK,
                Blocks.DIRT,
                Blocks.COARSE_DIRT,
                Blocks.WATER
        };

        BlockPredicate borderBlockPredicate = BlockPredicate.anyOf(
                Direction.Plane.HORIZONTAL.stream().map(direction -> {
                    return BlockPredicate.matchesBlocks(direction.getNormal(), borderReplaceables);
                }).toArray(BlockPredicate[]::new)
        );

        BlockPredicate floorBlockPredicate = BlockPredicate.anyOf(
                Direction.Plane.HORIZONTAL.stream().map(direction -> {
                    return BlockPredicate.matchesBlocks(direction.getNormal().above(), floorReplaceables);
                }).toArray(BlockPredicate[]::new)
        );

        return BlockPredicate.allOf(
                BlockPredicate.not(borderBlockPredicate), // no border blocks
                BlockPredicate.matchesBlocks(Vec3i.ZERO, lakeBlocks), // lake block
                BlockPredicate.not(floorBlockPredicate) // no floor blocks
        );
    }

    // ========= PRISMATIC BORDER COLORS ===============================================================================
    public static final Holder<PlacedFeature> PRISMATIC_BORDER_PURPLE = FEATURES.create(
            "prismatic_border_purple",
            CalderaFeatures.PRISMATIC_BORDER_PURPLE,
            CountPlacement.of(256),
            InSquarePlacement.spread(),
            HeightRangePlacement.uniform(
                    VerticalAnchor.absolute(61),
                    VerticalAnchor.absolute(63)
            ),
            BlockPredicateFilter.forPredicate(
                    BlockPredicate.matchesFluids(Fluids.WATER)
            ),
            BiomeFilter.biome()
    );

    public static final Holder<PlacedFeature> PRISMATIC_BORDER_BROWN = FEATURES.create(
            "prismatic_border_brown",
            CalderaFeatures.PRISMATIC_BORDER_BROWN,
            CountPlacement.of(228),
            InSquarePlacement.spread(),
            HeightRangePlacement.uniform(
                    VerticalAnchor.absolute(61),
                    VerticalAnchor.absolute(63)
            ),
            BlockPredicateFilter.forPredicate(
                    BlockPredicate.matchesFluids(Fluids.WATER)
            ),
            BiomeFilter.biome()
    );

    public static final Holder<PlacedFeature> PRISMATIC_BORDER_RED = FEATURES.create(
            "prismatic_border_red",
            CalderaFeatures.PRISMATIC_BORDER_RED,
            CountPlacement.of(228),
            InSquarePlacement.spread(),
            HeightRangePlacement.uniform(
                    VerticalAnchor.absolute(61),
                    VerticalAnchor.absolute(63)
            ),
            BlockPredicateFilter.forPredicate(
                    BlockPredicate.matchesFluids(Fluids.WATER)
            ),
            BiomeFilter.biome()
    );

    public static final Holder<PlacedFeature> PRISMATIC_BORDER_ORANGE = FEATURES.create(
            "prismatic_border_orange",
            CalderaFeatures.PRISMATIC_BORDER_ORANGE,
            CountPlacement.of(4),
            CountPlacement.of(82),
            InSquarePlacement.spread(),
            HeightRangePlacement.uniform(
                    VerticalAnchor.absolute(61),
                    VerticalAnchor.absolute(63)
            ),
            BlockPredicateFilter.forPredicate(
                    BlockPredicate.matchesFluids(Fluids.WATER)
            ),
            BiomeFilter.biome()
    );

    public static final Holder<PlacedFeature> PRISMATIC_BORDER_YELLOW = FEATURES.create(
            "prismatic_border_yellow",
            CalderaFeatures.PRISMATIC_BORDER_YELLOW,
            CountPlacement.of(4),
            CountPlacement.of(82),
            InSquarePlacement.spread(),
            HeightRangePlacement.uniform(
                    VerticalAnchor.absolute(62),
                    VerticalAnchor.absolute(62)
            ),
            BlockPredicateFilter.forPredicate(
                    BlockPredicate.matchesFluids(Fluids.WATER)
            ),
            BiomeFilter.biome()
    );
}
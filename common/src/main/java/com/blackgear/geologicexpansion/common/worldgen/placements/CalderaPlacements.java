package com.blackgear.geologicexpansion.common.worldgen.placements;

import com.blackgear.geologicexpansion.core.GeologicExpansion;
import com.blackgear.geologicexpansion.core.platform.common.WorldGenRegistry;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.WeightedListInt;
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

    public static final Holder<PlacedFeature> PRISMATIC_POND = FEATURES.create(
            "prismatic_pond",
            CalderaFeatures.PRISMATIC_POND,
            CountPlacement.of(125),
            InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
            EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
            BiomeFilter.biome()
    );

    // ========= GEYSER ================================================================================================
    public static final Holder<PlacedFeature> GEYSER_PATCH = FEATURES.create("geyser_patch",
            CalderaFeatures.GEYSER_PATCH,
            CountPlacement.of(10),
            InSquarePlacement.spread(),
            PlacementUtils.FULL_RANGE,
            EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 12),
            RandomOffsetPlacement.vertical(ConstantInt.of(1)),
            BiomeFilter.biome()
    );

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

    public static final Holder<PlacedFeature> MAGMA = FEATURES.create(
        "magma",
        CalderaFeatures.MAGMA,
        CountPlacement.of(
            new WeightedListInt(
                SimpleWeightedRandomList.<IntProvider>builder()
                    .add(ConstantInt.of(3), 100)
                    .add(ConstantInt.of(2), 100)
                    .build()
            )
        ),
        InSquarePlacement.spread(),
        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
        BiomeFilter.biome()
    );
}
package com.blackgear.geologicexpansion.common.worldgen.placements;

import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RandomOffsetPlacement;

public class TravertinePlacements {
    public static void bootstrap() {}

    public static final Holder<PlacedFeature> TRAVERTINE_POOL = PlacementUtils.register(
            "travertine_pool",
//            TravertineFeatures.TRAVERTINE_POOL,
            TravertineFeatures.TRAVERTINE_FLOOR,
            CountPlacement.of(48),
            InSquarePlacement.spread(),
            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
            EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
            RandomOffsetPlacement.vertical(ConstantInt.of(1)),
            BiomeFilter.biome()
    );

    public static final Holder<PlacedFeature> ORE_LIMESTONE = PlacementUtils.register(
            "ore_limestone",
            TravertineFeatures.ORE_LIMESTONE,
            CountPlacement.of(32),
            InSquarePlacement.spread(),
            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
            BiomeFilter.biome()
    );

    public static final Holder<PlacedFeature> DISK_LIMESTONE = PlacementUtils.register(
            "disk_limestone",
            TravertineFeatures.DISK_LIMESTONE,
            CountPlacement.of(10),
            InSquarePlacement.spread(),
            RandomOffsetPlacement.vertical(ConstantInt.of(-1)),
            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
            BiomeFilter.biome()
    );
}
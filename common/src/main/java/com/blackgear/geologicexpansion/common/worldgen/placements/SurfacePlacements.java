package com.blackgear.geologicexpansion.common.worldgen.placements;

import com.blackgear.geologicexpansion.core.GeologicExpansion;
import com.blackgear.geologicexpansion.core.platform.common.WorldGenRegistry;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RandomOffsetPlacement;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraft.world.level.material.Fluids;

public class SurfacePlacements {
    public static final WorldGenRegistry FEATURES = WorldGenRegistry.of(GeologicExpansion.MOD_ID);

    public static final Holder<PlacedFeature> DISK_PACKED_MUD = FEATURES.create("disk_packed_mud",
        SurfaceFeatures.DISK_PACKED_MUD,
        InSquarePlacement.spread(),
        PlacementUtils.HEIGHTMAP_TOP_SOLID,
        BlockPredicateFilter.forPredicate(BlockPredicate.matchesFluids(Fluids.WATER)),
        BiomeFilter.biome()
    );
    public static final Holder<PlacedFeature> DISK_MUD = FEATURES.create("disk_mud",
        SurfaceFeatures.DISK_MUD,
        InSquarePlacement.spread(),
        PlacementUtils.HEIGHTMAP_TOP_SOLID,
        BlockPredicateFilter.forPredicate(
            BlockPredicate.anyOf(
                BlockPredicate.matchesBlocks(Blocks.PACKED_MUD),
                BlockPredicate.matchesFluids(Fluids.WATER)
            )
        ),
        BiomeFilter.biome()
    );

    // ========== TREES ================================================================================================
    public static final Holder<PlacedFeature> TREES_MAPLE_FOREST = FEATURES.create("trees_maple_forest",
        SurfaceFeatures.TREES_MAPLE_FOREST,
        VegetationPlacements.treePlacement(PlacementUtils.countExtra(6, 0.1F, 1))
    );

    public static final Holder<PlacedFeature> RED_MAPLE_TREE = FEATURES.create("red_maple_tree",
        TreeVegetationFeatures.RED_MAPLE,
        VegetationPlacements.treePlacement(PlacementUtils.countExtra(1, 0.1F, 1), Blocks.OAK_SAPLING)
    );
    public static final Holder<PlacedFeature> BROWN_MAPLE_TREE = FEATURES.create("brown_maple_tree",
        TreeVegetationFeatures.BROWN_MAPLE,
        VegetationPlacements.treePlacement(PlacementUtils.countExtra(1, 0.1F, 1), Blocks.OAK_SAPLING)
    );

    // ========== MISCELLANEOUS ========================================================================================
    public static final Holder<PlacedFeature> ROCK_PATCH = FEATURES.create("rock_patch",
            SurfaceFeatures.ROCK_PATCH,
            RarityFilter.onAverageOnceEvery(4),
            CountPlacement.of(3),
            InSquarePlacement.spread(),
            PlacementUtils.FULL_RANGE,
            BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(BlockTags.BASE_STONE_OVERWORLD)),
            BiomeFilter.biome()
    );

    // ========== OVERGROWTH ===========================================================================================
    public static final Holder<PlacedFeature> OVERGROWTH_PATCH = FEATURES.create("overgrowth_patch",
            SurfaceFeatures.OVERGROWTH_PATCH,
            CountPlacement.of(75),
            InSquarePlacement.spread(),
            PlacementUtils.FULL_RANGE,
            EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
            RandomOffsetPlacement.vertical(ConstantInt.of(1)),
            BiomeFilter.biome()
    );
}
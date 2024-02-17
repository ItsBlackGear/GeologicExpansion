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

public class SurfacePlacements {
    public static final WorldGenRegistry FEATURES = WorldGenRegistry.of(GeologicExpansion.MOD_ID);

    // ========== BOULDERS =============================================================================================

    public static final Holder<PlacedFeature> BOULDER = FEATURES.create("boulder",
        SurfaceFeatures.BOULDER,
        RarityFilter.onAverageOnceEvery(2),
        InSquarePlacement.spread(),
        PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
        EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 12),
        RandomOffsetPlacement.vertical(ConstantInt.of(-1)),
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
}
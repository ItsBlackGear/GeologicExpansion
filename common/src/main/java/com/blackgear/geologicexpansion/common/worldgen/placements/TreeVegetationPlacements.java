package com.blackgear.geologicexpansion.common.worldgen.placements;

import com.blackgear.geologicexpansion.core.GeologicExpansion;
import com.blackgear.geologicexpansion.core.platform.common.WorldGenRegistry;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class TreeVegetationPlacements {
    public static final WorldGenRegistry FEATURES = WorldGenRegistry.of(GeologicExpansion.MOD_ID);

    // ========== RED MAPLE ============================================================================================
    public static final Holder<PlacedFeature> RED_MAPLE_CHECKED = FEATURES.create(
        "red_maple_checked",
        TreeVegetationFeatures.RED_MAPLE,
        PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING)
    );
    public static final Holder<PlacedFeature> RED_MAPLE_0002 = FEATURES.create(
        "red_maple_0002",
        TreeVegetationFeatures.RED_MAPLE_BEES_0002,
        PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING)
    );
    public static final Holder<PlacedFeature> RED_MAPLE_002 = FEATURES.create(
        "red_maple_002",
        TreeVegetationFeatures.RED_MAPLE_BEES_002,
        PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING)
    );
    public static final Holder<PlacedFeature> RED_MAPLE_BEES = FEATURES.create(
        "red_maple_bees",
        TreeVegetationFeatures.RED_MAPLE_BEES,
        PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING)
    );

    // ========== BROWN MAPLE ==========================================================================================
    public static final Holder<PlacedFeature> BROWN_MAPLE_CHECKED = FEATURES.create(
        "brown_maple_checked",
        TreeVegetationFeatures.BROWN_MAPLE,
        PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING)
    );
    public static final Holder<PlacedFeature> BROWN_MAPLE_0002 = FEATURES.create(
        "brown_maple_0002",
        TreeVegetationFeatures.BROWN_MAPLE_BEES_0002,
        PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING)
    );
    public static final Holder<PlacedFeature> BROWN_MAPLE_002 = FEATURES.create(
        "brown_maple_002",
        TreeVegetationFeatures.BROWN_MAPLE_BEES_002,
        PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING)
    );
    public static final Holder<PlacedFeature> BROWN_MAPLE_BEES = FEATURES.create(
        "brown_maple_bees",
        TreeVegetationFeatures.BROWN_MAPLE_BEES,
        PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING)
    );

    // ========== FALLEN TREES =========================================================================================
    public static final Holder<PlacedFeature> FALLEN_OAK_TREE = FEATURES.create("fallen_oak_tree",
        TreeVegetationFeatures.FALLEN_OAK_TREE,
        VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.1F, 1), Blocks.OAK_SAPLING)
    );
    public static final Holder<PlacedFeature> FALLEN_OAK_TREE_PLAINS = FEATURES.create("fallen_oak_tree_plains",
        TreeVegetationFeatures.FALLEN_OAK_TREE,
        VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.01F, 1), Blocks.OAK_SAPLING)
    );
    public static final Holder<PlacedFeature> FALLEN_BIRCH_TREE = FEATURES.create("fallen_birch_tree",
        TreeVegetationFeatures.FALLEN_BIRCH_TREE,
        VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.1F, 1), Blocks.BIRCH_SAPLING)
    );
    public static final Holder<PlacedFeature> FALLEN_SPRUCE_TREE = FEATURES.create("fallen_spruce_tree",
        TreeVegetationFeatures.FALLEN_SPRUCE_TREE,
        VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.1F, 1), Blocks.SPRUCE_SAPLING)
    );
    public static final Holder<PlacedFeature> FALLEN_ACACIA_TREE = FEATURES.create("fallen_acacia_tree",
        TreeVegetationFeatures.FALLEN_ACACIA_TREE,
        VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.1F, 1), Blocks.ACACIA_SAPLING)
    );
    public static final Holder<PlacedFeature> FALLEN_MAPLE_TREE = FEATURES.create("fallen_maple_tree",
        TreeVegetationFeatures.FALLEN_MAPLE_TREE,
        VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.5F, 1), Blocks.ACACIA_SAPLING)
    );

    // ========== BUSHES ===============================================================================================
    public static final Holder<PlacedFeature> SPRUCE_BUSH = FEATURES.create("spruce_bush",
        TreeVegetationFeatures.SPRUCE_BUSH,
        VegetationPlacements.treePlacement(PlacementUtils.countExtra(1, 0.5F, 1), Blocks.SPRUCE_SAPLING)
    );
    public static final Holder<PlacedFeature> ACACIA_BUSH = FEATURES.create("acacia_bush",
        TreeVegetationFeatures.ACACIA_BUSH,
        VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.1F, 1), Blocks.ACACIA_SAPLING)
    );
    public static final Holder<PlacedFeature> RED_MAPLE_BUSH = FEATURES.create("red_maple_bush",
        TreeVegetationFeatures.RED_MAPLE_BUSH,
        VegetationPlacements.treePlacement(PlacementUtils.countExtra(1, 0.1F, 1), Blocks.OAK_SAPLING)
    );
    public static final Holder<PlacedFeature> BROWN_MAPLE_BUSH = FEATURES.create("brown_maple_bush",
        TreeVegetationFeatures.BROWN_MAPLE_BUSH,
        VegetationPlacements.treePlacement(PlacementUtils.countExtra(1, 0.1F, 1), Blocks.OAK_SAPLING)
    );
}
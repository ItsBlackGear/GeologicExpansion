package com.blackgear.geologicexpansion.common.worldgen.placements;

import com.blackgear.geologicexpansion.common.registries.GEBlocks;
import com.blackgear.geologicexpansion.common.registries.worldgen.GEFeatures;
import com.blackgear.geologicexpansion.common.worldgen.features.OvergrowthPatchConfiguration;
import com.blackgear.geologicexpansion.common.worldgen.features.StructurePieceConfiguration;
import com.blackgear.geologicexpansion.core.GeologicExpansion;
import com.blackgear.geologicexpansion.core.data.GEBlockTags;
import com.blackgear.geologicexpansion.core.platform.common.WorldGenRegistry;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.CaveSurface;

import java.util.List;
import java.util.OptionalInt;

public class SurfaceFeatures {
    public static final WorldGenRegistry FEATURES = WorldGenRegistry.of(GeologicExpansion.MOD_ID);

    private static final List<ResourceLocation> BOULDER_STRUCTURES = List.of(
        new ResourceLocation(GeologicExpansion.MOD_ID, "boulder/boulder_1"),
        new ResourceLocation(GeologicExpansion.MOD_ID, "boulder/boulder_2"),
        new ResourceLocation(GeologicExpansion.MOD_ID, "boulder/boulder_3"),
        new ResourceLocation(GeologicExpansion.MOD_ID, "boulder/boulder_4"),
        new ResourceLocation(GeologicExpansion.MOD_ID, "boulder/boulder_5"),
        new ResourceLocation(GeologicExpansion.MOD_ID, "boulder/boulder_6"),
        new ResourceLocation(GeologicExpansion.MOD_ID, "boulder/boulder_7"),
        new ResourceLocation(GeologicExpansion.MOD_ID, "boulder/boulder_1"),
        new ResourceLocation(GeologicExpansion.MOD_ID, "boulder/boulder_2"),
        new ResourceLocation(GeologicExpansion.MOD_ID, "boulder/boulder_3"),
        new ResourceLocation(GeologicExpansion.MOD_ID, "boulder/boulder_4"),
        new ResourceLocation(GeologicExpansion.MOD_ID, "boulder/boulder_5"),
        new ResourceLocation(GeologicExpansion.MOD_ID, "boulder/boulder_6"),
        new ResourceLocation(GeologicExpansion.MOD_ID, "boulder/boulder_7")
    );

    public static final Holder<ConfiguredFeature<StructurePieceConfiguration, ?>> BOULDER = FEATURES.create("boulder",
        GEFeatures.BOULDER.get(),
        new StructurePieceConfiguration.Builder(BOULDER_STRUCTURES).build()
    );

    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> TREES_MAPLE_FOREST = FEATURES.create("trees_maple_forest",
        Feature.RANDOM_SELECTOR,
        new RandomFeatureConfiguration(
            List.of(
                new WeightedPlacedFeature(TreeVegetationPlacements.RED_MAPLE_CHECKED, 0.4F),
                new WeightedPlacedFeature(TreeVegetationPlacements.BROWN_MAPLE_CHECKED, 0.4F),
                new WeightedPlacedFeature(net.minecraft.data.worldgen.placement.TreePlacements.BIRCH_CHECKED, 0.2F)
            ),
            net.minecraft.data.worldgen.placement.TreePlacements.OAK_CHECKED
        )
    );

    // ========== TREES ================================================================================================

    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> MAPLE_TREE = FEATURES.create("maple_tree",
        Feature.TREE,
        new TreeConfiguration.TreeConfigurationBuilder(
            BlockStateProvider.simple(Blocks.ACACIA_LOG),
            new StraightTrunkPlacer(3, 11, 0),
            BlockStateProvider.simple(GEBlocks.RED_MAPLE_LEAVES.get()),
            new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4),
            new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4))
        ).build()
    );

    // ========== OVERGROWTH ===========================================================================================
    public static final Holder<ConfiguredFeature<SimpleBlockConfiguration, ?>> OVERGROWTH_VEGETATION = FEATURES.create("overgrowth_vegetation",
        Feature.SIMPLE_BLOCK,
        new SimpleBlockConfiguration(
            BlockStateProvider.simple(GEBlocks.OVERGROWTH.get())
        )
    );

    public static final Holder<ConfiguredFeature<OvergrowthPatchConfiguration, ?>> OVERGROWTH_PATCH_BONEMEAL = FEATURES.create("overgrowth_patch_bonemeal",
        GEFeatures.OVERGROWTH_PATCH.get(),
        new OvergrowthPatchConfiguration(
            GEBlockTags.OVERGROWTH_GROWABLE_BLOCKS,
            false,
            PlacementUtils.inlinePlaced(OVERGROWTH_VEGETATION),
            CaveSurface.FLOOR,
            ConstantInt.of(1),
            0.0F,
            5,
            0.45F,
            ConstantInt.of(1),
            0.75F
        )
    );
}
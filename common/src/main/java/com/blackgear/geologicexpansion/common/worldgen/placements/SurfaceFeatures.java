package com.blackgear.geologicexpansion.common.worldgen.placements;

import com.blackgear.geologicexpansion.common.block.RockBlock;
import com.blackgear.geologicexpansion.common.registries.GEBlocks;
import com.blackgear.geologicexpansion.common.registries.worldgen.GEFeatures;
import com.blackgear.geologicexpansion.common.worldgen.features.LargeLakeFeatureConfiguration;
import com.blackgear.geologicexpansion.common.worldgen.features.OvergrowthPatchConfiguration;
import com.blackgear.geologicexpansion.core.GeologicExpansion;
import com.blackgear.geologicexpansion.core.data.GEBlockTags;
import com.blackgear.geologicexpansion.core.platform.common.WorldGenRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.DiskConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RuleBasedBlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.CaveSurface;

import java.util.List;
import java.util.OptionalInt;

public class SurfaceFeatures {
    public static final WorldGenRegistry FEATURES = WorldGenRegistry.of(GeologicExpansion.MOD_ID);

    public static final Holder<ConfiguredFeature<DiskConfiguration, ?>> DISK_PACKED_MUD = FEATURES.create("disk_packed_mud",
        Feature.DISK,
        new DiskConfiguration(
            RuleBasedBlockStateProvider.simple(Blocks.PACKED_MUD),
            BlockPredicate.matchesBlocks(List.of(Blocks.DIRT, Blocks.GRASS_BLOCK)),
            UniformInt.of(2, 6),
            2
        )
    );
    public static final Holder<ConfiguredFeature<DiskConfiguration, ?>> DISK_MUD = FEATURES.create("disk_mud",
        Feature.DISK,
        new DiskConfiguration(
            RuleBasedBlockStateProvider.simple(Blocks.MUD),
            BlockPredicate.matchesBlocks(List.of(Blocks.PACKED_MUD, Blocks.DIRT, Blocks.GRASS_BLOCK)),
            UniformInt.of(2, 3),
            1
        )
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

    // ========== MISCELLANEOUS ========================================================================================

    public static final Holder<ConfiguredFeature<LargeLakeFeatureConfiguration, ?>> LARGE_LAKE = FEATURES.create("large_lake",
        GEFeatures.LARGE_LAKE.get(),
        new LargeLakeFeatureConfiguration(
            15,
            22,
            4,
            10,
            BlockStateProvider.simple(Blocks.SAND.defaultBlockState()),
            BlockStateProvider.simple(Blocks.SAND.defaultBlockState()),
            HolderSet.direct(PlacementUtils.inlinePlaced(Feature.NO_OP, FeatureConfiguration.NONE)),
            HolderSet.direct(PlacementUtils.inlinePlaced(Feature.NO_OP, FeatureConfiguration.NONE)),
            List.of()
        )
    );

    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> ROCK_PATCH = FEATURES.create("rock_patch",
            Feature.RANDOM_PATCH,
            FeatureUtils.simplePatchConfiguration(
                    Feature.SIMPLE_BLOCK,
                    new SimpleBlockConfiguration(
                            new WeightedStateProvider(
                                    SimpleWeightedRandomList.<BlockState>builder()
                                            .add(GEBlocks.ROCK.get().defaultBlockState().setValue(RockBlock.ROCKS, 1), 1)
                                            .add(GEBlocks.ROCK.get().defaultBlockState().setValue(RockBlock.ROCKS, 2), 1)
                                            .add(GEBlocks.ROCK.get().defaultBlockState().setValue(RockBlock.ROCKS, 3), 1)
                                            .add(GEBlocks.ROCK.get().defaultBlockState().setValue(RockBlock.ROCKS, 4), 1)
                                            .build()
                            )
                    )
            )
    );

    // ========== OVERGROWTH ===========================================================================================
    public static final Holder<ConfiguredFeature<SimpleBlockConfiguration, ?>> OVERGROWTH_VEGETATION = FEATURES.create("overgrowth_vegetation",
            Feature.SIMPLE_BLOCK,
            new SimpleBlockConfiguration(
                    BlockStateProvider.simple(GEBlocks.OVERGROWTH.get())
            )
    );

    public static final Holder<ConfiguredFeature<OvergrowthPatchConfiguration, ?>> OVERGROWTH_PATCH = FEATURES.create("overgrowth_patch",
            GEFeatures.OVERGROWTH_PATCH.get(),
            new OvergrowthPatchConfiguration(
                    GEBlockTags.OVERGROWTH_GROWABLE_BLOCKS,
                    true,
                    PlacementUtils.inlinePlaced(OVERGROWTH_VEGETATION),
                    CaveSurface.FLOOR,
                    ConstantInt.of(1),
                    0.0F,
                    5,
                    0.6F,
                    UniformInt.of(1, 2),
                    0.75F
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
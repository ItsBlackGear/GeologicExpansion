package com.blackgear.geologicexpansion.common.worldgen.placements;

import com.blackgear.geologicexpansion.common.registries.GEBlocks;
import com.blackgear.geologicexpansion.common.registries.worldgen.GEFeatures;
import com.blackgear.geologicexpansion.common.worldgen.features.FallenTreeConfiguration;
import com.blackgear.geologicexpansion.common.worldgen.features.tree.MapleFoliagePlacer;
import com.blackgear.geologicexpansion.common.worldgen.features.tree.MapleTrunkPlacer;
import com.blackgear.geologicexpansion.core.GeologicExpansion;
import com.blackgear.geologicexpansion.core.platform.common.WorldGenRegistry;
import net.minecraft.core.Holder;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.util.valueproviders.WeightedListInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BushFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.BeehiveDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;

import java.util.List;

public class TreeVegetationFeatures {
    public static final WorldGenRegistry FEATURES = WorldGenRegistry.of(GeologicExpansion.MOD_ID);

    // ========== TREE DECORATORS ======================================================================================
    private static final BeehiveDecorator BEEHIVE_0002 = new BeehiveDecorator(0.002F);
    private static final BeehiveDecorator BEEHIVE_002 = new BeehiveDecorator(0.02F);
    private static final BeehiveDecorator BEEHIVE = new BeehiveDecorator(1.0F);

    // ========== RED MAPLE ============================================================================================
    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> RED_MAPLE = FEATURES.create(
        "red_maple",
        Feature.TREE,
        createMaple(GEBlocks.RED_MAPLE_LEAVES.get()).build()
    );
    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> RED_MAPLE_BEES_0002 = FEATURES.create(
        "red_maple_bees_0002",
        Feature.TREE,
        createMaple(GEBlocks.RED_MAPLE_LEAVES.get()).decorators(List.of(BEEHIVE_0002)).build()
    );
    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> RED_MAPLE_BEES_002 = FEATURES.create(
        "red_maple_bees_002",
        Feature.TREE,
        createMaple(GEBlocks.RED_MAPLE_LEAVES.get()).decorators(List.of(BEEHIVE_002)).build()
    );
    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> RED_MAPLE_BEES = FEATURES.create(
        "red_maple_bees",
        Feature.TREE,
        createMaple(GEBlocks.RED_MAPLE_LEAVES.get()).decorators(List.of(BEEHIVE)).build()
    );

    // ========== BROWN MAPLE ==========================================================================================
    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> BROWN_MAPLE = FEATURES.create(
        "brown_maple",
        Feature.TREE, createMaple(GEBlocks.BROWN_MAPLE_LEAVES.get()).build()
    );
    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> BROWN_MAPLE_BEES_0002 = FEATURES.create(
        "brown_maple_bees_0002",
        Feature.TREE, createMaple(GEBlocks.BROWN_MAPLE_LEAVES.get()).decorators(List.of(BEEHIVE_0002)).build()
    );
    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> BROWN_MAPLE_BEES_002 = FEATURES.create(
        "brown_maple_bees_002",
        Feature.TREE, createMaple(GEBlocks.BROWN_MAPLE_LEAVES.get()).decorators(List.of(BEEHIVE_002)).build()
    );
    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> BROWN_MAPLE_BEES = FEATURES.create(
        "brown_maple_bees",
        Feature.TREE, createMaple(GEBlocks.BROWN_MAPLE_LEAVES.get()).decorators(List.of(BEEHIVE)).build()
    );

    // ========== FALLEN TREES =========================================================================================
    public static final Holder<ConfiguredFeature<FallenTreeConfiguration, ?>> FALLEN_OAK_TREE = FEATURES.create("fallen_oak_tree",
        GEFeatures.FALLEN_TREE.get(), createFallenTree(Blocks.OAK_LOG)
    );
    public static final Holder<ConfiguredFeature<FallenTreeConfiguration, ?>> FALLEN_BIRCH_TREE = FEATURES.create("fallen_birch_tree",
        GEFeatures.FALLEN_TREE.get(), createFallenTree(Blocks.BIRCH_LOG)
    );
    public static final Holder<ConfiguredFeature<FallenTreeConfiguration, ?>> FALLEN_SPRUCE_TREE = FEATURES.create("fallen_spruce_tree",
        GEFeatures.FALLEN_TREE.get(), createFallenTree(Blocks.SPRUCE_LOG)
    );
    public static final Holder<ConfiguredFeature<FallenTreeConfiguration, ?>> FALLEN_ACACIA_TREE = FEATURES.create("fallen_acacia_tree",
        GEFeatures.FALLEN_TREE.get(), createFallenTree(Blocks.ACACIA_LOG)
    );
    public static final Holder<ConfiguredFeature<FallenTreeConfiguration, ?>> FALLEN_MAPLE_TREE = FEATURES.create("fallen_maple_tree",
        GEFeatures.FALLEN_TREE.get(), createFallenTree(GEBlocks.MAPLE_LOG.get())
    );

    // ========== BUSHES ===============================================================================================
    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> SPRUCE_BUSH = FEATURES.create("spruce_bush",
        Feature.TREE, createBush(Blocks.SPRUCE_LOG, Blocks.SPRUCE_LEAVES)
    );
    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> ACACIA_BUSH = FEATURES.create("acacia_bush",
        Feature.TREE, createBush(Blocks.ACACIA_LOG, Blocks.ACACIA_LEAVES)
    );
    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> RED_MAPLE_BUSH = FEATURES.create("red_maple_bush",
        Feature.TREE, createBush(GEBlocks.MAPLE_LOG.get(), GEBlocks.RED_MAPLE_LEAVES.get())
    );
    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> BROWN_MAPLE_BUSH = FEATURES.create("brown_maple_bush",
        Feature.TREE, createBush(GEBlocks.MAPLE_LOG.get(), GEBlocks.BROWN_MAPLE_LEAVES.get())
    );

    // ========== TREE BUILDERS ========================================================================================

    private static TreeConfiguration.TreeConfigurationBuilder createMaple(Block leaves) {
        return new TreeConfiguration.TreeConfigurationBuilder(
            BlockStateProvider.simple(GEBlocks.MAPLE_LOG.get()),
            new MapleTrunkPlacer(
                9,
                2,
                0,
                new WeightedListInt(
                    SimpleWeightedRandomList.<IntProvider>builder()
                        .add(ConstantInt.of(2), 1)
                        .add(ConstantInt.of(3), 1)
                        .add(ConstantInt.of(4), 1)
                        .add(ConstantInt.of(5), 1)
                        .build()
                ),
                UniformInt.of(1, 3),
                UniformInt.of(-5, -4),
                UniformInt.of(-4, -1)
            ),
            BlockStateProvider.simple(leaves),
            new MapleFoliagePlacer(
                ConstantInt.of(4),
                ConstantInt.of(0),
                ConstantInt.of(5),
                0.25F,
                0.5F,
                0.27F,
                0.53F
            ),
            new TwoLayersFeatureSize(1, 0, 2)
        ).ignoreVines();
    }

    private static FallenTreeConfiguration createFallenTree(Block log) {
        return new FallenTreeConfiguration(
            BlockStateProvider.simple(log),
            UniformInt.of(3, 5),
            ConstantInt.of(1),
            0.6F,
            new WeightedStateProvider(
                SimpleWeightedRandomList.<BlockState>builder()
                    .add(GEBlocks.OVERGROWTH.get().defaultBlockState(), 50)
                    .add(Blocks.RED_MUSHROOM.defaultBlockState(), 25)
                    .add(Blocks.BROWN_MUSHROOM.defaultBlockState(), 25)
                    .build()
            )
        );
    }

    private static TreeConfiguration createBush(Block log, Block leaves) {
        return new TreeConfiguration.TreeConfigurationBuilder(
            BlockStateProvider.simple(log),
            new StraightTrunkPlacer(1, 0, 0),
            BlockStateProvider.simple(leaves),
            new BushFoliagePlacer(
                ConstantInt.of(2),
                ConstantInt.of(0),
                1
            ),
            new TwoLayersFeatureSize(0, 0, 0)
        ).build();
    }
}
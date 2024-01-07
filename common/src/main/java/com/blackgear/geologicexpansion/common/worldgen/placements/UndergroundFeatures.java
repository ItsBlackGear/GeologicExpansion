package com.blackgear.geologicexpansion.common.worldgen.placements;

import com.blackgear.geologicexpansion.common.block.OvergrowthBlock;
import com.blackgear.geologicexpansion.common.registries.GEBlocks;
import com.blackgear.geologicexpansion.common.registries.worldgen.GEFeatures;
import com.blackgear.geologicexpansion.common.worldgen.features.MultiVegetationPatchConfiguration;
import com.blackgear.geologicexpansion.core.GeologicExpansion;
import com.blackgear.geologicexpansion.core.platform.common.WorldGenRegistry;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomBooleanFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.placement.CaveSurface;

public class UndergroundFeatures {
    public static final WorldGenRegistry FEATURES = WorldGenRegistry.of(GeologicExpansion.MOD_ID);

    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_LIMESTONE = FEATURES.create(
        "ore_limestone",
        Feature.ORE,
        new OreConfiguration(
            OreFeatures.NATURAL_STONE,
            GEBlocks.LIMESTONE.get().defaultBlockState(),
            64
        )
    );

    // ========== MOSSY PATCH ==========================================================================================
    public static final Holder<ConfiguredFeature<SimpleBlockConfiguration, ?>> MOSSY_PATCH_FLOOR_VEGETATION = FEATURES.create(
        "mossy_patch_floor_vegetation",
        Feature.SIMPLE_BLOCK,
        new SimpleBlockConfiguration(
            SimpleStateProvider.simple(GEBlocks.OVERGROWTH.get().defaultBlockState())
        )
    );

    public static final Holder<ConfiguredFeature<MultiVegetationPatchConfiguration, ?>> MOSSY_PATCH_FLOOR = FEATURES.create(
        "mossy_patch_floor",
        GEFeatures.MULTI_VEGETATION_PATCH.get(),
        new MultiVegetationPatchConfiguration(
            BlockTags.STONE_ORE_REPLACEABLES,
            new WeightedStateProvider(
                SimpleWeightedRandomList.<BlockState>builder()
                    .add(Blocks.MOSSY_COBBLESTONE.defaultBlockState(), 7)
                    .add(Blocks.MOSS_BLOCK.defaultBlockState(), 4)
                    .add(Blocks.STONE.defaultBlockState(), 50)
                    .build()
            ),
            PlacementUtils.inlinePlaced(MOSSY_PATCH_FLOOR_VEGETATION),
            CaveSurface.FLOOR,
            ConstantInt.of(8),
            0.8F,
            2,
            0.08F,
            UniformInt.of(3, 5),
            0.7F,
            true
        )
    );

    public static final Holder<ConfiguredFeature<RandomBooleanFeatureConfiguration, ?>> MOSSY_PATCH_CEILING_VEGETATION = FEATURES.create(
        "mossy_patch_ceiling_vegetation",
        Feature.RANDOM_BOOLEAN_SELECTOR,
        new RandomBooleanFeatureConfiguration(
            PlacementUtils.inlinePlaced(GEFeatures.EXTRA_VINES.get(), FeatureConfiguration.NONE),
            PlacementUtils.inlinePlaced(
                Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(
                    SimpleStateProvider.simple(GEBlocks.OVERGROWTH.get().defaultBlockState().setValue(OvergrowthBlock.HANGING, true))
                )
            )
        )
    );

    public static final Holder<ConfiguredFeature<MultiVegetationPatchConfiguration, ?>> MOSSY_PATCH_CEILING = FEATURES.create(
        "mossy_patch_ceiling",
        GEFeatures.MULTI_VEGETATION_PATCH.get(),
        new MultiVegetationPatchConfiguration(
            BlockTags.STONE_ORE_REPLACEABLES,
            new WeightedStateProvider(
                SimpleWeightedRandomList.<BlockState>builder()
                    .add(Blocks.MOSSY_COBBLESTONE.defaultBlockState(), 10)
                    .add(Blocks.MOSS_BLOCK.defaultBlockState(), 7)
                    .add(Blocks.STONE.defaultBlockState(), 50)
                    .build()
            ),
            PlacementUtils.inlinePlaced(MOSSY_PATCH_CEILING_VEGETATION),
            CaveSurface.CEILING,
            ConstantInt.of(8),
            0.0F,
            5,
            0.3F,
            UniformInt.of(3, 5),
            0.3F,
            true
        )
    );
}
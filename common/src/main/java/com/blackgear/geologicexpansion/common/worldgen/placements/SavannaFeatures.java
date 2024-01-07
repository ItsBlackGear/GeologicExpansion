package com.blackgear.geologicexpansion.common.worldgen.placements;

import com.blackgear.geologicexpansion.core.GeologicExpansion;
import com.blackgear.geologicexpansion.core.data.GEBlockTags;
import com.blackgear.geologicexpansion.core.platform.common.WorldGenRegistry;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.InclusiveRange;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.DualNoiseProvider;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import java.util.List;

public class SavannaFeatures {
    public static final WorldGenRegistry FEATURES = WorldGenRegistry.of(GeologicExpansion.MOD_ID);

    public static final Holder<ConfiguredFeature<VegetationPatchConfiguration, ?>> SAVANNA_SURFACE = FEATURES.create(
        "savanna_surface",
        Feature.VEGETATION_PATCH,
        new VegetationPatchConfiguration(
            GEBlockTags.SAVANNA_TERRACOTTA,
            new DualNoiseProvider(
                new InclusiveRange<>(1, 25),
                new NormalNoise.NoiseParameters(-3, 0.001, 0.002, 0.003, 0.01),
                1.0F,
                99,
                new NormalNoise.NoiseParameters(-7, 1, 1, 1),
                1.0F,
                List.of(
                    Blocks.GRASS_BLOCK.defaultBlockState()
                )
            ),
            PlacementUtils.inlinePlaced(Feature.NO_OP, FeatureConfiguration.NONE),
            CaveSurface.FLOOR,
            ConstantInt.of(1),
            0.1F,
            2,
            0,
            UniformInt.of(0, 4),
            0.5F
        )
    );
}
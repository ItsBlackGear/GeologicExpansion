package com.blackgear.geologicexpansion.common.worldgen.placements;

import com.blackgear.geologicexpansion.common.registries.GEBlocks;
import com.blackgear.geologicexpansion.core.GeologicExpansion;
import com.blackgear.geologicexpansion.core.platform.common.WorldGenRegistry;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;

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
}
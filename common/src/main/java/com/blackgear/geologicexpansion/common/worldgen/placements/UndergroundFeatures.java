package com.blackgear.geologicexpansion.common.worldgen.placements;

import com.blackgear.geologicexpansion.common.registries.GEBlocks;
import com.blackgear.geologicexpansion.core.GeologicExpansion;
import com.blackgear.geologicexpansion.core.platform.common.WorldGenRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

public class UndergroundFeatures {
    public static final WorldGenRegistry<ConfiguredFeature<?, ?>> FEATURES = WorldGenRegistry.of(Registries.CONFIGURED_FEATURE, GeologicExpansion.MOD_ID);

    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_LIMESTONE = FEATURES.create("ore_limestone");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        FEATURES.register(
                context,
                ORE_LIMESTONE,
                Feature.ORE,
                new OreConfiguration(
                        new TagMatchTest(BlockTags.BASE_STONE_OVERWORLD),
                        GEBlocks.LIMESTONE.get().defaultBlockState(),
                        64
                )
        );
    }
}
package com.blackgear.geologicexpansion.common.worldgen.placements;

import com.blackgear.geologicexpansion.common.registries.GEBlocks;
import com.blackgear.geologicexpansion.common.registries.worldgen.GEFeatures;
import com.blackgear.geologicexpansion.common.worldgen.features.OvergrowthPatchConfiguration;
import com.blackgear.geologicexpansion.core.GeologicExpansion;
import com.blackgear.geologicexpansion.core.data.GEBlockTags;
import com.blackgear.geologicexpansion.core.platform.common.WorldGenRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.CaveSurface;

public class SurfaceFeatures {
    public static final WorldGenRegistry<ConfiguredFeature<?, ?>> FEATURES = WorldGenRegistry.of(Registries.CONFIGURED_FEATURE, GeologicExpansion.MOD_ID);

    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERGROWTH_PATCH = FEATURES.create("overgrowth_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERGROWTH_PATCH_BONEMEAL = FEATURES.create("overgrowth_patch_bonemeal");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        SimpleBlockConfiguration overgrowthConfiguration = new SimpleBlockConfiguration(BlockStateProvider.simple(GEBlocks.OVERGROWTH.get()));
        FEATURES.register(context,
                OVERGROWTH_PATCH,
                GEFeatures.OVERGROWTH_PATCH.get(),
                new OvergrowthPatchConfiguration(
                        GEBlockTags.OVERGROWTH_GROWABLE_BLOCKS,
                        true,
                        PlacementUtils.inlinePlaced(Feature.SIMPLE_BLOCK, overgrowthConfiguration),
                        CaveSurface.FLOOR,
                        ConstantInt.of(1),
                        0.0F,
                        5,
                        0.6F,
                        UniformInt.of(1, 2),
                        0.75F
                )
        );
        FEATURES.register(context,
                OVERGROWTH_PATCH_BONEMEAL,
                GEFeatures.OVERGROWTH_PATCH.get(),
                new OvergrowthPatchConfiguration(
                        GEBlockTags.OVERGROWTH_GROWABLE_BLOCKS,
                        false,
                        PlacementUtils.inlinePlaced(Feature.SIMPLE_BLOCK, overgrowthConfiguration),
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
}
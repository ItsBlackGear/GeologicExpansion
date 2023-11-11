package com.blackgear.geologicexpansion.common.worldgen.placements;

import com.blackgear.geologicexpansion.common.registries.GEBlocks;
import com.blackgear.geologicexpansion.common.registries.worldgen.GEFeatures;
import com.blackgear.geologicexpansion.common.worldgen.features.OvergrowthPatchConfiguration;
import com.blackgear.geologicexpansion.core.GeologicExpansion;
import com.blackgear.geologicexpansion.core.data.GEBlockTags;
import com.blackgear.geologicexpansion.core.platform.common.WorldGenRegistry;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.CaveSurface;

public class SurfaceFeatures {
    public static final WorldGenRegistry FEATURES = WorldGenRegistry.of(GeologicExpansion.MOD_ID);

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
                    true, PlacementUtils.inlinePlaced(OVERGROWTH_VEGETATION),
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
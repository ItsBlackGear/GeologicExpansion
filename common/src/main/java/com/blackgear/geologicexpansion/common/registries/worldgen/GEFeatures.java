package com.blackgear.geologicexpansion.common.registries.worldgen;

import com.blackgear.geologicexpansion.common.worldgen.features.OvergrowthPatchConfiguration;
import com.blackgear.geologicexpansion.common.worldgen.features.OvergrowthPatchFeature;
import com.blackgear.geologicexpansion.core.GeologicExpansion;
import com.blackgear.geologicexpansion.core.platform.CoreRegistry;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.feature.Feature;

import java.util.function.Supplier;

public class GEFeatures {
    public static final CoreRegistry<Feature<?>> FEATURES = CoreRegistry.create(Registry.FEATURE, GeologicExpansion.MOD_ID);

    public static final Supplier<Feature<OvergrowthPatchConfiguration>> OVERGROWTH_PATCH = FEATURES.register("overgrowth", () -> new OvergrowthPatchFeature(OvergrowthPatchConfiguration.CODEC));
}
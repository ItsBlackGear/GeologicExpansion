package com.blackgear.geologicexpansion.common.registries.worldgen;

import com.blackgear.geologicexpansion.common.worldgen.features.GeyserPatchFeature;
import com.blackgear.geologicexpansion.common.worldgen.features.OvergrowthPatchConfiguration;
import com.blackgear.geologicexpansion.common.worldgen.features.OvergrowthPatchFeature;
import com.blackgear.geologicexpansion.common.worldgen.features.ScatteredVegetationPatchFeature;
import com.blackgear.geologicexpansion.core.GeologicExpansion;
import com.blackgear.geologicexpansion.core.platform.CoreRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;

import java.util.function.Supplier;

public class GEFeatures {
    public static final CoreRegistry<Feature<?>> FEATURES = CoreRegistry.create(BuiltInRegistries.FEATURE, GeologicExpansion.MOD_ID);

    public static final Supplier<Feature<OvergrowthPatchConfiguration>> OVERGROWTH_PATCH = FEATURES.register("overgrowth_patch", () -> new OvergrowthPatchFeature(OvergrowthPatchConfiguration.CODEC));
    public static final Supplier<Feature<NoneFeatureConfiguration>> GEYSER_PATCH = FEATURES.register("geyser_patch", () -> new GeyserPatchFeature(NoneFeatureConfiguration.CODEC));
    public static final Supplier<Feature<VegetationPatchConfiguration>> SCATTERED_VEGETATION_PATCH = FEATURES.register("scattered_vegetation_patch", () -> new ScatteredVegetationPatchFeature(VegetationPatchConfiguration.CODEC));
}
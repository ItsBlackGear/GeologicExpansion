package com.blackgear.geologicexpansion.common.registries.worldgen;

import com.blackgear.geologicexpansion.common.worldgen.features.ExposedOreConfiguration;
import com.blackgear.geologicexpansion.common.worldgen.features.ExposedOreFeature;
import com.blackgear.geologicexpansion.common.worldgen.features.ExtraVinesFeature;
import com.blackgear.geologicexpansion.common.worldgen.features.LargeLakeFeature;
import com.blackgear.geologicexpansion.common.worldgen.features.LargeLakeFeatureConfiguration;
import com.blackgear.geologicexpansion.common.worldgen.features.MultiVegetationPatchConfiguration;
import com.blackgear.geologicexpansion.common.worldgen.features.MultiVegetationPatchFeature;
import com.blackgear.geologicexpansion.common.worldgen.features.FallenTreeConfiguration;
import com.blackgear.geologicexpansion.common.worldgen.features.FallenTreeFeature;
import com.blackgear.geologicexpansion.common.worldgen.features.GeyserPatchFeature;
import com.blackgear.geologicexpansion.common.worldgen.features.OvergrowthPatchConfiguration;
import com.blackgear.geologicexpansion.common.worldgen.features.OvergrowthPatchFeature;
import com.blackgear.geologicexpansion.common.worldgen.features.ScatteredVegetationPatchFeature;
import com.blackgear.geologicexpansion.core.GeologicExpansion;
import com.blackgear.geologicexpansion.core.platform.CoreRegistry;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;

import java.util.function.Supplier;

public class GEFeatures {
    public static final CoreRegistry<Feature<?>> FEATURES = CoreRegistry.create(Registry.FEATURE, GeologicExpansion.MOD_ID);

    public static final Supplier<Feature<OvergrowthPatchConfiguration>> OVERGROWTH_PATCH = FEATURES.register("overgrowth_patch", () -> new OvergrowthPatchFeature(OvergrowthPatchConfiguration.CODEC));
    public static final Supplier<Feature<NoneFeatureConfiguration>> GEYSER_PATCH = FEATURES.register("geyser_patch", () -> new GeyserPatchFeature(NoneFeatureConfiguration.CODEC));
    public static final Supplier<Feature<VegetationPatchConfiguration>> SCATTERED_VEGETATION_PATCH = FEATURES.register("scattered_vegetation_patch", () -> new ScatteredVegetationPatchFeature(VegetationPatchConfiguration.CODEC));

    public static final Supplier<Feature<FallenTreeConfiguration>> FALLEN_TREE = FEATURES.register("fallen_tree", () -> new FallenTreeFeature(FallenTreeConfiguration.CODEC));
    public static final Supplier<Feature<ExposedOreConfiguration>> EXPOSED_ORE_FEATURE = FEATURES.register("exposed_ore", () -> new ExposedOreFeature(ExposedOreConfiguration.CODEC));
    public static final Supplier<Feature<MultiVegetationPatchConfiguration>> MULTI_VEGETATION_PATCH = FEATURES.register("multi_vegetation_patch", () -> new MultiVegetationPatchFeature(MultiVegetationPatchConfiguration.CODEC));
    public static final Supplier<Feature<NoneFeatureConfiguration>> EXTRA_VINES = FEATURES.register("extra_vines", () -> new ExtraVinesFeature(NoneFeatureConfiguration.CODEC));

    public static final Supplier<Feature<LargeLakeFeatureConfiguration>> LARGE_LAKE = FEATURES.register("large_lake", () -> new LargeLakeFeature(LargeLakeFeatureConfiguration.CODEC));
}
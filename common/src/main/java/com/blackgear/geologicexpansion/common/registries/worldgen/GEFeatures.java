package com.blackgear.geologicexpansion.common.registries.worldgen;

import com.blackgear.geologicexpansion.core.GeologicExpansion;
import com.blackgear.geologicexpansion.core.platform.CoreRegistry;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.feature.Feature;

public class GEFeatures {
    public static final CoreRegistry<Feature<?>> FEATURES = CoreRegistry.create(Registry.FEATURE, GeologicExpansion.MOD_ID);
}
package com.blackgear.geologicexpansion.common;

import com.blackgear.geologicexpansion.common.entity.duck.Duck;
import com.blackgear.geologicexpansion.common.registries.GEEntities;
import com.blackgear.geologicexpansion.common.worldgen.WorldGeneration;
import com.blackgear.geologicexpansion.common.worldgen.placements.CalderaFeatures;
import com.blackgear.geologicexpansion.common.worldgen.placements.CalderaPlacements;
import com.blackgear.geologicexpansion.core.platform.common.BiomeManager;
import com.blackgear.geologicexpansion.core.platform.common.EntityRegistry;

public class CommonSetup {
    public static void onInstance() {
        // ========== ENTITY ATTRIBUTE REGISTRY ========================================================================
        EntityRegistry.attributes(GEEntities.DUCK, Duck::createAttributes);
    }

    public static void postInstance() {
        // ========= FEATURE REGISTRY ==================================================================================
        CalderaFeatures.FEATURES.register();
        CalderaPlacements.FEATURES.register();

        // ========= WORLD GEN INITIALIZATION ==========================================================================
        BiomeManager.bootstrap();
        WorldGeneration.bootstrap();
    }
}
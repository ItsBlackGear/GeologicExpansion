package com.blackgear.geologicexpansion.common;

import com.blackgear.geologicexpansion.common.worldgen.WorldGeneration;
import com.blackgear.geologicexpansion.core.platform.common.BiomeManager;

public class CommonSetup {
    public static void onInstance() {

    }

    public static void postInstance() {
        BiomeManager.bootstrap();
        WorldGeneration.bootstrap();
    }
}
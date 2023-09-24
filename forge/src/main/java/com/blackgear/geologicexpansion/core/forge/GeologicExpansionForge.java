package com.blackgear.geologicexpansion.core.forge;

import com.blackgear.geologicexpansion.core.GeologicExpansion;
import com.blackgear.geologicexpansion.core.platform.common.BiomeManager;
import net.minecraftforge.fml.common.Mod;

@Mod(GeologicExpansion.MOD_ID)
public class GeologicExpansionForge {
    public GeologicExpansionForge() {
        GeologicExpansion.bootstrap();
        BiomeManager.bootstrap();
    }
}
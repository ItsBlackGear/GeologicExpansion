package com.blackgear.geologicexpansion.core.forge;

import com.blackgear.geologicexpansion.core.GeologicExpansion;
import com.blackgear.geologicexpansion.core.config.forge.ConfigEntriesImpl;
import com.blackgear.geologicexpansion.core.platform.common.BiomeManager;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod(GeologicExpansion.MOD_ID)
public class GeologicExpansionForge {
    public GeologicExpansionForge() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigEntriesImpl.COMMON_SPEC);

        GeologicExpansion.bootstrap();
        BiomeManager.bootstrap();
    }
}
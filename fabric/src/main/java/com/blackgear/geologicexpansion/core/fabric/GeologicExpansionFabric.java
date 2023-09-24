package com.blackgear.geologicexpansion.core.fabric;

import com.blackgear.geologicexpansion.core.GeologicExpansion;
import net.fabricmc.api.ModInitializer;

public class GeologicExpansionFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        GeologicExpansion.bootstrap();
    }
}
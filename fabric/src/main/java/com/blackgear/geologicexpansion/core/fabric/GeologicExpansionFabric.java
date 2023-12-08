package com.blackgear.geologicexpansion.core.fabric;

import com.blackgear.geologicexpansion.core.GeologicExpansion;
import com.blackgear.geologicexpansion.core.config.fabric.ConfigEntriesImpl;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;

public class GeologicExpansionFabric implements ModInitializer {
    public static final ConfigEntriesImpl CONFIG = AutoConfig.register(ConfigEntriesImpl.class, GsonConfigSerializer::new).getConfig();

    @Override
    public void onInitialize() {
        GeologicExpansion.bootstrap();
    }
}
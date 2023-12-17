package com.blackgear.geologicexpansion.core.fabric;

import com.blackgear.geologicexpansion.core.GeologicExpansion;
import com.blackgear.geologicexpansion.core.config.fabric.ConfigEntriesImpl;
import com.blackgear.geologicexpansion.core.datafix.DataFixes;
import com.blackgear.geologicexpansion.core.datafix.ModDataFixerBuilder;
import com.blackgear.geologicexpansion.core.datafix.SimpleFixes;
import com.llamalad7.mixinextras.MixinExtrasBootstrap;
import com.mojang.datafixers.schemas.Schema;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.datafix.schemas.NamespacedSchema;
import org.jetbrains.annotations.NotNull;

public class GeologicExpansionFabric implements ModInitializer {
    public static final ConfigEntriesImpl CONFIG = AutoConfig.register(ConfigEntriesImpl.class, GsonConfigSerializer::new).getConfig();
    private static final int DATA_VERSION = 1;

    @Override
    public void onInitialize() {
        MixinExtrasBootstrap.init();
        GeologicExpansion.bootstrap();
        applyDataFixes(FabricLoader.getInstance().getModContainer(GeologicExpansion.MOD_ID).orElseThrow());
    }

    private static void applyDataFixes(@NotNull ModContainer container) {
        ModDataFixerBuilder builder = new ModDataFixerBuilder(DATA_VERSION);
        builder.addSchema(0, DataFixes.BASE_SCHEMA);
        Schema schema = builder.addSchema(1, NamespacedSchema::new);
        SimpleFixes.addBlockRenameFix(builder, "Rename Geyser", new ResourceLocation(GeologicExpansion.MOD_ID, "prismatic_geyser"), new ResourceLocation(GeologicExpansion.MOD_ID, "geyser"), schema);
        SimpleFixes.addItemRenameFix(builder, "Rename Geyser", new ResourceLocation(GeologicExpansion.MOD_ID, "prismatic_geyser"), new ResourceLocation(GeologicExpansion.MOD_ID, "geyser"), schema);
        DataFixes.buildAndRegisterFixer(container, builder);
    }
}
package com.blackgear.geologicexpansion.core;

import com.blackgear.geologicexpansion.client.ClientSetup;
import com.blackgear.geologicexpansion.common.CommonSetup;
import com.blackgear.geologicexpansion.common.registries.GEBiomes;
import com.blackgear.geologicexpansion.common.registries.GEBlocks;
import com.blackgear.geologicexpansion.common.registries.GEItems;
import com.blackgear.geologicexpansion.common.worldgen.placements.TravertineFeatures;
import com.blackgear.geologicexpansion.common.worldgen.placements.TravertinePlacements;
import com.blackgear.geologicexpansion.core.platform.Environment;
import com.blackgear.geologicexpansion.core.platform.ModInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class GeologicExpansion {
    public static final String MOD_ID = "geologicexpansion";
    public static final ModInstance INSTANCE = ModInstance.create(MOD_ID).common(CommonSetup::onInstance).postCommon(CommonSetup::postInstance).client(ClientSetup::onInstance).postClient(ClientSetup::postInstance).build();
    public static final CreativeModeTab CREATIVE_TAB = Environment.createTab(new ResourceLocation(GeologicExpansion.MOD_ID), () -> new ItemStack(Items.STONE));

    public static void bootstrap() {
        INSTANCE.bootstrap();

        GEBlocks.BLOCKS.register();
        GEItems.ITEMS.register();

        TravertineFeatures.bootstrap();
        TravertinePlacements.bootstrap();

        GEBiomes.BIOMES.register();
    }
}
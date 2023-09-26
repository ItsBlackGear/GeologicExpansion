package com.blackgear.geologicexpansion.common.registries;

import com.blackgear.geologicexpansion.core.GeologicExpansion;
import com.blackgear.geologicexpansion.core.platform.CoreRegistry;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;

public class GEItems {
    public static final CoreRegistry<Item> ITEMS = CoreRegistry.create(Registry.ITEM, GeologicExpansion.MOD_ID);
}
package com.blackgear.geologicexpansion.core.data;

import com.blackgear.geologicexpansion.core.GeologicExpansion;
import com.blackgear.geologicexpansion.core.platform.common.TagRegistry;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class GEItemTags {
    public static final TagRegistry<Item> TAGS = TagRegistry.of(Registry.ITEM_REGISTRY, GeologicExpansion.MOD_ID);

    public static final TagKey<Item> DUCK_TRADEABLES = TAGS.create("duck_tradeables");
}
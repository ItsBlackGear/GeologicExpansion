package com.blackgear.geologicexpansion.common.registries;

import com.blackgear.geologicexpansion.common.item.DuckEggItem;
import com.blackgear.geologicexpansion.core.GeologicExpansion;
import com.blackgear.geologicexpansion.core.platform.CoreRegistry;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;

import java.util.function.Supplier;

public class GEItems {
    public static final CoreRegistry<Item> ITEMS = CoreRegistry.create(Registry.ITEM, GeologicExpansion.MOD_ID);

    public static final Supplier<Item> DUCK_SPAWN_EGG = create("duck_spawn_egg", () -> new SpawnEggItem(GEEntities.DUCK.get(), 4927013, 1543936, new Item.Properties().tab(GeologicExpansion.CREATIVE_TAB)));
    public static final Supplier<Item> DUCK_EGG = create("duck_egg", () -> new DuckEggItem(new Item.Properties().stacksTo(16).tab(GeologicExpansion.CREATIVE_TAB)));

    private static <T extends Item> Supplier<T> create(String key, Supplier<T> item) {
        return ITEMS.register(key, item);
    }
}
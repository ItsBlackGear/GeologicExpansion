package com.blackgear.geologicexpansion.common.registries;

import com.blackgear.geologicexpansion.common.item.DuckEggItem;
import com.blackgear.geologicexpansion.core.GeologicExpansion;
import com.blackgear.geologicexpansion.core.platform.CoreRegistry;
import com.blackgear.geologicexpansion.core.platform.Environment;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class GEItems {
    public static final CoreRegistry<Item> ITEMS = CoreRegistry.create(BuiltInRegistries.ITEM, GeologicExpansion.MOD_ID);

    public static final Supplier<Item> DUCK_SPAWN_EGG = create("duck_spawn_egg", () -> Environment.createSpawnEgg(GEEntities.DUCK, 4927013, 1543936, new Item.Properties()));
    public static final Supplier<Item> DUCK_EGG = create("duck_egg", () -> new DuckEggItem(new Item.Properties().stacksTo(16)));

    private static <T extends Item> Supplier<T> create(String key, Supplier<T> item) {
        return ITEMS.register(key, item);
    }
}
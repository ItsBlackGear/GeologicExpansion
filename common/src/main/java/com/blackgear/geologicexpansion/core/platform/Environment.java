package com.blackgear.geologicexpansion.core.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;

import java.util.function.Supplier;

public class Environment {
    @ExpectPlatform
    public static CreativeModeTab createTab(ResourceLocation location, Supplier<ItemStack> icon) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static boolean isModLoaded(String modId) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static boolean isClientSide() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static WoodType create(ResourceLocation location, BlockSetType blockSet) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T extends Mob> SpawnEggItem createSpawnEgg(Supplier<EntityType<T>> type, int backgroundColor, int highlightColor, Item.Properties properties) {
        throw new AssertionError();
    }
}
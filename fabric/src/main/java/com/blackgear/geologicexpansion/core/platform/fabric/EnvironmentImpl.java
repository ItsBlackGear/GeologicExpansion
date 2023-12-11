package com.blackgear.geologicexpansion.core.platform.fabric;

import com.blackgear.geologicexpansion.core.platform.Environment;
import com.blackgear.geologicexpansion.core.platform.common.resource.CreativeModTabBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.network.chat.Component;
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

public class EnvironmentImpl {
    public static CreativeModeTab createTab(ResourceLocation location, Supplier<ItemStack> icon) {
        return new CreativeModTabBuilder().title(Component.keybind(location.toString())).icon(icon).build();
    }

    public static boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    public static boolean isClientSide() {
        return FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;
    }

    public static WoodType create(ResourceLocation location, BlockSetType blockSet) {
        WoodType type = WoodType.register(new WoodType(location.toString(), blockSet));
        if (Environment.isClientSide()) Sheets.SIGN_MATERIALS.put(type, Sheets.createSignMaterial(type));
        return type;
    }

    public static <T extends Mob> SpawnEggItem createSpawnEgg(Supplier<EntityType<T>> type, int backgroundColor, int highlightColor, Item.Properties properties) {
        return new SpawnEggItem(type.get(), backgroundColor, highlightColor, properties);
    }

//    public static class WoodTypeBuilder extends WoodType {
//        private final ResourceLocation location;
//
//        protected WoodTypeBuilder(ResourceLocation location) {
//            super(location.getPath());
//            this.location = location;
//        }
//
//        public ResourceLocation getLocation() {
//            return this.location;
//        }
//    }
}
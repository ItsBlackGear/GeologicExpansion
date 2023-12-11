package com.blackgear.geologicexpansion.core.platform.forge;

import com.blackgear.geologicexpansion.core.platform.Environment;
import com.blackgear.geologicexpansion.core.platform.common.resource.CreativeModTabBuilder;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;

import java.util.function.Supplier;

@SuppressWarnings("NullableProblems")
public class EnvironmentImpl {
    public static CreativeModeTab createTab(ResourceLocation location, Supplier<ItemStack> icon) {
        return new CreativeModTabBuilder().title(Component.keybind(location.toString())).icon(icon).build();
    }

    public static boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    public static boolean isClientSide() {
        return FMLLoader.getDist() == Dist.CLIENT;
    }

    public static WoodType create(ResourceLocation location, BlockSetType blockSet) {
        WoodType type = WoodType.register(new WoodType(location.toString(), blockSet));
        if (Environment.isClientSide()) Sheets.addWoodType(type);
        return type;
    }

    public static <T extends Mob> SpawnEggItem createSpawnEgg(Supplier<EntityType<T>> type, int backgroundColor, int highlightColor, Item.Properties properties) {
        return new ForgeSpawnEggItem(type, backgroundColor, highlightColor, properties);
    }
}
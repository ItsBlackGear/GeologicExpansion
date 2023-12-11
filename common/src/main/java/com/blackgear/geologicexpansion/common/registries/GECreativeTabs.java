package com.blackgear.geologicexpansion.common.registries;

import com.blackgear.geologicexpansion.core.GeologicExpansion;
import com.blackgear.geologicexpansion.core.platform.CoreRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Items;

import java.util.function.Supplier;

public class GECreativeTabs {
    public static final CoreRegistry<CreativeModeTab> TABS = CoreRegistry.create(BuiltInRegistries.CREATIVE_MODE_TAB, GeologicExpansion.MOD_ID);

    public static final Supplier<CreativeModeTab> GEOLOGIC_EXPANSION = TABS.register("geologic_expansion", () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
            .icon(Items.STONE::getDefaultInstance)
            .title(Component.translatable("itemGroup.minecraft.geologicexpansion"))
            .displayItems((parameters, output) -> {
                output.accept(GEItems.DUCK_SPAWN_EGG.get());
                output.accept(GEItems.DUCK_EGG.get());

                output.accept(GEBlocks.OVERGROWTH.get());

                output.accept(GEBlocks.LIMESTONE.get());
                output.accept(GEBlocks.LIMESTONE_SLAB.get());
                output.accept(GEBlocks.LIMESTONE_STAIRS.get());
                output.accept(GEBlocks.LIMESTONE_WALL.get());
                output.accept(GEBlocks.POLISHED_LIMESTONE.get());
                output.accept(GEBlocks.POLISHED_LIMESTONE_SLAB.get());
                output.accept(GEBlocks.POLISHED_LIMESTONE_STAIRS.get());
                output.accept(GEBlocks.POLISHED_LIMESTONE_WALL.get());

                output.accept(GEBlocks.GEYSER.get());

                output.accept(GEBlocks.PRISMATIC_STONE.get());
                output.accept(GEBlocks.PRISMATIC_STONE_SLAB.get());
                output.accept(GEBlocks.PRISMATIC_STONE_STAIRS.get());
                output.accept(GEBlocks.PRISMATIC_STONE_WALL.get());
                output.accept(GEBlocks.POLISHED_PRISMATIC_STONE.get());
                output.accept(GEBlocks.POLISHED_PRISMATIC_STONE_SLAB.get());
                output.accept(GEBlocks.POLISHED_PRISMATIC_STONE_STAIRS.get());
                output.accept(GEBlocks.POLISHED_PRISMATIC_STONE_WALL.get());

                output.accept(GEBlocks.WHITE_PRISMATIC_STONE.get());
                output.accept(GEBlocks.WHITE_PRISMATIC_STONE_SLAB.get());
                output.accept(GEBlocks.WHITE_PRISMATIC_STONE_STAIRS.get());
                output.accept(GEBlocks.WHITE_PRISMATIC_STONE_WALL.get());
                output.accept(GEBlocks.WHITE_POLISHED_PRISMATIC_STONE.get());
                output.accept(GEBlocks.WHITE_POLISHED_PRISMATIC_STONE_SLAB.get());
                output.accept(GEBlocks.WHITE_POLISHED_PRISMATIC_STONE_STAIRS.get());
                output.accept(GEBlocks.WHITE_POLISHED_PRISMATIC_STONE_WALL.get());

                output.accept(GEBlocks.ORANGE_PRISMATIC_STONE.get());
                output.accept(GEBlocks.ORANGE_PRISMATIC_STONE_SLAB.get());
                output.accept(GEBlocks.ORANGE_PRISMATIC_STONE_STAIRS.get());
                output.accept(GEBlocks.ORANGE_PRISMATIC_STONE_WALL.get());
                output.accept(GEBlocks.ORANGE_POLISHED_PRISMATIC_STONE.get());
                output.accept(GEBlocks.ORANGE_POLISHED_PRISMATIC_STONE_SLAB.get());
                output.accept(GEBlocks.ORANGE_POLISHED_PRISMATIC_STONE_STAIRS.get());
                output.accept(GEBlocks.ORANGE_POLISHED_PRISMATIC_STONE_WALL.get());

                output.accept(GEBlocks.MAGENTA_PRISMATIC_STONE.get());
                output.accept(GEBlocks.MAGENTA_PRISMATIC_STONE_SLAB.get());
                output.accept(GEBlocks.MAGENTA_PRISMATIC_STONE_STAIRS.get());
                output.accept(GEBlocks.MAGENTA_PRISMATIC_STONE_WALL.get());
                output.accept(GEBlocks.MAGENTA_POLISHED_PRISMATIC_STONE.get());
                output.accept(GEBlocks.MAGENTA_POLISHED_PRISMATIC_STONE_SLAB.get());
                output.accept(GEBlocks.MAGENTA_POLISHED_PRISMATIC_STONE_STAIRS.get());
                output.accept(GEBlocks.MAGENTA_POLISHED_PRISMATIC_STONE_WALL.get());

                output.accept(GEBlocks.LIGHT_BLUE_PRISMATIC_STONE.get());
                output.accept(GEBlocks.LIGHT_BLUE_PRISMATIC_STONE_SLAB.get());
                output.accept(GEBlocks.LIGHT_BLUE_PRISMATIC_STONE_STAIRS.get());
                output.accept(GEBlocks.LIGHT_BLUE_PRISMATIC_STONE_WALL.get());
                output.accept(GEBlocks.LIGHT_BLUE_POLISHED_PRISMATIC_STONE.get());
                output.accept(GEBlocks.LIGHT_BLUE_POLISHED_PRISMATIC_STONE_SLAB.get());
                output.accept(GEBlocks.LIGHT_BLUE_POLISHED_PRISMATIC_STONE_STAIRS.get());
                output.accept(GEBlocks.LIGHT_BLUE_POLISHED_PRISMATIC_STONE_WALL.get());

                output.accept(GEBlocks.YELLOW_PRISMATIC_STONE.get());
                output.accept(GEBlocks.YELLOW_PRISMATIC_STONE_SLAB.get());
                output.accept(GEBlocks.YELLOW_PRISMATIC_STONE_STAIRS.get());
                output.accept(GEBlocks.YELLOW_PRISMATIC_STONE_WALL.get());
                output.accept(GEBlocks.YELLOW_POLISHED_PRISMATIC_STONE.get());
                output.accept(GEBlocks.YELLOW_POLISHED_PRISMATIC_STONE_SLAB.get());
                output.accept(GEBlocks.YELLOW_POLISHED_PRISMATIC_STONE_STAIRS.get());
                output.accept(GEBlocks.YELLOW_POLISHED_PRISMATIC_STONE_WALL.get());

                output.accept(GEBlocks.LIME_PRISMATIC_STONE.get());
                output.accept(GEBlocks.LIME_PRISMATIC_STONE_SLAB.get());
                output.accept(GEBlocks.LIME_PRISMATIC_STONE_STAIRS.get());
                output.accept(GEBlocks.LIME_PRISMATIC_STONE_WALL.get());
                output.accept(GEBlocks.LIME_POLISHED_PRISMATIC_STONE.get());
                output.accept(GEBlocks.LIME_POLISHED_PRISMATIC_STONE_SLAB.get());
                output.accept(GEBlocks.LIME_POLISHED_PRISMATIC_STONE_STAIRS.get());
                output.accept(GEBlocks.LIME_POLISHED_PRISMATIC_STONE_WALL.get());

                output.accept(GEBlocks.PINK_PRISMATIC_STONE.get());
                output.accept(GEBlocks.PINK_PRISMATIC_STONE_SLAB.get());
                output.accept(GEBlocks.PINK_PRISMATIC_STONE_STAIRS.get());
                output.accept(GEBlocks.PINK_PRISMATIC_STONE_WALL.get());
                output.accept(GEBlocks.PINK_POLISHED_PRISMATIC_STONE.get());
                output.accept(GEBlocks.PINK_POLISHED_PRISMATIC_STONE_SLAB.get());
                output.accept(GEBlocks.PINK_POLISHED_PRISMATIC_STONE_STAIRS.get());
                output.accept(GEBlocks.PINK_POLISHED_PRISMATIC_STONE_WALL.get());

                output.accept(GEBlocks.GRAY_PRISMATIC_STONE.get());
                output.accept(GEBlocks.GRAY_PRISMATIC_STONE_SLAB.get());
                output.accept(GEBlocks.GRAY_PRISMATIC_STONE_STAIRS.get());
                output.accept(GEBlocks.GRAY_PRISMATIC_STONE_WALL.get());
                output.accept(GEBlocks.GRAY_POLISHED_PRISMATIC_STONE.get());
                output.accept(GEBlocks.GRAY_POLISHED_PRISMATIC_STONE_SLAB.get());
                output.accept(GEBlocks.GRAY_POLISHED_PRISMATIC_STONE_STAIRS.get());
                output.accept(GEBlocks.GRAY_POLISHED_PRISMATIC_STONE_WALL.get());

                output.accept(GEBlocks.LIGHT_GRAY_PRISMATIC_STONE.get());
                output.accept(GEBlocks.LIGHT_GRAY_PRISMATIC_STONE_SLAB.get());
                output.accept(GEBlocks.LIGHT_GRAY_PRISMATIC_STONE_STAIRS.get());
                output.accept(GEBlocks.LIGHT_GRAY_PRISMATIC_STONE_WALL.get());
                output.accept(GEBlocks.LIGHT_GRAY_POLISHED_PRISMATIC_STONE.get());
                output.accept(GEBlocks.LIGHT_GRAY_POLISHED_PRISMATIC_STONE_SLAB.get());
                output.accept(GEBlocks.LIGHT_GRAY_POLISHED_PRISMATIC_STONE_STAIRS.get());
                output.accept(GEBlocks.LIGHT_GRAY_POLISHED_PRISMATIC_STONE_WALL.get());

                output.accept(GEBlocks.CYAN_PRISMATIC_STONE.get());
                output.accept(GEBlocks.CYAN_PRISMATIC_STONE_SLAB.get());
                output.accept(GEBlocks.CYAN_PRISMATIC_STONE_STAIRS.get());
                output.accept(GEBlocks.CYAN_PRISMATIC_STONE_WALL.get());
                output.accept(GEBlocks.CYAN_POLISHED_PRISMATIC_STONE.get());
                output.accept(GEBlocks.CYAN_POLISHED_PRISMATIC_STONE_SLAB.get());
                output.accept(GEBlocks.CYAN_POLISHED_PRISMATIC_STONE_STAIRS.get());
                output.accept(GEBlocks.CYAN_POLISHED_PRISMATIC_STONE_WALL.get());

                output.accept(GEBlocks.PURPLE_PRISMATIC_STONE.get());
                output.accept(GEBlocks.PURPLE_PRISMATIC_STONE_SLAB.get());
                output.accept(GEBlocks.PURPLE_PRISMATIC_STONE_STAIRS.get());
                output.accept(GEBlocks.PURPLE_PRISMATIC_STONE_WALL.get());
                output.accept(GEBlocks.PURPLE_POLISHED_PRISMATIC_STONE.get());
                output.accept(GEBlocks.PURPLE_POLISHED_PRISMATIC_STONE_SLAB.get());
                output.accept(GEBlocks.PURPLE_POLISHED_PRISMATIC_STONE_STAIRS.get());
                output.accept(GEBlocks.PURPLE_POLISHED_PRISMATIC_STONE_WALL.get());

                output.accept(GEBlocks.BLUE_PRISMATIC_STONE.get());
                output.accept(GEBlocks.BLUE_PRISMATIC_STONE_SLAB.get());
                output.accept(GEBlocks.BLUE_PRISMATIC_STONE_STAIRS.get());
                output.accept(GEBlocks.BLUE_PRISMATIC_STONE_WALL.get());
                output.accept(GEBlocks.BLUE_POLISHED_PRISMATIC_STONE.get());
                output.accept(GEBlocks.BLUE_POLISHED_PRISMATIC_STONE_SLAB.get());
                output.accept(GEBlocks.BLUE_POLISHED_PRISMATIC_STONE_STAIRS.get());
                output.accept(GEBlocks.BLUE_POLISHED_PRISMATIC_STONE_WALL.get());

                output.accept(GEBlocks.BROWN_PRISMATIC_STONE.get());
                output.accept(GEBlocks.BROWN_PRISMATIC_STONE_SLAB.get());
                output.accept(GEBlocks.BROWN_PRISMATIC_STONE_STAIRS.get());
                output.accept(GEBlocks.BROWN_PRISMATIC_STONE_WALL.get());
                output.accept(GEBlocks.BROWN_POLISHED_PRISMATIC_STONE.get());
                output.accept(GEBlocks.BROWN_POLISHED_PRISMATIC_STONE_SLAB.get());
                output.accept(GEBlocks.BROWN_POLISHED_PRISMATIC_STONE_STAIRS.get());
                output.accept(GEBlocks.BROWN_POLISHED_PRISMATIC_STONE_WALL.get());

                output.accept(GEBlocks.GREEN_PRISMATIC_STONE.get());
                output.accept(GEBlocks.GREEN_PRISMATIC_STONE_SLAB.get());
                output.accept(GEBlocks.GREEN_PRISMATIC_STONE_STAIRS.get());
                output.accept(GEBlocks.GREEN_PRISMATIC_STONE_WALL.get());
                output.accept(GEBlocks.GREEN_POLISHED_PRISMATIC_STONE.get());
                output.accept(GEBlocks.GREEN_POLISHED_PRISMATIC_STONE_SLAB.get());
                output.accept(GEBlocks.GREEN_POLISHED_PRISMATIC_STONE_STAIRS.get());
                output.accept(GEBlocks.GREEN_POLISHED_PRISMATIC_STONE_WALL.get());

                output.accept(GEBlocks.RED_PRISMATIC_STONE.get());
                output.accept(GEBlocks.RED_PRISMATIC_STONE_SLAB.get());
                output.accept(GEBlocks.RED_PRISMATIC_STONE_STAIRS.get());
                output.accept(GEBlocks.RED_PRISMATIC_STONE_WALL.get());
                output.accept(GEBlocks.RED_POLISHED_PRISMATIC_STONE.get());
                output.accept(GEBlocks.RED_POLISHED_PRISMATIC_STONE_SLAB.get());
                output.accept(GEBlocks.RED_POLISHED_PRISMATIC_STONE_STAIRS.get());
                output.accept(GEBlocks.RED_POLISHED_PRISMATIC_STONE_WALL.get());

                output.accept(GEBlocks.BLACK_PRISMATIC_STONE.get());
                output.accept(GEBlocks.BLACK_PRISMATIC_STONE_SLAB.get());
                output.accept(GEBlocks.BLACK_PRISMATIC_STONE_STAIRS.get());
                output.accept(GEBlocks.BLACK_PRISMATIC_STONE_WALL.get());
                output.accept(GEBlocks.BLACK_POLISHED_PRISMATIC_STONE.get());
                output.accept(GEBlocks.BLACK_POLISHED_PRISMATIC_STONE_SLAB.get());
                output.accept(GEBlocks.BLACK_POLISHED_PRISMATIC_STONE_STAIRS.get());
                output.accept(GEBlocks.BLACK_POLISHED_PRISMATIC_STONE_WALL.get());
            })
            .build()
    );
}
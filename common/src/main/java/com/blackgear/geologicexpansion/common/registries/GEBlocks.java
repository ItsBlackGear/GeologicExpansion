package com.blackgear.geologicexpansion.common.registries;

import com.blackgear.geologicexpansion.core.GeologicExpansion;
import com.blackgear.geologicexpansion.core.platform.CoreRegistry;
import net.minecraft.core.Registry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

import java.util.function.Function;
import java.util.function.Supplier;

public class GEBlocks {
    public static final CoreRegistry<Block> BLOCKS = CoreRegistry.create(Registry.BLOCK, GeologicExpansion.MOD_ID);

    public static final Supplier<Block> LIMESTONE = create("limestone", () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.SAND).requiresCorrectToolForDrops().strength(1.5F, 6.0F)), GeologicExpansion.CREATIVE_TAB);
    public static final Supplier<Block> LIMESTONE_SLAB = create("limestone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(LIMESTONE.get())), GeologicExpansion.CREATIVE_TAB);
    public static final Supplier<Block> LIMESTONE_STAIRS = create("limestone_stairs", () -> new StairBlock(LIMESTONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(LIMESTONE.get())), GeologicExpansion.CREATIVE_TAB);
    public static final Supplier<Block> LIMESTONE_WALL = create("limestone_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(LIMESTONE.get())), GeologicExpansion.CREATIVE_TAB);
    public static final Supplier<Block> POLISHED_LIMESTONE = create("polished_limestone", () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.SAND).requiresCorrectToolForDrops().strength(1.5F, 6.0F)), GeologicExpansion.CREATIVE_TAB);
    public static final Supplier<Block> POLISHED_LIMESTONE_SLAB = create("polished_limestone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(POLISHED_LIMESTONE.get())), GeologicExpansion.CREATIVE_TAB);
    public static final Supplier<Block> POLISHED_LIMESTONE_STAIRS = create("polished_limestone_stairs", () -> new StairBlock(POLISHED_LIMESTONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(POLISHED_LIMESTONE.get())), GeologicExpansion.CREATIVE_TAB);
    public static final Supplier<Block> POLISHED_LIMESTONE_WALL = create("polished_limestone_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(POLISHED_LIMESTONE.get())), GeologicExpansion.CREATIVE_TAB);

    private static <T extends Block> Supplier<T> create(String key, Supplier<T> block, CreativeModeTab tab) {
        return create(key, block, entry -> new BlockItem(entry.get(), new Item.Properties().tab(tab)));
    }

    private static <T extends Block> Supplier<T> create(String key, Supplier<T> block, Function<Supplier<T>, Item> item) {
        Supplier<T> entry = create(key, block);
        GEItems.ITEMS.register(key, () -> item.apply(entry));
        return entry;
    }

    private static <T extends Block> Supplier<T> create(String key, Supplier<T> block) {
        return BLOCKS.register(key, block);
    }
}
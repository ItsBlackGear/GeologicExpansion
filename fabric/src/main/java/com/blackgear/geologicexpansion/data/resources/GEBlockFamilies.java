package com.blackgear.geologicexpansion.data.resources;

import com.blackgear.geologicexpansion.common.registries.GEBlocks;
import com.google.common.collect.Maps;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.BlockFamily;
import net.minecraft.world.level.block.Block;

import java.util.Map;
import java.util.stream.Stream;

public class GEBlockFamilies {
    private static final Map<Block, BlockFamily> FAMILIES = Maps.newHashMap();
    private static final Map<Block, BlockFamily> STONE = Maps.newHashMap();

    // LIMESTONE
    public static final BlockFamily LIMESTONE = stone(GEBlocks.LIMESTONE.get())
            .slab(GEBlocks.LIMESTONE_SLAB.get())
            .wall(GEBlocks.LIMESTONE_WALL.get())
            .stairs(GEBlocks.LIMESTONE_STAIRS.get())
            .polished(GEBlocks.POLISHED_LIMESTONE.get())
            .getFamily();

    public static final BlockFamily POLISHED_LIMESTONE = stone(GEBlocks.POLISHED_LIMESTONE.get())
            .slab(GEBlocks.POLISHED_LIMESTONE_SLAB.get())
            .wall(GEBlocks.POLISHED_LIMESTONE_WALL.get())
            .stairs(GEBlocks.POLISHED_LIMESTONE_STAIRS.get())
            .getFamily();

    // PRISMATIC STONE
    public static final BlockFamily PRISMATIC_STONE = stone(GEBlocks.PRISMATIC_STONE.get())
            .slab(GEBlocks.PRISMATIC_STONE_SLAB.get())
            .wall(GEBlocks.PRISMATIC_STONE_WALL.get())
            .stairs(GEBlocks.PRISMATIC_STONE_STAIRS.get())
            .polished(GEBlocks.POLISHED_PRISMATIC_STONE.get())
            .getFamily();

    public static final BlockFamily POLISHED_PRISMATIC_STONE = stone(GEBlocks.POLISHED_PRISMATIC_STONE.get())
            .slab(GEBlocks.POLISHED_PRISMATIC_STONE_SLAB.get())
            .wall(GEBlocks.POLISHED_PRISMATIC_STONE_WALL.get())
            .stairs(GEBlocks.POLISHED_PRISMATIC_STONE_STAIRS.get())
            .getFamily();

    public static final BlockFamily WHITE_PRISMATIC_STONE = stone(GEBlocks.WHITE_PRISMATIC_STONE.get())
            .slab(GEBlocks.WHITE_PRISMATIC_STONE_SLAB.get())
            .wall(GEBlocks.WHITE_PRISMATIC_STONE_WALL.get())
            .stairs(GEBlocks.WHITE_PRISMATIC_STONE_STAIRS.get())
            .polished(GEBlocks.WHITE_POLISHED_PRISMATIC_STONE.get())
            .getFamily();

    public static final BlockFamily WHITE_POLISHED_PRISMATIC_STONE = stone(GEBlocks.WHITE_POLISHED_PRISMATIC_STONE.get())
            .slab(GEBlocks.WHITE_POLISHED_PRISMATIC_STONE_SLAB.get())
            .wall(GEBlocks.WHITE_POLISHED_PRISMATIC_STONE_WALL.get())
            .stairs(GEBlocks.WHITE_POLISHED_PRISMATIC_STONE_STAIRS.get())
            .getFamily();

    public static final BlockFamily ORANGE_PRISMATIC_STONE = stone(GEBlocks.ORANGE_PRISMATIC_STONE.get())
            .slab(GEBlocks.ORANGE_PRISMATIC_STONE_SLAB.get())
            .wall(GEBlocks.ORANGE_PRISMATIC_STONE_WALL.get())
            .stairs(GEBlocks.ORANGE_PRISMATIC_STONE_STAIRS.get())
            .polished(GEBlocks.ORANGE_POLISHED_PRISMATIC_STONE.get())
            .getFamily();

    public static final BlockFamily ORANGE_POLISHED_PRISMATIC_STONE = stone(GEBlocks.ORANGE_POLISHED_PRISMATIC_STONE.get())
            .slab(GEBlocks.ORANGE_POLISHED_PRISMATIC_STONE_SLAB.get())
            .wall(GEBlocks.ORANGE_POLISHED_PRISMATIC_STONE_WALL.get())
            .stairs(GEBlocks.ORANGE_POLISHED_PRISMATIC_STONE_STAIRS.get())
            .getFamily();

    public static final BlockFamily MAGENTA_PRISMATIC_STONE = stone(GEBlocks.MAGENTA_PRISMATIC_STONE.get())
            .slab(GEBlocks.MAGENTA_PRISMATIC_STONE_SLAB.get())
            .wall(GEBlocks.MAGENTA_PRISMATIC_STONE_WALL.get())
            .stairs(GEBlocks.MAGENTA_PRISMATIC_STONE_STAIRS.get())
            .polished(GEBlocks.MAGENTA_POLISHED_PRISMATIC_STONE.get())
            .getFamily();

    public static final BlockFamily MAGENTA_POLISHED_PRISMATIC_STONE = stone(GEBlocks.MAGENTA_POLISHED_PRISMATIC_STONE.get())
            .slab(GEBlocks.MAGENTA_POLISHED_PRISMATIC_STONE_SLAB.get())
            .wall(GEBlocks.MAGENTA_POLISHED_PRISMATIC_STONE_WALL.get())
            .stairs(GEBlocks.MAGENTA_POLISHED_PRISMATIC_STONE_STAIRS.get())
            .getFamily();

    public static final BlockFamily LIGHT_BLUE_PRISMATIC_STONE = stone(GEBlocks.LIGHT_BLUE_PRISMATIC_STONE.get())
            .slab(GEBlocks.LIGHT_BLUE_PRISMATIC_STONE_SLAB.get())
            .wall(GEBlocks.LIGHT_BLUE_PRISMATIC_STONE_WALL.get())
            .stairs(GEBlocks.LIGHT_BLUE_PRISMATIC_STONE_STAIRS.get())
            .polished(GEBlocks.LIGHT_BLUE_POLISHED_PRISMATIC_STONE.get())
            .getFamily();

    public static final BlockFamily LIGHT_BLUE_POLISHED_PRISMATIC_STONE = stone(GEBlocks.LIGHT_BLUE_POLISHED_PRISMATIC_STONE.get())
            .slab(GEBlocks.LIGHT_BLUE_POLISHED_PRISMATIC_STONE_SLAB.get())
            .wall(GEBlocks.LIGHT_BLUE_POLISHED_PRISMATIC_STONE_WALL.get())
            .stairs(GEBlocks.LIGHT_BLUE_POLISHED_PRISMATIC_STONE_STAIRS.get())
            .getFamily();

    public static final BlockFamily YELLOW_PRISMATIC_STONE = stone(GEBlocks.YELLOW_PRISMATIC_STONE.get())
            .slab(GEBlocks.YELLOW_PRISMATIC_STONE_SLAB.get())
            .wall(GEBlocks.YELLOW_PRISMATIC_STONE_WALL.get())
            .stairs(GEBlocks.YELLOW_PRISMATIC_STONE_STAIRS.get())
            .polished(GEBlocks.YELLOW_POLISHED_PRISMATIC_STONE.get())
            .getFamily();

    public static final BlockFamily YELLOW_POLISHED_PRISMATIC_STONE = stone(GEBlocks.YELLOW_POLISHED_PRISMATIC_STONE.get())
            .slab(GEBlocks.YELLOW_POLISHED_PRISMATIC_STONE_SLAB.get())
            .wall(GEBlocks.YELLOW_POLISHED_PRISMATIC_STONE_WALL.get())
            .stairs(GEBlocks.YELLOW_POLISHED_PRISMATIC_STONE_STAIRS.get())
            .getFamily();

    public static final BlockFamily LIME_PRISMATIC_STONE = stone(GEBlocks.LIME_PRISMATIC_STONE.get())
            .slab(GEBlocks.LIME_PRISMATIC_STONE_SLAB.get())
            .wall(GEBlocks.LIME_PRISMATIC_STONE_WALL.get())
            .stairs(GEBlocks.LIME_PRISMATIC_STONE_STAIRS.get())
            .polished(GEBlocks.LIME_POLISHED_PRISMATIC_STONE.get())
            .getFamily();

    public static final BlockFamily LIME_POLISHED_PRISMATIC_STONE = stone(GEBlocks.LIME_POLISHED_PRISMATIC_STONE.get())
            .slab(GEBlocks.LIME_POLISHED_PRISMATIC_STONE_SLAB.get())
            .wall(GEBlocks.LIME_POLISHED_PRISMATIC_STONE_WALL.get())
            .stairs(GEBlocks.LIME_POLISHED_PRISMATIC_STONE_STAIRS.get())
            .getFamily();

    public static final BlockFamily PINK_PRISMATIC_STONE = stone(GEBlocks.PINK_PRISMATIC_STONE.get())
            .slab(GEBlocks.PINK_PRISMATIC_STONE_SLAB.get())
            .wall(GEBlocks.PINK_PRISMATIC_STONE_WALL.get())
            .stairs(GEBlocks.PINK_PRISMATIC_STONE_STAIRS.get())
            .polished(GEBlocks.PINK_POLISHED_PRISMATIC_STONE.get())
            .getFamily();

    public static final BlockFamily PINK_POLISHED_PRISMATIC_STONE = stone(GEBlocks.PINK_POLISHED_PRISMATIC_STONE.get())
            .slab(GEBlocks.PINK_POLISHED_PRISMATIC_STONE_SLAB.get())
            .wall(GEBlocks.PINK_POLISHED_PRISMATIC_STONE_WALL.get())
            .stairs(GEBlocks.PINK_POLISHED_PRISMATIC_STONE_STAIRS.get())
            .getFamily();

    public static final BlockFamily GRAY_PRISMATIC_STONE = stone(GEBlocks.GRAY_PRISMATIC_STONE.get())
            .slab(GEBlocks.GRAY_PRISMATIC_STONE_SLAB.get())
            .wall(GEBlocks.GRAY_PRISMATIC_STONE_WALL.get())
            .stairs(GEBlocks.GRAY_PRISMATIC_STONE_STAIRS.get())
            .polished(GEBlocks.GRAY_POLISHED_PRISMATIC_STONE.get())
            .getFamily();

    public static final BlockFamily GRAY_POLISHED_PRISMATIC_STONE = stone(GEBlocks.GRAY_POLISHED_PRISMATIC_STONE.get())
            .slab(GEBlocks.GRAY_POLISHED_PRISMATIC_STONE_SLAB.get())
            .wall(GEBlocks.GRAY_POLISHED_PRISMATIC_STONE_WALL.get())
            .stairs(GEBlocks.GRAY_POLISHED_PRISMATIC_STONE_STAIRS.get())
            .getFamily();

    public static final BlockFamily LIGHT_GRAY_PRISMATIC_STONE = stone(GEBlocks.LIGHT_GRAY_PRISMATIC_STONE.get())
            .slab(GEBlocks.LIGHT_GRAY_PRISMATIC_STONE_SLAB.get())
            .wall(GEBlocks.LIGHT_GRAY_PRISMATIC_STONE_WALL.get())
            .stairs(GEBlocks.LIGHT_GRAY_PRISMATIC_STONE_STAIRS.get())
            .polished(GEBlocks.LIGHT_GRAY_POLISHED_PRISMATIC_STONE.get())
            .getFamily();

    public static final BlockFamily LIGHT_GRAY_POLISHED_PRISMATIC_STONE = stone(GEBlocks.LIGHT_GRAY_POLISHED_PRISMATIC_STONE.get())
            .slab(GEBlocks.LIGHT_GRAY_POLISHED_PRISMATIC_STONE_SLAB.get())
            .wall(GEBlocks.LIGHT_GRAY_POLISHED_PRISMATIC_STONE_WALL.get())
            .stairs(GEBlocks.LIGHT_GRAY_POLISHED_PRISMATIC_STONE_STAIRS.get())
            .getFamily();

    public static final BlockFamily CYAN_PRISMATIC_STONE = stone(GEBlocks.CYAN_PRISMATIC_STONE.get())
            .slab(GEBlocks.CYAN_PRISMATIC_STONE_SLAB.get())
            .wall(GEBlocks.CYAN_PRISMATIC_STONE_WALL.get())
            .stairs(GEBlocks.CYAN_PRISMATIC_STONE_STAIRS.get())
            .polished(GEBlocks.CYAN_POLISHED_PRISMATIC_STONE.get())
            .getFamily();

    public static final BlockFamily CYAN_POLISHED_PRISMATIC_STONE = stone(GEBlocks.CYAN_POLISHED_PRISMATIC_STONE.get())
            .slab(GEBlocks.CYAN_POLISHED_PRISMATIC_STONE_SLAB.get())
            .wall(GEBlocks.CYAN_POLISHED_PRISMATIC_STONE_WALL.get())
            .stairs(GEBlocks.CYAN_POLISHED_PRISMATIC_STONE_STAIRS.get())
            .getFamily();

    public static final BlockFamily PURPLE_PRISMATIC_STONE = stone(GEBlocks.PURPLE_PRISMATIC_STONE.get())
            .slab(GEBlocks.PURPLE_PRISMATIC_STONE_SLAB.get())
            .wall(GEBlocks.PURPLE_PRISMATIC_STONE_WALL.get())
            .stairs(GEBlocks.PURPLE_PRISMATIC_STONE_STAIRS.get())
            .polished(GEBlocks.PURPLE_POLISHED_PRISMATIC_STONE.get())
            .getFamily();

    public static final BlockFamily PURPLE_POLISHED_PRISMATIC_STONE = stone(GEBlocks.PURPLE_POLISHED_PRISMATIC_STONE.get())
            .slab(GEBlocks.PURPLE_POLISHED_PRISMATIC_STONE_SLAB.get())
            .wall(GEBlocks.PURPLE_POLISHED_PRISMATIC_STONE_WALL.get())
            .stairs(GEBlocks.PURPLE_POLISHED_PRISMATIC_STONE_STAIRS.get())
            .getFamily();

    public static final BlockFamily BLUE_PRISMATIC_STONE = stone(GEBlocks.BLUE_PRISMATIC_STONE.get())
            .slab(GEBlocks.BLUE_PRISMATIC_STONE_SLAB.get())
            .wall(GEBlocks.BLUE_PRISMATIC_STONE_WALL.get())
            .stairs(GEBlocks.BLUE_PRISMATIC_STONE_STAIRS.get())
            .polished(GEBlocks.BLUE_POLISHED_PRISMATIC_STONE.get())
            .getFamily();

    public static final BlockFamily BLUE_POLISHED_PRISMATIC_STONE = stone(GEBlocks.BLUE_POLISHED_PRISMATIC_STONE.get())
            .slab(GEBlocks.BLUE_POLISHED_PRISMATIC_STONE_SLAB.get())
            .wall(GEBlocks.BLUE_POLISHED_PRISMATIC_STONE_WALL.get())
            .stairs(GEBlocks.BLUE_POLISHED_PRISMATIC_STONE_STAIRS.get())
            .getFamily();

    public static final BlockFamily BROWN_PRISMATIC_STONE = stone(GEBlocks.BROWN_PRISMATIC_STONE.get())
            .slab(GEBlocks.BROWN_PRISMATIC_STONE_SLAB.get())
            .wall(GEBlocks.BROWN_PRISMATIC_STONE_WALL.get())
            .stairs(GEBlocks.BROWN_PRISMATIC_STONE_STAIRS.get())
            .polished(GEBlocks.BROWN_POLISHED_PRISMATIC_STONE.get())
            .getFamily();

    public static final BlockFamily BROWN_POLISHED_PRISMATIC_STONE = stone(GEBlocks.BROWN_POLISHED_PRISMATIC_STONE.get())
            .slab(GEBlocks.BROWN_POLISHED_PRISMATIC_STONE_SLAB.get())
            .wall(GEBlocks.BROWN_POLISHED_PRISMATIC_STONE_WALL.get())
            .stairs(GEBlocks.BROWN_POLISHED_PRISMATIC_STONE_STAIRS.get())
            .getFamily();

    public static final BlockFamily GREEN_PRISMATIC_STONE = stone(GEBlocks.GREEN_PRISMATIC_STONE.get())
            .slab(GEBlocks.GREEN_PRISMATIC_STONE_SLAB.get())
            .wall(GEBlocks.GREEN_PRISMATIC_STONE_WALL.get())
            .stairs(GEBlocks.GREEN_PRISMATIC_STONE_STAIRS.get())
            .polished(GEBlocks.GREEN_POLISHED_PRISMATIC_STONE.get())
            .getFamily();

    public static final BlockFamily GREEN_POLISHED_PRISMATIC_STONE = stone(GEBlocks.GREEN_POLISHED_PRISMATIC_STONE.get())
            .slab(GEBlocks.GREEN_POLISHED_PRISMATIC_STONE_SLAB.get())
            .wall(GEBlocks.GREEN_POLISHED_PRISMATIC_STONE_WALL.get())
            .stairs(GEBlocks.GREEN_POLISHED_PRISMATIC_STONE_STAIRS.get())
            .getFamily();

    public static final BlockFamily RED_PRISMATIC_STONE = stone(GEBlocks.RED_PRISMATIC_STONE.get())
            .slab(GEBlocks.RED_PRISMATIC_STONE_SLAB.get())
            .wall(GEBlocks.RED_PRISMATIC_STONE_WALL.get())
            .stairs(GEBlocks.RED_PRISMATIC_STONE_STAIRS.get())
            .polished(GEBlocks.RED_POLISHED_PRISMATIC_STONE.get())
            .getFamily();

    public static final BlockFamily RED_POLISHED_PRISMATIC_STONE = stone(GEBlocks.RED_POLISHED_PRISMATIC_STONE.get())
            .slab(GEBlocks.RED_POLISHED_PRISMATIC_STONE_SLAB.get())
            .wall(GEBlocks.RED_POLISHED_PRISMATIC_STONE_WALL.get())
            .stairs(GEBlocks.RED_POLISHED_PRISMATIC_STONE_STAIRS.get())
            .getFamily();

    public static final BlockFamily BLACK_PRISMATIC_STONE = stone(GEBlocks.BLACK_PRISMATIC_STONE.get())
            .slab(GEBlocks.BLACK_PRISMATIC_STONE_SLAB.get())
            .wall(GEBlocks.BLACK_PRISMATIC_STONE_WALL.get())
            .stairs(GEBlocks.BLACK_PRISMATIC_STONE_STAIRS.get())
            .polished(GEBlocks.BLACK_POLISHED_PRISMATIC_STONE.get())
            .getFamily();

    public static final BlockFamily BLACK_POLISHED_PRISMATIC_STONE = stone(GEBlocks.BLACK_POLISHED_PRISMATIC_STONE.get())
            .slab(GEBlocks.BLACK_POLISHED_PRISMATIC_STONE_SLAB.get())
            .wall(GEBlocks.BLACK_POLISHED_PRISMATIC_STONE_WALL.get())
            .stairs(GEBlocks.BLACK_POLISHED_PRISMATIC_STONE_STAIRS.get())
            .getFamily();


    public static BlockFamily.Builder stone(Block baseBlock) {
        BlockFamily.Builder builder = create(baseBlock);
        BlockFamily family = STONE.put(baseBlock, builder.getFamily());
        if (family != null) {
            throw new IllegalStateException("Duplicate stone type definition for " + BuiltInRegistries.BLOCK.getKey(baseBlock));
        } else {
            return builder;
        }
    }

    public static BlockFamily.Builder create(Block baseBlock) {
        BlockFamily.Builder builder = new BlockFamily.Builder(baseBlock);
        BlockFamily family = FAMILIES.put(baseBlock, builder.getFamily());
        if (family != null) {
            throw new IllegalStateException("Duplicate family definition for " + BuiltInRegistries.BLOCK.getKey(baseBlock));
        } else {
            return builder;
        }
    }

    public static Stream<BlockFamily> getAllFamilies() {
        return FAMILIES.values().stream();
    }

    public static Stream<BlockFamily> getStoneFamilies() {
        return STONE.values().stream();
    }
}
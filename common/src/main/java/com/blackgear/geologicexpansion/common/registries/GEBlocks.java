package com.blackgear.geologicexpansion.common.registries;

import com.blackgear.geologicexpansion.common.block.GeyserBlock;
import com.blackgear.geologicexpansion.common.block.OvergrowthBlock;
import com.blackgear.geologicexpansion.core.GeologicExpansion;
import com.blackgear.geologicexpansion.core.platform.CoreRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.function.Function;
import java.util.function.Supplier;

public class GEBlocks {
    public static final CoreRegistry<Block> BLOCKS = CoreRegistry.create(BuiltInRegistries.BLOCK, GeologicExpansion.MOD_ID);

    // ========== OVERGROWTH ===========================================================================================
    public static final Supplier<Block> OVERGROWTH = create(
            "overgrowth",
            () -> new OvergrowthBlock(
                    Properties.of()
                            .mapColor(MapColor.PLANT)
                            .replaceable()
                            .noCollission()
                            .strength(0.2F)
                            .sound(SoundType.VINE)
                            .ignitedByLava()
                            .pushReaction(PushReaction.DESTROY)
                            .instabreak()
                            .noOcclusion()
                            .sound(SoundType.MOSS_CARPET)
            )
    );

    // ========== LIMESTONE ============================================================================================
    public static final Supplier<Block> LIMESTONE = create(
            "limestone",
            () -> new Block(
                    Properties.of()
                            .mapColor(MapColor.SAND)
                            .requiresCorrectToolForDrops()
                            .strength(1.5F, 6.0F)
            )
    );
    public static final Supplier<Block> LIMESTONE_SLAB = create(
            "limestone_slab",
            () -> new SlabBlock(
                    Properties.copy(LIMESTONE.get())
            )
    );
    public static final Supplier<Block> LIMESTONE_STAIRS = create(
            "limestone_stairs",
            () -> new StairBlock(
                    LIMESTONE.get().defaultBlockState(),
                    Properties.copy(LIMESTONE.get())
            )
    );
    public static final Supplier<Block> LIMESTONE_WALL = create(
            "limestone_wall",
            () -> new WallBlock(
                    Properties.copy(LIMESTONE.get())
            )
    );
    public static final Supplier<Block> POLISHED_LIMESTONE = create(
            "polished_limestone",
            () -> new Block(
                    Properties.of()
                            .mapColor(MapColor.SAND)
                            .requiresCorrectToolForDrops()
                            .strength(1.5F, 6.0F)
            )
    );
    public static final Supplier<Block> POLISHED_LIMESTONE_SLAB = create(
            "polished_limestone_slab",
            () -> new SlabBlock(
                    Properties.copy(POLISHED_LIMESTONE.get())
            )
    );
    public static final Supplier<Block> POLISHED_LIMESTONE_STAIRS = create(
            "polished_limestone_stairs",
            () -> new StairBlock(
                    POLISHED_LIMESTONE.get().defaultBlockState(),
                    Properties.copy(POLISHED_LIMESTONE.get())
            )
    );
    public static final Supplier<Block> POLISHED_LIMESTONE_WALL = create(
            "polished_limestone_wall",
            () -> new WallBlock(
                    Properties.copy(POLISHED_LIMESTONE.get())
            )
    );

    // ========== GEYSER ===============================================================================================
    public static final Supplier<Block> GEYSER = create(
            "geyser",
            () -> new GeyserBlock(
                    Properties.of()
                            .requiresCorrectToolForDrops()
                            .sound(SoundType.TUFF)
                            .strength(1.5F, 6.0F)
            )
    );

    // ========== PRISMATIC STONE ======================================================================================
    public static final Supplier<Block> PRISMATIC_STONE = create("prismatic_stone", () -> new Block(Properties.of().requiresCorrectToolForDrops().sound(SoundType.TUFF).strength(1.5F, 6.0F)));
    public static final Supplier<Block> PRISMATIC_STONE_SLAB = create("prismatic_stone_slab", () -> new SlabBlock(Properties.copy(PRISMATIC_STONE.get()).strength(2.0F, 6.0F)));
    public static final Supplier<Block> PRISMATIC_STONE_STAIRS = create("prismatic_stone_stairs", () -> new StairBlock(PRISMATIC_STONE.get().defaultBlockState(), Properties.copy(PRISMATIC_STONE.get())));
    public static final Supplier<Block> PRISMATIC_STONE_WALL = create("prismatic_stone_wall", () -> new WallBlock(Properties.copy(PRISMATIC_STONE.get())));
    public static final Supplier<Block> POLISHED_PRISMATIC_STONE = create("polished_prismatic_stone", () -> new Block(Properties.of().requiresCorrectToolForDrops().sound(SoundType.TUFF).strength(2.0F, 6.0F)));
    public static final Supplier<Block> POLISHED_PRISMATIC_STONE_SLAB = create("polished_prismatic_stone_slab", () -> new SlabBlock(Properties.copy(POLISHED_PRISMATIC_STONE.get()).strength(2.0F, 6.0F)));
    public static final Supplier<Block> POLISHED_PRISMATIC_STONE_STAIRS = create("polished_prismatic_stone_stairs", () -> new StairBlock(POLISHED_PRISMATIC_STONE.get().defaultBlockState(), Properties.copy(POLISHED_PRISMATIC_STONE.get())));
    public static final Supplier<Block> POLISHED_PRISMATIC_STONE_WALL = create("polished_prismatic_stone_wall", () -> new WallBlock(Properties.copy(POLISHED_PRISMATIC_STONE.get())));

    public static final Supplier<Block> WHITE_PRISMATIC_STONE = create("white_prismatic_stone", () -> new Block(Properties.copy(PRISMATIC_STONE.get()).mapColor(MapColor.TERRACOTTA_WHITE)));
    public static final Supplier<Block> WHITE_PRISMATIC_STONE_SLAB = create("white_prismatic_stone_slab", () -> new SlabBlock(Properties.copy(WHITE_PRISMATIC_STONE.get()).strength(2.0F, 6.0F)));
    public static final Supplier<Block> WHITE_PRISMATIC_STONE_STAIRS = create("white_prismatic_stone_stairs", () -> new StairBlock(WHITE_PRISMATIC_STONE.get().defaultBlockState(), Properties.copy(WHITE_PRISMATIC_STONE.get())));
    public static final Supplier<Block> WHITE_PRISMATIC_STONE_WALL = create("white_prismatic_stone_wall", () -> new WallBlock(Properties.copy(WHITE_PRISMATIC_STONE.get())));
    public static final Supplier<Block> WHITE_POLISHED_PRISMATIC_STONE = create("white_polished_prismatic_stone", () -> new Block(Properties.copy(POLISHED_PRISMATIC_STONE.get()).mapColor(MapColor.TERRACOTTA_WHITE)));
    public static final Supplier<Block> WHITE_POLISHED_PRISMATIC_STONE_SLAB = create("white_polished_prismatic_stone_slab", () -> new SlabBlock(Properties.copy(WHITE_POLISHED_PRISMATIC_STONE.get()).strength(2.0F, 6.0F)));
    public static final Supplier<Block> WHITE_POLISHED_PRISMATIC_STONE_STAIRS = create("white_polished_prismatic_stone_stairs", () -> new StairBlock(WHITE_POLISHED_PRISMATIC_STONE.get().defaultBlockState(), Properties.copy(WHITE_POLISHED_PRISMATIC_STONE.get())));
    public static final Supplier<Block> WHITE_POLISHED_PRISMATIC_STONE_WALL = create("white_polished_prismatic_stone_wall", () -> new WallBlock(Properties.copy(WHITE_POLISHED_PRISMATIC_STONE.get())));

    public static final Supplier<Block> ORANGE_PRISMATIC_STONE = create("orange_prismatic_stone", () -> new Block(Properties.copy(PRISMATIC_STONE.get()).mapColor(MapColor.COLOR_ORANGE)));
    public static final Supplier<Block> ORANGE_PRISMATIC_STONE_SLAB = create("orange_prismatic_stone_slab", () -> new SlabBlock(Properties.copy(ORANGE_PRISMATIC_STONE.get()).strength(2.0F, 6.0F)));
    public static final Supplier<Block> ORANGE_PRISMATIC_STONE_STAIRS = create("orange_prismatic_stone_stairs", () -> new StairBlock(ORANGE_PRISMATIC_STONE.get().defaultBlockState(), Properties.copy(ORANGE_PRISMATIC_STONE.get())));
    public static final Supplier<Block> ORANGE_PRISMATIC_STONE_WALL = create("orange_prismatic_stone_wall", () -> new WallBlock(Properties.copy(ORANGE_PRISMATIC_STONE.get())));
    public static final Supplier<Block> ORANGE_POLISHED_PRISMATIC_STONE = create("orange_polished_prismatic_stone", () -> new Block(Properties.copy(POLISHED_PRISMATIC_STONE.get()).mapColor(MapColor.COLOR_ORANGE)));
    public static final Supplier<Block> ORANGE_POLISHED_PRISMATIC_STONE_SLAB = create("orange_polished_prismatic_stone_slab", () -> new SlabBlock(Properties.copy(ORANGE_POLISHED_PRISMATIC_STONE.get()).strength(2.0F, 6.0F)));
    public static final Supplier<Block> ORANGE_POLISHED_PRISMATIC_STONE_STAIRS = create("orange_polished_prismatic_stone_stairs", () -> new StairBlock(ORANGE_POLISHED_PRISMATIC_STONE.get().defaultBlockState(), Properties.copy(ORANGE_POLISHED_PRISMATIC_STONE.get())));
    public static final Supplier<Block> ORANGE_POLISHED_PRISMATIC_STONE_WALL = create("orange_polished_prismatic_stone_wall", () -> new WallBlock(Properties.copy(ORANGE_POLISHED_PRISMATIC_STONE.get())));

    public static final Supplier<Block> MAGENTA_PRISMATIC_STONE = create("magenta_prismatic_stone", () -> new Block(Properties.copy(PRISMATIC_STONE.get()).mapColor(MapColor.COLOR_MAGENTA)));
    public static final Supplier<Block> MAGENTA_PRISMATIC_STONE_SLAB = create("magenta_prismatic_stone_slab", () -> new SlabBlock(Properties.copy(MAGENTA_PRISMATIC_STONE.get()).strength(2.0F, 6.0F)));
    public static final Supplier<Block> MAGENTA_PRISMATIC_STONE_STAIRS = create("magenta_prismatic_stone_stairs", () -> new StairBlock(MAGENTA_PRISMATIC_STONE.get().defaultBlockState(), Properties.copy(MAGENTA_PRISMATIC_STONE.get())));
    public static final Supplier<Block> MAGENTA_PRISMATIC_STONE_WALL = create("magenta_prismatic_stone_wall", () -> new WallBlock(Properties.copy(MAGENTA_PRISMATIC_STONE.get())));
    public static final Supplier<Block> MAGENTA_POLISHED_PRISMATIC_STONE = create("magenta_polished_prismatic_stone", () -> new Block(Properties.copy(POLISHED_PRISMATIC_STONE.get()).mapColor(MapColor.COLOR_MAGENTA)));
    public static final Supplier<Block> MAGENTA_POLISHED_PRISMATIC_STONE_SLAB = create("magenta_polished_prismatic_stone_slab", () -> new SlabBlock(Properties.copy(MAGENTA_POLISHED_PRISMATIC_STONE.get()).strength(2.0F, 6.0F)));
    public static final Supplier<Block> MAGENTA_POLISHED_PRISMATIC_STONE_STAIRS = create("magenta_polished_prismatic_stone_stairs", () -> new StairBlock(MAGENTA_POLISHED_PRISMATIC_STONE.get().defaultBlockState(), Properties.copy(MAGENTA_POLISHED_PRISMATIC_STONE.get())));
    public static final Supplier<Block> MAGENTA_POLISHED_PRISMATIC_STONE_WALL = create("magenta_polished_prismatic_stone_wall", () -> new WallBlock(Properties.copy(MAGENTA_POLISHED_PRISMATIC_STONE.get())));

    public static final Supplier<Block> LIGHT_BLUE_PRISMATIC_STONE = create("light_blue_prismatic_stone", () -> new Block(Properties.copy(PRISMATIC_STONE.get()).mapColor(MapColor.COLOR_LIGHT_BLUE)));
    public static final Supplier<Block> LIGHT_BLUE_PRISMATIC_STONE_SLAB = create("light_blue_prismatic_stone_slab", () -> new SlabBlock(Properties.copy(LIGHT_BLUE_PRISMATIC_STONE.get()).strength(2.0F, 6.0F)));
    public static final Supplier<Block> LIGHT_BLUE_PRISMATIC_STONE_STAIRS = create("light_blue_prismatic_stone_stairs", () -> new StairBlock(LIGHT_BLUE_PRISMATIC_STONE.get().defaultBlockState(), Properties.copy(LIGHT_BLUE_PRISMATIC_STONE.get())));
    public static final Supplier<Block> LIGHT_BLUE_PRISMATIC_STONE_WALL = create("light_blue_prismatic_stone_wall", () -> new WallBlock(Properties.copy(LIGHT_BLUE_PRISMATIC_STONE.get())));
    public static final Supplier<Block> LIGHT_BLUE_POLISHED_PRISMATIC_STONE = create("light_blue_polished_prismatic_stone", () -> new Block(Properties.copy(POLISHED_PRISMATIC_STONE.get()).mapColor(MapColor.COLOR_LIGHT_BLUE)));
    public static final Supplier<Block> LIGHT_BLUE_POLISHED_PRISMATIC_STONE_SLAB = create("light_blue_polished_prismatic_stone_slab", () -> new SlabBlock(Properties.copy(LIGHT_BLUE_POLISHED_PRISMATIC_STONE.get()).strength(2.0F, 6.0F)));
    public static final Supplier<Block> LIGHT_BLUE_POLISHED_PRISMATIC_STONE_STAIRS = create("light_blue_polished_prismatic_stone_stairs", () -> new StairBlock(LIGHT_BLUE_POLISHED_PRISMATIC_STONE.get().defaultBlockState(), Properties.copy(LIGHT_BLUE_POLISHED_PRISMATIC_STONE.get())));
    public static final Supplier<Block> LIGHT_BLUE_POLISHED_PRISMATIC_STONE_WALL = create("light_blue_polished_prismatic_stone_wall", () -> new WallBlock(Properties.copy(LIGHT_BLUE_POLISHED_PRISMATIC_STONE.get())));

    public static final Supplier<Block> YELLOW_PRISMATIC_STONE = create("yellow_prismatic_stone", () -> new Block(Properties.copy(PRISMATIC_STONE.get()).mapColor(MapColor.COLOR_YELLOW)));
    public static final Supplier<Block> YELLOW_PRISMATIC_STONE_SLAB = create("yellow_prismatic_stone_slab", () -> new SlabBlock(Properties.copy(YELLOW_PRISMATIC_STONE.get()).strength(2.0F, 6.0F)));
    public static final Supplier<Block> YELLOW_PRISMATIC_STONE_STAIRS = create("yellow_prismatic_stone_stairs", () -> new StairBlock(YELLOW_PRISMATIC_STONE.get().defaultBlockState(), Properties.copy(YELLOW_PRISMATIC_STONE.get())));
    public static final Supplier<Block> YELLOW_PRISMATIC_STONE_WALL = create("yellow_prismatic_stone_wall", () -> new WallBlock(Properties.copy(YELLOW_PRISMATIC_STONE.get())));
    public static final Supplier<Block> YELLOW_POLISHED_PRISMATIC_STONE = create("yellow_polished_prismatic_stone", () -> new Block(Properties.copy(POLISHED_PRISMATIC_STONE.get()).mapColor(MapColor.COLOR_YELLOW)));
    public static final Supplier<Block> YELLOW_POLISHED_PRISMATIC_STONE_SLAB = create("yellow_polished_prismatic_stone_slab", () -> new SlabBlock(Properties.copy(YELLOW_POLISHED_PRISMATIC_STONE.get()).strength(2.0F, 6.0F)));
    public static final Supplier<Block> YELLOW_POLISHED_PRISMATIC_STONE_STAIRS = create("yellow_polished_prismatic_stone_stairs", () -> new StairBlock(YELLOW_POLISHED_PRISMATIC_STONE.get().defaultBlockState(), Properties.copy(YELLOW_POLISHED_PRISMATIC_STONE.get())));
    public static final Supplier<Block> YELLOW_POLISHED_PRISMATIC_STONE_WALL = create("yellow_polished_prismatic_stone_wall", () -> new WallBlock(Properties.copy(YELLOW_POLISHED_PRISMATIC_STONE.get())));

    public static final Supplier<Block> LIME_PRISMATIC_STONE = create("lime_prismatic_stone", () -> new Block(Properties.copy(PRISMATIC_STONE.get()).mapColor(MapColor.COLOR_LIGHT_GREEN)));
    public static final Supplier<Block> LIME_PRISMATIC_STONE_SLAB = create("lime_prismatic_stone_slab", () -> new SlabBlock(Properties.copy(LIME_PRISMATIC_STONE.get()).strength(2.0F, 6.0F)));
    public static final Supplier<Block> LIME_PRISMATIC_STONE_STAIRS = create("lime_prismatic_stone_stairs", () -> new StairBlock(LIME_PRISMATIC_STONE.get().defaultBlockState(), Properties.copy(LIME_PRISMATIC_STONE.get())));
    public static final Supplier<Block> LIME_PRISMATIC_STONE_WALL = create("lime_prismatic_stone_wall", () -> new WallBlock(Properties.copy(LIME_PRISMATIC_STONE.get())));
    public static final Supplier<Block> LIME_POLISHED_PRISMATIC_STONE = create("lime_polished_prismatic_stone", () -> new Block(Properties.copy(POLISHED_PRISMATIC_STONE.get()).mapColor(MapColor.COLOR_LIGHT_GREEN)));
    public static final Supplier<Block> LIME_POLISHED_PRISMATIC_STONE_SLAB = create("lime_polished_prismatic_stone_slab", () -> new SlabBlock(Properties.copy(LIME_POLISHED_PRISMATIC_STONE.get()).strength(2.0F, 6.0F)));
    public static final Supplier<Block> LIME_POLISHED_PRISMATIC_STONE_STAIRS = create("lime_polished_prismatic_stone_stairs", () -> new StairBlock(LIME_POLISHED_PRISMATIC_STONE.get().defaultBlockState(), Properties.copy(LIME_POLISHED_PRISMATIC_STONE.get())));
    public static final Supplier<Block> LIME_POLISHED_PRISMATIC_STONE_WALL = create("lime_polished_prismatic_stone_wall", () -> new WallBlock(Properties.copy(LIME_POLISHED_PRISMATIC_STONE.get())));

    public static final Supplier<Block> PINK_PRISMATIC_STONE = create("pink_prismatic_stone", () -> new Block(Properties.copy(PRISMATIC_STONE.get()).mapColor(MapColor.COLOR_PINK)));
    public static final Supplier<Block> PINK_PRISMATIC_STONE_SLAB = create("pink_prismatic_stone_slab", () -> new SlabBlock(Properties.copy(PINK_PRISMATIC_STONE.get()).strength(2.0F, 6.0F)));
    public static final Supplier<Block> PINK_PRISMATIC_STONE_STAIRS = create("pink_prismatic_stone_stairs", () -> new StairBlock(PINK_PRISMATIC_STONE.get().defaultBlockState(), Properties.copy(PINK_PRISMATIC_STONE.get())));
    public static final Supplier<Block> PINK_PRISMATIC_STONE_WALL = create("pink_prismatic_stone_wall", () -> new WallBlock(Properties.copy(PINK_PRISMATIC_STONE.get())));
    public static final Supplier<Block> PINK_POLISHED_PRISMATIC_STONE = create("pink_polished_prismatic_stone", () -> new Block(Properties.copy(POLISHED_PRISMATIC_STONE.get()).mapColor(MapColor.COLOR_PINK)));
    public static final Supplier<Block> PINK_POLISHED_PRISMATIC_STONE_SLAB = create("pink_polished_prismatic_stone_slab", () -> new SlabBlock(Properties.copy(PINK_POLISHED_PRISMATIC_STONE.get()).strength(2.0F, 6.0F)));
    public static final Supplier<Block> PINK_POLISHED_PRISMATIC_STONE_STAIRS = create("pink_polished_prismatic_stone_stairs", () -> new StairBlock(PINK_POLISHED_PRISMATIC_STONE.get().defaultBlockState(), Properties.copy(PINK_POLISHED_PRISMATIC_STONE.get())));
    public static final Supplier<Block> PINK_POLISHED_PRISMATIC_STONE_WALL = create("pink_polished_prismatic_stone_wall", () -> new WallBlock(Properties.copy(PINK_POLISHED_PRISMATIC_STONE.get())));

    public static final Supplier<Block> GRAY_PRISMATIC_STONE = create("gray_prismatic_stone", () -> new Block(Properties.copy(PRISMATIC_STONE.get()).mapColor(MapColor.COLOR_GRAY)));
    public static final Supplier<Block> GRAY_PRISMATIC_STONE_SLAB = create("gray_prismatic_stone_slab", () -> new SlabBlock(Properties.copy(GRAY_PRISMATIC_STONE.get()).strength(2.0F, 6.0F)));
    public static final Supplier<Block> GRAY_PRISMATIC_STONE_STAIRS = create("gray_prismatic_stone_stairs", () -> new StairBlock(GRAY_PRISMATIC_STONE.get().defaultBlockState(), Properties.copy(GRAY_PRISMATIC_STONE.get())));
    public static final Supplier<Block> GRAY_PRISMATIC_STONE_WALL = create("gray_prismatic_stone_wall", () -> new WallBlock(Properties.copy(GRAY_PRISMATIC_STONE.get())));
    public static final Supplier<Block> GRAY_POLISHED_PRISMATIC_STONE = create("gray_polished_prismatic_stone", () -> new Block(Properties.copy(POLISHED_PRISMATIC_STONE.get()).mapColor(MapColor.COLOR_GRAY)));
    public static final Supplier<Block> GRAY_POLISHED_PRISMATIC_STONE_SLAB = create("gray_polished_prismatic_stone_slab", () -> new SlabBlock(Properties.copy(GRAY_POLISHED_PRISMATIC_STONE.get()).strength(2.0F, 6.0F)));
    public static final Supplier<Block> GRAY_POLISHED_PRISMATIC_STONE_STAIRS = create("gray_polished_prismatic_stone_stairs", () -> new StairBlock(GRAY_POLISHED_PRISMATIC_STONE.get().defaultBlockState(), Properties.copy(GRAY_POLISHED_PRISMATIC_STONE.get())));
    public static final Supplier<Block> GRAY_POLISHED_PRISMATIC_STONE_WALL = create("gray_polished_prismatic_stone_wall", () -> new WallBlock(Properties.copy(GRAY_POLISHED_PRISMATIC_STONE.get())));

    public static final Supplier<Block> LIGHT_GRAY_PRISMATIC_STONE = create("light_gray_prismatic_stone", () -> new Block(Properties.copy(PRISMATIC_STONE.get()).mapColor(MapColor.COLOR_LIGHT_GRAY)));
    public static final Supplier<Block> LIGHT_GRAY_PRISMATIC_STONE_SLAB = create("light_gray_prismatic_stone_slab", () -> new SlabBlock(Properties.copy(LIGHT_GRAY_PRISMATIC_STONE.get()).strength(2.0F, 6.0F)));
    public static final Supplier<Block> LIGHT_GRAY_PRISMATIC_STONE_STAIRS = create("light_gray_prismatic_stone_stairs", () -> new StairBlock(LIGHT_GRAY_PRISMATIC_STONE.get().defaultBlockState(), Properties.copy(LIGHT_GRAY_PRISMATIC_STONE.get())));
    public static final Supplier<Block> LIGHT_GRAY_PRISMATIC_STONE_WALL = create("light_gray_prismatic_stone_wall", () -> new WallBlock(Properties.copy(LIGHT_GRAY_PRISMATIC_STONE.get())));
    public static final Supplier<Block> LIGHT_GRAY_POLISHED_PRISMATIC_STONE = create("light_gray_polished_prismatic_stone", () -> new Block(Properties.copy(POLISHED_PRISMATIC_STONE.get()).mapColor(MapColor.COLOR_LIGHT_GRAY)));
    public static final Supplier<Block> LIGHT_GRAY_POLISHED_PRISMATIC_STONE_SLAB = create("light_gray_polished_prismatic_stone_slab", () -> new SlabBlock(Properties.copy(LIGHT_GRAY_POLISHED_PRISMATIC_STONE.get()).strength(2.0F, 6.0F)));
    public static final Supplier<Block> LIGHT_GRAY_POLISHED_PRISMATIC_STONE_STAIRS = create("light_gray_polished_prismatic_stone_stairs", () -> new StairBlock(LIGHT_GRAY_POLISHED_PRISMATIC_STONE.get().defaultBlockState(), Properties.copy(LIGHT_GRAY_POLISHED_PRISMATIC_STONE.get())));
    public static final Supplier<Block> LIGHT_GRAY_POLISHED_PRISMATIC_STONE_WALL = create("light_gray_polished_prismatic_stone_wall", () -> new WallBlock(Properties.copy(LIGHT_GRAY_POLISHED_PRISMATIC_STONE.get())));

    public static final Supplier<Block> CYAN_PRISMATIC_STONE = create("cyan_prismatic_stone", () -> new Block(Properties.copy(PRISMATIC_STONE.get()).mapColor(MapColor.COLOR_CYAN)));
    public static final Supplier<Block> CYAN_PRISMATIC_STONE_SLAB = create("cyan_prismatic_stone_slab", () -> new SlabBlock(Properties.copy(CYAN_PRISMATIC_STONE.get()).strength(2.0F, 6.0F)));
    public static final Supplier<Block> CYAN_PRISMATIC_STONE_STAIRS = create("cyan_prismatic_stone_stairs", () -> new StairBlock(CYAN_PRISMATIC_STONE.get().defaultBlockState(), Properties.copy(CYAN_PRISMATIC_STONE.get())));
    public static final Supplier<Block> CYAN_PRISMATIC_STONE_WALL = create("cyan_prismatic_stone_wall", () -> new WallBlock(Properties.copy(CYAN_PRISMATIC_STONE.get())));
    public static final Supplier<Block> CYAN_POLISHED_PRISMATIC_STONE = create("cyan_polished_prismatic_stone", () -> new Block(Properties.copy(POLISHED_PRISMATIC_STONE.get()).mapColor(MapColor.COLOR_CYAN)));
    public static final Supplier<Block> CYAN_POLISHED_PRISMATIC_STONE_SLAB = create("cyan_polished_prismatic_stone_slab", () -> new SlabBlock(Properties.copy(CYAN_POLISHED_PRISMATIC_STONE.get()).strength(2.0F, 6.0F)));
    public static final Supplier<Block> CYAN_POLISHED_PRISMATIC_STONE_STAIRS = create("cyan_polished_prismatic_stone_stairs", () -> new StairBlock(CYAN_POLISHED_PRISMATIC_STONE.get().defaultBlockState(), Properties.copy(CYAN_POLISHED_PRISMATIC_STONE.get())));
    public static final Supplier<Block> CYAN_POLISHED_PRISMATIC_STONE_WALL = create("cyan_polished_prismatic_stone_wall", () -> new WallBlock(Properties.copy(CYAN_POLISHED_PRISMATIC_STONE.get())));

    public static final Supplier<Block> PURPLE_PRISMATIC_STONE = create("purple_prismatic_stone", () -> new Block(Properties.copy(PRISMATIC_STONE.get()).mapColor(MapColor.COLOR_PURPLE)));
    public static final Supplier<Block> PURPLE_PRISMATIC_STONE_SLAB = create("purple_prismatic_stone_slab", () -> new SlabBlock(Properties.copy(PURPLE_PRISMATIC_STONE.get()).strength(2.0F, 6.0F)));
    public static final Supplier<Block> PURPLE_PRISMATIC_STONE_STAIRS = create("purple_prismatic_stone_stairs", () -> new StairBlock(PURPLE_PRISMATIC_STONE.get().defaultBlockState(), Properties.copy(PURPLE_PRISMATIC_STONE.get())));
    public static final Supplier<Block> PURPLE_PRISMATIC_STONE_WALL = create("purple_prismatic_stone_wall", () -> new WallBlock(Properties.copy(PURPLE_PRISMATIC_STONE.get())));
    public static final Supplier<Block> PURPLE_POLISHED_PRISMATIC_STONE = create("purple_polished_prismatic_stone", () -> new Block(Properties.copy(POLISHED_PRISMATIC_STONE.get()).mapColor(MapColor.COLOR_PURPLE)));
    public static final Supplier<Block> PURPLE_POLISHED_PRISMATIC_STONE_SLAB = create("purple_polished_prismatic_stone_slab", () -> new SlabBlock(Properties.copy(PURPLE_POLISHED_PRISMATIC_STONE.get()).strength(2.0F, 6.0F)));
    public static final Supplier<Block> PURPLE_POLISHED_PRISMATIC_STONE_STAIRS = create("purple_polished_prismatic_stone_stairs", () -> new StairBlock(PURPLE_POLISHED_PRISMATIC_STONE.get().defaultBlockState(), Properties.copy(PURPLE_POLISHED_PRISMATIC_STONE.get())));
    public static final Supplier<Block> PURPLE_POLISHED_PRISMATIC_STONE_WALL = create("purple_polished_prismatic_stone_wall", () -> new WallBlock(Properties.copy(PURPLE_POLISHED_PRISMATIC_STONE.get())));

    public static final Supplier<Block> BLUE_PRISMATIC_STONE = create("blue_prismatic_stone", () -> new Block(Properties.copy(PRISMATIC_STONE.get()).mapColor(MapColor.COLOR_BLUE)));
    public static final Supplier<Block> BLUE_PRISMATIC_STONE_SLAB = create("blue_prismatic_stone_slab", () -> new SlabBlock(Properties.copy(BLUE_PRISMATIC_STONE.get()).strength(2.0F, 6.0F)));
    public static final Supplier<Block> BLUE_PRISMATIC_STONE_STAIRS = create("blue_prismatic_stone_stairs", () -> new StairBlock(BLUE_PRISMATIC_STONE.get().defaultBlockState(), Properties.copy(BLUE_PRISMATIC_STONE.get())));
    public static final Supplier<Block> BLUE_PRISMATIC_STONE_WALL = create("blue_prismatic_stone_wall", () -> new WallBlock(Properties.copy(BLUE_PRISMATIC_STONE.get())));
    public static final Supplier<Block> BLUE_POLISHED_PRISMATIC_STONE = create("blue_polished_prismatic_stone", () -> new Block(Properties.copy(POLISHED_PRISMATIC_STONE.get()).mapColor(MapColor.COLOR_BLUE)));
    public static final Supplier<Block> BLUE_POLISHED_PRISMATIC_STONE_SLAB = create("blue_polished_prismatic_stone_slab", () -> new SlabBlock(Properties.copy(BLUE_POLISHED_PRISMATIC_STONE.get()).strength(2.0F, 6.0F)));
    public static final Supplier<Block> BLUE_POLISHED_PRISMATIC_STONE_STAIRS = create("blue_polished_prismatic_stone_stairs", () -> new StairBlock(BLUE_POLISHED_PRISMATIC_STONE.get().defaultBlockState(), Properties.copy(BLUE_POLISHED_PRISMATIC_STONE.get())));
    public static final Supplier<Block> BLUE_POLISHED_PRISMATIC_STONE_WALL = create("blue_polished_prismatic_stone_wall", () -> new WallBlock(Properties.copy(BLUE_POLISHED_PRISMATIC_STONE.get())));

    public static final Supplier<Block> BROWN_PRISMATIC_STONE = create("brown_prismatic_stone", () -> new Block(Properties.copy(PRISMATIC_STONE.get()).mapColor(MapColor.COLOR_BROWN)));
    public static final Supplier<Block> BROWN_PRISMATIC_STONE_SLAB = create("brown_prismatic_stone_slab", () -> new SlabBlock(Properties.copy(BROWN_PRISMATIC_STONE.get()).strength(2.0F, 6.0F)));
    public static final Supplier<Block> BROWN_PRISMATIC_STONE_STAIRS = create("brown_prismatic_stone_stairs", () -> new StairBlock(BROWN_PRISMATIC_STONE.get().defaultBlockState(), Properties.copy(BROWN_PRISMATIC_STONE.get())));
    public static final Supplier<Block> BROWN_PRISMATIC_STONE_WALL = create("brown_prismatic_stone_wall", () -> new WallBlock(Properties.copy(BROWN_PRISMATIC_STONE.get())));
    public static final Supplier<Block> BROWN_POLISHED_PRISMATIC_STONE = create("brown_polished_prismatic_stone", () -> new Block(Properties.copy(POLISHED_PRISMATIC_STONE.get()).mapColor(MapColor.COLOR_BROWN)));
    public static final Supplier<Block> BROWN_POLISHED_PRISMATIC_STONE_SLAB = create("brown_polished_prismatic_stone_slab", () -> new SlabBlock(Properties.copy(BROWN_POLISHED_PRISMATIC_STONE.get()).strength(2.0F, 6.0F)));
    public static final Supplier<Block> BROWN_POLISHED_PRISMATIC_STONE_STAIRS = create("brown_polished_prismatic_stone_stairs", () -> new StairBlock(BROWN_POLISHED_PRISMATIC_STONE.get().defaultBlockState(), Properties.copy(BROWN_POLISHED_PRISMATIC_STONE.get())));
    public static final Supplier<Block> BROWN_POLISHED_PRISMATIC_STONE_WALL = create("brown_polished_prismatic_stone_wall", () -> new WallBlock(Properties.copy(BROWN_POLISHED_PRISMATIC_STONE.get())));

    public static final Supplier<Block> GREEN_PRISMATIC_STONE = create("green_prismatic_stone", () -> new Block(Properties.copy(PRISMATIC_STONE.get()).mapColor(MapColor.COLOR_GREEN)));
    public static final Supplier<Block> GREEN_PRISMATIC_STONE_SLAB = create("green_prismatic_stone_slab", () -> new SlabBlock(Properties.copy(GREEN_PRISMATIC_STONE.get()).strength(2.0F, 6.0F)));
    public static final Supplier<Block> GREEN_PRISMATIC_STONE_STAIRS = create("green_prismatic_stone_stairs", () -> new StairBlock(GREEN_PRISMATIC_STONE.get().defaultBlockState(), Properties.copy(GREEN_PRISMATIC_STONE.get())));
    public static final Supplier<Block> GREEN_PRISMATIC_STONE_WALL = create("green_prismatic_stone_wall", () -> new WallBlock(Properties.copy(GREEN_PRISMATIC_STONE.get())));
    public static final Supplier<Block> GREEN_POLISHED_PRISMATIC_STONE = create("green_polished_prismatic_stone", () -> new Block(Properties.copy(POLISHED_PRISMATIC_STONE.get()).mapColor(MapColor.COLOR_GREEN)));
    public static final Supplier<Block> GREEN_POLISHED_PRISMATIC_STONE_SLAB = create("green_polished_prismatic_stone_slab", () -> new SlabBlock(Properties.copy(GREEN_POLISHED_PRISMATIC_STONE.get()).strength(2.0F, 6.0F)));
    public static final Supplier<Block> GREEN_POLISHED_PRISMATIC_STONE_STAIRS = create("green_polished_prismatic_stone_stairs", () -> new StairBlock(GREEN_POLISHED_PRISMATIC_STONE.get().defaultBlockState(), Properties.copy(GREEN_POLISHED_PRISMATIC_STONE.get())));
    public static final Supplier<Block> GREEN_POLISHED_PRISMATIC_STONE_WALL = create("green_polished_prismatic_stone_wall", () -> new WallBlock(Properties.copy(GREEN_POLISHED_PRISMATIC_STONE.get())));

    public static final Supplier<Block> RED_PRISMATIC_STONE = create("red_prismatic_stone", () -> new Block(Properties.copy(PRISMATIC_STONE.get()).mapColor(MapColor.COLOR_RED)));
    public static final Supplier<Block> RED_PRISMATIC_STONE_SLAB = create("red_prismatic_stone_slab", () -> new SlabBlock(Properties.copy(RED_PRISMATIC_STONE.get()).strength(2.0F, 6.0F)));
    public static final Supplier<Block> RED_PRISMATIC_STONE_STAIRS = create("red_prismatic_stone_stairs", () -> new StairBlock(RED_PRISMATIC_STONE.get().defaultBlockState(), Properties.copy(RED_PRISMATIC_STONE.get())));
    public static final Supplier<Block> RED_PRISMATIC_STONE_WALL = create("red_prismatic_stone_wall", () -> new WallBlock(Properties.copy(RED_PRISMATIC_STONE.get())));
    public static final Supplier<Block> RED_POLISHED_PRISMATIC_STONE = create("red_polished_prismatic_stone", () -> new Block(Properties.copy(POLISHED_PRISMATIC_STONE.get()).mapColor(MapColor.COLOR_RED)));
    public static final Supplier<Block> RED_POLISHED_PRISMATIC_STONE_SLAB = create("red_polished_prismatic_stone_slab", () -> new SlabBlock(Properties.copy(RED_POLISHED_PRISMATIC_STONE.get()).strength(2.0F, 6.0F)));
    public static final Supplier<Block> RED_POLISHED_PRISMATIC_STONE_STAIRS = create("red_polished_prismatic_stone_stairs", () -> new StairBlock(RED_POLISHED_PRISMATIC_STONE.get().defaultBlockState(), Properties.copy(RED_POLISHED_PRISMATIC_STONE.get())));
    public static final Supplier<Block> RED_POLISHED_PRISMATIC_STONE_WALL = create("red_polished_prismatic_stone_wall", () -> new WallBlock(Properties.copy(RED_POLISHED_PRISMATIC_STONE.get())));

    public static final Supplier<Block> BLACK_PRISMATIC_STONE = create("black_prismatic_stone", () -> new Block(Properties.copy(PRISMATIC_STONE.get()).mapColor(MapColor.COLOR_BLACK)));
    public static final Supplier<Block> BLACK_PRISMATIC_STONE_SLAB = create("black_prismatic_stone_slab", () -> new SlabBlock(Properties.copy(BLACK_PRISMATIC_STONE.get()).strength(2.0F, 6.0F)));
    public static final Supplier<Block> BLACK_PRISMATIC_STONE_STAIRS = create("black_prismatic_stone_stairs", () -> new StairBlock(BLACK_PRISMATIC_STONE.get().defaultBlockState(), Properties.copy(BLACK_PRISMATIC_STONE.get())));
    public static final Supplier<Block> BLACK_PRISMATIC_STONE_WALL = create("black_prismatic_stone_wall", () -> new WallBlock(Properties.copy(BLACK_PRISMATIC_STONE.get())));
    public static final Supplier<Block> BLACK_POLISHED_PRISMATIC_STONE = create("black_polished_prismatic_stone", () -> new Block(Properties.copy(POLISHED_PRISMATIC_STONE.get()).mapColor(MapColor.COLOR_BLACK)));
    public static final Supplier<Block> BLACK_POLISHED_PRISMATIC_STONE_SLAB = create("black_polished_prismatic_stone_slab", () -> new SlabBlock(Properties.copy(BLACK_POLISHED_PRISMATIC_STONE.get()).strength(2.0F, 6.0F)));
    public static final Supplier<Block> BLACK_POLISHED_PRISMATIC_STONE_STAIRS = create("black_polished_prismatic_stone_stairs", () -> new StairBlock(BLACK_POLISHED_PRISMATIC_STONE.get().defaultBlockState(), Properties.copy(BLACK_POLISHED_PRISMATIC_STONE.get())));
    public static final Supplier<Block> BLACK_POLISHED_PRISMATIC_STONE_WALL = create("black_polished_prismatic_stone_wall", () -> new WallBlock(Properties.copy(BLACK_POLISHED_PRISMATIC_STONE.get())));

    private static <T extends Block> Supplier<T> create(String key, Supplier<T> block) {
        return create(key, block, entry -> new BlockItem(entry.get(), new Item.Properties()));
    }

    private static <T extends Block> Supplier<T> create(String key, Supplier<T> block, Function<Supplier<T>, Item> item) {
        Supplier<T> entry = createDefault(key, block);
        GEItems.ITEMS.register(key, () -> item.apply(entry));
        return entry;
    }

    private static <T extends Block> Supplier<T> createDefault(String key, Supplier<T> block) {
        return BLOCKS.register(key, block);
    }
}
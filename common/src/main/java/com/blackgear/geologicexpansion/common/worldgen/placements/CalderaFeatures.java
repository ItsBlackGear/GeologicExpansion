package com.blackgear.geologicexpansion.common.worldgen.placements;

import com.blackgear.geologicexpansion.common.registries.GEBlocks;
import com.blackgear.geologicexpansion.core.GeologicExpansion;
import com.blackgear.geologicexpansion.core.platform.common.WorldGenRegistry;
import net.minecraft.core.Holder;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.DiskConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RuleBasedBlockStateProvider;

public class CalderaFeatures {
    public static final WorldGenRegistry FEATURES = WorldGenRegistry.of(GeologicExpansion.MOD_ID);

    // ========== PRISMATIC LAKE =======================================================================================
    public static final Holder<ConfiguredFeature<SimpleBlockConfiguration, ?>> PRISMATIC_LAKE = FEATURES.create(
            "prismatic_lake",
            Feature.SIMPLE_BLOCK,
            new SimpleBlockConfiguration(
                    BlockStateProvider.simple(Blocks.WATER)
            )
    );

    // ========== PRISMATIC BORDER COLORS ==============================================================================
    public static final Holder<ConfiguredFeature<DiskConfiguration, ?>> PRISMATIC_BORDER_PURPLE = FEATURES.create(
            "prismatic_border_purple",
            Feature.DISK,
            new DiskConfiguration(
                    RuleBasedBlockStateProvider.simple(GEBlocks.PURPLE_PRISMATIC_STONE.get()),
                    BlockPredicate.matchesBlocks(
                            GEBlocks.PRISMATIC_STONE.get(),
                            Blocks.COARSE_DIRT,
                            Blocks.DIRT,
                            Blocks.GRASS_BLOCK
                    ),
                    UniformInt.of(4, 8),
                    4
            )
    );

    public static final Holder<ConfiguredFeature<DiskConfiguration, ?>> PRISMATIC_BORDER_BROWN = FEATURES.create(
            "prismatic_border_brown",
            Feature.DISK,
            new DiskConfiguration(
                    RuleBasedBlockStateProvider.simple(GEBlocks.BROWN_PRISMATIC_STONE.get()),
                    BlockPredicate.matchesBlocks(
                            GEBlocks.PURPLE_PRISMATIC_STONE.get(),
                            GEBlocks.PRISMATIC_STONE.get(),
                            Blocks.COARSE_DIRT,
                            Blocks.DIRT,
                            Blocks.GRASS_BLOCK
                    ),
                    UniformInt.of(4, 6),
                    3
            )
    );

    public static final Holder<ConfiguredFeature<DiskConfiguration, ?>> PRISMATIC_BORDER_RED = FEATURES.create(
            "prismatic_border_red",
            Feature.DISK,
            new DiskConfiguration(
                    RuleBasedBlockStateProvider.simple(GEBlocks.RED_PRISMATIC_STONE.get()),
                    BlockPredicate.matchesBlocks(
                            GEBlocks.PURPLE_PRISMATIC_STONE.get(),
                            GEBlocks.BROWN_PRISMATIC_STONE.get(),
                            GEBlocks.PRISMATIC_STONE.get(),
                            Blocks.COARSE_DIRT,
                            Blocks.DIRT,
                            Blocks.GRASS_BLOCK
                    ),
                    UniformInt.of(3, 4),
                    2
            )
    );

    public static final Holder<ConfiguredFeature<DiskConfiguration, ?>> PRISMATIC_BORDER_ORANGE = FEATURES.create(
            "prismatic_border_orange",
            Feature.DISK,
            new DiskConfiguration(
                    RuleBasedBlockStateProvider.simple(GEBlocks.ORANGE_PRISMATIC_STONE.get()),
                    BlockPredicate.matchesBlocks(
                            GEBlocks.PURPLE_PRISMATIC_STONE.get(),
                            GEBlocks.BROWN_PRISMATIC_STONE.get(),
                            GEBlocks.RED_PRISMATIC_STONE.get(),
                            GEBlocks.PRISMATIC_STONE.get(),
                            Blocks.COARSE_DIRT,
                            Blocks.DIRT,
                            Blocks.GRASS_BLOCK
                    ),
                    UniformInt.of(2, 3),
                    1
            )
    );

    public static final Holder<ConfiguredFeature<DiskConfiguration, ?>> PRISMATIC_BORDER_YELLOW = FEATURES.create(
            "prismatic_border_yellow",
            Feature.DISK,
            new DiskConfiguration(
                    RuleBasedBlockStateProvider.simple(GEBlocks.YELLOW_PRISMATIC_STONE.get()),
                    BlockPredicate.matchesBlocks(
                            GEBlocks.PURPLE_PRISMATIC_STONE.get(),
                            GEBlocks.ORANGE_PRISMATIC_STONE.get(),
                            GEBlocks.RED_PRISMATIC_STONE.get(),
                            GEBlocks.PRISMATIC_STONE.get(),
                            Blocks.COARSE_DIRT,
                            Blocks.DIRT,
                            Blocks.GRASS_BLOCK
                    ),
                    ConstantInt.of(1),
                    0
            )
    );
}
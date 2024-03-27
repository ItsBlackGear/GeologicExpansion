package com.blackgear.geologicexpansion.common.worldgen.placements;

import com.blackgear.geologicexpansion.common.registries.GEBlocks;
import com.blackgear.geologicexpansion.common.registries.worldgen.GEFeatures;
import com.blackgear.geologicexpansion.core.GeologicExpansion;
import com.blackgear.geologicexpansion.core.platform.common.WorldGenRegistry;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RuleBasedBlockStateProvider;
import net.minecraft.world.level.levelgen.placement.CaveSurface;

public class CalderaFeatures {
    public static final WorldGenRegistry FEATURES = WorldGenRegistry.of(GeologicExpansion.MOD_ID);

    public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> PRISMATIC_POND = FEATURES.create(
        "prismatic_pond",
        GEFeatures.POND.get(),
        FeatureConfiguration.NONE
    );

    // ========== GEYSER ===============================================================================================
    public static final Holder<ConfiguredFeature<VegetationPatchConfiguration, ?>> GEYSER_PATCH = FEATURES.create(
        "geyser_patch",
        GEFeatures.SCATTERED_VEGETATION_PATCH.get(),
        new VegetationPatchConfiguration(
            BlockTags.MOSS_REPLACEABLE,
            BlockStateProvider.simple(GEBlocks.GRAY_PRISMATIC_STONE.get()),
            PlacementUtils.inlinePlaced(GEFeatures.GEYSER_PATCH.get(), FeatureConfiguration.NONE),
            CaveSurface.FLOOR,
            UniformInt.of(2, 3),
            0.8F,
            5,
            0.25F,
            UniformInt.of(1, 2),
            0.3F
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

    public static final Holder<ConfiguredFeature<UnderwaterMagmaConfiguration, ?>> MAGMA = FEATURES.create(
            "magma",
            Feature.UNDERWATER_MAGMA,
            new UnderwaterMagmaConfiguration(
                2,
                3,
                0.3F
            )
    );
}
package com.blackgear.geologicexpansion.common.worldgen.placements;

import com.blackgear.geologicexpansion.common.registries.GEBlocks;
import com.blackgear.geologicexpansion.common.registries.worldgen.GEFeatures;
import com.blackgear.geologicexpansion.core.GeologicExpansion;
import com.blackgear.geologicexpansion.core.platform.common.WorldGenRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.DiskConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RuleBasedBlockStateProvider;
import net.minecraft.world.level.levelgen.placement.CaveSurface;

public class CalderaFeatures {
    public static final WorldGenRegistry<ConfiguredFeature<?, ?>> FEATURES = WorldGenRegistry.of(Registries.CONFIGURED_FEATURE, GeologicExpansion.MOD_ID);

    // ========== PRISMATIC LAKE =======================================================================================
    public static final ResourceKey<ConfiguredFeature<?, ?>> PRISMATIC_LAKE = FEATURES.create("prismatic_lake");

    // ========== GEYSER ===============================================================================================
    public static final ResourceKey<ConfiguredFeature<?, ?>> GEYSER_PATCH = FEATURES.create("geyser_patch");

    // ========== PRISMATIC BORDER COLORS ==============================================================================
    public static final ResourceKey<ConfiguredFeature<?, ?>> PRISMATIC_BORDER_PURPLE = FEATURES.create("prismatic_border_purple");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PRISMATIC_BORDER_BROWN = FEATURES.create("prismatic_border_brown");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PRISMATIC_BORDER_RED = FEATURES.create("prismatic_border_red");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PRISMATIC_BORDER_ORANGE = FEATURES.create("prismatic_border_orange");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PRISMATIC_BORDER_YELLOW = FEATURES.create("prismatic_border_yellow");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        // ========== PRISMATIC LAKE ===================================================================================
        FEATURES.register(
                context,
                PRISMATIC_LAKE,
                Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.WATER))
        );

        // ========== GEYSER ===========================================================================================
        FEATURES.register(
                context,
                GEYSER_PATCH,
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

        // ========== PRISMATIC BORDER COLORS ==========================================================================
        FEATURES.register(
                context,
                PRISMATIC_BORDER_PURPLE,
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
        FEATURES.register(
                context,
                PRISMATIC_BORDER_BROWN,
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
        FEATURES.register(
                context,
                PRISMATIC_BORDER_RED,
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
        FEATURES.register(
                context,
                PRISMATIC_BORDER_ORANGE,
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
        FEATURES.register(
                context,
                PRISMATIC_BORDER_YELLOW,
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
}
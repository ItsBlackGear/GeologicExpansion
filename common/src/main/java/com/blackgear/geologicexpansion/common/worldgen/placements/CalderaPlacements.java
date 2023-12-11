package com.blackgear.geologicexpansion.common.worldgen.placements;

import com.blackgear.geologicexpansion.common.registries.GEBlocks;
import com.blackgear.geologicexpansion.core.GeologicExpansion;
import com.blackgear.geologicexpansion.core.platform.common.WorldGenRegistry;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RandomOffsetPlacement;
import net.minecraft.world.level.material.Fluids;

public class CalderaPlacements {
    public static final WorldGenRegistry<PlacedFeature> FEATURES = WorldGenRegistry.of(Registries.PLACED_FEATURE, GeologicExpansion.MOD_ID);

    // ========== PRISMATIC LAKE =======================================================================================
    public static final ResourceKey<PlacedFeature> PRISMATIC_LAKE = FEATURES.create("prismatic_lake");

    // ========= GEYSER ================================================================================================
    public static final ResourceKey<PlacedFeature> GEYSER_PATCH = FEATURES.create("geyser_patch");

    // ========= PRISMATIC BORDER COLORS ===============================================================================
    public static final ResourceKey<PlacedFeature> PRISMATIC_BORDER_PURPLE = FEATURES.create("prismatic_border_purple");
    public static final ResourceKey<PlacedFeature> PRISMATIC_BORDER_BROWN = FEATURES.create("prismatic_border_brown");
    public static final ResourceKey<PlacedFeature> PRISMATIC_BORDER_RED = FEATURES.create("prismatic_border_red");
    public static final ResourceKey<PlacedFeature> PRISMATIC_BORDER_ORANGE = FEATURES.create("prismatic_border_orange");
    public static final ResourceKey<PlacedFeature> PRISMATIC_BORDER_YELLOW = FEATURES.create("prismatic_border_yellow");


    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> holder = context.lookup(Registries.CONFIGURED_FEATURE);
        // ========== PRISMATIC LAKE ===================================================================================
        FEATURES.register(
                context,
                PRISMATIC_LAKE,
                holder.getOrThrow(CalderaFeatures.PRISMATIC_LAKE),
                CountPlacement.of(20),
                CountPlacement.of(30),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                CountPlacement.of(5),
                RandomOffsetPlacement.horizontal(UniformInt.of(-4, 4)),
                EnvironmentScanPlacement.scanningFor(
                        Direction.DOWN,
                        getPrismaticLakeBlockPredicates(),
                        6
                ),
                BiomeFilter.biome()
        );

        // ========= GEYSER ============================================================================================
        FEATURES.register(
                context,
                GEYSER_PATCH,
                holder.getOrThrow(CalderaFeatures.GEYSER_PATCH),
                CountPlacement.of(10),
                InSquarePlacement.spread(),
                PlacementUtils.FULL_RANGE,
                EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 12),
                RandomOffsetPlacement.vertical(ConstantInt.of(1)),
                BiomeFilter.biome()
        );

        // ========= PRISMATIC BORDER COLORS ===========================================================================
        FEATURES.register(
                context,
                PRISMATIC_BORDER_PURPLE,
                holder.getOrThrow(CalderaFeatures.PRISMATIC_BORDER_PURPLE),
                CountPlacement.of(256),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(
                        VerticalAnchor.absolute(61),
                        VerticalAnchor.absolute(63)
                ),
                BlockPredicateFilter.forPredicate(
                        BlockPredicate.matchesFluids(Fluids.WATER)
                ),
                BiomeFilter.biome()
        );
        FEATURES.register(
                context,
                PRISMATIC_BORDER_BROWN,
                holder.getOrThrow(CalderaFeatures.PRISMATIC_BORDER_BROWN),
                CountPlacement.of(228),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(
                        VerticalAnchor.absolute(61),
                        VerticalAnchor.absolute(63)
                ),
                BlockPredicateFilter.forPredicate(
                        BlockPredicate.matchesFluids(Fluids.WATER)
                ),
                BiomeFilter.biome()
        );
        FEATURES.register(
                context,
                PRISMATIC_BORDER_RED,
                holder.getOrThrow(CalderaFeatures.PRISMATIC_BORDER_RED),
                CountPlacement.of(228),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(
                        VerticalAnchor.absolute(61),
                        VerticalAnchor.absolute(63)
                ),
                BlockPredicateFilter.forPredicate(
                        BlockPredicate.matchesFluids(Fluids.WATER)
                ),
                BiomeFilter.biome()
        );
        FEATURES.register(
                context,
                PRISMATIC_BORDER_ORANGE,
                holder.getOrThrow(CalderaFeatures.PRISMATIC_BORDER_ORANGE),
                CountPlacement.of(4),
                CountPlacement.of(82),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(
                        VerticalAnchor.absolute(61),
                        VerticalAnchor.absolute(63)
                ),
                BlockPredicateFilter.forPredicate(
                        BlockPredicate.matchesFluids(Fluids.WATER)
                ),
                BiomeFilter.biome()
        );
        FEATURES.register(
                context,
                PRISMATIC_BORDER_YELLOW,
                holder.getOrThrow(CalderaFeatures.PRISMATIC_BORDER_YELLOW),
                CountPlacement.of(4),
                CountPlacement.of(82),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(
                        VerticalAnchor.absolute(62),
                        VerticalAnchor.absolute(62)
                ),
                BlockPredicateFilter.forPredicate(
                        BlockPredicate.matchesFluids(Fluids.WATER)
                ),
                BiomeFilter.biome()
        );
    }

    private static BlockPredicate getPrismaticLakeBlockPredicates() {
        Block[] borderReplaceables = {
                GEBlocks.ORANGE_PRISMATIC_STONE.get(),
                GEBlocks.BROWN_PRISMATIC_STONE.get(),
                GEBlocks.RED_PRISMATIC_STONE.get(),
                Blocks.AIR,
                Blocks.LAVA
        };

        Block[] lakeBlocks = {
                GEBlocks.YELLOW_PRISMATIC_STONE.get(),
                Blocks.MAGMA_BLOCK
        };

        Block[] floorReplaceables = {
                GEBlocks.YELLOW_PRISMATIC_STONE.get(),
                GEBlocks.ORANGE_PRISMATIC_STONE.get(),
                GEBlocks.PURPLE_PRISMATIC_STONE.get(),
                GEBlocks.BROWN_PRISMATIC_STONE.get(),
                GEBlocks.RED_PRISMATIC_STONE.get(),
                GEBlocks.PRISMATIC_STONE.get(),
                Blocks.LAVA,
                Blocks.GRASS_BLOCK,
                Blocks.DIRT,
                Blocks.COARSE_DIRT,
                Blocks.WATER
        };

        BlockPredicate borderBlockPredicate = BlockPredicate.anyOf(
                Direction.Plane.HORIZONTAL.stream().map(direction -> {
                    return BlockPredicate.matchesBlocks(direction.getNormal(), borderReplaceables);
                }).toArray(BlockPredicate[]::new)
        );

        BlockPredicate floorBlockPredicate = BlockPredicate.anyOf(
                Direction.Plane.HORIZONTAL.stream().map(direction -> {
                    return BlockPredicate.matchesBlocks(direction.getNormal().above(), floorReplaceables);
                }).toArray(BlockPredicate[]::new)
        );

        return BlockPredicate.allOf(
                BlockPredicate.not(borderBlockPredicate), // no border blocks
                BlockPredicate.matchesBlocks(Vec3i.ZERO, lakeBlocks), // lake block
                BlockPredicate.not(floorBlockPredicate) // no floor blocks
        );
    }

}
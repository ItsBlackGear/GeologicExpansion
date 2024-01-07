package com.blackgear.geologicexpansion.common.worldgen.placements;

import com.blackgear.geologicexpansion.core.GeologicExpansion;
import com.blackgear.geologicexpansion.core.platform.common.WorldGenRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.NoiseThresholdCountPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;

public class MapleForestPlacements {
    public static final WorldGenRegistry FEATURES = WorldGenRegistry.of(GeologicExpansion.MOD_ID);

    public static final Holder<PlacedFeature> MAPLE_LEAF_CARPETS = FEATURES.create(
        "maple_leaf_carpets",
        MapleForestFeatures.MAPLE_LEAF_CARPETS,
        NoiseThresholdCountPlacement.of(-0.8, 5, 10),
        InSquarePlacement.spread(),
        PlacementUtils.HEIGHTMAP_TOP_SOLID,
        BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(new BlockPos(0, -1, 0), Blocks.GRASS_BLOCK)),
        BiomeFilter.biome()
    );
    public static final Holder<PlacedFeature> MAPLE_PATCH_PUMPKIN = FEATURES.create(
        "maple_patch_pumpkin",
        VegetationFeatures.PATCH_PUMPKIN,
        RarityFilter.onAverageOnceEvery(50),
        InSquarePlacement.spread(),
        PlacementUtils.HEIGHTMAP,
        BiomeFilter.biome()
    );
}
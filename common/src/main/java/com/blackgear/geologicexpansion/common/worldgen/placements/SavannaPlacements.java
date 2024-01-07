package com.blackgear.geologicexpansion.common.worldgen.placements;

import com.blackgear.geologicexpansion.core.GeologicExpansion;
import com.blackgear.geologicexpansion.core.platform.common.WorldGenRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.List;
import java.util.stream.IntStream;

public class SavannaPlacements {
    public static final WorldGenRegistry FEATURES = WorldGenRegistry.of(GeologicExpansion.MOD_ID);

    public static final Holder<PlacedFeature> SAVANNA_SURFACE = FEATURES.create(
        "savanna_surface",
        SavannaFeatures.SAVANNA_SURFACE,
        CountPlacement.of(20),
        CountPlacement.of(10),
        InSquarePlacement.spread(),
        PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
        BlockPredicateFilter.forPredicate(replaceablePatch()),
        BiomeFilter.biome()
    );

    /**
     * Returns a block predicate that matches a 5x5 patch of replaceable blocks.
     */
    public static BlockPredicate replaceablePatch() {
        List<Vec3i> list = IntStream.rangeClosed(-2, 2)
            .boxed()
            .flatMap(x -> IntStream.rangeClosed(-2, 2).mapToObj(z -> new Vec3i(x, 0, z)))
            .toList();

        return BlockPredicate.allOf(
            list.stream().map(vec -> BlockPredicate.matchesBlocks(vec, Blocks.AIR, Blocks.GRASS, Blocks.TALL_GRASS)).toArray(BlockPredicate[]::new)
        );
    }
}
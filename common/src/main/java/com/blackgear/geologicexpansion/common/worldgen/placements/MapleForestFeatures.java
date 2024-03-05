package com.blackgear.geologicexpansion.common.worldgen.placements;

import com.blackgear.geologicexpansion.common.registries.GEBlocks;
import com.blackgear.geologicexpansion.core.GeologicExpansion;
import com.blackgear.geologicexpansion.core.platform.common.WorldGenRegistry;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;

public class MapleForestFeatures {
    public static final WorldGenRegistry FEATURES = WorldGenRegistry.of(GeologicExpansion.MOD_ID);

    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> MAPLE_LEAF_CARPETS = FEATURES.create(
        "maple_leaf_carpets",
        Feature.FLOWER,
        new RandomPatchConfiguration(
            70,
            6,
            2,
            PlacementUtils.onlyWhenEmpty(
                Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(
                    new WeightedStateProvider(
                        SimpleWeightedRandomList.<BlockState>builder()
                        .add(GEBlocks.RED_MAPLE_LEAF_CARPET.get().defaultBlockState(), 1)
                        .add(GEBlocks.BROWN_MAPLE_LEAF_CARPET.get().defaultBlockState(), 1)
                        .build()
                    )
                )
            )
        )
    );
}
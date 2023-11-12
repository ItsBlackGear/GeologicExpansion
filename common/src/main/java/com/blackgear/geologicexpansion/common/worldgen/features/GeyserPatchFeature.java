package com.blackgear.geologicexpansion.common.worldgen.features;

import com.blackgear.geologicexpansion.common.block.GeyserBlock;
import com.blackgear.geologicexpansion.common.registries.GEBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;

public class GeyserPatchFeature extends Feature<NoneFeatureConfiguration> {
    public GeyserPatchFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        BlockStateProvider provider = new WeightedStateProvider(
                SimpleWeightedRandomList.<BlockState>builder()
                        .add(GEBlocks.GEYSER.get().defaultBlockState(), 50)
                        .add(GEBlocks.GRAY_PRISMATIC_STONE_SLAB.get().defaultBlockState(), 20)
        );
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        BlockPos pos = context.origin();
        BlockState state = provider.getState(context.random(), pos);
        if (state.canSurvive(level, pos)) {
            if (state.getBlock() instanceof GeyserBlock) {
                if (!level.isEmptyBlock(pos.above())) return false;

                if (random.nextBoolean()) {
                    DoublePlantBlock.placeAt(level, state, pos, 2);
                } else if (random.nextBoolean()) {
                    DoublePlantBlock.placeAt(level, state, pos.below(), 2);
                } else {
                    DoublePlantBlock.placeAt(level, state, pos.above(), 2);
                    level.setBlock(pos, GEBlocks.GRAY_PRISMATIC_STONE.get().defaultBlockState(), 2);
                }

            } else {
                level.setBlock(pos, state, 2);
            }

            return true;
        } else {
            return false;
        }
    }
}
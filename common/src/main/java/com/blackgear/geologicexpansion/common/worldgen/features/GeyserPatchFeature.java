package com.blackgear.geologicexpansion.common.worldgen.features;

import com.blackgear.geologicexpansion.common.registries.GEBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class GeyserPatchFeature extends Feature<NoneFeatureConfiguration> {
    public GeyserPatchFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        BlockPos pos = context.origin();
        BlockState geyser = GEBlocks.GEYSER.get().defaultBlockState();
        if (geyser.canSurvive(level, pos)) {
            switch (random.nextInt(3)) {
                case 0 -> DoublePlantBlock.placeAt(level, geyser, pos, 2);
                case 1 -> DoublePlantBlock.placeAt(level, geyser, pos.below(), 2);
                case 2 -> {
                    DoublePlantBlock.placeAt(level, geyser, pos.above(), 2);
                    level.setBlock(pos, GEBlocks.GRAY_PRISMATIC_STONE.get().defaultBlockState(), 2);
                }
            }

            return true;
        }

        return false;
    }
}
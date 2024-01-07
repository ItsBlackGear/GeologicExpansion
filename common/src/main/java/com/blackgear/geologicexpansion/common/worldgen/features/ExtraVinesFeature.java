package com.blackgear.geologicexpansion.common.worldgen.features;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class ExtraVinesFeature extends Feature<NoneFeatureConfiguration> {
    public ExtraVinesFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos pos = context.origin();
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

        IntProvider value = UniformInt.of(1, 5);

        if (level.isEmptyBlock(pos)) {
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                if (VineBlock.isAcceptableNeighbour(level, pos.relative(direction), direction)) {
                    BlockState state = Blocks.VINE.defaultBlockState().setValue(VineBlock.getPropertyForFace(direction), true);

                    for (int i = 0; i <= value.sample(context.random()); i++) {
                        mutable.set(pos).move(Direction.DOWN, i);
                        if (!level.isEmptyBlock(mutable)) {
                            break;
                        }
                        level.setBlock(mutable, state, 2);
                    }

                    return true;
                }
            }
        }

        return false;
    }
}
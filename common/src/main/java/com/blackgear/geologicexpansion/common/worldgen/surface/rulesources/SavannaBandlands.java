package com.blackgear.geologicexpansion.common.worldgen.surface.rulesources;

import com.blackgear.geologicexpansion.core.mixin.access.ContextAccessor;
import com.blackgear.geologicexpansion.core.mixin.access.SurfaceSystemAccessor;
import com.mojang.serialization.MapCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import java.util.Arrays;

public enum SavannaBandlands implements SurfaceRules.RuleSource {
    INSTANCE;

    static final BlockState LIGHT_GRAY_TERRACOTTA = Blocks.LIGHT_GRAY_TERRACOTTA.defaultBlockState();
    static final BlockState WHITE_TERRACOTTA = Blocks.WHITE_TERRACOTTA.defaultBlockState();
    static final BlockState PACKED_MUD = Blocks.PACKED_MUD.defaultBlockState();

    static final KeyDispatchDataCodec<SavannaBandlands> CODEC = KeyDispatchDataCodec.of(MapCodec.unit(INSTANCE));

    @Override
    public KeyDispatchDataCodec<? extends SurfaceRules.RuleSource> codec() {
        return CODEC;
    }

    @Override
    public SurfaceRules.SurfaceRule apply(SurfaceRules.Context context) {
        ContextAccessor instance = (ContextAccessor)(Object)context;
        NormalNoise clayBandsOffsetNoise = instance.getRandomState().getOrCreateNoise(Noises.CLAY_BANDS_OFFSET);
        BlockState[] clayBands = generateBands(((SurfaceSystemAccessor)instance.getSystem()).getNoiseRandom().fromHashOf(new ResourceLocation("savanna_clay_bands")));
        return (x, y, z) -> getBands(clayBandsOffsetNoise, clayBands, x, y, z);
    }

    private static BlockState[] generateBands(RandomSource random) {
        BlockState[] states = new BlockState[192];
        Arrays.fill(states, LIGHT_GRAY_TERRACOTTA);

        for(int i = 0; i < states.length; ++i) {
            i += random.nextInt(5) + 1;
            if (i < states.length) {
                states[i] = PACKED_MUD;
            }
        }

        makeBands(random, states, 1, WHITE_TERRACOTTA);
        makeBands(random, states, 2, PACKED_MUD);
        makeBands(random, states, 1, WHITE_TERRACOTTA);
        int index = random.nextIntBetweenInclusive(9, 15);
        int counter = 0;

        for(int i = 0; counter < index && i < states.length; i += random.nextInt(16) + 4) {
            states[i] = WHITE_TERRACOTTA;
            if (i - 1 > 0 && random.nextBoolean()) {
                states[i - 1] = LIGHT_GRAY_TERRACOTTA;
            }

            if (i + 1 < states.length && random.nextBoolean()) {
                states[i + 1] = LIGHT_GRAY_TERRACOTTA;
            }

            ++counter;
        }

        return states;
    }

    private static void makeBands(RandomSource random, BlockState[] states, int length, BlockState state) {
        int count = random.nextIntBetweenInclusive(6, 15);

        for(int i = 0; i < count; ++i) {
            int bandLength = length + random.nextInt(3);
            int index = random.nextInt(states.length);

            for(int offset = 0; index + offset < states.length && offset < bandLength; ++offset) {
                states[index + offset] = state;
            }
        }
    }

    private BlockState getBands(NormalNoise offsetNoise, BlockState[] states, int x, int y, int z) {
        int noise = (int) Math.round(offsetNoise.getValue(x, 0.0, z) * 4.0);
        return states[(y + noise + states.length) % states.length];
    }
}
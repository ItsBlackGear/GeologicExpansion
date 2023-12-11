package com.blackgear.geologicexpansion.common.registries.worldgen;

import com.blackgear.geologicexpansion.core.GeologicExpansion;
import com.blackgear.geologicexpansion.core.platform.common.WorldGenRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.synth.NormalNoise.NoiseParameters;

public class GENoises {
    public static final WorldGenRegistry<NoiseParameters> NOISES = WorldGenRegistry.of(Registries.NOISE, GeologicExpansion.MOD_ID);

    public static final ResourceKey<NoiseParameters> PRISMATIC_PATCH = NOISES.create("prismatic_patch");

    public static void bootstrap(BootstapContext<NoiseParameters> context) {
        NOISES.register(
                context,
                PRISMATIC_PATCH,
                -6,
                1.5,
                1.25, 1.0, 2.5, 1
        );
    }
}
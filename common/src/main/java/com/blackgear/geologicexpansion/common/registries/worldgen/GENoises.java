package com.blackgear.geologicexpansion.common.registries.worldgen;

import com.blackgear.geologicexpansion.core.GeologicExpansion;
import com.blackgear.geologicexpansion.core.platform.CoreRegistry;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.synth.NormalNoise.NoiseParameters;

import java.util.function.Supplier;

public class GENoises {
    public static final CoreRegistry<NoiseParameters> NOISES = CoreRegistry.create(BuiltinRegistries.NOISE, GeologicExpansion.MOD_ID);

    public static final ResourceKey<NoiseParameters> PRISMATIC_PATCH = create("prismatic_patch", () -> new NoiseParameters(-6, 1.5, 1.25, 1.0, 2.5, 1));

    private static ResourceKey<NoiseParameters> create(String key, Supplier<NoiseParameters> noiseParameters) {
        NOISES.register(key, noiseParameters);
        return ResourceKey.create(Registry.NOISE_REGISTRY, new ResourceLocation(GeologicExpansion.MOD_ID, key));
    }
}
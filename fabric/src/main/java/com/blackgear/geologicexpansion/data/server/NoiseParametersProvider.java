package com.blackgear.geologicexpansion.data.server;

import com.blackgear.geologicexpansion.common.registries.worldgen.GENoises;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import java.util.concurrent.CompletableFuture;

public class NoiseParametersProvider extends FabricDynamicRegistryProvider {
    public NoiseParametersProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(HolderLookup.Provider registries, Entries entries) {
        this.add(registries, entries, GENoises.PRISMATIC_PATCH);
    }

    private void add(HolderLookup.Provider registries, Entries entries, ResourceKey<NormalNoise.NoiseParameters> key) {
        final HolderLookup.RegistryLookup<NormalNoise.NoiseParameters> registry = registries.lookupOrThrow(Registries.NOISE);
        entries.add(key, registry.getOrThrow(key).value());
    }

    @Override
    public String getName() {
        return "worldgen/noise";
    }
}
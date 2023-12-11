package com.blackgear.geologicexpansion.data.server;

import com.blackgear.geologicexpansion.common.registries.worldgen.GEBiomes;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

import java.util.concurrent.CompletableFuture;

public class BiomeProvider extends FabricDynamicRegistryProvider {
    public BiomeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(HolderLookup.Provider registries, Entries entries) {
        this.add(registries, entries, GEBiomes.PRISMATIC_CALDERA);
    }

    private void add(HolderLookup.Provider registries, Entries entries, ResourceKey<Biome> key) {
        final HolderLookup.RegistryLookup<Biome> registry = registries.lookupOrThrow(Registries.BIOME);
        entries.add(key, registry.getOrThrow(key).value());
    }

    @Override
    public String getName() {
        return "worldgen/biome";
    }
}
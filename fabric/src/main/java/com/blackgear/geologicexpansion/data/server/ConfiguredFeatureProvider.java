package com.blackgear.geologicexpansion.data.server;

import com.blackgear.geologicexpansion.common.worldgen.placements.CalderaFeatures;
import com.blackgear.geologicexpansion.common.worldgen.placements.SurfaceFeatures;
import com.blackgear.geologicexpansion.common.worldgen.placements.UndergroundFeatures;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import java.util.concurrent.CompletableFuture;

public class ConfiguredFeatureProvider extends FabricDynamicRegistryProvider {
    public ConfiguredFeatureProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(HolderLookup.Provider registries, Entries entries) {
        // ========== CALDERA FEATURES =================================================================================
        this.add(registries, entries, CalderaFeatures.PRISMATIC_LAKE);
        this.add(registries, entries, CalderaFeatures.GEYSER_PATCH);
        this.add(registries, entries, CalderaFeatures.PRISMATIC_BORDER_PURPLE);
        this.add(registries, entries, CalderaFeatures.PRISMATIC_BORDER_BROWN);
        this.add(registries, entries, CalderaFeatures.PRISMATIC_BORDER_RED);
        this.add(registries, entries, CalderaFeatures.PRISMATIC_BORDER_ORANGE);
        this.add(registries, entries, CalderaFeatures.PRISMATIC_BORDER_YELLOW);

        // ========== SURFACE FEATURES =================================================================================
        this.add(registries, entries, SurfaceFeatures.OVERGROWTH_PATCH);
        this.add(registries, entries, SurfaceFeatures.OVERGROWTH_PATCH_BONEMEAL);

        // ========== UNDERGROUND FEATURES =============================================================================
        this.add(registries, entries, UndergroundFeatures.ORE_LIMESTONE);
    }

    private void add(HolderLookup.Provider registries, Entries entries, ResourceKey<ConfiguredFeature<?, ?>> key) {
        final HolderLookup.RegistryLookup<ConfiguredFeature<?, ?>> registry = registries.lookupOrThrow(Registries.CONFIGURED_FEATURE);
        entries.add(key, registry.getOrThrow(key).value());
    }

    @Override
    public String getName() {
        return "worldgen/configured_feature";
    }
}
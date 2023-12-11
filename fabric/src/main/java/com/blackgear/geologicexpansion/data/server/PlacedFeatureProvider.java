package com.blackgear.geologicexpansion.data.server;

import com.blackgear.geologicexpansion.common.worldgen.placements.CalderaPlacements;
import com.blackgear.geologicexpansion.common.worldgen.placements.SurfacePlacements;
import com.blackgear.geologicexpansion.common.worldgen.placements.UndergroundPlacements;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.concurrent.CompletableFuture;

public class PlacedFeatureProvider extends FabricDynamicRegistryProvider {
    public PlacedFeatureProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(HolderLookup.Provider registries, Entries entries) {
        // ========== CALDERA FEATURES =================================================================================
        this.add(registries, entries, CalderaPlacements.PRISMATIC_LAKE);
        this.add(registries, entries, CalderaPlacements.GEYSER_PATCH);
        this.add(registries, entries, CalderaPlacements.PRISMATIC_BORDER_PURPLE);
        this.add(registries, entries, CalderaPlacements.PRISMATIC_BORDER_BROWN);
        this.add(registries, entries, CalderaPlacements.PRISMATIC_BORDER_RED);
        this.add(registries, entries, CalderaPlacements.PRISMATIC_BORDER_ORANGE);
        this.add(registries, entries, CalderaPlacements.PRISMATIC_BORDER_YELLOW);

        // ========== SURFACE FEATURES =================================================================================
        this.add(registries, entries, SurfacePlacements.OVERGROWTH_PATCH);

        // ========== UNDERGROUND FEATURES =============================================================================
        this.add(registries, entries, UndergroundPlacements.ORE_LIMESTONE);
    }

    private void add(HolderLookup.Provider registries, Entries entries, ResourceKey<PlacedFeature> key) {
        final HolderLookup.RegistryLookup<PlacedFeature> registry = registries.lookupOrThrow(Registries.PLACED_FEATURE);
        entries.add(key, registry.getOrThrow(key).value());
    }

    @Override
    public String getName() {
        return "worldgen/placed_feature";
    }
}

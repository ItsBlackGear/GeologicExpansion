package com.blackgear.geologicexpansion.data;

import com.blackgear.geologicexpansion.common.registries.worldgen.GEBiomes;
import com.blackgear.geologicexpansion.common.registries.worldgen.GENoises;
import com.blackgear.geologicexpansion.common.worldgen.placements.CalderaFeatures;
import com.blackgear.geologicexpansion.common.worldgen.placements.CalderaPlacements;
import com.blackgear.geologicexpansion.common.worldgen.placements.SurfaceFeatures;
import com.blackgear.geologicexpansion.common.worldgen.placements.SurfacePlacements;
import com.blackgear.geologicexpansion.common.worldgen.placements.UndergroundFeatures;
import com.blackgear.geologicexpansion.common.worldgen.placements.UndergroundPlacements;
import com.blackgear.geologicexpansion.data.client.ModelGenerator;
import com.blackgear.geologicexpansion.data.common.RecipeGenerator;
import com.blackgear.geologicexpansion.data.common.loot.BlockLootTableGenerator;
import com.blackgear.geologicexpansion.data.common.loot.EntityLootGenerator;
import com.blackgear.geologicexpansion.data.common.tag.BiomeTagGenerator;
import com.blackgear.geologicexpansion.data.common.tag.BlockTagGenerator;
import com.blackgear.geologicexpansion.data.common.tag.EntityTypeTagGenerator;
import com.blackgear.geologicexpansion.data.common.tag.ItemTagGenerator;
import com.blackgear.geologicexpansion.data.server.BiomeProvider;
import com.blackgear.geologicexpansion.data.server.ConfiguredFeatureProvider;
import com.blackgear.geologicexpansion.data.server.NoiseParametersProvider;
import com.blackgear.geologicexpansion.data.server.PlacedFeatureProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;

public class DataGeneration implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        // ========== CLIENT SIDE ==========
        FabricDataGenerator.Pack pack = generator.createPack();
        pack.addProvider(ModelGenerator::new);

        // ========== COMMON SIDE ==========
        FabricTagProvider.BlockTagProvider blockTags = pack.addProvider(BlockTagGenerator::new);
        pack.addProvider((output, registriesFuture) -> new ItemTagGenerator(output, registriesFuture, blockTags));
        pack.addProvider(BiomeTagGenerator::new);
        pack.addProvider(EntityTypeTagGenerator::new);

        pack.addProvider(BlockLootTableGenerator::new);
        pack.addProvider(EntityLootGenerator::new);
        pack.addProvider(RecipeGenerator::new);

        // ========== SERVER SIDE ==========
        pack.addProvider(ConfiguredFeatureProvider::new);
        pack.addProvider(PlacedFeatureProvider::new);
        pack.addProvider(NoiseParametersProvider::new);
        pack.addProvider(BiomeProvider::new);
    }

    @Override
    public void buildRegistry(RegistrySetBuilder builder) {
        // ========== CALDERA FEATURES =================================================================================
        builder.add(Registries.CONFIGURED_FEATURE, CalderaFeatures::bootstrap);
        builder.add(Registries.PLACED_FEATURE, CalderaPlacements::bootstrap);

        // ========== SURFACE FEATURES =================================================================================
        builder.add(Registries.CONFIGURED_FEATURE, SurfaceFeatures::bootstrap);
        builder.add(Registries.PLACED_FEATURE, SurfacePlacements::bootstrap);

        // ========== UNDERGROUND FEATURES =============================================================================
        builder.add(Registries.CONFIGURED_FEATURE, UndergroundFeatures::bootstrap);
        builder.add(Registries.PLACED_FEATURE, UndergroundPlacements::bootstrap);

        // ========== NOISE PARAMETERS =================================================================================
        builder.add(Registries.NOISE, GENoises::bootstrap);

        // ========== BIOMES ===========================================================================================
        builder.add(Registries.BIOME, GEBiomes::bootstrap);
    }
}
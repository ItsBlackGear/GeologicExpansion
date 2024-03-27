package com.blackgear.geologicexpansion.common.registries.worldgen;

import com.blackgear.geologicexpansion.common.worldgen.resources.BiomeBuilder;
import com.blackgear.geologicexpansion.core.GeologicExpansion;
import com.blackgear.geologicexpansion.core.mixin.access.OverworldBiomesAccessor;
import com.blackgear.geologicexpansion.core.platform.CoreRegistry;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;

import java.util.function.Supplier;

public class GEBiomes {
    public static final CoreRegistry<Biome> BIOMES = CoreRegistry.create(BuiltinRegistries.BIOME, GeologicExpansion.MOD_ID);
    public static final BiomeBuilder BUILDER = new BiomeBuilder();

    public static final ResourceKey<Biome> PRISMATIC_CALDERA = create("prismatic_caldera", GEBiomes::prismaticCaldera);
    public static final ResourceKey<Biome> MAPLE_FOREST = create("maple_forest", GEBiomes::mapleForest);
    public static final ResourceKey<Biome> SAVANNA_STRATA = create("savanna_strata", GEBiomes::savannaStrata);

    private static ResourceKey<Biome> create(String key, Supplier<Biome> biome) {
        BIOMES.register(key, biome);
        return ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(GeologicExpansion.MOD_ID, key));
    }

    public static Biome prismaticCaldera() {
        MobSpawnSettings.Builder mobSpawnSettings = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.farmAnimals(mobSpawnSettings);
        mobSpawnSettings.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 8, 4, 4));
        mobSpawnSettings.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 4, 2, 3));
        mobSpawnSettings.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.FOX, 8, 2, 4));
        BiomeDefaultFeatures.commonSpawns(mobSpawnSettings);

        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder();
        OverworldBiomesAccessor.globalOverworldGeneration(generationSettings);
        BiomeDefaultFeatures.addFerns(generationSettings);
        BiomeDefaultFeatures.addDefaultOres(generationSettings);
        BiomeDefaultFeatures.addDefaultSoftDisks(generationSettings);
        BiomeDefaultFeatures.addTaigaTrees(generationSettings);
        BiomeDefaultFeatures.addDefaultFlowers(generationSettings);
        BiomeDefaultFeatures.addGiantTaigaVegetation(generationSettings);
        BiomeDefaultFeatures.addDefaultMushrooms(generationSettings);
        BiomeDefaultFeatures.addDefaultExtraVegetation(generationSettings);
        BiomeDefaultFeatures.addCommonBerryBushes(generationSettings);

        return BUILDER.create(
            Biome.Precipitation.RAIN,
            0.24775F,
            0.8F,
            mobSpawnSettings,
            generationSettings,
            Musics.createGameMusic(SoundEvents.MUSIC_BIOME_OLD_GROWTH_TAIGA)
        );
    }

    public static Biome mapleForest() {
        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.farmAnimals(spawnSettings);
        spawnSettings.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 4, 2, 3));
        spawnSettings.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.FOX, 8, 2, 4));
        BiomeDefaultFeatures.commonSpawns(spawnSettings);

        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder();
        OverworldBiomesAccessor.globalOverworldGeneration(generationSettings);
//        BiomeDefaultFeatures.addFerns(generationSettings);
//        BiomeDefaultFeatures.addDefaultOres(generationSettings);
//        BiomeDefaultFeatures.addDefaultSoftDisks(generationSettings);
//        BiomeDefaultFeatures.addDefaultFlowers(generationSettings);
//        generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_PLAIN);
//        generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.MUSHROOM_ISLAND_VEGETATION);
//        generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.RED_MUSHROOM_TAIGA);
//        generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.BROWN_MUSHROOM_TAIGA);
//        BiomeDefaultFeatures.addDefaultMushrooms(generationSettings);
//        BiomeDefaultFeatures.addDefaultExtraVegetation(generationSettings);
//        BiomeDefaultFeatures.addCommonBerryBushes(generationSettings);
//        generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_SUNFLOWER);

        return BUILDER.create(
            Biome.Precipitation.RAIN,
            0.5F,
            0.8F,
            spawnSettings,
            generationSettings,
            Musics.createGameMusic(SoundEvents.MUSIC_BIOME_LUSH_CAVES)
        );
    }

    public static Biome savannaStrata() {
        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.farmAnimals(spawnSettings);
        spawnSettings.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.HORSE, 1, 2, 6));
        spawnSettings.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.DONKEY, 1, 1, 1));
        BiomeDefaultFeatures.commonSpawns(spawnSettings);

        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder();
        OverworldBiomesAccessor.globalOverworldGeneration(generationSettings);
        BiomeDefaultFeatures.addSavannaGrass(generationSettings);
        BiomeDefaultFeatures.addDefaultOres(generationSettings);
        BiomeDefaultFeatures.addDefaultSoftDisks(generationSettings);
        BiomeDefaultFeatures.addSavannaTrees(generationSettings);
        BiomeDefaultFeatures.addWarmFlowers(generationSettings);
        BiomeDefaultFeatures.addSavannaExtraGrass(generationSettings);
        BiomeDefaultFeatures.addDefaultMushrooms(generationSettings);
        BiomeDefaultFeatures.addDefaultExtraVegetation(generationSettings);

        return BUILDER.create(
            Biome.Precipitation.RAIN,
            2.0F,
            0.0F,
            spawnSettings,
            generationSettings,
            null
        );
    }
}
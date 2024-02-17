package com.blackgear.geologicexpansion.common.registries.worldgen;

import com.blackgear.geologicexpansion.core.GeologicExpansion;
import com.blackgear.geologicexpansion.core.mixin.access.OverworldBiomesAccessor;
import com.blackgear.geologicexpansion.core.platform.CoreRegistry;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class GEBiomes {
    public static final CoreRegistry<Biome> BIOMES = CoreRegistry.create(BuiltinRegistries.BIOME, GeologicExpansion.MOD_ID);

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
        BiomeGenerationSettings.Builder biomeGenerationSettings = new BiomeGenerationSettings.Builder();
        OverworldBiomesAccessor.globalOverworldGeneration(biomeGenerationSettings);
        BiomeDefaultFeatures.addMossyStoneBlock(biomeGenerationSettings);
        BiomeDefaultFeatures.addFerns(biomeGenerationSettings);
        BiomeDefaultFeatures.addDefaultOres(biomeGenerationSettings);
        BiomeDefaultFeatures.addDefaultSoftDisks(biomeGenerationSettings);
        BiomeDefaultFeatures.addTaigaTrees(biomeGenerationSettings);
        BiomeDefaultFeatures.addDefaultFlowers(biomeGenerationSettings);
        BiomeDefaultFeatures.addGiantTaigaVegetation(biomeGenerationSettings);
        BiomeDefaultFeatures.addDefaultMushrooms(biomeGenerationSettings);
        BiomeDefaultFeatures.addDefaultExtraVegetation(biomeGenerationSettings);
        BiomeDefaultFeatures.addCommonBerryBushes(biomeGenerationSettings);
        return biome(Biome.Precipitation.RAIN, 0.24775F, 0.8F, mobSpawnSettings, biomeGenerationSettings, Musics.createGameMusic(SoundEvents.MUSIC_BIOME_OLD_GROWTH_TAIGA));
    }

    public static Biome mapleForest() {
        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.farmAnimals(spawnSettings);
        spawnSettings.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 4, 2, 3));
        spawnSettings.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.FOX, 8, 2, 4));
        BiomeDefaultFeatures.commonSpawns(spawnSettings);
        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder();
        OverworldBiomesAccessor.globalOverworldGeneration(generationSettings);
        BiomeDefaultFeatures.addDefaultSoftDisks(generationSettings);
        BiomeDefaultFeatures.addDefaultFlowers(generationSettings);
        generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_PLAIN);
        generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.MUSHROOM_ISLAND_VEGETATION);
        generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.RED_MUSHROOM_TAIGA);
        generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.BROWN_MUSHROOM_TAIGA);
        BiomeDefaultFeatures.addDefaultMushrooms(generationSettings);
        BiomeDefaultFeatures.addDefaultExtraVegetation(generationSettings);
        BiomeDefaultFeatures.addCommonBerryBushes(generationSettings);
        generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_SUNFLOWER);

        return biome(Biome.Precipitation.RAIN, 0.5F, 0.8F, spawnSettings, generationSettings, Musics.createGameMusic(SoundEvents.MUSIC_BIOME_LUSH_CAVES));
    }

    public static Biome savannaStrata() {
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
        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.farmAnimals(spawnSettings);
        spawnSettings.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.HORSE, 1, 2, 6));
        spawnSettings.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.DONKEY, 1, 1, 1));
        BiomeDefaultFeatures.commonSpawns(spawnSettings);

        return biome(Biome.Precipitation.RAIN, 2.0F, 0.0F, builder -> {
            builder.grassColorOverride(16762714);
            builder.foliageColorOverride(9734425);
        }, spawnSettings, generationSettings, null);
    }

    protected static int calculateSkyColor(float temperature) {
        float modifier = temperature / 3.0F;
        modifier = Mth.clamp(modifier, -1.0F, 1.0F);
        return Mth.hsvToRgb(0.62222224F - modifier * 0.05F, 0.5F + modifier * 0.1F, 1.0F);
    }

    public static Biome biome(Biome.Precipitation precipitation, float temperature, float downfall, MobSpawnSettings.Builder spawnSettingsBuilder, BiomeGenerationSettings.Builder generationSettingsBuilder, @Nullable Music music) {
        return biome(precipitation, temperature, downfall, 4159204, 329011, spawnSettingsBuilder, generationSettingsBuilder, music);
    }

    public static Biome biome(Biome.Precipitation precipitation, float temperature, float downfall, int waterColor, int waterFogColor, MobSpawnSettings.Builder spawnSettingsBuilder, BiomeGenerationSettings.Builder generationSettingsBuilder, @Nullable Music music) {
        BiomeSpecialEffects.Builder specialEffects = new BiomeSpecialEffects.Builder();
        specialEffects.waterColor(waterColor);
        specialEffects.waterFogColor(waterFogColor);
        specialEffects.fogColor(12638463);
        specialEffects.skyColor(calculateSkyColor(temperature));
        specialEffects.ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS);
        specialEffects.backgroundMusic(music);

        return biome(precipitation, temperature, downfall, specialEffects, spawnSettingsBuilder, generationSettingsBuilder);
    }

    public static Biome biome(
        Biome.Precipitation precipitation,
        float temperature,
        float downfall,
        Consumer<BiomeSpecialEffects.Builder> specialEffects, MobSpawnSettings.Builder spawnSettingsBuilder, BiomeGenerationSettings.Builder generationSettingsBuilder, @Nullable Music music) {
        return biome(precipitation, temperature, downfall, 4159204, 329011, specialEffects, spawnSettingsBuilder, generationSettingsBuilder, music);
    }

    public static Biome biome(
        Biome.Precipitation precipitation,
        float temperature,
        float downfall,
        int waterColor,
        int waterFogColor,
        Consumer<BiomeSpecialEffects.Builder> specialEffects,
        MobSpawnSettings.Builder spawnSettingsBuilder,
        BiomeGenerationSettings.Builder generationSettingsBuilder,
        @Nullable Music music
    ) {
        BiomeSpecialEffects.Builder specialEffectsBuilder = new BiomeSpecialEffects.Builder();
        specialEffectsBuilder.waterColor(waterColor);
        specialEffectsBuilder.waterFogColor(waterFogColor);
        specialEffectsBuilder.fogColor(12638463);
        specialEffectsBuilder.skyColor(calculateSkyColor(temperature));
        specialEffectsBuilder.ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS);
        specialEffectsBuilder.backgroundMusic(music);

        specialEffects.accept(specialEffectsBuilder);

        return biome(precipitation, temperature, downfall, specialEffectsBuilder, spawnSettingsBuilder, generationSettingsBuilder);
    }

    public static Biome biome(
        Biome.Precipitation precipitation,
        float temperature,
        float downfall,
        BiomeSpecialEffects.Builder specialEffects,
        MobSpawnSettings.Builder spawnSettings,
        BiomeGenerationSettings.Builder generationSettings
    ) {
        return new Biome.BiomeBuilder()
            .precipitation(precipitation)
            .temperature(temperature)
            .downfall(downfall)
            .specialEffects(specialEffects.build())
            .mobSpawnSettings(spawnSettings.build())
            .generationSettings(generationSettings.build())
            .build();
    }
}
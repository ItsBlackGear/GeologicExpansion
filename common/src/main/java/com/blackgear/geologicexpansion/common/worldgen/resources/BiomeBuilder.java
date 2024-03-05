package com.blackgear.geologicexpansion.common.worldgen.resources;

import net.minecraft.sounds.Music;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class BiomeBuilder {
    private int calculateSkyColor(float temperature) {
        float modifier = temperature / 3.0F;
        modifier = Mth.clamp(modifier, -1.0F, 1.0F);
        return Mth.hsvToRgb(0.62222224F - modifier * 0.05F, 0.5F + modifier * 0.1F, 1.0F);
    }

    public Biome create(
        Biome.Precipitation precipitation,
        float temperature,
        float downfall,
        MobSpawnSettings.Builder spawnSettingsBuilder,
        BiomeGenerationSettings.Builder generationSettingsBuilder,
        @Nullable Music music
    ) {
        return create(precipitation, temperature, downfall, 4159204, 329011, spawnSettingsBuilder, generationSettingsBuilder, music);
    }

    public Biome create(
        Biome.Precipitation precipitation,
        float temperature,
        float downfall,
        int waterColor,
        int waterFogColor,
        MobSpawnSettings.Builder spawnSettingsBuilder,
        BiomeGenerationSettings.Builder generationSettingsBuilder,
        @Nullable Music music
    ) {
        BiomeSpecialEffects.Builder specialEffects = new BiomeSpecialEffects.Builder();
        specialEffects.waterColor(waterColor);
        specialEffects.waterFogColor(waterFogColor);
        specialEffects.fogColor(12638463);
        specialEffects.skyColor(calculateSkyColor(temperature));
        specialEffects.ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS);
        specialEffects.backgroundMusic(music);

        return create(precipitation, temperature, downfall, specialEffects, spawnSettingsBuilder, generationSettingsBuilder);
    }

    public Biome create(
        Biome.Precipitation precipitation,
        float temperature,
        float downfall,
        Consumer<BiomeSpecialEffects.Builder> specialEffects,
        MobSpawnSettings.Builder spawnSettingsBuilder,
        BiomeGenerationSettings.Builder generationSettingsBuilder,
        @Nullable Music music
    ) {
        return create(precipitation, temperature, downfall, 4159204, 329011, specialEffects, spawnSettingsBuilder, generationSettingsBuilder, music);
    }

    public Biome create(
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

        return create(precipitation, temperature, downfall, specialEffectsBuilder, spawnSettingsBuilder, generationSettingsBuilder);
    }

    public Biome create(
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
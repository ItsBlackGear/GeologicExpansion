package com.blackgear.geologicexpansion.core.mixin.access;

import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.sounds.Music;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.MobSpawnSettings;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(OverworldBiomes.class)
public interface OverworldBiomesAccessor {
    @Invoker("globalOverworldGeneration")
    static void globalOverworldGeneration(BiomeGenerationSettings.Builder builder) {
        throw new UnsupportedOperationException();
    }

    @Invoker
    static Biome callBiome(
        boolean hasPercipitation,
        float temperature,
        float downfall,
        MobSpawnSettings.Builder mobSpawnSettings,
        BiomeGenerationSettings.Builder generationSettings,
        @Nullable Music backgroundMusic
    ) {
        throw new UnsupportedOperationException();
    }
}

package com.blackgear.geologicexpansion.core.util;

import com.blackgear.geologicexpansion.core.GeologicExpansion;
import com.blackgear.geologicexpansion.core.mixin.access.NoiseGeneratorSettingsAccessor;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.storage.WorldData;

public class LevelUtils {
    public static void appendSurfaceRules(WorldData data, ResourceKey<LevelStem> key, SurfaceRules.RuleSource surfaceRules) {
        if (data == null) throw new NullPointerException("Minecraft server's world data is null...");

        LevelStem stem = data.worldGenSettings().dimensions().get(key);
        if (stem == null) {
            throw new NullPointerException(key.location() + " is not a valid level stem key as it doesn't exist in this world's settings");
        }

        ChunkGenerator generator = stem.generator();

        boolean isGexBiome = generator.getBiomeSource().possibleBiomes().stream().anyMatch(holder -> holder.unwrapKey().orElseThrow().location().getNamespace().equals(GeologicExpansion.MOD_ID));
        if (isGexBiome) {
            if (generator instanceof NoiseBasedChunkGenerator noise) {
                NoiseGeneratorSettings settings = noise.generatorSettings().value();
                ((NoiseGeneratorSettingsAccessor)(Object)settings).setSurfaceRule(SurfaceRules.sequence(surfaceRules, settings.surfaceRule()));
            }
        }
    }
}
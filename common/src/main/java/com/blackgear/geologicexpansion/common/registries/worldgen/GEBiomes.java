package com.blackgear.geologicexpansion.common.registries.worldgen;

import com.blackgear.geologicexpansion.core.GeologicExpansion;
import com.blackgear.geologicexpansion.core.mixin.access.OverworldBiomesAccessor;
import com.blackgear.geologicexpansion.core.platform.CoreRegistry;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.MobSpawnSettings;

import java.util.function.Supplier;

public class GEBiomes {
    public static final CoreRegistry<Biome> BIOMES = CoreRegistry.create(BuiltinRegistries.BIOME, GeologicExpansion.MOD_ID);

    public static final ResourceKey<Biome> PRISMATIC_CALDERA = create("prismatic_caldera", GEBiomes::prismaticCaldera);

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
        return OverworldBiomesAccessor.callBiome(Biome.Precipitation.RAIN, 0.24775F, 0.8F, mobSpawnSettings, biomeGenerationSettings, Musics.createGameMusic(SoundEvents.MUSIC_BIOME_OLD_GROWTH_TAIGA));
    }
}
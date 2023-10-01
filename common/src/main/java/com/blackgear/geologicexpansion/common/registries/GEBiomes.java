package com.blackgear.geologicexpansion.common.registries;

import com.blackgear.geologicexpansion.core.GeologicExpansion;
import com.blackgear.geologicexpansion.core.platform.CoreRegistry;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

import java.util.function.Supplier;

public class GEBiomes {
    public static final CoreRegistry<Biome> BIOMES = CoreRegistry.create(BuiltinRegistries.BIOME, GeologicExpansion.MOD_ID);

    public static final ResourceKey<Biome> PRISMATIC_CALDERA = create("prismatic_caldera", OverworldBiomes::theVoid);
    public static final ResourceKey<Biome> TRAVERTINE = create("travertine", OverworldBiomes::theVoid);

    private static ResourceKey<Biome> create(String key, Supplier<Biome> biome) {
        BIOMES.register(key, biome);
        return ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(GeologicExpansion.MOD_ID, key));
    }
}
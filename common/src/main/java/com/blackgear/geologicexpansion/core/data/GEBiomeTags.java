package com.blackgear.geologicexpansion.core.data;

import com.blackgear.geologicexpansion.core.GeologicExpansion;
import com.blackgear.geologicexpansion.core.platform.common.TagRegistry;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class GEBiomeTags {
    public static final TagRegistry<Biome> TAGS = TagRegistry.of(Registry.BIOME_REGISTRY, GeologicExpansion.MOD_ID);

    public static final TagKey<Biome> CAN_DUCKS_SPAWN = TAGS.create("can_ducks_spawn");
}
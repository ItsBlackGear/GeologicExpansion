package com.blackgear.geologicexpansion.core.data;

import com.blackgear.geologicexpansion.core.GeologicExpansion;
import com.blackgear.geologicexpansion.core.platform.common.TagRegistry;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class GEEntityTags {
    public static final TagRegistry<EntityType<?>> TAGS = TagRegistry.of(Registry.ENTITY_TYPE_REGISTRY, GeologicExpansion.MOD_ID);

    public static final TagKey<EntityType<?>> GRIZZLY_HUNT_TARGETS = TAGS.create("grizzly_hunt_targets");
}
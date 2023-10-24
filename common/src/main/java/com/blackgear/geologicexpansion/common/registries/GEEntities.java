package com.blackgear.geologicexpansion.common.registries;

import com.blackgear.geologicexpansion.common.entity.Duck;
import com.blackgear.geologicexpansion.core.GeologicExpansion;
import com.blackgear.geologicexpansion.core.platform.CoreRegistry;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import java.util.function.Supplier;

public class GEEntities {
    public static final CoreRegistry<EntityType<?>> ENTITIES = CoreRegistry.create(Registry.ENTITY_TYPE, GeologicExpansion.MOD_ID);

    public static final Supplier<EntityType<Duck>> DUCK = create("duck", EntityType.Builder.of(Duck::new, MobCategory.CREATURE).sized(0.4F, 0.7F).clientTrackingRange(10));

    private static <T extends Entity> Supplier<EntityType<T>> create(String key, EntityType.Builder<T> builder) {
        return ENTITIES.register(key, () -> builder.build(key));
    }
}
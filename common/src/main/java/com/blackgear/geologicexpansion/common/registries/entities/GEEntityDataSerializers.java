package com.blackgear.geologicexpansion.common.registries.entities;

import com.blackgear.geologicexpansion.common.entity.duck.DuckA;
import com.blackgear.geologicexpansion.common.entity.resource.EntityState;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;

public class GEEntityDataSerializers {
    public static final EntityDataSerializer<DuckA.State> DUCK_STATE = EntityDataSerializer.simpleEnum(DuckA.State.class);
    public static final EntityDataSerializer<EntityState> ENTITY_STATE = EntityDataSerializer.simpleEnum(EntityState.class);

    public static void bootstrap() {
        EntityDataSerializers.registerSerializer(DUCK_STATE);
        EntityDataSerializers.registerSerializer(ENTITY_STATE);
    }
}
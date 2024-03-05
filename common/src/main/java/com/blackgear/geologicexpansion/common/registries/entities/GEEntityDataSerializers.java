package com.blackgear.geologicexpansion.common.registries.entities;

import com.blackgear.geologicexpansion.common.entity.duck.Duck;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;

public class GEEntityDataSerializers {
    public static final EntityDataSerializer<Duck.State> DUCK_STATE = EntityDataSerializer.simpleEnum(Duck.State.class);

    public static void bootstrap() {
        EntityDataSerializers.registerSerializer(DUCK_STATE);
    }
}
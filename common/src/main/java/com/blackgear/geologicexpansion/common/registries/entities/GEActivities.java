package com.blackgear.geologicexpansion.common.registries.entities;

import com.blackgear.geologicexpansion.core.GeologicExpansion;
import com.blackgear.geologicexpansion.core.mixin.access.ActivityAccessor;
import com.blackgear.geologicexpansion.core.platform.CoreRegistry;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.schedule.Activity;

import java.util.function.Supplier;

public class GEActivities {
    public static final CoreRegistry<Activity> ACTIVITIES = CoreRegistry.create(Registry.ACTIVITY, GeologicExpansion.MOD_ID);

    public static final Supplier<Activity> HUNT = create("hunt");

    private static Supplier<Activity> create(String key) {
        return ACTIVITIES.register(key, () -> ActivityAccessor.createActivity(key));
    }
}
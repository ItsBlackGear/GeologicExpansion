package com.blackgear.geologicexpansion.common.entity.bear.behavior;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.AnimalPanic;

public class BearPanic extends AnimalPanic {
    public BearPanic(float speedModifier) {
        super(speedModifier);
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, PathfinderMob entity) {
        return entity.getLastHurtByMob() != null && entity.isBaby() || entity.isOnFire();
    }
}
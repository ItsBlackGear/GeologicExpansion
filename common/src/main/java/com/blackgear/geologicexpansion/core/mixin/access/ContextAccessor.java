package com.blackgear.geologicexpansion.core.mixin.access;

import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.SurfaceSystem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SurfaceRules.Context.class)
public interface ContextAccessor {
    @Accessor
    SurfaceSystem getSystem();

    @Accessor
    RandomState getRandomState();
}
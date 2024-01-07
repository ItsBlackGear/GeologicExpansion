package com.blackgear.geologicexpansion.core.mixin.access;

import net.minecraft.world.level.levelgen.PositionalRandomFactory;
import net.minecraft.world.level.levelgen.SurfaceSystem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SurfaceSystem.class)
public interface SurfaceSystemAccessor {
    @Accessor
    PositionalRandomFactory getNoiseRandom();
}

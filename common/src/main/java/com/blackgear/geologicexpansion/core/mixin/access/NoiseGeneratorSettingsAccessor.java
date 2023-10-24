package com.blackgear.geologicexpansion.core.mixin.access;

import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.SurfaceRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(NoiseGeneratorSettings.class)
public interface NoiseGeneratorSettingsAccessor {
    @Accessor
    SurfaceRules.RuleSource getSurfaceRule();

    @Mutable
    @Accessor
    void setSurfaceRule(SurfaceRules.RuleSource surfaceRule);
}

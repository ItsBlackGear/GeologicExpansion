package com.blackgear.geologicexpansion.core.mixin.common;

import com.blackgear.geologicexpansion.common.worldgen.surface.GESurfaceRules;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.SurfaceRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NoiseGeneratorSettings.class)
public class NoiseGeneratorSettingsMixin {
    @Inject(method = "surfaceRule", at = @At("RETURN"), cancellable = true)
    private void aVoid(CallbackInfoReturnable<SurfaceRules.RuleSource> cir) {
        cir.setReturnValue(SurfaceRules.sequence(GESurfaceRules.makeRules(), cir.getReturnValue()));
    }
}
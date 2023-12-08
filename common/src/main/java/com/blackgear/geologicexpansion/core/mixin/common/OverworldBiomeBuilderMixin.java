package com.blackgear.geologicexpansion.core.mixin.common;

import com.blackgear.geologicexpansion.common.registries.worldgen.GEBiomes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(OverworldBiomeBuilder.class)
public abstract class OverworldBiomeBuilderMixin {
    @Inject(method = "pickMiddleBiome", at = @At("HEAD"), cancellable = true)
    private void ge$pickMiddleBiome(int temperature, int humidity, Climate.Parameter param, CallbackInfoReturnable<ResourceKey<Biome>> cir) {
        if (param.max() >= 0L && temperature == 1 && humidity == 3) {
            cir.setReturnValue(GEBiomes.PRISMATIC_CALDERA);
        }
    }
}
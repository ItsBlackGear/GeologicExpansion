package com.blackgear.geologicexpansion.core.mixin.common.worldgen;

import com.blackgear.geologicexpansion.common.registries.worldgen.GEBiomes;
import com.blackgear.geologicexpansion.common.worldgen.biome.*;
import com.blackgear.geologicexpansion.core.config.ConfigEntries;
import com.mojang.datafixers.util.Pair;
import net.minecraft.SharedConstants;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Consumer;

@Mixin(OverworldBiomeBuilder.class)
public abstract class OverworldBiomeBuilderMixin {
    @Shadow protected abstract void addSurfaceBiome(
        Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper,
        Climate.Parameter temperature,
        Climate.Parameter humidity,
        Climate.Parameter continentalness,
        Climate.Parameter erosion,
        Climate.Parameter depth,
        float offset,
        ResourceKey<Biome> biome
    );

    @Inject(method = "pickMiddleBiome", at = @At("HEAD"), cancellable = true)
    private void ge$pickMiddleBiome(int temperature, int humidity, Climate.Parameter param, CallbackInfoReturnable<ResourceKey<Biome>> cir) {
        if (ConfigEntries.includePrismaticCaldera()) {
            if (param.max() >= 0L && temperature == 1 && humidity == 3) {
                cir.setReturnValue(GEBiomes.PRISMATIC_CALDERA);
            }
        }
    }

    @Inject(
        method = "addLowSlice",
        at = @At("TAIL")
    )
    private void addBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper, Climate.Parameter depth, CallbackInfo ci) {
        this.addSurfaceBiome(
            mapper,
            Temperature.WARM.get(),
            Humidity.NEUTRAL.get(),
            Continentalness.span(Continentalness.MID_INLAND, Continentalness.NEAR_INLAND),
            Erosion.span(Erosion.EROSION_2, Erosion.EROSION_3),
            depth,
            0.0F,
            GEBiomes.MAPLE_FOREST
        );
    }
}

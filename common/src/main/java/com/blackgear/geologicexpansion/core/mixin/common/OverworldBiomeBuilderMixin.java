package com.blackgear.geologicexpansion.core.mixin.common;

import com.blackgear.geologicexpansion.common.registries.worldgen.GEBiomes;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(OverworldBiomeBuilder.class)
public abstract class OverworldBiomeBuilderMixin {
    @Shadow protected abstract void addSurfaceBiome(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> consumer, Climate.Parameter temperature, Climate.Parameter humidity, Climate.Parameter continentalness, Climate.Parameter erosion, Climate.Parameter depth, float weirdness, ResourceKey<Biome> key);

    @Shadow @Final private Climate.Parameter[] temperatures;

    @Shadow @Final private Climate.Parameter[] humidities;

    @Shadow @Final private Climate.Parameter midInlandContinentalness;

    @Shadow @Final private Climate.Parameter[] erosions;

    @Inject(method = "addValleys", at = @At("TAIL"))
    private void ge$addValleys(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> consumer, Climate.Parameter depth, CallbackInfo ci) {
        this.addSurfaceBiome(
                consumer,
                this.temperatures[2],
                this.humidities[3],
                this.midInlandContinentalness,
                Climate.Parameter.span(this.erosions[2], this.erosions[3]),
                depth,
                0,
                GEBiomes.PRISMATIC_CALDERA
        );
    }

    @Inject(method = "addLowSlice", at = @At("TAIL"))
    private void ge$addLowSlice(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> consumer, Climate.Parameter depth, CallbackInfo ci) {
        this.addSurfaceBiome(
                consumer,
                this.temperatures[2],
                this.humidities[3],
                this.midInlandContinentalness,
                Climate.Parameter.span(this.erosions[2], this.erosions[3]),
                depth,
                0,
                GEBiomes.PRISMATIC_CALDERA
        );
    }

    @Inject(method = "addMidSlice", at = @At("TAIL"))
    private void ge$addMidSlice(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> consumer, Climate.Parameter depth, CallbackInfo ci) {
        this.addSurfaceBiome(
                consumer,
                this.temperatures[2],
                this.humidities[3],
                this.midInlandContinentalness,
                Climate.Parameter.span(this.erosions[2], this.erosions[3]),
                depth,
                0,
                GEBiomes.PRISMATIC_CALDERA
        );
    }

    @Inject(method = "addHighSlice", at = @At("TAIL"))
    private void ge$addHighSlice(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> consumer, Climate.Parameter depth, CallbackInfo ci) {
        this.addSurfaceBiome(
                consumer,
                this.temperatures[2],
                this.humidities[3],
                this.midInlandContinentalness,
                Climate.Parameter.span(this.erosions[2], this.erosions[3]),
                depth,
                0,
                GEBiomes.PRISMATIC_CALDERA
        );
    }

    @Inject(method = "addPeaks", at = @At("TAIL"))
    private void ge$addPeaks(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> consumer, Climate.Parameter depth, CallbackInfo ci) {
        this.addSurfaceBiome(
                consumer,
                this.temperatures[2],
                this.humidities[3],
                this.midInlandContinentalness,
                Climate.Parameter.span(this.erosions[2], this.erosions[3]),
                depth,
                0,
                GEBiomes.PRISMATIC_CALDERA
        );
    }
}
package com.blackgear.geologicexpansion.common.worldgen.biome;

import net.minecraft.world.level.biome.Climate;

public enum Humidity {
    ARID(Climate.Parameter.span(-1.0F, -0.35F)),
    DRY(Climate.Parameter.span(-0.35F, -0.1F)),
    NEUTRAL(Climate.Parameter.span(-0.1F, 0.1F)),
    WET(Climate.Parameter.span(0.1F, 0.3F)),
    HUMID(Climate.Parameter.span(0.3F, 1.0F)),
    FULL_RANGE(Climate.Parameter.span(-1.0F, 1.0F));

    public final Climate.Parameter parameter;

    Humidity(Climate.Parameter parameter) {
        this.parameter = parameter;
    }

    public Climate.Parameter get() {
        return this.parameter;
    }

    public static Climate.Parameter span(Humidity min, Humidity max) {
        return Climate.Parameter.span(min.parameter.min(), max.parameter.max());
    }
}
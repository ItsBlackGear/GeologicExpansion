package com.blackgear.geologicexpansion.common.worldgen.biome;

import net.minecraft.world.level.biome.Climate;

public enum Temperature {
    ICY(Climate.Parameter.span(-1.0F, -0.45F)),
    COOL(Climate.Parameter.span(-0.45F, -0.15F)),
    NEUTRAL(Climate.Parameter.span(-0.15F, 0.2F)),
    WARM(Climate.Parameter.span(0.2F, 0.55F)),
    HOT(Climate.Parameter.span(0.55F, 1.0F)),
    FROZEN(Climate.Parameter.span(-1.0F, -0.45F)),
    UNFROZEN(Climate.Parameter.span(-0.45F, 1.0F)),
    FULL_RANGE(Climate.Parameter.span(-1.0F, 1.0F));

    public final Climate.Parameter parameter;

    Temperature(Climate.Parameter parameter) {
        this.parameter = parameter;
    }

    public Climate.Parameter get() {
        return this.parameter;
    }

    public static Climate.Parameter span(Temperature min, Temperature max) {
        return Climate.Parameter.span(min.parameter.min(), max.parameter.max());
    }
}
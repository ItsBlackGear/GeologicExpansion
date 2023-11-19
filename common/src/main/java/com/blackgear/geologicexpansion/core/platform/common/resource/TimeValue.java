package com.blackgear.geologicexpansion.core.platform.common.resource;

import net.minecraft.util.valueproviders.UniformInt;

public class TimeValue {
    public static int seconds(int seconds) {
        return seconds * 20;
    }

    public static int minutes(int minutes) {
        return seconds(minutes * 60);
    }

    public static UniformInt minutes(int min, int max) {
        return UniformInt.of(minutes(min), minutes(max));
    }
}
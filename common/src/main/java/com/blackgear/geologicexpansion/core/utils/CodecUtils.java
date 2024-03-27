package com.blackgear.geologicexpansion.core.utils;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.util.valueproviders.IntProvider;

import java.util.function.Function;

public class CodecUtils {
    public static <T> Codec<T> validate(Codec<T> codec, Function<T, DataResult<T>> function) {
        return codec.flatXmap(function, function);
    }

    public static <T extends IntProvider> Codec<T> codec(int min, int max, Codec<T> codec) {
        return validate(codec, provider -> {
            if (provider.getMinValue() < min) {
                return DataResult.error("Value provider too low: " + min + " [" + provider.getMinValue() + "-" + provider.getMaxValue() + "]");
            }
            if (provider.getMaxValue() > max) {
                return DataResult.error("Value provider too high: " + max + " [" + provider.getMinValue() + "-" + provider.getMaxValue() + "]");
            }
            return DataResult.success(provider);
        });
    }
}
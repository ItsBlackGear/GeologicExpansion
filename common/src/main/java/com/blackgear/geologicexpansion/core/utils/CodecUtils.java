package com.blackgear.geologicexpansion.core.utils;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.util.valueproviders.IntProvider;

import java.util.function.Function;

public class CodecUtils {
    public static <T> Codec<T> validate(Codec<T> codec, Function<T, DataResult<T>> function) {
        return codec.flatXmap(function, function);
    }

    public static <T extends IntProvider> Codec<T> codec(int i, int j, Codec<T> codec) {
        return validate(codec, intProvider -> {
            if (intProvider.getMinValue() < i) {
                return DataResult.error("Value provider too low: " + i + " [" + intProvider.getMinValue() + "-" + intProvider.getMaxValue() + "]");
            }
            if (intProvider.getMaxValue() > j) {
                return DataResult.error("Value provider too high: " + j + " [" + intProvider.getMinValue() + "-" + intProvider.getMaxValue() + "]");
            }
            return DataResult.success(intProvider);
        });
    }
}
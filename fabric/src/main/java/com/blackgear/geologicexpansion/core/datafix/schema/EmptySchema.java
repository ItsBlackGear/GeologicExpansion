package com.blackgear.geologicexpansion.core.datafix.schema;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.TypeTemplate;
import it.unimi.dsi.fastutil.objects.Object2ObjectMaps;
import org.jetbrains.annotations.Range;

import java.util.Map;
import java.util.function.Supplier;

public class EmptySchema extends FirstSchema {
    public EmptySchema(@Range(from = 0, to = Integer.MAX_VALUE) int versionKey) {
        super(versionKey);
    }

    @Override
    public void registerType(boolean recursive, DSL.TypeReference type, Supplier<TypeTemplate> template) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected Map<String, Type<?>> buildTypes() {
        return Object2ObjectMaps.emptyMap();
    }
}
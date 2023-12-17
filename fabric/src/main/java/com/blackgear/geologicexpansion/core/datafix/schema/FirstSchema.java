package com.blackgear.geologicexpansion.core.datafix.schema;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import org.jetbrains.annotations.Range;

import java.util.Map;
import java.util.function.Supplier;

public class FirstSchema extends Schema {
    public FirstSchema(@Range(from = 0, to = Integer.MAX_VALUE) int versionKey) {
        super(versionKey, null);
    }

    @Override
    public void registerTypes(Schema schema, Map<String, Supplier<TypeTemplate>> entityTypes, Map<String, Supplier<TypeTemplate>> blockEntityTypes) {}

    @Override
    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema schema) {
        return Map.of();
    }

    @Override
    public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema schema) {
        return Map.of();
    }
}
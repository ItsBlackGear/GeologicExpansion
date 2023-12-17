package com.blackgear.geologicexpansion.core.datafix;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.DataFixerBuilder;
import com.mojang.datafixers.schemas.Schema;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.datafix.fixes.BlockRenameFix;
import net.minecraft.util.datafix.fixes.ItemRenameFix;
import net.minecraft.util.datafix.fixes.RenameBiomesFix;
import net.minecraft.util.datafix.fixes.SimplestEntityRenameFix;
import net.minecraft.util.datafix.schemas.NamespacedSchema;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;

public class SimpleFixes {
    private SimpleFixes() {
        throw new RuntimeException("SimpleFixes contains only static declarations.");
    }

    public static void addBlockRenameFix(@NotNull DataFixerBuilder builder, @NotNull String name, @NotNull ResourceLocation oldId, @NotNull ResourceLocation newId, @NotNull Schema schema) {
        Objects.requireNonNull(builder, "DataFixerBuilder cannot be null");
        Objects.requireNonNull(name, "Fix name cannot be null");
        Objects.requireNonNull(oldId, "Old identifier cannot be null");
        Objects.requireNonNull(newId, "New identifier cannot be null");
        Objects.requireNonNull(schema, "Schema cannot be null");

        final String oldIdStr = oldId.toString(), newIdStr = newId.toString();
        builder.addFixer(BlockRenameFix.create(schema, name, (inputName) -> Objects.equals(NamespacedSchema.ensureNamespaced(inputName), oldIdStr) ? newIdStr : inputName));
    }

    public static void addEntityRenameFix(@NotNull DataFixerBuilder builder, @NotNull String name, @NotNull ResourceLocation oldId, @NotNull ResourceLocation newId, @NotNull Schema schema) {
        Objects.requireNonNull(builder, "DataFixerBuilder cannot be null");
        Objects.requireNonNull(name, "Fix name cannot be null");
        Objects.requireNonNull(oldId, "Old identifier cannot be null");
        Objects.requireNonNull(newId, "New identifier cannot be null");
        Objects.requireNonNull(schema, "Schema cannot be null");

        final String oldIdStr = oldId.toString(), newIdStr = newId.toString();
        builder.addFixer(new SimplestEntityRenameFix(name, schema, false) {
            @Override
            protected @NotNull String rename(String inputName) {
                return Objects.equals(NamespacedSchema.ensureNamespaced(inputName), oldIdStr) ? newIdStr : inputName;
            }
        });
    }

    public static void addItemRenameFix(@NotNull DataFixerBuilder builder, @NotNull String name, @NotNull ResourceLocation oldId, @NotNull ResourceLocation newId, @NotNull Schema schema) {
        Objects.requireNonNull(builder, "DataFixerBuilder cannot be null");
        Objects.requireNonNull(name, "Fix name cannot be null");
        Objects.requireNonNull(oldId, "Old identifier cannot be null");
        Objects.requireNonNull(newId, "New identifier cannot be null");
        Objects.requireNonNull(schema, "Schema cannot be null");

        final String oldIdStr = oldId.toString(), newIdStr = newId.toString();
        builder.addFixer(ItemRenameFix.create(schema, name, (inputName) -> Objects.equals(NamespacedSchema.ensureNamespaced(inputName), oldIdStr) ? newIdStr : inputName));
    }

    public static void addBiomeRenameFix(@NotNull DataFixerBuilder builder, @NotNull String name, @NotNull Map<ResourceLocation, ResourceLocation> changes, @NotNull Schema schema) {
        Objects.requireNonNull(builder, "DataFixerBuilder cannot be null");
        Objects.requireNonNull(name, "Fix name cannot be null");
        Objects.requireNonNull(changes, "Changes cannot be null");
        Objects.requireNonNull(schema, "Schema cannot be null");

        var mapBuilder = ImmutableMap.<String, String>builder();
        for (var entry : changes.entrySet()) {
            mapBuilder.put(entry.getKey().toString(), entry.getValue().toString());
        }
        builder.addFixer(new RenameBiomesFix(schema, false, name, mapBuilder.build()));
    }
}
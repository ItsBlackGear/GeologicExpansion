package com.blackgear.geologicexpansion.core.datafix;

import com.blackgear.geologicexpansion.core.datafix.schema.EmptySchema;
import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.schemas.Schema;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.datafix.DataFixTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

public class NoOpDataFixesInternals extends DataFixesInternals {
    private final Schema schema;
    private boolean frozen;

    public NoOpDataFixesInternals() {
        this.schema = new EmptySchema(0);
        this.frozen = false;
    }

    @Override
    public void registerFixer(@NotNull String modId, @Range(from = 0, to = Integer.MAX_VALUE) int currentVersion, @NotNull DataFixer dataFixer) {}

    @Override
    public @Nullable DataFixerEntry getFixerEntry(@NotNull String modId) {
        return null;
    }

    @Override
    public @NotNull Schema createBaseSchema() {
        return this.schema;
    }

    @Override
    public @NotNull CompoundTag updateWithAllFixers(@NotNull DataFixTypes dataFixTypes, @NotNull CompoundTag compound) {
        return compound.copy();
    }

    @Override
    public @NotNull CompoundTag addModDataVersions(@NotNull CompoundTag compound) {
        return compound;
    }

    @Override
    public void freeze() {
        this.frozen = true;
    }

    @Override
    public boolean isFrozen() {
        return this.frozen;
    }
}
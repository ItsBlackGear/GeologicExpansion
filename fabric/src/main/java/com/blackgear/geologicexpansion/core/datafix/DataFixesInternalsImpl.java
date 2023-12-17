package com.blackgear.geologicexpansion.core.datafix;

import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.objects.Object2ReferenceOpenHashMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.util.datafix.DataFixTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.util.Collections;
import java.util.Map;

public class DataFixesInternalsImpl extends DataFixesInternals {
    private final @NotNull Schema latest;
    private Map<String, DataFixerEntry> dataFixers;
    private boolean frozen;

    public DataFixesInternalsImpl(@NotNull Schema latest) {
        this.latest = latest;
        this.dataFixers = new Object2ReferenceOpenHashMap<>();
        this.frozen = false;
    }

    @Override
    public void registerFixer(@NotNull String modId, @Range(from = 0, to = Integer.MAX_VALUE) int currentVersion, @NotNull DataFixer dataFixer) {
        if (this.dataFixers.containsKey(modId)) {
            throw new IllegalArgumentException("Mod '" + modId + "' already has a registered data fixer");
        }

        this.dataFixers.put(modId, new DataFixerEntry(dataFixer, currentVersion));
    }

    @Override
    public @Nullable DataFixerEntry getFixerEntry(@NotNull String modId) {
        return this.dataFixers.get(modId);
    }

    @Override
    public @NotNull Schema createBaseSchema() {
        return new Schema(0, this.latest);
    }

    @Override
    public @NotNull CompoundTag updateWithAllFixers(@NotNull DataFixTypes dataFixTypes, @NotNull CompoundTag compound) {
        var current = new Dynamic<>(NbtOps.INSTANCE, compound);

        for (Map.Entry<String, DataFixerEntry> entry : this.dataFixers.entrySet()) {
            int dataVersion = getModDataVersion(compound, entry.getKey());
            DataFixerEntry dataFixerEntry = entry.getValue();

            current = dataFixerEntry.fixer().update(dataFixTypes.getType(), current, dataVersion, dataFixerEntry.currentVersion());
        }

        return (CompoundTag)current.getValue();
    }

    @Override
    public @NotNull CompoundTag addModDataVersions(@NotNull CompoundTag compound) {
        for (Map.Entry<String, DataFixerEntry> entry : this.dataFixers.entrySet()) {
            compound.putInt(entry.getKey() + "_DataVersion", entry.getValue().currentVersion());
        }

        return compound;
    }

    @Override
    public void freeze() {
        if (!this.frozen) {
            this.dataFixers = Collections.unmodifiableMap(this.dataFixers);
        }

        this.frozen = true;
    }

    @Override
    public boolean isFrozen() {
        return this.frozen;
    }
}
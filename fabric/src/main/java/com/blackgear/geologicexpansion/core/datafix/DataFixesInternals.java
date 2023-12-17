package com.blackgear.geologicexpansion.core.datafix;

import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.schemas.Schema;
import net.minecraft.SharedConstants;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.util.datafix.DataFixers;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

public abstract class DataFixesInternals {
    private static DataFixesInternals instance;

    @Contract(pure = true)
    @Range(from = 0, to = Integer.MAX_VALUE)
    public static int getModDataVersion(@NotNull CompoundTag tag, @NotNull String modId) {
        return tag.getInt(modId + "_DataVersion");
    }

    public static @NotNull DataFixesInternals get() {
        if (instance == null) {
            Schema latest;
            try {
                latest = DataFixers.getDataFixer().getSchema(DataFixUtils.makeKey(SharedConstants.getCurrentVersion().getDataVersion().getVersion()));
            } catch (Exception e) {
                latest = null;
            }

            if (latest == null) {
                instance = new NoOpDataFixesInternals();
            } else {
                instance = new DataFixesInternalsImpl(latest);
            }
        }

        return instance;
    }

    public abstract void registerFixer(@NotNull String modId, @Range(from = 0, to = Integer.MAX_VALUE) int currentVersion, @NotNull DataFixer dataFixer);

    public abstract @Nullable DataFixerEntry getFixerEntry(@NotNull String modId);

    @Contract(value = "-> new", pure = true)
    public abstract @NotNull Schema createBaseSchema();

    public abstract @NotNull CompoundTag updateWithAllFixers(@NotNull DataFixTypes dataFixTypes, @NotNull CompoundTag compound);

    public abstract @NotNull CompoundTag addModDataVersions(@NotNull CompoundTag compound);

    public abstract void freeze();

    @Contract(pure = true)
    public abstract boolean isFrozen();

    public record DataFixerEntry(DataFixer fixer, int currentVersion) {}
}
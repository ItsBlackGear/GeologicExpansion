package com.blackgear.geologicexpansion.core.datafix;

import com.google.common.base.Preconditions;
import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.schemas.Schema;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;

public class DataFixes {
    private DataFixes() {
        throw new UnsupportedOperationException("DataFixes contains only static declarations");
    }

    public static final BiFunction<Integer, Schema, Schema> BASE_SCHEMA = (version, parent) -> {
        Preconditions.checkArgument(version == 0, "version must be 0");
        Preconditions.checkArgument(parent == null, "parent must be null");
        return DataFixesInternals.get().createBaseSchema();
    };

    public static void registerFixer(@NotNull String modId, @Range(from = 0, to = Integer.MAX_VALUE) int currentVersion, @NotNull DataFixer dataFixer) {
        Objects.requireNonNull(modId, "modId cannot be null");
        //noinspection ConstantValue
        Preconditions.checkArgument(currentVersion >= 0, "currentVersion must be positive");
        Objects.requireNonNull(dataFixer, "dataFixer cannot be null");

        if (isFrozen()) {
            throw new IllegalStateException("Can't register data fixer after registry is frozen");
        }

        DataFixesInternals.get().registerFixer(modId, currentVersion, dataFixer);
    }

    public static void registerFixer(@NotNull ModContainer mod, @Range(from = 0, to = Integer.MAX_VALUE) int currentVersion, @NotNull DataFixer dataFixer) {
        Objects.requireNonNull(mod, "mod cannot be null");
        registerFixer(mod.getMetadata().getId(), currentVersion, dataFixer);
    }

    public static void buildAndRegisterFixer(@NotNull ModContainer mod, @NotNull ModDataFixerBuilder builder) {
        Objects.requireNonNull(mod, "mod cannot be null");
        Objects.requireNonNull(builder, "data fixer builder cannot be null");

        registerFixer(mod.getMetadata().getId(), builder.getDataVersion(), builder.build(Util::bootstrapExecutor));
    }

    public static @NotNull Optional<DataFixer> getFixer(@NotNull String modId) {
        Objects.requireNonNull(modId, "modId cannot be null");

        DataFixesInternals.DataFixerEntry entry = DataFixesInternals.get().getFixerEntry(modId);
        if (entry == null) {
            return Optional.empty();
        }

        return Optional.of(entry.fixer());
    }

    @Contract(pure = true)
    @Range(from = 0, to = Integer.MAX_VALUE)
    public static int getModDataVersion(@NotNull CompoundTag compound, @NotNull String modId) {
        Objects.requireNonNull(compound, "compound cannot be null");
        Objects.requireNonNull(modId, "modId cannot be null");

        return DataFixesInternals.getModDataVersion(compound, modId);
    }

    @Contract(pure = true)
    public static boolean isFrozen() {
        return DataFixesInternals.get().isFrozen();
    }
}
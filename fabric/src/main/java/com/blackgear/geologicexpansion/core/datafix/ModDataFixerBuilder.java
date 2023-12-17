package com.blackgear.geologicexpansion.core.datafix;

import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.DataFixerBuilder;
import net.minecraft.SharedConstants;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.concurrent.Executor;
import java.util.function.Supplier;

public class ModDataFixerBuilder extends DataFixerBuilder {
    protected final int dataVersion;

    public ModDataFixerBuilder(@Range(from = 0, to = Integer.MAX_VALUE) int dataVersion) {
        super(dataVersion);
        this.dataVersion = dataVersion;
    }

    @Range(from = 0, to = Integer.MAX_VALUE)
    public int getDataVersion() {
        return this.dataVersion;
    }

    @Contract(value = "_ -> new")
    public @NotNull DataFixer build(@NotNull Supplier<Executor> executorGetter) {
        return switch (SharedConstants.DATAFIXER_OPTIMIZATION_OPTION) {
            case UNINITIALIZED_UNOPTIMIZED, INITIALIZED_UNOPTIMIZED -> this.buildUnoptimized();
            case UNINITIALIZED_OPTIMIZED, INITIALIZED_OPTIMIZED -> this.buildOptimized(executorGetter.get());
        };
    }
}
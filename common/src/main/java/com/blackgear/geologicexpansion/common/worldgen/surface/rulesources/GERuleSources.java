package com.blackgear.geologicexpansion.common.worldgen.surface.rulesources;

import com.blackgear.geologicexpansion.core.GeologicExpansion;
import com.blackgear.geologicexpansion.core.platform.CoreRegistry;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.SurfaceRules;

import java.util.function.Supplier;

public class GERuleSources {
    public static final CoreRegistry<Codec<? extends SurfaceRules.RuleSource>> RULES = CoreRegistry.create(Registry.RULE, GeologicExpansion.MOD_ID);

    public static final Supplier<Codec<SavannaBandlands>> SAVANNA_BANDLANDS = create("savanna_bandlands", SavannaBandlands.CODEC);

    private static <T extends SurfaceRules.RuleSource> Supplier<Codec<T>> create(String key, KeyDispatchDataCodec<T> codec) {
        return RULES.register(key, codec::codec);
    }
}
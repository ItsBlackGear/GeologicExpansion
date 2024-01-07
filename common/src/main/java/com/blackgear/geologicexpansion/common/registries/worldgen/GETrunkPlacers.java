package com.blackgear.geologicexpansion.common.registries.worldgen;

import com.blackgear.geologicexpansion.common.worldgen.features.tree.MapleTrunkPlacer;
import com.blackgear.geologicexpansion.core.GeologicExpansion;
import com.blackgear.geologicexpansion.core.mixin.access.TrunkPlacerTypeAccessor;
import com.blackgear.geologicexpansion.core.platform.CoreRegistry;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import java.util.function.Supplier;

public class GETrunkPlacers {
    public static final CoreRegistry<TrunkPlacerType<?>> TRUNK_PLACERS = CoreRegistry.create(Registry.TRUNK_PLACER_TYPES, GeologicExpansion.MOD_ID);

    public static final Supplier<TrunkPlacerType<MapleTrunkPlacer>> MAPLE_TRUNK_PLACER = create("maple_trunk_placer", MapleTrunkPlacer.CODEC);

    private static <P extends TrunkPlacer> Supplier<TrunkPlacerType<P>> create(String key, Codec<P> codec) {
        return TRUNK_PLACERS.register(key, () -> TrunkPlacerTypeAccessor.createTrunkPlacerType(codec));
    }
}
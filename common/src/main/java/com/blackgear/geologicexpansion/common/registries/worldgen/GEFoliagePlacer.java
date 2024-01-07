package com.blackgear.geologicexpansion.common.registries.worldgen;

import com.blackgear.geologicexpansion.common.worldgen.features.tree.MapleFoliagePlacer;
import com.blackgear.geologicexpansion.core.GeologicExpansion;
import com.blackgear.geologicexpansion.core.mixin.access.FoliagePlacerTypeAccessor;
import com.blackgear.geologicexpansion.core.platform.CoreRegistry;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

import java.util.function.Supplier;

public class GEFoliagePlacer {
    public static final CoreRegistry<FoliagePlacerType<?>> FOLIAGE_PLACERS = CoreRegistry.create(Registry.FOLIAGE_PLACER_TYPES, GeologicExpansion.MOD_ID);

    public static final Supplier<FoliagePlacerType<MapleFoliagePlacer>> MAPLE_FOLIAGE_PLACER = FOLIAGE_PLACERS.register("cherry_foliage_placer", () -> FoliagePlacerTypeAccessor.createFoliagePlacerType(MapleFoliagePlacer.CODEC));
}
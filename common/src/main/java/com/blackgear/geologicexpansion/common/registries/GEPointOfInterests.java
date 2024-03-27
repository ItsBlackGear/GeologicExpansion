package com.blackgear.geologicexpansion.common.registries;

import com.blackgear.geologicexpansion.core.GeologicExpansion;
import com.blackgear.geologicexpansion.core.platform.CoreRegistry;
import com.google.common.collect.ImmutableSet;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.village.poi.PoiType;

import java.util.function.Supplier;

public class GEPointOfInterests {
    public static final CoreRegistry<PoiType> POINTS = CoreRegistry.create(Registry.POINT_OF_INTEREST_TYPE, GeologicExpansion.MOD_ID);

    public static final ResourceKey<PoiType> GEOLOGIST_TABLE = create("geologist_table");
    public static final Supplier<PoiType> GEOLOGIST_TABLE_POI = POINTS.register("geologist_table",
        () -> new PoiType(
            ImmutableSet.copyOf(GEBlocks.GEOLOGIST_TABLE.get().getStateDefinition().getPossibleStates()),
            1,
            1
        )
    );

    private static ResourceKey<PoiType> create(String key) {
        return ResourceKey.create(Registry.POINT_OF_INTEREST_TYPE_REGISTRY, new ResourceLocation(GeologicExpansion.MOD_ID, key));
    }
}
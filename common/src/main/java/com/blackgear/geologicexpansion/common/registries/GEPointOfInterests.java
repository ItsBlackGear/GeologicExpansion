package com.blackgear.geologicexpansion.common.registries;

import com.blackgear.geologicexpansion.core.GeologicExpansion;
import com.blackgear.geologicexpansion.core.mixin.access.PoiTypesAccessor;
import com.blackgear.geologicexpansion.core.platform.CoreRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.village.poi.PoiType;

import java.util.function.Supplier;

public class GEPointOfInterests {
    public static final CoreRegistry<PoiType> POINTS = CoreRegistry.create(Registry.POINT_OF_INTEREST_TYPE, GeologicExpansion.MOD_ID);

    public static final ResourceKey<PoiType> GEOLOGIST_TABLE = create("geologist_table", () -> new PoiType(PoiTypesAccessor.callGetBlockStates(GEBlocks.GEOLOGIST_TABLE.get()), 1, 1));

    public static ResourceKey<PoiType> create(String key, Supplier<PoiType> type) {
        ResourceKey<PoiType> resource = ResourceKey.create(Registry.POINT_OF_INTEREST_TYPE_REGISTRY, new ResourceLocation(GeologicExpansion.MOD_ID, key));
        POINTS.register(key, type);
        PoiTypesAccessor.callRegisterBlockStates(Registry.POINT_OF_INTEREST_TYPE.getHolderOrThrow(resource));
        return resource;
    }
}
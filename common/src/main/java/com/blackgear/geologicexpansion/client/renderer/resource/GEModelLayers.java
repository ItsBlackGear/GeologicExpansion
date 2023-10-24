package com.blackgear.geologicexpansion.client.renderer.resource;

import com.blackgear.geologicexpansion.core.GeologicExpansion;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class GEModelLayers {
    public static final ModelLayerLocation DUCK = create("duck");

    private static ModelLayerLocation create(String key) {
        return create(key, "main");
    }

    private static ModelLayerLocation create(String key, String layer) {
        return new ModelLayerLocation(new ResourceLocation(GeologicExpansion.MOD_ID, key), layer);
    }
}
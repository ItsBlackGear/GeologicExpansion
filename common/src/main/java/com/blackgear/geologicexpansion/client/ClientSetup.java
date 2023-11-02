package com.blackgear.geologicexpansion.client;

import com.blackgear.geologicexpansion.client.renderer.entity.DuckRenderer;
import com.blackgear.geologicexpansion.client.renderer.entity.model.AltDuckModel;
import com.blackgear.geologicexpansion.client.renderer.entity.model.DuckModel;
import com.blackgear.geologicexpansion.client.renderer.resource.GEModelLayers;
import com.blackgear.geologicexpansion.common.registries.GEEntities;
import com.blackgear.geologicexpansion.core.platform.client.RenderRegistry;

public class ClientSetup {
    public static void onInstance() {
        // ========== ENTITY RENDERER REGISTRY =========================================================================
        RenderRegistry.entity(GEEntities.DUCK, DuckRenderer::new, GEModelLayers.DUCK, AltDuckModel::createBodyLayer);
    }

    public static void postInstance() {

    }
}
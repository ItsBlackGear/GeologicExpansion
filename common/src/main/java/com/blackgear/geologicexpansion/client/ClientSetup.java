package com.blackgear.geologicexpansion.client;

import com.blackgear.geologicexpansion.client.renderer.entity.DuckRenderer;
import com.blackgear.geologicexpansion.client.renderer.entity.model.DuckModel;
import com.blackgear.geologicexpansion.client.renderer.resource.GEModelLayers;
import com.blackgear.geologicexpansion.common.registries.GEBlocks;
import com.blackgear.geologicexpansion.common.registries.GEEntities;
import com.blackgear.geologicexpansion.core.platform.client.RenderRegistry;
import net.minecraft.client.renderer.RenderType;

public class ClientSetup {
    public static void onInstance() {
        // ========== ENTITY RENDERER REGISTRY =========================================================================
        RenderRegistry.entity(GEEntities.DUCK, DuckRenderer::new, GEModelLayers.DUCK, DuckModel::createBodyLayer);

        RenderRegistry.block(RenderType.cutout(), GEBlocks.OVERGROWTH.get());
    }

    public static void postInstance() {

    }
}
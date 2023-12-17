package com.blackgear.geologicexpansion.client;

import com.blackgear.geologicexpansion.client.particle.GeyserEruptionParticle;
import com.blackgear.geologicexpansion.client.registries.GEParticleTypes;
import com.blackgear.geologicexpansion.client.renderer.entity.DuckRenderer;
import com.blackgear.geologicexpansion.client.renderer.entity.model.DuckModel;
import com.blackgear.geologicexpansion.client.renderer.resource.GEModelLayers;
import com.blackgear.geologicexpansion.common.registries.GEBlocks;
import com.blackgear.geologicexpansion.common.registries.GEEntities;
import com.blackgear.geologicexpansion.core.platform.client.ParticleRegistry;
import com.blackgear.geologicexpansion.core.platform.client.RenderRegistry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

public class ClientSetup {
    public static void onInstance() {
        // ========== ENTITY RENDERER REGISTRY =========================================================================
        RenderRegistry.entity(GEEntities.DUCK, DuckRenderer::new, GEModelLayers.DUCK, DuckModel::createBodyLayer);
        RenderRegistry.renderer(GEEntities.DUCK_EGG, ThrownItemRenderer::new);

        ParticleRegistry.create(GEParticleTypes.GEYSER_ERUPTION, GeyserEruptionParticle.Provider::new);
    }

    public static void postInstance() {
        RenderRegistry.block(RenderType.cutout(),
                GEBlocks.OVERGROWTH.get(),
                GEBlocks.FIERY_HIBISCUS.get()
        );
    }
}
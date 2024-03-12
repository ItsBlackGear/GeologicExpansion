package com.blackgear.geologicexpansion.client;

import com.blackgear.geologicexpansion.client.particle.GeyserEruptionParticle;
import com.blackgear.geologicexpansion.client.particle.MapleParticle;
import com.blackgear.geologicexpansion.client.registries.GEParticleTypes;
import com.blackgear.geologicexpansion.client.renderer.entity.DuckEntityRenderer;
import com.blackgear.geologicexpansion.client.renderer.entity.model.DuckModel;
import com.blackgear.geologicexpansion.client.renderer.entity.GrizzlyRenderer;
import com.blackgear.geologicexpansion.client.renderer.entity.model.GrizzlyModel;
import com.blackgear.geologicexpansion.client.renderer.resource.GEModelLayers;
import com.blackgear.geologicexpansion.common.registries.GEBlocks;
import com.blackgear.geologicexpansion.common.registries.GEEntities;
import com.blackgear.geologicexpansion.core.platform.client.ParticleRegistry;
import com.blackgear.geologicexpansion.core.platform.client.RenderRegistry;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.block.state.BlockState;

public class ClientSetup {
    public static void onInstance() {
        // ========== ENTITY RENDERER REGISTRY =========================================================================
        RenderRegistry.entity(GEEntities.DUCK, DuckEntityRenderer::new, GEModelLayers.DUCK_ENTITY, DuckModel::createBodyLayer);
        RenderRegistry.renderer(GEEntities.DUCK_EGG, ThrownItemRenderer::new);
        RenderRegistry.entity(GEEntities.GRIZZLY, GrizzlyRenderer::new, GEModelLayers.GRIZZLY_BEAR, GrizzlyModel::createBodyLayer);

        // ========== PARTICLE REGISTRY ================================================================================
        ParticleRegistry.create(GEParticleTypes.GEYSER_ERUPTION, GeyserEruptionParticle.Provider::new);
        ParticleRegistry.create(GEParticleTypes.RED_MAPLE_LEAVES, MapleParticle.Provider::new);
        ParticleRegistry.create(GEParticleTypes.BROWN_MAPLE_LEAVES, MapleParticle.Provider::new);

        // ========== COLOR REGISTRY ===================================================================================
        BlockColor color = (state, level, pos, tint) -> {
            if (level != null && pos != null) {
                return BiomeColors.getAverageGrassColor(level, pos);
            }

            return GrassColor.get(0.5D, 1.0D);
        };

        RenderRegistry.blockColor(color, GEBlocks.OVERGROWTH);

        RenderRegistry.itemColor((stack, tint) -> {
            BlockState state = ((BlockItem)stack.getItem()).getBlock().defaultBlockState();
            return color.getColor(state, null, null, tint);
        }, GEBlocks.OVERGROWTH);
    }

    public static void postInstance() {
        // ========== BLOCK RENDERER REGISTRY ==========================================================================
        RenderRegistry.block(RenderType.cutout(),
            GEBlocks.OVERGROWTH.get(),
            GEBlocks.FIERY_HIBISCUS.get()
        );
        RenderRegistry.block(RenderType.cutoutMipped(),
            GEBlocks.RED_MAPLE_LEAVES.get(),
            GEBlocks.BROWN_MAPLE_LEAVES.get(),
            GEBlocks.ASPEN_LEAVES.get(),
            GEBlocks.RED_MAPLE_LEAF_CARPET.get(),
            GEBlocks.BROWN_MAPLE_LEAF_CARPET.get()
        );
    }
}
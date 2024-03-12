package com.blackgear.geologicexpansion.client.renderer.entity;

import com.blackgear.geologicexpansion.client.renderer.entity.layer.GrizzlyHeldItemLayer;
import com.blackgear.geologicexpansion.client.renderer.entity.model.GrizzlyModel;
import com.blackgear.geologicexpansion.client.renderer.resource.GEModelLayers;
import com.blackgear.geologicexpansion.common.entity.bear.Grizzly;
import com.blackgear.geologicexpansion.core.GeologicExpansion;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class GrizzlyRenderer extends MobRenderer<Grizzly, GrizzlyModel<Grizzly>> {
    public GrizzlyRenderer(EntityRendererProvider.Context context) {
        super(context, new GrizzlyModel<>(context.bakeLayer(GEModelLayers.GRIZZLY_BEAR)), 0.9F);
        this.addLayer(new GrizzlyHeldItemLayer(this, context.getItemInHandRenderer()));
    }

    @Override
    public ResourceLocation getTextureLocation(Grizzly entity) {
        if (entity.isSleeping()) {
            return new ResourceLocation(GeologicExpansion.MOD_ID, "textures/entity/bear/grizzly/grizzly_sleep.png");
        }

        return new ResourceLocation(GeologicExpansion.MOD_ID, "textures/entity/bear/grizzly/grizzly.png");
    }

    @Override
    protected void scale(Grizzly livingEntity, PoseStack matrixStack, float partialTickTime) {
        matrixStack.scale(1.2F, 1.2F, 1.2F);
        super.scale(livingEntity, matrixStack, partialTickTime);
    }
}
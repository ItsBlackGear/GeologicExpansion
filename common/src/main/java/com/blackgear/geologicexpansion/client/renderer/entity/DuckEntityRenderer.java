package com.blackgear.geologicexpansion.client.renderer.entity;

import com.blackgear.geologicexpansion.client.renderer.entity.layer.DuckEntityHeldItemLayer;
import com.blackgear.geologicexpansion.client.renderer.entity.model.DuckEntityModel;
import com.blackgear.geologicexpansion.client.renderer.resource.GEModelLayers;
import com.blackgear.geologicexpansion.common.entity.duck.DuckEntity;
import com.blackgear.geologicexpansion.core.GeologicExpansion;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class DuckEntityRenderer extends MobRenderer<DuckEntity, DuckEntityModel<DuckEntity>> {
    private static final ResourceLocation DUCK_LOCATION = new ResourceLocation(GeologicExpansion.MOD_ID, "textures/entity/duck.png");

    public DuckEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new DuckEntityModel<>(context.bakeLayer(GEModelLayers.DUCK_ENTITY)), 0.6F);
        this.addLayer(new DuckEntityHeldItemLayer<>(this, context.getItemInHandRenderer()));
    }

    @Override
    public ResourceLocation getTextureLocation(DuckEntity entity) {
        return DUCK_LOCATION;
    }

    @Override
    public void render(DuckEntity entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
        if (entity.isBaby()) {
            matrixStack.scale(0.5F, 0.5F, 0.5F);
        }

        super.render(entity, entityYaw, partialTicks, matrixStack, buffer, packedLight);
    }
}
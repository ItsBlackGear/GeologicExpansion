package com.blackgear.geologicexpansion.client.renderer.entity;

import com.blackgear.geologicexpansion.client.renderer.entity.layer.DuckHeldItemLayer;
import com.blackgear.geologicexpansion.client.renderer.entity.model.DuckModel;
import com.blackgear.geologicexpansion.client.renderer.resource.GEModelLayers;
import com.blackgear.geologicexpansion.common.entity.duck.Duck;
import com.blackgear.geologicexpansion.core.GeologicExpansion;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

@Environment(EnvType.CLIENT)
public class DuckRenderer extends MobRenderer<Duck, DuckModel<Duck>> {
    private static final ResourceLocation DUCK_LOCATION = new ResourceLocation(GeologicExpansion.MOD_ID, "textures/entity/duck.png");

    public DuckRenderer(EntityRendererProvider.Context context) {
        super(context, new DuckModel<>(context.bakeLayer(GEModelLayers.DUCK)), 0.3F);
        this.addLayer(new DuckHeldItemLayer(this, context.getItemInHandRenderer()));
    }

    @Override
    public ResourceLocation getTextureLocation(Duck entity) {
        return DUCK_LOCATION;
    }

    @Override
    protected float getBob(Duck livingBase, float partialTicks) {
        float f = Mth.lerp(partialTicks, livingBase.oFlap, livingBase.flap);
        float g = Mth.lerp(partialTicks, livingBase.oFlapSpeed, livingBase.flapSpeed);
        return (Mth.sin(f) + 1.0F) * g;
    }

    @Override
    protected void setupRotations(Duck duck, PoseStack matrixStack, float ageInTicks, float rotationYaw, float partialTicks) {
        super.setupRotations(duck, matrixStack, ageInTicks, rotationYaw, partialTicks);
        if (!((double)duck.animationSpeed < 0.01) && !duck.isInWaterOrBubble()) {
            // Add waddle animation
            float f = 13.0F;
            float g = duck.animationPosition - duck.animationSpeed * (1.0F - partialTicks) + 6.0F;
            float h = (Math.abs(g % f - 6.5F) - 3.25F) / 3.25F;
            matrixStack.mulPose(Vector3f.ZP.rotationDegrees(6.5F * h));
        }
    }
}
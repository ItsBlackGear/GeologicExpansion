package com.blackgear.geologicexpansion.client.renderer.entity;

import com.blackgear.geologicexpansion.client.renderer.entity.layer.DuckHeldItemLayer;
import com.blackgear.geologicexpansion.client.renderer.entity.model.DuckModel;
import com.blackgear.geologicexpansion.client.renderer.resource.GEModelLayers;
import com.blackgear.geologicexpansion.common.entity.duck.AbstractDuck;
import com.blackgear.geologicexpansion.core.GeologicExpansion;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

@Environment(EnvType.CLIENT)
public class DuckRenderer<T extends AbstractDuck> extends MobRenderer<T, DuckModel<T>> {
    private static final ResourceLocation DUCK_LOCATION = new ResourceLocation(GeologicExpansion.MOD_ID, "textures/entity/duck.png");

    public DuckRenderer(EntityRendererProvider.Context context) {
        super(context, new DuckModel<>(context.bakeLayer(GEModelLayers.DUCK)), 0.3F);
        this.addLayer(new DuckHeldItemLayer<>(this, context.getItemInHandRenderer()));
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return DUCK_LOCATION;
    }

    @Override
    protected float getBob(T livingBase, float partialTicks) {
        float f = Mth.lerp(partialTicks, livingBase.oFlap, livingBase.flap);
        float g = Mth.lerp(partialTicks, livingBase.oFlapSpeed, livingBase.flapSpeed);
        return (Mth.sin(f) + 1.0F) * g;
    }
}
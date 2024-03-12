package com.blackgear.geologicexpansion.client.renderer.entity.layer;

import com.blackgear.geologicexpansion.client.renderer.entity.model.DuckModel;
import com.blackgear.geologicexpansion.common.entity.duck.Duck;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.EquipmentSlot;

public class DuckHeldItemLayer<T extends Duck> extends RenderLayer<T, DuckModel<T>> {
    private final ItemInHandRenderer itemRenderer;

    public DuckHeldItemLayer(RenderLayerParent<T, DuckModel<T>> parent, ItemInHandRenderer itemRenderer) {
        super(parent);
        this.itemRenderer = itemRenderer;
    }

    @Override
    public void render(
        PoseStack stack,
        MultiBufferSource buffer,
        int packedLight,
        T duck,
        float limbSwing,
        float limbSwingAmount,
        float partialTick,
        float ageInTicks,
        float netHeadYaw,
        float headPitch
    ) {
        stack.pushPose();
        var model = this.getParentModel();
        model.duck.translateAndRotate(stack);
        model.head.translateAndRotate(stack);
        model.beak.translateAndRotate(stack);
        stack.translate(0.0F, 0.0, -0.225F);
        stack.mulPose(Vector3f.YP.rotationDegrees(model.head.yRot));
        stack.mulPose(Vector3f.XP.rotationDegrees(model.head.xRot));
        stack.scale(0.75F, 0.75F, 0.75F);

        stack.mulPose(Vector3f.XP.rotationDegrees(90.0F));

        this.itemRenderer.renderItem(duck, duck.getItemBySlot(EquipmentSlot.MAINHAND), ItemTransforms.TransformType.GROUND, false, stack, buffer, packedLight);
        stack.popPose();
    }
}
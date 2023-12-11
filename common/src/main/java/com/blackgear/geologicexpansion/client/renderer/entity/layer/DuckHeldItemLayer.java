package com.blackgear.geologicexpansion.client.renderer.entity.layer;

import com.blackgear.geologicexpansion.client.renderer.entity.model.DuckModel;
import com.blackgear.geologicexpansion.common.entity.duck.Duck;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

@Environment(EnvType.CLIENT)
public class DuckHeldItemLayer extends RenderLayer<Duck, DuckModel<Duck>> {
    private final ItemInHandRenderer itemInHandRenderer;

    public DuckHeldItemLayer(RenderLayerParent<Duck, DuckModel<Duck>> renderLayerParent, ItemInHandRenderer itemInHandRenderer) {
        super(renderLayerParent);
        this.itemInHandRenderer = itemInHandRenderer;
    }

    @Override
    public void render(PoseStack matrices, MultiBufferSource buffer, int packedLight, Duck duck, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        matrices.pushPose();
        matrices.translate(this.getParentModel().head.x / 16.0F, this.getParentModel().head.y / 16.0F, this.getParentModel().head.z / 16.0F);
        matrices.mulPose(Axis.YP.rotationDegrees(netHeadYaw));
        matrices.mulPose(Axis.XP.rotationDegrees(headPitch));

        matrices.translate(0.0F, -0.175F, -0.35F);
        matrices.mulPose(Axis.XP.rotationDegrees(90.0F));

        ItemStack stack = duck.getItemBySlot(EquipmentSlot.MAINHAND);
        this.itemInHandRenderer.renderItem(duck, stack, ItemDisplayContext.GROUND, false, matrices, buffer, packedLight);
        matrices.popPose();
    }
}
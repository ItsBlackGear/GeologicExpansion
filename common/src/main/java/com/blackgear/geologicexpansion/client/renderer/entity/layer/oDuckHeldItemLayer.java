package com.blackgear.geologicexpansion.client.renderer.entity.layer;

import com.blackgear.geologicexpansion.client.renderer.entity.model.oDuckModel;
import com.blackgear.geologicexpansion.common.entity.duck.ODuck;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

@Environment(EnvType.CLIENT)
public class oDuckHeldItemLayer extends RenderLayer<ODuck, oDuckModel<ODuck>> {
    private final ItemInHandRenderer itemInHandRenderer;

    public oDuckHeldItemLayer(RenderLayerParent<ODuck, oDuckModel<ODuck>> renderLayerParent, ItemInHandRenderer itemInHandRenderer) {
        super(renderLayerParent);
        this.itemInHandRenderer = itemInHandRenderer;
    }

    @Override
    public void render(PoseStack matrices, MultiBufferSource buffer, int packedLight, ODuck duck, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        matrices.pushPose();
        matrices.translate(this.getParentModel().head.x / 16.0F, this.getParentModel().head.y / 16.0F, this.getParentModel().head.z / 16.0F);
        matrices.mulPose(Vector3f.YP.rotationDegrees(netHeadYaw));
        matrices.mulPose(Vector3f.XP.rotationDegrees(headPitch));

        matrices.translate(0.0F, -0.175F, -0.35F);
        matrices.mulPose(Vector3f.XP.rotationDegrees(90.0F));

        ItemStack stack = duck.getItemBySlot(EquipmentSlot.MAINHAND);
        this.itemInHandRenderer.renderItem(duck, stack, ItemTransforms.TransformType.GROUND, false, matrices, buffer, packedLight);
        matrices.popPose();
    }
}
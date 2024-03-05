package com.blackgear.geologicexpansion.client.renderer.entity.layer;

import com.blackgear.geologicexpansion.client.renderer.entity.model.GrizzlyBearModel;
import com.blackgear.geologicexpansion.common.entity.bear.Grizzly;
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
public class GrizzlyHeldItemLayer extends RenderLayer<Grizzly, GrizzlyBearModel<Grizzly>> {
    private final ItemInHandRenderer itemRenderer;

    public GrizzlyHeldItemLayer(RenderLayerParent<Grizzly, GrizzlyBearModel<Grizzly>> parent, ItemInHandRenderer itemRenderer) {
        super(parent);
        this.itemRenderer = itemRenderer;
    }

    @Override
    public void render(
        PoseStack poseStack,
        MultiBufferSource buffer,
        int packedLight,
        Grizzly bear,
        float limbSwing,
        float limbSwingAmount,
        float partialTick,
        float ageInTicks,
        float netHeadYaw,
        float headPitch
    ) {
        boolean sleeping = bear.isSleeping();
        boolean baby = bear.isBaby();
        poseStack.pushPose();
        if (baby) {
            poseStack.scale(0.75F, 0.75F, 0.75F);
            poseStack.translate(0.0, 0.0, 0.209375F);
        }

        poseStack.translate(this.getParentModel().head.x / 16.0F, this.getParentModel().head.y / 16.0F, this.getParentModel().head.z / 16.0F);
        poseStack.mulPose(Vector3f.YP.rotationDegrees(netHeadYaw));
        poseStack.mulPose(Vector3f.XP.rotationDegrees(headPitch));
        if (bear.isBaby()) {
            if (sleeping) {
                poseStack.translate(0.4F, 0.26F, 0.15F);
            } else {
                poseStack.translate(0.0F, 0.9, -0.95);
            }
        } else if (sleeping) {
            poseStack.translate(0.46F, 0.26F, 0.22F);
        } else {
            poseStack.translate(0.0F, 0.15F, -1.25);
        }

        poseStack.mulPose(Vector3f.XP.rotationDegrees(90.0F));
        if (sleeping) {
            poseStack.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
        }

        ItemStack itemStack = bear.getItemBySlot(EquipmentSlot.MAINHAND);
        this.itemRenderer.renderItem(bear, itemStack, ItemTransforms.TransformType.GROUND, false, poseStack, buffer, packedLight);
        poseStack.popPose();
    }
}
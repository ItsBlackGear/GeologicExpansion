package com.blackgear.geologicexpansion.client.renderer.entity.layer;

import com.blackgear.geologicexpansion.client.renderer.entity.model.GrizzlyModel;
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
public class GrizzlyHeldItemLayer extends RenderLayer<Grizzly, GrizzlyModel<Grizzly>> {
    private final ItemInHandRenderer itemRenderer;

    public GrizzlyHeldItemLayer(RenderLayerParent<Grizzly, GrizzlyModel<Grizzly>> parent, ItemInHandRenderer itemRenderer) {
        super(parent);
        this.itemRenderer = itemRenderer;
    }

    @Override
    public void render(
        PoseStack stack,
        MultiBufferSource buffer,
        int packedLight,
        Grizzly grizzly,
        float limbSwing,
        float limbSwingAmount,
        float partialTick,
        float ageInTicks,
        float netHeadYaw,
        float headPitch
    ) {
        boolean sleeping = grizzly.isSleeping();
        boolean baby = grizzly.isBaby();
        stack.pushPose();
        if (baby) {
            stack.scale(0.75F, 0.75F, 0.75F);
            stack.translate(0.0, 0.0, 0.209375F);
        }

        var model = this.getParentModel();
        model.bear.translateAndRotate(stack);
        model.head.translateAndRotate(stack);

        //stack.translate(this.getParentModel().head.x / 16.0F, this.getParentModel().head.y / 16.0F, this.getParentModel().head.z / 16.0F);

        stack.translate(-0.45, 0.25, 0.25);
        stack.mulPose(Vector3f.YP.rotationDegrees(model.head.yRot));
        stack.mulPose(Vector3f.XP.rotationDegrees(model.head.xRot));
        if (grizzly.isBaby()) {
            if (sleeping) {
                stack.translate(0.4F, 0.26F, 0.15F);
            } else {
                stack.translate(0.0F, 0.9, -0.95);
            }
        } else if (sleeping) {
            stack.translate(0.46F, 0.26F, 0.22F);
        } else {
            stack.translate(0.0F, 0.15F, -1.25);
        }

        stack.mulPose(Vector3f.XP.rotationDegrees(90.0F));
        if (sleeping) {
            stack.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
        }

        this.itemRenderer.renderItem(grizzly, grizzly.getItemBySlot(EquipmentSlot.MAINHAND), ItemTransforms.TransformType.GROUND, false, stack, buffer, packedLight);
        stack.popPose();
    }
}
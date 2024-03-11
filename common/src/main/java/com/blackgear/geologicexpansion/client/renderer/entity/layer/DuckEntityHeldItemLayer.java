package com.blackgear.geologicexpansion.client.renderer.entity.layer;

import com.blackgear.geologicexpansion.client.renderer.entity.model.DuckEntityModel;
import com.blackgear.geologicexpansion.common.entity.duck.DuckEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

public class DuckEntityHeldItemLayer<T extends DuckEntity> extends RenderLayer<T, DuckEntityModel<T>> {
    private final ItemInHandRenderer itemInHand;

    public DuckEntityHeldItemLayer(RenderLayerParent<T, DuckEntityModel<T>> parent, ItemInHandRenderer itemInHand) {
        super(parent);
        this.itemInHand = itemInHand;
    }

    @Override
    public void render(PoseStack stack, MultiBufferSource buffer, int packedLight, T duck, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        var head = this.getParentModel().head;

        stack.pushPose();

        head.translateAndRotate(stack);
        stack.translate(0.02, -0.8, -0.03);
        stack.mulPose(Vector3f.YP.rotationDegrees(-48.0F));
        stack.scale(0.9F, 0.9F, 0.9F);
        stack.translate(0.0, 3.0, 0.0);

        // Rotates the item to make it look like it's being held
        stack.mulPose(Vector3f.XP.rotationDegrees(90.0F));

        this.itemInHand.renderItem(duck, duck.getItemBySlot(EquipmentSlot.MAINHAND), ItemTransforms.TransformType.GROUND, false, stack, buffer, packedLight);
        stack.popPose();
    }
}
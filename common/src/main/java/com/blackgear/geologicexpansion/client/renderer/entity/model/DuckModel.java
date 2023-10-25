package com.blackgear.geologicexpansion.client.renderer.entity.model;

import com.blackgear.geologicexpansion.client.renderer.entity.animation.DuckAnimations;
import com.blackgear.geologicexpansion.common.entity.duck.Duck;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

@Environment(EnvType.CLIENT)
public class DuckModel<T extends Duck> extends HierarchicalModel<T> {
    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;
    private final ModelPart rightWing;
    private final ModelPart leftWing;
    public final ModelPart beak;

    private float headXRot;
    private float beakXRot;

    public DuckModel(ModelPart root) {
        this.root = root;
        this.head = root.getChild("head");
        this.beak = root.getChild("beak");
        this.body = root.getChild("body");
        this.rightLeg = root.getChild("right_leg");
        this.leftLeg = root.getChild("left_leg");
        this.rightWing = root.getChild("right_wing");
        this.leftWing = root.getChild("left_wing");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -6.0F, -2.0F, 4.0F, 6.0F, 3.0F), PartPose.offset(0.0F, 15.0F, -4.0F));
        root.addOrReplaceChild("beak", CubeListBuilder.create().texOffs(14, 0).addBox(-2.0F, -4.0F, -4.0F, 4.0F, 2.0F, 2.0F), PartPose.offset(0.0F, 15.0F, -4.0F));
        root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 9).addBox(-3.0F, -4.0F, -3.0F, 6.0F, 8.0F, 6.0F), PartPose.offsetAndRotation(0.0F, 16.0F, 0.0F, 1.5707964F, 0.0F, 0.0F));
        CubeListBuilder legShape = CubeListBuilder.create().texOffs(26, 0).addBox(-1.0F, 0.0F, -3.0F, 3.0F, 5.0F, 3.0F);
        root.addOrReplaceChild("right_leg", legShape, PartPose.offset(-2.0F, 19.0F, 1.0F));
        root.addOrReplaceChild("left_leg", legShape, PartPose.offset(1.0F, 19.0F, 1.0F));
        root.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(24, 13).addBox(0.0F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F), PartPose.offset(-4.0F, 13.0F, 0.0F));
        root.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(24, 13).addBox(-1.0F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F), PartPose.offset(4.0F, 13.0F, 0.0F));
        return LayerDefinition.create(mesh, 64, 32);
    }

    protected Iterable<ModelPart> headParts() {
        return ImmutableList.of(this.head, this.beak);
    }

    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(this.body, this.rightLeg, this.leftLeg, this.rightWing, this.leftWing);
    }

    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.head.xRot = headPitch * 0.017453292F;
        this.head.yRot = netHeadYaw * 0.017453292F;
        this.beak.xRot = this.head.xRot;
        this.beak.yRot = this.head.yRot;
        this.rightLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.leftLeg.xRot = Mth.cos(limbSwing * 0.6662F + 3.1415927F) * 1.4F * limbSwingAmount;
        this.rightWing.zRot = ageInTicks;
        this.leftWing.zRot = -ageInTicks;

        this.animate(entity.floatTransformationState, DuckAnimations.WATER_FLOATING_TRANSFORM, ageInTicks);

        if (entity.isInWaterOrBubble() && entity.isBaby()) {
            this.head.y = 17;
            this.beak.y = 17;
        }

        this.head.xRot = this.headXRot;
        this.beak.xRot = this.beakXRot;
    }

    @Override
    public void prepareMobModel(T entity, float limbSwing, float limbSwingAmount, float partialTick) {
        super.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTick);
        this.head.y = 6.0F + entity.getHeadEatPositionScale(partialTick) * 9.0F;
        this.headXRot = entity.getHeadEatAngleScale(partialTick);
        this.beak.y = 6.0F + entity.getHeadEatPositionScale(partialTick) * 9.0F;
        this.beakXRot = entity.getHeadEatAngleScale(partialTick);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        if (this.young) {
            poseStack.pushPose();

            poseStack.translate(0.0D, 5.0F / 16.0F, 2.0F / 16.0F);
            this.headParts().forEach(modelPart -> modelPart.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha));

            poseStack.popPose();
            poseStack.pushPose();

            float f = 1.0F / 2.0F;
            poseStack.scale(f, f, f);
            poseStack.translate(0.0D, 24.0F / 16.0F, 0.0D);
            this.bodyParts().forEach(modelPart -> modelPart.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha));
            poseStack.popPose();
        } else {
            this.headParts().forEach(modelPart -> modelPart.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha));
            this.bodyParts().forEach(modelPart -> modelPart.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha));
        }
    }

    @Override
    public ModelPart root() {
        return this.root;
    }
}
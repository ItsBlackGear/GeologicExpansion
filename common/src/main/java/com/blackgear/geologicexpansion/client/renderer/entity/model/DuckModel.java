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

//TODO: redo...
@Environment(EnvType.CLIENT)
public class DuckModel<T extends Duck> extends HierarchicalModel<T> {
    private final ModelPart root;
    public final ModelPart head;
    private final ModelPart body;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;
    private final ModelPart rightWing;
    private final ModelPart leftWing;

    private float headXRot;

    public DuckModel(ModelPart root) {
        this.root = root.getChild("root");
        ModelPart bone = this.root.getChild("bone");
        this.head = bone.getChild("head");
        this.body = bone.getChild("body");
        this.rightLeg = this.root.getChild("right_leg");
        this.leftLeg = this.root.getChild("left_leg");
        this.rightWing = bone.getChild("right_wing");
        this.leftWing = bone.getChild("left_wing");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();

        PartDefinition root = mesh.getRoot().addOrReplaceChild("root", CubeListBuilder.create(), PartPose.ZERO);
        PartDefinition bone = root.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.ZERO);
        bone.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 9).addBox(-3.0F, -4.0F, -3.0F, 6.0F, 8.0F, 6.0F), PartPose.offsetAndRotation(0.0F, -8.0F, 0.0F, 1.5708F, 0.0F, 0.0F));
        root.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(26, 0).addBox(-1.0F, 0.0F, -3.0F, 3.0F, 5.0F, 3.0F), PartPose.offset(-2.0F, -5.0F, 1.0F));
        root.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(26, 0).addBox(-1.0F, 0.0F, -3.0F, 3.0F, 5.0F, 3.0F), PartPose.offset(1.0F, -5.0F, 1.0F));
        bone.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(24, 13).addBox(0.0F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F), PartPose.offset(-4.0F, -11.0F, 0.0F));
        bone.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(24, 13).addBox(-1.0F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F), PartPose.offset(4.0F, -11.0F, 0.0F));
        PartDefinition head = bone.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -6.0F, -2.0F, 4.0F, 6.0F, 3.0F), PartPose.offset(0.0F, -9.0F, -4.0F));
        head.addOrReplaceChild("beak", CubeListBuilder.create().texOffs(14, 0).addBox(-2.0F, -4.0F, -4.0F, 4.0F, 2.0F, 2.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 64, 32);
    }

    protected Iterable<ModelPart> headParts() {
        return ImmutableList.of(this.head);
    }

    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(this.body, this.rightLeg, this.leftLeg, this.rightWing, this.leftWing);
    }

    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        float motionSpeed = Math.min((float)entity.getDeltaMovement().length() * 200.0F, 8.0F);
        this.animateDuck(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        this.setupPoses(entity);
        this.animate(entity.floatTransformationState, DuckAnimations.IN_WATER_TRANSFORM, ageInTicks, motionSpeed);
    }

    /**
     * I'm not proud of this code, but it was either this or delaying the project some more...
     */
    private void setupPoses(T entity) {
        if (entity.isInWaterOrBubble()) {
            this.head.y = this.young ? 17 : 19;
            this.rightWing.y = 17;
            this.body.y = 20;
            this.rightLeg.y = 23;
        } else {
            this.head.y = 15;
            this.rightWing.y = 13;
            this.body.y = 16;
            this.rightLeg.y = 18;
        }

        this.leftWing.y = this.rightWing.y;
        this.leftLeg.y = this.rightLeg.y;

        this.head.xRot = this.headXRot;
    }

    private void animateDuck(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.xRot = headPitch * 0.017453292F;
        this.head.yRot = netHeadYaw * 0.017453292F;
        this.rightLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.leftLeg.xRot = Mth.cos(limbSwing * 0.6662F + 3.1415927F) * 1.4F * limbSwingAmount;
        this.rightWing.zRot = ageInTicks;
        this.leftWing.zRot = -ageInTicks;
    }

    @Override
    public void prepareMobModel(T entity, float limbSwing, float limbSwingAmount, float partialTick) {
        super.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTick);
        this.head.y = 6.0F + entity.getHeadEatPositionScale(partialTick) * 9.0F;
        this.headXRot = entity.getHeadEatAngleScale(partialTick);
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
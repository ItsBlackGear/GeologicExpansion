package com.blackgear.geologicexpansion.client.renderer.entity.model;

import com.blackgear.geologicexpansion.client.renderer.entity.animation.DuckAnimations;
import com.blackgear.geologicexpansion.common.entity.duck.AbstractDuck;
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

public class DuckModel<T extends AbstractDuck> extends HierarchicalModel<T> {
    private final ModelPart root;
    public final ModelPart head;
//    private final ModelPart rightWing;
//    private final ModelPart leftWing;
//    private final ModelPart rightLeg;
//    private final ModelPart leftLeg;

    public DuckModel(ModelPart root) {
        this.root = root.getChild("root");
        this.head = this.root.getChild("head");
//        ModelPart torso = this.root.getChild("torso");
//        this.rightWing = torso.getChild("right_wing");
//        this.leftWing = torso.getChild("left_wing");
//        this.rightLeg = this.root.getChild("right_leg");
//        this.leftLeg = this.root.getChild("left_leg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition torso = root.addOrReplaceChild("torso", CubeListBuilder.create(), PartPose.offset(0.0F, -8.0F, 0.0F));

        PartDefinition right_wing = torso.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(24, 13).addBox(-1.0F, -1.0F, -1.0F, 1.0F, 4.0F, 6.0F), PartPose.offset(-3.0F, -2.0F, -2.0F));

        PartDefinition left_wing = torso.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(24, 13).addBox(0.0F, -1.0F, -1.0F, 1.0F, 4.0F, 6.0F), PartPose.offset(3.0F, -2.0F, -2.0F));

        PartDefinition body = torso.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 9).addBox(-3.0F, -4.0F, -3.0F, 6.0F, 8.0F, 6.0F), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

        PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -6.0F, -2.0F, 4.0F, 6.0F, 3.0F), PartPose.offset(0.0F, -9.0F, -4.0F));

        PartDefinition beak = head.addOrReplaceChild("beak", CubeListBuilder.create().texOffs(14, 0).addBox(-2.0F, -4.0F, -4.0F, 4.0F, 2.0F, 2.0F), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition right_leg = root.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(26, 0).addBox(-1.0F, 0.0F, -3.0F, 3.0F, 5.0F, 3.0F), PartPose.offset(-2.0F, -5.0F, 1.0F));

        PartDefinition left_leg = root.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(26, 0).addBox(-1.0F, 0.0F, -3.0F, 3.0F, 5.0F, 3.0F), PartPose.offset(1.0F, -5.0F, 1.0F));

        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.root.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
//        this.animateHeadLookTarget(netHeadYaw, headPitch);
//        this.animateLimbs(limbSwing, limbSwingAmount, ageInTicks);
        this.animate(entity.fishingAnimationState, DuckAnimations.FISHING, ageInTicks, 1);
    }
//    private void animateLimbs(float limbSwing, float limbSwingAmount, float ageInTicks) {
//        this.rightLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
//        this.leftLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
//        this.rightWing.zRot = ageInTicks;
//        this.leftWing.zRot = -ageInTicks;
//    }
//
//    private void animateHeadLookTarget(float netHeadYaw, float headPitch) {
//        this.head.xRot = headPitch * (float) (Math.PI / 180.0);

//        this.head.yRot = netHeadYaw * (float) (Math.PI / 180.0);

//    }
}
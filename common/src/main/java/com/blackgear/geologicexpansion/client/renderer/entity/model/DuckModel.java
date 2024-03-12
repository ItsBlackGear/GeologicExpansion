package com.blackgear.geologicexpansion.client.renderer.entity.model;

import com.blackgear.geologicexpansion.client.renderer.entity.animation.DuckAnimations;
import com.blackgear.geologicexpansion.client.renderer.entity.animation.resources.AnimationHelper;
import com.blackgear.geologicexpansion.common.entity.duck.Duck;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class DuckModel<T extends Duck> extends HierarchicalModel<T> {
    public final ModelPart duck;
    public final ModelPart head;
    public final ModelPart beak;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;

    public DuckModel(ModelPart root) {
        this.duck = root.getChild("duck");
        this.leftLeg = this.duck.getChild("left_leg");
        this.rightLeg = this.duck.getChild("right_leg");
        this.head = this.duck.getChild("head");
        this.beak = this.head.getChild("beak");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition duck = root.addOrReplaceChild("duck", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
        duck.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(26, 0).addBox(-1.0F, 0.0F, -3.0F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, -5.0F, 1.0F));
        duck.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(26, 0).addBox(-1.0F, 0.0F, -3.0F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -5.0F, 1.0F));

        PartDefinition head = duck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -6.0F, -2.0F, 4.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -9.0F, -4.0F));
        head.addOrReplaceChild("beak", CubeListBuilder.create().texOffs(14, 0).addBox(-2.0F, -1.0F, -1.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, -3.0F));

        PartDefinition torso = duck.addOrReplaceChild("torso", CubeListBuilder.create(), PartPose.offset(0.0F, -8.0F, 0.0F));
        torso.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(24, 13).addBox(-1.0F, -1.0F, -1.0F, 1.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, -2.0F, -2.0F));
        torso.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(24, 13).addBox(0.0F, -1.0F, -1.0F, 1.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, -2.0F, -2.0F));
        torso.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 9).addBox(-3.0F, -4.0F, -3.0F, 6.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

        return LayerDefinition.create(mesh, 64, 32);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);

        this.head.xRot = headPitch * (float) (Math.PI / 180.0);
        this.head.yRot = netHeadYaw * (float) (Math.PI / 180.0);
        this.rightLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.leftLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;

        if (entity.isInWaterOrBubble()) {
            this.duck.y = 28.0F;
        }

        AnimationHelper.animate(this, entity.idleAnimationState, DuckAnimations.DUCK_WATER_IDLE, ageInTicks);
        AnimationHelper.animate(this, entity.fallingAnimationState, DuckAnimations.DUCK_FALL, ageInTicks);
        AnimationHelper.animate(this, entity.fishingAnimationState, DuckAnimations.DUCK_FISH, ageInTicks);

        if (this.young) {
            AnimationHelper.applyStatic(this, DuckAnimations.BABY_TRANSFORM);
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.duck.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart root() {
        return this.duck;
    }
}
package com.blackgear.geologicexpansion.client.renderer.entity.model;

import com.blackgear.geologicexpansion.client.renderer.entity.animation.GrizzlyAnimations;
import com.blackgear.geologicexpansion.client.renderer.entity.animation.resources.AnimationHelper;
import com.blackgear.geologicexpansion.common.entity.bear.Grizzly;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

@Environment(EnvType.CLIENT)
public class GrizzlyModel<T extends Grizzly> extends AgeableHierarchicalModel<T> {
    public final ModelPart bear;
    public final ModelPart head;

    public GrizzlyModel(ModelPart root) {
        super(0.5f, 24.0f);
        this.bear = root.getChild("bear");
        ModelPart body = this.bear.getChild("body");
        this.head = body.getChild("head");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition bear = partdefinition.addOrReplaceChild("bear", CubeListBuilder.create(), PartPose.offset(7.0F, 12.0F, 1.0F));

        PartDefinition body = bear.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-9.0F, -13.0F, -15.0F, 18.0F, 14.0F, 32.0F, new CubeDeformation(0.01F)), PartPose.offset(-7.0F, 2.0F, -1.0F));

        body.addOrReplaceChild("chest", CubeListBuilder.create().texOffs(0, 46).addBox(-10.0F, -15.5F, -15.1F, 20.0F, 19.0F, 18.0F), PartPose.offset(0.0F, 0.0F, 0.0F));

        body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(68, 0).addBox(-6.0F, -5.0F, -8.0F, 12.0F, 10.0F, 12.0F)
            .texOffs(0, 21).addBox(-4.0F, 1.0F, -12.0F, 8.0F, 4.0F, 4.0F)
            .texOffs(0, 46).addBox(4.0F, -7.0F, -2.0F, 4.0F, 4.0F, 1.0F)
            .texOffs(0, 46).mirror().addBox(-8.0F, -7.0F, -2.0F, 4.0F, 4.0F, 1.0F).mirror(false), PartPose.offset(0.0F, -6.0F, -16.0F));

        bear.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(76, 46).mirror().addBox(-3.5F, -2.0F, -3.5F, 7.0F, 12.0F, 7.0F).mirror(false), PartPose.offset(-12.25F, 2.0F, -9.5F));
        bear.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(76, 46).addBox(-3.5F, -2.0F, -3.5F, 7.0F, 12.0F, 7.0F), PartPose.offset(-1.75F, 2.0F, -9.5F));
        bear.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-3.75F, -2.0F, -4.5F, 7.0F, 12.0F, 9.0F, new CubeDeformation(-0.01F)).mirror(false), PartPose.offset(-12.25F, 2.0F, 9.5F));
        bear.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(0, 0).addBox(-3.25F, -2.0F, -4.5F, 7.0F, 12.0F, 9.0F, new CubeDeformation(-0.01F)), PartPose.offset(-1.75F, 2.0F, 9.5F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }
    
    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);

        this.head.xRot = headPitch * (float) (Math.PI / 180.0);
        this.head.yRot = netHeadYaw * (float) (Math.PI / 180.0);

        if (entity.getTarget() != null) {
            AnimationHelper.animateWalk(this, GrizzlyAnimations.GRIZZLY_WALK, limbSwing / 10, limbSwingAmount / 10, 9.0F, 100.0F);
        } else {
            AnimationHelper.animateWalk(this, GrizzlyAnimations.GRIZZLY_WALK, limbSwing, limbSwingAmount, 9.0F, 100.0F);
        }

        AnimationHelper.animate(this, entity.attackAnimationState, GrizzlyAnimations.GRIZZLY_ATTACK, ageInTicks);

        if (this.young) {
            AnimationHelper.applyStatic(this, GrizzlyAnimations.BABY_TRANSFORM);
        }
    }

    @Override
    public ModelPart root() {
        return this.bear;
    }
}
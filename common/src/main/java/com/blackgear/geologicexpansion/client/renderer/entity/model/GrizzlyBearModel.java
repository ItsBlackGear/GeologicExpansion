package com.blackgear.geologicexpansion.client.renderer.entity.model;

import com.blackgear.geologicexpansion.common.entity.bear.GrizzlyBear;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.QuadrupedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

@Environment(EnvType.CLIENT)
public class GrizzlyBearModel<T extends GrizzlyBear> extends QuadrupedModel<T> {
    public GrizzlyBearModel(ModelPart root) {
        super(root, true, 16.0F, 4.0F, 2.25F, 2.0F, 24);
    }

    //TODO fix this
    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        root.addOrReplaceChild("head",
            CubeListBuilder.create()
                .texOffs(64, 37).addBox(-6.0F, -5.0F, -8.0F, 12.0F, 10.0F, 12.0F)
                .texOffs(50, 37).addBox(-4.0F, 1.0F, -12.0F, 8.0F, 4.0F, 4.0F)
                .texOffs(0, 0).addBox(4.0F, -7.0F, -2.0F, 4.0F, 4.0F, 1.0F)
                .texOffs(0, 0).addBox(-8.0F, -7.0F, -2.0F, 4.0F, 4.0F, 1.0F),
            PartPose.offset(-0.5F, 10.0F, -12.0F)
        );

        root.addOrReplaceChild("body",
            CubeListBuilder.create()
                .texOffs(0, 37).addBox(-9.0F, -24.0F, 0.0F, 18.0F, 32.0F, 14.0F)
                .texOffs(0, 0).addBox(-10.0F, -24.0F, -3.5F, 20.0F, 18.0F, 19.0F),
            PartPose.offsetAndRotation(-2.0F, 6.0F, 12.0F, (float) (Math.PI / 2), 0.0F, 0.0F));

        root.addOrReplaceChild("right_hind_leg",
            CubeListBuilder.create()
                .texOffs(64, 59).addBox(-3.5F, -2.0F, -4.5F, 7.0F, 12.0F, 9.0F),
            PartPose.offset(6.5F, 14.0F, 13.0F)
        );

        root.addOrReplaceChild("left_hind_leg",
            CubeListBuilder.create()
                .texOffs(64, 59).addBox(-3.5F, -2.0F, -4.5F, 7.0F, 12.0F, 9.0F),
            PartPose.offset(-6.5F, 14.0F, 13.0F)
        );

        root.addOrReplaceChild("right_front_leg",
            CubeListBuilder.create()
                .texOffs(59, 0).addBox(-3.5F, -2.0F, -3.5F, 7.0F, 12.0F, 7.0F),
            PartPose.offset(5.0F, 14.0F, -8.0F)
        );

        root.addOrReplaceChild("left_front_leg",
            CubeListBuilder.create()
                .texOffs(59, 0).addBox(-3.5F, -2.0F, -3.5F, 7.0F, 12.0F, 7.0F),
            PartPose.offset(-5.0F, 14.0F, -8.0F)
        );

        return LayerDefinition.create(mesh, 128, 128);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        // Calculate the animation variables
        float deltaTicks = ageInTicks - (float)entity.tickCount;
        float scale = entity.getStandingAnimationScale(deltaTicks);
        scale *= scale;
        float inverseScale = 1.0F - scale;

        float bodyXRotOffset = (float)Math.PI * 0.35F;
        float legXRotOffset = (float)Math.PI * 0.45F;

        // Adjust the body position and rotation
        this.body.xRot = ((float)Math.PI / 2) - scale * bodyXRotOffset;
        this.body.y = 6.0F * inverseScale + 11.0F * scale;

        // Adjust the position and rotation of the right front leg
        this.rightFrontLeg.y = 14.0F * inverseScale - 6.0F * scale;
        this.rightFrontLeg.z = -8.0F * inverseScale - 4.0F * scale;
        this.rightFrontLeg.xRot -= scale * legXRotOffset;

        // Make the left front leg match the position and rotation of the right front leg
        this.leftFrontLeg.y = this.rightFrontLeg.y;
        this.leftFrontLeg.z = this.rightFrontLeg.z;
        this.leftFrontLeg.xRot -= scale * legXRotOffset;

        if (this.young) {
            // Adjust the position of the head for young entities
            this.head.y = 9.0F * inverseScale - 9.0F * scale;
            this.head.z = -12.0F * inverseScale - 7.0F * scale;
        } else {
            // Adjust the position of the head for adult entities
            this.head.y = 10.0F * inverseScale - 14.0F * scale;
            this.head.z = -12.0F * inverseScale - 3.0F * scale;
        }

        // Adjust the rotation of the head
        this.head.xRot += scale * (float)Math.PI * 0.15F;
    }
}
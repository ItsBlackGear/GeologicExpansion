package com.blackgear.geologicexpansion.client.renderer.entity.model;

import com.blackgear.geologicexpansion.common.entity.bear.GrizzlyBear;
import com.google.common.collect.ImmutableList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

@Environment(EnvType.CLIENT)
public class GrizzlyBearModel<T extends GrizzlyBear> extends AgeableListModel<T> {
    private final ModelPart root;
    public final ModelPart head;
    private final ModelPart rightHindLeg;
    private final ModelPart leftHindLeg;
    private final ModelPart rightFrontLeg;
    private final ModelPart leftFrontLeg;

    public GrizzlyBearModel(ModelPart root) {
        super(true, 16.0F, 4.0F, 2.25F, 2.0F, 24);
        this.head = root.getChild("head");
        this.root = root.getChild("body");
        this.rightHindLeg = root.getChild("right_hind_leg");
        this.leftHindLeg = root.getChild("left_hind_leg");
        this.rightFrontLeg = root.getChild("right_front_leg");
        this.leftFrontLeg = root.getChild("left_front_leg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        root.addOrReplaceChild("head",
            CubeListBuilder.create()
                .texOffs(64, 37).addBox(-6.0F, -6.0F - 1, -3.0F - 11, 12.0F, 10.0F, 12.0F)
                .texOffs(50, 37).addBox(-4.0F, 0.0F - 1, -6.0F - 12, 8.0F, 4.0F, 4.0F)
                .texOffs(0, 0).addBox(4.0F, -8.0F - 1, 3.0F - 11, 4.0F, 4.0F, 1.0F)
                .texOffs(0, 0).mirror().addBox(-8.0F, -8.0F - 1, 3.0F - 11, 4.0F, 4.0F, 1.0F).mirror(false),
            PartPose.offset(0.0F, 14.0F, -17.0F)
        );

        root.addOrReplaceChild("body",
            CubeListBuilder.create()
                .texOffs(0, 37).addBox(-11.0F, -31.0F, -7.0F - 2, 18.0F, 32.0F, 14.0F, new CubeDeformation(0.01F))
                .texOffs(0, 0).addBox(-12.0F, -31.1F, -9.5F - 2, 20.0F, 18.0F, 19.0F),
            PartPose.offsetAndRotation(2.0F, 9.0F, 14.0F, (float) (Math.PI / 2), 0.0F, 0.0F)
        );

        root.addOrReplaceChild("left_hind_leg",
            CubeListBuilder.create()
                .texOffs(64, 59).addBox(-2.0F, -2.0F, -2.0F, 7.0F, 12.0F, 9.0F, new CubeDeformation(-0.01F)),
            PartPose.offset(4.0F, 14.0F, 6.0F)
        );

        root.addOrReplaceChild("right_hind_leg",
            CubeListBuilder.create()
                .texOffs(64, 59).mirror().addBox(-5.0F, -2.0F, -2.0F, 7.0F, 12.0F, 9.0F, new CubeDeformation(-0.01F)).mirror(false),
            PartPose.offset(-4.0F, 14.0F, 6.0F)
        );

        root.addOrReplaceChild("left_front_leg",
            CubeListBuilder.create()
                .texOffs(59, 0).addBox(-5.0F, -2.0F, -3.0F - 3, 7.0F, 12.0F, 7.0F),
            PartPose.offset(7.0F, 14.0F, -11.0F)
        );

        root.addOrReplaceChild("right_front_leg",
            CubeListBuilder.create()
                .texOffs(59, 0).mirror().addBox(-5.0F, -2.0F, -3.0F - 3, 7.0F, 12.0F, 7.0F).mirror(false),
            PartPose.offset(-4.0F, 14.0F, -11.0F)
        );

        return LayerDefinition.create(mesh, 128, 128);
    }

    @Override
    protected Iterable<ModelPart> headParts() {
        return ImmutableList.of(this.head);
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(this.root, this.rightHindLeg, this.leftHindLeg, this.rightFrontLeg, this.leftFrontLeg);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.xRot = headPitch * (float) (Math.PI / 180.0);
        this.head.yRot = netHeadYaw * (float) (Math.PI / 180.0);
        this.rightHindLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.leftHindLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.rightFrontLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.leftFrontLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;

        // Calculate the animation variables
        float deltaTicks = ageInTicks - (float)entity.tickCount;
        float scale = entity.getStandingAnimationScale(deltaTicks);
        scale *= scale;
        float inverseScale = 1.0F - scale;

        float bodyXRotOffset = (float)Math.PI * 0.35F;
        float legXRotOffset = (float)Math.PI * 0.45F;

        // Adjust the body position and rotation
        this.root.xRot = ((float)Math.PI / 2) - scale * bodyXRotOffset;
        this.root.y = 6.0F * inverseScale + 11.0F * scale;

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
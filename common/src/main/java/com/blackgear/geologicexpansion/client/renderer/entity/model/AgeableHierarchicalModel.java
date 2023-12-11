package com.blackgear.geologicexpansion.client.renderer.entity.model;

import com.blackgear.geologicexpansion.core.mixin.access.HierarchicalModelAccessor;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

import java.util.function.Function;

@Environment(EnvType.CLIENT)
public abstract class AgeableHierarchicalModel<E extends Entity> extends HierarchicalModel<E> {
    private final float youngScaleFactor;
    private final float bodyYOffset;

    public AgeableHierarchicalModel(float youngScaleFactor, float bodyYOffset) {
        this(youngScaleFactor, bodyYOffset, RenderType::entityCutoutNoCull);
    }

    public AgeableHierarchicalModel(float youngScaleFactor, float bodyYOffset, Function<ResourceLocation, RenderType> function) {
        super(function);
        this.youngScaleFactor = youngScaleFactor;
        this.bodyYOffset = bodyYOffset;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        if (this.young) {
            poseStack.pushPose();
            poseStack.scale(this.youngScaleFactor, this.youngScaleFactor, this.youngScaleFactor);
            poseStack.translate(0.0F, this.bodyYOffset / 16.0F, 0.0F);
            this.root().render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            poseStack.popPose();
        } else {
            this.root().render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        }
    }

    protected void animateWalk(AnimationDefinition definition, float limbSwing, float limbSwingAmount, float speed, float intensity) {
        long accumulatedTime = (long)(limbSwing * 50.0F * speed);
        float scale = Math.min(limbSwingAmount * intensity, 1.0F);
        KeyframeAnimations.animate(this, definition, accumulatedTime, scale, HierarchicalModelAccessor.getAnimationVectorCache());
    }

    protected void applyStatic(AnimationDefinition definition) {
        KeyframeAnimations.animate(this, definition, 0L, 1.0F, HierarchicalModelAccessor.getAnimationVectorCache());
    }
}
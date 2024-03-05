package com.blackgear.geologicexpansion.client.renderer.entity.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Environment(EnvType.CLIENT)
public abstract class AnimatableModel<E extends Entity> extends EntityModel<E> {
    private static final Vector3f ANIMATION_VECTOR_CACHE = new Vector3f();

    public AnimatableModel() {
        this(RenderType::entityCutoutNoCull);
    }

    public AnimatableModel(Function<ResourceLocation, RenderType> function) {
        super(function);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.root().render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    public abstract ModelPart root();

    public Optional<ModelPart> getAnyDescendantWithName(String string) {
        if (string.equals("root")) {
            return Optional.of(this.root());
        }
        return this.root().getAllParts().filter(modelPart -> modelPart.hasChild(string)).findFirst().map(modelPart -> modelPart.getChild(string));
    }

    // ========== ANIMATIONS ==========

    protected void animate(AnimationState animationState, AnimationDefinition animationDefinition, float f) {
        this.animate(animationState, animationDefinition, f, 1.0f);
    }

    protected void animateWalk(AnimationDefinition animationDefinition, float f, float g, float h, float i) {
        long l = (long)(f * 50.0f * h);
        float j = Math.min(g * i, 1.0f);
        animate(this, animationDefinition, l, j);
    }

    protected void animate(AnimationState animationState2, AnimationDefinition animationDefinition, float f, float g) {
        animationState2.updateTime(f, g);
        animationState2.ifStarted(animationState -> animate(this, animationDefinition, animationState.getAccumulatedTime(), 1.0f));
    }

    protected void applyStatic(AnimationDefinition animationDefinition) {
        animate(this, animationDefinition, 0L, 1.0F);
    }

    // ========== KEYFRAME ANIMATIONS ==========

    private static void animate(AnimatableModel<?> model, AnimationDefinition animationDefinition, long accumulatedTime, float scale) {
        float elapsedSeconds = getElapsedSeconds(animationDefinition, accumulatedTime);

        for(Map.Entry<String, List<AnimationChannel>> entry : animationDefinition.boneAnimations().entrySet()) {
            Optional<ModelPart> optional = model.getAnyDescendantWithName(entry.getKey());
            List<AnimationChannel> list = entry.getValue();
            optional.ifPresent(modelPart -> list.forEach(animationChannel -> {
                Keyframe[] keyframes = animationChannel.keyframes();
                int i = Math.max(0, Mth.binarySearch(0, keyframes.length, ix -> elapsedSeconds <= keyframes[ix].timestamp()) - 1);
                int j = Math.min(keyframes.length - 1, i + 1);
                Keyframe keyframe = keyframes[i];
                Keyframe keyframe2 = keyframes[j];
                float h = elapsedSeconds - keyframe.timestamp();
                float k = j != i ? Mth.clamp(h / (keyframe2.timestamp() - keyframe.timestamp()), 0.0F, 1.0F) : 0.0F;
                keyframe2.interpolation().apply(AnimatableModel.ANIMATION_VECTOR_CACHE, k, keyframes, i, j, scale);
                animationChannel.target().apply(modelPart, AnimatableModel.ANIMATION_VECTOR_CACHE);
            }));
        }
    }

    private static float getElapsedSeconds(AnimationDefinition definition, long accumulatedTime) {
        float f = (float)accumulatedTime / 1000.0F;
        return definition.looping() ? f % definition.lengthInSeconds() : f;
    }
}
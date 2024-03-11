package com.blackgear.geologicexpansion.client.renderer.entity.animation.resources;

import com.mojang.math.Vector3f;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.AnimationState;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AnimationHelper {
    private static final Vector3f ANIMATION_VECTOR_CACHE = new Vector3f();

    public static void animate(HierarchicalModel<?> model, AnimationState state, AnimationDefinition definition, float time) {
        animate(model, state, definition, time, 1.0f);
    }

    public static void animateWalk(HierarchicalModel<?> model, AnimationDefinition definition, float f, float g, float h, float i) {
        long accumulatedTime = (long)(f * 50.0f * h);
        float scale = Math.min(g * i, 1.0f);
        animate(model, definition, accumulatedTime, scale, ANIMATION_VECTOR_CACHE);
    }

    public static void animate(HierarchicalModel<?> model, AnimationState state, AnimationDefinition definition, float time, float speed) {
        state.updateTime(time, speed);
        state.ifStarted(animationState -> animate(model, definition, animationState.getAccumulatedTime(), 1.0f, ANIMATION_VECTOR_CACHE));
    }

    public static void applyStatic(HierarchicalModel<?> model, AnimationDefinition definition) {
        animate(model, definition, 0L, 1.0f, ANIMATION_VECTOR_CACHE);
    }

    public static void animate(HierarchicalModel<?> model, AnimationDefinition definition, long accumulatedTime, float scale, Vector3f vectorCache) {
        float elapsed = getElapsedSeconds(definition, accumulatedTime);

        for (Map.Entry<String, List<AnimationChannel>> animation : definition.boneAnimations().entrySet()) {
            Optional<ModelPart> optional = model.getAnyDescendantWithName(animation.getKey());
            List<AnimationChannel> channel = animation.getValue();
            optional.ifPresent(modelPart -> channel.forEach(animationChannel -> {
                Keyframe[] frames = animationChannel.keyframes();
                int frameIndex = Math.max(0, Mth.binarySearch(0, frames.length, i -> elapsed <= frames[i].timestamp()) - 1);
                int nextFrameIndex = Math.min(frames.length - 1, frameIndex + 1);
                Keyframe frame = frames[frameIndex];
                Keyframe nextFrame = frames[nextFrameIndex];
                float remaining = elapsed - frame.timestamp();
                float delta = nextFrameIndex != frameIndex ? Mth.clamp(remaining / (nextFrame.timestamp() - frame.timestamp()), 0.0f, 1.0f) : 0.0f;
                nextFrame.interpolation().apply(vectorCache, delta, frames, frameIndex, nextFrameIndex, scale);
                animationChannel.target().apply(modelPart, vectorCache);
            }));
        }
    }

    private static float getElapsedSeconds(AnimationDefinition definition, long accumulatedTime) {
        float seconds = (float)accumulatedTime / 1000.0f;
        return definition.looping() ? seconds % definition.lengthInSeconds() : seconds;
    }
}
package com.blackgear.geologicexpansion.client.renderer.entity.animation;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

@Environment(EnvType.CLIENT)
public class oDuckAnimations {
    public static final AnimationDefinition IN_WATER_TRANSFORM = AnimationDefinition.Builder.withLength(0.0F)
        .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0f, KeyframeAnimations.posVec(0f, 1f, -2f), AnimationChannel.Interpolations.LINEAR)
        )).addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0f, KeyframeAnimations.degreeVec(50f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
        )).addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0f, KeyframeAnimations.posVec(0f, 1f, -2f), AnimationChannel.Interpolations.LINEAR)
        )).addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0f, KeyframeAnimations.degreeVec(50f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
        )).build();
}
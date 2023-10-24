package com.blackgear.geologicexpansion.client.renderer.entity.animation;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

public class DuckAnimations {
    public static final AnimationDefinition WATER_FLOATING_TRANSFORM = AnimationDefinition.Builder.withLength(0.0f)
            .addAnimation("head",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, -3f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("beak",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, -3f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("body",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, -3f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("left_wing",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, -3f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("right_wing",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, -3f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("left_leg",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, -2f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("left_leg",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(50f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("right_leg",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, -2f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("right_leg",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(50f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR))).build();
}
package com.blackgear.geologicexpansion.common.worldgen.surface.surfacerules;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;

public class SurfaceBuilder {
    protected static SurfaceRules.RuleSource makeRuleState(Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }

    protected static SurfaceRules.ConditionSource surfaceNoiseAbove(double value) {
        return SurfaceRules.noiseCondition(Noises.SURFACE, value / 8.25D, Double.MAX_VALUE);
    }
}
package com.blackgear.geologicexpansion.common.worldgen.surface.surfacerules;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;

public class SurfaceBuilder {
    protected static final SurfaceRules.RuleSource MYCELIUM = makeRuleState(Blocks.MYCELIUM);
    protected static final SurfaceRules.RuleSource BLACKSTONE = makeRuleState(Blocks.BLACKSTONE);
    protected static final SurfaceRules.RuleSource AMETHYST_BLOCK = makeRuleState(Blocks.AMETHYST_BLOCK);
    protected static final SurfaceRules.RuleSource BASALT = makeRuleState(Blocks.BASALT);
    protected static final SurfaceRules.RuleSource SMOOTH_BASALT = makeRuleState(Blocks.SMOOTH_BASALT);
    protected static final SurfaceRules.RuleSource BEDROCK = makeRuleState(Blocks.BEDROCK);

    protected static final SurfaceRules.RuleSource MOSS_BLOCK = makeRuleState(Blocks.MOSS_BLOCK);
    protected static final SurfaceRules.RuleSource PODZOL = makeRuleState(Blocks.PODZOL);
    protected static final SurfaceRules.RuleSource COARSE_DIRT = makeRuleState(Blocks.COARSE_DIRT);
    protected static final SurfaceRules.RuleSource ROOTED_DIRT = makeRuleState(Blocks.ROOTED_DIRT);
    protected static final SurfaceRules.RuleSource DIRT = makeRuleState(Blocks.DIRT);
    protected static final SurfaceRules.RuleSource CALCITE = makeRuleState(Blocks.CALCITE);
    protected static final SurfaceRules.RuleSource GRANITE = makeRuleState(Blocks.GRANITE);
    protected static final SurfaceRules.RuleSource DIORITE = makeRuleState(Blocks.DIORITE);
    protected static final SurfaceRules.RuleSource GRASS_BLOCK = makeRuleState(Blocks.GRASS_BLOCK);
    protected static final SurfaceRules.RuleSource MUD = makeRuleState(Blocks.MUD);
    protected static final SurfaceRules.RuleSource TUFF = makeRuleState(Blocks.TUFF);

    protected static final SurfaceRules.RuleSource SNOW_BLOCK = makeRuleState(Blocks.SNOW_BLOCK);
    protected static final SurfaceRules.RuleSource POWDER_SNOW = makeRuleState(Blocks.POWDER_SNOW);
    protected static final SurfaceRules.RuleSource GRAVEL = makeRuleState(Blocks.POWDER_SNOW);
    protected static final SurfaceRules.RuleSource DEEPSLATE = makeRuleState(Blocks.DEEPSLATE);
    protected static final SurfaceRules.RuleSource STONE = makeRuleState(Blocks.STONE);
    protected static final SurfaceRules.RuleSource PACKED_ICE = makeRuleState(Blocks.PACKED_ICE);
    protected static final SurfaceRules.RuleSource BLUE_ICE = makeRuleState(Blocks.BLUE_ICE);
    protected static final SurfaceRules.RuleSource ICE = makeRuleState(Blocks.ICE);
    protected static final SurfaceRules.RuleSource WATER = makeRuleState(Blocks.WATER);
    protected static final SurfaceRules.RuleSource AIR = makeRuleState(Blocks.AIR);

    protected static final SurfaceRules.RuleSource TERRACOTTA = makeRuleState(Blocks.TERRACOTTA);
    protected static final SurfaceRules.RuleSource RED_TERRACOTTA = makeRuleState(Blocks.RED_TERRACOTTA);
    protected static final SurfaceRules.RuleSource ORANGE_TERRACOTTA = makeRuleState(Blocks.ORANGE_TERRACOTTA);
    protected static final SurfaceRules.RuleSource YELLOW_TERRACOTTA = makeRuleState(Blocks.YELLOW_TERRACOTTA);
    protected static final SurfaceRules.RuleSource CYAN_TERRACOTTA = makeRuleState(Blocks.CYAN_TERRACOTTA);
    protected static final SurfaceRules.RuleSource BROWN_TERRACOTTA = makeRuleState(Blocks.BROWN_TERRACOTTA);
    protected static final SurfaceRules.RuleSource WHITE_TERRACOTTA = makeRuleState(Blocks.WHITE_TERRACOTTA);
    protected static final SurfaceRules.RuleSource LIGHT_GRAY_TERRACOTTA = makeRuleState(Blocks.LIGHT_GRAY_TERRACOTTA);
    protected static final SurfaceRules.RuleSource RED_SAND = makeRuleState(Blocks.RED_SAND);
    protected static final SurfaceRules.RuleSource RED_SANDSTONE = makeRuleState(Blocks.RED_SANDSTONE);
    protected static final SurfaceRules.RuleSource SAND = makeRuleState(Blocks.SAND);
    protected static final SurfaceRules.RuleSource SANDSTONE = makeRuleState(Blocks.SANDSTONE);
    protected static final SurfaceRules.RuleSource PACKED_MUD = makeRuleState(Blocks.PACKED_MUD);
    
    protected static SurfaceRules.RuleSource makeRuleState(Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }

    protected static SurfaceRules.ConditionSource surfaceNoiseAbove(double value) {
        return SurfaceRules.noiseCondition(Noises.SURFACE, value / 8.25D, Double.MAX_VALUE);
    }
}
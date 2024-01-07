package com.blackgear.geologicexpansion.common.worldgen.surface.surfacerules;

import com.blackgear.geologicexpansion.common.worldgen.surface.rulesources.SavannaBandlands;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;

public class SavannaPlateauSurface extends SurfaceBuilder {
    public static SurfaceRules.RuleSource generate() {
        return SurfaceRules.ifTrue(
            SurfaceRules.isBiome(Biomes.SAVANNA_PLATEAU, Biomes.SAVANNA, Biomes.WINDSWEPT_SAVANNA),
            SurfaceRules.sequence(
                SurfaceRules.ifTrue(
                    SurfaceRules.ON_FLOOR,
                    SurfaceRules.sequence(
                        SurfaceRules.ifTrue(
                            SurfaceRules.yStartCheck(VerticalAnchor.absolute(54), 1),
                            SavannaBandlands.INSTANCE
                        ),
                        SurfaceRules.ifTrue(
                            SurfaceRules.waterBlockCheck(-1, 0),
                            SurfaceRules.sequence(
                                makeRuleState(Blocks.PACKED_MUD)
                            )
                        ),
                        SurfaceRules.sequence(
                            SurfaceRules.ifTrue(SurfaceRules.ON_CEILING, STONE),
                            GRAVEL
                        )
                    )
                ),
                SurfaceRules.ifTrue(
                    SurfaceRules.yStartCheck(VerticalAnchor.absolute(63), -1),
                    SurfaceRules.sequence(
                        SurfaceRules.ifTrue(
                            SurfaceRules.yBlockCheck(VerticalAnchor.absolute(63), 0),
                            SurfaceRules.ifTrue(
                                SurfaceRules.not(SurfaceRules.yStartCheck(VerticalAnchor.absolute(74), 1)),
                                makeRuleState(Blocks.PACKED_MUD)
                            )
                        ),
                        SavannaBandlands.INSTANCE
                    )
                ),
                SurfaceRules.ifTrue(
                    SurfaceRules.UNDER_FLOOR,
                    SurfaceRules.ifTrue(SurfaceRules.waterStartCheck(-6, -1), WHITE_TERRACOTTA)
                )
            )
        );
    }

    public static SurfaceRules.RuleSource create() {
        SurfaceRules.ConditionSource isBlockAboveY97WithVariationAbove = SurfaceRules.yBlockCheck(VerticalAnchor.absolute(97), 2);
        SurfaceRules.ConditionSource isBlockAboveY256 = SurfaceRules.yBlockCheck(VerticalAnchor.absolute(256), 0);
        SurfaceRules.ConditionSource isSurfaceAbove63WithVariationBelow = SurfaceRules.yStartCheck(VerticalAnchor.absolute(63), -1);
        SurfaceRules.ConditionSource isSurfaceAboveY74WithVariationAbove = SurfaceRules.yStartCheck(VerticalAnchor.absolute(74), 1);
        SurfaceRules.ConditionSource isBlockAboveY60 = SurfaceRules.yBlockCheck(VerticalAnchor.absolute(60), 0);
        SurfaceRules.ConditionSource isBlockAboveY62 = SurfaceRules.yBlockCheck(VerticalAnchor.absolute(62), 0);
        SurfaceRules.ConditionSource isBlockAboveY63 = SurfaceRules.yBlockCheck(VerticalAnchor.absolute(63), 0);
        SurfaceRules.ConditionSource isInOrAboveShallowWater = SurfaceRules.waterBlockCheck(-1, 0);
        SurfaceRules.ConditionSource isAboveWater = SurfaceRules.waterBlockCheck(0, 0);
        SurfaceRules.ConditionSource isInOrAboveDeepWaterWithVariationBelow = SurfaceRules.waterStartCheck(-6, -1);
        SurfaceRules.ConditionSource isHole = SurfaceRules.hole();
        SurfaceRules.ConditionSource isFrozenOcean = SurfaceRules.isBiome(Biomes.FROZEN_OCEAN, Biomes.DEEP_FROZEN_OCEAN);
        SurfaceRules.ConditionSource isSteep = SurfaceRules.steep();
        SurfaceRules.RuleSource surfaceGrassOrDirtIfSubmerged = SurfaceRules.sequence(SurfaceRules.ifTrue(isAboveWater, GRASS_BLOCK), DIRT);
        SurfaceRules.RuleSource sandOrSandstoneCeiling = SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.ON_CEILING, SANDSTONE), SAND);
        SurfaceRules.RuleSource gravelOrStoneCeiling = SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.ON_CEILING, STONE), GRAVEL);
        SurfaceRules.ConditionSource isSandyShoreOrOcean = SurfaceRules.isBiome(Biomes.WARM_OCEAN, Biomes.BEACH, Biomes.SNOWY_BEACH);
        SurfaceRules.ConditionSource isDesert = SurfaceRules.isBiome(Biomes.DESERT);

        SurfaceRules.ConditionSource isSuitableSurfaceNoiseLower = SurfaceRules.noiseCondition(Noises.SURFACE, -0.909D, -0.5454D);
        SurfaceRules.ConditionSource isSuitableSurfaceNoiseMid = SurfaceRules.noiseCondition(Noises.SURFACE, -0.1818D, 0.1818D);
        SurfaceRules.ConditionSource isSuitableSurfaceNoiseUpper = SurfaceRules.noiseCondition(Noises.SURFACE, 0.5454D, 0.909D);

        SurfaceRules.RuleSource surfaceRule = SurfaceRules.sequence(
            SurfaceRules.ifTrue(
                SurfaceRules.isBiome(Biomes.SAVANNA, Biomes.SAVANNA_PLATEAU, Biomes.WINDSWEPT_SAVANNA),
                SurfaceRules.sequence(
                    SurfaceRules.ifTrue(
                        SurfaceRules.ON_FLOOR,
                        SurfaceRules.sequence(
                            SurfaceRules.ifTrue(isBlockAboveY256, LIGHT_GRAY_TERRACOTTA),
//                            SurfaceRules.ifTrue(isBlockAboveY256, ORANGE_TERRACOTTA),
                            SurfaceRules.ifTrue(
                                isSurfaceAboveY74WithVariationAbove,
                                SavannaBandlands.INSTANCE
                            ),
//                            SurfaceRules.ifTrue(
//                                isInOrAboveShallowWater,
//                                SurfaceRules.sequence(
//                                    SurfaceRules.ifTrue(SurfaceRules.ON_CEILING, RED_SANDSTONE),
//                                    RED_SAND
//                                )
//                            ),
                            SurfaceRules.ifTrue(
                                SurfaceRules.not(isHole),
//                                ORANGE_TERRACOTTA
                                LIGHT_GRAY_TERRACOTTA
                            ),
//                            SurfaceRules.ifTrue(isInOrAboveDeepWaterWithVariationBelow, WHITE_TERRACOTTA),
                            gravelOrStoneCeiling
                        )
                    ),
                    SurfaceRules.ifTrue(
                        isSurfaceAbove63WithVariationBelow,
                        SurfaceRules.sequence(
                            SurfaceRules.ifTrue(
                                isBlockAboveY63,
                                SurfaceRules.ifTrue(
                                    SurfaceRules.not(isSurfaceAboveY74WithVariationAbove),
//                                    ORANGE_TERRACOTTA
                                    LIGHT_GRAY_TERRACOTTA
                                )
                            ),
                            SavannaBandlands.INSTANCE
                        )
                    )
//                    SurfaceRules.ifTrue(
//                        SurfaceRules.UNDER_FLOOR,
//                        SurfaceRules.ifTrue(isInOrAboveDeepWaterWithVariationBelow, WHITE_TERRACOTTA)
//                    )
                )
            ),
            SurfaceRules.ifTrue(
                SurfaceRules.ON_FLOOR,
                gravelOrStoneCeiling
            )
        );

        return surfaceRule;
    }
}
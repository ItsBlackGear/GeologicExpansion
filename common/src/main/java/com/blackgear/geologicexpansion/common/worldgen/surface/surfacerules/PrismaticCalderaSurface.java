package com.blackgear.geologicexpansion.common.worldgen.surface.surfacerules;

import com.blackgear.geologicexpansion.common.registries.GEBlocks;
import com.blackgear.geologicexpansion.common.registries.worldgen.GENoises;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;

public class PrismaticCalderaSurface extends SurfaceBuilder {
    public static final SurfaceRules.ConditionSource STRIPE_NOISE_A = SurfaceRules.noiseCondition(Noises.SURFACE, -0.9, -0.55);
    public static final SurfaceRules.ConditionSource STRIPE_NOISE_B = SurfaceRules.noiseCondition(Noises.SURFACE, -0.18, 0.18);
    public static final SurfaceRules.ConditionSource STRIPE_NOISE_C = SurfaceRules.noiseCondition(Noises.SURFACE, 0.55, 0.9);

    public static final SurfaceRules.RuleSource PRISMATIC_PATCHES = SurfaceRules.sequence(
        SurfaceRules.ifTrue(
            SurfaceRules.noiseCondition(GENoises.PRISMATIC_PATCH, 0.7, 100),
            SurfaceRules.ifTrue(
                SurfaceRules.not(SurfaceRules.steep()),
                makeRuleState(GEBlocks.YELLOW_PRISMATIC_STONE.get())
            )
        ),
        SurfaceRules.ifTrue(
            SurfaceRules.noiseCondition(GENoises.PRISMATIC_PATCH, 0.6, 100),
            SurfaceRules.ifTrue(
                SurfaceRules.not(SurfaceRules.steep()),
                makeRuleState(GEBlocks.ORANGE_PRISMATIC_STONE.get())
            )
        ),
        SurfaceRules.ifTrue(
            SurfaceRules.noiseCondition(GENoises.PRISMATIC_PATCH, 0.5, 100),
            makeRuleState(GEBlocks.RED_PRISMATIC_STONE.get())
        ),
        SurfaceRules.ifTrue(
            SurfaceRules.noiseCondition(GENoises.PRISMATIC_PATCH, 0.4, 100),
            makeRuleState(GEBlocks.BROWN_PRISMATIC_STONE.get())
        ),
        SurfaceRules.ifTrue(
            SurfaceRules.noiseCondition(GENoises.PRISMATIC_PATCH, 0.3, 100),
            makeRuleState(GEBlocks.PURPLE_PRISMATIC_STONE.get())
        ),
        SurfaceRules.ifTrue(
            SurfaceRules.noiseCondition(GENoises.PRISMATIC_PATCH, 0.2, 100),
            makeRuleState(GEBlocks.PRISMATIC_STONE.get())
        )
    );

    public static SurfaceRules.RuleSource generate() {
        SurfaceRules.ConditionSource isBlockAboveY256 = SurfaceRules.yBlockCheck(VerticalAnchor.absolute(256), 0);
        SurfaceRules.ConditionSource isSurfaceAboveY63WithVariationBelow = SurfaceRules.yStartCheck(VerticalAnchor.absolute(63), -1);
        SurfaceRules.ConditionSource isSurfaceAboveY74WithVariationBelow = SurfaceRules.yStartCheck(VerticalAnchor.absolute(74), 1);
        SurfaceRules.ConditionSource isSurfaceAboveY63 = SurfaceRules.yBlockCheck(VerticalAnchor.absolute(63), 0);
        SurfaceRules.ConditionSource isInOrAboveDeepWaterWithVariationBelow = SurfaceRules.waterStartCheck(-6, -1);
        SurfaceRules.RuleSource gravelOrStoneCeiling = SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.ON_CEILING, STONE), GRAVEL);

        return SurfaceRules.sequence(
            SurfaceRules.ifTrue(
                SurfaceRules.ON_FLOOR,
                SurfaceRules.ifTrue(
                    SurfaceRules.yBlockCheck(VerticalAnchor.absolute(62), 0),
                    SurfaceRules.ifTrue(
                        SurfaceRules.not(SurfaceRules.yBlockCheck(VerticalAnchor.absolute(63), 0)),
                        SurfaceRules.ifTrue(
                            SurfaceRules.noiseCondition(Noises.SWAMP, 0.2),
                            makeRuleState(Blocks.WATER)
                        )
                    )
                )
            ),
            PRISMATIC_PATCHES,
            CALDERA_VEGETATION,
            SurfaceRules.ifTrue(
                SurfaceRules.ON_FLOOR,
                SurfaceRules.sequence(
                    SurfaceRules.ifTrue(isBlockAboveY256, makeRuleState(GEBlocks.PRISMATIC_STONE.get())),
                    SurfaceRules.ifTrue(
                        isSurfaceAboveY74WithVariationBelow,
                        SurfaceRules.sequence(
                            SurfaceRules.ifTrue(STRIPE_NOISE_A, COARSE_DIRT),
                            SurfaceRules.ifTrue(STRIPE_NOISE_B, COARSE_DIRT),
                            SurfaceRules.ifTrue(STRIPE_NOISE_C, COARSE_DIRT)
                        )
                    ),
                    SurfaceRules.ifTrue(isInOrAboveDeepWaterWithVariationBelow, makeRuleState(GEBlocks.PRISMATIC_STONE.get())),
                    gravelOrStoneCeiling
                )
            ),
            SurfaceRules.ifTrue(
                isSurfaceAboveY63WithVariationBelow,
                SurfaceRules.sequence(
                    SurfaceRules.ifTrue(
                        isSurfaceAboveY63,
                        SurfaceRules.ifTrue(
                            SurfaceRules.not(isSurfaceAboveY74WithVariationBelow),
                            makeRuleState(GEBlocks.PRISMATIC_STONE.get())
                        )
                    )
                )
            ),
            SurfaceRules.ifTrue(
                SurfaceRules.UNDER_FLOOR,
                SurfaceRules.ifTrue(
                    isInOrAboveDeepWaterWithVariationBelow,
                    makeRuleState(GEBlocks.PRISMATIC_STONE.get())
                )
            )
        );
    }

    public static final SurfaceRules.RuleSource CALDERA_VEGETATION = SurfaceRules.ifTrue(
        SurfaceRules.ON_FLOOR,
        SurfaceRules.sequence(
            // Spreads a layer of vegetation on a range of blocks
            SurfaceRules.ifTrue(
                // Checks if it is at the specified min height
                SurfaceRules.yBlockCheck(VerticalAnchor.absolute(68), 2),
                SurfaceRules.ifTrue(
                    // Sets a max height to avoid extra placement above it
                    SurfaceRules.not(SurfaceRules.yBlockCheck(VerticalAnchor.absolute(120), 2)),
                    SurfaceRules.ifTrue(
                        // Check if the terrain is not steep
                        SurfaceRules.not(SurfaceRules.steep()),
                        SurfaceRules.sequence(
                            // Generate Coarse Dirt Stripes
                            SurfaceRules.ifTrue(STRIPE_NOISE_A, COARSE_DIRT),
                            SurfaceRules.ifTrue(STRIPE_NOISE_B, COARSE_DIRT),
                            SurfaceRules.ifTrue(STRIPE_NOISE_C, COARSE_DIRT),
                            SurfaceRules.sequence(
                                SurfaceRules.ifTrue(
                                    // Place grass if not in water
                                    SurfaceRules.waterBlockCheck(0, 0),
                                    GRASS_BLOCK
                                ),
                                DIRT
                            )
                        )
                    )
                )
            ),
            // Spreads a layer of coarse dirt at the base of the vegetation
            SurfaceRules.ifTrue(
                // Checks if it's a few blocks below the min height
                SurfaceRules.yBlockCheck(VerticalAnchor.absolute(64), 2),
                SurfaceRules.ifTrue(
                    // Checks for the base min height to avoid extra placement above it
                    SurfaceRules.not(SurfaceRules.yBlockCheck(VerticalAnchor.absolute(68), 2)),
                    SurfaceRules.sequence(
                        SurfaceRules.ifTrue(
                            // Place coarse dirt if not in water
                            SurfaceRules.waterBlockCheck(0, 0),
                            COARSE_DIRT
                        ),
                        DIRT
                    )
                )
            )
        )
    );
}

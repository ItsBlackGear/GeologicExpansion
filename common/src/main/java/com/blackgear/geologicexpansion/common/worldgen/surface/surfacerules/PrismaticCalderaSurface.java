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
        SurfaceRules.ConditionSource conditionSource2 = SurfaceRules.yBlockCheck(VerticalAnchor.absolute(256), 0);
        SurfaceRules.ConditionSource conditionSource3 = SurfaceRules.yStartCheck(VerticalAnchor.absolute(63), -1);
        SurfaceRules.ConditionSource conditionSource4 = SurfaceRules.yStartCheck(VerticalAnchor.absolute(74), 1);
        SurfaceRules.ConditionSource conditionSource7 = SurfaceRules.yBlockCheck(VerticalAnchor.absolute(63), 0);
        SurfaceRules.ConditionSource conditionSource10 = SurfaceRules.waterStartCheck(-6, -1);
        SurfaceRules.RuleSource ruleSource3 = SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.ON_CEILING, makeRuleState(Blocks.STONE)), makeRuleState(Blocks.GRAVEL));

        return SurfaceRules.sequence(
                PRISMATIC_PATCHES,
                CALDERA_VEGETATION,
                SurfaceRules.ifTrue(
                        SurfaceRules.ON_FLOOR,
                        SurfaceRules.sequence(
                                SurfaceRules.ifTrue(conditionSource2, makeRuleState(GEBlocks.PRISMATIC_STONE.get())),
                                SurfaceRules.ifTrue(
                                        conditionSource4,
                                        SurfaceRules.sequence(
                                                SurfaceRules.ifTrue(STRIPE_NOISE_A, makeRuleState(Blocks.COARSE_DIRT)),
                                                SurfaceRules.ifTrue(STRIPE_NOISE_B, makeRuleState(Blocks.COARSE_DIRT)),
                                                SurfaceRules.ifTrue(STRIPE_NOISE_C, makeRuleState(Blocks.COARSE_DIRT))
                                        )
                                ),
                                SurfaceRules.ifTrue(conditionSource10, makeRuleState(GEBlocks.PRISMATIC_STONE.get())),
                                ruleSource3
                        )
                ),
                SurfaceRules.ifTrue(
                        conditionSource3,
                        SurfaceRules.sequence(
                                SurfaceRules.ifTrue(
                                        conditionSource7,
                                        SurfaceRules.ifTrue(
                                                SurfaceRules.not(conditionSource4),
                                                makeRuleState(GEBlocks.PRISMATIC_STONE.get())
                                        )
                                )
                        )
                ),
                SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, SurfaceRules.ifTrue(conditionSource10, makeRuleState(GEBlocks.PRISMATIC_STONE.get())))
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
                                                    SurfaceRules.ifTrue(STRIPE_NOISE_A, makeRuleState(Blocks.COARSE_DIRT)),
                                                    SurfaceRules.ifTrue(STRIPE_NOISE_B, makeRuleState(Blocks.COARSE_DIRT)),
                                                    SurfaceRules.ifTrue(STRIPE_NOISE_C, makeRuleState(Blocks.COARSE_DIRT)),
                                                    SurfaceRules.sequence(
                                                            SurfaceRules.ifTrue(
                                                                    // Place grass if not in water
                                                                    SurfaceRules.waterBlockCheck(0, 0),
                                                                    makeRuleState(Blocks.GRASS_BLOCK)
                                                            ),
                                                            makeRuleState(Blocks.DIRT)
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
                                                    makeRuleState(Blocks.COARSE_DIRT)
                                            ),
                                            makeRuleState(Blocks.DIRT)
                                    )
                            )
                    )
            )
    );
}

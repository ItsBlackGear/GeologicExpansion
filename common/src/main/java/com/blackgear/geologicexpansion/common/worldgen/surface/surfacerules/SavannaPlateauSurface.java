package com.blackgear.geologicexpansion.common.worldgen.surface.surfacerules;

import com.blackgear.geologicexpansion.common.worldgen.surface.rulesources.SavannaBandlands;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;

public class SavannaPlateauSurface extends SurfaceBuilder {
    public static SurfaceRules.RuleSource generate() {
        return SurfaceRules.sequence(
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
        );
    }
}
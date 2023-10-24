package com.blackgear.geologicexpansion.common.worldgen.surface;

import com.blackgear.geologicexpansion.common.registries.worldgen.GEBiomes;
import com.blackgear.geologicexpansion.common.worldgen.surface.surfacerules.PrismaticCalderaSurface;
import net.minecraft.world.level.levelgen.SurfaceRules;

public class GESurfaceRules {
    public static SurfaceRules.RuleSource makeRules() {
        return SurfaceRules.sequence(
                SurfaceRules.ifTrue(
                        SurfaceRules.abovePreliminarySurface(),
                        SurfaceRules.sequence(
                                SurfaceRules.ifTrue(
                                        SurfaceRules.isBiome(GEBiomes.PRISMATIC_CALDERA),
                                        PrismaticCalderaSurface.generate()
                                )
                        )
                )
        );
    }
}
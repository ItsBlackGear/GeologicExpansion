package com.blackgear.geologicexpansion.common.worldgen.surface;

import com.blackgear.geologicexpansion.common.registries.worldgen.GEBiomes;
import com.blackgear.geologicexpansion.common.worldgen.surface.surfacerules.PrismaticCalderaSurface;
import com.blackgear.geologicexpansion.common.worldgen.surface.surfacerules.SavannaPlateauSurface;
import net.minecraft.world.level.biome.Biomes;
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
                    ),
                    SurfaceRules.ifTrue(
                        SurfaceRules.isBiome(GEBiomes.SAVANNA_STRATA),
//                        SurfaceRules.isBiome(Biomes.SAVANNA, Biomes.SAVANNA_PLATEAU, Biomes.WINDSWEPT_SAVANNA),
                        SavannaPlateauSurface.generate()
                    )
                )
            )
        );
    }
}
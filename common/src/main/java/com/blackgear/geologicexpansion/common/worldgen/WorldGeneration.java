package com.blackgear.geologicexpansion.common.worldgen;

import com.blackgear.geologicexpansion.common.registries.GEBiomes;
import com.blackgear.geologicexpansion.common.worldgen.placements.TravertinePlacements;
import com.blackgear.geologicexpansion.core.platform.common.BiomeManager;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.level.levelgen.GenerationStep;

public class WorldGeneration {
    public static void bootstrap() {
        BiomeManager.add((writer, context) -> {
            if (context.is(GEBiomes.TRAVERTINE)) {
//                writer.feature(GenerationStep.Decoration.UNDERGROUND_ORES, TravertinePlacements.DISK_LIMESTONE);
//                writer.feature(GenerationStep.Decoration.UNDERGROUND_ORES, TravertinePlacements.ORE_LIMESTONE);
                writer.feature(GenerationStep.Decoration.VEGETAL_DECORATION, TravertinePlacements.TRAVERTINE_POOL);
                writer.feature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.TREES_OLD_GROWTH_SPRUCE_TAIGA);
            }
        });
    }
}
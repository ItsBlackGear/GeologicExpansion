package com.blackgear.geologicexpansion.common.worldgen;

import com.blackgear.geologicexpansion.common.worldgen.placements.CalderaPlacements;
import com.blackgear.geologicexpansion.common.worldgen.placements.MapleForestPlacements;
import com.blackgear.geologicexpansion.common.worldgen.placements.SurfacePlacements;
import com.blackgear.geologicexpansion.common.worldgen.placements.TreeVegetationPlacements;
import com.blackgear.geologicexpansion.core.platform.common.BiomeWriter;
import net.minecraft.world.level.levelgen.GenerationStep;

public class BiomeModdedFeatures {
    public static void addPrismaticStoneBorder(BiomeWriter writer) {
        writer.feature(GenerationStep.Decoration.RAW_GENERATION, CalderaPlacements.PRISMATIC_BORDER_YELLOW);
        writer.feature(GenerationStep.Decoration.RAW_GENERATION, CalderaPlacements.PRISMATIC_BORDER_ORANGE);
        writer.feature(GenerationStep.Decoration.RAW_GENERATION, CalderaPlacements.PRISMATIC_BORDER_RED);
        writer.feature(GenerationStep.Decoration.RAW_GENERATION, CalderaPlacements.PRISMATIC_BORDER_BROWN);
        writer.feature(GenerationStep.Decoration.RAW_GENERATION, CalderaPlacements.PRISMATIC_BORDER_PURPLE);
    }

    public static void addPrismaticLakeDecorations(BiomeWriter writer) {
        writer.feature(GenerationStep.Decoration.RAW_GENERATION, CalderaPlacements.PRISMATIC_POND);
        writer.feature(GenerationStep.Decoration.RAW_GENERATION, CalderaPlacements.MAGMA);
    }

    public static void addMapleForestVegetation(BiomeWriter writer) {
        writer.feature(GenerationStep.Decoration.VEGETAL_DECORATION, SurfacePlacements.TREES_MAPLE_FOREST);
        writer.feature(GenerationStep.Decoration.VEGETAL_DECORATION, TreeVegetationPlacements.RED_MAPLE_BUSH);
        writer.feature(GenerationStep.Decoration.VEGETAL_DECORATION, TreeVegetationPlacements.BROWN_MAPLE_BUSH);
        writer.feature(GenerationStep.Decoration.VEGETAL_DECORATION, TreeVegetationPlacements.FALLEN_MAPLE_TREE);
        writer.feature(GenerationStep.Decoration.VEGETAL_DECORATION, MapleForestPlacements.MAPLE_LEAF_CARPETS);
        writer.feature(GenerationStep.Decoration.VEGETAL_DECORATION, MapleForestPlacements.MAPLE_PATCH_PUMPKIN);
    }
}
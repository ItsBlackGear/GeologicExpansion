package com.blackgear.geologicexpansion.data.common;

import com.blackgear.geologicexpansion.data.resources.GEBlockFamilies;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

public class RecipeGenerator extends FabricRecipeProvider {
    public RecipeGenerator(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generateRecipes(Consumer<FinishedRecipe> exporter) {
        GEBlockFamilies.getAllFamilies().filter(BlockFamily::shouldGenerateRecipe).forEach(family -> generateRecipes(exporter, family));
        generateStonecutterRecipes(exporter, GEBlockFamilies.LIMESTONE);
        generateStonecutterRecipes(exporter, GEBlockFamilies.POLISHED_LIMESTONE);
    }

    private void generateStonecutterRecipes(Consumer<FinishedRecipe> exporter, BlockFamily family) {
        if (family.getVariants().containsKey(BlockFamily.Variant.SLAB)) {
            stonecutterResultFromBase(exporter, family.get(BlockFamily.Variant.SLAB), family.getBaseBlock(), 2);
        }

        if (family.getVariants().containsKey(BlockFamily.Variant.STAIRS)) {
            stonecutterResultFromBase(exporter, family.get(BlockFamily.Variant.STAIRS), family.getBaseBlock());
        }

        if (family.getVariants().containsKey(BlockFamily.Variant.WALL)) {
            stonecutterResultFromBase(exporter, family.get(BlockFamily.Variant.WALL), family.getBaseBlock());
        }

        if (family.getVariants().containsKey(BlockFamily.Variant.POLISHED)) {
            stonecutterResultFromBase(exporter, family.get(BlockFamily.Variant.POLISHED), family.getBaseBlock());
        }
    }
}
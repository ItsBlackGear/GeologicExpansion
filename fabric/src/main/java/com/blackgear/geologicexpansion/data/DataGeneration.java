package com.blackgear.geologicexpansion.data;

import com.blackgear.geologicexpansion.data.client.ModelGenerator;
import com.blackgear.geologicexpansion.data.common.RecipeGenerator;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class DataGeneration implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        generator.addProvider(ModelGenerator::new);

        generator.addProvider(RecipeGenerator::new);
    }
}
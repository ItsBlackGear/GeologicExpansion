package com.blackgear.geologicexpansion.data.client;

import com.blackgear.geologicexpansion.common.registries.GEBlocks;
import com.blackgear.geologicexpansion.data.resources.GEBlockFamilies;
import com.google.common.collect.ImmutableMap;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.TexturedModel;
import net.minecraft.world.level.block.Block;

import java.util.Map;

public class ModelGenerator extends FabricModelProvider {
    private final Map<Block, TexturedModel> texturedModels = ImmutableMap.<Block, TexturedModel>builder()
            .put(GEBlocks.LIMESTONE.get(), TexturedModel.COLUMN_WITH_WALL.get(GEBlocks.LIMESTONE.get()))
            .build();

    public ModelGenerator(FabricDataOutput generator) {
        super(generator);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators gen) {
        GEBlockFamilies.getAllFamilies().filter(BlockFamily::shouldGenerateModel).forEach(family -> {
            Block block = family.getBaseBlock();
            TexturedModel texturedModel = this.texturedModels.getOrDefault(block, TexturedModel.CUBE.get(block));
            gen.new BlockFamilyProvider(texturedModel.getMapping()).fullBlock(block, texturedModel.getTemplate()).generateFor(family);
        });
    }

    @Override
    public void generateItemModels(ItemModelGenerators gen) {

    }
}
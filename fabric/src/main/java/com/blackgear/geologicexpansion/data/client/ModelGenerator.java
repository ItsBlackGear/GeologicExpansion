package com.blackgear.geologicexpansion.data.client;

import com.blackgear.geologicexpansion.common.registries.GEBlocks;
import com.blackgear.geologicexpansion.data.resources.GEBlockFamilies;
import com.google.common.collect.ImmutableMap;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
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

    public ModelGenerator(FabricDataGenerator generator) {
        super(generator);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators gen) {
        GEBlockFamilies.getStoneFamilies().filter(BlockFamily::shouldGenerateModel).forEach(family -> {
            Block block = family.getBaseBlock();
            TexturedModel texturedModel = this.texturedModels.getOrDefault(block, TexturedModel.CUBE.get(block));
            gen.new BlockFamilyProvider(texturedModel.getMapping()).fullBlock(block, texturedModel.getTemplate()).generateFor(family);
        });
        GEBlockFamilies.getAllFamilies().filter(BlockFamily::shouldGenerateModel).forEach(family -> gen.family(family.getBaseBlock()).generateFor(family));

        gen.woodProvider(GEBlocks.MAPLE_LOG.get()).logWithHorizontal(GEBlocks.MAPLE_LOG.get()).wood(GEBlocks.MAPLE_WOOD.get());
        gen.woodProvider(GEBlocks.STRIPPED_MAPLE_LOG.get()).logWithHorizontal(GEBlocks.STRIPPED_MAPLE_LOG.get()).wood(GEBlocks.STRIPPED_MAPLE_WOOD.get());
        gen.createFullAndCarpetBlocks(GEBlocks.RED_MAPLE_LEAVES.get(), GEBlocks.RED_MAPLE_LEAF_CARPET.get());
        gen.createFullAndCarpetBlocks(GEBlocks.BROWN_MAPLE_LEAVES.get(), GEBlocks.BROWN_MAPLE_LEAF_CARPET.get());
        gen.createTrivialCube(GEBlocks.ASPEN_LEAVES.get());

    }

    @Override
    public void generateItemModels(ItemModelGenerators gen) {

    }
}
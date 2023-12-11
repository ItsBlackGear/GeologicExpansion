package com.blackgear.geologicexpansion.data.common.loot;

import com.blackgear.geologicexpansion.common.registries.GEBlocks;
import com.blackgear.geologicexpansion.data.resources.GEBlockFamilies;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;

public class BlockLootTableGenerator extends FabricBlockLootTableProvider {
    public BlockLootTableGenerator(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        GEBlockFamilies.getStoneFamilies().forEach(this::familyDropsSelf);

        this.add(GEBlocks.OVERGROWTH.get(), BlockLootSubProvider::createShearsOnlyDrop);
        this.add(GEBlocks.GEYSER.get(), block -> this.createSinglePropConditionTable(block, DoorBlock.HALF, DoubleBlockHalf.LOWER));
    }

    private void familyDropsSelf(BlockFamily family) {
        this.dropSelf(family.getBaseBlock());
        family.getVariants().forEach((variant, block) -> this.dropSelf(block));
    }
}
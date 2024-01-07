package com.blackgear.geologicexpansion.data.common.tag;

import com.blackgear.geologicexpansion.common.registries.GEBlocks;
import com.blackgear.geologicexpansion.core.data.GEBlockTags;
import com.blackgear.geologicexpansion.data.resources.GEBlockFamilies;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.data.BlockFamily;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;

public class BlockTagGenerator extends FabricTagProvider.BlockTagProvider {
    public BlockTagGenerator(FabricDataGenerator generator) {
        super(generator);
    }

    @Override
    protected void generateTags() {
        GEBlockFamilies.getAllFamilies().forEach(this::populateTags);

        GEBlockFamilies.getStoneFamilies().forEach(family -> {
            this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(family.getBaseBlock());
            family.getVariants().forEach((variant, block) -> this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(block));
        });

        this.tag(BlockTags.BASE_STONE_OVERWORLD).add(
                GEBlocks.PRISMATIC_STONE.get(),
                GEBlocks.LIMESTONE.get()
        );
        this.tag(BlockTags.STONE_ORE_REPLACEABLES).add(
                GEBlocks.PRISMATIC_STONE.get(),
                GEBlocks.LIMESTONE.get()
        );
        this.tag(BlockTags.REPLACEABLE_PLANTS).add(
                GEBlocks.OVERGROWTH.get()
        );
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
                GEBlocks.GEYSER.get()
        );

        this.tag(GEBlockTags.MAPLE_LOGS).add(
            GEBlocks.MAPLE_LOG.get(),
            GEBlocks.STRIPPED_MAPLE_LOG.get(),
            GEBlocks.MAPLE_WOOD.get(),
            GEBlocks.STRIPPED_MAPLE_WOOD.get()
        );
        this.tag(BlockTags.LOGS_THAT_BURN).addTag(GEBlockTags.MAPLE_LOGS);
        this.tag(BlockTags.MUSHROOM_GROW_BLOCK).addTag(BlockTags.LOGS_THAT_BURN);

        // ========== WORLD GENERATION TAGS ============================================================================
        this.tag(GEBlockTags.OVERGROWTH_GROWABLE_BLOCKS).add(
                Blocks.PODZOL,
                Blocks.MOSSY_COBBLESTONE
        );
        this.tag(GEBlockTags.CAN_FALLEN_TREES_GENERATE_ON).add(
                Blocks.GRASS_BLOCK,
                Blocks.DIRT,
                Blocks.PODZOL,
                Blocks.COARSE_DIRT
        );
        this.tag(GEBlockTags.SAVANNA_TERRACOTTA).add(
            Blocks.LIGHT_GRAY_TERRACOTTA,
            Blocks.WHITE_TERRACOTTA,
            Blocks.PACKED_MUD,
            Blocks.BROWN_TERRACOTTA
        );
        this.tag(BlockTags.OVERWORLD_CARVER_REPLACEABLES).add(Blocks.PACKED_MUD);
        this.tag(BlockTags.LEAVES).add(GEBlocks.RED_MAPLE_LEAVES.get(), GEBlocks.BROWN_MAPLE_LEAVES.get(), GEBlocks.ASPEN_LEAVES.get());
        this.tag(BlockTags.MINEABLE_WITH_HOE).add(GEBlocks.RED_MAPLE_LEAVES.get(), GEBlocks.BROWN_MAPLE_LEAVES.get(), GEBlocks.ASPEN_LEAVES.get());
    }

    private void populateTags(BlockFamily family) {
        for (BlockFamily.Variant variant : family.getVariants().keySet()) {
            switch (variant) {
                case WALL -> this.tag(BlockTags.WALLS).add(family.get(variant));
                case SLAB -> this.tag(BlockTags.SLABS).add(family.get(variant));
                case STAIRS -> this.tag(BlockTags.STAIRS).add(family.get(variant));
                case FENCE -> this.tag(BlockTags.FENCES).add(family.get(variant));
                case FENCE_GATE -> this.tag(BlockTags.FENCE_GATES).add(family.get(variant));
                default -> {}
            }
        }
    }
}
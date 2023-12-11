package com.blackgear.geologicexpansion.data.common.tag;

import com.blackgear.geologicexpansion.common.registries.GEBlocks;
import com.blackgear.geologicexpansion.core.data.GEBlockTags;
import com.blackgear.geologicexpansion.data.resources.GEBlockFamilies;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.BlockFamily;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;

public class BlockTagGenerator extends FabricTagProvider.BlockTagProvider {
    public BlockTagGenerator(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider arg) {
        GEBlockFamilies.getAllFamilies().forEach(this::populateTags);

        GEBlockFamilies.getStoneFamilies().forEach(family -> {
            this.getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_PICKAXE).add(family.getBaseBlock());
            family.getVariants().forEach((variant, block) -> this.getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_PICKAXE).add(block));
        });

        this.getOrCreateTagBuilder(GEBlockTags.OVERGROWTH_GROWABLE_BLOCKS).add(
                Blocks.PODZOL,
                Blocks.MOSSY_COBBLESTONE
        );
        this.getOrCreateTagBuilder(BlockTags.BASE_STONE_OVERWORLD).add(
                GEBlocks.PRISMATIC_STONE.get(),
                GEBlocks.LIMESTONE.get()
        );
        this.getOrCreateTagBuilder(BlockTags.STONE_ORE_REPLACEABLES).add(
                GEBlocks.PRISMATIC_STONE.get(),
                GEBlocks.LIMESTONE.get()
        );
        this.getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_PICKAXE).add(
                GEBlocks.GEYSER.get()
        );
    }

    private void populateTags(BlockFamily family) {
        if (family.getVariants().containsKey(BlockFamily.Variant.WALL)) {
            this.getOrCreateTagBuilder(BlockTags.WALLS).add(family.get(BlockFamily.Variant.WALL));
        }
        if (family.getVariants().containsKey(BlockFamily.Variant.SLAB)) {
            this.getOrCreateTagBuilder(BlockTags.SLABS).add(family.get(BlockFamily.Variant.SLAB));
        }
        if (family.getVariants().containsKey(BlockFamily.Variant.STAIRS)) {
            this.getOrCreateTagBuilder(BlockTags.STAIRS).add(family.get(BlockFamily.Variant.STAIRS));
        }
        if (family.getVariants().containsKey(BlockFamily.Variant.FENCE)) {
            this.getOrCreateTagBuilder(BlockTags.FENCES).add(family.get(BlockFamily.Variant.FENCE));
        }
        if (family.getVariants().containsKey(BlockFamily.Variant.FENCE_GATE)) {
            this.getOrCreateTagBuilder(BlockTags.FENCE_GATES).add(family.get(BlockFamily.Variant.FENCE_GATE));
        }
    }
}
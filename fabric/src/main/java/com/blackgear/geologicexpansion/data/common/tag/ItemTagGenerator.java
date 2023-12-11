package com.blackgear.geologicexpansion.data.common.tag;

import com.blackgear.geologicexpansion.common.registries.GEBlocks;
import com.blackgear.geologicexpansion.data.resources.GEBlockFamilies;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.BlockFamily;
import net.minecraft.tags.ItemTags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ItemTagGenerator extends FabricTagProvider.ItemTagProvider {
    public ItemTagGenerator(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture, @Nullable FabricTagProvider.BlockTagProvider blockTagProvider) {
        super(output, completableFuture, blockTagProvider);
    }

    @Override
    protected void addTags(HolderLookup.Provider arg) {
        GEBlockFamilies.getAllFamilies().forEach(this::populateTags);

        this.getOrCreateTagBuilder(ItemTags.STONE_TOOL_MATERIALS).add(GEBlocks.PRISMATIC_STONE.get().asItem());
        this.getOrCreateTagBuilder(ItemTags.STONE_CRAFTING_MATERIALS).add(GEBlocks.PRISMATIC_STONE.get().asItem());
    }

    private void populateTags(BlockFamily family) {
        if (family.getVariants().containsKey(BlockFamily.Variant.WALL)) {
            this.getOrCreateTagBuilder(ItemTags.WALLS).add(family.get(BlockFamily.Variant.WALL).asItem());
        }
        if (family.getVariants().containsKey(BlockFamily.Variant.SLAB)) {
            this.getOrCreateTagBuilder(ItemTags.SLABS).add(family.get(BlockFamily.Variant.SLAB).asItem());
        }
        if (family.getVariants().containsKey(BlockFamily.Variant.STAIRS)) {
            this.getOrCreateTagBuilder(ItemTags.STAIRS).add(family.get(BlockFamily.Variant.STAIRS).asItem());
        }
        if (family.getVariants().containsKey(BlockFamily.Variant.FENCE)) {
            this.getOrCreateTagBuilder(ItemTags.FENCES).add(family.get(BlockFamily.Variant.FENCE).asItem());
        }
    }
}

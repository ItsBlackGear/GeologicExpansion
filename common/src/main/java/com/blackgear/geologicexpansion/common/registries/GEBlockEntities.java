package com.blackgear.geologicexpansion.common.registries;

import com.blackgear.geologicexpansion.common.block.entity.GeyserBlockEntity;
import com.blackgear.geologicexpansion.core.GeologicExpansion;
import com.blackgear.geologicexpansion.core.platform.CoreRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

public class GEBlockEntities {
    public static final CoreRegistry<BlockEntityType<?>> BLOCK_ENTITIES = CoreRegistry.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, GeologicExpansion.MOD_ID);

    public static final Supplier<BlockEntityType<GeyserBlockEntity>> GEYSER = BLOCK_ENTITIES.register("geyser", () -> BlockEntityType.Builder.of(GeyserBlockEntity::new, GEBlocks.GEYSER.get()).build(null));
}
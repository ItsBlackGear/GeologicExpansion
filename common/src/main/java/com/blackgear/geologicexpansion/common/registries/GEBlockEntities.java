package com.blackgear.geologicexpansion.common.registries;

import com.blackgear.geologicexpansion.common.block.entity.GeyserBlockEntity;
import com.blackgear.geologicexpansion.core.GeologicExpansion;
import com.blackgear.geologicexpansion.core.platform.CoreRegistry;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.Objects;
import java.util.function.Supplier;

public class GEBlockEntities {
    public static final CoreRegistry<BlockEntityType<?>> BLOCK_ENTITIES = CoreRegistry.create(Registry.BLOCK_ENTITY_TYPE, GeologicExpansion.MOD_ID);

    public static final Supplier<BlockEntityType<GeyserBlockEntity>> GEYSER = create("geyser", BlockEntityType.Builder.of(GeyserBlockEntity::new, GEBlocks.GEYSER.get()));

    private static <T extends BlockEntity> Supplier<BlockEntityType<T>> create(String key, BlockEntityType.Builder<T> builder) {
        return BLOCK_ENTITIES.register(key, () -> builder.build(Util.fetchChoiceType(References.BLOCK_ENTITY, key)));
    }
}
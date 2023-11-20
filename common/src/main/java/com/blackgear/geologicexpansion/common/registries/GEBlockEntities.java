package com.blackgear.geologicexpansion.common.registries;

import com.blackgear.geologicexpansion.common.block.entity.GeyserBlockEntity;
import com.blackgear.geologicexpansion.core.GeologicExpansion;
import com.blackgear.geologicexpansion.core.mixin.access.BuilderAccessor;
import com.blackgear.geologicexpansion.core.platform.CoreRegistry;
import com.google.common.collect.ImmutableSet;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public class GEBlockEntities {
    public static final CoreRegistry<BlockEntityType<?>> BLOCK_ENTITIES = CoreRegistry.create(Registry.BLOCK_ENTITY_TYPE, GeologicExpansion.MOD_ID);

    public static final Supplier<BlockEntityType<GeyserBlockEntity>> GEYSER = BLOCK_ENTITIES.register("geyser", () -> BlockEntityType.Builder.of(GeyserBlockEntity::new, GEBlocks.GEYSER.get()).build(null));
}
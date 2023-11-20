package com.blackgear.geologicexpansion.core.mixin.access;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Set;

@Mixin(BlockEntityType.Builder.class)
public interface BuilderAccessor {
    @Invoker("<init>")
    static <T extends BlockEntity> BlockEntityType.Builder<T> createBuilder(BlockEntityType.BlockEntitySupplier<? extends T> blockEntitySupplier, Set<Block> set) {
        throw new UnsupportedOperationException();
    }
}

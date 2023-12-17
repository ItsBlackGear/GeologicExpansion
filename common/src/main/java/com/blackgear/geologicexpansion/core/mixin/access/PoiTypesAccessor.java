package com.blackgear.geologicexpansion.core.mixin.access;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Set;

@Mixin(PoiTypes.class)
public interface PoiTypesAccessor {
    @Invoker
    static void callRegisterBlockStates(Holder<PoiType> holder) {
        throw new UnsupportedOperationException();
    }

    @Invoker
    static Set<BlockState> callGetBlockStates(Block block) {
        throw new UnsupportedOperationException();
    }
}

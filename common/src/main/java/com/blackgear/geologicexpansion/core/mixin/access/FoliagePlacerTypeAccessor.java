package com.blackgear.geologicexpansion.core.mixin.access;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(FoliagePlacerType.class)
public interface FoliagePlacerTypeAccessor {
    @Invoker("<init>")
    static <P extends FoliagePlacer> FoliagePlacerType<P> createFoliagePlacerType(Codec<P> codec) {
        throw new UnsupportedOperationException();
    }
}

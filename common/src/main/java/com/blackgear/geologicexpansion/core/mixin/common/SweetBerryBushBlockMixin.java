package com.blackgear.geologicexpansion.core.mixin.common;

import com.blackgear.geologicexpansion.common.registries.GEEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SweetBerryBushBlock.class)
public class SweetBerryBushBlockMixin {
    @Inject(
        method = "entityInside",
        at = @At(
            "HEAD"
        ),
        cancellable = true
    )
    private void ge$entityInside(BlockState state, Level level, BlockPos pos, Entity entity, CallbackInfo ci) {
        if (entity instanceof LivingEntity living && entity.getType() == GEEntities.GRIZZLY.get() && living.isBaby()) {
            ci.cancel();
        }
    }
}
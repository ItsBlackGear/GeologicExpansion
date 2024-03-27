package com.blackgear.geologicexpansion.core.mixin.common.block;

import com.blackgear.geologicexpansion.common.entity.resource.FluidWalker;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LiquidBlock.class)
public class LiquidBlockMixin {
    @Shadow @Final public static IntegerProperty LEVEL;

    @Inject(
        method = "getCollisionShape",
        at = @At("HEAD"),
        cancellable = true
    )
    private void ge$getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context, CallbackInfoReturnable<VoxelShape> cir) {
        if (context instanceof EntityCollisionContext ctx && ctx.getEntity() instanceof FluidWalker entity) {
            cir.setReturnValue(
                context.isAbove(entity.getStableLiquidShape(), pos, true)
                && state.getValue(LEVEL) == 0
                && context.canStandOnFluid(level.getFluidState(pos.above()), state.getFluidState())
                ? entity.getStableLiquidShape()
                : Shapes.empty()
            );
        }
    }
}
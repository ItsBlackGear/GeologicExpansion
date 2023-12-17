package com.blackgear.geologicexpansion.core.mixin.fabric.datafix;

import com.blackgear.geologicexpansion.core.datafix.DataFixesInternals;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.chunk.storage.ChunkSerializer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value = ChunkSerializer.class, priority = 1001)
public class ChunkSerializerMixin {
    @ModifyVariable(method = "write", at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/CompoundTag;putInt(Ljava/lang/String;I)V", ordinal = 0))
    private static CompoundTag addModDataVersions(CompoundTag compound) {
        return DataFixesInternals.get().addModDataVersions(compound);
    }
}
package com.blackgear.geologicexpansion.core.mixin.fabric.datafix;

import com.blackgear.geologicexpansion.core.datafix.DataFixesInternals;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Player.class, priority = 1001)
public class PlayerMixin {
    @Inject(method = "addAdditionalSaveData", at = @At("RETURN"))
    public void addModDataVersions(CompoundTag compound, CallbackInfo ci) {
        DataFixesInternals.get().addModDataVersions(compound);
    }
}
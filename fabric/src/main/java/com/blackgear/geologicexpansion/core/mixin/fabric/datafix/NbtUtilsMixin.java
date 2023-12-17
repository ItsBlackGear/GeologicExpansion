package com.blackgear.geologicexpansion.core.mixin.fabric.datafix;

import com.blackgear.geologicexpansion.core.datafix.DataFixesInternals;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.mojang.datafixers.DataFixer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.util.datafix.DataFixTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = NbtUtils.class, priority = 1001)
public class NbtUtilsMixin {
    @ModifyReturnValue(method = "update(Lcom/mojang/datafixers/DataFixer;Lnet/minecraft/util/datafix/DataFixTypes;Lnet/minecraft/nbt/CompoundTag;II)Lnet/minecraft/nbt/CompoundTag;", at = @At("RETURN"))
    private static CompoundTag updateDataWithFixers(CompoundTag original, DataFixer fixer, DataFixTypes fixTypes, CompoundTag compound, int oldVersion, int targetVersion) {
        return DataFixesInternals.get().updateWithAllFixers(fixTypes, original);
    }
}
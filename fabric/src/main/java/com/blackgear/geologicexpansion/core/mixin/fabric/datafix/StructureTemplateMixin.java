package com.blackgear.geologicexpansion.core.mixin.fabric.datafix;

import com.blackgear.geologicexpansion.core.datafix.DataFixesInternals;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = StructureTemplate.class, priority = 1001)
public class StructureTemplateMixin {
    @ModifyReturnValue(method = "save", at = @At("RETURN"))
    private CompoundTag addModDataVersions(CompoundTag out, CompoundTag compound) {
        DataFixesInternals.get().addModDataVersions(out);
        return out;
    }
}
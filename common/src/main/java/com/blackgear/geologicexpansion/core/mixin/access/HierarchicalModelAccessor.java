package com.blackgear.geologicexpansion.core.mixin.access;

import net.minecraft.client.model.HierarchicalModel;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(HierarchicalModel.class)
public interface HierarchicalModelAccessor {
    @Accessor("ANIMATION_VECTOR_CACHE")
    static Vector3f getAnimationVectorCache() {
        throw new UnsupportedOperationException();
    }
}

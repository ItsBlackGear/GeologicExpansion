package com.blackgear.geologicexpansion.core.mixin.common;

import com.blackgear.geologicexpansion.common.worldgen.surface.GESurfaceRules;
import com.blackgear.geologicexpansion.core.util.LevelUtils;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.progress.ChunkProgressListener;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.storage.WorldData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin {
    @Shadow public abstract WorldData getWorldData();

    @Inject(method = "createLevels", at = @At("RETURN"))
    private void addSurfaceRules(ChunkProgressListener listener, CallbackInfo ci) {
        LevelUtils.appendSurfaceRules(this.getWorldData(), LevelStem.OVERWORLD, GESurfaceRules.makeRules());
    }
}
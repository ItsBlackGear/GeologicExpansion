package com.blackgear.geologicexpansion.common.entity.duck.behavior;

import com.blackgear.geologicexpansion.common.entity.duck.ODuck;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.level.block.Blocks;

import java.util.Map;

public class DuckStartFishing<E extends ODuck> extends Behavior<E> {
    public DuckStartFishing(Map<MemoryModuleType<?>, MemoryStatus> map) {
        super(map);
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, E entity) {
        if (entity.getRandom().nextInt(1000) != 0) {
            return false;
        }

        BlockPos pos = entity.blockPosition();
        return level.getBlockState(pos.below()).is(Blocks.WATER) && entity.canFish() && entity.shouldFish();
    }

    @Override
    protected void start(ServerLevel level, E entity, long gameTime) {
        level.broadcastEntityEvent(entity, ODuck.DUCK_FISHING_ANIMATION);
        entity.getNavigation().stop();
    }

    @Override
    protected void stop(ServerLevel level, E entity, long gameTime) {
        super.stop(level, entity, gameTime);
    }

    @Override
    protected boolean canStillUse(ServerLevel level, E entity, long gameTime) {
        return super.canStillUse(level, entity, gameTime);
    }

    @Override
    protected void tick(ServerLevel level, E entity, long gameTime) {
        BlockPos pos = entity.blockPosition().below();
        if (level.getBlockState(pos).is(Blocks.WATER) && entity.canFish() && entity.shouldFish()) {
            entity.playSound(SoundEvents.FISHING_BOBBER_SPLASH, 0.25F, 1.0F + (level.random.nextFloat() - level.random.nextFloat()) * 0.4F);
            entity.ate();
        }
    }
}
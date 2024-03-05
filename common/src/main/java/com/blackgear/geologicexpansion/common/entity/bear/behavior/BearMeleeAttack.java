package com.blackgear.geologicexpansion.common.entity.bear.behavior;

import com.blackgear.geologicexpansion.common.entity.bear.Grizzly;
import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class BearMeleeAttack extends Behavior<Grizzly> {
    private final int cooldownBetweenAttacks;

    public BearMeleeAttack(int cooldownBetweenAttacks) {
        super(
            ImmutableMap.of(
                MemoryModuleType.LOOK_TARGET,
                MemoryStatus.REGISTERED,
                MemoryModuleType.ATTACK_TARGET,
                MemoryStatus.VALUE_PRESENT,
                MemoryModuleType.ATTACK_COOLING_DOWN,
                MemoryStatus.VALUE_ABSENT
            )
        );
        this.cooldownBetweenAttacks = cooldownBetweenAttacks;
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, Grizzly entity) {
        LivingEntity target = this.getAttackTarget(entity);
        return BehaviorUtils.canSee(entity, target) && entity.isWithinMeleeAttackRange(target);
    }

    @Override
    protected void start(ServerLevel level, Grizzly entity, long gameTime) {
        LivingEntity target = this.getAttackTarget(entity);
        BehaviorUtils.lookAtEntity(entity, target);
        entity.setStanding(true);
        entity.swing(InteractionHand.MAIN_HAND);
        entity.doHurtTarget(target);
        entity.getBrain().setMemoryWithExpiry(MemoryModuleType.ATTACK_COOLING_DOWN, true, this.cooldownBetweenAttacks);
    }

    @Override
    protected void stop(ServerLevel level, Grizzly entity, long gameTime) {
        entity.setStanding(false);
    }

    private LivingEntity getAttackTarget(Mob mob) {
        return mob.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).get();
    }
}

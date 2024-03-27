package com.blackgear.geologicexpansion.common.entity.bear.goals;

import com.blackgear.geologicexpansion.common.entity.bear.Grizzly;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class GrizzlyHuntTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
    private final Grizzly grizzly;

    public GrizzlyHuntTargetGoal(Grizzly grizzly, Class<T> targetType, int interval, boolean bl, boolean bl2, @Nullable Predicate<LivingEntity> targetConditions) {
        super(grizzly, targetType, interval, bl, bl2, targetConditions);
        this.grizzly = grizzly;
    }

    @Override
    public boolean canUse() {
        return (!this.grizzly.ateRecently() || this.grizzly.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty()) && !this.grizzly.isBaby() && super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        return this.grizzly.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty() && super.canContinueToUse();
    }
}
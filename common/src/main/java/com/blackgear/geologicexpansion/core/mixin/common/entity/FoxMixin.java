package com.blackgear.geologicexpansion.core.mixin.common.entity;

import com.blackgear.geologicexpansion.common.entity.bear.Grizzly;
import com.blackgear.geologicexpansion.common.entity.duck.Duck;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Fox.class)
public abstract class FoxMixin extends Animal {
    @Shadow public abstract Fox.Type getFoxType();
    @Shadow abstract boolean isDefending();

    protected FoxMixin(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(
        method = "registerGoals",
        at = @At("TAIL")
    )
    private void ge$registerGoals(CallbackInfo ci) {
        this.goalSelector.addGoal(4, new AvoidEntityGoal<>(this, Grizzly.class, 8.0F, 1.6, 1.4, entity -> !this.isDefending()));
    }

    @Inject(
        method = "setTargetGoals",
        at = @At("TAIL")
    )
    private void ge$setTargetGoals(CallbackInfo ci) {
        if (this.getFoxType() == Fox.Type.RED) {
            this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Animal.class, 10, false, false, animal -> animal instanceof Duck));
        } else {
            this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, Animal.class, 10, false, false, animal -> animal instanceof Duck));
        }
    }
}
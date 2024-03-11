package com.blackgear.geologicexpansion.common.entity.duck;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;

public class DuckB extends AbstractDuck {
    private int animationTimeout = 0;

    public DuckB(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level.isClientSide) {
            this.setupAnimationStates();
        }
    }

    private void setupAnimationStates() {
        if (this.animationTimeout == 0) {
            this.animationTimeout = this.random.nextInt(40) + 80;
            this.fishingAnimationState.start(this.tickCount);
        } else {
            --this.animationTimeout;
        }
    }
}
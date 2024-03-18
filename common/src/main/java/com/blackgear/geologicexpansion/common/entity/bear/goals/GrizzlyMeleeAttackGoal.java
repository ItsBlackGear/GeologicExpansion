package com.blackgear.geologicexpansion.common.entity.bear.goals;

import com.blackgear.geologicexpansion.common.entity.bear.Grizzly;
import com.blackgear.geologicexpansion.common.entity.resource.EntityState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class GrizzlyMeleeAttackGoal extends MeleeAttackGoal {
    private final Grizzly grizzly;

    public GrizzlyMeleeAttackGoal(Grizzly grizzly, double speedModifier, boolean followTargetEvenIfUnseen) {
        super(grizzly, speedModifier, followTargetEvenIfUnseen);
        this.grizzly = grizzly;
    }

    @Override
    protected void checkAndPerformAttack(LivingEntity enemy, double distToEnemySqr) {
        double distToEnemy = this.getAttackReachSqr(enemy);
        if (distToEnemySqr <= distToEnemy && this.isTimeToAttack()) {
            this.resetAttackCooldown();
            this.grizzly.doHurtTarget(enemy);
            this.grizzly.setState(EntityState.IDLE);
        } else if (distToEnemySqr <= distToEnemy * 2.0D) {
            if (this.isTimeToAttack()) {
                this.grizzly.setState(EntityState.IDLE);
                this.resetAttackCooldown();
            }

            if (this.getTicksUntilNextAttack() <= 10) {
                this.grizzly.setState(EntityState.ATTACKING);
//                this.grizzly.playWarningSound();
            }
        } else {
            this.resetAttackCooldown();
            this.grizzly.setState(EntityState.IDLE);
        }
    }

    @Override
    public void stop() {
        this.grizzly.setState(EntityState.IDLE);
        super.stop();
    }

    @Override
    protected double getAttackReachSqr(LivingEntity attackTarget) {
        return 4.0F + attackTarget.getBbWidth();
    }
}
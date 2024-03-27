package com.blackgear.geologicexpansion.common.entity.bear.goals;

import com.blackgear.geologicexpansion.common.entity.bear.Grizzly;
import com.blackgear.geologicexpansion.common.entity.resource.EntityState;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class GrizzlyMeleeAttackGoal extends MeleeAttackGoal {
    private final Grizzly grizzly;
    private int attackDelay = 40;
    private int ticksUntilNextAttack = 40;
    private boolean shouldCountTillNextAttack = false;

    public GrizzlyMeleeAttackGoal(Grizzly grizzly) {
        super(grizzly, 2.5, true);
        this.grizzly = grizzly;
    }

    @Override
    public void start() {
        super.start();
        this.attackDelay = 40;
        this.ticksUntilNextAttack = 40;
    }

    @Override
    protected void checkAndPerformAttack(LivingEntity enemy, double distToEnemySqr) {
        if (this.isEnemyWithinAttackDistance(enemy, distToEnemySqr)) {
            this.shouldCountTillNextAttack = true;

            if (this.isTimeToStartAttackAnimation()) {
                this.grizzly.setState(EntityState.ATTACKING);
            }

            if (this.isTimeToAttack()) {
                this.mob.getLookControl().setLookAt(enemy.getX(), enemy.getY(), enemy.getZ());
                this.performAttack(enemy);
            }
        } else {
            this.resetAttackCooldown();
            this.shouldCountTillNextAttack = false;
            this.grizzly.setState(EntityState.IDLE);
            this.grizzly.attackAnimationTimeout = 0;
        }
    }

    private boolean isEnemyWithinAttackDistance(LivingEntity enemy, double distToEnemySqr) {
        return distToEnemySqr <= this.getAttackReachSqr(enemy);
    }

    @Override
    protected void resetAttackCooldown() {
        this.ticksUntilNextAttack = this.adjustedTickDelay(this.attackDelay);
    }

    @Override
    protected boolean isTimeToAttack() {
        return this.ticksUntilNextAttack <= 0;
    }

    protected boolean isTimeToStartAttackAnimation() {
        return this.ticksUntilNextAttack <= this.attackDelay - 20;
    }

    @Override
    protected int getTicksUntilNextAttack() {
        return this.ticksUntilNextAttack;
    }

    protected void performAttack(LivingEntity enemy) {
        this.resetAttackCooldown();
        this.mob.swing(InteractionHand.MAIN_HAND);
        this.mob.doHurtTarget(enemy);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.shouldCountTillNextAttack) {
            this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1, 0);
        }
    }

    @Override
    public void stop() {
        this.grizzly.setState(EntityState.IDLE);
        super.stop();
    }

    @Override
    protected double getAttackReachSqr(LivingEntity attackTarget) {
        float f = this.grizzly.getBbWidth() - 0.1F;
        return f * 2.0F * f * 2.0F + attackTarget.getBbWidth();
//        return 4.0F + attackTarget.getBbWidth();
    }
}
package com.blackgear.geologicexpansion.common.entity.duck.goals;

import com.blackgear.geologicexpansion.common.entity.duck.Duck;
import com.mojang.datafixers.DataFixUtils;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.List;
import java.util.function.Predicate;

public class DuckFollowFlockLeaderGoal extends Goal {
    private final Duck duck;
    private int timeToRecalcPath;
    private int nextStartTick;

    public DuckFollowFlockLeaderGoal(Duck duck) {
        this.duck = duck;
        this.nextStartTick = this.nextStartTick(duck);
    }

    private int nextStartTick(Duck duck) {
        return reducedTickDelay(200 + duck.getRandom().nextInt(200) % 20);
    }

    @Override
    public boolean canUse() {
        if (this.duck.hasFollowers()) {
            return false;
        } else if (this.duck.isFollower()) {
            return true;
        } else if (this.nextStartTick > 0) {
            --this.nextStartTick;
            return false;
        } else {
            this.nextStartTick = this.nextStartTick(this.duck);
            Predicate<Duck> canBeLeader = duck -> duck.canBeFollowed() || !duck.isFollower();
            List<? extends Duck> ducks = this.duck.level.getEntitiesOfClass(this.duck.getClass(), this.duck.getBoundingBox().inflate(8.0D, 8.0D, 8.0D), canBeLeader);
            Duck leader = DataFixUtils.orElse(ducks.stream().filter(Duck::canBeFollowed).findAny(), this.duck);
            leader.addFollowers(ducks.stream().filter(duck -> !duck.isFollower()));
            return this.duck.isFollower();
        }
    }

    @Override
    public boolean canContinueToUse() {
        return this.duck.isFollower() && this.duck.inRangeOfLeader();
    }

    @Override
    public void start() {
        this.timeToRecalcPath = 0;
    }

    @Override
    public void stop() {
        this.duck.stopFollowing();
    }

    @Override
    public void tick() {
        if (--this.timeToRecalcPath <= 0) {
            this.timeToRecalcPath = this.adjustedTickDelay(10);
            this.duck.pathToLeader();
        }
    }
}
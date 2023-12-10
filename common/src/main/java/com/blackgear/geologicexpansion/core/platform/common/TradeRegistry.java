package com.blackgear.geologicexpansion.core.platform.common;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;

public class TradeRegistry {
    @ExpectPlatform
    public static void registerTrade(VillagerProfession profession, Level level, VillagerTrades.ItemListing... trades) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void registerWanderingTrade(boolean rare, VillagerTrades.ItemListing... trades) {
        throw new AssertionError();
    }

    public enum Level {
        NOVICE(1),
        APPRENTICE(2),
        JOURNEYMAN(3),
        EXPERT(4),
        MASTER(5);

        private final int value;

        Level(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }
}
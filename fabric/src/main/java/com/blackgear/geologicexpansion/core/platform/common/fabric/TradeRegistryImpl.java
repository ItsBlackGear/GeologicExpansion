package com.blackgear.geologicexpansion.core.platform.common.fabric;

import com.blackgear.geologicexpansion.core.platform.common.TradeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;

import java.util.Collections;

public class TradeRegistryImpl {
    public static void registerTrade(VillagerProfession profession, TradeRegistry.Level level, VillagerTrades.ItemListing... trades) {
        TradeOfferHelper.registerVillagerOffers(profession, level.getValue(), tradeList -> Collections.addAll(tradeList, trades));
    }

    public static void registerWanderingTrade(boolean rare, VillagerTrades.ItemListing... trades) {
        TradeOfferHelper.registerWanderingTraderOffers(rare ? 2 : 1, tradeList -> Collections.addAll(tradeList, trades));
    }
}
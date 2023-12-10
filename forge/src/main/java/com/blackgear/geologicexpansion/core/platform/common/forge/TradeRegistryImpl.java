package com.blackgear.geologicexpansion.core.platform.common.forge;

import com.blackgear.geologicexpansion.core.GeologicExpansion;
import com.blackgear.geologicexpansion.core.platform.common.TradeRegistry;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(modid = GeologicExpansion.MOD_ID)
public class TradeRegistryImpl {
    private static final Map<VillagerProfession, Int2ObjectMap<List<VillagerTrades.ItemListing>>> TRADES = new HashMap<>();
    private static final List<VillagerTrades.ItemListing> WANDERING_TRADES_GENERIC_TRADES = new ArrayList<>();
    private static final List<VillagerTrades.ItemListing> WANDERING_TRADES_RARE_TRADES = new ArrayList<>();

    public static void registerTrade(VillagerProfession profession, TradeRegistry.Level level, VillagerTrades.ItemListing... trades) {
        Int2ObjectMap<List<VillagerTrades.ItemListing>> perProfession = TRADES.computeIfAbsent(profession, trade -> new Int2ObjectOpenHashMap<>());
        List<VillagerTrades.ItemListing> perLevel = perProfession.computeIfAbsent(level.getValue(), trade -> new ArrayList<>());
        Collections.addAll(perLevel, trades);
    }

    public static void registerWanderingTrade(boolean rare, VillagerTrades.ItemListing... trades) {
        if (rare) {
            Collections.addAll(WANDERING_TRADES_RARE_TRADES, trades);
        } else {
            Collections.addAll(WANDERING_TRADES_GENERIC_TRADES, trades);
        }
    }

    @SubscribeEvent
    public static void onTradeEvent(VillagerTradesEvent event) {
        Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = TRADES.get(event.getType());

        if (trades != null) {
            for (Int2ObjectMap.Entry<List<VillagerTrades.ItemListing>> entry : trades.int2ObjectEntrySet()) {
                event.getTrades().computeIfAbsent(entry.getIntKey(), level -> NonNullList.create()).addAll(entry.getValue());
            }
        }
    }

    @SubscribeEvent
    public static void onWanderingTradeEvent(WandererTradesEvent event) {
        if (!WANDERING_TRADES_GENERIC_TRADES.isEmpty()) {
            event.getGenericTrades().addAll(WANDERING_TRADES_GENERIC_TRADES);
        }

        if (!WANDERING_TRADES_RARE_TRADES.isEmpty()) {
            event.getRareTrades().addAll(WANDERING_TRADES_RARE_TRADES);
        }
    }
}
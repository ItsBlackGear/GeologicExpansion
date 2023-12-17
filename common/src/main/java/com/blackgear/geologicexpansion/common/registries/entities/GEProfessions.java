package com.blackgear.geologicexpansion.common.registries.entities;

import com.blackgear.geologicexpansion.common.registries.GEPointOfInterests;
import com.blackgear.geologicexpansion.core.GeologicExpansion;
import com.blackgear.geologicexpansion.core.platform.CoreRegistry;
import com.google.common.collect.ImmutableSet;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class GEProfessions {
    public static final CoreRegistry<VillagerProfession> PROFESSIONS = CoreRegistry.create(Registry.VILLAGER_PROFESSION, GeologicExpansion.MOD_ID);

    public static final Supplier<VillagerProfession> GEOLOGIST = create(
            "geologist",
            GEPointOfInterests.GEOLOGIST_TABLE,
            SoundEvents.VILLAGER_WORK_ARMORER
    );
    
    
    private static Supplier<VillagerProfession> create(String key, ResourceKey<PoiType> jobSite, @Nullable SoundEvent workSound) {
        return create(key, holder -> holder.is(jobSite), holder -> holder.is(jobSite), workSound);
    }
    
    private static Supplier<VillagerProfession> create(String key, Predicate<Holder<PoiType>> jobSite, Predicate<Holder<PoiType>> acquirableJobSites, @Nullable SoundEvent workSound) {
        return create(key, jobSite, acquirableJobSites, ImmutableSet.of(), ImmutableSet.of(), workSound);
    }
    
    private static Supplier<VillagerProfession> create(String key, ResourceKey<PoiType> jobSite, ImmutableSet<Item> requestedItems, ImmutableSet<Block> secondaryPoi, @Nullable SoundEvent workSound) {
        return create(key, holder -> holder.is(jobSite), holder -> holder.is(jobSite), requestedItems, secondaryPoi, workSound);
    }
    
    private static Supplier<VillagerProfession> create(String key, Predicate<Holder<PoiType>> heldJobSite, Predicate<Holder<PoiType>> accessibleJobSites, ImmutableSet<Item> requestedItems, ImmutableSet<Block> secondaryPoi, @Nullable SoundEvent workSound) {
        return PROFESSIONS.register(key, () -> new VillagerProfession(key, heldJobSite, accessibleJobSites, requestedItems, secondaryPoi, workSound));
    }
}
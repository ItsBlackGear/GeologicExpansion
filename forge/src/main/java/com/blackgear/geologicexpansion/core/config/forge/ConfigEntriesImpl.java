package com.blackgear.geologicexpansion.core.config.forge;

import com.blackgear.geologicexpansion.core.GeologicExpansion;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.tuple.Pair;

@Mod.EventBusSubscriber(modid = GeologicExpansion.MOD_ID)
public class ConfigEntriesImpl {
    private static final Pair<Common, ForgeConfigSpec> FORGE_CONFIGURATIONS = new ForgeConfigSpec.Builder().configure(Common::new);
    public static final ForgeConfigSpec COMMON_SPEC = FORGE_CONFIGURATIONS.getRight();
    public static final ConfigEntriesImpl.Common COMMON = FORGE_CONFIGURATIONS.getLeft();

    // ========== TOGGLEABLE FEATURES ==================================================================================
    public static boolean includeGeysers() {
        return COMMON.includeGeysers.get();
    }

    public static boolean includeLimestone() {
        return COMMON.includeLimestone.get();
    }

    public static boolean includeOvergrowth() {
        return COMMON.includeOvergrowth.get();
    }

    public static boolean includeDucks() {
        return COMMON.includeDucks.get();
    }

    // ========== ENTITY BEHAVIOR ======================================================================================
    public static boolean canDucksFish() {
        return COMMON.canDucksFish.get();
    }

    // ========== BIOME CONFIGURATION ==================================================================================
    public static boolean includePrismaticCaldera() {
        return true;
//        return COMMON.includePrismaticCaldera.get();
    }

    // ========== FORGE CONFIGURATION ==================================================================================
    public static class Common {
        // ========== TOGGLEABLE FEATURES ==============================================================================
        public final ForgeConfigSpec.ConfigValue<Boolean> includeGeysers;
        public final ForgeConfigSpec.ConfigValue<Boolean> includeLimestone;
        public final ForgeConfigSpec.ConfigValue<Boolean> includeOvergrowth;
        public final ForgeConfigSpec.ConfigValue<Boolean> includeDucks;
        public final ForgeConfigSpec.ConfigValue<Boolean> includePrismaticCaldera;

        // ========== ENTITY BEHAVIOR ==================================================================================
        public final ForgeConfigSpec.ConfigValue<Boolean> canDucksFish;

        protected Common(ForgeConfigSpec.Builder builder) {
            builder.push("toggleable_features");
            builder.comment("Toggleable features");
            this.includeGeysers = builder.comment("Determine if Geysers should generate").define("includeGeysers", true);
            this.includeLimestone = builder.comment("Determine if Limestone should generate").define("includeLimestone", true);
            this.includeOvergrowth = builder.comment("Determine if Overgrowth should generate").define("includeOvergrowth", true);
            this.includeDucks = builder.comment("Determine if Ducks should spawn").define("includeDucks", true);
            this.includePrismaticCaldera = builder.comment("Determine if the Prismatic Caldera biome should spawn").define("includePrismaticCaldera", true);
            builder.pop();
            builder.push("entity_behavior");
            builder.comment("Entity behavior");
            this.canDucksFish = builder.comment("Determine if Ducks should be able to fish").define("canDucksFish", true);
            builder.pop();
        }
    }
}
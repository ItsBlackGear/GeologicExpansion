package com.blackgear.geologicexpansion.data.common.tag;

import com.blackgear.geologicexpansion.common.registries.GEPointOfInterests;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.Registry;
import net.minecraft.tags.PoiTypeTags;
import net.minecraft.world.entity.ai.village.poi.PoiType;

public class PoiTypeTagGenerator extends FabricTagProvider<PoiType> {
    public PoiTypeTagGenerator(FabricDataGenerator dataGenerator) {
        super(dataGenerator, Registry.POINT_OF_INTEREST_TYPE);
    }

    @Override
    protected void generateTags() {
        this.tag(PoiTypeTags.ACQUIRABLE_JOB_SITE).add(GEPointOfInterests.GEOLOGIST_TABLE);
    }
}

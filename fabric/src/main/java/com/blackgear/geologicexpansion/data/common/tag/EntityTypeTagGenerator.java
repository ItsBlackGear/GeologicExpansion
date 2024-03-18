package com.blackgear.geologicexpansion.data.common.tag;

import com.blackgear.geologicexpansion.common.registries.GEEntities;
import com.blackgear.geologicexpansion.core.data.GEEntityTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;

public class EntityTypeTagGenerator extends FabricTagProvider.EntityTypeTagProvider {
    public EntityTypeTagGenerator(FabricDataGenerator generator) {
        super(generator);
    }

    @Override
    protected void generateTags() {
        this.tag(GEEntityTags.GRIZZLY_HUNT_TARGETS)
            .add(EntityType.FOX)
            .add(EntityType.CHICKEN)
            .add(EntityType.RABBIT)
            .add(GEEntities.DUCK.get())
        ;
        this.tag(EntityTypeTags.IMPACT_PROJECTILES)
            .add(GEEntities.DUCK_EGG.get());
    }
}
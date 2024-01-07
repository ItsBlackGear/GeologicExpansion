package com.blackgear.geologicexpansion.common.worldgen.surface.surfacerules;

import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;

public class SavannaSurfaceBuilder extends SurfaceBuilder {
    public static SurfaceRules.RuleSource generate() {
        return SurfaceRules.ifTrue(
            SurfaceRules.abovePreliminarySurface(),
            SurfaceRules.sequence(
                SurfaceRules.ifTrue(
                    SurfaceRules.isBiome(Biomes.SAVANNA_PLATEAU),
                    SurfaceRules.sequence(
                        SurfaceRules.ifTrue(
                            SurfaceRules.ON_FLOOR,
                            SurfaceRules.sequence(
                                SurfaceRules.ifTrue(
                                    SurfaceRules.yStartCheck(VerticalAnchor.absolute(54), 1),
                                    SurfaceRules.bandlands()
                                ),
                                SurfaceRules.ifTrue(
                                    SurfaceRules.waterBlockCheck(-1, 0),
                                    SurfaceRules.sequence(
                                        SurfaceRules.ifTrue(
                                            SurfaceRules.ON_CEILING,
                                            SANDSTONE
                                        ),
                                        SAND
                                    )
                                ),
                                SurfaceRules.sequence(
                                    SurfaceRules.ifTrue(
                                        SurfaceRules.ON_CEILING,
                                        STONE
                                    ),
                                    GRAVEL
                                )
                            )
                        ),
                        SurfaceRules.ifTrue(
                            SurfaceRules.yStartCheck(VerticalAnchor.absolute(63), -1),
                            SurfaceRules.sequence(
                                SurfaceRules.ifTrue(
                                    SurfaceRules.yBlockCheck(VerticalAnchor.absolute(63), 0),
                                    SurfaceRules.ifTrue(
                                        SurfaceRules.not(SurfaceRules.yStartCheck(VerticalAnchor.absolute(74), 1)),
                                        ORANGE_TERRACOTTA
                                    )
                                ),
                                SurfaceRules.bandlands()
                            )
                        ),
                        SurfaceRules.ifTrue(
                            SurfaceRules.UNDER_FLOOR,
                            SurfaceRules.ifTrue(SurfaceRules.waterStartCheck(-6, -1), WHITE_TERRACOTTA)
                        )
                    )
                ),
                SurfaceRules.ifTrue(
                    SurfaceRules.ON_FLOOR,
                    SurfaceRules.ifTrue(
                        SurfaceRules.waterBlockCheck(-1, 0),
                        SurfaceRules.sequence(
                            SurfaceRules.ifTrue(
                                SurfaceRules.isBiome(
                                    Biomes.PLAINS,
                                    Biomes.MEADOW,
                                    Biomes.SNOWY_PLAINS,
                                    Biomes.SNOWY_TAIGA,
                                    Biomes.SUNFLOWER_PLAINS,
                                    Biomes.TAIGA,
                                    Biomes.FOREST,
                                    Biomes.FLOWER_FOREST,
                                    Biomes.BIRCH_FOREST,
                                    Biomes.OLD_GROWTH_BIRCH_FOREST,
                                    Biomes.DARK_FOREST,
                                    Biomes.OLD_GROWTH_PINE_TAIGA,
                                    Biomes.OLD_GROWTH_SPRUCE_TAIGA
                                ),
                                SurfaceRules.ifTrue(SurfaceRules.steep(), STONE)
                            ),
                            SurfaceRules.ifTrue(SurfaceRules.waterBlockCheck(0, 0), GRASS_BLOCK),
                            DIRT
                        )
                    )
                ),
                SurfaceRules.ifTrue(
                    SurfaceRules.waterStartCheck(-6, -1),
                    SurfaceRules.sequence(
                        SurfaceRules.ifTrue(
                            SurfaceRules.UNDER_FLOOR,
                            SurfaceRules.ifTrue(
                                SurfaceRules.isBiome(
                                    Biomes.PLAINS,
                                    Biomes.MEADOW,
                                    Biomes.SNOWY_PLAINS,
                                    Biomes.SNOWY_TAIGA,
                                    Biomes.SUNFLOWER_PLAINS,
                                    Biomes.TAIGA,
                                    Biomes.FOREST,
                                    Biomes.FLOWER_FOREST,
                                    Biomes.BIRCH_FOREST,
                                    Biomes.OLD_GROWTH_BIRCH_FOREST,
                                    Biomes.DARK_FOREST,
                                    Biomes.OLD_GROWTH_PINE_TAIGA,
                                    Biomes.OLD_GROWTH_SPRUCE_TAIGA
                                ),
                                SurfaceRules.sequence(
                                    SurfaceRules.ifTrue(SurfaceRules.steep(), STONE),
                                    SurfaceRules.ifTrue(SurfaceRules.waterBlockCheck(-1, 0), DIRT),
                                    DIRT
                                )
                            )
                        ),
                        DIRT
                    )
                ),
                SurfaceRules.ifTrue(
                    SurfaceRules.ON_FLOOR,
                    SurfaceRules.sequence(
                        SurfaceRules.ifTrue(SurfaceRules.ON_CEILING, STONE),
                        GRAVEL
                    )
                )
            )
        );
    }
}

package com.blackgear.geologicexpansion.common.worldgen.features;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import java.util.stream.Stream;

public class BoulderFeature extends Feature<StructurePieceConfiguration> {
    public BoulderFeature(Codec<StructurePieceConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<StructurePieceConfiguration> context) {
        BlockPos origin = context.origin();
        StructurePieceConfiguration config = context.config();
        RandomSource random = context.random();
        WorldGenLevel level = context.level();

        StructurePlaceSettings settings = new StructurePlaceSettings().setRandom(random);
        StructureTemplate template = config.getTemplate(level, random);
        Vec3i size = template.getSize();
        BlockPos offset = origin.offset((size.getX() / 2), 0, (size.getZ() / 2));
        settings.setRotationPivot(new BlockPos(size.getX() / 2, settings.getRotationPivot().getY(), size.getZ() / 2));

        if (config.requireGround()) {
            BlockPos pos = template.getZeroPositionWithTransform(offset, Mirror.NONE, Rotation.NONE);

            Stream<BlockPos> positions = BlockPos.betweenClosedStream(pos, pos.offset(size.getX(), 0, size.getZ()));
            boolean canBePlaced = positions.allMatch(pos1 -> level.getBlockState(pos1).getMaterial().isSolid());

            if (canBePlaced) {
                return template.placeInWorld(level, pos, pos, settings, random, 2);
            }
        }

        return false;
    }
}

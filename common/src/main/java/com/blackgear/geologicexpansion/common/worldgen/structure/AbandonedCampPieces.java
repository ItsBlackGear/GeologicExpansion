package com.blackgear.geologicexpansion.common.worldgen.structure;

import com.blackgear.geologicexpansion.core.GeologicExpansion;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public class AbandonedCampPieces {
    private static final ResourceLocation[] TEMPLATES = new ResourceLocation[] {
        new ResourceLocation(GeologicExpansion.MOD_ID, "campsite/campsite_1")
    };

    public static void addPieces(StructureTemplateManager manager, StructurePieceAccessor pieces, RandomSource random, BlockPos pos) {
        Rotation rotation = Rotation.getRandom(random);
        pieces.addPiece(new AbandonedCampPiece(manager, Util.getRandom(TEMPLATES, random), pos, rotation));
    }

    public static class AbandonedCampPiece extends TemplateStructurePiece {
        public AbandonedCampPiece(StructureTemplateManager manager, ResourceLocation template, BlockPos blockPos, Rotation rotation) {
            super(null, 0, manager, template, template.toString(), makeSettings(rotation), blockPos);
        }

        public AbandonedCampPiece(StructureTemplateManager manager, CompoundTag tag) {
            super(null, tag, manager, location -> makeSettings(Rotation.valueOf(tag.getString("Rot"))));
        }

        private static StructurePlaceSettings makeSettings(Rotation rotation) {
            return new StructurePlaceSettings().setRotation(rotation).setMirror(Mirror.NONE).addProcessor(BlockIgnoreProcessor.STRUCTURE_AND_AIR);
        }

        @Override
        protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {
            super.addAdditionalSaveData(context, tag);
            tag.putString("Rot", this.placeSettings.getRotation().name());
        }

        @Override
        protected void handleDataMarker(String name, BlockPos pos, ServerLevelAccessor level, RandomSource random, BoundingBox box) {}

        @Override
        public void postProcess(WorldGenLevel level, StructureManager structureManager, ChunkGenerator generator, RandomSource random, BoundingBox box, ChunkPos chunkPos, BlockPos pos) {
            box.encapsulate(this.template.getBoundingBox(this.placeSettings, this.templatePosition));
            super.postProcess(level, structureManager, generator, random, box, chunkPos, pos);
        }
    }
}
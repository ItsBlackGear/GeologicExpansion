package com.blackgear.geologicexpansion.common.registries.worldgen;

import com.blackgear.geologicexpansion.core.GeologicExpansion;
import com.blackgear.geologicexpansion.core.platform.CoreRegistry;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;

public class GEStructurePieces {
    public static final CoreRegistry<StructurePieceType> PIECES = CoreRegistry.create(Registry.STRUCTURE_PIECE, GeologicExpansion.MOD_ID);
}
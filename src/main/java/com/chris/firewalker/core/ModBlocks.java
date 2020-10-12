package com.chris.firewalker.core;

import com.chris.firewalker.Reference;
import com.chris.firewalker.blocks.SolidMagmaBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
            Reference.MOD_ID);
    public static final RegistryObject<Block> SOLID_MAGMA = BLOCKS.register("solid_magma",
            () -> new SolidMagmaBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(0.5F).sound(SoundType.GLASS).notSolid()));
}

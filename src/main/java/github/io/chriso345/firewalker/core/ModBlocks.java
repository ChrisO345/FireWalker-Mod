package github.io.chriso345.firewalker.core;

import github.io.chriso345.firewalker.Reference;
import github.io.chriso345.firewalker.blocks.SolidMagmaBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.client.sound.Sound;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import java.util.ArrayList;

public class ModBlocks {
    public static ArrayList<Block> BLOCKS = new ArrayList<Block>();
    public static ArrayList<String> BLOCK_IDS = new ArrayList<String>();


    public static final Block SOLID_MAGMA = registerBlocks(new SolidMagmaBlock(FabricBlockSettings.create()
            .mapColor(MapColor.DARK_RED).ticksRandomly().strength(0.5F).sounds(BlockSoundGroup.GLASS).nonOpaque()
            .notSolid()), "solid_magma");

    public static Block registerBlocks(Block block_id, String block_name) {
        BLOCKS.add(block_id);
        BLOCK_IDS.add(block_name);
        return block_id;
    }

    public static void register() {
        for (Block block : BLOCKS) {
            int pos = BLOCKS.indexOf(block);
            Registry.register(Registries.BLOCK, new Identifier(Reference.MOD_ID, BLOCK_IDS.get(pos)), block);
//            Registry.register(Registries.ITEM, new Identifier(Reference.MOD_ID, BLOCK_IDS.get(pos)),
//                    new BlockItem(block, new FabricItemSettings()));
        }
    }
}

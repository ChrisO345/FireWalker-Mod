package github.io.chriso345.firewalker.core;

import github.io.chriso345.firewalker.Reference;
import github.io.chriso345.firewalker.blocks.SolidMagmaBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
            Reference.MOD_ID);
    public static final RegistryObject<Block> SOLID_MAGMA = BLOCKS.register("solid_magma",
            () -> new SolidMagmaBlock(Block.Properties.of().mapColor(MapColor.NETHER).randomTicks().strength(0.5F)
                    .sound(SoundType.GLASS).noOcclusion().isRedstoneConductor(ModBlocks::never)));
// (Material.ROCK).hardnessAndResistance(0.5F).sound(SoundType.GLASS).notSolid()));

    private static boolean never(BlockState p_50806_, BlockGetter p_50807_, BlockPos p_50808_) {
        return false;
    }
}

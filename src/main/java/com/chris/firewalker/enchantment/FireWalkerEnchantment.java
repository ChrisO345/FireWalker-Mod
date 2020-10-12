package com.chris.firewalker.enchantment;

import com.chris.firewalker.Reference;
import com.chris.firewalker.core.ModBlocks;
import com.chris.firewalker.core.ModEnchantments;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.world.World;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class FireWalkerEnchantment extends Enchantment {
    public FireWalkerEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentType.ARMOR_FEET, new EquipmentSlotType[]{EquipmentSlotType.FEET});
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    public boolean isTreasureEnchantment() {
        return true;
    }

    @Override
    public int getMinEnchantability(int level) {
        return 15;
    }

    @Override
    public int getMaxEnchantability(int level) {
        return super.getMinEnchantability(level) + 50;
    }
    @Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class MagmaEquipped {
        //        @SubscribeEvent
//        public static void doStuff(PlayerTickEvent event) {
//            PlayerEntity playerIn = event.player;
//            World worldIn = playerIn.world;
//            if (playerIn.hasItemInSlot(EquipmentSlotType.FEET)
//                    && EnchantmentHelper.getEnchantmentLevel(ModEnchantments.MAGMA_WALKER.get(),
//                    playerIn.getItemStackFromSlot(EquipmentSlotType.FEET)) > 0) {
//                if (playerIn.isCrouching()) {
//                    if (worldIn.getBlockState(playerIn.getPosition().down()) == Blocks.BARRIER.getDefaultState()) {
//                        worldIn.setBlockState(playerIn.getPosition(), Blocks.AIR.getDefaultState());
//                        worldIn.setBlockState(playerIn.getPosition().down().down(), Blocks.BARRIER.getDefaultState());
//                    }
//                } else {
//                    if (worldIn.getBlockState(playerIn.getPosition().down()) == Blocks.AIR.getDefaultState()) {
//                        worldIn.setBlockState(playerIn.getPosition().down(), Blocks.BARRIER.getDefaultState());
//                    }
//                }
//            }
        @SubscribeEvent
        public static void doStuff(PlayerTickEvent event) {
            PlayerEntity playerIn = event.player;
            LivingEntity living = playerIn;
            World worldIn = playerIn.getEntityWorld();
            BlockPos pos = playerIn.getPosition();
            if (playerIn.hasItemInSlot(EquipmentSlotType.FEET)
                    && EnchantmentHelper.getEnchantmentLevel(ModEnchantments.FIRE_WALKER.get(),
                    playerIn.getItemStackFromSlot(EquipmentSlotType.FEET)) > 0) {
                int level = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.FIRE_WALKER.get(),
                        playerIn.getItemStackFromSlot(EquipmentSlotType.FEET));
                if (playerIn.isOnGround()) {
                    BlockState blockstate = ModBlocks.SOLID_MAGMA.get().getDefaultState();
                    float f = (float) Math.min(16, 2 + level);
                    BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

                    for (BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add((double) (-f), -1.0D, (double) (-f)), pos.add((double) f, -1.0D, (double) f))) {
                        if (blockpos.withinDistance(playerIn.getPositionVec(), (double) f)) {
                            blockpos$mutable.setPos(blockpos.getX(), blockpos.getY() + 1, blockpos.getZ());
                            BlockState blockstate1 = worldIn.getBlockState(blockpos$mutable);
                            if (blockstate1.isAir(worldIn, blockpos$mutable)) {
                                BlockState blockstate2 = worldIn.getBlockState(blockpos);
                                boolean isFull = blockstate2.getBlock() == Blocks.LAVA && blockstate2.get(FlowingFluidBlock.LEVEL) == 0;
                                if (blockstate2.getMaterial() == Material.LAVA && isFull && blockstate.isValidPosition(worldIn, blockpos) && worldIn.placedBlockCollides(blockstate, blockpos, ISelectionContext.dummy()) && !net.minecraftforge.event.ForgeEventFactory.onBlockPlace(living, net.minecraftforge.common.util.BlockSnapshot.create(worldIn.getDimensionKey(), worldIn, blockpos), net.minecraft.util.Direction.UP)) {
                                    worldIn.setBlockState(blockpos, blockstate);
                                    worldIn.getPendingBlockTicks().scheduleTick(blockpos, ModBlocks.SOLID_MAGMA.get(), MathHelper.nextInt(playerIn.getRNG(), 60, 120));
                                }
                            }
                        }
                    }

                }
            }
        }
    }
    @Override
    protected boolean canApplyTogether(Enchantment ench) {
        return super.canApplyTogether(ench) && ench != Enchantments.FROST_WALKER && ench != Enchantments.DEPTH_STRIDER;
    }
}

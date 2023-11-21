package github.io.chriso345.firewalker.enchantments;

import github.io.chriso345.firewalker.Reference;
import github.io.chriso345.firewalker.blocks.SolidMagmaBlock;
import github.io.chriso345.firewalker.core.ModBlocks;
import github.io.chriso345.firewalker.core.ModEnchantments;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.logging.Logger;

public class FireWalkerEnchantment extends Enchantment {
    public FireWalkerEnchantment() {
        super(Rarity.RARE, EnchantmentCategory.ARMOR_FEET, new EquipmentSlot[]{EquipmentSlot.FEET});
    }

    public int getMinCost(int p_45017_) {
        return p_45017_ * 10;
    }

    public int getMaxCost(int p_45027_) {
        return this.getMinCost(p_45027_) + 15;
    }

    public boolean isTreasureOnly() {
        return true;
    }

    public int getMaxLevel() {
        return 2;
    }


    @Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class FireWalkerEquipped {
        @SubscribeEvent
        public static void createBridge(TickEvent.PlayerTickEvent event) {
            Player playerIn = event.player;
            LivingEntity living = playerIn;
            Level worldIn = playerIn.level();
            BlockPos pos = BlockPos.containing(playerIn.position());
            if (playerIn.hasItemInSlot(EquipmentSlot.FEET)
                    && EnchantmentHelper.getEnchantmentLevel(ModEnchantments.FIRE_WALKER.get(),
                    playerIn) > 0) {
                int level = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.FIRE_WALKER.get(),
                        playerIn);
                if (playerIn.onGround()) {
                    BlockState blockstate = ModBlocks.SOLID_MAGMA.get().defaultBlockState();
                    int f = Math.min(16, 2 + level);
                    BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

                    for(BlockPos blockpos : BlockPos.betweenClosed(pos.offset(-f, -1, -f), pos.offset(f, -1, f))) {
                        if (blockpos.closerToCenterThan(living.position(), (double)f)) {
                            blockpos$mutableblockpos.set(blockpos.getX(), blockpos.getY() + 1, blockpos.getZ());
                            BlockState blockstate1 = worldIn.getBlockState(blockpos$mutableblockpos);
                            if (blockstate1.isAir()) {
                                BlockState blockstate2 = worldIn.getBlockState(blockpos);
                                if (blockstate2 == SolidMagmaBlock.meltsInto()&& blockstate.canSurvive(worldIn, blockpos) && worldIn.isUnobstructed(blockstate, blockpos, CollisionContext.empty()) && !ForgeEventFactory.onBlockPlace(living, BlockSnapshot.create(worldIn.dimension(), worldIn, blockpos), Direction.UP)) {
                                    worldIn.setBlockAndUpdate(blockpos, blockstate);
                                    worldIn.scheduleTick(blockpos, ModBlocks.SOLID_MAGMA.get(), Mth.nextInt(living.getRandom(), 60, 120));
                                }
                            }
                        }
                    }

                }
            }
        }
    }

    public boolean checkCompatibility(Enchantment p_45024_) {
        return super.checkCompatibility(p_45024_) && p_45024_ != Enchantments.DEPTH_STRIDER && p_45024_ != Enchantments.FROST_WALKER;
    }
}

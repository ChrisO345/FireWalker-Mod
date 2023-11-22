package github.io.chriso345.firewalker.enchantments;

import github.io.chriso345.firewalker.blocks.SolidMagmaBlock;
import github.io.chriso345.firewalker.core.ModBlocks;
import github.io.chriso345.firewalker.core.ModEnchantments;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class FireWalkerEnchantment extends Enchantment {
    public FireWalkerEnchantment() {
        super(Rarity.RARE, EnchantmentTarget.ARMOR_FEET, new EquipmentSlot[] {EquipmentSlot.FEET});
    }

    @Override
    public int getMinPower(int level) {
        return level * 10;
    }

    @Override
    public int getMaxPower(int level) {
        return this.getMinPower(level) + 15;
    }

    @Override
    public boolean isTreasure() {
        return true;
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    // TODO: Fix this PoS code...
    public static void freezeLava(LivingEntity entity, World world, BlockPos blockPos, int level) {
        if (!entity.isOnGround()) {
            return;
        }
        BlockState blockState = ModBlocks.SOLID_MAGMA.getDefaultState();
        int i = Math.min(16, 2 + level);
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (BlockPos blockPos2 : BlockPos.iterate(blockPos.add(-i, -1, -i), blockPos.add(i, -1, i))) {
            BlockState blockState3;
            if (!blockPos2.isWithinDistance(entity.getPos(), (double)i)) continue;
            mutable.set(blockPos2.getX(), blockPos2.getY() + 1, blockPos2.getZ());
            BlockState blockState2 = world.getBlockState(mutable);
            if (!blockState2.isAir() || (blockState3 = world.getBlockState(blockPos2)) != SolidMagmaBlock.getMeltedState() || !blockState.canPlaceAt(world, blockPos2) || !world.canPlace(blockState, blockPos2, ShapeContext.absent())) continue;
            world.setBlockState(blockPos2, blockState);
            world.scheduleBlockTick(blockPos2, ModBlocks.SOLID_MAGMA, MathHelper.nextInt(entity.getRandom(), 60, 120));
        }
    }

    public static void onTickEvent(World world) {
        for (PlayerEntity player : world.getPlayers()) {
            int i = EnchantmentHelper.getEquipmentLevel(ModEnchantments.FIRE_WALKER, player);
            if (i > 0) {
                freezeLava(player, world, player.getBlockPos(), i);
            }
        }
    }

    @Override
    public boolean canAccept(Enchantment other) {
        return super.canAccept(other) && (other != Enchantments.DEPTH_STRIDER && other != Enchantments.FROST_WALKER);
    }
}

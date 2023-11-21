package github.io.chriso345.firewalker.core;

import github.io.chriso345.firewalker.Reference;
import github.io.chriso345.firewalker.enchantments.FireWalkerEnchantment;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEnchantments {
    public static final DeferredRegister<Enchantment> REGISTER = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, Reference.MOD_ID);

    public static final RegistryObject<Enchantment> FIRE_WALKER = REGISTER.register("fire_walker", FireWalkerEnchantment::new);
}
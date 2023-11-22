package github.io.chriso345.firewalker.core;

import github.io.chriso345.firewalker.Reference;
import github.io.chriso345.firewalker.enchantments.FireWalkerEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEnchantments {
    public static Enchantment FIRE_WALKER = new FireWalkerEnchantment();

    public static void register() {
        Registry.register(Registries.ENCHANTMENT, new Identifier(Reference.MOD_ID, "fire_walker"), FIRE_WALKER);
    }
}

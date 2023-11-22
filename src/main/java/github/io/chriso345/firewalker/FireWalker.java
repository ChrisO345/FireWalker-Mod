package github.io.chriso345.firewalker;

import github.io.chriso345.firewalker.core.ModBlocks;
import github.io.chriso345.firewalker.core.ModEnchantments;
import github.io.chriso345.firewalker.enchantments.FireWalkerEnchantment;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.client.util.ClientPlayerTickable;

public class FireWalker implements ModInitializer {
    @Override
    public void onInitialize() {
        ModBlocks.register();
        ModEnchantments.register();
        ServerTickEvents.START_WORLD_TICK.register(FireWalkerEnchantment::onTickEvent);
//        ServerTickEvents.START_WORLD_TICK.register(FireWalkerEnchantment::onTickEvent);
    }
}

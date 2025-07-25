package dev.cxd.raven_delivery.init;


import dev.cxd.raven_delivery.RavensDeliveries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import java.util.function.Function;

public class ModItems {

    public static final Item RAVEN_SPAWN_EGG = registerItem("raven_spawn_egg",
            new SpawnEggItem(ModEntities.RAVEN, 0xFFFFFF, 0xFFFFFF, new Item.Settings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(RavensDeliveries.MOD_ID, name), item);
    }

    public static void initialize() {
        RavensDeliveries.LOGGER.info("Registering Mod Items for " + RavensDeliveries.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(entries -> {
            entries.addAfter(Items.RAVAGER_SPAWN_EGG, RAVEN_SPAWN_EGG);
            entries.addAfter(RAVEN_SPAWN_EGG, Items.SALMON_SPAWN_EGG);
        });
    }

}

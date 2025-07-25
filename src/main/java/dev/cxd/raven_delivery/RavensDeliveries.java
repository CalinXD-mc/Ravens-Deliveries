package dev.cxd.raven_delivery;

import dev.cxd.raven_delivery.entity.RavenEntity;
import dev.cxd.raven_delivery.init.ModEntities;
import dev.cxd.raven_delivery.init.ModItems;
import dev.cxd.raven_delivery.init.ModSounds;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RavensDeliveries implements ModInitializer {
	public static final String MOD_ID = "raven_delivery";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		ModEntities.initialize();
		ModSounds.initialize();
		ModItems.initialize();

		FabricDefaultAttributeRegistry.register(ModEntities.RAVEN, RavenEntity.createAttributes());

		LOGGER.info("Hello Fabric world!");
	}
}
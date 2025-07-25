package dev.cxd.raven_delivery.init;

import dev.cxd.raven_delivery.RavensDeliveries;
import dev.cxd.raven_delivery.entity.RavenEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModEntities {

    private static final RegistryKey<EntityType<?>> RAVEN_KEY =
            RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(RavensDeliveries.MOD_ID, "raven"));

    public static final EntityType<RavenEntity> RAVEN = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(RavensDeliveries.MOD_ID, "raven"),
            EntityType.Builder.create(RavenEntity::new, SpawnGroup.CREATURE)
                    .dimensions(0.6f, 0.6f).build(String.valueOf(RAVEN_KEY)));

    public static void initialize() {
        RavensDeliveries.LOGGER.info("Registering Mobs for " + RavensDeliveries.MOD_ID);
    }

}

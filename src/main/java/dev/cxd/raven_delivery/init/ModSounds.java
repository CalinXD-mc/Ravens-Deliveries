package dev.cxd.raven_delivery.init;

import dev.cxd.raven_delivery.RavensDeliveries;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSounds {

    public static final SoundEvent RAVEN_CAW = registerSoundEvent("entity.raven.caw");

    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = Identifier.of(RavensDeliveries.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void initialize() {
        RavensDeliveries.LOGGER.info("Registering Sounds for " + RavensDeliveries.MOD_ID);
    }

}

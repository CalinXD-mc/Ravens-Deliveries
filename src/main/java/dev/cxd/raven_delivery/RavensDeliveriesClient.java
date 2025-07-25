package dev.cxd.raven_delivery;

import dev.cxd.raven_delivery.client.entity.raven.RavenModel;
import dev.cxd.raven_delivery.client.entity.raven.RavenRenderer;
import dev.cxd.raven_delivery.init.ModEntities;
import net.fabricmc.api.ClientModInitializer;

import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.*;

public class RavensDeliveriesClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityModelLayerRegistry.registerModelLayer(RavenModel.RAVEN, RavenModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.RAVEN, RavenRenderer::new);
    }
}
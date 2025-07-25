package dev.cxd.raven_delivery.client.entity.raven;

import com.google.common.collect.Maps;

import dev.cxd.raven_delivery.RavensDeliveries;
import dev.cxd.raven_delivery.entity.RavenEntity;
import dev.cxd.raven_delivery.entity.RavenVariant;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.RotationAxis;

import java.util.Map;

public class RavenRenderer extends MobEntityRenderer<RavenEntity, RavenModel<RavenEntity>> {
    private static final Map<RavenVariant, Identifier> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(RavenVariant.class), map -> {
                map.put(RavenVariant.DARK,
                        Identifier.of(RavensDeliveries.MOD_ID, "textures/entity/raven_dark.png"));
                map.put(RavenVariant.SEA_GREEN,
                        Identifier.of(RavensDeliveries.MOD_ID, "textures/entity/raven_sea_green.png"));
                map.put(RavenVariant.ALBINO,
                        Identifier.of(RavensDeliveries.MOD_ID, "textures/entity/raven_albino.png"));
            });

    public RavenRenderer(EntityRendererFactory.Context context) {
        super(context, new RavenModel<>(context.getPart(RavenModel.RAVEN)), 0.35f);
    }

    @Override
    public Identifier getTexture(RavenEntity entity) {
        return LOCATION_BY_VARIANT.get(entity.getVariant());
    }

    @Override
    public void render(RavenEntity livingEntity, float f, float g, MatrixStack matrixStack,
                       VertexConsumerProvider vertexConsumerProvider, int i) {

        if (livingEntity.isBaby()) {
            matrixStack.scale(0.5f, 0.5f, 0.5f);
        } else {
            matrixStack.scale(1f, 1f, 1f);
        }

        super.render(livingEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}

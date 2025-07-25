package dev.cxd.raven_delivery.client.entity.raven;

import dev.cxd.raven_delivery.RavensDeliveries;
import dev.cxd.raven_delivery.entity.RavenEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class RavenModel<T extends RavenEntity> extends SinglePartEntityModel<T> {
    public static final EntityModelLayer RAVEN = new EntityModelLayer(Identifier.of(RavensDeliveries.MOD_ID, "raven"), "main");

    private final Animation fastFlyingAnimation;
    private final Animation idlingAnimation;
    private final Animation sitIdlingAnimation;

    private final ModelPart root;

    public RavenModel(ModelPart root) {
        super();

        this.root = root.getChild("root");

        ModelPart root1 = root.getChild("root");
        ModelPart body = root1.getChild("body");
        ModelPart rightwing = body.getChild("rightwing");
        ModelPart leftwing = body.getChild("leftwing");
        ModelPart legs = body.getChild("legs");
        ModelPart rightleg = legs.getChild("rightleg");
        ModelPart leftleg = legs.getChild("leftleg");

        this.fastFlyingAnimation = RavenAnimations.FASTFLY;
        this.idlingAnimation = RavenAnimations.IDLE;
        this.sitIdlingAnimation = RavenAnimations.SITIDLE;
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 23.0F, 0.0F));

        ModelPartData body = root.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-2.5F, -5.0F, -5.0F, 5.0F, 5.0F, 8.0F, new Dilation(0.0F))
                .uv(8, 13).cuboid(-1.5F, -3.0F, -7.0F, 3.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -2.0F, 1.0F));

        ModelPartData rightwing = body.addChild("rightwing", ModelPartBuilder.create().uv(0, 21).cuboid(-1.0F, -2.0F, 0.0F, 1.0F, 4.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.5F, -2.5F, -2.0F));

        ModelPartData leftwing = body.addChild("leftwing", ModelPartBuilder.create().uv(16, 13).cuboid(0.0F, -2.0F, 0.0F, 1.0F, 4.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(2.5F, -2.5F, -2.0F));

        ModelPartData legs = body.addChild("legs", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData rightleg = legs.addChild("rightleg", ModelPartBuilder.create().uv(0, 0).cuboid(-0.5F, 0.0F, -1.0F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.5F, 0.0F, 0.0F));

        ModelPartData leftleg = legs.addChild("leftleg", ModelPartBuilder.create().uv(3, 3).cuboid(-0.5F, 0.0F, -1.0F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(1.5F, 0.0F, 0.0F));
        return TexturedModelData.of(modelData, 32, 32);
    }
//    @Override
//    public void setAngles(RavenRenderState state) {
//        super.setAngles(state);
//
//        this.sitIdlingAnimation.apply(state.sittingAnimationState, state.age, 1f);
//
//        this.fastFlyingAnimation.applyWalking(state.limbSwingAnimationProgress, state.limbSwingAmplitude, 2f, 2.5f);
//        this.idlingAnimation.apply(state.idleAnimationState, state.age, 1f);
//    }


    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
        root.render(matrices, vertices, light, overlay, color);
    }

    @Override
    public ModelPart getPart() {
        return root;
    }

    @Override
    public void setAngles(RavenEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);

        this.updateAnimation(entity.sittingAnimationState, RavenAnimations.SITIDLE, 1f);

        this.animateMovement(RavenAnimations.FASTFLY, limbSwing, limbSwingAmount, 2f, 2.5f);
        this.updateAnimation(entity.idleAnimationState, RavenAnimations.IDLE, ageInTicks, 1f);
    }
}

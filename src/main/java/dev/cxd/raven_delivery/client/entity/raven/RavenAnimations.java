package dev.cxd.raven_delivery.client.entity.raven;

import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;

public class RavenAnimations {

    public static final Animation IDLE = Animation.Builder.create(6f).looping()
            .addBoneAnimation("body",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, -2f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("body",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(3f, AnimationHelper.createRotationalVector(-5f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(6f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("rightwing",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(1.5f, AnimationHelper.createRotationalVector(0f, -7.5f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(4.5f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(5.208343f, AnimationHelper.createRotationalVector(-2.5f, -3.02f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(6f, AnimationHelper.createRotationalVector(0f, -0.52f, -0.11f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("leftwing",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(1.5f, AnimationHelper.createRotationalVector(0f, 7.5f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(4.5f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(5.208343f, AnimationHelper.createRotationalVector(-2.5f, 3.02f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(6f, AnimationHelper.createRotationalVector(0f, 0.52f, 0.11f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("rightleg",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 2f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("leftleg",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 2f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("root",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(5f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR))).build();
    public static final Animation SITIDLE = Animation.Builder.create(2f).looping()
            .addBoneAnimation("legs",
                    new Transformation(Transformation.Targets.SCALE,
                            new Keyframe(0f, AnimationHelper.createScalingVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("body",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, -1f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("body",
                    new Transformation(Transformation.Targets.SCALE,
                            new Keyframe(0f, AnimationHelper.createScalingVector(1f, 1f, 1f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(1f, AnimationHelper.createScalingVector(1f, 1f, 1f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(2f, AnimationHelper.createScalingVector(1f, 1f, 1f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("rightwing",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(1f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(2f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("rightwing",
                    new Transformation(Transformation.Targets.SCALE,
                            new Keyframe(0f, AnimationHelper.createScalingVector(1f, 1f, 1f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(1f, AnimationHelper.createScalingVector(1f, 1f, 1f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(2f, AnimationHelper.createScalingVector(1f, 1f, 1f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("leftwing",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(1f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(2f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("leftwing",
                    new Transformation(Transformation.Targets.SCALE,
                            new Keyframe(0f, AnimationHelper.createScalingVector(1f, 1f, 1f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(1f, AnimationHelper.createScalingVector(1f, 1f, 1f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(2f, AnimationHelper.createScalingVector(1f, 1f, 1f),
                                    Transformation.Interpolations.LINEAR))).build();
    public static final Animation FASTFLY = Animation.Builder.create(0.5f).looping()
            .addBoneAnimation("legs",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.25f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.5f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("body",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.25f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.5f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("body",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(4f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("rightwing",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 2f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("rightwing",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.25f, AnimationHelper.createRotationalVector(0f, -65f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.5f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("leftwing",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 2f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("leftwing",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.25f, AnimationHelper.createRotationalVector(0f, 65f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.5f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("rightleg",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 2.7f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("leftleg",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 2.7f, 0f),
                                    Transformation.Interpolations.LINEAR))).build();
    public static final Animation FLY = Animation.Builder.create(1.5f).looping()
            .addBoneAnimation("legs",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.75f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(1.5f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("body",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.75f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("body",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(4f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("rightwing",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 2f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("rightwing",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.75f, AnimationHelper.createRotationalVector(0f, -65f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(1.3333f, AnimationHelper.createRotationalVector(0f, -16.94f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(1.5f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("leftwing",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 2f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("leftwing",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.75f, AnimationHelper.createRotationalVector(0f, 65f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(1.3333f, AnimationHelper.createRotationalVector(0f, 16.94f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(1.5f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("rightleg",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 2.7f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("leftleg",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 2.7f, 0f),
                                    Transformation.Interpolations.LINEAR))).build();
    public static final Animation DIVE = Animation.Builder.create(0.5f)
            .addBoneAnimation("body",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(60f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("rightwing",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.5f, AnimationHelper.createRotationalVector(0f, -5f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("leftwing",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.5f, AnimationHelper.createRotationalVector(0f, 5f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("rightleg",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.5f, AnimationHelper.createTranslationalVector(0f, 2.9f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("leftleg",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.5f, AnimationHelper.createTranslationalVector(0f, 2.9f, 0f),
                                    Transformation.Interpolations.LINEAR))).build();
}

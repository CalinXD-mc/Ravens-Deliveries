package dev.cxd.raven_delivery.entity;

import dev.cxd.raven_delivery.entity.goals.FlyTreeGoal;
import dev.cxd.raven_delivery.entity.goals.RavenDeliverBundleGoal;
import dev.cxd.raven_delivery.init.ModEntities;
import dev.cxd.raven_delivery.init.ModSounds;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static com.mojang.datafixers.TypeRewriteRule.orElse;

public class RavenEntity extends TameableEntity implements Angerable {
    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;
    public AnimationState sittingAnimationState = new AnimationState();

    private static final TrackedData<Boolean> SITTING =
            DataTracker.registerData(RavenEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    private static final TrackedData<Optional<UUID>> RECEIVER_UUID =
            DataTracker.registerData(RavenEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);
    public static final TrackedData<Boolean> GOING_TO_RECEIVER =
            DataTracker.registerData(RavenEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private ItemStack carriedBundle = ItemStack.EMPTY;

    private static final TrackedData<Integer> DATA_ID_TYPE_VARIANT =
            DataTracker.registerData(RavenEntity.class, TrackedDataHandlerRegistry.INTEGER);

    public RavenEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);

        this.moveControl = new FlightMoveControl(this, 10, false);
        this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, -1.0F);
        this.setPathfindingPenalty(PathNodeType.DAMAGE_FIRE, -1.0F);
        this.setPathfindingPenalty(PathNodeType.COCOA, -1.0F);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new AnimalMateGoal(this, 1.0F));
        this.goalSelector.add(1, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(2, new SitGoal(this));
        this.goalSelector.add(0, new RavenDeliverBundleGoal<>(this, 1.0D, 6.0F, 128.0F, false));
        this.goalSelector.add(4, new TemptGoal(this, 1.25D, Ingredient.ofItems(Items.WHEAT_SEEDS), false));
        this.goalSelector.add(4, new FollowOwnerGoal(this, 1.0F, 5.0F, 1.0F));
        this.goalSelector.add(4, new FlyTreeGoal(this, 1.0F));
        this.goalSelector.add(4, new PounceAtTargetGoal(this, 0.4F));
        this.goalSelector.add(5, new MeleeAttackGoal(this, 1.0F, true));
        this.goalSelector.add(6, new FollowMobGoal(this, 1.0F, 3.0F, 7.0F));

//        this.targetSelector.add(1, new TrackOwnerAttackerGoal(this));
//        this.targetSelector.add(2, new AttackWithOwnerGoal(this));
//        this.targetSelector.add(3, (new RevengeGoal(this, new Class[0])).setGroupRevenge(new Class[0]));
//        this.targetSelector.add(4, new ActiveTargetGoal<>(this, PlayerEntity.class, 10, true, false, this::shouldAngerAt));
//        this.targetSelector.add(6, new UntamedActiveTargetGoal<>(this, TurtleEntity.class, false, TurtleEntity.BABY_TURTLE_ON_LAND_FILTER));
//        this.targetSelector.add(7, new ActiveTargetGoal<>(this, AbstractSkeletonEntity.class, false));
//        this.targetSelector.add(8, new UniversalAngerGoal<>(this, true));
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return TameableEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2)
                .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.6F)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2F)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32);
    }

    private void setupAnimationStates() {
        if (this.isSitting()) {
            this.sittingAnimationState.startIfNotRunning(this.age);
        } else {
            this.sittingAnimationState.stop();

            if (this.idleAnimationTimeout <= 0) {
                this.idleAnimationTimeout = 120;
                this.idleAnimationState.start(this.age);
            } else {
                --this.idleAnimationTimeout;
            }
        }
    }



    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);

        // Taming logic
        if (!this.isTamed() && stack.isIn(ItemTags.MEAT)) {
            this.eat(player, hand, stack);

            if (!this.getWorld().isClient) {
                if (this.random.nextInt(5) == 0) {
                    this.setOwner(player);
                    this.setSitting(true);
                    this.getWorld().sendEntityStatus(this, (byte) 7);
                } else {
                    this.getWorld().sendEntityStatus(this, (byte) 6);
                }
            }
            return ActionResult.SUCCESS;
        }

        // Bundle delivery logic
        if (stack.isOf(Items.BUNDLE) && !stack.getName().getString().isEmpty()) {
            if (this.isTamed() && this.isOwner(player)) {
                String targetName = stack.getName().getString();
                MinecraftServer server = this.getServer();

                if (server != null) {
                    ServerPlayerEntity target = server.getPlayerManager().getPlayerList().stream()
                            .filter(p -> p.getName().getString().equalsIgnoreCase(targetName))
                            .findFirst()
                            .orElse(null);

                    if (target != null) {
                        this.setReceiverUuid(target.getUuid());
                        this.setCarriedBundle(stack.copy());
                        stack.decrement(1);
                        this.setSitting(false);
                        return ActionResult.SUCCESS;
                    } else {
                        player.sendMessage(Text.literal("No player named \"" + targetName + "\" is online."), true);
                        return ActionResult.FAIL;
                    }
                }
            }
        }

        // Sit/stand toggle
        if (this.isTamed() && this.isOwner(player)) {
            if (!this.getWorld().isClient) {
                this.setSitting(!this.isSitting());
                this.navigation.stop();
                this.setTarget(null);
            }
            return ActionResult.SUCCESS;
        }

        return super.interactMob(player, hand);
    }

    public void setCarriedBundle(ItemStack stack) {
        this.carriedBundle = stack;
    }

    public ItemStack getCarriedBundle() {
        return this.carriedBundle;
    }

    public boolean hasCarriedBundle() {
        return !this.carriedBundle.isEmpty();
    }


//    public void setReceiver(@Nullable LivingEntity target) {
//        if (target != null) {
//            try {
//                UUID ref = new UUID;
//                this.receiverRef = Optional.of(ref);
//                this.dataTracker.set(RECEIVER_UUID, this.receiverRef);
//            } catch (Exception e) {
//                this.receiverRef = Optional.empty();
//                this.dataTracker.set(RECEIVER_UUID, Optional.empty());
//            }
//        } else {
//            this.receiverRef = Optional.empty();
//            this.dataTracker.set(RECEIVER_UUID, Optional.empty());
//        }
//    }


    public void setReceiverUuid(@Nullable UUID uuid) {
        this.dataTracker.set(RECEIVER_UUID, Optional.ofNullable(uuid));
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        BirdNavigation birdNavigation = new BirdNavigation(this, world);
//        birdNavigation.setCanOpenDoors(false);
        birdNavigation.setCanSwim(true);
        return birdNavigation;
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.getWorld().isClient()) {
            this.setupAnimationStates();
        }
    }

    @Override
    public void tickMovement() {
        flapWings();
        super.tickMovement();
    }

    private void flapWings() {
        Vec3d vec3d = this.getVelocity();
        if (!this.isOnGround() && vec3d.y < (double)0.0F) {
            this.setVelocity(vec3d.multiply((double)1.0F, 0.6, (double)1.0F));
        }
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isOf(Items.WHEAT_SEEDS);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        RavenEntity baby = ModEntities.RAVEN.create(world);
        assert baby != null;

        if (entity instanceof RavenEntity other) {
            RavenVariant[] parentVariants = new RavenVariant[] {
                    this.getVariant(),
                    other.getVariant()
            };
            RavenVariant variant = Util.getRandom(Arrays.asList(parentVariants), this.random);
            baby.setVariant(variant);

            if (this.isTamed() && other.isTamed()) {
                baby.setTamed(true, true);
                baby.setOwner((PlayerEntity) this.getOwner());
            }
        }

        return baby;
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);

        builder.add(RECEIVER_UUID, Optional.empty());
        builder.add(GOING_TO_RECEIVER, false);

        builder.add(DATA_ID_TYPE_VARIANT, 0);
        builder.add(SITTING, false);
    }

    @Override
    public boolean isSitting() {
        return this.dataTracker.get(SITTING);
    }

    @Override
    public void setSitting(boolean sitting) {
        this.dataTracker.set(SITTING, sitting);
    }

    public UUID getReceiverUuid() {
        return this.dataTracker.get(RECEIVER_UUID).orElse(null);
    }

    public RavenVariant getVariant() {
        return RavenVariant.byId(this.getTypeVariant() & 255);
    }

    private int getTypeVariant() {
        return this.dataTracker.get(DATA_ID_TYPE_VARIANT);
    }

    private void setVariant(RavenVariant variant) {
        this.dataTracker.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
    }

//    @Override
//    public void writeData(WriteView view) {
//        super.writeData(view);
//        view.putBoolean("Sitting", this.isSitting());
//        view.putInt("Variant", this.getTypeVariant());
//    }
//
//    @Override
//    public void readData(ReadView view) {
//        super.readData(view);
//        this.setSitting(view.getBoolean("Sitting", false));
//        this.dataTracker.set(DATA_ID_TYPE_VARIANT, view.getInt("Variant", 0));
//    }


//    @Override
//    public NbtCompound writeNbt(NbtCompound nbt) {
//        nbt.putBoolean("Sitting", this.isSitting());
//        nbt.putInt("Variant", this.getTypeVariant());
//        return super.writeNbt(nbt);
//    }
//
//    @Override
//    public void readNbt(NbtCompound nbt) {
//        this.setSitting(nbt.getBoolean("Sitting"));
//        this.dataTracker(DATA_ID_TYPE_VARIANT, nbt.getInt("Variant"));
//        super.readNbt(nbt);
//    }


    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("Sitting", this.isSitting());
        nbt.putInt("Variant", this.getTypeVariant());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setSitting(nbt.getBoolean("Sitting"));
        this.dataTracker.set(DATA_ID_TYPE_VARIANT, nbt.getInt("Variant"));
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason,
                                 @Nullable EntityData entityData) {
        RavenVariant variant = Util.getRandom(RavenVariant.values(), this.random);
        setVariant(variant);
        return super.initialize(world, difficulty, spawnReason, entityData);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.RAVEN_CAW;
    }

    @Override
    public int getAngerTime() {
        return 0;
    }

    @Override
    public void setAngerTime(int angerTime) {

    }

    @Override
    public @Nullable UUID getAngryAt() {
        return null;
    }

    @Override
    public void setAngryAt(@Nullable UUID angryAt) {

    }

    @Override
    public void chooseRandomAngerTime() {

    }
}
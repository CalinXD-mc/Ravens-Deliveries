package dev.cxd.raven_delivery.entity.goals;

import dev.cxd.raven_delivery.entity.RavenEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.EnumSet;
import java.util.Objects;
import java.util.UUID;

public class RavenDeliverBundleGoal<T extends TameableEntity> extends Goal {
    private final T tameable; // The raven (or other tameable mob)
    private LivingEntity owner; // The entity that owns the raven
    private final World world;
    private final double speed;
    private final EntityNavigation navigation;
    private int updateCountdownTicks;
    private final float maxDistance;
    private final float minDistance;
    private float oldWaterPathfindingPenalty;
    private final boolean leavesAllowed;
    private LivingEntity receiver; // The delivery target

    public RavenDeliverBundleGoal(T tameable, double speed, float minDistance, float maxDistance, boolean leavesAllowed) {
        this.tameable = tameable;
        this.world = tameable.getWorld(); // field_6002
        this.speed = speed;
        this.navigation = tameable.getNavigation(); // method_5942
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
        this.leavesAllowed = leavesAllowed;

        // Sets goal control flags: MOVE and LOOK
        this.setControls(EnumSet.of(Control.MOVE, Control.LOOK)); // method_6265, class_4134.field_18405/06

        // Validate that the tameable has a valid navigation type and is a RavenEntity
        if (!(tameable.getNavigation() instanceof BirdNavigation)
                && !(tameable.getNavigation() instanceof EntityNavigation)
                || !(tameable instanceof RavenEntity)) {
            throw new IllegalArgumentException("Unsupported mob type for DeliverBundleGoal");
        }
    }

    @Override
    public boolean canStart() {
        RavenEntity raven = (RavenEntity) this.tameable;

        UUID receiverUuid = raven.getReceiverUuid();
        if (receiverUuid == null) {
            return false;
        }

        Entity potentialReceiver = ((ServerWorld) this.world).getEntity(receiverUuid);
        if (!(potentialReceiver instanceof LivingEntity)) {
            return false;
        }

        this.receiver = (LivingEntity) potentialReceiver;

        LivingEntity ownerCandidate = this.tameable.getOwner();
        if (ownerCandidate == null || !ownerCandidate.isAlive()) {
            return false;
        }

        if (this.tameable.squaredDistanceTo(this.receiver) < (this.minDistance * this.minDistance)
                && raven.hasCarriedBundle()) {


            ItemStack bundle = raven.getCarriedBundle().copy();
            ItemEntity drop = new ItemEntity(this.world,
                    this.receiver.getX(), this.receiver.getY() + 1.0D, this.receiver.getZ(), bundle);
            drop.setVelocity(0, 0.25D, 0);
            this.world.spawnEntity(drop);

            raven.setCarriedBundle(ItemStack.EMPTY);
            raven.setReceiverUuid(null);
            raven.getDataTracker().set(RavenEntity.GOING_TO_RECEIVER, false);
            return false; // Don’t start the goal — we’re done!
        }

        this.owner = ownerCandidate;
        return true;
    }

    @Override
    public boolean shouldContinue() {
        if (this.navigation.isIdle()) {
            return false;
        }

        return this.tameable.squaredDistanceTo(this.receiver) > (this.maxDistance * this.maxDistance);
    }

    @Override
    public void start() {
        this.updateCountdownTicks = 0;

        this.oldWaterPathfindingPenalty = this.tameable.getPathfindingPenalty(PathNodeType.WATER);

        this.tameable.setPathfindingPenalty(PathNodeType.WATER, 0.0F);

        this.tameable.getDataTracker().set(RavenEntity.GOING_TO_RECEIVER, true);
    }

    @Override
    public void stop() {
        this.receiver = null;
        this.navigation.stop(); // method_6340()
        this.tameable.setPathfindingPenalty(PathNodeType.WATER, this.oldWaterPathfindingPenalty); // method_5941()
    }

    @Override
    public void tick() {
        // 🧭 Look at the receiver
        this.tameable.getLookControl().lookAt(this.receiver, 10.0F, this.tameable.getMaxLookPitchChange());

        // 📦 Deliver the bundle if close enough
        if (this.tameable.squaredDistanceTo(this.receiver) < 4.0D
                && ((RavenEntity) this.tameable).hasCarriedBundle()) {

            ItemStack bundle = ((RavenEntity) this.tameable).getCarriedBundle().copy();
            ItemEntity drop = new ItemEntity(
                    this.world,
                    this.receiver.getX(),
                    this.receiver.getY() + 1.0D,
                    this.receiver.getZ(),
                    bundle
            );
            drop.setVelocity(0, 0.25D, 0);
            this.world.spawnEntity(drop);

            ((RavenEntity) this.tameable).setCarriedBundle(ItemStack.EMPTY);
            ((RavenEntity) this.tameable).setReceiverUuid(null);
            this.tameable.getDataTracker().set(RavenEntity.GOING_TO_RECEIVER, false);

            this.navigation.stop();
            this.stop();
            return;
        }

        // ⏱️ Countdown to next path update
        if (--this.updateCountdownTicks <= 0) {
            this.updateCountdownTicks = 10;

            if (this.tameable.squaredDistanceTo(this.receiver) >= 10000.0D) {
                this.tryTeleport();
            } else {
                this.navigation.startMovingTo(this.receiver, this.speed);
            }
        }
    }

    private void tryTeleport() {
        BlockPos targetPos = this.receiver.getBlockPos(); // method_24515()
        //((RavenEntity) this.tameable).spawnFeatherParticles(10); // cosmetic effect

        for (int i = 0; i < 10; ++i) {
            int dx = this.getRandomInt(-3, 3);
            int dy = this.getRandomInt(-1, 1);
            int dz = this.getRandomInt(-3, 3);

            boolean success = this.tryTeleportTo(
                    targetPos.getX() + dx, // method_10263()
                    targetPos.getY() + dy, // method_10264()
                    targetPos.getZ() + dz  // method_10260()
            );

            if (success) {
                return;
            }
        }
    }

    private boolean tryTeleportTo(int x, int y, int z) {
        // Prevent teleporting too close to the receiver (within 2 blocks on X/Z)
        if (Math.abs(x - this.receiver.getX()) < 2.0D && Math.abs(z - this.receiver.getZ()) < 2.0D) {
            return false;
        }

        BlockPos targetPos = new BlockPos(x, y, z);

        // Check if the target position is valid for teleportation
        if (!this.canTeleportTo(targetPos)) {
            return false;
        }

        // Teleport the tameable to the center of the block
        this.tameable.refreshPositionAndAngles(
                x + 0.5D, y, z + 0.5D,
                this.tameable.getYaw(), this.tameable.getPitch()
        );

        this.navigation.stop();
        return true;
    }

    private boolean canTeleportTo(BlockPos pos) {
//        PathNodeType pathNodeType = NavigationType.AIR.getPathNodeType(this.world, pos.down());
//        if (type != PathNodeType.OPEN) {
//            return false;
//        }

        BlockState blockState = this.world.getBlockState(pos.down()); // method_8320(), method_10074()
        if (!this.leavesAllowed && blockState.getBlock() instanceof LeavesBlock) { // method_26204(), class_2397
            return false;
        }

        BlockPos offset = pos.subtract(this.tameable.getBlockPos());
        return this.world.isSpaceEmpty(this.tameable, this.tameable.getBoundingBox().offset(offset));
    }

    private int getRandomInt(int min, int max) {
        return this.tameable.getRandom().nextInt(max - min + 1) + min;
    }

}

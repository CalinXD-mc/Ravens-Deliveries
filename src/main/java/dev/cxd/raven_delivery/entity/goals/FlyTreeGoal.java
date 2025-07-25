package dev.cxd.raven_delivery.entity.goals;

import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.ai.FuzzyTargeting;
import net.minecraft.entity.ai.goal.FlyGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

public class FlyTreeGoal extends FlyGoal  {
    public FlyTreeGoal(PathAwareEntity pathAwareEntity, double d) {
        super(pathAwareEntity, d);
    }

    @Nullable
    protected Vec3d getWanderTarget() {
        Vec3d vec3d = null;
        if (this.mob.isTouchingWater()) {
            vec3d = FuzzyTargeting.find(this.mob, 15, 15);
        }

        if (this.mob.getRandom().nextFloat() >= this.probability) {
            vec3d = this.locateTree();
        }

        return vec3d == null ? super.getWanderTarget() : vec3d;
    }

    @Nullable
    private Vec3d locateTree() {
        BlockPos blockPos = this.mob.getBlockPos();
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        BlockPos.Mutable mutable2 = new BlockPos.Mutable();

        for(BlockPos blockPos2 : BlockPos.iterate(MathHelper.floor(this.mob.getX() - (double)3.0F), MathHelper.floor(this.mob.getY() - (double)6.0F), MathHelper.floor(this.mob.getZ() - (double)3.0F), MathHelper.floor(this.mob.getX() + (double)3.0F), MathHelper.floor(this.mob.getY() + (double)6.0F), MathHelper.floor(this.mob.getZ() + (double)3.0F))) {
            if (!blockPos.equals(blockPos2)) {
                BlockState blockState = this.mob.getWorld().getBlockState(mutable2.set(blockPos2, Direction.DOWN));
                boolean bl = blockState.getBlock() instanceof LeavesBlock || blockState.isIn(BlockTags.LOGS);
                if (bl && this.mob.getWorld().isAir(blockPos2) && this.mob.getWorld().isAir(mutable.set(blockPos2, Direction.UP))) {
                    return Vec3d.ofBottomCenter(blockPos2);
                }
            }
        }

        return null;
    }
}

package moe.nyancat.foolsbarrel.mixin;

import moe.nyancat.foolsbarrel.BarrelUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LeapAtTargetGoal.class)
public abstract class LeapAtTargetGoalMixin {
    @Shadow @Final private Mob mob;

    @Inject(method = "canUse", at = @At("HEAD"), cancellable = true)
    private void foolsbarrel$preventLeapCanUse(CallbackInfoReturnable<Boolean> cir) {
        if (BarrelUtil.isWearingBarrel(this.mob)) { cir.setReturnValue(false); return; }
        LivingEntity target = this.mob.getTarget();
        if (target != null && BarrelUtil.isHiddenInBarrel(target)) { cir.setReturnValue(false); }
    }

    @Inject(method = "canContinueToUse", at = @At("HEAD"), cancellable = true)
    private void foolsbarrel$preventLeapContinue(CallbackInfoReturnable<Boolean> cir) {
        if (BarrelUtil.isWearingBarrel(this.mob)) { cir.setReturnValue(false); return; }
        LivingEntity target = this.mob.getTarget();
        if (target != null && BarrelUtil.isHiddenInBarrel(target)) { cir.setReturnValue(false); }
    }
}

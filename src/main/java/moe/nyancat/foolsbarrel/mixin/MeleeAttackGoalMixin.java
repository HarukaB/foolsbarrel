package moe.nyancat.foolsbarrel.mixin;

import moe.nyancat.foolsbarrel.BarrelUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MeleeAttackGoal.class)
public abstract class MeleeAttackGoalMixin {
    @Shadow @Final protected PathfinderMob mob;

    @Inject(method = "canUse", at = @At("HEAD"), cancellable = true)
    private void foolsbarrel$preventAttackCanUse(CallbackInfoReturnable<Boolean> cir) {
        if (BarrelUtil.isWearingBarrel(this.mob)) { cir.setReturnValue(false); return; }
        LivingEntity target = this.mob.getTarget();
        if (target != null && BarrelUtil.isHiddenInBarrel(target)) { cir.setReturnValue(false); }
    }

    @Inject(method = "canContinueToUse", at = @At("HEAD"), cancellable = true)
    private void foolsbarrel$preventAttackContinue(CallbackInfoReturnable<Boolean> cir) {
        if (BarrelUtil.isWearingBarrel(this.mob)) { cir.setReturnValue(false); return; }
        LivingEntity target = this.mob.getTarget();
        if (target != null && BarrelUtil.isHiddenInBarrel(target)) { cir.setReturnValue(false); }
    }
}

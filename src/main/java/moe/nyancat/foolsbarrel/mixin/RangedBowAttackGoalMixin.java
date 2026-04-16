package moe.nyancat.foolsbarrel.mixin;

import moe.nyancat.foolsbarrel.BarrelUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.ai.goal.RangedBowAttackGoal;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RangedBowAttackGoal.class)
public abstract class RangedBowAttackGoalMixin {
    @Shadow @Final private Monster mob;

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

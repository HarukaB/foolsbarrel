package moe.nyancat.foolsbarrel.mixin;

import moe.nyancat.foolsbarrel.BarrelUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.SwellGoal;
import net.minecraft.world.entity.monster.Creeper;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SwellGoal.class)
public abstract class SwellGoalMixin {
    @Shadow @Final private Creeper creeper;
    @Shadow @Nullable private LivingEntity target;

    @Inject(method = "canUse", at = @At("HEAD"), cancellable = true)
    private void foolsbarrel$preventSwell(CallbackInfoReturnable<Boolean> cir) {
        LivingEntity target = this.creeper.getTarget();
        if (target != null && BarrelUtil.isHiddenInBarrel(target)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void foolsbarrel$stopSwell(CallbackInfo ci) {
        if (this.target != null && BarrelUtil.isHiddenInBarrel(this.target)) {
            this.creeper.setTarget(null);
            this.creeper.setSwellDir(-1);
            ci.cancel();
        }
    }
}

package moe.nyancat.foolsbarrel.mixin;

import moe.nyancat.foolsbarrel.BarrelUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Mob.class)
public abstract class MobMixin {
    @Shadow @Nullable
    public abstract LivingEntity getTarget();

    @Shadow
    public abstract void setTarget(@Nullable LivingEntity target);

    @Inject(method = "serverAiStep", at = @At("HEAD"))
    private void foolsbarrel$clearHiddenTarget(CallbackInfo ci) {
        LivingEntity target = this.getTarget();
        if (target != null && BarrelUtil.isHiddenInBarrel(target)) {
            this.setTarget(null);
        }
    }

    @Inject(method = "doHurtTarget", at = @At("HEAD"), cancellable = true)
    private void foolsbarrel$preventBarrelMobAttack(ServerLevel level, Entity target, CallbackInfoReturnable<Boolean> cir) {
        if (BarrelUtil.isWearingBarrel((Mob) (Object) this)) {
            cir.setReturnValue(false);
        }
    }
}

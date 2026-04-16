package moe.nyancat.foolsbarrel.client.mixin;

import moe.nyancat.foolsbarrel.BarrelUtil;
import net.minecraft.client.Camera;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public abstract class CameraMixin {
    @Shadow private float eyeHeight;
    @Shadow private float eyeHeightOld;
    @Shadow private Entity entity;
    @Shadow private boolean detached;

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void foolsbarrel$adjustEyeHeight(CallbackInfo ci) {
        if (this.entity != null
                && !this.detached
                && this.entity.isCrouching()
                && this.entity instanceof LivingEntity le
                && BarrelUtil.isWearingBarrel(le)) {
            this.eyeHeightOld = this.eyeHeight;
            this.eyeHeight += (0.875F - this.eyeHeight) * 0.5F;
            ci.cancel();
        }
    }
}

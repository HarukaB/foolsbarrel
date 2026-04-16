package moe.nyancat.foolsbarrel.client.mixin;

import moe.nyancat.foolsbarrel.BarrelUtil;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin {

    @Inject(method = "aiStep", at = @At("TAIL"))
    private void foolsbarrel$snapPosition(CallbackInfo ci) {
        LocalPlayer self = (LocalPlayer) (Object) this;
        if (BarrelUtil.isWearingBarrel(self) && self.isCrouching()) {
            self.setPos(
                    Math.floor(self.getX()) + 0.5,
                    self.getY(),
                    Math.floor(self.getZ()) + 0.5
            );
        }
    }
}

package moe.nyancat.foolsbarrel.mixin;

import moe.nyancat.foolsbarrel.BarrelUtil;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin {
    @Unique
    private Pose foolsbarrel$oldPose;

    @Inject(method = "updatePlayerPose", at = @At("HEAD"))
    private void foolsbarrel$captureOldPose(CallbackInfo ci) {
        this.foolsbarrel$oldPose = ((Player) (Object) this).getPose();
    }

    @Inject(method = "aiStep", at = @At("TAIL"))
    private void foolsbarrel$snapPosition(CallbackInfo ci) {
        Player self = (Player) (Object) this;
        if (BarrelUtil.isWearingBarrel(self) && self.isCrouching()) {
            self.setPos(
                    Math.floor(self.getX()) + 0.5,
                    self.getY(),
                    Math.floor(self.getZ()) + 0.5
            );
        }
    }

    @Inject(method = "updatePlayerPose", at = @At("TAIL"))
    private void foolsbarrel$playBarrelSounds(CallbackInfo ci) {
        Player self = (Player) (Object) this;
        Pose newPose = self.getPose();
        if (this.foolsbarrel$oldPose != newPose && BarrelUtil.isWearingBarrel(self)) {
            if (this.foolsbarrel$oldPose == Pose.CROUCHING) {
                // Leaving crouch = opening the barrel lid
                self.level().playSound(null, self, SoundEvents.BARREL_OPEN, SoundSource.BLOCKS, 1.0F, 1.0F);
            } else if (newPose == Pose.CROUCHING) {
                // Entering crouch = closing the barrel lid
                self.level().playSound(null, self, SoundEvents.BARREL_CLOSE, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
        }
    }
}

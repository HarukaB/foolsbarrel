package moe.nyancat.foolsbarrel.mixin;

import moe.nyancat.foolsbarrel.BarrelUtil;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LookAtPlayerGoal.class)
public abstract class LookAtPlayerGoalMixin {

    @Shadow
    protected Mob mob;

    @Inject(method = "canUse", at = @At("HEAD"), cancellable = true)
    private void foolsbarrel$preventLook(CallbackInfoReturnable<Boolean> cir) {
        if (this.mob.getTarget() instanceof Player player
                && BarrelUtil.isHiddenInBarrel(player)) {
            cir.setReturnValue(false);
        }
    }
}

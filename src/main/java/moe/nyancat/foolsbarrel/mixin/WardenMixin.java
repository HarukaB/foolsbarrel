package moe.nyancat.foolsbarrel.mixin;

import moe.nyancat.foolsbarrel.BarrelUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Warden.class)
public abstract class WardenMixin {

    @Inject(
            method = "increaseAngerAt(Lnet/minecraft/world/entity/Entity;IZ)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private void foolsbarrel$blockAngerIncrease(Entity entity, int amount, boolean playSound, CallbackInfo ci) {
        if (entity instanceof Player player && BarrelUtil.isHiddenInBarrel(player)) {
            ci.cancel(); //直接阻止所有 anger 增长
        }
    }

    @Inject(method = "canTargetEntity", at = @At("HEAD"), cancellable = true)
    private void foolsbarrel$ignoreBarrelPlayer(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (entity instanceof Player player && BarrelUtil.isHiddenInBarrel(player)) {

            Warden self = (Warden)(Object)this;

            int anger = self.getAngerManagement().getActiveAnger(entity);

            if (anger < 30) {
                cir.setReturnValue(false);
            }
        }
    }

    @Inject(method = "customServerAiStep", at = @At("TAIL"))
    private void foolsbarrel$accelerateAngerDecay(ServerLevel level, CallbackInfo ci) {
        Warden self = (Warden) (Object) this;

        if (self.tickCount % 20 != 0) return;

        self.getEntityAngryAt()
                .filter(e -> e instanceof Player player && BarrelUtil.isHiddenInBarrel(player))
                .ifPresent(e -> {
                    var anger = self.getAngerManagement();
                    anger.tick(level, self::canTargetEntity);
                });
    }
}
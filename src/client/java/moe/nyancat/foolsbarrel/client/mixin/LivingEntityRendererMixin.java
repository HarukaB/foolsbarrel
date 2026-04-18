package moe.nyancat.foolsbarrel.client.mixin;

import moe.nyancat.foolsbarrel.BarrelUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin {

    @Inject(method = "setupRotations", at = @At("HEAD"), cancellable = true)
    private void foolsbarrel$freezeRotation(LivingEntityRenderState state, PoseStack poseStack, float animTime, float bodyRot, CallbackInfo ci) {
        if (state instanceof net.minecraft.client.renderer.entity.state.HumanoidRenderState humanoid
                && BarrelUtil.isWearingBarrel(humanoid.headEquipment)
                && humanoid.isCrouching) {
            ci.cancel();
        }
    }
}

package moe.nyancat.foolsbarrel.client.mixin;

import moe.nyancat.foolsbarrel.BarrelUtil;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidModel.class)
public abstract class HumanoidModelMixin {
    @Shadow public ModelPart head;
    @Shadow public ModelPart hat;
    @Shadow public ModelPart body;
    @Shadow public ModelPart leftArm;
    @Shadow public ModelPart rightArm;
    @Shadow public ModelPart leftLeg;
    @Shadow public ModelPart rightLeg;

    @Inject(method = "setupAnim(Lnet/minecraft/client/renderer/entity/state/HumanoidRenderState;)V", at = @At("RETURN"))
    private void foolsbarrel$hideBodyParts(HumanoidRenderState state, CallbackInfo ci) {
        boolean hasBarrel = BarrelUtil.isWearingBarrel(state.headEquipment);
        if (hasBarrel) {
            this.head.visible = false;
            this.hat.visible = false;
            this.leftArm.visible = false;
            this.rightArm.visible = false;
            if (state.isCrouching) {
                this.body.visible = false;
                this.leftLeg.visible = false;
                this.rightLeg.visible = false;
            }
        } else {
            // Restore visibility when barrel is removed (visible isn't reset by resetPose)
            this.head.visible = true;
            this.hat.visible = true;
            this.leftArm.visible = true;
            this.rightArm.visible = true;
            this.body.visible = true;
            this.leftLeg.visible = true;
            this.rightLeg.visible = true;
        }
    }
}

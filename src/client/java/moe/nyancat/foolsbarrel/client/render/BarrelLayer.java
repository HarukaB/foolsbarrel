package moe.nyancat.foolsbarrel.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import moe.nyancat.foolsbarrel.BarrelUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.BlockModelRenderState;
import net.minecraft.client.renderer.block.BlockModelResolver;
import net.minecraft.client.renderer.block.model.BlockDisplayContext;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class BarrelLayer<S extends LivingEntityRenderState, M extends EntityModel<? super S>> extends RenderLayer<S, M> {

    private final BlockModelResolver blockModelResolver;
    private final BlockModelRenderState blockModelRenderState = new BlockModelRenderState();
    private final BlockDisplayContext displayContext = BlockDisplayContext.create();

    public BarrelLayer(RenderLayerParent<S, M> renderer) {
        super(renderer);
        this.blockModelResolver = new BlockModelResolver(Minecraft.getInstance().getModelManager());
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector collector, int packedLight, S state, float yRot, float xRot) {
        if (!(state instanceof HumanoidRenderState humanoid)) return;
        if (!BarrelUtil.isWearingBarrel(humanoid.headEquipment)) return;

        BlockState blockState = Blocks.BARREL.defaultBlockState();
        if (blockState.hasProperty(BlockStateProperties.OPEN)) {
            blockState = blockState.setValue(BlockStateProperties.OPEN, !humanoid.isCrouching);
        }
        if (blockState.hasProperty(BlockStateProperties.FACING)) {
            // UP in model space → entity renderer's scale(-1,-1,1) flips it → bottom faces up visually
            blockState = blockState.setValue(BlockStateProperties.FACING, Direction.UP);
        }

        poseStack.pushPose();

        if (humanoid.isCrouching) {
            poseStack.scale(1.07F, 1.07F, 1.07F);
            poseStack.translate(-0.5, 0.28, -0.5);
        } else {
            poseStack.translate(-0.5, -0.25, -0.5);
        }

        // No manual flip needed — entity renderer's scale(-1,-1,1) naturally
        // inverts the barrel so the bottom faces up (correct for barrel-on-head)

        // Use BlockModelRenderState + BlockModelResolver (same as BlockDisplayRenderer)
        // This passes packedLight directly, matching how the reference 22w13ob rendered blocks
        blockModelResolver.update(blockModelRenderState, blockState, displayContext);
        blockModelRenderState.submit(poseStack, collector, packedLight, OverlayTexture.NO_OVERLAY, 0);

        poseStack.popPose();
    }
}

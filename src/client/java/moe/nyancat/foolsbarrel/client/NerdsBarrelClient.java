package moe.nyancat.foolsbarrel.client;

import moe.nyancat.foolsbarrel.BarrelUtil;
import moe.nyancat.foolsbarrel.client.render.BarrelLayer;
import moe.nyancat.foolsbarrel.client.render.BarrelOverlayRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRenderEvents;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityRenderLayerRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.resources.Identifier;

public class NerdsBarrelClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Register BarrelLayer on player and humanoid mob renderers
        LivingEntityRenderLayerRegistrationCallback.EVENT.register((entityType, renderer, registrar, context) -> {
            if (renderer instanceof AvatarRenderer<?> || renderer instanceof HumanoidMobRenderer<?, ?, ?>) {
                //noinspection unchecked,rawtypes
                registrar.register(new BarrelLayer(renderer));
            }
        });

        // Register barrel overlay HUD element
        HudElementRegistry.attachElementAfter(
                VanillaHudElements.MISC_OVERLAYS,
                Identifier.fromNamespaceAndPath("foolsbarrel", "barrel_overlay"),
                BarrelOverlayRenderer::extractRenderState
        );

        // Hide cape when wearing barrel (using Fabric API event)
        LivingEntityFeatureRenderEvents.ALLOW_CAPE_RENDER.register(state ->
                !BarrelUtil.isWearingBarrel(state.headEquipment)
        );
    }
}

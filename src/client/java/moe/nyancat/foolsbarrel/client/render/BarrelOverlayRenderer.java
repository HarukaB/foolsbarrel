package moe.nyancat.foolsbarrel.client.render;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Items;

public class BarrelOverlayRenderer {
    private static final Identifier BARREL_OVERLAY = Identifier.fromNamespaceAndPath("foolsbarrel", "textures/misc/barrel_eye_holes.png");

    public static void extractRenderState(GuiGraphicsExtractor extractor, DeltaTracker deltaTracker) {
        var mc = Minecraft.getInstance();
        if (mc.player == null) return;
        if (!mc.options.getCameraType().isFirstPerson()) return;
        if (!mc.player.getItemBySlot(EquipmentSlot.HEAD).is(Items.BARREL)) return;

        int screenWidth = extractor.guiWidth();
        int screenHeight = extractor.guiHeight();

        // Calculate centered square matching reference code
        float size = Math.min(screenWidth, screenHeight);
        float scale = Math.min((float) screenWidth / size, (float) screenHeight / size);
        int w = (int) (size * scale);
        int h = (int) (size * scale);
        int x0 = (screenWidth - w) / 2;
        int y0 = (int) ((screenHeight - h) / 2.0F - mc.player.xRotO * 4.0F);
        int x1 = x0 + w;
        int y1 = y0 + h;

        // Draw barrel eye holes texture (using the same pattern as vanilla spyglass overlay)
        extractor.blit(RenderPipelines.GUI_TEXTURED, BARREL_OVERLAY, x0, y0, 0.0F, 0.0F, w, h, w, h);

        // Draw black fill around the overlay area (same pattern as vanilla)
        int black = 0xFF000000;
        extractor.fill(RenderPipelines.GUI, 0, y1, screenWidth, screenHeight, black);  // bottom
        extractor.fill(RenderPipelines.GUI, 0, 0, screenWidth, y0, black);              // top
        extractor.fill(RenderPipelines.GUI, 0, y0, x0, y1, black);                      // left
        extractor.fill(RenderPipelines.GUI, x1, y0, screenWidth, y1, black);             // right
    }
}

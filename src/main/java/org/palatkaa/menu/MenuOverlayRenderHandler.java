package org.palatkaa.menu;

import net.minecraft.client.Minecraft;
import org.palatkaa.context.RenderContext;
import org.palatkaa.event.EventTarget;
import org.palatkaa.event.events.render.FinalGuiRenderEvent;
import org.palatkaa.feature.impl.visual.HudFeature;
import org.palatkaa.menu.core.MenuOverlay;
import org.palatkaa.utils.render.gui.Render2DUtil;

public final class MenuOverlayRenderHandler {
   @EventTarget
   public void onFinalGuiRender(FinalGuiRenderEvent event) {
      Minecraft minecraft = event.getClient();
      RenderContext.enter2D(event.getGui(), event.getGuiGraphicsExtractor(), event.getDeltaTracker());

      try {
         int screenWidth = minecraft.getWindow().getGuiScaledWidth();
         int screenHeight = minecraft.getWindow().getGuiScaledHeight();
         Render2DUtil.beginFrame();
         Render2DUtil.setBackdropBlurScale(HudFeature.glassBlurScale());
         MenuOverlay.render(minecraft, event.getGuiGraphicsExtractor(), screenWidth, screenHeight);
         Render2DUtil.flush();
      } finally {
         RenderContext.exit2D();
      }
   }
}


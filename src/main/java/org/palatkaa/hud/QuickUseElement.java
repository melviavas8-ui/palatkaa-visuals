package org.palatkaa.hud;

import net.minecraft.client.Minecraft;

/**
 * QuickUse HUD element. ServerHelperFeature was removed — element stays registered
 * but renders nothing until a replacement source of quick-use entries is provided.
 */
public final class QuickUseElement extends HudElement {
   public QuickUseElement() {
      super("quick_use", "QuickUse");
   }

   @Override
   protected void layout(Minecraft mc, float unit) {
      this.width = 0.0F;
      this.height = 0.0F;
   }

   @Override
   protected float defaultY(float unit) {
      return 220.0F * unit;
   }

   @Override
   protected void draw(Minecraft mc, float unit) {
      // no-op: ServerHelperFeature removed
   }
}

package ua.daamky.events;

import ua.daamky.system.events.CancellableEvent;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;

public class RenderEvent extends CancellableEvent {
   private final RenderTickCounter f1;
   private final DrawContext f2;

   public RenderEvent(RenderTickCounter var1) {
      this(var1, null);
   }

   public RenderEvent(RenderTickCounter var1, DrawContext var2) {
      this.f1 = var1;
      this.f2 = var2;
   }
   public RenderTickCounter m582() {
      return this.f1;
   }
   public DrawContext m583() {
      return this.f2;
   }
}

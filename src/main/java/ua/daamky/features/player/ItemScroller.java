package ua.daamky.features.player;

import ua.daamky.events.RenderEvent;
import ua.daamky.mixins.interfaces.IHandledScreen;
import ua.daamky.settings.NumberSetting;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;
import ua.daamky.system.events.EventHandler;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.InputUtil;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import org.lwjgl.glfw.GLFW;

@NewFunction(
   I0 = "ItemScroller",
   I00 = "Быстрое перемещение предметов при зажатом Shift и ЛКМ",
   I000 = Category.PLAYER
)
public class ItemScroller extends Module {
   private final NumberSetting f1 = new NumberSetting("Cooldown", 30.0, 1.0, 200.0, 0.01);
   private long f2;

   public ItemScroller() {
      this.addSettings(new Setting[]{this.f1});
   }

   @EventHandler
   public void m330(RenderEvent var1) {
      if (!this.util.m81()
         && this.mc.currentScreen != null
         && this.mc.interactionManager != null
         && this.mc.currentScreen instanceof HandledScreen var2
         && this.m687()
         && GLFW.glfwGetMouseButton(this.mc.getWindow().getHandle(), 0) == 1
         && this.m14((long)this.f1.getValue())) {
         Slot var4 = ((IHandledScreen)var2).getFocusedSlot();
         if (var4 != null && var4.hasStack()) {
            this.mc.interactionManager.clickSlot(var2.getScreenHandler().syncId, var4.id, 0, SlotActionType.QUICK_MOVE, this.mc.player);
            this.m116();
         }
      }
   }

   private boolean m687() {
      return this.mc.options.sneakKey.isPressed() || InputUtil.isKeyPressed(this.mc.getWindow(), this.mc.options.sneakKey.getDefaultKey().getCode());
   }

   private boolean m14(long var1) {
      return System.currentTimeMillis() - this.f2 >= var1;
   }

   private void m116() {
      this.f2 = System.currentTimeMillis();
   }
}

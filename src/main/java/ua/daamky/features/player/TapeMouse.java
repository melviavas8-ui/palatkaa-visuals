package ua.daamky.features.player;

import ua.daamky.events.PostMotionEvent;
import ua.daamky.mixins.interfaces.IMinecraftClient;
import ua.daamky.settings.ModeSettingBase;
import ua.daamky.settings.NumberSetting;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;
import ua.daamky.system.events.EventHandler;

@NewFunction(
   I0 = "TapeMouse",
   I00 = "Автоматические клики для фоновых ботов",
   I000 = Category.PLAYER
)
public class TapeMouse extends Module {
   private final NumberSetting f1 = new NumberSetting("CPS", 1.0, 0.0, 2.0, 0.05);
   private final ModeSettingBase f2 = new ModeSettingBase(
      "Кнопка", "Левая", "Правая"
   );
   private long f3;

   public TapeMouse() {
      this.addSettings(new Setting[]{this.f1, this.f2});
   }

   @EventHandler
   public void m67(PostMotionEvent var1) {
      long var2 = (long)(1000.0 / this.f1.getValue());
      long var4 = System.currentTimeMillis();
      if (var4 - this.f3 >= var2) {
         this.m115();
         this.f3 = var4;
      }
   }

   private void m115() {
      if (this.mc.player != null && this.mc.currentScreen == null) {
         if (this.f2.m17("Правая")) {
            ((IMinecraftClient)this.mc).invokeDoItemUse();
         } else {
            ((IMinecraftClient)this.mc).invokeDoAttack();
         }
      }
   }
}

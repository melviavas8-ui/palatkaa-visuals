package ua.daamky.features.player;

import ua.daamky.events.KeyEvent;
import ua.daamky.settings.KeybindSetting;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;
import ua.daamky.system.events.EventHandler;
import ua.daamky.utils.render.RenderHelper;

@NewFunction(
   I0 = "Bots",
   I00 = "Управление ботами через GUI",
   I000 = Category.PLAYER
)
public class Bots extends Module {
   private final KeybindSetting f1 = new KeybindSetting("Бинд GUI", -1);

   public Bots() {
      this.addSettings(new Setting[]{this.f1});
   }

   @EventHandler
   public void m781(KeyEvent var1) {
      if (var1.m189() == 1) {
         if (this.f1.m13(var1.m580().key()) && this.mc.currentScreen == null) {
            this.mc.setScreen(new RenderHelper());
         }
      }
   }
}

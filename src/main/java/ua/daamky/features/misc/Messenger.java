package ua.daamky.features.misc;

import ua.daamky.events.KeyEvent;
import ua.daamky.gui.MessengerScreen;
import ua.daamky.settings.KeybindSetting;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;
import ua.daamky.system.events.EventHandler;

@NewFunction(
   I0 = "Messenger",
   I00 = "Внутриигровой чат сообщества Daamky",
   I000 = Category.MISC
)
public class Messenger extends Module {
   private final KeybindSetting f1 = new KeybindSetting("Бинд открытия", -1);

   public Messenger() {
      this.addSettings(new Setting[]{this.f1});
   }

   @EventHandler
   public void m781(KeyEvent var1) {
      if (var1.m189() == 1) {
         if (this.f1.m13(var1.m580().key()) && this.mc.currentScreen == null) {
            this.mc.setScreen(new MessengerScreen());
         }
      }
   }
}

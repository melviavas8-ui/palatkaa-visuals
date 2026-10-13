package ua.daamky.features.misc;

import ua.daamky.events.PostMotionEvent;
import ua.daamky.settings.BindSetting;
import ua.daamky.settings.BooleanSetting;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;
import ua.daamky.system.events.EventHandler;
import net.minecraft.client.gui.screen.DeathScreen;

@NewFunction(
   I0 = "AutoRespawn",
   I00 = "Автоматически возрождается после смерти",
   I000 = Category.MISC
)
public class AutoRespawn extends Module {
   private final BooleanSetting f1 = new BooleanSetting("Использовать команду", false);
   private final BindSetting f2 = new BindSetting("Команда", "home");
   private boolean f3 = false;

   public AutoRespawn() {
      this.addSettings(new Setting[]{this.f1, this.f2});
   }

   @Override
   public void onEnable() {
      this.f3 = false;
      super.onEnable();
   }

   @EventHandler
   public void m67(PostMotionEvent var1) {
      if (this.mc.player != null) {
         if (this.mc.currentScreen instanceof DeathScreen) {
            if (!this.f3) {
               this.mc.player.requestRespawn();
               this.mc.setScreen(null);
               if (this.f1.m6()) {
                  String var2 = this.f2.m30();
                  if (var2.startsWith("/")) {
                     var2 = var2.substring(1);
                  }

                  if (this.mc.player.networkHandler != null && !var2.isEmpty()) {
                     this.mc.player.networkHandler.sendChatCommand(var2);
                  }
               }

               this.f3 = true;
            }
         } else {
            this.f3 = false;
         }
      }
   }
}

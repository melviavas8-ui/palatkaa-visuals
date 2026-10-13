package ua.daamky.features.misc;

import ua.daamky.events.PostMotionEvent;
import ua.daamky.settings.BooleanSetting;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;
import ua.daamky.system.events.EventHandler;
import ua.daamky.utils.FunTimeUtil;

@NewFunction(
   I0 = "ChatHelper",
   I00 = "Расширяет возможности чата",
   I000 = Category.MISC
)
public class ChatHelper extends Module {
   private final BooleanSetting f1 = new BooleanSetting("Автоматическое /event delay", false);
   private int f2 = -1;

   public ChatHelper() {
      this.addSettings(new Setting[]{this.f1});
   }

   @EventHandler
   public void m67(PostMotionEvent var1) {
      if (!this.util.m81() && this.f1.m6()) {
         int var2;
         if (!FunTimeUtil.m6() && !FunTimeUtil.m101()) {
            var2 = FunTimeUtil.m31() ? FunTimeUtil.m103() : -1;
         } else {
            var2 = FunTimeUtil.m102();
         }

         if (var2 == -1) {
            this.f2 = 0;
         } else {
            if (this.f2 == -1) {
               this.f2 = var2;
            } else if (var2 != this.f2 && this.mc.player.age >= 5) {
               this.mc.player.networkHandler.sendChatCommand("event delay");
               this.f2 = var2;
            }
         }
      }
   }
}

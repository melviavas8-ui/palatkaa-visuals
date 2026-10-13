package ua.daamky.features.misc;

import ua.daamky.events.ChatMessageEvent;
import ua.daamky.events.PostMotionEvent;
import ua.daamky.settings.BooleanSetting;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;
import ua.daamky.system.events.EventHandler;
import ua.daamky.utils.player.RaytraceUtil;
import java.util.Locale;

@NewFunction(
   I0 = "AutoTpAccept",
   I00 = "Автоматически принимает запросы телепортации",
   I000 = Category.MISC
)
public class AutoTpAccept extends Module {
   private static final String[] f1 = new String[]{
      "has requested teleport",
      "просит телепортироваться",
      "хочет телепортироваться к вам",
      "просит к вам телепортироваться"
   };
   private final BooleanSetting f2 = new BooleanSetting("Принимать только друзей", true);
   private boolean f3;

   public AutoTpAccept() {
      this.addSettings(new Setting[]{this.f2});
   }

   @EventHandler
   public void m780(ChatMessageEvent var1) {
      String var2 = var1.m556().content().getString();
      String var3 = var2.toLowerCase(Locale.ROOT);
      if (this.m20(var3)) {
         this.f3 = !this.f2.m6() || RaytraceUtil.m394().values().stream().anyMatch(var1x -> var3.contains(var1x.toLowerCase(Locale.ROOT)));
      }
   }

   @EventHandler
   public void m67(PostMotionEvent var1) {
      if (this.f3 && this.mc.player != null && this.mc.player.networkHandler != null) {
         this.mc.player.networkHandler.sendChatCommand("tpaccept");
         this.f3 = false;
      }
   }

   @Override
   public void onDisable() {
      this.f3 = false;
   }

   private boolean m20(String var1) {
      for (String var5 : f1) {
         if (var1.contains(var5)) {
            return true;
         }
      }

      return false;
   }
}

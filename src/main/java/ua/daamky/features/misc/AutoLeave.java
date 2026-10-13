package ua.daamky.features.misc;

import ua.daamky.events.PostMotionEvent;
import ua.daamky.settings.BooleanSetting;
import ua.daamky.settings.ModeSettingBase;
import ua.daamky.settings.NumberSetting;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;
import ua.daamky.system.events.EventHandler;
import ua.daamky.utils.player.RaytraceUtil;
import java.util.Locale;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.Team;
import net.minecraft.text.Text;

@NewFunction(
   I0 = "AutoLeave",
   I00 = "Автоматически выходит, когда рядом появляется игрок",
   I000 = Category.MISC
)
public class AutoLeave extends Module {
   private final ModeSettingBase f1 = new ModeSettingBase(
      "Тип выхода", "Main Menu", "Hub"
   );
   private final BooleanSetting f2 = new BooleanSetting("Players", true);
   private final BooleanSetting f3 = new BooleanSetting("Staff", true);
   private final NumberSetting f4 = new NumberSetting(
      "Максимальная дистанция", 10.0, 5.0, 40.0, 1.0
   );

   public AutoLeave() {
      this.addSettings(new Setting[]{this.f1, this.f2, this.f3, this.f4});
   }

   @EventHandler
   public void m67(PostMotionEvent var1) {
      if (this.mc.player != null && this.mc.world != null && this.mc.getNetworkHandler() != null) {
         if (this.f2.m6()) {
            for (PlayerEntity var3 : this.mc.world.getPlayers()) {
               if (var3 != this.mc.player && !RaytraceUtil.m80(var3.getGameProfile().name()) && (double)this.mc.player.distanceTo(var3) <= this.f4.getValue()) {
                  this.m779(var3.getName().copy().append(" - появился рядом " + Math.round(this.mc.player.distanceTo(var3)) + "м"));
                  return;
               }
            }
         }

         if (this.f3.m6() && this.m687()) {
            this.m779(Text.literal("Стафф на сервере"));
         }
      }
   }

   private boolean m687() {
      for (PlayerListEntry var2 : this.mc.getNetworkHandler().getListedPlayerListEntries()) {
         Team var3 = var2.getScoreboardTeam();
         String var4 = ((var3 == null ? "" : var3.getPrefix().getString() + " " + var3.getSuffix().getString()) + " " + var2.getProfile().name())
            .toLowerCase(Locale.ROOT);
         if (var4.contains("staff")
            || var4.contains("moder")
            || var4.contains("helper")
            || var4.contains("admin")
            || var4.contains("модер")
            || var4.contains("хелпер")
            || var4.contains("админ")) {
            return true;
         }
      }

      return false;
   }

   private void m779(Text var1) {
      if (this.mc.getNetworkHandler() != null) {
         if (this.f1.m17("Hub")) {
            this.mc.getNetworkHandler().sendChatCommand("hub");
         } else {
            this.mc.getNetworkHandler().getConnection().disconnect(Text.literal("[AutoLeave]\n").append(var1));
         }

         this.toggle();
      }
   }
}

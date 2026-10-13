package ua.daamky.features.misc;

import ua.daamky.events.GameJoinEvent;
import ua.daamky.events.InteractEvent;
import ua.daamky.events.PostMotionEvent;
import ua.daamky.settings.BindSetting;
import ua.daamky.settings.BooleanSetting;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;
import ua.daamky.system.events.EventHandler;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.entity.player.PlayerEntity;

@NewFunction(
   I0 = "TrashTalk",
   I00 = "Отправляет сообщения в чат после снятия тотема и убийств",
   I000 = Category.MISC
)
public class TrashTalk extends Module {
   private static final byte f1 = 35;
   private static final long f2 = 10000L;
   private static final long f3 = 1000L;
   private final BooleanSetting f4 = new BooleanSetting("Писать за тотем", true);
   private final BindSetting f5 = new BindSetting(
      "Текст за тотем", "попнул тотем"
   );
   private final BooleanSetting f6 = new BooleanSetting("Писать за килл", true);
   private final BindSetting f7 = new BindSetting(
      "Текст за килл", "убит"
   );
   private final Map<UUID, Long> f8 = new HashMap<>();
   private UUID f9;
   private String f10;
   private long f11;

   public TrashTalk() {
      this.addSettings(new Setting[]{this.f4, this.f5, this.f6, this.f7});
   }

   @Override
   public void onDisable() {
      this.f8.clear();
      this.f9 = null;
      this.f10 = null;
      this.f11 = 0L;
   }

   @EventHandler
   public void m771(InteractEvent var1) {
      if (var1.m552() == this.mc.player && var1.m553() instanceof PlayerEntity var2 && var2 != this.mc.player) {
         this.f9 = var2.getUuid();
         this.f10 = var2.getGameProfile().name();
         this.f11 = System.currentTimeMillis();
      }
   }

   @EventHandler
   public void m988(GameJoinEvent var1) {
      if (this.f4.m6()) {
         if (var1.m555().getStatus() == 35 && var1.m553() instanceof PlayerEntity var2 && var2 != this.mc.player) {
            this.m989(var2.getUuid(), var2.getGameProfile().name(), this.f5.m30());
         }
      }
   }

   @EventHandler
   public void m67(PostMotionEvent var1) {
      if (this.f6.m6()) {
         if (this.mc.world != null && this.f9 != null && System.currentTimeMillis() - this.f11 <= 10000L) {
            if (this.mc.world.getPlayerByUuid(this.f9) instanceof PlayerEntity var3) {
               if (!var3.isAlive() || var3.getHealth() <= 0.0F) {
                  this.m989(this.f9, this.f10, this.f7.m30());
                  this.f9 = null;
                  this.f10 = null;
               }
            }
         }
      }
   }

   private void m989(UUID var1, String var2, String var3) {
      if (this.mc.player != null && this.mc.player.networkHandler != null && var3 != null && !var3.isBlank() && var2 != null && !var2.isBlank()) {
         long var4 = System.currentTimeMillis();
         Long var6 = this.f8.get(var1);
         if (var6 == null || var4 - var6 >= 1000L) {
            this.f8.put(var1, var4);
            this.mc.player.networkHandler.sendChatMessage(var2 + " " + var3.trim());
         }
      }
   }
}

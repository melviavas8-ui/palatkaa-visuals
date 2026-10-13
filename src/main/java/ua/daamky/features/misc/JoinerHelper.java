package ua.daamky.features.misc;

import ua.daamky.events.PacketReceiveEvent;
import ua.daamky.events.PostMotionEvent;
import ua.daamky.settings.ModeSettingBase;
import ua.daamky.settings.NumberSetting;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;
import ua.daamky.system.events.EventHandler;
import java.util.Locale;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.network.packet.s2c.common.DisconnectS2CPacket;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;
import org.lwjgl.glfw.GLFW;

@NewFunction(
   I0 = "JoinerHelper",
   I00 = "Помогает заходить на выбранный сервер",
   I000 = Category.MISC
)
public class JoinerHelper extends Module {
   private final ModeSettingBase f1 = new ModeSettingBase(
      "Сервер",
      "ReallyWorld",
      "SpookyTime Duels"
   );
   private final NumberSetting f2 = new NumberSetting("Номер грифа", 1.0, 1.0, 54.0, 1.0);
   private long f3;

   public JoinerHelper() {
      this.addSettings(new Setting[]{this.f1, this.f2});
   }

   @Override
   public void onEnable() {
      this.f3 = 0L;
      this.m115();
   }

   @Override
   public void onDisable() {
      this.f3 = 0L;
   }

   @EventHandler
   public void m67(PostMotionEvent var1) {
      if (this.mc.player != null) {
         if (GLFW.glfwGetKey(this.mc.getWindow().getHandle(), 260) == 1) {
            this.toggle();
         } else if (this.mc.currentScreen == null && this.mc.player.age < 5) {
            this.m115();
         } else if (this.mc.currentScreen instanceof GenericContainerScreen var2) {
            for (int var5 = 0; var5 < ((GenericContainerScreenHandler)var2.getScreenHandler()).slots.size(); var5++) {
               String var4 = ((Slot)((GenericContainerScreenHandler)var2.getScreenHandler()).slots.get(var5))
                  .getStack()
                  .getName()
                  .getString()
                  .toLowerCase(Locale.ROOT);
               if (this.m20(var4) && System.currentTimeMillis() - this.f3 > 70L) {
                  this.mc
                     .interactionManager
                     .clickSlot(((GenericContainerScreenHandler)var2.getScreenHandler()).syncId, var5, 0, SlotActionType.PICKUP, this.mc.player);
                  this.f3 = System.currentTimeMillis();
                  return;
               }
            }
         }
      }
   }

   @EventHandler
   public void m594(PacketReceiveEvent var1) {
      if (var1.m581() instanceof DisconnectS2CPacket var2) {
         String var4 = var2.reason().getString().toLowerCase(Locale.ROOT);
         if (var4.contains("переполнен")
            || var4.contains("подождите")
            || var4.contains("уже подключены")
            || var4.contains("server is full")) {
            this.m115();
         }
      }
   }

   private boolean m20(String var1) {
      if (this.f1.m17("ReallyWorld")) {
         int var2 = (int)this.f2.getValue();
         return var1.contains("гриферское выживание")
            || var1.contains("гриф #" + var2)
            || var1.contains("grief #" + var2);
      } else {
         return var1.contains("дуэли")
            || var1.contains("duels")
            || var1.contains("липкий поршень");
      }
   }

   private void m115() {
      if (this.mc.player != null && this.mc.player.networkHandler != null) {
         this.mc
            .player
            .networkHandler
            .sendPacket(
               new PlayerInteractItemC2SPacket(
                  Hand.MAIN_HAND, this.mc.player.getInventory().getSelectedSlot(), this.mc.player.getYaw(), this.mc.player.getPitch()
               )
            );
      }
   }
}

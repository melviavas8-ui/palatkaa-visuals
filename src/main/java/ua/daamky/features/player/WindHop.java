package ua.daamky.features.player;

import ua.daamky.events.MovementInputEvent;
import ua.daamky.events.PacketSendEvent;
import ua.daamky.events.PostMotionEvent;
import ua.daamky.settings.BooleanSetting;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;
import ua.daamky.system.events.EventHandler;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;

@NewFunction(
   I0 = "WindHop",
   I00 = "Автоматически прыгает после использования заряда ветра",
   I000 = Category.PLAYER
)
public class WindHop extends Module {
   private final BooleanSetting f1 = new BooleanSetting("Поворачивать голову вниз", true);
   private int f2 = -1;

   public WindHop() {
      this.addSettings(new Setting[]{this.f1});
   }

   @EventHandler
   public void m870(PacketSendEvent var1) {
      if (!this.util.m81()) {
         if (var1.m581() instanceof PlayerInteractItemC2SPacket var2 && this.mc.player.getStackInHand(var2.getHand()).isOf(Items.WIND_CHARGE)) {
            this.f2 = 2;
            if (this.f1.m6()) {
               this.mc.player.setPitch(90.0F);
            }
         }
      }
   }

   @EventHandler
   public void m67(PostMotionEvent var1) {
      if (this.f2 > 0) {
         this.f2--;
      }
   }

   @EventHandler
   public void m660(MovementInputEvent var1) {
      if (this.f2 == 0) {
         var1.m4(true);
         this.f2 = -1;
      }
   }
}

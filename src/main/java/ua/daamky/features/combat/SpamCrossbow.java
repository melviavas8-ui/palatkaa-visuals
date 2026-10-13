package ua.daamky.features.combat;

import ua.daamky.events.PostMotionEvent;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;
import ua.daamky.system.events.EventHandler;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.util.Hand;

@NewFunction(
   I0 = "SpamCrossbow",
   I00 = "Спамит стрелами из арбалета",
   I000 = Category.COMBAT
)
public class SpamCrossbow extends Module {
   @EventHandler
   public void m67(PostMotionEvent var1) {
      if (this.mc.player != null && this.mc.world != null) {
         boolean var2 = this.mc.player.getMainHandStack().getItem() == Items.CROSSBOW;
         boolean var3 = this.mc.player.getOffHandStack().getItem() == Items.CROSSBOW;
         if (var2 || var3) {
            Hand var4 = var2 ? Hand.MAIN_HAND : Hand.OFF_HAND;
            this.mc.player.networkHandler.sendPacket(new PlayerInteractItemC2SPacket(var4, 0, this.mc.player.getYaw(), this.mc.player.getPitch()));
         }
      }
   }
}

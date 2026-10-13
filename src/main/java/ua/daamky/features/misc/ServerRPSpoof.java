package ua.daamky.features.misc;

import ua.daamky.events.PacketReceiveEvent;
import ua.daamky.events.PostMotionEvent;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;
import ua.daamky.system.events.EventHandler;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.c2s.common.ResourcePackStatusC2SPacket;
import net.minecraft.network.packet.c2s.common.ResourcePackStatusC2SPacket.Status;
import net.minecraft.network.packet.s2c.common.ResourcePackSendS2CPacket;

@NewFunction(
   I0 = "ServerRPSpoof",
   I00 = "Отправляет серверу статус успешной загрузки ресурс-пака",
   I000 = Category.MISC
)
public class ServerRPSpoof extends Module {
   private ResourcePackSendS2CPacket f1;
   private long f2;

   @EventHandler
   public void m594(PacketReceiveEvent var1) {
      if (var1.m581() instanceof ResourcePackSendS2CPacket var2) {
         this.f1 = var2;
         this.f2 = 0L;
         var1.m29();
      }
   }

   @EventHandler
   public void m67(PostMotionEvent var1) {
      if (this.f1 != null && this.mc.player != null) {
         ClientPlayNetworkHandler var2 = this.mc.getNetworkHandler();
         if (var2 != null) {
            if (this.f2 == 0L) {
               var2.sendPacket(new ResourcePackStatusC2SPacket(this.f1.id(), Status.ACCEPTED));
               this.f2 = System.currentTimeMillis();
            } else {
               if (System.currentTimeMillis() - this.f2 >= 300L) {
                  var2.sendPacket(new ResourcePackStatusC2SPacket(this.f1.id(), Status.SUCCESSFULLY_LOADED));
                  this.f1 = null;
                  this.f2 = 0L;
               }
            }
         }
      }
   }

   @Override
   public void onDisable() {
      this.f1 = null;
      this.f2 = 0L;
   }
}

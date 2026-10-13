package ua.daamky.events;

import ua.daamky.system.events.CancellableEvent;
import net.minecraft.network.packet.Packet;

public class PacketSendEvent extends CancellableEvent {
   private final Packet<?> f1;
   private boolean f2;

   public PacketSendEvent(Packet<?> var1) {
      this.f1 = var1;
   }

   public Packet<?> m581() {
      return this.f1;
   }

   public void m29() {
      this.f2 = true;
   }

   @Override
   public boolean isCancelled() {
      return this.f2;
   }
}

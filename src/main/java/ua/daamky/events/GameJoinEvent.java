package ua.daamky.events;

import ua.daamky.system.events.CancellableEvent;
import net.minecraft.entity.Entity;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;

public class GameJoinEvent extends CancellableEvent {
   private final EntityStatusS2CPacket f1;
   private final Entity f2;

   public GameJoinEvent(EntityStatusS2CPacket var1, Entity var2) {
      this.f1 = var1;
      this.f2 = var2;
   }
   public EntityStatusS2CPacket m555() {
      return this.f1;
   }
   public Entity m553() {
      return this.f2;
   }
}

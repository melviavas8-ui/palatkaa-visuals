package ua.daamky.events;

import ua.daamky.system.events.CancellableEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

public class InteractEvent extends CancellableEvent {
   private final PlayerEntity f1;
   private final Entity f2;

   public InteractEvent(PlayerEntity var1, Entity var2) {
      this.f1 = var1;
      this.f2 = var2;
   }
   public PlayerEntity m552() {
      return this.f1;
   }
   public Entity m553() {
      return this.f2;
   }
}

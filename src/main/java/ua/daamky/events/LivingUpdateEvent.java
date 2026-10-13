package ua.daamky.events;

import ua.daamky.system.events.CancellableEvent;
import net.minecraft.util.math.BlockPos;

public class LivingUpdateEvent extends CancellableEvent {
   private final BlockPos f1;

   public LivingUpdateEvent(BlockPos var1) {
      this.f1 = var1;
   }
   public BlockPos m554() {
      return this.f1;
   }
}

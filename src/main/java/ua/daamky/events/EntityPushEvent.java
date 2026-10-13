package ua.daamky.events;

import ua.daamky.system.events.CancellableEvent;
import net.minecraft.util.math.Vec3d;

public class EntityPushEvent extends CancellableEvent {
   private final float f1;
   private final Vec3d f2;
   public float m271() {
      return this.f1;
   }
   public Vec3d m557() {
      return this.f2;
   }
   public EntityPushEvent(float var1, Vec3d var2) {
      this.f1 = var1;
      this.f2 = var2;
   }
}

package ua.daamky.events;

import ua.daamky.system.events.CancellableEvent;

public class MovementInputEvent extends CancellableEvent {
   private float f1;
   private float f2;
   private boolean f3;
   private boolean f4;
   private boolean f5;
   public float m271() {
      return this.f1;
   }
   public float m272() {
      return this.f2;
   }
   public boolean m101() {
      return this.f3;
   }
   public boolean m31() {
      return this.f4;
   }
   public boolean m41() {
      return this.f5;
   }
   public void m348(float var1) {
      this.f1 = var1;
   }
   public void m410(float var1) {
      this.f2 = var1;
   }
   public void m4(boolean var1) {
      this.f3 = var1;
   }
   public void m300(boolean var1) {
      this.f4 = var1;
   }
   public void m579(boolean var1) {
      this.f5 = var1;
   }
   public MovementInputEvent(float var1, float var2, boolean var3, boolean var4, boolean var5) {
      this.f1 = var1;
      this.f2 = var2;
      this.f3 = var3;
      this.f4 = var4;
      this.f5 = var5;
   }
}

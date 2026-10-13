package ua.daamky.events;

import ua.daamky.system.events.CancellableEvent;
import net.minecraft.client.input.KeyInput;

public class KeyEvent extends CancellableEvent {
   int f1;
   KeyInput f2;
   public int m189() {
      return this.f1;
   }
   public KeyInput m580() {
      return this.f2;
   }
   public KeyEvent(int var1, KeyInput var2) {
      this.f1 = var1;
      this.f2 = var2;
   }
}

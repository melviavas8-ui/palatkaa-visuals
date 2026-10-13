package ua.daamky.system.events;


public abstract class CancellableEvent {
   private boolean cancelled = false;

   public CancellableEvent() {
      this.cancelled = false;
   }
   public boolean isCancelled() {
      return this.cancelled;
   }
   public void setCancelled(boolean var1) {
      this.cancelled = var1;
   }
}

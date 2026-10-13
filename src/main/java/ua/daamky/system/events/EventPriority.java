package ua.daamky.system.events;

public enum EventPriority {
   LOWEST(0),
   LOW(1),
   NORMAL(2),
   HIGH(3),
   HIGHEST(4),
   MONITOR(5);

   private final int level;

   private EventPriority(int var3) {
      this.level = var3;
   }

   public int getLevel() {
      return this.level;
   }
}

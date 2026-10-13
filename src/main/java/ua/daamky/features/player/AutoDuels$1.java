package ua.daamky.features.player;

public class AutoDuels$1 {
   public long f1 = System.currentTimeMillis();

   public AutoDuels$1() {
   }

   public boolean m14(long var1) {
      return System.currentTimeMillis() - this.f1 >= var1;
   }

   public void m63() {
      this.f1 = System.currentTimeMillis();
   }
}

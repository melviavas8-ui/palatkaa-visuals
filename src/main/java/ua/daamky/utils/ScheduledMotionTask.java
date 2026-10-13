package ua.daamky.utils;

public class ScheduledMotionTask {
   public long f1;
   public Runnable f2;
   public double f3;

   public ScheduledMotionTask(long var1, Runnable var3) {
      this.f1 = var1;
      this.f2 = var3;
      this.f3 = 0.0;
   }

   public ScheduledMotionTask(long var1, double var3, Runnable var5) {
      this.f1 = var1;
      this.f3 = var3;
      this.f2 = var5;
   }
}

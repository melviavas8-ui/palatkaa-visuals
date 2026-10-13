package ua.daamky.settings;

import java.util.function.DoubleSupplier;

public class NumberSetting extends Setting {
   private double value;
   private double min;
   private double max;
   private double step;
   private Runnable listener;

   public NumberSetting(String var1, double var2, double var4, double var6, double var8) {
      super(var1);
      this.value = Math.clamp(var2, var4, var6);
      this.min = var4;
      this.max = var6;
      this.step = var8;
   }

   public NumberSetting(String var1, double var2, DoubleSupplier var4, DoubleSupplier var5, double var6) {
      super(var1);
      this.value = Math.clamp(var2, var4.getAsDouble(), var5.getAsDouble());
      this.min = var4.getAsDouble();
      this.max = var5.getAsDouble();
      this.step = var6;
   }

   public void setValue(double var1) {
      this.value = Math.clamp(var1, this.min, this.max);
      if (this.listener != null) {
         this.listener.run();
      }
   }

   public void m5(Runnable var1) {
      this.listener = var1;
      var1.run();
   }
   public double getValue() {
      return this.value;
   }
   public double getMin() {
      return this.min;
   }
   public double getMax() {
      return this.max;
   }
   public double getStep() {
      return this.step;
   }
   public void m25(double var1) {
      this.min = var1;
   }
   public void m26(double var1) {
      this.max = var1;
   }
   public void m27(double var1) {
      this.step = var1;
   }
}

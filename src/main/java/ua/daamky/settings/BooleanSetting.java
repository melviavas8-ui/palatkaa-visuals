package ua.daamky.settings;


public class BooleanSetting extends Setting {
   private boolean f1;
   private Runnable f2;

   public BooleanSetting(String var1, boolean var2) {
      super(var1);
      this.f1 = var2;
   }

   public void m4(boolean var1) {
      this.f1 = var1;
      if (this.f2 != null) {
         this.f2.run();
      }
   }

   public void m5(Runnable var1) {
      this.f2 = var1;
      var1.run();
   }
   public boolean m6() {
      return this.f1;
   }
}

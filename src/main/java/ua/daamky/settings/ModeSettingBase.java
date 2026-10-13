package ua.daamky.settings;

import java.util.Arrays;
import java.util.List;

public class ModeSettingBase extends Setting {
   private String f1;
   private final List<String> f2;
   private Runnable f3;

   public ModeSettingBase(String var1, String... var2) {
      super(var1);
      this.f2 = Arrays.asList(var2);
      this.f1 = var2[0];
   }

   public void m16(String var1) {
      if (this.f2.contains(var1)) {
         this.f1 = var1;
         if (this.f3 != null) {
            this.f3.run();
         }
      }
   }

   public boolean m17(String var1) {
      return this.f1.equalsIgnoreCase(var1);
   }

   public void m5(Runnable var1) {
      this.f3 = var1;
      var1.run();
   }
   public String m18() {
      return this.f1;
   }
   public List<String> m19() {
      return this.f2;
   }
}

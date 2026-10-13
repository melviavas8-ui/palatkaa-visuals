package ua.daamky.settings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MultiChoiceSettingBase extends Setting {
   private final Map<String, Boolean> f1 = new HashMap<>();
   private final List<String> f2 = new ArrayList<>();
   private final List<String> f3 = new ArrayList<>();
   private Runnable f4;

   public MultiChoiceSettingBase(String var1, String... var2) {
      super(var1);

      for (String var6 : var2) {
         this.f1.put(var6, true);
         this.f2.add(var6);
         this.f3.add(var6);
      }
   }

   public boolean m20(String var1) {
      return this.f1.getOrDefault(var1, false);
   }

   public void m21(String var1) {
      if (this.f1.containsKey(var1)) {
         boolean var2 = !this.f1.get(var1);
         this.f1.put(var1, var2);
         if (var2 && !this.f2.contains(var1)) {
            this.f2.add(var1);
         } else if (!var2) {
            this.f2.remove(var1);
         }

         this.m23();
      }
   }

   public void m22(Set<String> var1) {
      this.f2.clear();

      for (String var3 : this.f3) {
         boolean var4 = var1.contains(var3);
         this.f1.put(var3, var4);
         if (var4) {
            this.f2.add(var3);
         }
      }

      this.m23();
   }

   public void m5(Runnable var1) {
      this.f4 = var1;
      this.m23();
   }

   private void m23() {
      if (this.f4 != null) {
         this.f4.run();
      }
   }
   public List<String> m24() {
      return this.f2;
   }
   public List<String> m19() {
      return this.f3;
   }
}

package ua.daamky.utils;

public class PresetItemData {
   public String f1;
   public int f2;
   public long f3;
   public boolean f4;
   public String f5;
   public String f6;
   public String f7;

   public PresetItemData() {
      this.f1 = "minecraft:stone";
      this.f2 = 1;
      this.f3 = 0L;
      this.f4 = false;
      this.f5 = "";
      this.f6 = "";
      this.f7 = "";
   }

   public PresetItemData(String var1, int var2) {
      this.f1 = "minecraft:stone";
      this.f2 = 1;
      this.f3 = 0L;
      this.f4 = false;
      this.f5 = "";
      this.f6 = "";
      this.f7 = "";
      this.f1 = var1;
      this.f2 = var2;
   }

   public String m37() {
      if (this.f5 != null && !this.f5.isBlank()) {
         return this.f5;
      } else {
         return this.f6 != null && !this.f6.isBlank() ? this.f6 : this.f1;
      }
   }

   public String m40() {
      return this.f4 ? "" : (this.f5 == null ? "" : this.f5);
   }
}

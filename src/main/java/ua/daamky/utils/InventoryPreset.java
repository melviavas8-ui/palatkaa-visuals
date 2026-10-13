package ua.daamky.utils;

public class InventoryPreset {
   public String f1;
   public PresetItemData[] f2;

   public InventoryPreset() {
      this.f1 = "новый";
      this.f2 = new PresetItemData[41];
   }

   public InventoryPreset(String var1) {
      this.f1 = "новый";
      this.f2 = new PresetItemData[41];
      this.f1 = var1;
   }

   public int m113() {
      int var1 = 0;
      if (this.f2 != null) {
         for (PresetItemData var5 : this.f2) {
            if (var5 != null) {
               var1++;
            }
         }
      }

      return var1;
   }

   public void m314() {
      if (this.f2 == null || this.f2.length != 41) {
         PresetItemData[] var1 = new PresetItemData[41];
         if (this.f2 != null) {
            System.arraycopy(this.f2, 0, var1, 0, Math.min(this.f2.length, 41));
         }

         this.f2 = var1;
      }

      if (this.f1 == null || this.f1.isBlank()) {
         this.f1 = "новый";
      }
   }
}

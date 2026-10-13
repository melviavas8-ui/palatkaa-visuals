package ua.daamky.features.misc;

import net.minecraft.item.ItemStack;

final public class UseTracker$1 {
   public final String f1;
   public final ItemStack f2;
   public final String f3;
   public final long f4 = System.currentTimeMillis();
   public boolean f5;
   public float f6;

   public UseTracker$1(String var1, ItemStack var2, String var3) {
      this.f1 = var1;
      this.f2 = var2;
      this.f3 = var3;
   }

   public float m329() {
      if (System.currentTimeMillis() - this.f4 > 3000L) {
         this.f5 = true;
      }

      float var1 = this.f5 ? 0.0F : 1.0F;
      this.f6 = this.f6 + (var1 - this.f6) * 0.25F;
      return Math.max(0.0F, Math.min(1.0F, this.f6));
   }
}

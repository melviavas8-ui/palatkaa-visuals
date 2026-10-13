package ua.daamky.utils;

public enum CosmeticAttachPoint {
   f1(-1),
   f2(1),
   f3(3),
   f4(-1),
   f5(-1),
   f6(-1),
   f7(2),
   f8(0);

   private final int f9;

   public static CosmeticAttachPoint[] m575() {
      return values();
   }

   public static CosmeticAttachPoint m576(String var0) {
      return Enum.valueOf(CosmeticAttachPoint.class, var0);
   }

   private CosmeticAttachPoint(int var3) {
      this.f9 = var3;
   }

   public int m189() {
      return this.ordinal();
   }

   public int m559() {
      return this.f9;
   }

   public static CosmeticAttachPoint m577(int var0) {
      return var0 >= 0 && var0 < m575().length ? m575()[var0] : f2;
   }
}

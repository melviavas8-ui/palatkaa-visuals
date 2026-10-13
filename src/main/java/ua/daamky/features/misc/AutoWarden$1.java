package ua.daamky.features.misc;

final public class AutoWarden$1 {
   final long f1;
   final long f2;
   final int f3;

   AutoWarden$1(long var1, long var3, int var5) {
      this.f1 = var1;
      this.f2 = var3;
      this.f3 = var5;
   }

   long m782(long var1) {
      return this.f1 < 0L ? -1L : Math.max(0L, this.f1 - (var1 - this.f2));
   }
}

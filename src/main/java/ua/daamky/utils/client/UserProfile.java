package ua.daamky.utils.client;

import java.nio.file.Path;
import java.nio.file.Paths;

public final class UserProfile {
   private static final Path f1;
   private static long f2;
    private static String f3 = "daamky quantum";
   private static String f4 = "daamky quantum";
   private static String f5 = "daamky quantum";
   private static String f6 = "by melviavas";

   private UserProfile() {
   }

   public static String m37() {
      m23();
      return f3;
   }

   public static String m40() {
      m23();
      return f4;
   }

   public static String m18() {
      m23();
      return f5;
   }

   public static String m30() {
      m23();
      return f6;
   }

   public static String m58(String var0) {
      String var1 = m37();
      return var1 != null && !var1.isEmpty() ? var1 : var0;
   }

   private static void m23() {
   }

   private static String m66(String var0, String var1) {
      String var2 = "\"" + var1 + "\"";
      int var3 = var0.indexOf(var2);
      if (var3 < 0) {
         return null;
      } else {
         var3 = var0.indexOf(58, var3 + var2.length());
         if (var3 < 0) {
            return null;
         } else {
            int var4 = var0.indexOf(34, var3);
            int var5 = var0.indexOf(44, var3);
            int var6 = var0.indexOf(125, var3);
            int var7 = var5 < 0 ? var6 : (var6 < 0 ? var5 : Math.min(var5, var6));
            if (var4 < 0 || var7 >= 0 && var4 >= var7) {
               return var7 < 0 ? null : var0.substring(var3 + 1, var7).trim();
            } else {
               int var8 = var0.indexOf(34, var4 + 1);
               return var8 < 0 ? null : var0.substring(var4 + 1, var8);
            }
         }
      }
   }

   static {
      String var10000 = System.getProperty("user.home");
      String[] var10001 = new String[2];
      var10001[0] = ".daamky";
      var10001[1] = "account.json";
      f1 = Paths.get(var10000, var10001);
   }
}

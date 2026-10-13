package ua.daamky.utils.player;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public final class RaytraceUtil {
   private static final Map<String, String> f1 = new LinkedHashMap<>();

   private RaytraceUtil() {
   }

   public static boolean m20(String var0) {
      return f1.putIfAbsent(m65(var0), var0) == null;
   }

   public static boolean m17(String var0) {
      return f1.remove(m65(var0)) != null;
   }

   public static int m113() {
      int var0 = f1.size();
      f1.clear();
      return var0;
   }

   public static Map<String, String> m394() {
      return new LinkedHashMap<>(f1);
   }

   public static void m395(Map<String, String> var0) {
      f1.clear();
      f1.putAll(var0);
   }

   public static boolean m80(String var0) {
      return var0 != null && f1.containsKey(m65(var0));
   }

   private static String m65(String var0) {
      return var0.toLowerCase(Locale.ROOT);
   }
}

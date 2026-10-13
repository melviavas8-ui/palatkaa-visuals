package ua.daamky.gui.alts;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import net.fabricmc.loader.api.FabricLoader;

public class AltManager {
   private static final List<AltManager$1> f1 = new ArrayList<>();
   private static Path f2;
   private static Path f3;

   public static void m63() {
      f2 = FabricLoader.getInstance().getGameDir().resolve("Daamky");
      f3 = f2.resolve("alts.txt");
      m29();
   }

   public static List<AltManager$1> m369() {
      return new ArrayList<>(f1);
   }

   public static void m16(String var0) {
      if (f1.stream().noneMatch(var1 -> var1.f1.equalsIgnoreCase(var0))) {
         f1.add(new AltManager$1(var0, false));
         m23();
      }
   }

   public static void m21(String var0) {
      if (f1.removeIf(var1 -> var1.f1.equalsIgnoreCase(var0))) {
         m23();
      }
   }

   public static void m60(String var0) {
      for (AltManager$1 var2 : f1) {
         if (var2.f1.equalsIgnoreCase(var0)) {
            var2.f2 = !var2.f2;
            m23();
            break;
         }
      }
   }

   public static void m77(String var0, String var1) {
      for (AltManager$1 var3 : f1) {
         if (var3.f1.equalsIgnoreCase(var0)) {
            var3.f1 = var1;
            m23();
            break;
         }
      }
   }

   private static void m23() {
      f1.sort((var0, var1) -> {
         if (var0.f2 && !var1.f2) {
            return -1;
         } else {
            return !var0.f2 && var1.f2 ? 1 : var0.f1.compareToIgnoreCase(var1.f1);
         }
      });
      m286();
   }

   public static void m29() {
      if (Files.exists(f3)) {
         try {
            List<String> var0 = Files.readAllLines(f3);
            f1.clear();

            for (String var2 : var0) {
               var2 = var2.trim();
               if (!var2.isEmpty()) {
                  if (var2.contains(":")) {
                     String[] var3 = var2.split(":", 2);
                     f1.add(new AltManager$1(var3[0], Boolean.parseBoolean(var3[1])));
                  } else {
                     f1.add(new AltManager$1(var2, false));
                  }
               }
            }

            f1.sort((var0x, var1) -> {
               if (var0x.f2 && !var1.f2) {
                  return -1;
               } else {
                  return !var0x.f2 && var1.f2 ? 1 : var0x.f1.compareToIgnoreCase(var1.f1);
               }
            });
         } catch (IOException var4) {
            var4.printStackTrace();
         }
      }
   }

   public static void m286() {
      try {
         Files.createDirectories(f2);
         ArrayList var0 = new ArrayList();

         for (AltManager$1 var2 : f1) {
            var0.add(var2.f1 + ":" + var2.f2);
         }

         Files.write(f3, var0);
      } catch (IOException var3) {
         var3.printStackTrace();
      }
   }
}

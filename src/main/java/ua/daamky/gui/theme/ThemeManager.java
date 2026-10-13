package ua.daamky.gui.theme;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import net.fabricmc.loader.api.FabricLoader;

public class ThemeManager {
   public static Color f1 = new Color(242, 139, 130);
   public static Color f2 = new Color(138, 180, 248);
   public static boolean f3 = false;
   public static ThemeManager$1 f4 = ThemeManager$1.f1;
   public static final Color f5 = new Color(242, 139, 130);
   public static final Color f6 = new Color(138, 180, 248);
   public static final List<ThemeManager$2> f7 = List.of(
      new ThemeManager$2("Красная", f5),
      new ThemeManager$2("Синяя", f6),
      new ThemeManager$2("Оранжевая", new Color(252, 173, 112)),
      new ThemeManager$2("Жёлтая", new Color(253, 214, 99)),
      new ThemeManager$2("Лаймовая", new Color(203, 238, 123)),
      new ThemeManager$2("Зелёная", new Color(129, 201, 149)),
      new ThemeManager$2("Мятная", new Color(127, 224, 200)),
      new ThemeManager$2("Бирюзовая", new Color(120, 217, 236)),
      new ThemeManager$2("Индиго", new Color(169, 162, 251)),
      new ThemeManager$2("Фиолетовая", new Color(197, 138, 249)),
      new ThemeManager$2("Розовая", new Color(255, 139, 203)),
      new ThemeManager$2("Белая", new Color(232, 234, 237))
   );
   public static final List<Color> f8 = new ArrayList<>();
   private static Path f9;

   private ThemeManager() {
   }

   public static void m63() {
      Path var0 = FabricLoader.getInstance().getGameDir().resolve("Daamky");
      f9 = var0.resolve("themes.properties");

      try {
         Files.createDirectories(var0);
         m286();
      } catch (IOException var2) {
      }
   }

   public static void m8(Color var0) {
      f1 = m1383(var0);
      m29();
   }

   public static void m1356(Color var0) {
      f2 = m1383(var0);
      m29();
   }

   public static Color m1379() {
      if (!f3) {
         return f1;
      } else {
         float var0 = (float)(System.currentTimeMillis() % 2000L) / 2000.0F;
         float var1 = (float)(Math.sin((double)var0 * Math.PI * 2.0) * 0.5 + 0.5);
         int var2 = (int)((float)f1.getRed() + (float)(f2.getRed() - f1.getRed()) * var1);
         int var3 = (int)((float)f1.getGreen() + (float)(f2.getGreen() - f1.getGreen()) * var1);
         int var4 = (int)((float)f1.getBlue() + (float)(f2.getBlue() - f1.getBlue()) * var1);
         return new Color(var2, var3, var4);
      }
   }

   public static int m1380(Color var0) {
      f8.add(m1383(var0));
      m29();
      return f8.size() - 1;
   }

   public static void m1381(int var0, Color var1) {
      if (var0 >= 0 && var0 < f8.size()) {
         f8.set(var0, m1383(var1));
         m29();
      }
   }

   public static void m1004(int var0) {
      if (var0 >= 0 && var0 < f8.size()) {
         Color var1 = f8.remove(var0);
         if (m1370(var1, f1)) {
            f1 = f8.isEmpty() ? f5 : f8.get(Math.min(var0, f8.size() - 1));
         }

         m29();
      }
   }

   public static void m29() {
      if (f9 != null) {
         Properties var0 = new Properties();
         var0.setProperty("selected", m1382(f1));
         var0.setProperty("selected2", m1382(f2));
         var0.setProperty("twoColors", String.valueOf(f3));
         var0.setProperty("guiStyle", f4.name());
         var0.setProperty("custom.count", String.valueOf(f8.size()));

         for (int var1 = 0; var1 < f8.size(); var1++) {
            var0.setProperty("custom." + var1, m1382(f8.get(var1)));
         }

         try (BufferedWriter var7 = Files.newBufferedWriter(f9)) {
            var0.store(var7, "Daamky themes");
         } catch (IOException var6) {
         }
      }
   }

   private static void m286() throws IOException {
      if (!Files.exists(f9)) {
         m29();
      } else {
         Properties var0 = new Properties();

         try (BufferedReader var1 = Files.newBufferedReader(f9)) {
            var0.load(var1);
         }

         f1 = m387(var0.getProperty("selected"), f5);
         f2 = m387(var0.getProperty("selected2"), f6);
         f3 = Boolean.parseBoolean(var0.getProperty("twoColors", "false"));

         try {
            f4 = ThemeManager$1.m1377(var0.getProperty("guiStyle", "NORMAL"));
         } catch (IllegalArgumentException var5) {
            f4 = ThemeManager$1.f1;
         }

         f8.clear();
         int var7 = m388(var0.getProperty("custom.count"), 0);

         for (int var2 = 0; var2 < var7; var2++) {
            f8.add(m387(var0.getProperty("custom." + var2), f5));
         }
      }
   }

   private static Color m387(String var0, Color var1) {
      if (var0 != null && var0.length() == 7 && var0.charAt(0) == '#') {
         try {
            return new Color(Integer.parseInt(var0.substring(1), 16));
         } catch (NumberFormatException var3) {
            return var1;
         }
      } else {
         return var1;
      }
   }

   private static int m388(String var0, int var1) {
      try {
         return Integer.parseInt(var0);
      } catch (NumberFormatException var3) {
         return var1;
      }
   }

   private static String m1382(Color var0) {
      return String.format("#%02X%02X%02X", var0.getRed(), var0.getGreen(), var0.getBlue());
   }

   private static Color m1383(Color var0) {
      return new Color(var0.getRed(), var0.getGreen(), var0.getBlue());
   }

   private static boolean m1370(Color var0, Color var1) {
      return var0.getRed() == var1.getRed() && var0.getGreen() == var1.getGreen() && var0.getBlue() == var1.getBlue();
   }
}

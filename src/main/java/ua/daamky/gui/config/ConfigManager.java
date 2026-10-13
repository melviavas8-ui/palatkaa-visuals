package ua.daamky.gui.config;

import ua.daamky.features.render.Interface;
import ua.daamky.gui.theme.ThemeManager;
import ua.daamky.settings.BindSetting;
import ua.daamky.settings.BooleanSetting;
import ua.daamky.settings.ColorSetting;
import ua.daamky.settings.KeybindSetting;
import ua.daamky.settings.ModeSettingBase;
import ua.daamky.settings.MultiChoiceSettingBase;
import ua.daamky.settings.NumberSetting;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.ModuleManager;
import ua.daamky.utils.player.RaytraceUtil;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Stream;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Util;

public final class ConfigManager {
   private static final String f1 = "config.properties";
   private static final Properties f2 = new Properties();
   private static Path f3;
   private static Path f4;

   private ConfigManager() {
   }

   public static void m63() {
      f3 = FabricLoader.getInstance().getGameDir().resolve("Daamky");
      f4 = f3.resolve("config.properties");
      m308();
      m6();
   }

   public static boolean m81() {
      return m20(null);
   }

   public static boolean m20(String var0) {
      m293();
      Properties var1 = new Properties();
      m376(var1);

      try {
         Files.createDirectories(f3);

         try (BufferedWriter var2 = Files.newBufferedWriter(m380(var0))) {
            var1.store(var2, "Daamky config");
         }

         ThemeManager.m29();
         return true;
      } catch (IOException var7) {
         return false;
      }
   }

   public static boolean m6() {
      return m17(null);
   }

   public static boolean m17(String var0) {
      m293();
      Path var1 = m380(var0);
      if (!Files.exists(var1)) {
         if (var0 == null) {
            m81();
         }

         return false;
      } else {
         Properties var2 = new Properties();

         try {
            boolean var4;
            try (BufferedReader var3 = Files.newBufferedReader(var1)) {
               var2.load(var3);
               m377(var2);
               var4 = true;
            }

            return var4;
         } catch (IOException var8) {
            return false;
         }
      }
   }

   public static void m286() {
      m293();
      m377(f2);
      m81();
   }

   public static boolean m31() {
      m293();

      try {
         Files.createDirectories(f3);
         Util.getOperatingSystem().open(f3);
         return true;
      } catch (Exception var1) {
         return false;
      }
   }

   public static boolean m80(String var0) {
      return var0 != null && var0.matches("[A-Za-z0-9_-]{1,32}");
   }

   public static boolean m374(String var0) {
      m293();
      if (!m80(var0)) {
         return false;
      } else {
         try {
            return Files.deleteIfExists(m380(var0));
         } catch (IOException var2) {
            return false;
         }
      }
   }

   public static List<String> m375() {
      m293();

      try {
         Files.createDirectories(f3);

         List<String> var1;
         try (Stream<Path> var0 = Files.list(f3)) {
            var1 = var0.filter(var0x -> Files.isRegularFile(var0x) && var0x.getFileName().toString().endsWith(".properties"))
               .map(var0x -> var0x.getFileName().toString())
               .filter(var0x -> !"themes.properties".equalsIgnoreCase(var0x))
               .map(var0x -> var0x.substring(0, var0x.length() - ".properties".length()))
               .filter(ConfigManager::m80)
               .sorted(String.CASE_INSENSITIVE_ORDER)
               .toList();
         }

         return var1;
      } catch (IOException var5) {
         return List.of();
      }
   }

   private static void m308() {
      f2.clear();
      m376(f2);
      f2.setProperty("theme.selected", m386(ThemeManager.f5));
      f2.setProperty("theme.selected2", m386(ThemeManager.f6));
      f2.setProperty("theme.twoColors", "false");
      f2.setProperty("theme.custom.count", "0");
      f2.setProperty("friends.count", "0");
   }

   private static void m376(Properties var0) {
      var0.setProperty("theme.selected", m386(ThemeManager.f1));
      var0.setProperty("theme.selected2", m386(ThemeManager.f2));
      var0.setProperty("theme.twoColors", String.valueOf(ThemeManager.f3));
      var0.setProperty("theme.custom.count", String.valueOf(ThemeManager.f8.size()));

      for (int var1 = 0; var1 < ThemeManager.f8.size(); var1++) {
         var0.setProperty("theme.custom." + var1, m386(ThemeManager.f8.get(var1)));
      }

      Map<String, String> var9 = RaytraceUtil.m394();
      var0.setProperty("friends.count", String.valueOf(var9.size()));
      int var2 = 0;

      for (String var4 : var9.values()) {
         var0.setProperty("friends." + var2, var4);
         var2++;
      }

      for (Module var11 : ModuleManager.getModules()) {
         String var5 = m381(var11);
         var0.setProperty(var5 + ".enabled", String.valueOf(var11.isEnabled()));
         var0.setProperty(var5 + ".keybind", String.valueOf(var11.getKey()));

         for (Setting var7 : var11.getSettings()) {
            String var8 = var5 + ".setting." + m382(var7);
            m378(var0, var8, var7);
         }
      }

      if (Interface.f1 != null) {
         Interface.f1.m376(var0);
      }
   }

   private static void m377(Properties var0) {
      ThemeManager.m8(m387(var0.getProperty("theme.selected"), ThemeManager.f5));
      ThemeManager.m1356(m387(var0.getProperty("theme.selected2"), ThemeManager.f6));
      ThemeManager.f3 = Boolean.parseBoolean(var0.getProperty("theme.twoColors", "false"));
      ThemeManager.f8.clear();
      int var1 = m388(var0.getProperty("theme.custom.count"), 0);

      for (int var2 = 0; var2 < var1; var2++) {
         ThemeManager.f8.add(m387(var0.getProperty("theme.custom." + var2), ThemeManager.f5));
      }

      ThemeManager.m29();
      int var10 = m388(var0.getProperty("friends.count"), 0);
      LinkedHashMap var3 = new LinkedHashMap();

      for (int var4 = 0; var4 < var10; var4++) {
         String var5 = var0.getProperty("friends." + var4);
         if (var5 != null && var5.matches("[A-Za-z0-9_]{1,16}")) {
            var3.put(var5.toLowerCase(), var5);
         }
      }

      RaytraceUtil.m395(var3);

      for (Module var12 : ModuleManager.getModules()) {
         String var6 = m381(var12);
         var12.setKey(m388(var0.getProperty(var6 + ".keybind"), 0));

         for (Setting var8 : var12.getSettings()) {
            String var9 = var6 + ".setting." + m382(var8);
            m379(var0, var9, var8);
         }

         boolean var13 = Boolean.parseBoolean(var0.getProperty(var6 + ".enabled", "false"));
         if (var12.isEnabled() != var13) {
            var12.toggle();
         }
      }

      if (Interface.f1 != null) {
         Interface.f1.m377(var0);
      }
   }

   private static void m378(Properties var0, String var1, Setting var2) {
      if (var2 instanceof BooleanSetting var3) {
         var0.setProperty(var1, String.valueOf(var3.m6()));
      } else if (var2 instanceof NumberSetting var4) {
         var0.setProperty(var1, String.valueOf(var4.getValue()));
      } else if (var2 instanceof ModeSettingBase var5) {
         var0.setProperty(var1, var5.m18());
      } else if (var2 instanceof MultiChoiceSettingBase var6) {
         var0.setProperty(var1, String.join(",", var6.m24()));
      } else if (var2 instanceof KeybindSetting var7) {
         var0.setProperty(var1, String.valueOf(var7.getKey()));
      } else if (var2 instanceof ColorSetting var8) {
         var0.setProperty(var1, m386(var8.m7()));
      } else if (var2 instanceof BindSetting var9) {
         var0.setProperty(var1, var9.m30());
      }
   }

   private static void m379(Properties var0, String var1, Setting var2) {
      String var3 = var0.getProperty(var1);
      if (var3 == null) {
         var3 = var0.getProperty(m384(var1));
      }

      if (var3 == null && var2 instanceof ModeSettingBase && var1.endsWith(".setting.Стиль_HUD")) {
         var3 = m385(var0, var1);
      }

      if (var3 != null) {
         if (var2 instanceof BooleanSetting var4) {
            var4.m4(Boolean.parseBoolean(var3));
         } else if (var2 instanceof NumberSetting var5) {
            var5.setValue(m389(var3, var5.getValue()));
         } else if (var2 instanceof ModeSettingBase var6) {
            var6.m16(var3);
         } else if (var2 instanceof MultiChoiceSettingBase var7) {
            var7.m22(var3.isEmpty() ? Set.of() : Set.of(var3.split(",")));
         } else if (var2 instanceof KeybindSetting var8) {
            var8.m15(m388(var3, var8.getKey()));
         } else if (var2 instanceof ColorSetting var9) {
            var9.m8(m387(var3, var9.m7()));
         } else if (var2 instanceof BindSetting var10) {
            var10.m16(var3);
         }
      }
   }

   private static void m293() {
      if (f3 == null || f4 == null) {
         f3 = FabricLoader.getInstance().getGameDir().resolve("Daamky");
         f4 = f3.resolve("config.properties");
      }
   }

   private static Path m380(String var0) {
      return var0 == null ? f4 : f3.resolve(var0 + ".properties");
   }

   private static String m381(Module var0) {
      return "function." + m383(var0.getName());
   }

   private static String m382(Setting var0) {
      return m383(var0.getName());
   }

   private static String m383(String var0) {
      return var0.replace(".", "_")
         .replace(" ", "_");
   }

   private static String m384(String var0) {
      return var0.replace("Watermark_Лёд", "Watermark_LiquidGlass")
         .replace("Keybinds_Лёд", "Keybinds_LiquidGlass")
         .replace("Potions_Лёд", "Potions_LiquidGlass")
         .replace("TargetHUD_Лёд", "TargetHUD_LiquidGlass")
         .replace("Hotbar_Лёд", "Hotbar_LiquidGlass");
   }

   private static String m385(Properties var0, String var1) {
      String var2 = var1.substring(0, var1.length() - ".setting.Стиль_HUD".length());
      String var3 = var2 + ".setting.";
      if (!Boolean.parseBoolean(var0.getProperty(var3 + "Watermark_Жидкое_стекло"))
         && !Boolean.parseBoolean(var0.getProperty(var3 + "Keybinds_Жидкое_стекло"))
         && !Boolean.parseBoolean(var0.getProperty(var3 + "Potions_Жидкое_стекло"))
         && !Boolean.parseBoolean(var0.getProperty(var3 + "TargetHUD_Жидкое_стекло"))
         && !Boolean.parseBoolean(var0.getProperty(var3 + "Hotbar_Жидкое_стекло"))) {
         return !Boolean.parseBoolean(var0.getProperty(var3 + "Watermark_LiquidGlass"))
               && !Boolean.parseBoolean(var0.getProperty(var3 + "Keybinds_LiquidGlass"))
               && !Boolean.parseBoolean(var0.getProperty(var3 + "Potions_LiquidGlass"))
               && !Boolean.parseBoolean(var0.getProperty(var3 + "TargetHUD_LiquidGlass"))
               && !Boolean.parseBoolean(var0.getProperty(var3 + "Hotbar_LiquidGlass"))
               && !"LiquidGlass Watermark".equals(var0.getProperty(var3 + "Watermark_Style"))
            ? null
            : "Лёд";
      } else {
         return "Жидкое стекло";
      }
   }

   private static String m386(Color var0) {
      return String.format("#%02X%02X%02X", var0.getRed(), var0.getGreen(), var0.getBlue());
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

   private static double m389(String var0, double var1) {
      try {
         return Double.parseDouble(var0);
      } catch (NumberFormatException var4) {
         return var1;
      }
   }
}

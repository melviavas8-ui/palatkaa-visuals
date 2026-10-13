package ua.daamky.utils;

import ua.daamky.gui.Daamky_2;
import ua.daamky.gui.config.ConfigManager;
import java.util.List;
import java.util.Locale;
import net.minecraft.util.Formatting;

public final class ConfigCommand {
   public static final String f1 = "cfg";

   private ConfigCommand() {
   }

   public static boolean m20(String var0) {
      String var1 = var0.trim();
      if (!m80(var1)) {
         return false;
      } else {
         String[] var2 = var1.split("\\s+");
         if (var2.length >= 2 && var2.length <= 3) {
            String var3 = var2[1].toLowerCase(Locale.ROOT);
            switch (var3) {
               case "dir":
                  if (var2.length != 2) {
                     m29();
                  } else {
                     m63();
                  }
                  break;
               case "load":
                  m427(var2);
                  break;
               case "save":
                  m429(var2);
                  break;
               case "reset":
                  if (var2.length != 2) {
                     m29();
                  } else {
                     m314();
                  }
                  break;
               default:
                  m29();
            }

            return true;
         } else {
            m29();
            return true;
         }
      }
   }

   public static List<String> m428(String var0) {
      String var1 = var0.stripLeading().toLowerCase(Locale.ROOT);
      if (!".".equals(var1)
         && (!var1.startsWith(".") || !".cfg".startsWith(var1))) {
         if (!var1.startsWith(".cfg ")) {
            return List.of();
         } else {
            String var2 = var1.substring(".cfg ".length());
            return var2.contains(" ")
               ? List.of()
               : List.of(
                     ".cfg dir",
                     ".cfg load",
                     ".cfg save",
                     ".cfg reset"
                  )
                  .stream()
                  .filter(var1x -> var1x.substring(".cfg ".length()).toLowerCase(Locale.ROOT).startsWith(var2))
                  .toList();
         }
      } else {
         return List.of(".cfg");
      }
   }

   private static boolean m80(String var0) {
      String var1 = var0.toLowerCase(Locale.ROOT);
      return var1.equals(".cfg") || var1.startsWith(".cfg ");
   }

   private static void m63() {
      if (ConfigManager.m31()) {
         Daamky_2.m467("Config folder opened.", Formatting.GREEN);
      } else {
         Daamky_2.m467("Could not open config folder.", Formatting.RED);
      }
   }

   private static void m427(String[] var0) {
      if (var0.length == 3 && !ConfigManager.m80(var0[2])) {
         Daamky_2.m467("Invalid config name. Use letters, numbers, _ or -.", Formatting.YELLOW);
      } else {
         String var1 = var0.length == 3 ? var0[2] : null;
         if (ConfigManager.m17(var1)) {
            Daamky_2.m467(m65(var1) + " loaded.", Formatting.GREEN);
         } else {
            Daamky_2.m467(m65(var1) + " was not found or could not be loaded.", Formatting.RED);
         }
      }
   }

   private static void m429(String[] var0) {
      if (var0.length == 3 && !ConfigManager.m80(var0[2])) {
         Daamky_2.m467("Invalid config name. Use letters, numbers, _ or -.", Formatting.YELLOW);
      } else {
         String var1 = var0.length == 3 ? var0[2] : null;
         if (ConfigManager.m20(var1)) {
            Daamky_2.m467(m65(var1) + " saved.", Formatting.GREEN);
         } else {
            Daamky_2.m467("Could not save " + m65(var1).toLowerCase(Locale.ROOT) + ".", Formatting.RED);
         }
      }
   }

   private static void m314() {
      ConfigManager.m286();
      Daamky_2.m467("Settings reset.", Formatting.GREEN);
   }

   private static void m29() {
      Daamky_2.m467(".cfg dir - open config folder", Formatting.GRAY);
      Daamky_2.m467(".cfg load [name] - load config", Formatting.GRAY);
      Daamky_2.m467(".cfg save [name] - save config", Formatting.GRAY);
      Daamky_2.m467(".cfg reset - reset all settings", Formatting.GRAY);
   }

   private static String m65(String var0) {
      return var0 == null ? "Config" : "Config '" + var0 + "'";
   }
}

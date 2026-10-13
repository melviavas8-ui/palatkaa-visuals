package ua.daamky.utils;

import ua.daamky.mixins.interfaces.IBossBarHud;
import ua.daamky.mixins.interfaces.IPlayerListHud;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ClientBossBar;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.Item.TooltipContext;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;

public final class FunTimeUtil {
   private static final MinecraftClient f1 = MinecraftClient.getInstance();
   private static final Pattern f2 = Pattern.compile("(\\d+):(\\d+)");

   private FunTimeUtil() {
   }

   public static String m37() {
      if (f1.inGameHud == null) {
         return "";
      } else {
         PlayerListHud var0 = f1.inGameHud.getPlayerListHud();
         if (var0 == null) {
            return "";
         } else {
            Text var1 = ((IPlayerListHud)var0).getHeader();
            return var1 == null ? "" : var1.getString();
         }
      }
   }

   public static String m40() {
      return f1.getCurrentServerEntry() != null && f1.getCurrentServerEntry().address != null ? f1.getCurrentServerEntry().address : "";
   }

   private static boolean m17(String var0) {
      return m37().toLowerCase(Locale.ROOT).contains(var0) || m40().toLowerCase(Locale.ROOT).contains(var0);
   }

   public static boolean m6() {
      return m17("funtime");
   }

   public static boolean m101() {
      return m17("spookytime");
   }

   public static boolean m31() {
      return m17("holyworld");
   }

   public static boolean m41() {
      return m17("reallyworld");
   }

   public static int m102() {
      String var0 = m37();
      if (var0 != null && var0.contains("Анархия-")) {
         try {
            return Integer.parseInt(var0.split("Анархия-")[1].trim().split("\\s")[0]);
         } catch (Exception var2) {
            return -1;
         }
      } else {
         return -1;
      }
   }

   public static int m103() {
      String var0 = m37().trim().replaceAll("(?s).*\\n", "");
      if (var0.contains("Лайт") && var0.contains("#")) {
         try {
            return Integer.parseInt(var0.replaceAll(".*#(\\d+).*", "$1"));
         } catch (Exception var2) {
         }
      }

      return -1;
   }

   public static boolean m104() {
      if (f1.inGameHud == null) {
         return false;
      } else {
         for (ClientBossBar var1 : ((IBossBarHud)f1.inGameHud.getBossBarHud()).getBossBars().values()) {
            String var2 = var1.getName().getString().toLowerCase(Locale.ROOT);
            if (var2.contains("pvp")
               || var2.contains("пвп")
               || var2.contains("дуэль")) {
               return true;
            }
         }

         return false;
      }
   }

   public static int m105(ItemStack var0) {
      if (var0 != null && !var0.isEmpty() && f1.player != null) {
         if (!var0.getName().getString().contains("Товар не актуален") && !var0.isOf(Items.GRAY_DYE)) {
            List var1 = var0.getTooltip(TooltipContext.DEFAULT, f1.player, TooltipType.BASIC);

            for (int var2 = 1; var2 < var1.size(); var2++) {
               String var3 = ((Text)var1.get(var2)).getString();
               int var4 = var3.indexOf("$ Цена: ");
               int var5 = "$ Цена: ".length();
               if (var4 < 0) {
                  var4 = var3.indexOf("$ Ценa: ");
               }

               if (var4 >= 0) {
                  String var6 = var3.substring(var4 + var5)
                     .trim()
                     .replace(",", "")
                     .replace("$", "")
                     .replaceAll("\\s+", "");
                  if (var6.isEmpty()) {
                     return -1;
                  }

                  try {
                     int var7 = Integer.parseInt(var6);
                     return var7 / Math.max(var0.getCount(), 1);
                  } catch (NumberFormatException var8) {
                     return -1;
                  }
               }
            }

            return -1;
         } else {
            return -1;
         }
      } else {
         return -1;
      }
   }

   public static int m106(String var0) {
      if (var0 != null && !var0.isBlank()) {
         String var1 = var0.replaceAll("§.", "")
            .toLowerCase()
            .replaceAll("\\s+", "")
            .trim();
         String[] var2 = new String[]{
            "i",
            "ii",
            "iii",
            "iv",
            "v",
            "vi",
            "vii",
            "viii",
            "ix",
            "x"
         };

         for (int var3 = var2.length - 1; var3 >= 0; var3--) {
            if (var1.contains(var2[var3])) {
               return var3 + 1;
            }
         }

         String var6 = var1.replaceAll("[^0-9]", "");
         if (!var6.isEmpty()) {
            try {
               int var4 = Integer.parseInt(var6);
               if (var4 >= 1 && var4 <= 10) {
                  return var4;
               }
            } catch (NumberFormatException var5) {
            }
         }

         return 0;
      } else {
         return 0;
      }
   }

   public static int m107() {
      if (f1.inGameHud == null) {
         return -1;
      } else {
         for (ClientBossBar var1 : ((IBossBarHud)f1.inGameHud.getBossBarHud()).getBossBars().values()) {
            String var2 = var1.getName().getString().toLowerCase(Locale.ROOT);
            if (var2.contains("pvp") || var2.contains("пвп")) {
               Matcher var3 = f2.matcher(var2);
               if (var3.find()) {
                  return Integer.parseInt(var3.group(1)) * 60 + Integer.parseInt(var3.group(2));
               }

               Matcher var4 = Pattern.compile("(\\d+)").matcher(var2);
               if (var4.find()) {
                  return Integer.parseInt(var4.group(1));
               }
            }
         }

         return -1;
      }
   }
}

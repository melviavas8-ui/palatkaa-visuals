package ua.daamky.utils;

import ua.daamky.gui.Daamky_2;
import ua.daamky.utils.player.RaytraceUtil;
import java.util.List;
import java.util.Locale;
import net.minecraft.util.Formatting;

public final class FriendCommand {
   public static final String f1 = ".";
   public static final String f2 = "friend";

   private FriendCommand() {
   }

   public static boolean m20(String var0) {
      String var1 = var0.trim();
      if (!m80(var1)) {
         return false;
      } else {
         String[] var2 = var1.split("\\s+");
         if (var2.length == 1) {
            m63();
            return true;
         } else {
            String var3 = var2[1].toLowerCase(Locale.ROOT);
            switch (var3) {
               case "add":
                  m427(var2);
                  break;
               case "remove":
                  m429(var2);
                  break;
               case "clear":
                  m430(var2);
                  break;
               default:
                  m63();
            }

            return true;
         }
      }
   }

   public static List<String> m428(String var0) {
      String var1 = var0.stripLeading().toLowerCase(Locale.ROOT);
      if (!".".equals(var1)
         && (!var1.startsWith(".") || !".friend".startsWith(var1))) {
         if (!var1.startsWith(".friend ")) {
            return List.of();
         } else {
            String var2 = var1.substring(".friend ".length());
            return var2.contains(" ")
               ? List.of()
               : List.of(
                     ".friend add <nick>",
                     ".friend remove <nick>",
                     ".friend clear"
                  )
                  .stream()
                  .filter(var1x -> var1x.substring(".friend ".length()).toLowerCase(Locale.ROOT).startsWith(var2))
                  .toList();
         }
      } else {
         return List.of(".friend");
      }
   }

   private static boolean m80(String var0) {
      String var1 = var0.toLowerCase(Locale.ROOT);
      return var1.equals(".friend") || var1.startsWith(".friend ");
   }

   private static void m427(String[] var0) {
      if (var0.length == 3 && m374(var0[2])) {
         if (RaytraceUtil.m20(var0[2])) {
            m467(var0[2] + " added to friends.", Formatting.GREEN);
         } else {
            m467(var0[2] + " is already in friends.", Formatting.YELLOW);
         }
      } else {
         m467("Usage: .friend add <nick>", Formatting.YELLOW);
      }
   }

   private static void m429(String[] var0) {
      if (var0.length == 3 && m374(var0[2])) {
         if (RaytraceUtil.m17(var0[2])) {
            m467(var0[2] + " removed from friends.", Formatting.GREEN);
         } else {
            m467(var0[2] + " was not found in friends.", Formatting.YELLOW);
         }
      } else {
         m467("Usage: .friend remove <nick>", Formatting.YELLOW);
      }
   }

   private static void m430(String[] var0) {
      if (var0.length != 2) {
         m467("Usage: .friend clear", Formatting.YELLOW);
      } else {
         int var1 = RaytraceUtil.m113();
         m467("Friends list cleared (" + var1 + ").", Formatting.GREEN);
      }
   }

   private static boolean m374(String var0) {
      return var0.matches("[A-Za-z0-9_]{1,16}");
   }

   private static void m63() {
      m467(".friend add <nick> - add friend", Formatting.GRAY);
      m467(".friend remove <nick> - remove friend", Formatting.GRAY);
      m467(".friend clear - clear friends list", Formatting.GRAY);
   }

   private static void m467(String var0, Formatting var1) {
      Daamky_2.m467(var0, var1);
   }
}

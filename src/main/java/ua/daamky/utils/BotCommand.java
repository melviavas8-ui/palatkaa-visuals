package ua.daamky.utils;

import ua.daamky.gui.Daamky_2;
import java.util.List;
import java.util.Locale;
import net.minecraft.util.Formatting;

public final class BotCommand {
   public static final String f1 = "bot";

   private BotCommand() {
   }

   public static boolean m20(String var0) {
      String var1 = var0.trim();
      if (!m80(var1)) {
         return false;
      } else {
         String[] var2 = var1.split("\\s+");
         if (var2.length < 2) {
            m63();
            return true;
         } else {
            String var3 = var2[1].toLowerCase(Locale.ROOT);
            switch (var3) {
               case "connect":
                  m427(var2);
                  break;
               case "remove":
                  m429(var2);
                  break;
               case "return":
                  m433(var2);
                  break;
               case "control":
                  m430(var2);
                  break;
               case "say":
                  m431(var2);
                  break;
               case "sayall":
                  m432(var2);
                  break;
               case "list":
                  m434(var2);
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
         && (!var1.startsWith(".") || !".bot".startsWith(var1))) {
         if (!var1.startsWith(".bot ")) {
            return List.of();
         } else {
            String var2 = var1.substring(".bot ".length());
            if (var2.contains(" ")
               && !var2.toLowerCase(Locale.ROOT).startsWith("say ")
               && !var2.toLowerCase(Locale.ROOT).startsWith("sayall ")
               && !var2.toLowerCase(Locale.ROOT).startsWith("connect ")) {
               return List.of();
            } else {
               return !var2.contains(" ")
                  ? List.of(
                        ".bot connect <name> <ip>",
                        ".bot remove <name>",
                        ".bot return",
                        ".bot control <name>",
                        ".bot say <name> <msg>",
                        ".bot sayall <msg>",
                        ".bot list"
                     )
                     .stream()
                     .filter(var1x -> var1x.substring(".bot ".length()).toLowerCase(Locale.ROOT).startsWith(var2))
                     .toList()
                  : List.of();
            }
         }
      } else {
         return List.of(".bot");
      }
   }

   private static void m427(String[] var0) {
      if (var0.length != 4) {
         Daamky_2.m467("Usage: .bot connect <name> <ip>", Formatting.YELLOW);
      } else {
         String var1 = var0[2];
         String var2 = var0[3];
         BotManager.m77(var1, var2);
         Daamky_2.m467("Connected: " + var1 + " -> " + var2 + " (Previous session frozen)", Formatting.GREEN);
      }
   }

   private static void m429(String[] var0) {
      if (var0.length != 3) {
         Daamky_2.m467("Usage: .bot remove <name>", Formatting.YELLOW);
      } else {
         String var1 = var0[2];
         if (BotManager.m80(var1)) {
            Daamky_2.m467("Bot disconnected and removed: " + var1, Formatting.GREEN);
         } else {
            Daamky_2.m467("Bot not found: " + var1, Formatting.RED);
         }
      }
   }

   private static void m430(String[] var0) {
      if (var0.length != 3) {
         Daamky_2.m467("Usage: .bot control <name>", Formatting.YELLOW);
      } else {
         String var1 = var0[2];
         if (BotManager.m17(var1)) {
            Daamky_2.m467("Switched to bot: " + var1, Formatting.GREEN);
         } else {
            Daamky_2.m467("Bot not found: " + var1, Formatting.RED);
         }
      }
   }

   private static void m431(String[] var0) {
      if (var0.length < 4) {
         Daamky_2.m467("Usage: .bot say <name> <message>", Formatting.YELLOW);
      } else {
         String var1 = var0[2];
         StringBuilder var2 = new StringBuilder();

         for (int var3 = 3; var3 < var0.length; var3++) {
            var2.append(var0[var3]).append(" ");
         }

         if (BotManager.m79(var1, var2.toString().trim())) {
            Daamky_2.m467("Message from " + var1 + " sent.", Formatting.GREEN);
         } else {
            Daamky_2.m467("Bot not found: " + var1, Formatting.RED);
         }
      }
   }

   private static void m432(String[] var0) {
      if (var0.length < 3) {
         Daamky_2.m467("Usage: .bot sayall <message>", Formatting.YELLOW);
      } else {
         StringBuilder var1 = new StringBuilder();

         for (int var2 = 2; var2 < var0.length; var2++) {
            var1.append(var0[var2]).append(" ");
         }

         BotManager.m16(var1.toString().trim());
         Daamky_2.m467("Message sent from all bots.", Formatting.GREEN);
      }
   }

   private static void m433(String[] var0) {
      if (BotManager.m81()) {
         Daamky_2.m467("Returned to previous session", Formatting.GREEN);
      } else {
         Daamky_2.m467("No saved session to return to", Formatting.RED);
      }
   }

   private static void m434(String[] var0) {
      List<BotSession> var1 = BotManager.m76();
      if (var1.isEmpty()) {
         Daamky_2.m467("Bot list is empty", Formatting.YELLOW);
      } else {
         Daamky_2.m467("Connected bots:", Formatting.AQUA);

         for (BotSession var3 : var1) {
            Daamky_2.m467("- " + var3.m37() + " @ " + var3.m40(), Formatting.AQUA);
         }
      }
   }

   private static boolean m80(String var0) {
      String var1 = var0.toLowerCase(Locale.ROOT);
      return var1.equals(".bot") || var1.startsWith(".bot ");
   }

   private static void m63() {
      Daamky_2.m467(".bot connect <name> <ip> - connect a bot", Formatting.GRAY);
      Daamky_2.m467(".bot control <name> - switch to bot", Formatting.GRAY);
      Daamky_2.m467(".bot say <name> <message> - send chat as bot", Formatting.GRAY);
      Daamky_2.m467(".bot sayall <message> - send chat as all bots", Formatting.GRAY);
      Daamky_2.m467(".bot remove <name> - disconnect bot", Formatting.GRAY);
      Daamky_2.m467(".bot return - return to previous session", Formatting.GRAY);
      Daamky_2.m467(".bot list - list connected bots", Formatting.GRAY);
   }
}

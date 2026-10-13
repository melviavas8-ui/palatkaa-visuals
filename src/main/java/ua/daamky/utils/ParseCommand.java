package ua.daamky.utils;

import ua.daamky.gui.Daamky_2;
import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.scoreboard.Team;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public final class ParseCommand {
   private static final MinecraftClient f1 = MinecraftClient.getInstance();

   private ParseCommand() {
   }

   public static void m63() {
      if (f1.player == null) {
         Daamky_2.m467("Вы не подключены к серверу.", Formatting.RED);
      } else {
         ClientPlayNetworkHandler var0 = f1.getNetworkHandler();
         if (var0 == null) {
            Daamky_2.m467("Список игроков недоступен.", Formatting.RED);
         } else {
            File var1 = new File(f1.runDirectory, "files/parser");
            if (!var1.exists() && !var1.mkdirs()) {
               Daamky_2.m467("Не удалось создать папку parser.", Formatting.RED);
            } else {
               Collection<PlayerListEntry> var2 = var0.getListedPlayerListEntries();
               File var3 = new File(var1, m40());

               try (FileWriter var4 = new FileWriter(var3, StandardCharsets.UTF_8)) {
                  for (PlayerListEntry var6 : var2) {
                     Team var7 = var6.getScoreboardTeam();
                     Text var8 = var7 == null ? Text.empty() : var7.getPrefix();
                     Text var9 = var7 == null ? Text.empty() : var7.getSuffix();
                     var4.write(var8.getString());
                     var4.write(var6.getProfile().name());
                     var4.write(var9.getString());
                     var4.write(System.lineSeparator());
                  }

                  Daamky_2.m467("Сохранено игроков: " + var2.size() + " -> " + var3.getName(), Formatting.GREEN);
               } catch (Exception var12) {
                  Daamky_2.m467("Ошибка сохранения parse: " + var12.getMessage(), Formatting.RED);
               }
            }
         }
      }
   }

   private static String m40() {
      String var0 = "local";
      if (f1.getCurrentServerEntry() != null && f1.getCurrentServerEntry().address != null) {
         var0 = f1.getCurrentServerEntry().address;
      }

      var0 = var0.replaceAll("[^A-Za-z0-9._-]", "_");
      String var1 = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
      return var0 + "_" + var1 + ".txt";
   }
}

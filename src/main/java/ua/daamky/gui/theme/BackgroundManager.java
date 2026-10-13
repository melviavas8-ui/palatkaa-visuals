package ua.daamky.gui.theme;

import java.nio.file.Files;
import java.nio.file.Path;
import net.minecraft.client.MinecraftClient;

public final class BackgroundManager {
   public static final String[] f1 = new String[]{
      "Обычный",
      "Космос",
      "Небула",
      "Закат",
      "Интерференция",
      "Волны",
      "Плазма",
      "Энергия"
   };
   private static int f2 = m1385();

   private BackgroundManager() {
   }

   public static int m113() {
      return f2;
   }

   public static String m40() {
      return f1[f2];
   }

   public static void m29() {
      f2 = (f2 + 1) % f1.length;
      m299();
   }

   private static Path m1384() {
      return MinecraftClient.getInstance()
         .runDirectory
         .toPath()
         .resolve("Daamky")
         .resolve("titlebg.txt");
   }

   private static int m1385() {
      try {
         int var0 = Integer.parseInt(Files.readString(m1384()).trim());
         if (var0 >= 0 && var0 < f1.length) {
            return var0;
         }
      } catch (Exception var1) {
      }

      return 0;
   }

   private static void m299() {
      try {
         Path var0 = m1384();
         Files.createDirectories(var0.getParent());
         Files.writeString(var0, Integer.toString(f2));
      } catch (Exception var1) {
      }
   }
}

package ua.daamky.gui;

import ua.daamky.gui.theme.ThemeManager;
import java.awt.Color;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;

public final class Daamky_2 {
   private static final String f1 = "Daamky";

   private Daamky_2() {
   }

   public static void m467(String var0, Formatting var1) {
      MinecraftClient var2 = MinecraftClient.getInstance();
      if (var2.inGameHud != null) {
         var2.inGameHud
            .getChatHud()
            .addMessage(
               Text.literal("[")
                  .formatted(Formatting.DARK_GRAY)
                  .append(m468())
                  .append(Text.literal("] ").formatted(Formatting.DARK_GRAY))
                  .append(Text.literal(var0).formatted(var1))
            );
      }
   }

   private static MutableText m468() {
      MutableText var0 = Text.empty();
      Color var1 = ThemeManager.m1379();
      Color var2 = Color.WHITE;

      for (int var3 = 0; var3 < "Daamky".length(); var3++) {
         float var4 = "Daamky".length() == 1
            ? 1.0F
            : (float)var3 / (float)("Daamky".length() - 1);
         int var5 = m260(var1.getRed(), var2.getRed(), var4);
         int var6 = m260(var1.getGreen(), var2.getGreen(), var4);
         int var7 = m260(var1.getBlue(), var2.getBlue(), var4);
         var0.append(
            Text.literal(String.valueOf("Daamky".charAt(var3)))
               .setStyle(Style.EMPTY.withColor(TextColor.fromRgb(var5 << 16 | var6 << 8 | var7)))
         );
      }

      return var0;
   }

   private static int m260(int var0, int var1, float var2) {
      return Math.round((float)var0 + (float)(var1 - var0) * var2);
   }
}

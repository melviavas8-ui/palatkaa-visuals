package ua.daamky.utils;

import java.util.ArrayList;
import java.util.Optional;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.StringVisitable.StyledVisitor;
import net.minecraft.util.Formatting;

public class ColoredText {
   public final String f1;
   public final int[] f2;

   public ColoredText(String var1, int[] var2) {
      this.f1 = var1;
      this.f2 = var2;
   }

   public ColoredText(String var1, int var2) {
      StringBuilder var3 = new StringBuilder();
      int[] var4 = new int[var1.length()];
      int var5 = var2;
      int var6 = 0;

      for (int var7 = 0; var7 < var1.length(); var7++) {
         char var8 = var1.charAt(var7);
         if (var8 == 167 && var7 + 1 < var1.length()) {
            char var9 = var1.charAt(++var7);
            Formatting var10 = Formatting.byCode(var9);
            if (var10 != null) {
               if (var10.getColorValue() != null) {
                  var5 = var10.getColorValue() | 0xFF000000;
               } else if (var10 == Formatting.RESET) {
                  var5 = var2;
               }
            }
         } else {
            var3.append(var8);
            var4[var6++] = var5;
         }
      }

      this.f1 = var3.toString();
      this.f2 = new int[var6];
      System.arraycopy(var4, 0, this.f2, 0, var6);
   }

   public static ColoredText m1031(Text var0, final int var1) {
      final StringBuilder var2 = new StringBuilder();
      final ArrayList var3 = new ArrayList();
      var0.visit(new StyledVisitor<Void>() {
         public Optional<Void> accept(Style var1x, String var2x) {
            int var3x = var1;
            if (var1x != null && var1x.getColor() != null) {
               var3x = var1x.getColor().getRgb() | 0xFF000000;
            }

            int var4 = var3x;

            for (int var5 = 0; var5 < var2x.length(); var5++) {
               char var6 = var2x.charAt(var5);
               if (var6 == 167 && var5 + 1 < var2x.length()) {
                  char var7 = var2x.charAt(++var5);
                  Formatting var8 = Formatting.byCode(var7);
                  if (var8 != null) {
                     if (var8.getColorValue() != null) {
                        var4 = var8.getColorValue() | 0xFF000000;
                     } else if (var8 == Formatting.RESET) {
                        var4 = var3x;
                     }
                  }
               } else {
                  var2.append(var6);
                  var3.add(var4);
               }
            }

            return Optional.empty();
         }
      }, Style.EMPTY);
      int[] var4 = new int[var3.size()];

      for (int var5 = 0; var5 < var3.size(); var5++) {
         var4[var5] = (Integer)var3.get(var5);
      }

      return new ColoredText(var2.toString(), var4);
   }
}

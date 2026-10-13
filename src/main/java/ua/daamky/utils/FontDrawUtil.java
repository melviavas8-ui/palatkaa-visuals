package ua.daamky.utils;

import ua.daamky.utils.render.fonts.FontRenderUtil;
import java.awt.Color;
import net.minecraft.client.gui.DrawContext;

public class FontDrawUtil {
   public static void m1032(DrawContext var0, String var1, float var2, float var3, float var4, Color var5) {
      ColoredText var6 = new ColoredText(var1, var5.getRGB());
      FontRenderUtil.m257(var0, var6.f1, var2, var3, var4, var6.f2);
   }

   public static void m1033(DrawContext var0, String var1, float var2, float var3, float var4, Color var5) {
      ColoredText var6 = new ColoredText(var1, var5.getRGB());
      float var7 = FontRenderUtil.m235(var6.f1, var4);
      FontRenderUtil.m257(var0, var6.f1, var2 - var7 / 2.0F, var3, var4, var6.f2);
   }

   public static void m1034(DrawContext var0, String var1, float var2, float var3, float var4, Color var5) {
      ColoredText var6 = new ColoredText(var1, var5.getRGB());
      float var7 = FontRenderUtil.m235(var6.f1, var4);
      FontRenderUtil.m257(var0, var6.f1, var2 - var7, var3, var4, var6.f2);
   }
}

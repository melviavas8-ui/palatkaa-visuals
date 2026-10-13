package ua.daamky.gui;

import ua.daamky.utils.SkyShaderRenderer;
import ua.daamky.utils.render.Render2DUtil;
import ua.daamky.utils.render.shaders.TextureShader;
import java.awt.Color;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.util.Identifier;
import org.joml.Vector3f;

public class TitleBackground {
   private static final Identifier f1 = Identifier.of(
      "daamky", "images/background.png"
   );
   private static final int[] f2 = new int[]{0, 14, 12, 18, 4, 10, 2, 6};
   private static final Color[] f3 = new Color[]{
      Color.WHITE,
      new Color(120, 155, 255),
      new Color(186, 92, 238),
      new Color(255, 148, 78),
      new Color(122, 146, 198),
      new Color(86, 150, 168),
      new Color(150, 118, 198),
      new Color(96, 132, 188)
   };
   private static final float[] f4 = new float[]{1.0F, 1.1F, 1.0F, 0.55F, 0.8F, 0.75F, 1.05F, 1.0F};
   private static int f5 = -1;
   private static int f6 = -1;
   private static long f7 = 0L;
   private static final long f8 = 500L;

   private TitleBackground() {
   }

   public static void m63() {
      byte var0 = 0;
      if (f5 == -1) {
         f5 = var0;
         f6 = var0;
      }

      if (var0 != f5) {
         f6 = f5;
         f5 = var0;
         f7 = System.currentTimeMillis();
      }

      float var1 = Math.clamp((float)(System.currentTimeMillis() - f7) / 500.0F, 0.0F, 1.0F);
      float var2 = var1 * var1 * (3.0F - 2.0F * var1);
      if (var2 < 1.0F && f6 != f5) {
         m347(f6, 1.0F);
         m347(f5, var2);
      } else {
         m347(f5, 1.0F);
      }
   }

   private static void m347(int var0, float var1) {
      if (var0 >= 1 && var0 < f2.length) {
         SkyShaderRenderer.m327(
            f3[var0], var1, f4[var0], f2[var0], new Vector3f(1.0F, 0.0F, 0.0F), new Vector3f(0.0F, 1.0F, 0.0F), new Vector3f(0.0F, 0.28F, -1.0F).normalize()
         );
      } else {
         m348(var1);
      }
   }

   private static void m348(float var0) {
      MinecraftClient var1 = MinecraftClient.getInstance();
      float var2 = (float)Render2DUtil.m113();
      float var3 = (float)Render2DUtil.m189();
      float var4 = var2 / var3;
      float var5 = 1.7777778F;
      float var6;
      float var7;
      float var8;
      float var9;
      if (var4 > var5) {
         var6 = var2;
         var7 = var2 / var5;
         var8 = 0.0F;
         var9 = (var3 - var7) / 2.0F;
      } else {
         var6 = var3 * var5;
         var7 = var3;
         var8 = (var2 - var6) / 2.0F;
         var9 = 0.0F;
      }

      AbstractTexture var10 = var1.getTextureManager().getTexture(f1);
      if (var10 != null) {
         int var11 = Math.round(Math.clamp(var0, 0.0F, 1.0F) * 255.0F);
         TextureShader.m341(Render2DUtil.m191(), var8, var9, var6, var7, var10.getGlTextureView(), new Color(255, 255, 255, var11).getRGB(), 0.0F, 0.0F);
      }
   }

   public static void m314() {
   }
}

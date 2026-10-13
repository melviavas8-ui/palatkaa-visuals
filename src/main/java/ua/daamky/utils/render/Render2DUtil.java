package ua.daamky.utils.render;

import ua.daamky.utils.MsdfFontManager;
import ua.daamky.utils.render.fonts.FontAtlas;
import ua.daamky.utils.render.fonts.FontRenderUtil;
import ua.daamky.utils.render.shaders.CircleShader;
import ua.daamky.utils.render.shaders.FluidGlassShader;
import ua.daamky.utils.render.shaders.GlowOutlineShader;
import ua.daamky.utils.render.shaders.GlowShader;
import ua.daamky.utils.render.shaders.HudBlurShader;
import ua.daamky.utils.render.shaders.LiquidGlassShader;
import ua.daamky.utils.render.shaders.OutlineShader;
import ua.daamky.utils.render.shaders.RectangleShader;
import ua.daamky.utils.render.shaders.Sampler0_2;
import ua.daamky.utils.render.shaders.TextureShader;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.util.Window;
import net.minecraft.util.Identifier;
import org.joml.Matrix4f;

public class Render2DUtil {
   private static final List<Runnable> f1 = new ArrayList<>();
   private static final float f2 = 0.0F;
   private static final int f3 = 2;
   private static Matrix4f f4;

   public static void m5(Runnable var0) {
      f1.add(var0);
   }

   public static int m113() {
      Window var0 = MinecraftClient.getInstance().getWindow();
      return (int)Math.ceil((double)var0.getWidth() / 2.0);
   }

   public static int m189() {
      Window var0 = MinecraftClient.getInstance().getWindow();
      return (int)Math.ceil((double)var0.getHeight() / 2.0);
   }

   public static void m190(Matrix4f var0) {
      f4 = var0;
   }

   public static Matrix4f m191() {
      Matrix4f var0 = f4;
      return var0 != null ? new Matrix4f(var0) : new Matrix4f().ortho(0.0F, (float)m113(), (float)m189(), 0.0F, -1000.0F, 1000.0F);
   }

   public static float m192() {
      Window var0 = MinecraftClient.getInstance().getWindow();
      int var1 = var0.getScaleFactor();
      return (float)var1 / 2.0F;
   }

   public static float m3(float var0) {
      return var0 * m192();
   }

   public static float m151(float var0) {
      return var0 * m192();
   }

   public static void m193(
      Matrix4f var0, float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, boolean var9, Color... var10
   ) {
      if (var9) {
         f1.add(() -> RectangleShader.m326(var0, var1, var2, var3, var4, var5, var6, var7, var8, 0.0F, ColorUtil.m367(var10)));
      } else {
         RectangleShader.m326(var0, var1, var2, var3, var4, var5, var6, var7, var8, 0.0F, ColorUtil.m367(var10));
      }
   }

   public static void m194(float var0, float var1, float var2, float var3, float var4, float var5, float var6, float var7, Color... var8) {
      m193(m191(), var0, var1, var2, var3, var4, var5, var6, var7, false, var8);
   }

   public static void m195(float var0, float var1, float var2, float var3, float var4, Color... var5) {
      m193(m191(), var0, var1, var2, var3, var4, var4, var4, var4, false, var5);
   }

   public static void m196(float var0, float var1, float var2, float var3, float var4, float var5) {
      Sampler0_2.m283(m191(), var0, var1, var2, var3, var4, var5, 0.0F);
   }

   public static void m197(float var0, float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, Color var10) {
      HudBlurShader.m318(m191(), var0, var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, 0.0F);
   }

   public static void m198(float var0, float var1, float var2, float var3, float var4, float var5, float var6, Color var7) {
      m197(var0, var1, var2, var3, var4, var4, var4, var4, var5, var6, var7);
   }

   public static void m199(float var0, float var1, float var2, float var3, float var4, float var5, float var6, Color var7) {
      LiquidGlassShader.m315(m191(), var0, var1, var2, var3, var4, var5, var6, ColorUtil.m368(var7), 0.0F);
   }

   public static void m200(float var0, float var1, float var2, float var3, float var4, float var5, float var6, Color var7) {
      FluidGlassShader.m315(m191(), var0, var1, var2, var3, var4, var5, var6, ColorUtil.m368(var7), 0.0F);
   }

   public static void m201(float var0, float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, Color... var9) {
      OutlineShader.m324(m191(), var0, var1, var2, var3, var4, var5, var6, var7, var8, 0.0F, ColorUtil.m367(var9));
   }

   public static void m202(float var0, float var1, float var2, float var3, float var4, float var5, Color... var6) {
      OutlineShader.m324(m191(), var0, var1, var2, var3, var4, var4, var4, var4, var5, 0.0F, ColorUtil.m367(var6));
   }

   public static void m203(DrawContext var0, float var1, float var2, String var3, float var4, Color var5, Color var6, float var7) {
      FontRenderUtil.m250(var0, var3, var1, var2, var4, ColorUtil.m368(var5), ColorUtil.m368(var6), var7, false);
   }

   public static void m204(DrawContext var0, FontAtlas var1, float var2, float var3, String var4, float var5, Color var6, Color var7, float var8) {
      FontRenderUtil.m251(var0, var1, var4, var2, var3, var5, ColorUtil.m368(var6), ColorUtil.m368(var7), var8, false);
   }

   public static void m205(DrawContext var0, float var1, float var2, String var3, float var4, Color var5) {
      FontRenderUtil.m227(var0, var3, var1, var2, var4, ColorUtil.m368(var5), false);
   }

   public static void m206(DrawContext var0, float var1, float var2, String var3, float var4, Color var5, String var6) {
      if (var6.equals("center")) {
         FontRenderUtil.m240(var0, var3, var1, var2, var4, ColorUtil.m368(var5));
      } else if (var6.equals("right")) {
         FontRenderUtil.m242(var0, var3, var1, var2, var4, ColorUtil.m368(var5));
      } else {
         FontRenderUtil.m226(var0, var3, var1, var2, var4, ColorUtil.m368(var5));
      }
   }

   public static void m207(DrawContext var0, float var1, float var2, String var3, float var4, Color var5, Color var6, String var7) {
      if (var7.equals("center")) {
         FontRenderUtil.m243(var0, var3, var1, var2, var4, ColorUtil.m368(var5), ColorUtil.m368(var6));
      } else if (var7.equals("right")) {
         FontRenderUtil.m245(var0, var3, var1, var2, var4, ColorUtil.m368(var5), ColorUtil.m368(var6));
      } else {
         FontRenderUtil.m255(var0, var3, var1, var2, var4, ColorUtil.m368(var5), ColorUtil.m368(var6));
      }
   }

   public static void m208(DrawContext var0, FontAtlas var1, float var2, float var3, String var4, float var5, Color var6) {
      FontRenderUtil.m229(var0, var1, var4, var2, var3, var5, ColorUtil.m368(var6), false);
   }

   public static void m209(DrawContext var0, float var1, float var2, String var3, float var4, Color... var5) {
      FontRenderUtil.m258(var0, MsdfFontManager.m281(), var3, var1, var2, var4, false, ColorUtil.m367(var5));
   }

   public static void m210(float var0, float var1, float var2, Identifier var3, float var4, Color var5) {
      MinecraftClient var6 = MinecraftClient.getInstance();
      AbstractTexture var7 = var6.getTextureManager().getTexture(var3);
      TextureShader.m342(m191(), var0, var1, var2, var7.getGlTextureView(), ColorUtil.m368(var5), var4, 0.0F);
   }

   public static void m211(float var0, float var1, float var2, Color var3) {
      GlowShader.m317(m191(), var0, var1, var2, ColorUtil.m368(var3), 0.0F);
   }

   public static void m212(float var0, float var1, float var2, float var3, Color var4) {
      CircleShader.m312(m191(), var0, var1, var2, var3, 0.0F, var4);
   }

   public static void m213(float var0, float var1, float var2, float var3, float var4, float var5, Color var6) {
      CircleShader.m313(m191(), var0, var1, var2, var3, var4, var5, 0.0F, var6);
   }

   public static void m214(float var0, float var1, float var2, float var3, float var4, float var5, Color var6) {
      m216(var0, var1, var2, var3, var4, var5, 0.0F, 1.0F, 0.0F, var6);
   }

   public static void m215(float var0, float var1, float var2, float var3, float var4, float var5, float var6, Color var7) {
      m216(var0, var1, var2, var3, var4, var5, 0.0F, 1.0F, Math.clamp(var6, 0.0F, 1.0F), var7);
   }

   public static void m216(float var0, float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, Color var9) {
      GlowOutlineShader.m316(m191(), var0, var1, var2, var3, var4, ColorUtil.m368(var9), var5, var6, Math.clamp(var7, 0.0F, 1.0F), var8, 1.0F, 0.0F);
   }

   public static void m217(float var0, float var1, float var2, float var3, float var4, float var5, float var6, float var7, Color var8) {
      if (!(var5 <= 0.0F) && !(var6 <= 0.0F)) {
         GlowOutlineShader.m316(
            m191(), var0, var1, var2, var3, 0.0F, ColorUtil.m368(var8), var4, var5, Math.clamp(var6, 0.0F, 1.0F), Math.max(0.1F, var7), 1.0F, 0.0F
         );
      }
   }

   public static void m218(float var0, float var1, float var2, float var3, float var4, Color var5) {
      m217(var0, var1, var2, var3, var4, 12.0F, 0.55F, 2.2F, var5);
   }

   public static boolean m31() {
      MinecraftClient var0 = MinecraftClient.getInstance();
      return var0.currentScreen != null && !(var0.currentScreen instanceof ChatScreen);
   }

   public static boolean m41() {
      MinecraftClient var0 = MinecraftClient.getInstance();
      return var0.currentScreen == null || var0.currentScreen instanceof ChatScreen;
   }

   public static void m219(DrawContext var0, FontAtlas var1, float var2, float var3, String var4, float var5, Color var6, String var7) {
      float var8 = var2;
      if (var7.equals("center")) {
         var8 = var2 - FontRenderUtil.m237(var1, var4, var5) / 2.0F;
      } else if (var7.equals("right")) {
         var8 = var2 - FontRenderUtil.m237(var1, var4, var5);
      }

      FontRenderUtil.m229(var0, var1, var4, var8, var3, var5, ColorUtil.m368(var6), false);
   }
}

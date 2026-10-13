package ua.daamky.gui;

import ua.daamky.utils.render.Render2DUtil;
import ua.daamky.utils.render.fonts.FontRenderUtil;
import java.awt.Color;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.PressableTextWidget;
import net.minecraft.client.gui.widget.TextIconButtonWidget;
import net.minecraft.util.Identifier;

public class AccountOverlay {
   private static final Map<ClickableWidget, Float> f1 = new IdentityHashMap<>();
   private static DrawContext f2;
   private static long f3;
   private static long f8;
   private static TitleScreen f9;
   private static final String f4 = "U";
   private static final Identifier f5 = Identifier.of("daamky", "images/logo.png");
   private static final Color f6 = new Color(15, 35, 80);
   private static final Color f7 = new Color(22, 48, 110);

   private static float m271() {
      return 0.5F + 0.5F * (float)Math.sin((double)System.currentTimeMillis() * 0.0016);
   }

   public static void m1063(DrawContext var0) {
      f2 = var0;
   }

   public static void m1386(TitleScreen var0) {
      if (var0 != f9) {
         f9 = var0;
         f8 = System.currentTimeMillis();
      }

      for (Element var2 : var0.children()) {
         if (var2 instanceof TextIconButtonWidget var3) {
            var3.visible = false;
            var3.active = false;
         } else if (var2 instanceof PressableTextWidget var4) {
            var4.visible = false;
            var4.active = false;
         }
      }
   }

   public static boolean m20(String var0) {
      MinecraftClient var1 = MinecraftClient.getInstance();
      return var1.currentScreen instanceof TitleScreen && var0 != null
         ? var0.contains("Текущий аккаунт")
            || var0.contains("Current account")
         : false;
   }

   public static void m63() {
      MinecraftClient var0 = MinecraftClient.getInstance();
      if (var0.currentScreen instanceof TitleScreen var1 && f2 != null) {
         m29();
         // Widgets live in GUI units; Render2DUtil draws in "virtual" units (half a physical pixel).
         // var2 converts GUI -> virtual, so drawing always matches the real hit boxes at any GUI scale.
         float var2 = Render2DUtil.m192();
         float var3 = (float)(System.currentTimeMillis() - f8);
         List<ClickableWidget> var4 = new ArrayList<>();
         float var5 = Float.MAX_VALUE;
         float var6 = Float.MAX_VALUE;
         float var7 = -Float.MAX_VALUE;
         float var8 = 30.0F;

         for (Element var10 : var1.children()) {
            if (var10 instanceof ButtonWidget var11 && var11.visible) {
               String var12 = m1389(var11);
               if (var12 != null) {
                  var4.add(var11);
                  if (!"F".equals(var12)) {
                     var5 = Math.min(var5, (float)var11.getX() * var2);
                     var6 = Math.min(var6, (float)var11.getY() * var2);
                     var7 = Math.max(var7, (float)(var11.getX() + var11.getWidth()) * var2);
                     var8 = (float)var11.getHeight() * var2;
                  }
               }
            }
         }

         if (var4.isEmpty()) {
            return;
         }

         float var13 = var8 / 30.0F;
         var4.sort((var0x, var1x) -> Integer.compare(var0x.getY(), var1x.getY()));
         if (var5 != Float.MAX_VALUE) {
            m1387((var5 + var7) / 2.0F, var6, var13, ease(var3 / 600.0F));
         }

         for (int var15 = 0; var15 < var4.size(); var15++) {
            ClickableWidget var16 = var4.get(var15);
            String var17 = m1389(var16);
            String var18 = "F".equals(var17) ? var16.getMessage().getString() : m59(var17);
            float var19 = ease((var3 - 140.0F - 70.0F * (float)var15) / 450.0F);
            m1388(var16, var18, var17, "Q".equals(var17), var2, var13, var19);
         }

         m1390(var0, var2 * (float)var1.width, var2 * (float)var1.height, var13, ease((var3 - 500.0F) / 600.0F));
      }
   }

   private static void m1387(float var0, float var1, float var2, float var3) {
      float var4 = 44.0F * var2;
      float var5 = 21.0F * var2;
      float var6 = FontRenderUtil.m239(FontRenderUtil.f2, var5);
      float var8 = var1 - 16.0F * var2;
      float var9 = var8 - var6;
      float var10 = var9 - 8.0F * var2 - var4;
      float var11 = var10 + var4 / 2.0F;
      Render2DUtil.m211(var0, var11, var4 * 1.7F, new Color(96, 140, 225, Math.round(70.0F * var3)));
      Render2DUtil.m210(var0 - var4 / 2.0F, var10, var4, f5, 0.0F, new Color(255, 255, 255, Math.round(255.0F * var3)));
      String var12 = "Daamky";
      float var13 = FontRenderUtil.m237(FontRenderUtil.f2, var12, var5);
      Render2DUtil.m208(f2, FontRenderUtil.f2, var0 - var13 / 2.0F, var9, var12, var5, new Color(238, 242, 250, Math.round(255.0F * var3)));
   }

   private static void m1390(MinecraftClient var0, float var1, float var2, float var3, float var4) {
      float var5 = 7.5F * var3;
      float var6 = 10.0F * var3;
      float var7 = var2 - var6 - FontRenderUtil.m239(FontRenderUtil.f1, var5);
      Color var8 = new Color(150, 162, 186, Math.round(170.0F * var4));
      String var9 = "Daamky Client  |  Minecraft " + var0.getGameVersion();
      Render2DUtil.m208(f2, FontRenderUtil.f1, var6, var7, var9, var5, var8);
      String var10 = var0.getSession().getUsername();
      float var11 = FontRenderUtil.m237(FontRenderUtil.f1, var10, var5);
      Render2DUtil.m208(f2, FontRenderUtil.f1, var1 - var6 - var11, var7, var10, var5, var8);
   }

   private static void m1388(ClickableWidget var0, String var1, String var2, boolean var3, float var4, float var5, float var6) {
      float var7 = var0.getAlpha() * var6;
      if (!(var7 <= 0.003F)) {
         float var8 = f1.computeIfAbsent(var0, var0x -> 0.0F);
         float var9 = m3(var8);
         float var10 = (float)var0.getX() * var4;
         float var11 = (float)var0.getY() * var4;
         float var12 = (float)var0.getWidth() * var4;
         float var13 = (float)var0.getHeight() * var4;
         float var14 = 8.0F * var5;
         Color var15 = new Color(96, 140, 225);
         Render2DUtil.m217(var10 + 1.0F, var11 + 2.0F * var5, var12 - 2.0F, var13 - 1.0F, var14, 9.0F, 0.24F * var7, 2.0F, new Color(0, 0, 0, 165));
         Render2DUtil.m198(var10, var11, var12, var13, var14, 7.0F, 0.5F * var7, new Color(16, 20, 34));
         Render2DUtil.m195(var10, var11, var12, var13, var14, new Color(26, 32, 54, Math.round((70.0F + 28.0F * var9) * var7)));
         if (var9 > 0.001F) {
            Render2DUtil.m195(var10, var11, var12, var13, var14, new Color(var15.getRed(), var15.getGreen(), var15.getBlue(), Math.round(34.0F * var9 * var7)));
         }

         Color var16 = m944(new Color(255, 255, 255, 24), new Color(var15.getRed(), var15.getGreen(), var15.getBlue(), 215), var9);
         Render2DUtil.m202(var10 + 0.5F, var11 + 0.5F, var12 - 1.0F, var13 - 1.0F, var14, 1.0F, m336(var16, (float)var16.getAlpha() / 255.0F * var7));
         float var17 = 9.5F * var5;
         float var18 = 11.0F * var5;
         Color var19 = m336(m944(new Color(210, 218, 232), new Color(246, 250, 255), var9), var7);
         Color var20 = m336(m944(new Color(150, 162, 186), var15, var9), var7);
         float var21 = var11 + var13 / 2.0F - FontRenderUtil.m239(FontRenderUtil.f2, var17) / 2.0F;
         float var22 = var11 + var13 / 2.0F - var18 * 0.47F;
         float var23 = FontRenderUtil.m237(FontRenderUtil.f3, var2, var18);
         float var24 = 11.0F * var5;
         if (var3 || var12 < 130.0F * var5) {
            float var25 = FontRenderUtil.m237(FontRenderUtil.f2, var1, var17);
            float var26 = var23 + 7.0F * var5 + var25;
            float var27 = var10 + (var12 - var26) / 2.0F;
            Render2DUtil.m208(f2, FontRenderUtil.f3, var27, var22, var2, var18, var20);
            Render2DUtil.m208(f2, FontRenderUtil.f2, var27 + var23 + 7.0F * var5, var21, var1, var17, var19);
         } else {
            Render2DUtil.m208(f2, FontRenderUtil.f2, var10 + var24, var21, var1, var17, var19);
            Render2DUtil.m208(f2, FontRenderUtil.f3, var10 + var12 - var24 - var23, var22, var2, var18, var20);
         }
      }
   }

   private static float ease(float var0) {
      float var1 = Math.clamp(var0, 0.0F, 1.0F) - 1.0F;
      return var1 * var1 * var1 + 1.0F;
   }

   private static void m29() {
      long var0 = System.currentTimeMillis();
      if (f3 == 0L) {
         f3 = var0;
      } else {
         float var2 = Math.min(50.0F, (float)(var0 - f3));
         f3 = var0;
         f1.entrySet().removeIf(var0x -> !var0x.getKey().visible);

         for (Entry var4 : f1.entrySet()) {
            ClickableWidget var5 = (ClickableWidget)var4.getKey();
            float var6 = var5.isHovered() ? 1.0F : 0.0F;
            float var7 = 1.0F - (float)Math.exp((double)(-0.018F * var2));
            float var8 = (Float)var4.getValue() + (var6 - (Float)var4.getValue()) * Math.clamp(var7, 0.0F, 1.0F);
            var4.setValue(Math.abs(var8 - var6) < 0.001F ? var6 : var8);
         }
      }
   }

   // Прячет кнопки, которые добавляют другие моды (ModMenu, IAS и т.д.) уже после init(): у них нет ни одной из наших меток.
   public static void m1400(TitleScreen var0) {
      boolean var1 = false;
      for (Element var2 : var0.children()) {
         if (var2 instanceof ClickableWidget var3 && var3.visible) {
            String var4 = m1389(var3);
            if ("P".equals(var4) || "N".equals(var4)) {
               var1 = true;
               break;
            }
         }
      }

      if (var1) {
         ClickableWidget var7 = null;
         ClickableWidget var8 = null;
         ClickableWidget var9 = null;
         ClickableWidget var10 = null;
         ClickableWidget var11 = null;
         for (Element var5 : var0.children()) {
            if (var5 instanceof ClickableWidget var6 && var6.visible) {
               String var12 = m1389(var6);
               if (var12 == null) {
                  var6.visible = false;
                  var6.active = false;
               } else if ("P".equals(var12)) {
                  var7 = var6;
               } else if ("N".equals(var12)) {
                  var8 = var6;
               } else if ("O".equals(var12)) {
                  var9 = var6;
               } else if ("U".equals(var12)) {
                  var10 = var6;
               } else if ("Q".equals(var12)) {
                  var11 = var6;
               }
            }
         }

         // Ряды идут вплотную друг к другу (без "воздуха" на месте скрытых кнопок) и весь блок центрируется по высоте.
         if (var7 != null && var8 != null) {
            int var13 = var7.getHeight();
            int var14 = var13 + Math.max(2, Math.round((float)var13 * 0.267F));
            int var15 = 3 + (var11 != null ? 1 : 0);
            int var16 = Math.round((float)var13 * 100.0F / 30.0F);
            int var17 = var16 + (var15 - 1) * var14 + var13;
            int var18 = (var0.height - var17) / 2 + var16;
            var7.setY(var18);
            var8.setY(var18 + var14);
            if (var9 != null) {
               var9.setY(var18 + 2 * var14);
            }

            if (var10 != null) {
               var10.setY(var18 + 2 * var14);
            }

            if (var11 != null) {
               var11.setY(var18 + 3 * var14);
            }
         }
      }
   }

   private static String m1389(ClickableWidget var0) {
      String var1 = var0.getMessage().getString().replaceAll("§.", "").toLowerCase();
      if (var1.contains("одиноч")
         || var1.contains("сингл")
         || var1.contains("singleplayer")
         || var1.contains("single")) {
         return "P";
      } else if (var1.contains("сетев")
         || var1.contains("мульти")
         || var1.contains("multiplayer")
         || var1.contains("multi")) {
         return "N";
      } else if (var1.contains("настрой") || var1.contains("options")) {
         return "O";
      } else if (var1.contains("выйти")
         || var1.contains("выход")
         || var1.contains("quit")) {
         return "Q";
      } else if (var1.contains("альт") || var1.contains("alt")) {
         return "U";
      } else {
         return var1.contains("фон") ? "F" : null;
      }
   }

   private static String m59(String var0) {
      return switch (var0) {
         case "P" -> "Одиночная игра";
         case "N" -> "Сетевая игра";
         case "O" -> "Настройки";
         case "Q" -> "Выйти";
         case "U" -> "Аккаунты";
         default -> "";
      };
   }

   private static Color m944(Color var0, Color var1, float var2) {
      var2 = Math.clamp(var2, 0.0F, 1.0F);
      return new Color(
         Math.round((float)var0.getRed() + (float)(var1.getRed() - var0.getRed()) * var2),
         Math.round((float)var0.getGreen() + (float)(var1.getGreen() - var0.getGreen()) * var2),
         Math.round((float)var0.getBlue() + (float)(var1.getBlue() - var0.getBlue()) * var2),
         Math.round((float)var0.getAlpha() + (float)(var1.getAlpha() - var0.getAlpha()) * var2)
      );
   }

   private static Color m336(Color var0, float var1) {
      return new Color(var0.getRed(), var0.getGreen(), var0.getBlue(), Math.round(255.0F * Math.clamp(var1, 0.0F, 1.0F)));
   }

   private static float m3(float var0) {
      float var1 = Math.clamp(var0, 0.0F, 1.0F) - 1.0F;
      return var1 * var1 * var1 + 1.0F;
   }
}

package ua.daamky.utils.render;

import com.mojang.blaze3d.textures.GpuTextureView;
import ua.daamky.gui.Daamky_4;
import ua.daamky.gui.TitleBackground;
import ua.daamky.gui.alts.AltManager;
import ua.daamky.gui.alts.AltManager$1;
import ua.daamky.mixins.interfaces.IMinecraftClient;
import ua.daamky.utils.EasingUtil;
import ua.daamky.utils.ScissorUtil;
import ua.daamky.utils.MsdfFontManager;
import ua.daamky.utils.render.fonts.FontAtlas;
import ua.daamky.utils.render.fonts.FontRenderUtil;
import ua.daamky.utils.render.shaders.TextureShader;
import java.awt.Color;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.Map.Entry;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.input.CharInput;
import net.minecraft.client.input.KeyInput;
import net.minecraft.client.session.Session;
import net.minecraft.client.util.DefaultSkinHelper;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

public class Render2D extends Screen {
   private final Screen f1;
   private String f2 = "";
   private boolean f3 = false;
   private float f4 = 0.0F;
   private long f5;
   private long f6;
   private final Map<String, Float> f7 = new HashMap<>();
   private String f8 = null;
   private float f9 = 0.0F;
   private float f10 = 0.0F;
   private final Map<String, Float> f11 = new HashMap<>();
   private final Map<String, Float> f12 = new HashMap<>();
   private String f13 = "";
   private String f14 = "";
   private long f15 = 0L;
   private final Map<String, Long> f16 = new HashMap<>();
   private final float[] f17 = new float[16];
   private static final float f18 = 400.0F;
   private static final Identifier f19 = Identifier.of(
      "daamky", "images/background.png"
   );

   public Render2D(Screen var1) {
      super(Text.literal("Alt Manager"));
      this.f1 = var1;
   }

   protected void init() {
      super.init();
      this.f5 = System.currentTimeMillis();
      this.f6 = System.currentTimeMillis();
   }

   public void renderBackground(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
      TitleBackground.m63();
   }

   public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
      this.renderBackground(context, mouseX, mouseY, deltaTicks);
      int var5 = Render2DUtil.m113();
      int var6 = Render2DUtil.m189();
      float var7 = Render2DUtil.m3((float)mouseX);
      float var8 = Render2DUtil.m151((float)mouseY);
      long var9 = System.currentTimeMillis();
      float var11 = (float)(var9 - this.f6);
      this.f6 = var9;

      for (Entry var13 : this.f11.entrySet()) {
         float var14 = (Float)var13.getValue();
         if (var14 > 0.0F) {
            var13.setValue(Math.max(0.0F, var14 - var11 / 100.0F));
         }
      }

      float var62 = (float)(var9 - this.f5);
      float var63 = Math.min(1.0F, var62 / 400.0F);
      float var64 = EasingUtil.m3(var63);
      float var15 = 320.0F;
      float var16 = 240.0F;
      float var17 = (float)var5 / 2.0F;
      float var18 = (float)var6 / 2.0F;
      float var19 = var17 - var15 / 2.0F;
      float var20 = var18 - var16 / 2.0F + (1.0F - var64) * 50.0F;
      float var21 = Math.min(1.0F, var63 * 2.0F);
      int var22 = (int)(255.0F * var21);
      float var23 = 10.0F;
      Render2DUtil.m217(var19, var20 + 1.5F, var15, var16, var23, 8.0F, 0.22F * var21, 2.0F, new Color(0, 0, 0, 170));
      Render2DUtil.m198(var19, var20, var15, var16, var23, 10.0F, var21, new Color(100, 100, 100, 10));
      Render2DUtil.m219(
         context,
         FontRenderUtil.f2,
         var17,
         var20 + 15.0F,
         "Alt Manager",
         16.0F,
         new Color(245, 247, 250, var22),
         "center"
      );
      String var24 = this.client.getSession().getUsername();
      if (this.f13.isEmpty()) {
         this.f13 = var24;
         this.f14 = var24;
      } else if (!var24.equals(this.f13)) {
         this.f14 = this.f13;
         this.f13 = var24;
         this.f15 = System.currentTimeMillis();
      }

      long var25 = System.currentTimeMillis();
      float var27 = 1.0F;
      if (this.f15 > 0L) {
         var27 = Math.min(1.0F, (float)(var25 - this.f15) / 400.0F);
      }

      FontAtlas var28 = MsdfFontManager.m281();
      String var29 = "Current: ";
      float var30 = FontRenderUtil.m237(var28, var29, 12.0F);
      if (var27 >= 1.0F) {
         float var31 = FontRenderUtil.m237(var28, this.f13, 12.0F);
         float var32 = var17 - (var30 + var31) / 2.0F;
         Render2DUtil.m205(context, var32, var20 + 35.0F, var29, 12.0F, new Color(185, 190, 196, var22));
         Render2DUtil.m205(context, var32 + var30, var20 + 35.0F, this.f13, 12.0F, new Color(185, 190, 196, var22));
      } else {
         float var65 = EasingUtil.m3(var27);
         float var67 = EasingUtil.m151(var27);
         float var33 = FontRenderUtil.m237(var28, this.f13, 12.0F);
         float var34 = FontRenderUtil.m237(var28, this.f14, 12.0F);
         float var35 = var30 + var34 + (var33 - var34) * var65;
         float var36 = var17 - var35 / 2.0F;
         Render2DUtil.m205(context, var36, var20 + 35.0F, var29, 12.0F, new Color(185, 190, 196, var22));
         float var37 = 6.0F;
         ScissorUtil.m357(
            (double)(var36 + var30 - var37), (double)(var20 + 35.0F - var37), (double)(Math.max(var33, var34) + var37 * 2.0F), (double)(12.0F + var37 * 2.0F)
         );
         float var38 = -15.0F * var67;
         int var39 = (int)((float)var22 * (1.0F - var27));
         if (var39 > 0) {
            Render2DUtil.m205(context, var36 + var30, var20 + 35.0F + var38, this.f14, 12.0F, new Color(185, 190, 196, var39));
         }

         float var40 = 15.0F * (1.0F - var65);
         int var41 = (int)((float)var22 * Math.min(1.0F, var27 * 2.0F));
         if (var41 > 0) {
            Render2DUtil.m205(context, var36 + var30, var20 + 35.0F + var40, this.f13, 12.0F, new Color(185, 190, 196, var41));
         }

         ScissorUtil.m29();
      }

      float var66 = var19 + 10.0F;
      float var68 = var20 + 55.0F;
      float var69 = var15 - 130.0F;
      float var70 = var16 - 70.0F;
      Render2DUtil.m198(var66, var68, var69, var70, var23, 8.0F, var21, new Color(150, 150, 150, 20));
      List var71 = AltManager.m369();
      ScissorUtil.m357((double)var66, (double)var68, (double)var69, (double)var70);
      float var72 = var68 + 5.0F + this.f4;

      for (int var73 = 0; var73 < var71.size(); var73++) {
         AltManager$1 var75 = (AltManager$1)var71.get(var73);
         if (!this.f16.containsKey(var75.f1)) {
            long var76 = var9 - this.f5 < 100L ? (long)var73 * 40L : 0L;
            this.f16.put(var75.f1, var9 + var76);
         }

         long var77 = this.f16.get(var75.f1);
         float var80 = Math.max(0.0F, Math.min(1.0F, (float)(var9 - var77) / 400.0F));
         if (var80 != 0.0F) {
            float var42 = EasingUtil.m3(var80);
            float var43 = 26.0F;
            float var44 = var43 * Math.max(0.0F, Math.min(1.0F, var80));
            float var45 = (1.0F - var42) * 20.0F;
            float var46 = Math.max(0.0F, Math.min(1.0F, var80 * 2.0F));
            int var47 = (int)((float)var22 * var46);
            float var48 = var21 * var46;
            boolean var49 = var7 >= var66 + 5.0F && var7 <= var66 + var69 - 5.0F && var8 >= var72 && var8 <= var72 + var43;
            float var50 = var49 ? 1.0F : 0.0F;
            float var51 = this.f7.getOrDefault(var75.f1, 0.0F);
            var51 += (var50 - var51) * (var11 / 80.0F);
            var51 = Math.max(0.0F, Math.min(1.0F, var51));
            this.f7.put(var75.f1, var51);
            float var52 = 16.0F;
            float var53 = 1.0F - var51;
            float var54 = var52 * var53;
            float var55 = var66 + 10.0F + var45 + (var52 - var54) / 2.0F;
            float var56 = var72 + 5.0F + (var52 - var54) / 2.0F;
            if (var54 > 0.5F) {
               Identifier var57 = Daamky_4.m221(var75.f1);
               int var58 = new Color(255, 255, 255, Math.round(255.0F * var48 * var53)).getRGB();
               if (var57 != null) {
                  GpuTextureView var59 = this.client.getTextureManager().getTexture(var57).getGlTextureView();
                  TextureShader.m344(Render2DUtil.m191(), var55, var56, var54, var59, var58, 2.0F, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, false);
               } else {
                  UUID var105 = UUID.nameUUIDFromBytes(("OfflinePlayer:" + var75.f1).getBytes());
                  Identifier var60 = DefaultSkinHelper.getSkinTextures(var105).body().texturePath();
                  GpuTextureView var61 = this.client.getTextureManager().getTexture(var60).getGlTextureView();
                  TextureShader.m344(Render2DUtil.m191(), var55, var56, var54, var61, var58, 2.0F, 0.0F, 0.125F, 0.125F, 0.125F, 0.125F, false);
                  TextureShader.m344(Render2DUtil.m191(), var55, var56, var54, var61, var58, 2.0F, 0.0F, 0.625F, 0.125F, 0.125F, 0.125F, false);
               }
            }

            float var102 = var66 + 10.0F + var45 + var52 + 6.0F - var51 * (var52 + 6.0F);
            Color var104 = var24.equals(var75.f1) ? new Color(255, 255, 255, var47) : new Color(100, 100, 100, var47);
            if (var75.f2) {
               var104 = new Color(255, 200, 50, var47);
            }

            Render2DUtil.m205(context, var102, var72 + 9.0F, var75.f1, 10.0F, var104);
            if (var51 > 0.01F) {
               float var106 = EasingUtil.m3(var51);
               float var107 = 14.0F;
               float var108 = var66 + var69 - 5.0F - var107 * 3.0F - 10.0F + var45;
               this.m33(
                  context,
                  "R",
                  var108,
                  var72 + 6.0F,
                  var107,
                  var7,
                  var8,
                  var48,
                  var75.f2 ? new Color(255, 200, 50) : new Color(200, 200, 200),
                  var106
               );
               this.m33(
                  context,
                  "T",
                  var108 + var107 + 5.0F,
                  var72 + 6.0F,
                  var107,
                  var7,
                  var8,
                  var48,
                  new Color(200, 200, 200),
                  var106
               );
               this.m33(
                  context,
                  "S",
                  var108 + (var107 + 5.0F) * 2.0F,
                  var72 + 6.0F,
                  var107,
                  var7,
                  var8,
                  var48,
                  new Color(255, 80, 80),
                  var106
               );
            }

            var72 += var44 + 2.0F;
         }
      }

      ScissorUtil.m29();
      float var74 = var66 + var69 + 10.0F;
      float var78 = var15 - var69 - 30.0F;
      float var79 = 20.0F;
      float var81 = EasingUtil.m3(this.f11.getOrDefault("field", 0.0F));
      float var82 = 1.0F - var81 * 0.05F;
      float var83 = var78 * var82;
      float var84 = var79 * var82;
      float var85 = var74 + (var78 - var83) / 2.0F;
      float var86 = var68 + (var79 - var84) / 2.0F;
      Render2DUtil.m198(var85, var86, var83, var84, 7.0F, 5.0F, var21, new Color(150, 150, 150, 20));
      if (this.f2.isEmpty() && !this.f3) {
         Render2DUtil.m205(context, var85 + 5.0F, var86 + 5.0F, "Nickname", 10.0F, new Color(185, 190, 196, var22));
      } else {
         float var87 = var85 + 5.0F;
         FontAtlas var90 = MsdfFontManager.m281();

         for (int var92 = 0; var92 < this.f2.length(); var92++) {
            char var93 = this.f2.charAt(var92);
            if (this.f17[var92] < 1.0F) {
               this.f17[var92] = this.f17[var92] + (1.0F - this.f17[var92]) * (var11 / 60.0F);
               if (this.f17[var92] > 1.0F) {
                  this.f17[var92] = 1.0F;
               }
            }

            float var96 = EasingUtil.m3(this.f17[var92]);
            if (var96 > 0.05F) {
               int var97 = (int)(255.0F * var21 * Math.max(0.0F, Math.min(1.0F, this.f17[var92])));
               float var98 = FontRenderUtil.m237(var90, String.valueOf(var93), 10.0F);
               float var99 = 10.0F * var96;
               float var100 = FontRenderUtil.m237(var90, String.valueOf(var93), var99);
               float var101 = var87 + (var98 - var100) / 2.0F;
               float var103 = var86 + 5.0F + (10.0F - var99) / 2.0F;
               Render2DUtil.m205(context, var101, var103, String.valueOf(var93), var99, new Color(245, 247, 250, var97));
            }

            var87 += FontRenderUtil.m237(var90, String.valueOf(var93), 10.0F) + 0.5F;
         }

         if (this.f3 && System.currentTimeMillis() / 500L % 2L == 0L) {
            Render2DUtil.m205(context, var87, var86 + 5.0F, "_", 10.0F, new Color(245, 247, 250, var22));
         }
      }

      float var88 = var68 + 30.0F;
      String var91 = this.f8 != null ? "Save" : "Add";
      this.m34(context, var91, var74, var88, var78, 20.0F, var7, var8, var21, "actionBtn", var11);
      var88 += 25.0F;
      this.m34(
         context,
         "Randomize",
         var74,
         var88,
         var78,
         20.0F,
         var7,
         var8,
         var21,
         "randBtn",
         var11
      );
      super.render(context, mouseX, mouseY, deltaTicks);
   }

   private void m33(DrawContext var1, String var2, float var3, float var4, float var5, float var6, float var7, float var8, Color var9, float var10) {
      boolean var11 = var6 >= var3 && var6 <= var3 + var5 && var7 >= var4 && var7 <= var4 + var5;
      Color var12 = var11 ? var9 : new Color(var9.getRed(), var9.getGreen(), var9.getBlue(), Math.round(150.0F));
      float var13 = Math.max(0.0F, Math.min(1.0F, var10));
      var12 = new Color(var12.getRed(), var12.getGreen(), var12.getBlue(), Math.round((float)var12.getAlpha() * var8 * var13));
      if (var13 > 0.05F) {
         float var14 = var3 + (1.0F - var10) * 20.0F;
         Render2DUtil.m219(var1, FontRenderUtil.f3, var14 + var5 / 2.0F, var4 + 2.0F, var2, 10.0F, var12, "center");
      }
   }

   private void m34(
      DrawContext var1, String var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, String var10, float var11
   ) {
      boolean var12 = var7 >= var3 && var7 <= var3 + var5 && var8 >= var4 && var8 <= var4 + var6;
      float var13 = EasingUtil.m3(this.f11.getOrDefault(var10, 0.0F));
      float var14 = var12 ? 1.0F : 0.0F;
      float var15 = this.f12.getOrDefault(var10, 0.0F);
      var15 += (var14 - var15) * (var11 / 60.0F);
      this.f12.put(var10, var15);
      float var16 = 1.0F + EasingUtil.m3(var15) * 0.05F;
      float var17 = var16 - var13 * 0.05F;
      float var18 = var5 * var17;
      float var19 = var6 * var17;
      float var20 = var3 + (var5 - var18) / 2.0F;
      float var21 = var4 + (var6 - var19) / 2.0F;
      float var22 = 4.0F;
      Render2DUtil.m198(var20, var21, var18, var19, var22 + 3.0F, 5.0F, var9, new Color(150, 150, 150, 20));
      Color var23 = var12 ? new Color(248, 250, 252, Math.round(255.0F * var9)) : new Color(185, 190, 196, Math.round(210.0F * var9));
      Render2DUtil.m206(var1, var20 + var18 / 2.0F, var21 + 5.0F * var17, var2, 10.0F * var17, var23, "center");
   }

   public boolean mouseClicked(Click click, boolean doubled) {
      float var3 = (float)Render2DUtil.m113();
      float var4 = (float)Render2DUtil.m189();
      double var5 = click.x();
      double var7 = click.y();
      int var9 = click.button();
      float var10 = Render2DUtil.m3((float)var5);
      float var11 = Render2DUtil.m151((float)var7);
      float var12 = 320.0F;
      float var13 = 240.0F;
      float var14 = var3 / 2.0F;
      float var15 = var4 / 2.0F;
      float var16 = var14 - var12 / 2.0F;
      float var17 = var15 - var13 / 2.0F;
      float var18 = var16 + 10.0F;
      float var19 = var17 + 55.0F;
      float var20 = var12 - 130.0F;
      float var21 = var13 - 70.0F;
      if (var10 >= var18 && var10 <= var18 + var20 && var11 >= var19 && var11 <= var19 + var21) {
         List var22 = AltManager.m369();
         float var23 = var19 + 5.0F + this.f4;

         for (int var24 = 0; var24 < var22.size(); var24++) {
            float var25 = 26.0F;
            if (var10 >= var18 + 5.0F && var10 <= var18 + var20 - 5.0F && var11 >= var23 && var11 <= var23 + var25) {
               float var26 = this.f7.getOrDefault(((AltManager$1)var22.get(var24)).f1, 0.0F);
               if (var26 > 0.5F) {
                  float var27 = 14.0F;
                  float var28 = var18 + var20 - 5.0F - var27 * 3.0F - 10.0F;
                  if (var10 >= var28 && var10 <= var28 + var27) {
                     AltManager.m60(((AltManager$1)var22.get(var24)).f1);
                     return true;
                  }

                  if (var10 >= var28 + var27 + 5.0F && var10 <= var28 + var27 * 2.0F + 5.0F) {
                     this.f8 = ((AltManager$1)var22.get(var24)).f1;
                     this.f2 = this.f8;
                     this.f3 = true;

                     for (int var40 = 0; var40 < 16; var40++) {
                        this.f17[var40] = var40 < this.f2.length() ? 1.0F : 0.0F;
                     }

                     return true;
                  }

                  if (var10 >= var28 + (var27 + 5.0F) * 2.0F && var10 <= var28 + var27 * 3.0F + 10.0F) {
                     AltManager.m21(((AltManager$1)var22.get(var24)).f1);
                     return true;
                  }
               }

               float var38 = 16.0F;
               float var39 = var18 + 10.0F + var38 + 6.0F - var26 * (var38 + 6.0F);
               float var29 = FontRenderUtil.m235(((AltManager$1)var22.get(var24)).f1, 10.0F);
               float var30 = var18 + 10.0F;
               float var31 = Math.max(var30 + var38 * (1.0F - var26), var39 + var29);
               if (var10 >= var30 && var10 <= var31 && var11 >= var23 + 5.0F && var11 <= var23 + 21.0F) {
                  this.m16(((AltManager$1)var22.get(var24)).f1);
               }

               return true;
            }

            var23 += var25 + 2.0F;
         }
      }

      float var32 = var18 + var20 + 10.0F;
      float var33 = var12 - var20 - 30.0F;
      this.f3 = var10 >= var32 && var10 <= var32 + var33 && var11 >= var19 && var11 <= var19 + 20.0F;
      if (this.f3 && var9 == 0) {
         this.f11.put("field", 1.0F);
      }

      if (!this.f3
         && var9 == 0
         && var10 >= var16
         && var10 <= var16 + var12
         && var11 >= var17
         && var11 <= var17 + var13
         && (this.f8 == null || !this.m35(var10, var11, var32, var19 + 30.0F, var33, 20.0F))) {
         this.f8 = null;
      }

      float var34 = var19 + 30.0F;
      if (!this.m35(var10, var11, var32, var34, var33, 20.0F)) {
         var34 += 25.0F;
         if (this.m35(var10, var11, var32, var34, var33, 20.0F)) {
            this.f11.put("randBtn", 1.0F);
            String var37 = this.m37();
            AltManager.m16(var37);
            return true;
         } else {
            return super.mouseClicked(click, doubled);
         }
      } else {
         this.f11.put("actionBtn", 1.0F);
         if (!this.f2.isEmpty()) {
            if (this.f8 != null) {
               AltManager.m77(this.f8, this.f2);
               this.f8 = null;
            } else {
               AltManager.m16(this.f2);
            }

            this.f2 = "";

            for (int var36 = 0; var36 < 16; var36++) {
               this.f17[var36] = 0.0F;
            }
         }

         return true;
      }
   }

   private boolean m35(float var1, float var2, float var3, float var4, float var5, float var6) {
      return var1 >= var3 && var1 <= var3 + var5 && var2 >= var4 && var2 <= var4 + var6;
   }

   public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
      this.f4 += (float)(verticalAmount * 15.0);
      if (this.f4 > 0.0F) {
         this.f4 = 0.0F;
      }

      return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
   }

   private void m16(String var1) {
      ((IMinecraftClient)this.client).setSession(this.m36(this.client.getSession(), var1));
   }

   private Session m36(Session var1, String var2) {
      try {
         Constructor var3 = Session.class.getDeclaredConstructor(String.class, UUID.class, String.class, Optional.class, Optional.class);
         var3.setAccessible(true);
         return (Session)var3.newInstance(var2, UUID.randomUUID(), var1.getAccessToken(), Optional.empty(), Optional.empty());
      } catch (Exception var4) {
         throw new RuntimeException(var4);
      }
   }

   private String m37() {
      String[] var1 = new String[]{
         "sacrificed",
         "angel",
         "dark",
         "shadow",
         "blood",
         "crimson",
         "silent",
         "dead",
         "cursed",
         "broken",
         "lost",
         "fallen",
         "hollow",
         "pale",
         "grim",
         "frost",
         "night",
         "doom",
         "void",
         "ghost",
         "weeping",
         "black",
         "ruined",
         "bleeding",
         "forsaken",
         "mystic",
         "cruel",
         "cold",
         "bitter",
         "fatal",
         "toxic",
         "venom",
         "death",
         "fear",
         "pain",
         "agony",
         "grief",
         "sorrow",
         "tragic",
         "gothic",
         "vamp",
         "witch",
         "demon",
         "devil",
         "evil",
         "sin",
         "vile",
         "pure",
         "divine",
         "holy",
         "sacred",
         "abyss",
         "phantom",
         "specter",
         "spirit",
         "wraith",
         "ghoul",
         "vampire",
         "werewolf",
         "dragon",
         "beast",
         "monster",
         "astral",
         "lunar",
         "solar",
         "cosmic",
         "stellar",
         "twilight",
         "midnight",
         "dusk",
         "eclipse",
         "storm",
         "thunder",
         "rain",
         "wind",
         "fire",
         "flame",
         "ash",
         "ember",
         "smoke",
         "dust",
         "iron",
         "steel",
         "silver",
         "gold",
         "ruby",
         "onyx",
         "opal",
         "jade",
         "pearl",
         "diamond",
         "crystal",
         "glass",
         "mirror",
         "dream",
         "nightmare",
         "vision",
         "starlight",
         "moonlight",
         "sad",
         "lonely",
         "hidden",
         "secret",
         "blind",
         "freezing",
         "burning",
         "glowing",
         "fading",
         "dying",
         "crying",
         "sighing",
         "sleeping",
         "waking",
         "dreaming",
         "falling",
         "flying",
         "drowning",
         "sinking",
         "floating",
         "rising",
         "hiding",
         "seeking",
         "losing",
         "taking",
         "breaking",
         "destroying",
         "killing",
         "saving",
         "healing",
         "hurting",
         "loving",
         "hating",
         "kissing",
         "biting",
         "scratching",
         "feeling",
         "surviving",
         "escaping",
         "fleeing",
         "crawling",
         "creeping",
         "sneaking",
         "waiting",
         "watching",
         "listening",
         "somber",
         "morbid",
         "ghastly",
         "macabre",
         "eerie",
         "spooky",
         "creepy",
         "sinister",
         "diabolical",
         "fiendish",
         "hellish",
         "infernal",
         "wicked",
         "corrupt",
         "twisted",
         "warped",
         "sick",
         "deranged",
         "insane",
         "mad",
         "psycho",
         "manic",
         "frantic",
         "wild",
         "savage",
         "feral",
         "rabid",
         "vicious",
         "brutal",
         "ruthless",
         "merciless",
         "pitiless",
         "heartless",
         "soulless",
         "mindless",
         "faceless",
         "nameless",
         "formless",
         "endless",
         "timeless",
         "deathless",
         "immortal",
         "eternal",
         "infinite",
         "grave",
         "tomb",
         "crypt",
         "vault",
         "cage",
         "trap",
         "snare",
         "net",
         "web"
      };
      String[] var2 = new String[]{
         "heart",
         "knife",
         "blade",
         "soul",
         "tears",
         "blood",
         "moon",
         "star",
         "scythe",
         "shade",
         "thorn",
         "crown",
         "reaper",
         "raven",
         "rose",
         "dust",
         "ash",
         "bone",
         "skull",
         "ghost",
         "flame",
         "bane",
         "beast",
         "demon",
         "angel",
         "wing",
         "sword",
         "dagger",
         "fang",
         "claw",
         "horn",
         "eye",
         "gaze",
         "kiss",
         "bite",
         "mark",
         "scar",
         "vein",
         "pulse",
         "breath",
         "song",
         "cry",
         "scream",
         "whisper",
         "echo",
         "shadow",
         "night",
         "mist",
         "fog",
         "cloud",
         "vapor",
         "poison",
         "cure",
         "life",
         "death",
         "rebirth",
         "flesh",
         "skin",
         "hair",
         "tail",
         "feather",
         "silk",
         "thread",
         "chain",
         "wire",
         "cord",
         "path",
         "way",
         "gate",
         "door",
         "wall",
         "room",
         "base",
         "fort",
         "castle",
         "tower",
         "city",
         "town",
         "kingdom",
         "empire",
         "world",
         "planet",
         "galaxy",
         "universe",
         "dimension",
         "realm",
         "domain",
         "space",
         "heaven",
         "hell",
         "purgatory",
         "limbo",
         "nexus",
         "core",
         "edge",
         "end",
         "start",
         "origin",
         "source",
         "seed",
         "tree",
         "flower",
         "leaf",
         "branch",
         "root",
         "grass",
         "weed",
         "bush",
         "forest",
         "wood",
         "jungle",
         "swamp",
         "desert",
         "sand",
         "dirt",
         "mud",
         "rock",
         "stone",
         "mountain",
         "hill",
         "valley",
         "cliff",
         "cave",
         "mine",
         "pit",
         "hole",
         "volcano",
         "ocean",
         "sea",
         "lake",
         "river",
         "stream",
         "drop",
         "tear",
         "sweat",
         "nectar",
         "honey",
         "wax",
         "amber",
         "coral",
         "shell",
         "ivory",
         "hoof",
         "slayer",
         "walker",
         "hunter",
         "bringer",
         "seeker",
         "keeper",
         "master",
         "lord",
         "king",
         "queen",
         "prince",
         "princess",
         "child",
         "boy",
         "girl",
         "man",
         "woman",
         "god",
         "goddess",
         "deity",
         "idol",
         "hero",
         "villain",
         "friend",
         "foe",
         "enemy",
         "ally",
         "lover",
         "partner",
         "mate",
         "companion",
         "pet",
         "slave",
         "servant",
         "ruler",
         "leader",
         "follower",
         "guide",
         "teacher",
         "student",
         "learner",
         "speaker",
         "singer",
         "dancer",
         "player",
         "maker",
         "creator",
         "destroyer",
         "killer",
         "savior",
         "healer",
         "hurter",
         "pain",
         "agony",
         "fear",
         "terror",
         "horror",
         "dread",
         "panic",
         "fright",
         "shock",
         "awe",
         "wonder",
         "magic",
         "spell",
         "charm",
         "hex",
         "curse",
         "jinx",
         "trick",
         "illusion",
         "dream",
         "nightmare",
         "vision",
         "memory",
         "thought",
         "mind",
         "brain",
         "spirit",
         "phantom",
         "specter",
         "wraith",
         "ghoul",
         "zombie",
         "mummy",
         "vampire",
         "werewolf",
         "dragon",
         "serpent",
         "snake",
         "viper",
         "cobra",
         "spider",
         "scorpion",
         "wasp",
         "bee",
         "ant",
         "fly",
         "bug",
         "worm",
         "slug",
         "snail",
         "leech"
      };
      Random var3 = new Random();
      String var4 = "";

      do {
         String var5 = var1[var3.nextInt(var1.length)];
         String var6 = var2[var3.nextInt(var2.length)];
         var4 = var5 + var6;
      } while (var4.length() > 16);

      return var4;
   }

   public boolean keyPressed(KeyInput input) {
      if (input.key() == 256) {
         this.client.setScreen(this.f1);
         return true;
      } else {
         if (this.f3) {
            if (input.key() == 259 && this.f2.length() > 0) {
               this.f2 = this.f2.substring(0, this.f2.length() - 1);
               this.f17[this.f2.length()] = 0.0F;
               return true;
            }

            if (input.key() == 257) {
               if (!this.f2.isEmpty()) {
                  if (this.f8 != null) {
                     AltManager.m77(this.f8, this.f2);
                     this.f8 = null;
                  } else {
                     AltManager.m16(this.f2);
                  }

                  this.f2 = "";

                  for (int var3 = 0; var3 < 16; var3++) {
                     this.f17[var3] = 0.0F;
                  }
               }

               return true;
            }

            if (input.key() == 86
               && (GLFW.glfwGetKey(this.client.getWindow().getHandle(), 341) == 1 || GLFW.glfwGetKey(this.client.getWindow().getHandle(), 345) == 1)) {
               String var2 = this.client.keyboard.getClipboard();
               if (var2 != null) {
                  this.f2 = this.f2 + var2.replaceAll("[^a-zA-Z0-9_]", "");
                  if (this.f2.length() > 16) {
                     this.f2 = this.f2.substring(0, 16);
                  }
               }

               return true;
            }
         }

         return super.keyPressed(input);
      }
   }

   public boolean charTyped(CharInput input) {
      if (this.f3 && input.isValidChar() && this.f2.length() < 16) {
         this.f2 = this.f2 + input.asString();
         return true;
      } else {
         return super.charTyped(input);
      }
   }
}

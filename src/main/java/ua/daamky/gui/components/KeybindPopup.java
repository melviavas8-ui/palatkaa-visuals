package ua.daamky.gui.components;

import ua.daamky.gui.theme.ThemeManager;
import ua.daamky.gui.theme.ThemeManager$1;
import ua.daamky.settings.BindSetting;
import ua.daamky.settings.KeybindSetting;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Module;
import ua.daamky.utils.ScissorUtil;
import ua.daamky.utils.SettingRenderer;
import ua.daamky.utils.SettingRendererRegistry;
import ua.daamky.utils.SettingRowRenderer;
import ua.daamky.utils.KeybindFormatter;
import ua.daamky.utils.render.Render2DUtil;
import ua.daamky.utils.render.fonts.FontRenderUtil;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class KeybindPopup {
   private static final float f1 = 38.0F;
   private static final float f2 = 8.8F;
   private static final float f3 = 6.8F;
   private static final float f4 = 8.0F;
   private static final float f5 = 0.0045F;
   private static final float f6 = 0.006F;
   private static final float f7 = 0.01F;
   private static final float f8 = 0.01F;
   private static final float f9 = 3.0F;
   private static KeybindPopup f10;
   private static final SettingRendererRegistry f11 = new SettingRendererRegistry();
   private final Module f12;
   private final SettingRowRenderer f13;
   private float f14;
   private float f15;
   private float f16;
   private float f17 = 1.0F;
   private float f18 = 38.0F;
   private float f19;
   private float f20;
   private float f21;
   private long f22;
   private boolean f23;
   private boolean f24;
   private KeybindSetting f25;
   private Setting f26;
   private final Set<Setting> f27 = new HashSet<>();
   private final Map<Setting, Float> f28 = new HashMap<>();
   private float f29;
   private long f30;
   private float f31;
   private boolean f32;
   private boolean f33;
   private float f34;
   private float f35;
   private float f36;
   private float f37 = 16.0F;

   public KeybindPopup(Module var1) {
      this.f12 = var1;
      this.f13 = new SettingRowRenderer(this.f27::contains, var1x -> this.f28.getOrDefault(var1x, 0.0F), this::m1399);
      this.f19 = var1.isEnabled() ? 1.0F : 0.0F;
      this.f22 = System.currentTimeMillis();
   }

   public Module m1392() {
      return this.f12;
   }

   public void m947(DrawContext var1, float var2, float var3, float var4, float var5, float var6) {
      this.m1346(var1, var2, var3, var4, var5, var6, 1.0F, 1.0F);
   }

   private String daamky$wrapKey;
   private float daamky$wrapW = -1.0F;
   private float daamky$wrapS = -1.0F;
   private List<String> daamky$wrapVal = List.of();

   // перенос строк описания считается только когда меняется текст/ширина/размер, а не каждый кадр
   private List<String> daamky$wrapCached(String var1, float var2, float var3) {
      if (this.daamky$wrapKey == null || !this.daamky$wrapKey.equals(var1) || this.daamky$wrapW != var2 || this.daamky$wrapS != var3) {
         this.daamky$wrapVal = this.m1401(var1, var2, var3, 2);
         this.daamky$wrapKey = var1;
         this.daamky$wrapW = var2;
         this.daamky$wrapS = var3;
      }

      return this.daamky$wrapVal;
   }

   public void m1346(DrawContext var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      this.f14 = var2;
      this.f15 = var3;
      this.f16 = var4;
      this.f17 = var7;
      String var9 = this.f12.getDescription();
      float var10 = 6.8F * var7;
      List<String> var11 = var9 != null && !var9.isEmpty() ? this.daamky$wrapCached(var9, var4 - 16.0F * var7, var10) : List.of();
      this.f18 = var11.isEmpty() ? 22.0F : 19.0F + (float)var11.size() * 7.0F + 7.0F;
      float var12 = this.f18 * var7;
      this.f33 = this.m35(var5, var6, var2, var3, var4, var12);
      this.m286();
      Color var13 = ThemeManager.m1379();
      float var14 = this.m525() * this.f20;
      float var15 = 3.0F * this.f20;
      float var16 = (this.f18 + var15 + var14) * var7;
      if (this.f33 && !this.f32) {
         MinecraftClient.getInstance()
            .getSoundManager()
            .play(
               PositionedSoundInstance.ui(
                  SoundEvent.of(Identifier.of("daamky", "scroll")), 1.0F, 1.0F
               )
            );
      }

      this.f32 = this.f33;
      float var17 = var8 * var8 * var8;
      if (var17 > 0.02F) {
         int var18 = ThemeManager.f4 == ThemeManager$1.f1 ? 72 : 108;
         Color var19 = new Color(10, 12, 16, Math.round((float)var18 + 26.0F * this.f34));
         Render2DUtil.m195(var2, var3, var4, var16, 6.0F * var7, this.m336(var19, var17));
         if (this.f19 > 0.01F) {
            Render2DUtil.m195(
               var2, var3, var4, var16, 6.0F * var7, this.m336(new Color(var13.getRed(), var13.getGreen(), var13.getBlue()), this.f19 * 0.12F * var17)
            );
         }

         if (this.f20 > 0.02F) {
            Render2DUtil.m195(
               var2 + 6.0F * var7, var3 + this.f18 * var7, var4 - 12.0F * var7, 0.6F * var7, 0.0F, this.m336(new Color(255, 255, 255, 16), var17 * this.f20)
            );
         }

         Color var20 = new Color(255, 255, 255, Math.round(14.0F + 12.0F * this.f34));
         Color var21 = this.m944(var20, new Color(var13.getRed(), var13.getGreen(), var13.getBlue(), 65), this.f19);
         Render2DUtil.m202(var2, var3, var4, var16, 6.0F * var7, 0.6F * var7, this.m336(var21, var17));
      }

      boolean var49 = !this.m1400().isEmpty();
      float var50 = 8.5F * var7;
      float var51 = var2 + var4 - 8.0F * var7 - var50;
      float var52 = var3 + 6.5F * var7;
      boolean var22 = this.m35(var5, var6, var51 - 2.0F * var7, var52 - 2.0F * var7, var50 + 4.0F * var7, var50 + 4.0F * var7);
      this.f36 = this.m334(this.f36, var22 ? 1.0F : 0.0F, 0.02F);
      if (var17 > 0.02F) {
         this.m218(var51, var52, this.f19, var8, this.f36, var13);
      }

      float var23 = 7.0F * var7;
      float var24 = var49 ? FontRenderUtil.m237(FontRenderUtil.f3, "O", var23) : 0.0F;
      float var25 = var51 - 7.0F * var7 - var24;
      this.f35 = this.m334(this.f35, var49 && this.m1354(var5, var6) ? 1.0F : 0.0F, 0.02F);
      if (var49 && var17 > 0.02F) {
         Color var26 = this.m944(new Color(150, 150, 156), new Color(232, 232, 238), this.f35);
         var26 = this.m944(var26, var13, this.f20);
         Render2DUtil.m208(var1, FontRenderUtil.f3, var25, var3 + 7.25F * var7, "O", var23, this.m336(var26, var17));
      }

      float var54 = (var49 ? var25 : var51) - 4.0F * var7;
      float var27 = 8.8F * var7;
      float var28 = var3 + 7.0F * var7;
      Color var29 = this.m944(new Color(this.f33 ? 150 : 120, this.f33 ? 150 : 120, this.f33 ? 156 : 126), var13, this.f19);
      Color var30 = new Color(255, 255, 255, Math.round(160.0F + 95.0F * this.f19));
      float var31 = 9.0F * var7;
      String var32 = this.m309();
      float var33 = FontRenderUtil.m237(FontRenderUtil.f3, var32, var31);
      float var34 = var2 + 8.0F * var7;
      float var35 = var28 + (FontRenderUtil.m3(var27) - FontRenderUtil.m239(FontRenderUtil.f3, var31)) / 2.0F;
      Render2DUtil.m208(var1, FontRenderUtil.f3, var34, var35, var32, var31, this.m336(var29, var8));
      float var36 = var34 + var33 + 6.0F * var7;
      float var37 = Math.max(10.0F * var7, var54 - var36);
      String var38 = this.f12.getName();
      float var39 = FontRenderUtil.m235(var38, var27);
      if (var39 <= var37) {
         Render2DUtil.m205(var1, var36, var28, var38, var27, this.m336(var30, var8 * (1.0F - this.f21)));
         this.f29 = 0.0F;
         this.f30 = 0L;
         this.f31 = 0.0F;
      } else {
         float var40 = var39 - var37 + 6.0F * var7;
         long var41 = System.currentTimeMillis();
         long var43 = this.f30 == 0L ? var41 : this.f30;
         float var45 = Math.min(50.0F, (float)(var41 - var43)) / 1000.0F;
         this.f30 = var41;
         if (!this.f33) {
            if (this.f29 > 0.0F) {
               this.f29 = Math.max(0.0F, this.f29 - 46.8F * var45);
            } else if (this.f29 < 0.0F) {
               this.f29 = 0.0F;
            }

            this.f31 = 0.0F;
         } else {
            this.f31 += var45;
            float var46 = Math.max(1.8F, var40 * 2.0F / 26.0F);
            float var47 = this.f31 % var46 / var46;
            this.f29 = var40 * (0.5F - 0.5F * (float)Math.cos((double)var47 * Math.PI * 2.0));
         }

         ScissorUtil.m357((double)var36, (double)(var28 - 2.0F * var7), (double)var37, (double)(var27 + 6.0F * var7));
         Render2DUtil.m205(var1, var36 - this.f29, var28, var38, var27, this.m336(var30, var8 * (1.0F - this.f21)));
         ScissorUtil.m29();
      }

      Render2DUtil.m205(
         var1, var36, var28, this.m999("Нажмите бинд", var37, var27), var27, this.m336(var30, var8 * this.f21)
      );
      int var55 = this.f12.getKey();
      float var56 = 6.5F * var7;
      String var42 = var55 > 0 ? KeybindFormatter.m90(var55) : null;
      if (var42 != null && !var42.isEmpty() && this.f21 < 0.5F && var17 > 0.02F) {
         float var57 = 10.0F * var7;
         float var44 = FontRenderUtil.m235(var42, var56) + 8.0F * var7;
         float var62 = var36 + Math.min(var39, var37) + 5.0F * var7;
         if (var62 + var44 <= var54) {
            float var65 = var28 + (FontRenderUtil.m3(var27) - var57) / 2.0F;
            Render2DUtil.m195(var62, var65, var44, var57, 3.0F * var7, this.m336(new Color(255, 255, 255, 16), var17));
            Render2DUtil.m202(var62, var65, var44, var57, 3.0F * var7, 0.5F * var7, this.m336(new Color(255, 255, 255, 30), var17));
            Render2DUtil.m206(
               var1,
               var62 + var44 / 2.0F,
               var65 + (var57 - FontRenderUtil.m3(var56)) / 2.0F,
               var42,
               var56,
               this.m336(new Color(255, 255, 255, 205), var17),
               "center"
            );
         }
      }

      if (!var11.isEmpty() && this.f21 < 0.5F) {
         float var58 = var2 + 8.0F * var7;
         Color var60 = new Color(150, 150, 156);
         float var63 = var8 * (1.0F - this.f21) * (0.6F + 0.4F * this.f19);
         float var66 = var3 + 19.0F * var7;

         for (String var48 : var11) {
            Render2DUtil.m205(var1, var58, var66, var48, var10, this.m336(var60, var63));
            var66 += 7.0F * var7;
         }
      }

      if (this.f20 > 0.02F) {
         float var59 = var3 + (this.f18 + var15) * var7;
         float var61 = var8 * this.f20;

         for (Setting var67 : this.m1400()) {
            float var69 = this.m1397(var67) * var7;
            this.m1394(var1, var67, var2 + 8.0F * var7, var59, var4 - 16.0F * var7, var5, var6, var7, var61);
            var59 += var69;
         }
      }
   }

   private void m218(float var1, float var2, float var3, float var4, float var5, Color var6) {
      float var7 = 8.5F * this.f17;
      float var8 = 2.975F * this.f17;
      Render2DUtil.m195(var1, var2, var7, var7, var8, this.m336(new Color(17, 17, 18), 0.357F * var4));
      if (var3 < 0.996F) {
         float var9 = var4 * (1.0F - var3);
         float var10 = 0.5F * this.f17;
         Render2DUtil.m202(
            var1 + 1.275F * this.f17,
            var2 + 1.275F * this.f17,
            var7 - 2.55F * this.f17,
            var7 - 2.55F * this.f17,
            2.55F * this.f17,
            var10,
            this.m336(var6, (35.0F + 45.0F * var5) / 255.0F * var9)
         );
         Render2DUtil.m202(
            var1 + 2.55F * this.f17,
            var2 + 2.55F * this.f17,
            var7 - 5.1F * this.f17,
            var7 - 5.1F * this.f17,
            2.125F * this.f17,
            var10,
            this.m336(var6, (20.0F + 25.0F * var5) / 255.0F * var9)
         );
      }

      if (var3 > 0.004F) {
         Render2DUtil.m195(var1, var2, var7, var7, var8, this.m336(var6, var3 * var4));
         float var11 = 2.125F * this.f17;
         float var12 = var7 - var11 * 2.0F;
         Render2DUtil.m195(var1 + var11, var2 + var11, var12, var12, var12 * 0.5F, this.m336(Color.WHITE, var3 * var4));
      }
   }

   public boolean m1332(float var1, float var2, int var3) {
      if (var3 == 0) {
         this.m308();
      }

      if (this.f25 != null) {
         if (KeybindSetting.m12(var3)) {
            this.f25.m15(KeybindSetting.m9(var3));
            this.m299();
            return true;
         } else {
            return var3 == 0;
         }
      } else if (this.f24) {
         if (KeybindSetting.m12(var3)) {
            this.f12.setKey(KeybindSetting.m9(var3));
            this.m299();
            return true;
         } else {
            return var3 == 0;
         }
      } else if (var3 != 0 && var3 != 1 && var3 != 2) {
         return false;
      } else {
         if (this.f23 && this.f20 > 0.85F) {
            float var4 = this.f15 + this.f18 + 3.0F;

            for (Setting var6 : this.m1400()) {
               float var7 = this.m1397(var6);
               if (this.m35(var1, var2, this.f14, var4, this.f16, var7)) {
                  float var8 = this.f14 + 8.0F * this.f17;
                  float var9 = this.f16 - 16.0F * this.f17;
                  if (this.m1395(var6, var3, var1 - var8, var2 - var4, var9)) {
                     this.f26 = var6;
                  }

                  return true;
               }

               var4 += var7;
            }
         }

         if (!this.m35(var1, var2, this.f14, this.f15, this.f16, this.f18)) {
            return false;
         } else if (var3 == 0 && this.m1354(var1, var2)) {
            this.f23 = !this.f23;
            MinecraftClient.getInstance()
               .getSoundManager()
               .play(
                  PositionedSoundInstance.ui(
                     SoundEvent.of(Identifier.of("daamky", "switchcategory")), 1.0F, 1.0F
                  )
               );
            return true;
         } else {
            if (var3 == 0) {
               this.f12.toggle();
            } else if (var3 == 1) {
               this.f23 = !this.f23;
               MinecraftClient.getInstance()
                  .getSoundManager()
                  .play(
                     PositionedSoundInstance.ui(
                        SoundEvent.of(Identifier.of("daamky", "switchcategory")),
                        1.0F,
                        1.0F
                     )
                  );
            } else {
               this.m23();
            }

            return true;
         }
      }
   }

   public boolean m1337(float var1, float var2, int var3) {
      if (this.f26 == null) {
         return false;
      } else {
         SettingRenderer var4 = f11.m1454(this.f26);
         float var5 = this.f14 + 8.0F * this.f17;
         float var6 = this.f16 - 16.0F * this.f17;
         float var7 = this.m1398(this.f26);
         return var4.m1423(this.f26, this.f13, var3, var1 - var5, var2 - var7, var6);
      }
   }

   public void m314() {
      this.f26 = null;
   }

   public boolean m1393(int var1) {
      BindSetting var2 = this.m1396();
      if (var2 != null) {
         if (var1 == 256 || var1 == 257 || var1 == 335) {
            var2.m4(false);
            return true;
         } else if (var1 == 259) {
            var2.m29();
            return true;
         } else {
            return true;
         }
      } else if (this.f25 != null) {
         this.f25.m15(var1 != 256 && var1 != 261 ? var1 : 0);
         this.f25 = null;
         this.m299();
         return true;
      } else if (!this.f24) {
         return false;
      } else {
         this.f12.setKey(var1 != 256 && var1 != 261 ? var1 : 0);
         this.m299();
         return true;
      }
   }

   public boolean m20(String var1) {
      BindSetting var2 = this.m1396();
      if (var2 == null) {
         return false;
      } else {
         var2.m21(var1);
         return true;
      }
   }

   public float m272() {
      return this.f18 + (3.0F + this.m525()) * this.f20;
   }

   private void m286() {
      long var1 = System.currentTimeMillis();
      float var3 = Math.min(50.0F, (float)(var1 - this.f22));
      this.f22 = var1;
      this.f37 = var3;
      float var4 = this.f33 ? 1.0F : 0.0F;
      float var5 = 1.0F - (float)Math.exp((double)(-0.016F * var3));
      this.f34 = this.f34 + (var4 - this.f34) * Math.clamp(var5, 0.0F, 1.0F);
      if (Math.abs(this.f34 - var4) < 0.001F) {
         this.f34 = var4;
      }

      float var6 = this.f12.isEnabled() ? 1.0F : 0.0F;
      if (this.f19 < var6) {
         this.f19 = Math.min(var6, this.f19 + var3 * 0.0045F);
      } else if (this.f19 > var6) {
         this.f19 = Math.max(var6, this.f19 - var3 * 0.0045F);
      }

      float var7 = this.f23 ? 1.0F : 0.0F;
      if (this.f20 < var7) {
         this.f20 = Math.min(var7, this.f20 + var3 * 0.006F);
      } else if (this.f20 > var7) {
         this.f20 = Math.max(var7, this.f20 - var3 * 0.006F);
      }

      float var8 = this.f24 ? 1.0F : 0.0F;
      if (this.f21 < var8) {
         this.f21 = Math.min(var8, this.f21 + var3 * 0.01F);
      } else if (this.f21 > var8) {
         this.f21 = Math.max(var8, this.f21 - var3 * 0.01F);
      }

      for (Setting var10 : this.m1400()) {
         float var11 = this.f28.getOrDefault(var10, 0.0F);
         float var12 = this.f27.contains(var10) ? 1.0F : 0.0F;
         if (var11 < var12) {
            var11 = Math.min(var12, var11 + var3 * 0.01F);
         } else if (var11 > var12) {
            var11 = Math.max(var12, var11 - var3 * 0.01F);
         }

         this.f28.put(var10, var11);
      }
   }

   private void m23() {
      if (f10 != null && f10 != this) {
         f10.m299();
      }

      f10 = this;
      this.f24 = true;
   }

   private void m299() {
      this.f24 = false;
      this.f25 = null;
      if (f10 == this) {
         f10 = null;
      }
   }

   private void m1394(DrawContext var1, Setting var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9) {
      SettingRenderer var10 = f11.m1454(var2);
      this.f13.m1387(var6, var7);
      var10.m1412(var1, var2, this.f13, var3, var4, var5, var8, var9);
   }

   private boolean m1395(Setting var1, int var2, float var3, float var4, float var5) {
      SettingRenderer var6 = f11.m1454(var1);
      boolean var7 = var6.m1411(var1, this.f13, var2, var3, var4, var5);
      if (var7 && var1 instanceof KeybindSetting var8 && var2 == 0) {
         this.m23();
         this.f25 = var8;
         var8.m15(-1);
      }

      return var7;
   }

   private void m308() {
      for (Setting var2 : this.f12.getSettings()) {
         if (var2 instanceof BindSetting var3) {
            var3.m4(false);
         }
      }
   }

   private BindSetting m1396() {
      for (Setting var2 : this.f12.getSettings()) {
         if (var2 instanceof BindSetting var3 && var3.m31()) {
            return var3;
         }
      }

      return null;
   }

   private float m525() {
      float var1 = 0.0F;

      for (Setting var3 : this.m1400()) {
         var1 += this.m1397(var3);
      }

      return var1;
   }

   private float m1397(Setting var1) {
      SettingRenderer var2 = f11.m1454(var1);
      return var2.m1410(var1, this.f13);
   }

   private float m1398(Setting var1) {
      float var2 = this.f15 + this.f18 + 3.0F;

      for (Setting var4 : this.m1400()) {
         if (var4 == var1) {
            return var2;
         }

         var2 += this.m1397(var4);
      }

      return this.f15;
   }

   private void m1399(Setting var1) {
      if (this.f27.contains(var1)) {
         this.f27.remove(var1);
      } else {
         this.f27.add(var1);
      }

      MinecraftClient.getInstance()
         .getSoundManager()
         .play(
            PositionedSoundInstance.ui(
               SoundEvent.of(Identifier.of("daamky", "switchcategory")), 1.0F, 1.0F
            )
         );
   }

   private List<Setting> m1400() {
      return this.f12.getSettings().stream().filter(Setting::isVisible).toList();
   }

   private boolean m35(float var1, float var2, float var3, float var4, float var5, float var6) {
      return var1 >= var3 && var1 <= var3 + var5 && var2 >= var4 && var2 <= var4 + var6;
   }

   private boolean m1354(float var1, float var2) {
      if (this.m1400().isEmpty()) {
         return false;
      } else {
         float var3 = 8.5F * this.f17;
         float var4 = this.f14 + this.f16 - 8.0F * this.f17 - var3;
         float var5 = 7.0F * this.f17;
         float var6 = FontRenderUtil.m237(FontRenderUtil.f3, "O", var5);
         float var7 = var4 - 7.0F * this.f17 - var6;
         float var8 = 4.0F * this.f17;
         return this.m35(var1, var2, var7 - var8, this.f15 + 4.0F * this.f17, var6 + var8 * 2.0F, 13.5F * this.f17);
      }
   }

   // $VF: Unable to simplify switch on enum
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private String m309() {
      return switch (this.f12.getCategory()) {
         case COMBAT -> "H";
         case RENDER -> "G";
         case MOVEMENT -> "D";
         case PLAYER -> "P";
         case MISC, MENU, CONFIG -> "F";
      };
   }

   private Color m944(Color var1, Color var2, float var3) {
      float var4 = this.m3(Math.clamp(var3, 0.0F, 1.0F));
      int var5 = (int)((float)var1.getRed() + (float)(var2.getRed() - var1.getRed()) * var4);
      int var6 = (int)((float)var1.getGreen() + (float)(var2.getGreen() - var1.getGreen()) * var4);
      int var7 = (int)((float)var1.getBlue() + (float)(var2.getBlue() - var1.getBlue()) * var4);
      int var8 = (int)((float)var1.getAlpha() + (float)(var2.getAlpha() - var1.getAlpha()) * var4);
      return new Color(var5, var6, var7, var8);
   }

   private float m334(float var1, float var2, float var3) {
      float var4 = 1.0F - (float)Math.exp((double)(-var3 * this.f37));
      float var5 = var1 + (var2 - var1) * Math.clamp(var4, 0.0F, 1.0F);
      return Math.abs(var5 - var2) < 0.001F ? var2 : var5;
   }

   private float m3(float var1) {
      return var1 < 0.5F ? 2.0F * var1 * var1 : 1.0F - (float)Math.pow((double)(-2.0F * var1 + 2.0F), 2.0) / 2.0F;
   }

   private Color m336(Color var1, float var2) {
      int var3 = (int)((float)var1.getAlpha() * Math.clamp(var2, 0.0F, 1.0F));
      return new Color(var1.getRed(), var1.getGreen(), var1.getBlue(), var3);
   }

   private List<String> m1401(String var1, float var2, float var3, int var4) {
      ArrayList var5 = new ArrayList();
      if (var1 == null) {
         return var5;
      } else {
         String[] var6 = var1.trim().split("\\s+");
         StringBuilder var7 = new StringBuilder();

         int var8;
         for (var8 = 0; var8 < var6.length; var8++) {
            String var9 = var6[var8];
            String var10 = var7.length() == 0 ? var9 : var7 + " " + var9;
            if (var7.length() != 0 && !(FontRenderUtil.m235(var10, var3) <= var2)) {
               var5.add(var7.toString());
               var7.setLength(0);
               var7.append(var9);
               if (var5.size() == var4 - 1) {
                  var8++;
                  break;
               }
            } else {
               var7.setLength(0);
               var7.append(var10);
            }
         }

         while (var8 < var6.length) {
            var7.append(" ").append(var6[var8]);
            var8++;
         }

         if (var7.length() > 0) {
            var5.add(var7.toString());
         }

         int var11 = var5.size() - 1;
         if (var11 >= 0) {
            var5.set(var11, this.m999((String)var5.get(var11), var2, var3));
         }

         return var5;
      }
   }

   private String m999(String var1, float var2, float var3) {
      if (FontRenderUtil.m235(var1, var3) <= var2) {
         return var1;
      } else {
         String var4 = "...";
         String var5 = var1;

         while (!var5.isEmpty() && FontRenderUtil.m235(var5 + var4, var3) > var2) {
            var5 = var5.substring(0, var5.length() - 1);
         }

         return var5.isEmpty() ? var4 : var5 + var4;
      }
   }
}

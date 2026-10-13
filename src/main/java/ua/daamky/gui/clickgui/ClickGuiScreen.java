package ua.daamky.gui.clickgui;

import ua.daamky.features.render.CapeManager;
import ua.daamky.gui.components.KeybindPopup;
import ua.daamky.gui.config.ConfigManager;
import ua.daamky.gui.theme.ThemeManager;
import ua.daamky.gui.theme.ThemeManager$1;
import ua.daamky.gui.theme.ThemeManager$2;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.ModuleManager;
import ua.daamky.utils.EasingUtil;
import ua.daamky.utils.ScissorUtil;
import ua.daamky.utils.client.UserProfile;
import ua.daamky.utils.render.Render2DUtil;
import ua.daamky.utils.render.shaders.HudBlurShader;
import ua.daamky.utils.render.TextureCache;
import ua.daamky.utils.render.fonts.FontRenderUtil;
import java.awt.Color;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.input.CharInput;
import net.minecraft.client.input.KeyInput;
import net.minecraft.client.render.Camera;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

public class ClickGuiScreen extends Screen {
   private static final Category[] f1 = new Category[]{Category.COMBAT, Category.MOVEMENT, Category.RENDER, Category.PLAYER, Category.MISC};
   private static final float f2 = 430.0F;
   private static final float f3 = 290.0F;
   private static final float f4 = 34.0F;
   private static final float f5 = 110.0F;
   private static final float f6 = 7.0F;
   private static final float f7 = 5.0F;
   private static final float f8 = 14.0F;
   private static final float f9 = 190.0F;
   private static final float f10 = 19.0F;
   private static final float f11 = 21.0F;
   private static final float f12 = 38.0F;
   private static final float f13 = 5.0F;
   private static final float f14 = 11.0F;
   private static final float f15 = 15.0F;
   private static final float f16 = 13.0F;
   private static final float f17 = 22.0F;
   private static final float f18 = 2.0F;
   private static final float f19 = 12.0F;
   private static final String f20 = "C";
   private static final String f21 = "S";
   private static final String f22 = "I";
   private static final String f23 = "B";
   private static final float f24 = 70.0F;
   private static final float f25 = 24.0F;
   private static final float f26 = 20.0F;
   private static final float f27 = 132.0F;
   private static final float f28 = 108.0F;
   private static final float f29 = 78.0F;
   private static final float f30 = 10.0F;
   private static final float f31 = 62.0F;
   private static final float f32 = 18.0F;
   private static final float f33 = 34.0F;
   private static final float f34 = 180.0F;
   private static final float f35 = 180.0F;
   private static final float f36 = 0.02F;
   private static final float f37 = 0.018F;
   private static final float f38 = 0.025F;
   private final Map<Category, List<KeybindPopup>> f39 = new EnumMap<>(Category.class);
   private final Map<Category, Float> f40 = new EnumMap<>(Category.class);
   private final List<KeybindPopup> f41 = new ArrayList<>();
   private float f42 = 0.0F;
   private float f43 = 0.0F;
   private float f44 = 0.0F;
   private float f45 = 0.0F;
   private final Map<Category, Float> f46 = new EnumMap<>(Category.class);
   private Category f47 = Category.COMBAT;
   private boolean f48 = true;
   private static final int f49 = 31;
   private ClickGuiScreen$3 f50 = ClickGuiScreen$3.f1;
   private float f51 = 1.0F;
   private final Map<String, Float> f52 = new HashMap<>();
   private float f53 = 16.0F;
   private float f54;
   private float f55;
   private float f56;
   private boolean f57;
   private String f58 = "Player";
   private long f59;
   private boolean f60;
   private boolean f61;
   private boolean f62;
   private boolean f63;
   private int f64 = -1;
   private float f65;
   private float f66;
   private float f67;
   private float f68;
   private float f69;
   private float f70;
   private int f71 = Integer.MIN_VALUE;
   private float f72;
   private float f73;
   private long f74;
   private long f75;
   private KeybindPopup f76;
   private boolean f77;
   private float f78;
   private long f79;
   private float f80;
   private float f81;
   private float f82;
   private boolean f83;
   private boolean f84;
   private final List<String> f85 = new ArrayList<>();
   private String f86 = null;
   private String f87 = "";
   private boolean f88 = false;
   private String f89 = "";
   private long f90 = 0L;
   private String f91 = "";
   private boolean f92 = false;
   private float f93 = 0.0F;
   private final List<ClickGuiScreen$2> f94 = new ArrayList<>();
   private static final float f95 = 900.0F;
   private static final float f96 = 600.0F;
   private static final double f97 = 2.6;
   private static long f98 = 0L;
   private static ClickGuiScreen f99;
   private static Vec3d f100;
   private static Vec3d f101;
   private static Quaternionf f102;
   private static boolean f103 = false;
   private static final int f104 = 4;
   private static final float f105 = 66.0F;
   private static final float f106 = 5.0F;
   private static final float f107 = 25.0F;
   private static final float f108 = 22.0F;
   private static final float f109 = 24.0F;
   private static final float f110 = 6.0F;
   private static final float f111 = 19.0F;
   private static final float f112 = 3.0F;
   private static final String[] f113 = new String[]{
      "Сохранить",
      "Загрузить",
      "Удалить",
      "Папка"
   };
   private static final String[] f114 = new String[]{
      "Обычный",
      "Цвет темы",
      "Жидкое стекло",
      "Градиент"
   };
   private static final String[] f115 = new String[]{
      "Матовый тёмный фон",
      "Размытие в цвете темы",
      "Полупрозрачная стеклянная панель",
      "Переход между двумя цветами темы"
   };
   private static final ThemeManager$1[] f116 = new ThemeManager$1[]{ThemeManager$1.f1, ThemeManager$1.f2, ThemeManager$1.f3, ThemeManager$1.f4};
   private static final float f117 = 27.0F;
   private static final float f118 = 4.0F;

   public ClickGuiScreen() {
      super(Text.of("Daamky"));

      try {
         String var1 = MinecraftClient.getInstance().getSession().getUsername();
         if (var1 != null && !var1.isEmpty()) {
            this.f58 = var1;
         }
      } catch (Throwable var2) {
      }

      this.m708();
      this.m707();
   }

   public void onDisplayed() {
      this.f60 = false;
      m63();
      this.f59 = System.currentTimeMillis();
      this.f75 = 0L;
      this.f68 = this.f61 ? 1.0F : 0.0F;
      this.f77 = false;
      this.f78 = 0.0F;
      float var1 = this.f61 ? this.f68 : 1.0F - this.f68;
      this.f74 = System.currentTimeMillis() - (long)(var1 * 180.0F);
      this.f70 = this.m646() ? 1.0F : 0.0F;
      MinecraftClient.getInstance()
         .getSoundManager()
         .play(
            PositionedSoundInstance.ui(
               SoundEvent.of(Identifier.of("daamky", "gui_open")), 1.0F, 1.0F
            )
         );
      super.onDisplayed();
   }

   public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
      HudBlurShader.f8 = 120L;
      try {
         this.daamky$renderInner(context, mouseX, mouseY, deltaTicks);
      } finally {
         HudBlurShader.f8 = 0L;
      }
   }

   private void daamky$renderInner(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
      // дробные координаты мыши: целые GUI-пиксели давали "ступеньки" в hover-анимациях
      float var5x = (float)mouseX;
      float var6x = (float)mouseY;
      try {
         var5x = (float)(this.client.mouse.getX() * (double)this.client.getWindow().getScaledWidth() / (double)Math.max(1, this.client.getWindow().getWidth()));
         var6x = (float)(this.client.mouse.getY() * (double)this.client.getWindow().getScaledHeight() / (double)Math.max(1, this.client.getWindow().getHeight()));
      } catch (Throwable ignored) {
      }

      float var5 = Render2DUtil.m3(var5x);
      float var6 = Render2DUtil.m151(var6x);
      this.f55 = var5;
      this.f56 = var6;
      this.m286();
      this.f76 = null;
      float var7 = this.m272();
      if (this.f60 && var7 <= 0.01F) {
         this.client.setScreen(null);
      } else {
         float var8 = this.f60 ? var7 * var7 : EasingUtil.m151(var7);
         float var9 = 0.88F + var8 * 0.12F;
         float var15x = (1.0F - var8) * 16.0F;
         float var11 = ((float)Render2DUtil.m113() - 430.0F) / 2.0F;
         float var12 = ((float)Render2DUtil.m189() - 290.0F) / 2.0F + var15x;
         float var13 = (float)Render2DUtil.m113() / 2.0F;
         float var14 = (float)Render2DUtil.m189() / 2.0F + var15x;
         this.m1326(context, var11, var12, var5, var6, var13, var14, var9, var8);
         super.render(context, mouseX, mouseY, deltaTicks);
      }
   }

   public void renderBackground(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
   }

   public static boolean m1324(DrawContext var0) {
      if (f98 != 0L && f99 != null && f100 != null && f102 != null) {
         MinecraftClient var1 = MinecraftClient.getInstance();
         if (var1.currentScreen == null && var1.player != null && var1.world != null) {
            long var2 = System.currentTimeMillis() - f98;
            if ((float)var2 >= 1500.0F) {
               f98 = 0L;
               return false;
            } else {
               Camera var4 = var1.gameRenderer.getCamera();
               if (var4 == null) {
                  return false;
               } else {
                  Vec3d var5 = var4.getCameraPos();
                  Vec3d var6 = f100.subtract(var5);
                  if (f101 != null && var6.lengthSquared() > 1.0E-4) {
                     double var7 = var5.subtract(f100).normalize().dotProduct(f101);
                     if (var7 <= 0.08) {
                        return false;
                     }
                  }

                  float var15 = (float)var2 <= 900.0F ? 0.0F : ((float)var2 - 900.0F) / 600.0F;
                  float var8 = var15 * var15 * var15;
                  int var9 = Render2DUtil.m113();
                  int var10 = Render2DUtil.m189();
                  if (var10 <= 0) {
                     return false;
                  } else {
                     float var11 = (float)Math.toRadians((double)((Integer)var1.options.getFov().getValue()).intValue());
                     float var12 = (float)var9 / (float)var10;
                     Matrix4f var13 = new Matrix4f().perspective(var11, var12, 0.05F, 1000.0F);
                     var13.mul(new Matrix4f().rotate(new Quaternionf(var4.getRotation()).conjugate()));
                     var13.translate((float)var6.x, (float)var6.y, (float)var6.z);
                     var13.rotate(f102);
                     float var14 = (float)(5.2 * Math.tan((double)var11 / 2.0) / (double)var10);
                     var13.scale(var14, -var14, var14);
                     var13.translate(-215.0F, -145.0F, 0.0F);
                     f99.m1325(var0, var13, 1.0F - var8);
                     return true;
                  }
               }
            }
         } else {
            f98 = 0L;
            return false;
         }
      } else {
         return false;
      }
   }

   private static void m63() {
      f98 = 0L;
   }

   private void m1325(DrawContext var1, Matrix4f var2, float var3) {
      this.f76 = null;
      this.m286();
      f103 = true;
      ScissorUtil.f2 = true;
      Render2DUtil.m190(var2);

      try {
         this.m1326(var1, 0.0F, 0.0F, -9999.0F, -9999.0F, 0.0F, 0.0F, 1.0F, var3);
      } finally {
         Render2DUtil.m190(null);
         ScissorUtil.f2 = false;
         f103 = false;
      }
   }

   public void close() {
      if (!this.f60) {
         ThemeManager.m29();
         float var2c = this.m272();
         this.f60 = true;
         this.f59 = System.currentTimeMillis() - (long)((1.0F - var2c) * 170.0F);
         MinecraftClient.getInstance()
            .getSoundManager()
            .play(
               PositionedSoundInstance.ui(
                  SoundEvent.of(Identifier.of("daamky", "gui_close")), 1.0F, 1.0F
               )
            );
         f98 = 0L;
         f99 = null;
         f100 = null;
         f101 = null;
         f102 = null;
      }
   }

   public boolean mouseClicked(Click click, boolean doubled) {
      if (!this.f60 && !(this.m272() < 0.6F)) {
         float var3 = Render2DUtil.m3((float)click.x());
         float var4 = Render2DUtil.m151((float)click.y());
         int var5 = click.button();
         float var6 = ((float)Render2DUtil.m113() - 430.0F) / 2.0F;
         float var7 = ((float)Render2DUtil.m189() - 290.0F) / 2.0F;
         if (var5 == 0 || var5 == 1) {
            float var8 = var6 + this.m273();
            float var9 = var7 + this.m274();
            this.f92 = this.m35(var3, var4, var8, var9, 190.0F, 19.0F);
            if (this.f92) {
               return true;
            }
         }

         if (var5 == 0) {
            for (int var14 = 0; var14 < f1.length; var14++) {
               if (this.m35(var3, var4, var6, var7 + this.m1327(var14), 110.0F, 22.0F)) {
                  Category var16 = f1[var14];
                  if (!this.f48 && this.f50 == ClickGuiScreen$3.f1 && var16 == this.f47) {
                     this.f48 = true;
                     this.f51 = 0.0F;
                     this.m1362();
                     this.m645();
                  } else {
                     this.f47 = var16;
                     this.f50 = ClickGuiScreen$3.f1;
                     this.f48 = false;
                     this.f51 = 0.0F;
                     this.m1362();
                     this.m645();
                  }

                  return true;
               }
            }

            if (this.m35(var3, var4, var6, var7 + this.m524(), 110.0F, 22.0F)) {
               if (this.f50 != ClickGuiScreen$3.f3) {
                  this.f50 = ClickGuiScreen$3.f3;
                  this.f51 = 0.0F;
               }

               return true;
            }

            if (this.m35(var3, var4, var6, var7 + this.m526(), 110.0F, 22.0F)) {
               if (this.f50 != ClickGuiScreen$3.f2) {
                  this.f50 = ClickGuiScreen$3.f2;
                  this.f51 = 0.0F;
                  this.f44 = 0.0F;
                  this.f45 = 0.0F;
                  this.m1362();
                  this.m645();
               }

               return true;
            }

            if (this.m35(var3, var4, var6, var7 + this.m525(), 110.0F, 22.0F)) {
               if (this.f50 != ClickGuiScreen$3.f4) {
                  this.f50 = ClickGuiScreen$3.f4;
                  this.f51 = 0.0F;
                  this.f88 = false;
                  this.m707();
                  this.m1362();
                  this.m645();
               }

               return true;
            }

            if (this.m35(var3, var4, var6, var7 + this.m2(), 110.0F, 22.0F)) {
               if (this.f50 != ClickGuiScreen$3.f5) {
                  this.f50 = ClickGuiScreen$3.f5;
                  this.f51 = 0.0F;
                  this.m1362();
                  this.m645();
               }

               return true;
            }
         }

         if (this.f50 == ClickGuiScreen$3.f3) {
            return this.m1343(var3, var4, var5);
         } else if (this.f50 == ClickGuiScreen$3.f2) {
            return this.m1332(var3, var4, var5);
         } else if (this.f50 == ClickGuiScreen$3.f4) {
            return this.m1337(var3, var4, var5);
         } else if (this.f50 == ClickGuiScreen$3.f5) {
            return this.m1340(var3, var4, var5);
         } else {
            float var15 = var6 + 110.0F + 7.0F;
            float var17 = var7 + 34.0F + 7.0F;
            float var10 = 306.0F;
            float var11 = this.m1017();
            if (this.m35(var3, var4, var15, var17, var10, var11)) {
               for (KeybindPopup var13 : this.m1348()) {
                  if (var13.m1332(var3, var4, var5)) {
                     return true;
                  }
               }
            }

            return super.mouseClicked(click, doubled);
         }
      } else {
         return true;
      }
   }

   public boolean keyPressed(KeyInput input) {
      int var2 = input.key();
      if (this.f50 == ClickGuiScreen$3.f4 && this.f88) {
         if (var2 == 259) {
            if (!this.f87.isEmpty()) {
               this.f87 = this.f87.substring(0, this.f87.length() - 1);
            }

            this.m314();
            return true;
         }

         if (var2 == 257 || var2 == 335) {
            this.m692(0);
            return true;
         }

         if (var2 == 256) {
            this.f88 = false;
            return true;
         }
      }

      for (KeybindPopup var4 : this.m1348()) {
         if (var4.m1393(var2)) {
            if (var2 == 259) {
               this.m314();
            }

            return true;
         }
      }

      if (this.f92) {
         if (var2 == 259 && !this.f91.isEmpty()) {
            this.f91 = this.f91.substring(0, this.f91.length() - 1);
            if (!this.f94.isEmpty()) {
               this.f94.remove(this.f94.size() - 1);
            }

            this.m708();
            this.m314();
            return true;
         }

         if (var2 == 257 || var2 == 335 || var2 == 256) {
            this.f92 = false;
            return true;
         }
      }

      return super.keyPressed(input);
   }

   public boolean charTyped(CharInput input) {
      if (!input.isValidChar()) {
         return super.charTyped(input);
      } else if (this.f50 == ClickGuiScreen$3.f4 && this.f88) {
         String var4 = input.asString();
         if (this.f87.length() < 32 && var4.matches("[A-Za-z0-9_-]")) {
            this.f87 = this.f87 + var4;
            this.m314();
         }

         return true;
      } else {
         for (KeybindPopup var3 : this.m1348()) {
            if (var3.m20(input.asString())) {
               this.m314();
               return true;
            }
         }

         if (this.f92) {
            this.f91 = this.f91 + input.asString();
            this.f94.add(new ClickGuiScreen$2(input.asString()));
            this.m708();
            this.m314();
            return true;
         } else {
            return super.charTyped(input);
         }
      }
   }

   private void m314() {
      int var1 = 1 + (int)(Math.random() * 7.0);
      MinecraftClient.getInstance()
         .getSoundManager()
         .play(PositionedSoundInstance.ui(SoundEvent.of(Identifier.of("daamky", "keyboard_" + var1)), 1.0F, 1.0F));
   }

   public boolean mouseDragged(Click click, double offsetX, double offsetY) {
      if (!this.f61 && !(this.f68 > 0.02F) || !this.f62 && !this.f63 && !this.f83 && !this.f84) {
         float var11 = Render2DUtil.m3((float)click.x());
         float var12 = Render2DUtil.m151((float)click.y());
         int var8 = click.button();

         for (KeybindPopup var10 : this.m1348()) {
            if (var10.m1337(var11, var12, var8)) {
               return true;
            }
         }

         return super.mouseDragged(click, offsetX, offsetY);
      } else {
         float var6 = Render2DUtil.m3((float)click.x());
         float var7 = Render2DUtil.m151((float)click.y());
         this.m1355(var6, var7);
         return true;
      }
   }

   public boolean mouseReleased(Click click) {
      this.f62 = false;
      this.f63 = false;
      this.f83 = false;
      this.f84 = false;

      for (List<KeybindPopup> var3 : this.f39.values()) {
         for (KeybindPopup var5 : var3) {
            var5.m314();
         }
      }

      if (this.f64 >= 0 && this.f64 < ThemeManager.f8.size()) {
         ThemeManager.m1381(this.f64, ThemeManager.f1);
      } else {
         ThemeManager.m29();
      }

      return super.mouseReleased(click);
   }

   public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
      if (!this.f60 && !(this.m272() < 0.6F)) {
         float var9 = Render2DUtil.m3((float)mouseX);
         float var10 = Render2DUtil.m151((float)mouseY);
         float var11 = ((float)Render2DUtil.m113() - 430.0F) / 2.0F;
         float var12 = ((float)Render2DUtil.m189() - 290.0F) / 2.0F;
         float var13 = var11 + 110.0F;
         float var14 = var12 + 34.0F;
         if (this.m35(var9, var10, var13, var14, 320.0F, 256.0F)) {
            if (this.f50 == ClickGuiScreen$3.f2) {
               this.f45 = Math.clamp(this.f45 - (float)verticalAmount * 34.0F, 0.0F, this.m529());
               return true;
            }

            if (this.m634()) {
               float var15 = this.f43 - (float)verticalAmount * 34.0F;
               this.f43 = Math.clamp(var15, 0.0F, this.m1350());
            } else {
               float var17 = this.f46.getOrDefault(this.f47, this.f40.getOrDefault(this.f47, 0.0F));
               float var16 = var17 - (float)verticalAmount * 34.0F;
               this.f46.put(this.f47, Math.clamp(var16, 0.0F, this.m1351(this.f47)));
            }

            return true;
         } else {
            return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
         }
      } else {
         return true;
      }
   }

   private float m272() {
      float var1 = (float)(System.currentTimeMillis() - this.f59) / (this.f60 ? 170.0F : 300.0F);
      var1 = Math.clamp(var1, 0.0F, 1.0F);
      return this.f60 ? 1.0F - var1 : var1;
   }

   private void m286() {
      long var1 = System.currentTimeMillis();
      if (this.f75 == 0L) {
         this.f75 = var1;
      } else {
         float var3 = Math.min(50.0F, (float)(var1 - this.f75));
         this.f75 = var1;
         this.f53 = var3;
         this.f68 = this.m1364();
         this.f78 = this.m1363();
         this.f70 = this.m1139(this.f70, this.m646() ? 1.0F : 0.0F, 0.02F, var3);
         this.f44 = this.m1139(this.f44, Math.clamp(this.f45, 0.0F, this.m529()), 0.016F, var3);
         this.f69 = this.m1139(this.f69, 0.0F, 0.018F, var3);
         this.f51 = Math.min(1.0F, this.f51 + var3 / 340.0F);

         for (Category var7 : f1) {
            float var8 = this.f40.getOrDefault(var7, 0.0F);
            float var9 = this.f46.getOrDefault(var7, var8);
            var9 = Math.clamp(var9, 0.0F, this.m1351(var7));
            this.f46.put(var7, var9);
            this.f40.put(var7, this.m1139(var8, var9, 0.016F, var3));
         }

         float var10 = Math.max(0.0F, this.m1121(this.f41) - this.m1017());
         this.f43 = Math.clamp(this.f43, 0.0F, var10);
         this.f42 = this.m1139(this.f42, this.f43, 0.016F, var3);

         for (ClickGuiScreen$2 var13 : this.f94) {
            var13.f2 = this.m1139(var13.f2, 1.0F, 0.03F, var3);
         }

         float var12 = !this.f92 && this.f91.isEmpty() && this.f94.isEmpty() ? 0.0F : 1.0F;
         this.f93 = this.m1139(this.f93, var12, 0.04F, var3);
      }
   }

   // $VF: Unable to simplify switch on enum
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private void m1326(DrawContext var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9) {
      float var10 = this.m334(var2, var6, var8);
      float var11 = this.m335(var3, var7, var8);
      float var12 = 430.0F * var8;
      float var13 = 290.0F * var8;
      float var14 = 14.0F * var8;
      float var15 = 34.0F * var8;
      float var16 = 110.0F * var8;
      Color var17 = ThemeManager.m1379();
      if (f103) {
         Render2DUtil.m195(var10, var11, var12, var13, var14, this.m336(new Color(14, 16, 24, 238), var9));
         Render2DUtil.m202(var10, var11, var12, var13, var14, 0.9F * var8, this.m336(var17, var9 * 0.8F));
      } else {
         switch (ThemeManager.f4) {
            case f2:
               Render2DUtil.m198(var10, var11, var12, var13, var14, 25.0F, var9, this.m1365(var17));
               Render2DUtil.m194(var10, var11, var12, var15, 0.0F, 0.0F, var14, var14, this.m336(var17, var9 * 0.12F));
               Render2DUtil.m202(var10, var11, var12, var13, var14, 0.9F * var8, this.m336(var17, var9 * 0.75F));
               break;
            case f3:
               Render2DUtil.m198(var10, var11, var12, var13, var14, 25.0F, var9, new Color(14, 14, 18, 132));
               Render2DUtil.m217(
                  var10 + 0.5F * var8, var11 + 1.2F * var8, var12 - 1.0F * var8, var13 - 0.4F * var8, var14, 10.0F, 0.28F * var9, 2.4F, new Color(0, 0, 0, 170)
               );
               float var20 = 0.88F * var9;
               Color var19 = new Color(var17.getRed(), var17.getGreen(), var17.getBlue(), Math.round(72.0F * Math.clamp(var20, 0.0F, 1.0F)));
               Render2DUtil.m200(var10, var11, var12, var13, var14, 1.0F, var20, var19);
               Render2DUtil.m202(
                  var10 + 0.45F * var8,
                  var11 + 0.45F * var8,
                  var12 - 0.9F * var8,
                  var13 - 0.9F * var8,
                  var14,
                  0.85F * var8,
                  this.m336(new Color(255, 255, 255, 82), var9),
                  this.m336(new Color(255, 255, 255, 44), var9),
                  this.m336(var17, 0.22F * var9),
                  this.m336(new Color(255, 255, 255, 24), var9)
               );
               Render2DUtil.m195(
                  var10 + 8.0F * var8,
                  var11 + 2.1F * var8,
                  var12 - 16.0F * var8,
                  1.05F * var8,
                  0.5F * var8,
                  this.m336(new Color(255, 255, 255, 54), var9),
                  this.m336(new Color(255, 255, 255, 6), var9)
               );
               break;
            case f4:
               Color var18 = ThemeManager.f3 ? ThemeManager.f2 : this.m1367(var17);
               Render2DUtil.m198(var10, var11, var12, var13, var14, 25.0F, var9, new Color(14, 13, 18, 150));
               Render2DUtil.m195(var10, var11, var12, var13, var14, this.m1366(this.m1365(var17), this.m1365(var18), var9));
               Render2DUtil.m202(
                  var10,
                  var11,
                  var12,
                  var13,
                  var14,
                  0.9F * var8,
                  this.m336(var17, var9 * 0.8F),
                  this.m336(var18, var9 * 0.8F),
                  this.m336(var18, var9 * 0.8F),
                  this.m336(var17, var9 * 0.8F)
               );
               break;
            default:
               Render2DUtil.m198(var10, var11, var12, var13, var14, 25.0F, var9, new Color(22, 19, 30, 150));
               Render2DUtil.m202(var10, var11, var12, var13, var14, 0.9F * var8, this.m336(new Color(255, 255, 255, 28), var9));
         }
      }

      Render2DUtil.m195(var10 + 1.0F * var8, var11 + var15, var12 - 2.0F * var8, 0.8F * var8, 0.0F, this.m336(new Color(255, 255, 255, 15), var9));
      Render2DUtil.m195(var10 + var16, var11 + var15, 0.8F * var8, var13 - var15 - var14 * 0.5F, 0.0F, this.m336(new Color(255, 255, 255, 12), var9));
      this.m947(var1, var10, var11, var12, var8, var9);
      this.m946(var1, var10, var11, var4, var5, var8, var9);
      this.m1330(var1, var2, var3, var10, var11, var4, var5, var8, var9);
   }

   // $VF: Unable to simplify switch on enum
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private void m947(DrawContext var1, float var2, float var3, float var4, float var5, float var6) {
      float var7 = 12.0F * var5;
      float var8 = var3 + 34.0F * var5 / 2.0F;
      this.f58 = UserProfile.m58(this.f58);
       String var9 = UserProfile.m30();
      float var10 = 15.0F * var5;
      float var11 = var8 - FontRenderUtil.m239(FontRenderUtil.f3, var10) / 2.0F;

      Color var12 = switch (ThemeManager.f4) {
         case f2, f4 -> new Color(255, 255, 255);
         default -> ThemeManager.m1379();
      };
      Render2DUtil.m208(var1, FontRenderUtil.f3, var2 + var7, var11, "A", var10, this.m336(var12, var6));
      float var13 = 21.0F * var5;
      float var14 = var2 + var4 - var7 - var13;
      float var15 = var8 - var13 / 2.0F;
      Identifier var16 = TextureCache.m221(UserProfile.m58(this.f58));
      if (var16 != null) {
         Render2DUtil.m210(var14, var15, var13, var16, var13 / 2.0F, this.m336(Color.WHITE, var6));
      } else {
         Render2DUtil.m195(var14, var15, var13, var13, var13 / 2.0F, this.m336(new Color(38, 38, 44), var6));
         String var17 = this.f58.isEmpty() ? "?" : this.f58.substring(0, 1).toUpperCase();
         float var18 = 9.5F * var5;
         Render2DUtil.m206(
            var1,
            var14 + var13 / 2.0F,
            var8 - FontRenderUtil.m3(var18) / 2.0F,
            var17,
            var18,
            this.m336(new Color(235, 235, 240), var6),
            "center"
         );
      }

      Render2DUtil.m202(var14, var15, var13, var13, var13 / 2.0F, 1.0F * var5, this.m336(ThemeManager.f1, var6 * 0.8F));
      float var22 = 8.0F * var5;
      float var23 = 6.5F * var5;
      float var29f = (191.0F - this.m273()) * var5;
      if (var29f > 0.0F) {
         float var30f = Math.min(1.0F, var29f / Math.max(FontRenderUtil.m235(this.f58, var22), FontRenderUtil.m235(var9, var23)));
         var22 *= var30f;
         var23 *= var30f;
      }

      float var19 = FontRenderUtil.m235(this.f58, var22);
      float var20 = FontRenderUtil.m235(var9, var23);
      float var21 = var14 - 8.0F * var5;
      Render2DUtil.m205(var1, var21 - var19, var8 - FontRenderUtil.m3(var22) - 0.5F * var5, this.f58, var22, this.m336(new Color(232, 232, 238), var6));
      Render2DUtil.m205(var1, var21 - var20, var8 + 1.5F * var5, var9, var23, this.m336(new Color(148, 148, 154), var6));
      this.m994(var1, var2 + this.m273() * var5, var3 + this.m274() * var5, var5, var6);
   }

   private static String m58(String var0) {
      if (var0 != null && !var0.isEmpty()) {
         String var1 = var0.toLowerCase();

         return switch (var1) {
            case "dev" -> "Developer";
            case "admin" -> "Admin";
            case "mod" -> "Moderator";
            case "user" -> "User";
            default -> var0.substring(0, 1).toUpperCase() + var0.substring(1);
         };
      } else {
         return "User";
      }
   }

   private float m273() {
      float var1 = 397.0F;
      float var2 = Math.max(FontRenderUtil.m235(this.f58, 8.0F), FontRenderUtil.m235(m58(UserProfile.m30()), 6.5F));
      float var3 = var1 - 8.0F - var2 - 8.0F;
      return Math.max(var3 - 190.0F, 118.0F);
   }

   private float m274() {
      return 7.5F;
   }

   private void m994(DrawContext var1, float var2, float var3, float var4, float var5) {
      float var6 = 190.0F * var4;
      float var7 = 19.0F * var4;
      float var8 = var5 * var5 * var5;
      float var9 = this.m1368("search:focus", this.f92);
      Color var10 = this.m1369(new Color(0, 0, 0, this.m1244(70, 120)), new Color(0, 0, 0, this.m1244(110, 160)), var9);
      Render2DUtil.m195(var2, var3, var6, var7, var7 / 2.0F, this.m336(var10, var8));
      if (var9 > 0.01F) {
         Render2DUtil.m202(var2, var3, var6, var7, var7 / 2.0F, 0.8F * var4, this.m336(ThemeManager.m1379(), var5 * 0.7F * var9));
      }

      float var11 = 8.5F * var4;
      float var12 = var2 + 7.0F * var4;
      float var13 = var3 + (var7 - FontRenderUtil.m239(FontRenderUtil.f3, var11)) / 2.0F;
      Color var14 = this.m1369(new Color(150, 150, 155), ThemeManager.m1379(), var9);
      Render2DUtil.m208(var1, FontRenderUtil.f3, var12, var13, "V", var11, this.m336(var14, var5));
      float var15 = var2 + 20.0F * var4;
      ScissorUtil.m357((double)(var2 + 18.0F * var4), (double)var3, (double)(var6 - 20.0F * var4), (double)var7);
      if (this.f93 < 0.99F) {
         float var16 = var5 * (1.0F - this.f93);
         Render2DUtil.m205(
            var1,
            var15,
            var3 + (var7 - FontRenderUtil.m3(8.5F * var4)) / 2.0F,
            "Поиск модулей...",
            8.5F * var4,
            this.m336(new Color(140, 140, 145), Math.max(0.0F, var16))
         );
      }

      float var23 = var15;

      for (ClickGuiScreen$2 var18 : this.f94) {
         float var19 = var5 * EasingUtil.m151(var18.f2);
         float var20 = var3 + (var7 - FontRenderUtil.m3(8.5F * var4)) / 2.0F + (1.0F - EasingUtil.m151(var18.f2)) * 4.0F * var4;
         Render2DUtil.m205(var1, var23, var20, var18.f1, 8.5F * var4, this.m336(Color.WHITE, var19));
         var23 += FontRenderUtil.m235(var18.f1, 8.5F * var4) * var18.f2;
      }

      if (this.f92) {
         float var24 = (float)(Math.sin((double)System.currentTimeMillis() / 250.0) * 0.5 + 0.5);
         float var25 = var7 - 8.0F * var4;
         float var26 = var25 * var24;
         float var27 = var3 + 4.0F * var4 + (var25 - var26) / 2.0F;
         float var21 = var5 * (0.2F + 0.8F * var24);
         float var22 = this.f91.isEmpty() && this.f94.isEmpty() ? var15 : var23 + 1.5F * var4;
         Render2DUtil.m195(var22, var27, 1.0F * var4, var26, 0.5F * var4, this.m336(Color.WHITE, var21));
      }

      ScissorUtil.m29();
   }

   private float m1327(int var1) {
      return 47.0F + (float)var1 * 24.0F;
   }

   private float m523() {
      return this.m1327(f1.length) + 12.0F;
   }

   private float m524() {
      return this.m1327(f1.length) + 12.0F;
   }

   private float m526() {
      return this.m524() + 22.0F + 2.0F;
   }

   private float m525() {
      return this.m526() + 22.0F + 2.0F;
   }

   private float m2() {
      return this.m525() + 22.0F + 2.0F;
   }

   private void m946(DrawContext var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      float var8 = 110.0F * var6;
      float var9 = 22.0F * var6;
      float var10 = 5.0F * var6;
      float var11;
      if (this.f50 == ClickGuiScreen$3.f3) {
         var11 = this.m524();
      } else if (this.f50 == ClickGuiScreen$3.f2) {
         var11 = this.m526();
      } else if (this.f50 == ClickGuiScreen$3.f4) {
         var11 = this.m525();
      } else if (this.f50 == ClickGuiScreen$3.f5) {
         var11 = this.m2();
      } else {
         var11 = this.m1327(this.m1329(this.f47));
      }

      boolean var12 = this.f48 && this.f50 == ClickGuiScreen$3.f1;
      if (this.f57 && !var12) {
         this.f54 = this.m1139(this.f54, var11, 0.02F, this.f53);
      } else {
         this.f54 = var11;
         this.f57 = true;
      }

      if (!var12) {
         float var13 = var3 + this.f54 * var6;
         Render2DUtil.m195(var2 + var10, var13, var8 - var10 * 2.0F, var9, 6.0F * var6, this.m336(new Color(255, 255, 255, 22), var7));
         Render2DUtil.m195(
            var2 + var10 + 1.5F * var6, var13 + var9 / 2.0F - 5.0F * var6, 2.2F * var6, 10.0F * var6, 1.1F * var6, this.m336(ThemeManager.m1379(), var7)
         );
      }

      for (int var15 = 0; var15 < f1.length; var15++) {
         Category var14 = f1[var15];
         this.m1328(
            var1,
            var2,
            var3 + this.m1327(var15) * var6,
            var8,
            this.m1371(var14),
            this.m1372(var14),
            !this.f48 && this.f50 == ClickGuiScreen$3.f1 && var14 == this.f47,
            "cat:" + var14.name(),
            var4,
            var5,
            var6,
            var7
         );
      }

      float var16 = var3 + (this.m523() - 6.0F) * var6;
      Render2DUtil.m195(var2 + 12.0F * var6, var16, var8 - 24.0F * var6, 0.6F * var6, 0.0F, this.m336(new Color(255, 255, 255, 16), var7));
      this.m1328(
         var1,
         var2,
         var3 + this.m524() * var6,
         var8,
         "C",
         "Темы",
         this.f50 == ClickGuiScreen$3.f3,
         "cat:themes",
         var4,
         var5,
         var6,
         var7
      );
      this.m1328(
         var1,
         var2,
         var3 + this.m526() * var6,
         var8,
         "V",
         "Косметика",
         this.f50 == ClickGuiScreen$3.f2,
         "cat:cosmetics",
         var4,
         var5,
         var6,
         var7
      );
      this.m1328(
         var1,
         var2,
         var3 + this.m525() * var6,
         var8,
         "S",
         "Конфиги",
         this.f50 == ClickGuiScreen$3.f4,
         "cat:configs",
         var4,
         var5,
         var6,
         var7
      );
      this.m1328(
         var1,
         var2,
         var3 + this.m2() * var6,
         var8,
         "I",
         "Настройки",
         this.f50 == ClickGuiScreen$3.f5,
         "cat:settings",
         var4,
         var5,
         var6,
         var7
      );
   }

   private void m1328(
      DrawContext var1,
      float var2,
      float var3,
      float var4,
      String var5,
      String var6,
      boolean var7,
      String var8,
      float var9,
      float var10,
      float var11,
      float var12
   ) {
      float var13 = 22.0F * var11;
      boolean var14 = this.m35(var9, var10, var2, var3, var4, var13);
      float var15 = this.m1368(var8, var14);
      float var16 = Math.max(var15, var7 ? 1.0F : 0.0F);
      float var17 = 5.0F * var11;
      if (var15 > 0.01F && !var7) {
         Render2DUtil.m195(var2 + var17, var3, var4 - var17 * 2.0F, var13, 6.0F * var11, this.m336(new Color(255, 255, 255, 12), var12 * var15));
      }

      float var18 = var3 + var13 / 2.0F;
      float var19 = EasingUtil.m151(var15) * 1.5F * var11;
      float var20 = 9.5F * var11;
      float var21 = var2 + 15.0F * var11 + var19;
      float var22 = FontRenderUtil.m237(FontRenderUtil.f3, var5, var20);
      Color var23 = this.m1369(new Color(150, 150, 156), new Color(242, 242, 246), var16);
      Color var24 = this.m1369(new Color(150, 150, 156), ThemeManager.m1379(), var16);
      Render2DUtil.m208(var1, FontRenderUtil.f3, var21, var18 - FontRenderUtil.m239(FontRenderUtil.f3, var20) / 2.0F, var5, var20, this.m336(var24, var12));
      float var25 = 8.5F * var11;
      Render2DUtil.m205(var1, var21 + var22 + 6.0F * var11, var18 - FontRenderUtil.m3(var25) / 2.0F, var6, var25, this.m336(var23, var12));
   }

   private int m1329(Category var1) {
      for (int var2 = 0; var2 < f1.length; var2++) {
         if (f1[var2] == var1) {
            return var2;
         }
      }

      return 0;
   }

   private void m951(DrawContext var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      float var8 = var2 + var4 / 2.0F;
      float var9 = var3 + var5 / 2.0F;
      int var10 = (int)(System.currentTimeMillis() / 55L % 31L);
      float var11 = 48.0F * var6;
      float var12 = (float)Math.sin((double)System.currentTimeMillis() / 320.0) * 3.5F * var6;
      Identifier var13 = Identifier.of("daamky", "images/duck/" + var10 + ".png");
      Render2DUtil.m210(var8 - var11 / 2.0F, var9 - var11 - 2.0F * var6 + var12, var11, var13, 0.0F, this.m336(Color.WHITE, var7));
      Render2DUtil.m206(
         var1,
         var8,
         var9 + 10.0F * var6,
         "Открой любую вкладку",
         10.5F * var6,
         this.m336(new Color(150, 146, 168), var7),
         "center"
      );
   }

   private void m1330(DrawContext var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9) {
      float var10 = var4 + 117.0F * var8;
      float var11 = var5 + 41.0F * var8;
      float var12 = 306.0F * var8;
      float var13 = this.m1017() * var8;
      float var14 = (var12 - 5.0F * var8) / 2.0F;
      if (this.f50 == ClickGuiScreen$3.f4) {
         this.m1336(var1, var10, var11, var12, var13, var6, var7, var8, var9);
      } else if (this.f50 == ClickGuiScreen$3.f5) {
         this.m1339(var1, var10, var11, var12, var13, var6, var7, var8, var9);
      } else if (this.f50 == ClickGuiScreen$3.f2) {
         this.m1331(var1, var10, var11, var12, var13, var6, var7, var8, var9);
      } else if (this.f50 == ClickGuiScreen$3.f3) {
         this.m1341(var1, var2, var3, var4, var5, var10, var11, var12, var13, var14, var6, var7, var8, var9);
      } else if (this.f48) {
         this.m951(var1, var10, var11, var12, var13, var8, var9);
      } else {
         List var15 = this.m1348();
         float var16 = this.m1349() * var8;
         ScissorUtil.m357((double)var10, (double)var11, (double)(var12 + 6.0F * var8), (double)var13);
         if (var15.isEmpty()) {
            Render2DUtil.m206(
               var1,
               var10 + var12 / 2.0F,
               var11 + var13 / 2.0F - FontRenderUtil.m3(9.5F * var8) / 2.0F,
               "Пусто",
               9.5F * var8,
               this.m336(new Color(105, 105, 111), var9),
               "center"
            );
            ScissorUtil.m29();
         } else {
            float var19 = var11 - var16;
            float var20 = var11 - var16;

            for (int var22 = 0; var22 < var15.size(); var22++) {
               KeybindPopup var23 = (KeybindPopup)var15.get(var22);
               boolean var24 = var22 % 2 == 0;
               float var25 = var24 ? var10 : var10 + var14 + 5.0F * var8;
               float var26 = var24 ? var19 : var20;
               float var30 = Math.clamp((this.f51 * 560.0F - (float)Math.min(var22, 8) * 26.0F) / 340.0F, 0.0F, 1.0F);
               float var31 = EasingUtil.m151(var30);
               float var32 = (1.0F - var31) * 14.0F * var8;
               float var21 = var9 * var9 * var31;
               if (var31 > 0.6F && this.m35(var6, var7, var25, var26, var14, 38.0F * var8) && this.m35(var6, var7, var10, var11, var12, var13)) {
                  this.f76 = var23;
               }

               boolean var27 = var26 + var23.m272() * var8 >= var11 - 4.0F && var26 <= var11 + var13 + 4.0F;
               if (var27 && var21 > 0.001F) {
                  var23.m1346(var1, var25, var26 + var32, var14, var6, var7, var8, var21);
               }

               float var28 = (var23.m272() + 5.0F) * var8;
               if (var24) {
                  var19 += var28;
               } else {
                  var20 += var28;
               }
            }

            ScissorUtil.m29();
            this.m1068(var4, var5, var8, var9);
         }
      }
   }

   private float m529() {
      int var1 = (CapeManager.m189() + 4 - 1) / 4;
      float var2 = 25.0F + (float)var1 * 66.0F + (float)Math.max(0, var1 - 1) * 5.0F;
      return Math.max(0.0F, var2 - this.m1017());
   }

   private void m1331(DrawContext var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9) {
      float var10 = EasingUtil.m151(this.f51);
      float var11 = (1.0F - var10) * 10.0F * var8;
      float var12 = var9 * var10;
      Render2DUtil.m205(
         var1, var2, var3 + var11, "Косметика", 9.0F * var8, this.m336(new Color(238, 238, 243), var12)
      );
      Render2DUtil.m206(
         var1,
         var2 + var4,
         var3 + 1.0F * var8 + var11,
         "По одному предмету каждого типа",
         6.5F * var8,
         this.m336(new Color(128, 128, 137), var12),
         "right"
      );
      float var13 = (var4 - 15.0F * var8) / 4.0F;
      ScissorUtil.m357((double)var2, (double)(var3 + 25.0F * var8), (double)var4, (double)(var5 - 25.0F * var8));

      for (int var14 = 0; var14 < CapeManager.m189(); var14++) {
         int var15 = var14 % 4;
         int var16 = var14 / 4;
         float var17 = var2 + (float)var15 * (var13 + 5.0F * var8);
         float var18 = var3 + (25.0F + (float)var16 * 71.0F - this.f44) * var8 + var11;
         float var19 = 66.0F * var8;
         if (!(var18 + var19 < var3 + 25.0F * var8) && !(var18 > var3 + var5)) {
            boolean var20 = CapeManager.m13(var14);
            boolean var21 = this.m35(var6, var7, var17, var18, var13, var19);
            float var22 = this.m1368("cosmetic:" + var14, var21);
            Color var23 = this.m1369(new Color(10, 12, 16, this.m1244(72, 108)), new Color(17, 19, 25, this.m1244(102, 138)), var22);
            Render2DUtil.m195(var17, var18, var13, var19, 7.0F * var8, this.m336(var23, var12));
            if (var20) {
               Render2DUtil.m195(var17, var18, var13, var19, 7.0F * var8, this.m336(ThemeManager.m1379(), var12 * 0.14F));
            }

            Render2DUtil.m202(
               var17,
               var18,
               var13,
               var19,
               7.0F * var8,
               0.65F * var8,
               this.m336(this.m1369(new Color(255, 255, 255, 14), ThemeManager.m1379(), var20 ? 1.0F : var22 * 0.45F), var12)
            );
            float var24 = 39.0F * var8;
            float var25 = var17 + (var13 - var24) / 2.0F;
            float var26 = var18 + 3.0F * var8;
            Render2DUtil.m210(var25, var26, var24, CapeManager.m477(var14), 4.0F * var8, this.m336(Color.WHITE, var12));
            if (var20) {
               float var27 = 6.0F * var8;
               Render2DUtil.m195(var17 + var13 - var27 - 5.0F * var8, var18 + 5.0F * var8, var27, var27, var27 / 2.0F, this.m336(ThemeManager.m1379(), var12));
            }

            String var33 = this.m999(CapeManager.m90(var14), var13 - 8.0F * var8, 6.6F * var8);
            Render2DUtil.m206(
               var1,
               var17 + var13 / 2.0F,
               var18 + 44.0F * var8,
               var33,
               6.6F * var8,
               this.m336(var20 ? new Color(250, 250, 255) : new Color(205, 205, 212), var12),
               "center"
            );
            Render2DUtil.m206(
               var1,
               var17 + var13 / 2.0F,
               var18 + 54.0F * var8,
               CapeManager.m476(var14),
               5.7F * var8,
               this.m336(var20 ? ThemeManager.m1379() : new Color(120, 120, 130), var12),
               "center"
            );
         }
      }

      ScissorUtil.m29();
      if (this.m529() > 0.0F) {
         float var28 = var2 + var4 - 2.0F * var8;
         float var29 = var3 + 25.0F * var8;
         float var30 = var5 - 25.0F * var8;
         float var31 = Math.max(22.0F * var8, var30 * (this.m1017() / (this.m1017() + this.m529())));
         float var32 = var29 + (var30 - var31) * (this.f44 / this.m529());
         Render2DUtil.m195(var28, var29, 1.5F * var8, var30, 0.75F * var8, this.m336(new Color(255, 255, 255, 12), var12));
         Render2DUtil.m195(var28, var32, 1.5F * var8, var31, 0.75F * var8, this.m336(ThemeManager.m1379(), var12 * 0.8F));
      }
   }

   private boolean m1332(float var1, float var2, int var3) {
      if (var3 != 0) {
         return true;
      } else {
         float var4 = ((float)Render2DUtil.m113() - 430.0F) / 2.0F;
         float var5 = ((float)Render2DUtil.m189() - 290.0F) / 2.0F;
         float var6 = var4 + 110.0F + 7.0F;
         float var7 = var5 + 34.0F + 7.0F;
         float var8 = 306.0F;
         float var9 = (var8 - 15.0F) / 4.0F;

         for (int var10 = 0; var10 < CapeManager.m189(); var10++) {
            int var11 = var10 % 4;
            int var12 = var10 / 4;
            float var13 = var6 + (float)var11 * (var9 + 5.0F);
            float var14 = var7 + 25.0F + (float)var12 * 71.0F - this.f44;
            if (!(var14 < var7 + 25.0F) && !(var14 + 66.0F > var7 + this.m1017()) && this.m35(var1, var2, var13, var14, var9, 66.0F)) {
               CapeManager.m15(var10);
               this.f69 = 1.0F;
               return true;
            }
         }

         return true;
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

         return var5 + var4;
      }
   }

   private float m530() {
      return 20.0F;
   }

   private float m563() {
      return this.m530() + 22.0F + 8.0F;
   }

   private float m564() {
      return this.m563() + 24.0F + 12.0F;
   }

   private float m1333(int var1) {
      return this.m564() + (float)var1 * 22.0F;
   }

   private int m751() {
      return Math.max(0, (int)((this.m1017() - this.m564() - 12.0F) / 22.0F));
   }

   private void m707() {
      this.f85.clear();
      this.f85.addAll(ConfigManager.m375());
      if (this.f86 != null && !this.f85.contains(this.f86)) {
         this.f86 = null;
      }
   }

   private Color m1143(Color var1, int var2) {
      return new Color(var1.getRed(), var1.getGreen(), var1.getBlue(), Math.max(0, Math.min(255, var2)));
   }

   private void m21(String var1) {
      this.f89 = var1;
      this.f90 = System.currentTimeMillis() + 3000L;
   }

   private String m1334() {
      String var1 = this.f87.trim();
      return !var1.isEmpty() && this.f85.contains(var1) ? var1 : this.f86;
   }

   private String m1335() {
      for (int var1 = 1; var1 < 1000; var1++) {
         String var2 = "config" + var1;
         if (!this.f85.contains(var2)) {
            return var2;
         }
      }

      return "config";
   }

   private void m692(int var1) {
      switch (var1) {
         case 0:
            String var5 = this.f87.trim();
            if (var5.isEmpty()) {
               var5 = this.f86 != null ? this.f86 : this.m1335();
            }

            if (!ConfigManager.m80(var5)) {
               this.m21("§cИмя: латиница, цифры, _ и -");
               return;
            }

            boolean var7 = ConfigManager.m20(var5);
            this.m21(var7 ? "§aСохранено: §f" + var5 : "§cНе удалось сохранить");
            if (var7) {
               this.f86 = var5;
               this.f87 = var5;
               this.m707();
            }
            break;
         case 1:
            String var4 = this.m1334();
            if (var4 == null) {
               this.m21("§eВыбери конфиг в списке");
               return;
            }

            boolean var6 = ConfigManager.m17(var4);
            this.m21(var6 ? "§aЗагружено: §f" + var4 : "§cНе удалось загрузить");
            if (var6) {
               this.f86 = var4;
               this.m708();
            }
            break;
         case 2:
            String var2 = this.m1334();
            if (var2 == null) {
               this.m21("§eВыбери конфиг в списке");
               return;
            }

            boolean var3 = ConfigManager.m374(var2);
            this.m21(var3 ? "§aУдалено: §f" + var2 : "§cНе удалось удалить");
            if (var3) {
               if (var2.equals(this.f86)) {
                  this.f86 = null;
               }

               this.f87 = "";
               this.m707();
            }
            break;
         case 3:
            this.m21(
               ConfigManager.m31()
                  ? "§aПапка открыта"
                  : "§cНе удалось открыть папку"
            );
      }
   }

   private void m1336(DrawContext var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9) {
      float var10 = EasingUtil.m151(this.f51);
      float var11 = (1.0F - var10) * 10.0F * var8;
      float var12 = var9 * var10;
      Render2DUtil.m205(var1, var2, var3 + var11, "Конфиги", 9.0F * var8, this.m336(new Color(238, 238, 243), var12));
      float var13 = var3 + this.m530() * var8 + var11;
      float var14 = 22.0F * var8;
      boolean var15 = this.m35(var6, var7, var2, var13, var4, var14);
      Render2DUtil.m195(var2, var13, var4, var14, 6.0F * var8, this.m336(new Color(10, 12, 16, this.m1244(72, 108)), var12));
      Color var16 = this.m1369(new Color(255, 255, 255, 14), ThemeManager.m1379(), this.f88 ? 1.0F : (var15 ? 0.4F : 0.0F));
      Render2DUtil.m202(var2, var13, var4, var14, 6.0F * var8, 0.6F * var8, this.m336(var16, var12));
      boolean var17 = this.f87.isEmpty();
      String var18 = var17 ? "Имя конфига" : this.f87;
      float var19 = var13 + (var14 - FontRenderUtil.m3(8.5F * var8)) / 2.0F;
      Render2DUtil.m205(var1, var2 + 9.0F * var8, var19, var18, 8.5F * var8, this.m336(var17 ? new Color(118, 118, 126) : new Color(238, 238, 243), var12));
      if (this.f88) {
         float var20 = (float)(Math.sin((double)System.currentTimeMillis() / 250.0) * 0.5 + 0.5);
         float var21 = var2 + 9.0F * var8 + (var17 ? 0.0F : FontRenderUtil.m235(this.f87, 8.5F * var8)) + 1.5F * var8;
         Render2DUtil.m195(var21, var13 + 5.0F * var8, 1.0F * var8, var14 - 10.0F * var8, 0.5F * var8, this.m336(Color.WHITE, var12 * (0.2F + 0.8F * var20)));
      }

      float var37 = var3 + this.m563() * var8 + var11;
      float var38 = 24.0F * var8;
      float var22 = (var4 - 6.0F * var8 * (float)(f113.length - 1)) / (float)f113.length;

      for (int var23 = 0; var23 < f113.length; var23++) {
         float var24 = var2 + (float)var23 * (var22 + 6.0F * var8);
         boolean var25 = this.m35(var6, var7, var24, var37, var22, var38);
         float var26 = this.m1368("cfgbtn:" + var23, var25);
         boolean var27 = var23 == 0;
         boolean var28 = var23 == 2;
         Color var29 = var27
            ? this.m1369(this.m1143(ThemeManager.m1379(), 150), this.m1143(ThemeManager.m1379(), 210), var26)
            : this.m1369(new Color(10, 12, 16, this.m1244(72, 108)), new Color(10, 12, 16, this.m1244(98, 132)), var26);
         Render2DUtil.m195(var24, var37, var22, var38, 6.0F * var8, this.m336(var29, var12));
         Color var30 = var27
            ? ThemeManager.m1379()
            : this.m1369(new Color(255, 255, 255, 14), var28 ? new Color(226, 96, 96) : ThemeManager.m1379(), var26 * 0.6F);
         Render2DUtil.m202(var24, var37, var22, var38, 6.0F * var8, 0.6F * var8, this.m336(var30, var12));
         Color var31 = var27
            ? new Color(255, 255, 255)
            : this.m1369(new Color(186, 186, 192), var28 ? new Color(240, 150, 150) : new Color(250, 250, 255), var26);
         Render2DUtil.m206(
            var1,
            var24 + var22 / 2.0F,
            var37 + (var38 - FontRenderUtil.m3(8.0F * var8)) / 2.0F,
            f113[var23],
            8.0F * var8,
            this.m336(var31, var12),
            "center"
         );
      }

      float var39 = var3 + this.m564() * var8 + var11;
      if (this.f85.isEmpty()) {
         Render2DUtil.m206(
            var1,
            var2 + var4 / 2.0F,
            var39 + 6.0F * var8,
            "Пока нет сохранённых конфигов",
            8.5F * var8,
            this.m336(new Color(118, 118, 126), var12),
            "center"
         );
      } else {
         int var40 = Math.min(this.f85.size(), this.m751());

         for (int var41 = 0; var41 < var40; var41++) {
            String var42 = this.f85.get(var41);
            float var43 = var3 + this.m1333(var41) * var8 + var11;
            float var44 = 19.0F * var8;
            boolean var45 = var42.equals(this.f86);
            boolean var46 = this.m35(var6, var7, var2, var43, var4, var44);
            float var47 = this.m1368("cfgrow:" + var42, var46);
            float var32 = Math.max(var47, var45 ? 1.0F : 0.0F);
            Render2DUtil.m195(
               var2,
               var43,
               var4,
               var44,
               6.0F * var8,
               this.m336(this.m1369(new Color(10, 12, 16, this.m1244(72, 108)), new Color(10, 12, 16, this.m1244(98, 132)), var47), var12)
            );
            if (var45) {
               Render2DUtil.m195(var2, var43, var4, var44, 6.0F * var8, this.m336(ThemeManager.m1379(), var12 * 0.12F));
            }

            Render2DUtil.m202(
               var2,
               var43,
               var4,
               var44,
               6.0F * var8,
               0.6F * var8,
               this.m336(this.m1369(new Color(255, 255, 255, 14), ThemeManager.m1379(), var45 ? 1.0F : var47 * 0.4F), var12)
            );
            float var33 = 7.0F * var8;
            float var34 = var2 + 9.0F * var8;
            float var35 = var43 + (var44 - var33) / 2.0F;
            Render2DUtil.m202(var34, var35, var33, var33, var33 / 2.0F, 0.6F * var8, this.m336(ThemeManager.m1379(), var12 * (0.25F + 0.45F * var32)));
            if (var45) {
               float var36 = var33 - 3.4F * var8;
               Render2DUtil.m195(var34 + 1.7F * var8, var35 + 1.7F * var8, var36, var36, var36 / 2.0F, this.m336(ThemeManager.m1379(), var12));
            }

            Render2DUtil.m205(
               var1,
               var34 + var33 + 7.0F * var8,
               var43 + (var44 - FontRenderUtil.m3(8.5F * var8)) / 2.0F,
               var42,
               8.5F * var8,
               this.m336(this.m1369(new Color(180, 180, 186), new Color(250, 250, 255), var32), var12)
            );
         }
      }

      if (!this.f89.isEmpty() && System.currentTimeMillis() < this.f90) {
         Render2DUtil.m205(var1, var2, var3 + (this.m1017() - 11.0F) * var8 + var11, this.f89, 8.0F * var8, this.m336(new Color(210, 210, 216), var12));
      }
   }

   private boolean m1337(float var1, float var2, int var3) {
      if (var3 != 0) {
         return true;
      } else {
         float var4 = ((float)Render2DUtil.m113() - 430.0F) / 2.0F;
         float var5 = ((float)Render2DUtil.m189() - 290.0F) / 2.0F;
         float var6 = var4 + 110.0F + 7.0F;
         float var7 = var5 + 34.0F + 7.0F;
         float var8 = 306.0F;
         if (this.m35(var1, var2, var6, var7 + this.m530(), var8, 22.0F)) {
            this.f88 = true;
            return true;
         } else {
            this.f88 = false;
            float var9 = (var8 - 6.0F * (float)(f113.length - 1)) / (float)f113.length;

            for (int var10 = 0; var10 < f113.length; var10++) {
               float var11 = var6 + (float)var10 * (var9 + 6.0F);
               if (this.m35(var1, var2, var11, var7 + this.m563(), var9, 24.0F)) {
                  this.m692(var10);
                  return true;
               }
            }

            int var12 = Math.min(this.f85.size(), this.m751());

            for (int var13 = 0; var13 < var12; var13++) {
               if (this.m35(var1, var2, var6, var7 + this.m1333(var13), var8, 19.0F)) {
                  this.f86 = this.f85.get(var13);
                  this.f87 = this.f86;
                  return true;
               }
            }

            return true;
         }
      }
   }

   private float m1338(int var1) {
      return 22.0F + (float)var1 * 31.0F;
   }

   private void m1339(DrawContext var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9) {
      float var10 = EasingUtil.m151(this.f51);
      float var11 = (1.0F - var10) * 10.0F * var8;
      float var12 = var9 * var10;
      Render2DUtil.m205(
         var1, var2, var3 + var11, "Вид ClickGui", 9.0F * var8, this.m336(new Color(238, 238, 243), var12)
      );

      for (int var13 = 0; var13 < f116.length; var13++) {
         float var14 = var3 + this.m1338(var13) * var8 + var11;
         boolean var15 = ThemeManager.f4 == f116[var13];
         boolean var16 = this.m35(var6, var7, var2, var14, var4, 27.0F * var8);
         float var17 = this.m1368("style:" + var13, var16);
         float var18 = Math.max(var17, var15 ? 1.0F : 0.0F);
         Color var19 = this.m1369(new Color(10, 12, 16, this.m1244(72, 108)), new Color(10, 12, 16, this.m1244(98, 132)), var17);
         Render2DUtil.m195(var2, var14, var4, 27.0F * var8, 6.0F * var8, this.m336(var19, var12));
         if (var15) {
            Render2DUtil.m195(var2, var14, var4, 27.0F * var8, 6.0F * var8, this.m336(ThemeManager.m1379(), var12 * 0.12F));
         }

         Color var20 = this.m1369(new Color(255, 255, 255, 14), ThemeManager.m1379(), var15 ? 1.0F : var17 * 0.4F);
         Render2DUtil.m202(var2, var14, var4, 27.0F * var8, 6.0F * var8, 0.6F * var8, this.m336(var20, var12));
         float var21 = 8.5F * var8;
         float var22 = var2 + 9.0F * var8;
         float var23 = var14 + (27.0F * var8 - var21) / 2.0F;
         Render2DUtil.m195(var22, var23, var21, var21, var21 / 2.0F, this.m336(new Color(17, 17, 18), var12 * 0.36F));
         Render2DUtil.m202(var22, var23, var21, var21, var21 / 2.0F, 0.6F * var8, this.m336(ThemeManager.m1379(), var12 * (0.25F + 0.45F * var18)));
         if (var15) {
            float var24 = var21 - 4.2F * var8;
            Render2DUtil.m195(var22 + 2.1F * var8, var23 + 2.1F * var8, var24, var24, var24 / 2.0F, this.m336(ThemeManager.m1379(), var12));
         }

         float var26 = var22 + var21 + 8.0F * var8 + EasingUtil.m151(var17) * 1.5F * var8;
         Color var25 = this.m1369(new Color(180, 180, 186), new Color(250, 250, 255), var18);
         Render2DUtil.m205(var1, var26, var14 + 7.0F * var8, f114[var13], 8.0F * var8, this.m336(var25, var12));
         Render2DUtil.m205(var1, var26, var14 + 17.5F * var8, f115[var13], 6.5F * var8, this.m336(new Color(140, 140, 147), var12));
      }
   }

   private boolean m1340(float var1, float var2, int var3) {
      if (var3 != 0) {
         return true;
      } else {
         float var4 = ((float)Render2DUtil.m113() - 430.0F) / 2.0F;
         float var5 = ((float)Render2DUtil.m189() - 290.0F) / 2.0F;
         float var6 = var4 + 110.0F + 7.0F;
         float var7 = var5 + 34.0F + 7.0F;
         float var8 = 306.0F;

         for (int var9 = 0; var9 < f116.length; var9++) {
            if (this.m35(var1, var2, var6, var7 + this.m1338(var9), var8, 27.0F)) {
               ThemeManager.f4 = f116[var9];
               ThemeManager.m29();
               return true;
            }
         }

         return true;
      }
   }

   private void m1068(float var1, float var2, float var3, float var4) {
      float var5 = this.m1350();
      if (!(var5 <= 0.0F)) {
         float var6 = this.m1017() * var3;
         float var7 = var1 + 425.5F * var3;
         float var8 = var2 + 41.0F * var3;
         float var9 = this.m1121(this.m1348());
         float var10 = Math.max(24.0F * var3, var6 * (this.m1017() / var9));
         float var11 = this.m1349() / var5;
         float var12 = var8 + (var6 - var10) * Math.clamp(var11, 0.0F, 1.0F);
         Render2DUtil.m195(var7, var8, 2.0F * var3, var6, 1.0F * var3, this.m336(new Color(255, 255, 255, 14), var4));
         Render2DUtil.m195(var7, var12, 2.0F * var3, var10, 1.0F * var3, this.m336(ThemeManager.f1, var4 * 0.75F));
      }
   }

   private float m1017() {
      return 242.0F;
   }

   private void m1341(
      DrawContext var1,
      float var2,
      float var3,
      float var4,
      float var5,
      float var6,
      float var7,
      float var8,
      float var9,
      float var10,
      float var11,
      float var12,
      float var13,
      float var14
   ) {
      float var15 = 26.0F * var13;
      float var16 = var15 + 6.0F * var13;
      float var17 = var8 - var10;
      int var18 = 0;

      for (ThemeManager$2 var20 : ThemeManager.f7) {
         float var21 = var6 + (float)(var18 % 2) * var17;
         float var22 = var7 + (float)(var18 / 2) * var16;
         this.m1342(var1, var21, var22, var10, var15, var20.m37(), var20.m1379(), this.m1370(var20.m1379(), ThemeManager.f1), var11, var12, var13, var14);
         var18++;
      }

      for (int var29 = 0; var29 < ThemeManager.f8.size(); var29++) {
         Color var31 = ThemeManager.f8.get(var29);
         float var33 = var6 + (float)(var18 % 2) * var17;
         float var35 = var7 + (float)(var18 / 2) * var16;
         this.m1342(var1, var33, var35, var10, var15, "Кастом " + (var29 + 1), var31, this.m1370(var31, ThemeManager.f1), var11, var12, var13, var14);
         var18++;
      }

      float var30 = var6 + (float)(var18 % 2) * var17;
      float var32 = var7 + (float)(var18 / 2) * var16;
      boolean var34 = this.m35(var11, var12, var30, var32, var10, var15);
      float var36 = this.m1368("theme:add", var34);
      Render2DUtil.m195(
         var30,
         var32,
         var10,
         var15,
         6.0F * var13,
         this.m336(this.m1369(new Color(10, 12, 16, this.m1244(72, 108)), new Color(10, 12, 16, this.m1244(98, 132)), var36), var14)
      );
      Render2DUtil.m206(
         var1,
         var30 + var10 / 2.0F,
         var32 + (var15 - FontRenderUtil.m3(8.5F * var13)) / 2.0F,
         "+ Добавить",
         8.5F * var13,
         this.m336(this.m1369(new Color(150, 150, 156), ThemeManager.m1379(), var36), var14),
         "center"
      );
      if (this.f61 || this.f68 > 0.02F) {
         float var23 = EasingUtil.m151(this.f68);
         float var24 = var13 * (0.94F + var23 * 0.06F);
         float var25 = var14 * var23;
         float var26 = var4 + (this.m1357() - var2) * var13;
         float var27 = var5 + (this.m1358() - var3) * var13;
         float var28 = (ThemeManager.f3 ? 272.0F : 132.0F) * var13;
         Render2DUtil.m195(
            var26 - 5.0F * var13,
            var27 - 5.0F * var13,
            var28 + 10.0F * var13,
            108.0F * var13 + 10.0F * var13,
            8.0F * var13,
            this.m336(new Color(14, 14, 18, 235), var25)
         );
         this.m1345(var1, var26, var27, var24, var25);
      }

      if (this.m646() || this.f70 > 0.02F) {
         this.m1344(var1, var4 + (this.f72 - var2) * var13, var5 + (this.f73 - var3) * var13, var13, var14 * this.f70);
      }
   }

   private void m1342(
      DrawContext var1,
      float var2,
      float var3,
      float var4,
      float var5,
      String var6,
      Color var7,
      boolean var8,
      float var9,
      float var10,
      float var11,
      float var12
   ) {
      boolean var13 = this.m35(var9, var10, var2, var3, var4, var5);
      float var14 = this.m1368("theme:" + var6, var13);
      float var15 = this.m1368("themeSel:" + var6, var8);
      Color var16 = this.m1369(new Color(10, 12, 16, this.m1244(72, 108)), new Color(10, 12, 16, this.m1244(98, 132)), var14);
      Render2DUtil.m195(var2, var3, var4, var5, 6.0F * var11, this.m336(var16, var12));
      if (var15 > 0.01F) {
         Render2DUtil.m202(var2, var3, var4, var5, 6.0F * var11, 0.9F * var11, this.m336(ThemeManager.m1379(), var12 * var15));
      }

      float var17 = EasingUtil.m151(var14) * 1.6F * var11;
      float var18 = 9.0F * var11 + var17;
      Render2DUtil.m195(var2 + 9.0F * var11 - var17 / 2.0F, var3 + (var5 - var18) / 2.0F, var18, var18, var18 / 2.0F, this.m336(var7, var12));
      Color var19 = this.m1369(new Color(182, 182, 188), new Color(240, 240, 245), Math.max(var14, var15));
      Render2DUtil.m205(
         var1,
         var2 + 9.0F * var11 + 9.0F * var11 + 7.0F * var11,
         var3 + (var5 - FontRenderUtil.m3(8.5F * var11)) / 2.0F,
         var6,
         8.5F * var11,
         this.m336(var19, var12)
      );
   }

   private boolean m1343(float var1, float var2, int var3) {
      if (var3 != 0 && var3 != 1) {
         return false;
      } else if (this.m1359(var1, var2, var3)) {
         return true;
      } else if (var3 == 0 && (this.f61 || this.f68 > 0.02F) && this.m1354(var1, var2)) {
         return true;
      } else {
         float var4 = ((float)Render2DUtil.m113() - 430.0F) / 2.0F;
         float var5 = ((float)Render2DUtil.m189() - 290.0F) / 2.0F;
         float var6 = var4 + 110.0F + 7.0F;
         float var7 = var5 + 34.0F + 7.0F;
         float var8 = 306.0F;
         float var9 = (var8 - 5.0F) / 2.0F;
         float var10 = var8 - var9;
         float var11 = 32.0F;
         int var12 = 0;

         for (int var13 = 0; var13 < ThemeManager.f7.size(); var13++) {
            float var14 = var6 + (float)(var12 % 2) * var10;
            float var15 = var7 + (float)(var12 / 2) * var11;
            if (this.m35(var1, var2, var14, var15, var9, 26.0F)) {
               if (var3 == 1) {
                  this.m1361(var1, var2, -1 - var13);
               } else {
                  this.m1353(ThemeManager.f7.get(var13).m1379());
               }

               return true;
            }

            var12++;
         }

         for (int var16 = 0; var16 < ThemeManager.f8.size(); var16++) {
            float var18 = var6 + (float)(var12 % 2) * var10;
            float var20 = var7 + (float)(var12 / 2) * var11;
            if (this.m35(var1, var2, var18, var20, var9, 26.0F)) {
               if (var3 == 1) {
                  this.m1361(var1, var2, var16);
               } else {
                  this.m1353(ThemeManager.f8.get(var16));
               }

               return true;
            }

            var12++;
         }

         float var17 = var6 + (float)(var12 % 2) * var10;
         float var19 = var7 + (float)(var12 / 2) * var11;
         if (var3 == 0 && this.m35(var1, var2, var17, var19, var9, 26.0F)) {
            ThemeManager.f3 = false;
            this.m805();
            return true;
         } else {
            if (var3 == 0 && this.f61) {
               this.m1362();
               ThemeManager.m29();
            }

            this.m645();
            return true;
         }
      }
   }

   private void m1344(DrawContext var1, float var2, float var3, float var4, float var5) {
      boolean var6 = this.f71 >= 0;
      float var7 = 62.0F * var4;
      float var8 = 18.0F * var4;
      float var9 = var8 * 2.0F;
      Render2DUtil.m198(var2, var3, var7, var9, 5.0F * var4, 25.0F, var5, new Color(18, 18, 22, 140));
      float var10 = this.m1368("ctx:change", this.m35(this.f55, this.f56, var2, var3, var7, var8));
      float var11 = this.m1368("ctx:delete", this.m35(this.f55, this.f56, var2, var3 + var8, var7, var8));
      if (var10 > 0.01F) {
         Render2DUtil.m195(var2, var3, var7, var8, 5.0F * var4, this.m336(new Color(255, 255, 255, 16), var5 * var10));
      }

      if (var11 > 0.01F) {
         Render2DUtil.m195(var2, var3 + var8, var7, var8, 5.0F * var4, this.m336(new Color(255, 255, 255, 16), var5 * var11));
      }

      Render2DUtil.m205(
         var1,
         var2 + 7.0F * var4 + var10 * 1.5F * var4,
         var3 + 5.2F * var4,
         "Change",
         7.5F * var4,
         this.m336(this.m1369(new Color(206, 206, 212), new Color(245, 245, 250), var10), var5)
      );
      Color var12 = var6 ? this.m1369(new Color(225, 130, 130), new Color(255, 165, 165), var11) : new Color(96, 96, 102);
      Render2DUtil.m205(
         var1, var2 + 7.0F * var4 + var11 * 1.5F * var4, var3 + 23.2F * var4, "Delete", 7.5F * var4, this.m336(var12, var5)
      );
   }

   private void m1345(DrawContext var1, float var2, float var3, float var4, float var5) {
      if (ThemeManager.f3) {
         float var6 = var2 + 132.0F * var4 + 8.0F * var4;
         this.m1346(var1, var2, var3, var4, var5, this.f65, this.f66, this.f67);
         this.m1346(var1, var6, var3, var4, var5, this.f80, this.f81, this.f82);
      } else {
         this.m1346(var1, var2, var3, var4, var5, this.f65, this.f66, this.f67);
      }
   }

   private void m1346(DrawContext var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      float var9 = var2 + 8.0F * var4;
      float var10 = var3 + 8.0F * var4;
      float var11 = 78.0F * var4;
      float var12 = 6.0F * var4;
      this.m1347(var9, var10, var11, var11, var12, var6, var5);
      Render2DUtil.m202(var9, var10, var11, var11, var12, 0.9F * var4, this.m336(new Color(255, 255, 255), var5 * 0.25F));
      float var13 = var9 + var7 * var11;
      float var14 = var10 + (1.0F - var8) * var11;
      Render2DUtil.m202(var13 - 2.5F * var4, var14 - 2.5F * var4, 5.0F * var4, 5.0F * var4, 2.5F * var4, 1.0F * var4, this.m336(Color.WHITE, var5));
      float var15 = var9 + var11 + 8.0F * var4;
      this.m1006(var15, var10, 10.0F * var4, var11, var5);
      Render2DUtil.m202(var15, var10, 10.0F * var4, var11, 10.0F * var4 / 2.0F, 0.9F * var4, this.m336(new Color(255, 255, 255), var5 * 0.25F));
      float var16 = var10 + var6 * var11;
      Render2DUtil.m202(var15 - 2.0F * var4, var16 - 1.5F * var4, 14.0F * var4, 3.0F * var4, 1.5F * var4, 1.0F * var4, this.m336(Color.WHITE, var5));
   }

   private void m1347(float var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      Color var8 = Color.WHITE;
      Color var9 = this.m1369(Color.WHITE, Color.getHSBColor(var6, 1.0F, 1.0F), 0.5F);
      Color var10 = Color.getHSBColor(var6, 1.0F, 1.0F);
      Color var11 = new Color(128, 128, 128);
      Color var12 = Color.getHSBColor(var6, 0.5F, 0.5F);
      Color var13 = Color.getHSBColor(var6, 1.0F, 0.5F);
      Color var14 = Color.BLACK;
      Render2DUtil.m195(
         var1,
         var2,
         var3,
         var4,
         var5,
         this.m336(var8, var7),
         this.m336(var9, var7),
         this.m336(var10, var7),
         this.m336(var11, var7),
         this.m336(var12, var7),
         this.m336(var13, var7),
         this.m336(var14, var7),
         this.m336(var14, var7),
         this.m336(var14, var7)
      );
   }

   private void m1006(float var1, float var2, float var3, float var4, float var5) {
      if (!(var4 <= 0.0F) && !(var3 <= 0.0F)) {
         byte var6 = 80;
         float var7 = Math.max(0.0F, var4 - var3);
         float var8 = var6 <= 1 ? 0.0F : var7 / (float)(var6 - 1);
         float var9 = var3 / 2.0F;

         for (int var10 = 0; var10 < var6; var10++) {
            float var11 = (float)var10 / (float)(var6 - 1);
            Render2DUtil.m195(var1, var2 + (float)var10 * var8, var3, var3, var9, this.m336(Color.getHSBColor(var11, 1.0F, 1.0F), var5));
         }
      }
   }

   private void m708() {
      this.f39.clear();

      for (Category var4 : Category.values()) {
         ArrayList var5 = new ArrayList();
         ModuleManager.getByCategory(var4).forEach(var2 -> {
            if (this.f91.isEmpty() || var2.getName().toLowerCase().contains(this.f91.toLowerCase())) {
               var5.add(new KeybindPopup(var2));
            }
         });
         if (var4 == Category.MISC) {
            ModuleManager.getByCategory(Category.MENU).forEach(var2 -> {
               if (this.f91.isEmpty() || var2.getName().toLowerCase().contains(this.f91.toLowerCase())) {
                  var5.add(new KeybindPopup(var2));
               }
            });
            ModuleManager.getByCategory(Category.CONFIG).forEach(var2 -> {
               if (this.f91.isEmpty() || var2.getName().toLowerCase().contains(this.f91.toLowerCase())) {
                  var5.add(new KeybindPopup(var2));
               }
            });
         }

         this.f39.put(var4, var5);
         this.f40.put(var4, 0.0F);
         this.f46.put(var4, 0.0F);
      }

      this.f41.clear();

      for (Category var9 : f1) {
         this.f41.addAll(this.f39.getOrDefault(var9, List.of()));
      }

      this.f42 = 0.0F;
      this.f43 = 0.0F;
   }

   private boolean m634() {
      return !this.f91.isEmpty();
   }

   private List<KeybindPopup> m1348() {
      return this.m634() ? this.f41 : this.f39.getOrDefault(this.f47, List.of());
   }

   private float m1349() {
      return this.m634() ? this.f42 : this.f40.getOrDefault(this.f47, 0.0F);
   }

   private float m1350() {
      return Math.max(0.0F, this.m1121(this.m1348()) - this.m1017());
   }

   private float m1351(Category var1) {
      return Math.max(0.0F, this.m1352(var1) - this.m1017());
   }

   private float m1352(Category var1) {
      return this.m1121(this.f39.getOrDefault(var1, List.of()));
   }

   private float m1121(List<KeybindPopup> var1) {
      if (var1.isEmpty()) {
         return 38.0F;
      } else {
         float var2 = 0.0F;
         float var3 = 0.0F;

         for (int var4 = 0; var4 < var1.size(); var4++) {
            float var5 = ((KeybindPopup)var1.get(var4)).m272() + 5.0F;
            if (var4 % 2 == 0) {
               var2 += var5;
            } else {
               var3 += var5;
            }
         }

         return Math.max(var2, var3) - 5.0F;
      }
   }

   private boolean m35(float var1, float var2, float var3, float var4, float var5, float var6) {
      return var1 >= var3 && var1 <= var3 + var5 && var2 >= var4 && var2 <= var4 + var6;
   }

   private float m334(float var1, float var2, float var3) {
      return var2 + (var1 - var2) * var3;
   }

   private float m335(float var1, float var2, float var3) {
      return var2 + (var1 - var2) * var3;
   }

   private Color m336(Color var1, float var2) {
      int var3 = (int)((float)var1.getAlpha() * Math.clamp(var2, 0.0F, 1.0F));
      return new Color(var1.getRed(), var1.getGreen(), var1.getBlue(), var3);
   }

   private boolean m1353(Color var1) {
      this.m645();
      this.f64 = -1;
      ThemeManager.m8(var1);
      this.m1356(var1);
      this.m1362();
      this.f62 = false;
      this.f63 = false;
      this.f83 = false;
      this.f84 = false;
      this.f69 = 1.0F;
      return true;
   }

   private void m805() {
      this.m1356(ThemeManager.f1);
      this.f64 = ThemeManager.m1380(ThemeManager.f1);
      this.m645();
      this.m743();
      this.f69 = 1.0F;
   }

   private boolean m1354(float var1, float var2) {
      float var3 = this.m1357();
      float var4 = this.m1358();
      float var5 = ThemeManager.f3 ? 272.0F : 132.0F;
      if (!this.m35(var1, var2, var3, var4, var5, 108.0F)) {
         return false;
      } else {
         float var6 = var3 + 8.0F;
         float var7 = var4 + 8.0F;
         float var8 = var6 + 78.0F + 8.0F;
         this.f62 = this.m35(var1, var2, var6, var7, 78.0F, 78.0F);
         this.f63 = this.m35(var1, var2, var8, var7, 10.0F, 78.0F);
         if (ThemeManager.f3) {
            float var9 = var3 + 132.0F + 8.0F;
            float var10 = var9 + 8.0F;
            float var11 = var10 + 78.0F + 8.0F;
            this.f83 = this.m35(var1, var2, var10, var7, 78.0F, 78.0F);
            this.f84 = this.m35(var1, var2, var11, var7, 10.0F, 78.0F);
         }

         this.m1355(var1, var2);
         return true;
      }
   }

   private void m1355(float var1, float var2) {
      float var3 = this.m1357();
      float var4 = this.m1358();
      float var5 = var3 + 8.0F;
      float var6 = var4 + 8.0F;
      float var7 = var5 + 78.0F + 8.0F;
      if (this.f62) {
         this.f66 = Math.clamp((var1 - var5) / 78.0F, 0.0F, 1.0F);
         this.f67 = 1.0F - Math.clamp((var2 - var6) / 78.0F, 0.0F, 1.0F);
      } else if (this.f63 || this.m35(var1, var2, var7, var6, 10.0F, 78.0F)) {
         this.f65 = Math.clamp((var2 - var6) / 78.0F, 0.0F, 1.0F);
      }

      if (ThemeManager.f3) {
         float var8 = var3 + 132.0F + 8.0F;
         float var9 = var8 + 8.0F;
         float var10 = var9 + 78.0F + 8.0F;
         if (this.f83) {
            this.f81 = Math.clamp((var1 - var9) / 78.0F, 0.0F, 1.0F);
            this.f82 = 1.0F - Math.clamp((var2 - var6) / 78.0F, 0.0F, 1.0F);
         } else if (this.f84 || this.m35(var1, var2, var10, var6, 10.0F, 78.0F)) {
            this.f80 = Math.clamp((var2 - var6) / 78.0F, 0.0F, 1.0F);
         }
      }

      Color var11 = Color.getHSBColor(this.f65, this.f66, this.f67);
      ThemeManager.f1 = var11;
      if (ThemeManager.f3) {
         ThemeManager.f2 = Color.getHSBColor(this.f80, this.f81, this.f82);
      }

      if (this.f64 >= 0 && this.f64 < ThemeManager.f8.size()) {
         ThemeManager.f8.set(this.f64, var11);
      }
   }

   private void m1356(Color var1) {
      float[] var2 = Color.RGBtoHSB(var1.getRed(), var1.getGreen(), var1.getBlue(), null);
      this.f65 = var2[0];
      this.f66 = var2[1];
      this.f67 = var2[2];
      if (ThemeManager.f3) {
         float[] var3 = Color.RGBtoHSB(ThemeManager.f2.getRed(), ThemeManager.f2.getGreen(), ThemeManager.f2.getBlue(), null);
         this.f80 = var3[0];
         this.f81 = var3[1];
         this.f82 = var3[2];
      }
   }

   private float m1357() {
      float var1 = ((float)Render2DUtil.m113() - 430.0F) / 2.0F;
      float var2 = 320.0F;
      float var3 = ThemeManager.f3 ? 272.0F : 132.0F;
      return var1 + 110.0F + (var2 - var3) / 2.0F;
   }

   private float m1358() {
      float var1 = ((float)Render2DUtil.m189() - 290.0F) / 2.0F;
      return var1 + 290.0F - 108.0F - 14.0F;
   }

   private boolean m1359(float var1, float var2, int var3) {
      if (!this.m646()) {
         return false;
      } else {
         boolean var4 = this.f71 >= 0;
         float var5 = 36.0F;
         if (!this.m35(var1, var2, this.f72, this.f73, 62.0F, var5)) {
            if (var3 == 0) {
               this.m645();
            }

            return false;
         } else if (var3 != 0) {
            return true;
         } else {
            int var6 = (int)((var2 - this.f73) / 18.0F);
            if (var6 == 0) {
               this.m738();
            } else {
               if (var4) {
                  ThemeManager.m1004(this.f71);
                  this.f64 = -1;
                  this.m1362();
                  this.f69 = 1.0F;
               }

               this.m645();
            }

            return true;
         }
      }
   }

   private void m738() {
      Color var1 = this.m1360();
      if (var1 == null) {
         this.m645();
      } else {
         this.m1356(var1);
         ThemeManager.m8(var1);
         this.f64 = this.f71 >= 0 ? this.f71 : ThemeManager.m1380(var1);
         this.m743();
         this.f69 = 1.0F;
         this.m645();
      }
   }

   private Color m1360() {
      if (this.f71 < 0) {
         int var1 = -1 - this.f71;
         return var1 < ThemeManager.f7.size() ? ThemeManager.f7.get(var1).m1379() : null;
      } else {
         return this.f71 < ThemeManager.f8.size() ? ThemeManager.f8.get(this.f71) : null;
      }
   }

   private void m1361(float var1, float var2, int var3) {
      this.f71 = var3;
      this.f72 = var1;
      this.f73 = var2;
      this.m1362();
      this.f62 = false;
      this.f63 = false;
      this.f83 = false;
      this.f84 = false;
   }

   private void m645() {
      this.f71 = Integer.MIN_VALUE;
   }

   private boolean m646() {
      return this.f71 != Integer.MIN_VALUE;
   }

   private void m743() {
      this.m4(true);
   }

   private void m1362() {
      this.m4(false);
   }

   private float m1363() {
      if (this.f79 == 0L) {
         return 0.0F;
      } else {
         float var1 = (float)(System.currentTimeMillis() - this.f79) / 180.0F;
         var1 = Math.clamp(var1, 0.0F, 1.0F);
         return this.f77 ? var1 : 1.0F - var1;
      }
   }

   private void m61(boolean var1) {
      if (this.f77 != var1) {
         this.f78 = this.m1363();
         this.f77 = var1;
         long var2 = (long)((var1 ? this.f78 : 1.0F - this.f78) * 180.0F);
         this.f79 = System.currentTimeMillis() - var2;
      }
   }

   private void m4(boolean var1) {
      if (this.f61 != var1) {
         this.f68 = this.m1364();
         this.f78 = this.m1363();
         this.f61 = var1;
         long var2 = (long)((var1 ? this.f68 : 1.0F - this.f68) * 180.0F);
         this.f74 = System.currentTimeMillis() - var2;
      }
   }

   private float m1364() {
      float var1 = (float)(System.currentTimeMillis() - this.f74) / 180.0F;
      var1 = Math.clamp(var1, 0.0F, 1.0F);
      return this.f61 ? var1 : 1.0F - var1;
   }

   private Color m1365(Color var1) {
      return new Color(
         Math.round((float)var1.getRed() * 0.2F) + 8, Math.round((float)var1.getGreen() * 0.2F) + 8, Math.round((float)var1.getBlue() * 0.2F) + 10, 208
      );
   }

   private Color[] m1366(Color var1, Color var2, float var3) {
      Color[] var4 = new Color[9];

      for (int var5 = 0; var5 < 3; var5++) {
         for (int var6 = 0; var6 < 3; var6++) {
            float var7 = (float)(var5 + var6) / 4.0F;
            var4[var5 * 3 + var6] = this.m336(this.m1369(var1, var2, var7), var3);
         }
      }

      return var4;
   }

   private Color m1367(Color var1) {
      float[] var2 = Color.RGBtoHSB(var1.getRed(), var1.getGreen(), var1.getBlue(), null);
      return Color.getHSBColor((var2[0] + 0.12F) % 1.0F, var2[1], Math.min(1.0F, var2[2] * 1.05F));
   }

   private int m1244(int var1, int var2) {
      return ThemeManager.f4 == ThemeManager$1.f1 ? var1 : var2;
   }

   private float m1368(String var1, boolean var2) {
      float var3 = this.f52.getOrDefault(var1, 0.0F);
      float var4 = this.m1139(var3, var2 ? 1.0F : 0.0F, 0.02F, this.f53);
      this.f52.put(var1, var4);
      return var4;
   }

   private float m1139(float var1, float var2, float var3, float var4) {
      float var5 = 1.0F - (float)Math.exp((double)(-var3 * var4));
      float var6 = var1 + (var2 - var1) * Math.clamp(var5, 0.0F, 1.0F);
      return Math.abs(var6 - var2) < 0.001F ? var2 : var6;
   }

   private Color m1369(Color var1, Color var2, float var3) {
      float var4 = Math.clamp(var3, 0.0F, 1.0F);
      int var5 = (int)((float)var1.getRed() + (float)(var2.getRed() - var1.getRed()) * var4);
      int var6 = (int)((float)var1.getGreen() + (float)(var2.getGreen() - var1.getGreen()) * var4);
      int var7 = (int)((float)var1.getBlue() + (float)(var2.getBlue() - var1.getBlue()) * var4);
      int var8 = (int)((float)var1.getAlpha() + (float)(var2.getAlpha() - var1.getAlpha()) * var4);
      return new Color(var5, var6, var7, var8);
   }

   private boolean m1370(Color var1, Color var2) {
      return var1.getRed() == var2.getRed() && var1.getGreen() == var2.getGreen() && var1.getBlue() == var2.getBlue();
   }

   // $VF: Unable to simplify switch on enum
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private String m1371(Category var1) {
      return switch (var1) {
         case COMBAT -> "H";
         case RENDER -> "G";
         case MOVEMENT -> "D";
         case MISC -> "F";
         case PLAYER -> "P";
         case MENU -> "F";
         case CONFIG -> "F";
      };
   }

   // $VF: Unable to simplify switch on enum
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private String m1372(Category var1) {
      return switch (var1) {
         case COMBAT -> "Combat";
         case RENDER -> "Render";
         case MOVEMENT -> "Move";
         case MISC -> "Misc";
         case PLAYER -> "Player";
         case MENU -> "Menu";
         case CONFIG -> "Config";
      };
   }
}

package ua.daamky.features.render;

import com.mojang.blaze3d.textures.GpuTextureView;
import ua.daamky.events.PostMotionEvent;
import ua.daamky.events.RenderEvent;

import ua.daamky.features.combat.AutoSwap;
import ua.daamky.gui.clickgui.ClickGuiScreen;
import ua.daamky.gui.config.ConfigManager;
import ua.daamky.gui.theme.ThemeManager;
import ua.daamky.mixins.interfaces.IBossBarHud;
import ua.daamky.settings.BooleanSetting;
import ua.daamky.settings.ModeSetting;
import ua.daamky.settings.MultiChoiceSetting;
import ua.daamky.settings.NumberSetting;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.ModuleManager;
import ua.daamky.system.api.NewFunction;
import ua.daamky.system.events.EventHandler;
import ua.daamky.utils.ColoredText;
import ua.daamky.utils.FontMeasureUtil;
import ua.daamky.utils.ScissorUtil;
import ua.daamky.utils.KeybindFormatter;
import ua.daamky.utils.MsdfFontManager;
import ua.daamky.utils.NeuroCommand;
import ua.daamky.utils.client.UserProfile;
import ua.daamky.utils.render.ColorUtil;
import ua.daamky.utils.render.Render2DUtil;
import ua.daamky.utils.render.fonts.FontRenderUtil;
import ua.daamky.utils.render.shaders.SweepShader;
import ua.daamky.utils.render.shaders.TextureShader;
import java.awt.Color;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.ClientBossBar;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.util.DefaultSkinHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.SkinTextures;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardDisplaySlot;
import net.minecraft.scoreboard.ScoreboardEntry;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.Team;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.GameMode;
import org.lwjgl.glfw.GLFW;

@NewFunction(
   I0 = "Interface",
   I00 = "Минималистичный HUD с ватермаркой и активными биндами",
   I000 = Category.RENDER
)
public class Interface extends Module {
   public static Interface f1;
   private static final float f2 = 7.0F;
   private static final float f3 = 5.0F;
   private static final float f4 = 22.0F;
   private static final float f5 = 6.0F;
   private static final float f6 = 6.0F;
   private static final float f7 = 2.0F;
   private static final float f8 = 22.0F;
   private static final float f9 = 17.0F;
   private static final float f10 = 8.5F;
   private static final float f11 = 7.2F;
   private static final float f12 = 9.0F;
   private static final float f13 = 4.0F;
   private static final float f14 = 8.0F;
   private static final float f15 = 0.0075F;
   private static final float f16 = 0.018F;
   private static final float f17 = 0.018F;
   private static final float f18 = 14.0F;
   private static final float f19 = 0.45F;
   private static final float f20 = 22.0F;
   private static final float f21 = 17.0F;
   private static final float f22 = 8.0F;
   private static final float f23 = 0.0075F;
   private static final float f24 = 0.018F;
   private static final float f25 = 0.018F;
   private static final float f26 = 14.0F;
   private static final float f27 = 0.45F;
   private static final float f28 = 107.0F;
   private static final float f29 = 40.0F;
   private static final float f30 = 96.0F;
   private static final float f31 = 34.0F;
   private static final float f32 = 96.0F;
   private static final float f33 = 17.0F;
   private static final float f34 = 0.018F;
   private static final float f35 = 118.0F;
   private static final float f36 = 0.014F;
   private static final String f37 = "Default";
   private static final String f38 = "Лёд";
   private static final String f39 = "Жидкое стекло";
   private static final float f40 = 29.0F;
   private static final float f41 = 4.3F;
   private static final float f42 = 21.5F;
   private static final float f43 = 6.9F;
   private static final float f44 = 6.7F;
   private static final Color f45 = new Color(245, 204, 96);
   private static final float f46 = 0.5F;
   private static final float f47 = 0.012F;
   private final BooleanSetting f48 = new BooleanSetting("Watermark", true);
   private final MultiChoiceSetting f49 = new MultiChoiceSetting(
      "Watermark: инфо",
      "FPS",
      "Пинг",
      "BPS",
      "Сервер",
      "Время"
   );
   private final ModeSetting f50 = new ModeSetting(
      "Стиль HUD",
      "Default",
      "Лёд",
      "Жидкое стекло"
   );
   private final ModeSetting f51 = new ModeSetting(
      "Watermark Style",
      "Default",
      "LiquidGlass Watermark"
   );
   private final BooleanSetting f52 = new BooleanSetting("Watermark Лёд", false);
   private final BooleanSetting f53 = new BooleanSetting("Watermark Жидкое стекло", false);
   private final BooleanSetting f54 = new BooleanSetting("Keybinds Лёд", false);
   private final BooleanSetting f55 = new BooleanSetting("Keybinds Жидкое стекло", false);
   private final BooleanSetting f56 = new BooleanSetting("Potions Лёд", false);
   private final BooleanSetting f57 = new BooleanSetting("Potions Жидкое стекло", false);
   private final BooleanSetting f58 = new BooleanSetting("TargetHUD Лёд", false);
   private final BooleanSetting f59 = new BooleanSetting("TargetHUD Жидкое стекло", false);
   private final BooleanSetting f60 = new BooleanSetting("Hotbar Лёд", false);
   private final BooleanSetting f61 = new BooleanSetting("Hotbar Жидкое стекло", false);
   private final BooleanSetting f62 = new BooleanSetting("Keybinds", true);
   private final BooleanSetting f63 = new BooleanSetting("TargetHUD", true);
   private final BooleanSetting f64 = new BooleanSetting("Armor HUD", false);
   private final ModeSetting f65 = new ModeSetting(
      "Стиль TargetHUD", "Круглый"
   );
   private final BooleanSetting f66 = new BooleanSetting("Potions", true);
   private final BooleanSetting f67 = new BooleanSetting("Hotbar", true);
   private final BooleanSetting f68 = new BooleanSetting("Scoreboard", true);
   private final BooleanSetting f69 = new BooleanSetting("BossBar", true);
   private final NumberSetting f70 = new NumberSetting("Сила блюра", 25.0, 0.0, 100.0, 1.0);
   private final BooleanSetting f71 = new BooleanSetting("Шейдер", false);
   private Interface$2 f72 = Interface$2.f1;
   private boolean f73;
   private boolean f74;
   private float f75;
   private float f76;
   private float f77 = 8.0F;
   private float f78 = 8.0F;
   private float f79;
   private float f80 = 0.0F;
   private double f81;
   private double f82;
   private float f83;
   private boolean f84;
   private String f85 = "";
   private long f86 = -1L;
   private float f87 = 8.0F;
   private float f88 = 37.0F;
   private float f89;
   private float f90 = 39.0F;
   private float f91 = 96.0F;
   private float f92;
   private float f93;
   private float f94 = 8.0F;
   private float f95 = 63.0F;
   private float f96 = 8.0F;
   private float f97 = 116.0F;
   private float f98;
   private float f99 = 39.0F;
   private float f100 = 96.0F;
   private float f101;
   private float f102;
   private float f103 = 8.0F;
   private float f104 = 200.0F;
   private float f105 = 56.0F;
   private float f106 = 14.0F;
   private float f107;
   private float f108 = 8.0F;
   private float f109 = 160.0F;
   private float f110 = 120.0F;
   private float f111 = 40.0F;
   private float f112 = -1.0F;
   private float f113 = 120.0F;
   private float f114 = 120.0F;
   private long f115 = 0L;
   private boolean f116;
   private float f117;
   private float f118;
   private float f119;
   private float f120;
   private Interface$4 f121 = Interface$4.f1;
   private float f122;
   private float f123;
   private LivingEntity f124;
   private long f125;
   private long f126;
   private boolean f127;
   private float f128;
   private String f129 = "";
   private String f130 = "";
   private long f131;
   private boolean f132;
   private float f133;
   private float f134;
   private float f135;
   private float f136;
   private float f137;
   private float f138;
   private float f139;
   private final float[] f140 = new float[9];
   private final Map<Module, Float> f141 = new HashMap<>();
   private final Map<String, Float> f142 = new HashMap<>();
   private final Map<String, Interface$6> f143 = new HashMap<>();
   private float f144 = 1.0F;
   private float f145 = 0.0F;
   private static final float f146 = 18.0F;
   private static final float f147 = 2.5F;
   private static final float f148 = 4.0F;
   private static final float f149 = 6.0F;
   private static final float f150 = 7.0F;
   private static final float f151 = 5.0F;
   private static final float f152 = 64.0F;
   private static final float f153 = 3.5F;
   private static final float f154 = 9.0F;
   private static final float f155 = 3.0F;
   private static final float f156 = 26.0F;
   private static final float f157 = 3.0F;
   private static final float f158 = 7.1F;
   private static final float f159 = 6.4F;
   private static final float f160 = 7.2F;
   private static final float f161 = 7.6F;
   private static final long f162 = 1200L;
   private static final long f163 = 260L;
   private static final float f164 = 0.024F;
   private static final float f165 = 0.018F;
   private static final float f166 = 0.014F;
   private static final float f167 = 1.3F;
   private static final float f168 = 0.92F;
   private static final float f169 = 186.0F;
   private static final float f170 = 5.5F;
   private static final float f171 = 3.0F;
   private static final float f172 = 8.0F;
   private static final float f173 = 6.0F;
   private static final float f174 = 9.5F;
   private static final float f175 = 3.0F;
   private static final int f176 = 6;
   private final Map<UUID, Float> f177 = new HashMap<>();

   public Interface() {
      f1 = this;
      this.f48.m5(() -> {
         this.f51.setVisible(false);
         this.f52.setVisible(false);
         this.f53.setVisible(false);
      });
      this.f62.m5(() -> {
         this.f54.setVisible(false);
         this.f55.setVisible(false);
      });
      this.f66.m5(() -> {
         this.f56.setVisible(false);
         this.f57.setVisible(false);
      });
      this.f63.m5(() -> {
         this.f65.setVisible(false);
         this.f58.setVisible(false);
         this.f59.setVisible(false);
      });
      this.f67.m5(() -> {
         this.f60.setVisible(false);
         this.f61.setVisible(false);
      });
      this.addSettings(
         new Setting[]{
            this.f48,
            this.f49,
            this.f50,
            this.f51,
            this.f52,
            this.f53,
            this.f62,
            this.f54,
            this.f55,
            this.f63,
            this.f65,
            this.f58,
            this.f59,
            this.f66,
            this.f56,
            this.f57,
            this.f67,
            this.f60,
            this.f61,
            this.f68,
            this.f69,
            this.f64,
            this.f70,
            this.f71
         }
      );
   }

   @EventHandler
   public void m67(PostMotionEvent var1) {
      if (this.mc.player == null) {
         this.f84 = false;
         this.f83 = 0.0F;
      } else {
         double var2 = this.mc.player.getX();
         double var4 = this.mc.player.getZ();
         if (this.f84) {
            double var6 = var2 - this.f81;
            double var8 = var4 - this.f82;
            float var10 = (float)(Math.sqrt(var6 * var6 + var8 * var8) * 20.0);
            this.f83 = this.f83 + (var10 - this.f83) * 0.35F;
         }

         this.f81 = var2;
         this.f82 = var4;
         this.f84 = true;
      }
   }

   @EventHandler
   public void m330(RenderEvent var1) {
      DrawContext var2 = var1.m583();
      if (var2 != null && !this.mc.options.hudHidden) {
         if (this.m650()) {
            boolean var3 = this.mc.currentScreen instanceof ChatScreen;
            List var4 = this.m1088();
            List var5 = this.m1082();
            boolean var6 = this.f62.m6() && (!var4.isEmpty() || var3);
            boolean var7 = this.f66.m6() && (!var5.isEmpty() || var3);
            LivingEntity var8 = this.m1137();
            Object var9 = var8 != null ? var8 : (this.m651() ? this.mc.player : null);
            boolean var10 = this.f63.m6() && var9 != null;
            this.m1120(var6, var10, var7, var4, var5);
            this.m645();
            if (this.f48.m6()) {
               float var11 = 0.0F;
               if (this.f69.m6()) {
                  float var12 = this.m1091();
                  if (var12 > 0.0F) {
                     float var13 = (float)Render2DUtil.m113() / 2.0F;
                     float var14 = var13 - 93.0F;
                     float var15 = var13 + 93.0F;
                     boolean var16 = this.f77 < var15 && this.f77 + this.f79 > var14;
                     if (var16 && this.f78 < var12) {
                        var11 = var12 - this.f78;
                     }
                  }
               }

               this.f80 = this.f80 + (var11 - this.f80) * 0.2F;
               if (var11 == 0.0F && this.f80 < 0.1F) {
                  this.f80 = 0.0F;
               }

               this.m954(var2, this.f77, this.f78 + this.f80);
            }

            if (this.f62.m6() && this.f93 > 0.02F) {
               this.m1069(var2, this.f87, this.f88, this.m795(), this.f93);
            }

            if (this.f66.m6() && this.f102 > 0.02F) {
               this.m1070(var2, this.f96, this.f97, this.m1083(), this.f102);
            }

            if (this.f64.m6()) {
               this.m1081(var2, this.f103, this.f104);
            }

            if (this.f63.m6() && this.f107 > 0.02F) {
               this.m1071(var2, this.f94, this.f95, (LivingEntity)var9, this.f107);
            }

            if (this.f117 > 0.02F && this.m651()) {
               this.m1074(var2, this.f119, this.f120, this.f117);
            }

            if (this.f67.m6()) {
               this.m1090(var2);
            }

            if (this.f69.m6()) {
               this.m1092(var2);
            }

            if (this.f68.m6()) {
               this.m1149(var2, this.f108, this.f109);
            }

            if (this.m651() && this.f72 != Interface$2.f1) {
               this.m1063(var2);
            }
         }
      }
   }

   private void m1063(DrawContext var1) {
      float var2 = (float)Render2DUtil.m113();
      float var3 = (float)Render2DUtil.m189();
      Color var4 = this.m1143(ThemeManager.m1379(), 110);
      Render2DUtil.m195(var2 / 2.0F - 0.3F, 0.0F, 0.6F, var3, 0.0F, var4);
      Render2DUtil.m195(0.0F, var3 / 2.0F - 0.3F, var2, 0.6F, 0.0F, var4);
   }

   private float m614(float var1, float var2) {
      float var3 = (float)Render2DUtil.m113();
      float var4 = 4.0F;
      if (Math.abs(var1 + var2 / 2.0F - var3 / 2.0F) < var4) {
         return var3 / 2.0F - var2 / 2.0F;
      } else if (Math.abs(var1 - 2.0F) < var4) {
         return 2.0F;
      } else {
         return Math.abs(var1 + var2 - (var3 - 2.0F)) < var4 ? var3 - 2.0F - var2 : var1;
      }
   }

   private float m423(float var1, float var2) {
      float var3 = (float)Render2DUtil.m189();
      float var4 = 4.0F;
      if (Math.abs(var1 + var2 / 2.0F - var3 / 2.0F) < var4) {
         return var3 / 2.0F - var2 / 2.0F;
      } else if (Math.abs(var1 - 2.0F) < var4) {
         return 2.0F;
      } else {
         return Math.abs(var1 + var2 - (var3 - 2.0F)) < var4 ? var3 - 2.0F - var2 : var1;
      }
   }

   private void m954(DrawContext var1, float var2, float var3) {
      FontRenderUtil.m91();
      String var4 = NeuroCommand.m18();
      String var5 = var4 != null ? "REC " : UserProfile.m58("Daamky");
      String var6 = var4 != null ? var4 : "";
      String var7 = "|";
      float var8 = FontRenderUtil.m235(var5, 8.5F) + (var6.isEmpty() ? 0.0F : FontRenderUtil.m235(var6, 8.5F));
      float var9 = FontRenderUtil.m235(var7, 7.2F);
      float var10 = 12.0F;
      float var11 = FontRenderUtil.m237(FontRenderUtil.f3, "A", var10);
      float var12 = var11 + 6.0F + var9 + 6.0F + var8;
      float var13 = 12.0F + var12;
      List var14 = this.m1064();
      float var15 = 6.0F + var9 + 6.0F;
      float var16 = 0.0F;

      for (int var17 = 0; var17 < var14.size(); var17++) {
         if (var17 > 0) {
            var16 += var15;
         }

         var16 += ((Interface$7)var14.get(var17)).m523();
      }

      boolean var32 = this.m1115() != Interface$3.f1;
      float var18 = 12.0F + var16;
      float var19 = 12.0F + var12 + (var14.isEmpty() ? 0.0F : var15 + var16);
      this.f79 = var19;
      if (var32) {
         this.m1068(var2, var3, var19, 22.0F);
      } else {
         this.m1110("Watermark", var2, var3, var19, 22.0F, 8.0F);
      }

      float var21 = this.m1142(var3, 22.0F) + 0.5F;
      float var22 = this.m1141(var3, 22.0F, 7.2F);
      Color var23 = new Color(120, 120, 126);
      float var24 = var2 + 6.0F;
      float var25 = var24 + var11 + 6.0F;
      float var26 = var25 + var9 + 6.0F;
      Render2DUtil.m208(var1, FontRenderUtil.f3, var24, var21 - 1.5F, "A", var10, ThemeManager.m1379());
      Render2DUtil.m205(var1, var25, var22, var7, 7.2F, var23);
      float var27 = this.m1141(var3, 22.0F, 8.5F);
      Render2DUtil.m205(var1, var26, var27, var5, 8.5F, new Color(255, 255, 255));
      if (!var6.isEmpty()) {
         Render2DUtil.m205(var1, var26 + FontRenderUtil.m235(var5, 8.5F), var27, var6, 8.5F, new Color(235, 70, 70));
      }

      float var28 = var26 + var8 + 6.0F;
      if (!var14.isEmpty()) {
         Render2DUtil.m205(var1, var28, var22, var7, 7.2F, var23);
      }

      float var29 = var28 + var9 + 6.0F;

      for (int var30 = 0; var30 < var14.size(); var30++) {
         if (var30 > 0) {
            Render2DUtil.m205(var1, var29, var22, var7, 7.2F, var23);
            var29 += var9 + 6.0F;
         }

         Interface$7 var31 = (Interface$7)var14.get(var30);
         Render2DUtil.m208(var1, FontRenderUtil.f3, var29, var21, var31.m40(), 9.0F, ThemeManager.m1379());
         var29 += var31.m192() + 4.0F;
         if (var31.m41()) {
            this.m1108(var1, var31.m37(), var31.m18(), var29, var22, 7.2F, new Color(255, 255, 255), "left");
         } else {
            Render2DUtil.m205(var1, var29, var22, var31.m18(), 7.2F, new Color(255, 255, 255));
         }

         var29 += var31.m273() + 6.0F;
      }
   }

   private List<Interface$7> m1064() {
      ArrayList var1 = new ArrayList();
      if (this.f49.m20("FPS")) {
         this.m1065(var1, "watermark.fps", "C", this.mc.getCurrentFps() + "fps", true);
      }

      if (this.f49.m20("Пинг")) {
         this.m1065(var1, "watermark.ping", "B", this.m1089() + "ms", true);
      }

      if (this.f49.m20("BPS")) {
         this.m1065(
            var1,
            "watermark.bps",
            "D",
            String.format(Locale.ROOT, "%.1f bps", this.f83),
            true
         );
      }

      if (this.f49.m20("Сервер")) {
         this.m1065(var1, "watermark.server", "E", this.m1066(), false);
      }

      if (this.f49.m20("Время")) {
         this.m1065(var1, "watermark.time", "F", this.m842(), true);
      }

      AutoSwap var2 = ModuleManager.getModule(AutoSwap.class);
      if (var2 != null && var2.isEnabled() && var2.m687()) {
         this.m1065(var1, "watermark.autocerber", "H", var2.m309(), true);
      }

      return var1;
   }

   private String m842() {
      long var1 = System.currentTimeMillis() / 1000L;
      if (var1 != this.f86) {
         this.f86 = var1;
         LocalTime var3 = LocalTime.now();
         this.f85 = String.format(Locale.ROOT, "%02d:%02d:%02d", var3.getHour(), var3.getMinute(), var3.getSecond());
      }

      return this.f85;
   }

   private void m1065(List<Interface$7> var1, String var2, String var3, String var4, boolean var5) {
      var1.add(new Interface$7(var2, var3, var4, FontRenderUtil.m237(FontRenderUtil.f3, var3, 9.0F), FontRenderUtil.m235(var4, 7.2F), var5));
   }

   private String m1066() {
      return this.mc.getCurrentServerEntry() != null && this.mc.getCurrentServerEntry().address != null
         ? this.mc.getCurrentServerEntry().address
         : "SinglePlayer";
   }

   private Color[] m1067(String var1, Color var2, Color var3) {
      Color[] var4 = new Color[var1.length()];
      long var5 = System.currentTimeMillis();

      for (int var7 = 0; var7 < var1.length(); var7++) {
         float var8 = (float)((var5 - (long)var7 * 120L) % 2000L) / 2000.0F;
         if (var8 < 0.0F) {
            var8++;
         }

         float var9 = (float)(Math.sin((double)var8 * Math.PI * 2.0) * 0.5 + 0.5);
         var4[var7] = new Color(ColorUtil.m260(var2.getRGB(), var3.getRGB(), var9), true);
      }

      return var4;
   }

   private void m1068(float var1, float var2, float var3, float var4) {
      float var5 = 8.5F;
      Render2DUtil.m217(var1 + 0.5F, var2 + 1.2F, var3 - 1.0F, var4 - 0.4F, var5, 10.0F, 0.34F, 2.4F, new Color(0, 0, 0, 170));
      this.m196(var1, var2, var3, var4, var5, 0.9F);
      Render2DUtil.m202(
         var1 + 0.45F,
         var2 + 0.45F,
         var3 - 0.9F,
         var4 - 0.9F,
         var5,
         0.85F,
         new Color(255, 255, 255, 82),
         new Color(255, 255, 255, 44),
         this.m1143(ThemeManager.m1379(), 52),
         new Color(255, 255, 255, 24)
      );
      Render2DUtil.m195(var1 + 6.0F, var2 + 2.1F, var3 - 12.0F, 1.05F, 0.5F, new Color(255, 255, 255, 54), new Color(255, 255, 255, 6));
      this.m1119("WatermarkGlass", var1, var2, var3, var4, var5, 1.0F);
   }

   private void m1069(DrawContext var1, float var2, float var3, List<Module> var4, float var5) {
      float var6 = this.m151(var5);
      float var7 = this.f91;
      float var8 = this.f92;
      this.f89 = var7;
      this.f90 = var8;
      this.m1112(Interface$4.f3, var2, var3, var7, var8, 6.0F, var6);
      if (!(var8 <= 0.5F)) {
         ScissorUtil.m357((double)var2, (double)var3, (double)var7, (double)var8);
         float var9 = this.m335(var8 / 22.0F, 0.0F, 1.0F);
         float var10 = var6 * this.m151(var9);
         if (var10 > 0.01F) {
            this.m1113(Interface$4.f3, var2, var3, var7, 22.0F * var9, 0.0F, 0.0F, 6.0F, 6.0F, var10);
            Render2DUtil.m208(
               var1, FontRenderUtil.f3, var2 + 7.0F, var3 + 7.0F, "I", 8.0F, this.m336(ThemeManager.m1379(), var10)
            );
            Render2DUtil.m205(
               var1,
               var2 + 7.0F + 12.0F,
               this.m1141(var3, 22.0F, 8.0F),
               "Keybinds",
               8.0F,
               this.m336(new Color(238, 238, 242), var10)
            );
         }

         float var11 = var3 + 22.0F;
         float var12 = this.m335((var5 - 0.45F) / 0.55F, 0.0F, 1.0F);

         for (Module var14 : var4) {
            float var15 = this.m151(this.f141.getOrDefault(var14, 0.0F)) * this.m151(var12);
            if (!(var15 <= 0.02F)) {
               float var16 = 17.0F * var15;
               float var17 = this.m335((var3 + var8 - var11) / Math.max(1.0F, var16), 0.0F, 1.0F);
               if (var17 <= 0.02F) {
                  break;
               }

               float var18 = var6 * var15 * this.m151(var17);
               float var19 = 14.0F * (1.0F - var15);
               String var20 = KeybindFormatter.m90(var14.getKey());
               Render2DUtil.m205(var1, var2 + 7.0F - var19, this.m1141(var11, var16, 8.0F), var14.getName(), 8.0F, this.m336(new Color(230, 230, 235), var18));
               Render2DUtil.m206(
                  var1,
                  var2 + var7 - 7.0F + var19 - 1.5F,
                  this.m1141(var11, var16, 8.0F),
                  var20,
                  8.0F,
                  this.m336(ThemeManager.m1379(), var18),
                  "right"
               );
               var11 += var16;
            }
         }

         ScissorUtil.m29();
      }
   }

   private void m1070(DrawContext var1, float var2, float var3, List<StatusEffectInstance> var4, float var5) {
      float var6 = this.m151(var5);
      float var7 = this.f100;
      float var8 = this.f101;
      this.f98 = var7;
      this.f99 = var8;
      this.m1112(Interface$4.f4, var2, var3, var7, var8, 6.0F, var6);
      if (!(var8 <= 0.5F)) {
         ScissorUtil.m357((double)var2, (double)var3, (double)var7, (double)var8);
         float var9 = this.m335(var8 / 22.0F, 0.0F, 1.0F);
         float var10 = var6 * this.m151(var9);
         if (var10 > 0.01F) {
            this.m1113(Interface$4.f4, var2, var3, var7, 22.0F * var9, 0.0F, 0.0F, 6.0F, 6.0F, var10);
            Render2DUtil.m208(
               var1, FontRenderUtil.f3, var2 + 7.0F, var3 + 7.0F, "J", 8.0F, this.m336(ThemeManager.m1379(), var10)
            );
            Render2DUtil.m205(
               var1,
               var2 + 7.0F + 12.0F,
               this.m1141(var3, 22.0F, 8.0F),
               "Potions",
               8.0F,
               this.m336(new Color(238, 238, 242), var10)
            );
         }

         float var11 = var3 + 22.0F;
         float var12 = this.m335((var5 - 0.45F) / 0.55F, 0.0F, 1.0F);

         for (StatusEffectInstance var14 : var4) {
            String var15 = this.m1085(var14);
            float var16 = this.m151(this.f142.getOrDefault(var15, 0.0F)) * this.m151(var12);
            if (!(var16 <= 0.02F)) {
               float var17 = 17.0F * var16;
               float var18 = this.m335((var3 + var8 - var11) / Math.max(1.0F, var17), 0.0F, 1.0F);
               if (var18 <= 0.02F) {
                  break;
               }

               float var19 = var6 * var16 * this.m151(var18);
               float var20 = 14.0F * (1.0F - var16);
               String var21 = this.m1086(var14);
               String var22 = this.m1087(var14);
               float var23 = 9.0F;
               float var24 = 4.0F;
               Identifier var25 = InGameHud.getEffectTexture(var14.getEffectType());
               float var26 = var2 + 7.0F - var20 + (var25 != null ? var23 + var24 : 0.0F);
               float var27 = var7 - 14.0F - 39.0F - (var25 != null ? var23 + var24 : 0.0F);
               float var28 = this.m1141(var11, var17, 8.0F);
               float var29 = FontRenderUtil.m235(var21, 8.0F);
               Color var30 = this.m336(new Color(230, 230, 235), var19);
               if (var25 != null) {
                  int var31 = new Color(255, 255, 255, (int)(var19 * 255.0F)).getRGB();
                  var1.drawGuiTexture(
                     RenderPipelines.GUI_TEXTURED, var25, (int)(var2 + 7.0F - var20), (int)(this.m1141(var11, var17, var23) - 1.0F) + 2, 9, 9, var31
                  );
               }

               if (var29 > var27) {
                  float var35 = var29 - var27;
                  float var32 = Math.max(1.8F, var35 * 2.0F / 26.0F);
                  float var33 = (float)(System.currentTimeMillis() % (long)(var32 * 1000.0F)) / (var32 * 1000.0F);
                  float var34 = var35 * (0.5F - 0.5F * (float)Math.cos((double)var33 * Math.PI * 2.0));
                  ScissorUtil.m357((double)var26, (double)(var28 - 2.0F), (double)var27, 12.0);
                  Render2DUtil.m205(var1, var26 - var34, var28, var21, 8.0F, var30);
                  ScissorUtil.m29();
               } else {
                  Render2DUtil.m205(var1, var26, var28, var21, 8.0F, var30);
               }

               this.m1108(
                  var1,
                  "potion.duration." + var15,
                  var22,
                  var2 + var7 - 7.0F + var20,
                  this.m1141(var11, var17, 8.0F),
                  8.0F,
                  this.m336(ThemeManager.m1379(), var19),
                  "right"
               );
               var11 += var17;
            }
         }

         ScissorUtil.m29();
      }
   }

   private void m1071(DrawContext var1, float var2, float var3, LivingEntity var4, float var5) {
      float var6 = this.m151(var5);
      if (var6 > 0.02F) {
         this.m1073(var1, var2, var3, var4, var6);
      }
   }

   private void m1072(DrawContext var1, float var2, float var3, LivingEntity var4, float var5) {
      LivingEntity var6 = var4 != null ? var4 : this.f124;
      float var7 = var4 != null ? 1.0F : 0.0F;
      this.f145 = this.f145 + (var7 - this.f145) * 0.12F;
      if (var6 != null) {
         this.f124 = var4 != null ? var4 : this.f124;
         float var8 = this.m335(var5, 0.0F, 1.0F);
         float var11 = 107.0F;
         float var12 = 40.0F;
         float var13 = 6.0F;
         this.m1112(Interface$4.f5, var2, var3, var11 - 10.0F, var12 - 4.0F, 4.0F, var8);
         float var14 = var2 + var13;
         float var15 = var3 - 14.0F;
         this.m1080(var1, var6, var14, var15, this.f145);
         ScissorUtil.m357((double)var2, (double)var3, (double)var11, (double)var12);
         float var16 = 24.0F;
         float var17 = var2 + var13;
         float var18 = var3 + (var12 - 4.0F - var16) / 2.0F;
         this.m1078(var1, var6, var17, var18, var8, var16, 3.0F);
         float var19 = var17 + var16 + 6.0F;
         float var20 = var2 + var11 - var13;
         float var21 = var20 - var19;
         String var22 = var6.getName().getString();
         float var23 = Math.max(0.0F, var6.getHealth());
         float var24 = Math.max(1.0F, var6.getMaxHealth());
         float var25 = Math.max(0.0F, var6.getAbsorptionAmount());
         float var26 = this.m335(var23 / var24, 0.0F, 1.0F);
         float var27 = this.m335(var25 / var24, 0.0F, 1.0F);
         this.f144 = this.f144 + (var26 - this.f144) * 0.12F;
         float var28 = var23 / var24 * 100.0F;
         String var29 = Math.round(var28) + "%";
         float var30 = var3 + (var12 - 24.0F) / 2.0F;
         float var32 = var30 + 20.0F;
         Render2DUtil.m205(var1, var19, var30 - 2.5F, this.m999(var22, var21 - 22.0F, 6.9F), 6.9F, this.m336(new Color(245, 245, 248), var8));
         float var33 = var21 - 6.0F;
         Render2DUtil.m195(var19, var32 - 3.5F, var33, 4.5F, 2.25F, this.m336(new Color(40, 40, 44), var8));
         Render2DUtil.m195(var19, var32 - 3.5F, Math.max(4.5F, var33 * this.f144), 4.5F, 2.25F, this.m336(this.m1144(ThemeManager.m1379(), 0.08F), var8));
         if (var27 > 0.001F) {
            Render2DUtil.m195(var19, var32 - 3.5F, Math.max(3.5F, var33 * var27), 1.4F, 0.7F, this.m336(f45, var8));
         }

         this.m1108(
            var1,
            "target.thunder.health",
            var29,
            var20 - 35.0F,
            var30 + 15.5F,
            6.7F,
            this.m336(new Color(236, 236, 240), var8),
            "center"
         );
         ScissorUtil.m29();
      }
   }

   private void m1073(DrawContext var1, float var2, float var3, LivingEntity var4, float var5) {
      LivingEntity var6 = var4 != null ? var4 : this.f124;
      float var7 = var4 != null ? 1.0F : 0.0F;
      this.f145 = this.f145 + (var7 - this.f145) * 0.12F;
      if (var6 != null) {
         this.f124 = var4 != null ? var4 : this.f124;
         float var8 = this.m335(var5, 0.0F, 1.0F);
         float var9 = 105.0F;
         float var10 = 34.0F;
         float var11 = 6.0F;
         this.m1112(Interface$4.f5, var2, var3, var9, var10, 8.0F, var8);
         float var12 = Math.max(0.0F, var6.getHealth());
         float var13 = Math.max(1.0F, var6.getMaxHealth());
         float var14 = Math.max(0.0F, var6.getAbsorptionAmount());
         float var15 = this.m335(var12 / var13, 0.0F, 1.0F);
         float var16 = this.m335(var14 / var13, 0.0F, 1.0F);
         this.f144 = this.f144 + (var15 - this.f144) * 0.12F;
         ScissorUtil.m357((double)var2, (double)var3, (double)var9, (double)var10);
         float var17 = var2 + var11;
         float var18 = var3 + (var10 - 21.5F) / 2.0F;
         this.m1078(var1, var6, var17, var18, var8, 21.5F, 3.5F);
         float var19 = var17 + 21.5F + 5.0F;
         String var20 = var6.getName().getString();
         String var21 = this.mc.player == null
            ? "Distance: 0.0"
            : String.format(Locale.US, "Distance: %.1f", this.mc.player.distanceTo(var6));
         Render2DUtil.m205(var1, var19, var3 + 8.0F, this.m999(var20, 42.0F, 7.4F), 7.4F, this.m336(new Color(245, 245, 248), var8));
         this.m1108(
            var1,
            "target.round.distance",
            var21,
            var19,
            var3 + 18.5F,
            5.9F,
            this.m336(new Color(235, 235, 238), var8),
            "left"
         );
         float var22 = var2 + var9 - 16.0F;
         float var23 = var3 + var10 / 2.0F;
         Render2DUtil.m212(var22, var23, 10.4F, 2.8F, this.m336(new Color(86, 86, 90), var8));
         Render2DUtil.m213(var22, var23, 10.4F, 2.8F, -90.0F, this.f144, this.m336(ThemeManager.m1379(), var8));
         if (var16 > 0.001F) {
            Render2DUtil.m213(var22, var23, 13.0F, 2.1F, -90.0F, var16, this.m336(f45, var8));
         }

         String var24 = this.m1145(var12);
         this.m1108(
            var1,
            "target.round.health",
            var24,
            var22 + 0.1F,
            var23 - 3.8F,
            6.3F,
            this.m336(new Color(246, 246, 248), var8),
            "center"
         );
         ScissorUtil.m29();
         this.m1080(var1, var6, var2 + 35.0F, var3 + var10 + 3.5F, this.f145 * var8);
      }
   }

   private void m1074(DrawContext var1, float var2, float var3, float var4) {
      float var5 = this.m151(var4);
      float var6 = this.m1131();
      float var7 = (1.0F - var5) * 7.0F;
      float var8 = var3 - var7;
      this.m1111("HudStyleMenu", var2, var8, 118.0F, var6 * var5, 6.0F, var5);
      if (!(var5 <= 0.08F)) {
         ScissorUtil.m357((double)var2, (double)var8, 118.0, (double)(var6 * var5));
         Render2DUtil.m205(var1, var2 + 7.0F, var8 + 5.4F, "HUD", 7.4F, this.m336(new Color(225, 225, 230), var5));
         this.m1075(var1, var2, var8 + 17.0F, "Default", this.m1115() == Interface$3.f1, var5);
         this.m1075(var1, var2, var8 + 34.0F, "Лёд", this.m1115() == Interface$3.f2, var5);
         this.m1075(var1, var2, var8 + 51.0F, "Жидкое стекло", this.m1115() == Interface$3.f3, var5);
         ScissorUtil.m29();
      }
   }

   private void m1075(DrawContext var1, float var2, float var3, String var4, boolean var5, float var6) {
      boolean var7 = this.m1138(this.f122, this.f123, var2, var3, 118.0F, 17.0F);
      if (var7 || var5) {
         Render2DUtil.m195(
            var2 + 3.0F, var3 + 2.0F, 112.0F, 13.0F, 4.0F, var5 ? this.m336(ThemeManager.m1379(), 0.55F * var6) : this.m336(new Color(38, 38, 42, 210), var6)
         );
      }

      Render2DUtil.m205(var1, var2 + 8.0F, var3 + 5.2F, var4, 7.0F, this.m336(var5 ? new Color(255, 255, 255) : new Color(210, 210, 215), var6));
   }

   private void m1076(DrawContext var1, float var2, float var3, String var4, float var5) {
      boolean var6 = this.f65.m17(var4);
      boolean var7 = this.m1138(this.f122, this.f123, var2, var3, 118.0F, 17.0F);
      if (var7 || var6) {
         Render2DUtil.m195(
            var2 + 3.0F, var3 + 2.0F, 112.0F, 13.0F, 4.0F, var6 ? this.m336(ThemeManager.m1379(), 0.55F * var5) : this.m336(new Color(38, 38, 42, 210), var5)
         );
      }

      Render2DUtil.m205(var1, var2 + 8.0F, var3 + 5.2F, var4, 7.0F, this.m336(var6 ? new Color(255, 255, 255) : new Color(210, 210, 215), var5));
   }

   private void m1077(DrawContext var1, LivingEntity var2, float var3, float var4, float var5) {
      this.m1078(var1, var2, var3, var4, var5, 29.0F, 4.3F);
   }

   private void m1078(DrawContext var1, LivingEntity var2, float var3, float var4, float var5, float var6, float var7) {
      if (var2 instanceof PlayerEntity var11) {
         SkinTextures var9 = var11 instanceof AbstractClientPlayerEntity var10 ? var10.getSkin() : DefaultSkinHelper.getSkinTextures(var11.getUuid());
         Identifier var12 = var9.body().texturePath();
         this.m1079(var12, var3, var4, var6, var7, 0.125F, 0.125F, 0.125F, 0.125F, var5);
         this.m1079(var12, var3, var4, var6, var7, 0.625F, 0.125F, 0.125F, 0.125F, var5);
      } else {
         Render2DUtil.m195(var3, var4, var6, var6, var7, this.m336(new Color(34, 34, 38), var5));
         String var8 = var2.getName().getString().isEmpty()
            ? "?"
            : var2.getName().getString().substring(0, 1).toUpperCase(Locale.ROOT);
         Render2DUtil.m206(
            var1,
            var3 + var6 / 2.0F,
            var4 + var6 * 0.31F,
            var8,
            var6 * 0.35F,
            this.m336(new Color(250, 250, 252), var5),
            "center"
         );
      }
   }

   private void m1079(Identifier var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, float var10) {
      GpuTextureView var11 = this.mc.getTextureManager().getTexture(var1).getGlTextureView();
      TextureShader.m344(Render2DUtil.m191(), var2, var3, var4, var11, this.m1008(255.0F * var10, 255, 255, 255), var5, 0.0F, var6, var7, var8, var9, false);
   }

   private void m1080(DrawContext var1, LivingEntity var2, float var3, float var4, float var5) {
      EquipmentSlot[] var6 = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
      float var7 = 13.0F;

      for (int var8 = 0; var8 < var6.length; var8++) {
         float var9 = var3 + (float)var8 * var7;
         ItemStack var10 = var2.getEquippedStack(var6[var8]);
         if (!Render2DUtil.m31()) {
            var1.getMatrices().pushMatrix();
            var1.getMatrices().translate(var9, var4);
            var1.getMatrices().scale(0.5F, 0.5F);
            var1.drawItem(var10, 0, 0);
            var1.drawStackOverlay(this.mc.textRenderer, var10, 0, 0);
            var1.getMatrices().popMatrix();
         }
      }
   }

   private void m1081(DrawContext var1, float var2, float var3) {
      List<ItemStack> var4 = new ArrayList<>();
      if (this.mc.player != null) {
         for (EquipmentSlot var8 : new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET}) {
            ItemStack var9 = this.mc.player.getEquippedStack(var8);
            if (!var9.isEmpty()) {
               var4.add(var9);
            }
         }

         ItemStack var15 = this.mc.player.getMainHandStack();
         if (!var15.isEmpty()) {
            var4.add(var15);
         }

         ItemStack var17 = this.mc.player.getOffHandStack();
         if (!var17.isEmpty()) {
            var4.add(var17);
         }
      }

      boolean var16 = this.m651();
      if (var4.isEmpty() && !var16) {
         this.f106 = 0.0F;
      } else {
         float var18 = 14.0F;
         float var19 = 13.0F;
         float var20 = 56.0F;
         int var21 = var4.isEmpty() ? 1 : var4.size();
         float var10 = var18 + (float)var21 * var19;
         this.f105 = var20;
         this.f106 = var10;
         this.m1112(Interface$4.f8, var2, var3, var20, var10, 6.0F, 1.0F);
         this.m1113(Interface$4.f8, var2, var3, var20, var18, 0.0F, 0.0F, 6.0F, 6.0F, 1.0F);
         Render2DUtil.m208(var1, FontRenderUtil.f3, var2 + 7.0F, var3 + 4.5F, "L", 8.0F, ThemeManager.m1379());
         Render2DUtil.m205(var1, var2 + 7.0F + 12.0F, this.m1141(var3, var18, 8.0F), "Armor", 8.0F, new Color(238, 238, 242));
         ScissorUtil.m357((double)var2, (double)var3, (double)var20, (double)var10);
         float var11 = var3 + var18;

         for (ItemStack var13 : var4) {
            if (!Render2DUtil.m31()) {
               var1.getMatrices().pushMatrix();
               var1.getMatrices().translate(var2 + 3.0F, var11 + (var19 - 8.0F) / 2.0F);
               var1.getMatrices().scale(0.5F, 0.5F);
               var1.drawItem(var13, 0, 0);
               var1.getMatrices().popMatrix();
            }

            if (var13.isDamageable() && var13.getMaxDamage() > 0) {
               int var14 = (int)((float)(var13.getMaxDamage() - var13.getDamage()) / (float)var13.getMaxDamage() * 100.0F);
               Render2DUtil.m206(
                  var1,
                  var2 + var20 - 7.0F,
                  this.m1141(var11, var19, 6.5F),
                  var14 + "%",
                  6.5F,
                  new Color(235, 235, 240),
                  "right"
               );
            }

            var11 += var19;
         }

         ScissorUtil.m29();
      }
   }

   private List<StatusEffectInstance> m1082() {
      ArrayList var1 = new ArrayList();
      if (this.mc.player == null) {
         return var1;
      } else {
         var1.addAll(this.mc.player.getStatusEffects());
         var1.sort(Comparator.comparing(this::m1086));
         return var1;
      }
   }

   private List<StatusEffectInstance> m1083() {
      List var1 = this.m1082();
      if (this.mc.player != null) {
         for (StatusEffectInstance var3 : this.mc.player.getStatusEffects()) {
            this.f142.putIfAbsent(this.m1085(var3), 0.0F);
         }
      }

      var1.sort(Comparator.comparing(this::m1086));
      return var1;
   }

   private boolean m1084(List<StatusEffectInstance> var1, String var2) {
      for (StatusEffectInstance var4 : var1) {
         if (this.m1085(var4).equals(var2)) {
            return true;
         }
      }

      return false;
   }

   private String m1085(StatusEffectInstance var1) {
      return ((StatusEffect)var1.getEffectType().value()).getTranslationKey();
   }

   private String m1086(StatusEffectInstance var1) {
      String var2 = ((StatusEffect)var1.getEffectType().value()).getName().getString();
      int var3 = var1.getAmplifier();
      if (var3 > 0) {
         var2 = var2 + " " + this.m288(var3 + 1);
      }

      return var2;
   }

   private String m1087(StatusEffectInstance var1) {
      if (var1.isInfinite()) {
         return "в€ћ";
      } else {
         int var2 = Math.max(0, var1.getDuration() / 20);
         int var3 = var2 / 60;
         int var4 = var2 % 60;
         return var3 + ":" + (var4 < 10 ? "0" : "") + var4;
      }
   }

   private String m288(int var1) {
      return switch (var1) {
         case 2 -> "II";
         case 3 -> "III";
         case 4 -> "IV";
         case 5 -> "V";
         default -> String.valueOf(var1);
      };
   }

   private List<Module> m1088() {
      ArrayList var1 = new ArrayList();

      for (Module var3 : ModuleManager.getModules()) {
         if (var3 != this && var3.isEnabled() && var3.getKey() > 0) {
            var1.add(var3);
         }
      }

      var1.sort(Comparator.comparing(Module::getName));
      return var1;
   }

   private List<Module> m795() {
      ArrayList var1 = new ArrayList();

      for (Module var3 : ModuleManager.getModules()) {
         if (var3 != this && var3.getKey() > 0 && (var3.isEnabled() || this.f141.getOrDefault(var3, 0.0F) > 0.02F)) {
            var1.add(var3);
         }
      }

      var1.sort(Comparator.comparing(Module::getName));
      return var1;
   }

   private int m1089() {
      if (this.mc.player != null && this.mc.getNetworkHandler() != null) {
         PlayerListEntry var1 = this.mc.getNetworkHandler().getPlayerListEntry(this.mc.player.getUuid());
         return var1 == null ? 0 : var1.getLatency();
      } else {
         return 0;
      }
   }

   public static boolean m687() {
      return f1 != null && f1.isEnabled() && f1.f69.m6();
   }

   public static boolean m1() {
      return f1 != null && f1.isEnabled() && f1.f67.m6();
   }

   public static boolean m585() {
      return f1 != null && f1.isEnabled();
   }

   private void m1090(DrawContext var1) {
      if (this.mc.player != null && this.mc.world != null) {
         float var2 = (float)Render2DUtil.m113();
         float var3 = (float)Render2DUtil.m189();
         float var4 = 190.0F;
         float var5 = (var2 - var4) / 2.0F;
         float var6 = var3 - 29.0F;
         int var7 = this.mc.player.getInventory().getSelectedSlot();
         this.m1095(var5 + 4.0F, var7);
         float var8 = 26.0F;
         this.m1112(Interface$4.f6, var5, var6, var4, var8, 6.0F, 1.0F);
         float var9 = var6 + 4.0F;
         float var10 = var5 + 4.0F;
         float var11 = var6 + 4.0F;

         for (int var12 = 0; var12 < 9; var12++) {
            float var13 = this.m151(this.f140[var12]);
            ItemStack var14 = this.mc.player.getInventory().getStack(var12);
            if (var14.isEmpty()) {
               this.m1096(var1, var10, var11, var12, var13);
            } else {
               float var15 = this.m623(0.92F, 1.3F, var13);
               float var16 = var10 + 9.0F;
               float var17 = var11 + 9.0F - var13 * 1.3F;
               if (!Render2DUtil.m31()) {
                  var1.getMatrices().pushMatrix();
                  var1.getMatrices().translate(var16, var17);
                  var1.getMatrices().scale(var15, var15);
                  var1.drawItem(var14, -8, -8);
                  var1.drawStackOverlay(this.mc.textRenderer, var14, -8, -8);
                  var1.getMatrices().popMatrix();
               }
            }

            var10 += 20.5F;
         }

         boolean var21 = this.m636();
         float var22 = var6 - 16.0F;
         if (var21) {
            float var23 = var6 - 3.5F - 2.0F;
            this.m1097(var1, var5, var23, var4);
            float var24 = (var4 - 26.0F) / 2.0F;
            float var25 = var5 + var4 - var24;
            float var26 = var23 - 9.0F - 3.0F;
            this.m1103(var1, var5, var26, var24);
            boolean var18 = this.m1101() > 0.05F;
            if (var18) {
               this.m1104(var1, var25, var26, var24);
            } else {
               this.m1105(var1, var25, var26, var24, true, 9.0F);
            }

            this.m1102(var1, var5 + var4 / 2.0F, var26);
            float var19 = var26;
            boolean var20 = this.m637();
            if (var20 || var18) {
               var19 = var26 - 9.0F - 3.0F;
               if (var20) {
                  this.m1106(var1, var5, var19, var24);
               }

               if (var18) {
                  this.m1105(var1, var25, var19, var24, true, 9.0F);
               }
            }

            var22 = var19 - 16.0F;
         }

         this.m1107(var1, var5 + var4 / 2.0F, var22);
      }
   }

   private float m1091() {
      if (this.mc.inGameHud != null && this.mc.inGameHud.getBossBarHud() != null) {
         Collection var1 = ((IBossBarHud)this.mc.inGameHud.getBossBarHud()).getBossBars().values();
         if (var1.isEmpty()) {
            return 0.0F;
         } else {
            int var2 = Math.min(var1.size(), 6);
            float var3 = 24.0F;
            return 8.0F + (float)var2 * var3;
         }
      } else {
         return 0.0F;
      }
   }

   private void m1092(DrawContext var1) {
      if (this.mc.inGameHud != null) {
         Collection<ClientBossBar> var2 = ((IBossBarHud)(Object)this.mc.inGameHud.getBossBarHud()).getBossBars().values();
         if (var2.isEmpty()) {
            this.f177.clear();
         } else {
            float var3 = (float)Render2DUtil.m113() / 2.0F;
            float var4 = var3 - 93.0F;
            float var5 = 8.0F;
            float var6 = 24.0F;
            int var7 = 0;

            for (ClientBossBar var9 : var2) {
               if (var7 >= 6) {
                  break;
               }

               this.m1093(var1, var9, var4, var5 + (float)var7 * var6);
               var7++;
            }

            this.f177.keySet().removeIf(var1x -> var2.stream().noneMatch(var1xx -> var1xx.getUuid().equals(var1x)));
         }
      }
   }

   private void m1093(DrawContext var1, ClientBossBar var2, float var3, float var4) {
      String var5 = this.m58(var2.getName().getString());
      float var6 = FontRenderUtil.m237(FontRenderUtil.f2, var5, 9.5F);
      float var7 = var3 + (186.0F - var6) / 2.0F;
      Render2DUtil.m208(var1, FontRenderUtil.f2, var7, var4, var5, 9.5F, new Color(240, 240, 245));
      float var8 = var4 + 9.5F + 3.0F;
      this.m1112(Interface$4.f7, var3, var8, 186.0F, 5.5F, 3.0F, 1.0F);
      float var9 = this.m335(var2.getPercent(), 0.0F, 1.0F);
      float var10 = this.f177.getOrDefault(var2.getUuid(), var9);
      var10 += (var9 - var10) * 0.18F;
      this.f177.put(var2.getUuid(), var10);
      Color var11 = this.m1094(var2);
      if (var10 > 0.001F) {
         float var12 = Math.max(5.5F, 186.0F * var10);
         Render2DUtil.m195(var3, var8, var12, 5.5F, 3.0F, var11);
         if (var12 > 6.0F) {
            Render2DUtil.m195(var3 + 2.5F, var8 + 1.1F, var12 - 5.0F, 0.9F, 0.45F, new Color(255, 255, 255, 42));
         }
      }
   }

   private String m58(String var1) {
      StringBuilder var2 = new StringBuilder(var1.length());

      for (int var3 = 0; var3 < var1.length(); var3++) {
         char var4 = var1.charAt(var3);
         if (var4 == 167 && var3 + 1 < var1.length()) {
            var3++;
         } else {
            var2.append(var4);
         }
      }

      return var2.toString();
   }

   // $VF: Unable to simplify switch on enum
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private Color m1094(ClientBossBar var1) {
      return switch (var1.getColor()) {
         case PINK -> new Color(244, 130, 190);
         case BLUE -> new Color(120, 170, 245);
         case RED -> new Color(244, 111, 118);
         case GREEN -> new Color(140, 205, 130);
         case YELLOW -> new Color(245, 210, 110);
         case PURPLE -> new Color(180, 140, 245);
         default -> ThemeManager.m1379();
      };
   }

   private boolean m636() {
      return this.mc.interactionManager == null || this.mc.interactionManager.getCurrentGameMode() != GameMode.CREATIVE;
   }

   private void m1095(float var1, int var2) {
      long var3 = System.currentTimeMillis();
      if (this.f126 == 0L) {
         this.f126 = var3;
      }

      float var5 = Math.min(50.0F, (float)(var3 - this.f126));
      this.f126 = var3;
      this.m348(var5);
      float var6 = var1 + (float)var2 * 20.5F;
      if (!this.f127) {
         this.f128 = var6;

         for (int var8 = 0; var8 < this.f140.length; var8++) {
            this.f140[var8] = var8 == var2 ? 1.0F : 0.0F;
         }

         this.f127 = true;
      } else {
         this.f128 = this.m1139(this.f128, var6, 0.024F, var5);

         for (int var7 = 0; var7 < this.f140.length; var7++) {
            this.f140[var7] = this.m1139(this.f140[var7], var7 == var2 ? 1.0F : 0.0F, 0.018F, var5);
         }
      }
   }

   private void m348(float var1) {
      if (this.mc.player != null) {
         float var2 = this.mc.player.getHealth();
         float var3 = this.mc.player.getMaxHealth();
         float var4 = this.mc.player.getAbsorptionAmount();
         float var5 = (float)this.mc.player.getHungerManager().getFoodLevel();
         float var6 = (float)this.mc.player.getArmor();
         float var7 = this.m335(this.mc.player.experienceProgress, 0.0F, 1.0F);
         float var8 = (float)this.mc.player.experienceLevel;
         if (!this.f132) {
            this.f133 = var2;
            this.f134 = var3;
            this.f135 = var4;
            this.f136 = var5;
            this.f137 = var6;
            this.f138 = var7;
            this.f139 = var8;
            this.f132 = true;
         } else {
            this.f133 = this.m1139(this.f133, var2, 0.014F, var1);
            this.f134 = this.m1139(this.f134, var3, 0.014F, var1);
            this.f135 = this.m1139(this.f135, var4, 0.014F, var1);
            this.f136 = this.m1139(this.f136, var5, 0.014F, var1);
            this.f137 = this.m1139(this.f137, var6, 0.014F, var1);
            this.f138 = this.m1139(this.f138, var7, 0.014F, var1);
            this.f139 = this.m1139(this.f139, var8, 0.014F, var1);
         }
      }
   }

   private void m1096(DrawContext var1, float var2, float var3, int var4, float var5) {
      float var6 = this.m335(var5, 0.0F, 1.0F);
      float var7 = this.m412(var6);
      float var8 = this.m623(1.0F, 1.8F, var7);
      float var9 = 7.2F * var8;
      String var10 = String.valueOf(var4 + 1);
      Render2DUtil.m206(
         var1,
         var2 + 9.0F,
         var3 + (18.0F - FontRenderUtil.m3(var9)) / 2.0F - 0.4F,
         var10,
         var9,
         new Color(225, 225, 230),
         "center"
      );
   }

   private void m1097(DrawContext var1, float var2, float var3, float var4) {
      if (this.mc.player != null) {
         float var5 = this.f132 ? this.f138 : this.m335(this.mc.player.experienceProgress, 0.0F, 1.0F);
         float var6 = 1.75F;
         Render2DUtil.m195(var2, var3, var4, 3.5F, var6, new Color(33, 39, 31, 210));
         if (var5 > 0.0F) {
            Render2DUtil.m195(var2, var3, Math.max(3.5F, var4 * var5), 3.5F, var6, new Color(153, 199, 116));
         }
      }
   }

   private boolean m637() {
      if (this.mc.player == null) {
         return false;
      } else {
         float var1 = this.f132 ? this.f137 : (float)this.mc.player.getArmor();
         return var1 > 0.05F || this.mc.player.getArmor() > 0;
      }
   }

   private void m1098(
      DrawContext var1,
      String var2,
      float var3,
      float var4,
      float var5,
      float var6,
      float var7,
      String var8,
      String var9,
      Color var10,
      Color var11,
      boolean var12
   ) {
      Render2DUtil.m195(var3, var4, var5, var6, 3.0F, var10);
      float var13 = this.m335(var7, 0.0F, 1.0F);
      if (var13 > 0.001F) {
         float var14 = Math.max(var6, var5 * var13);
         float var15 = var12 ? var3 + var5 - var14 : var3;
         Render2DUtil.m195(var15, var4, var14, var6, 3.0F, var11);
         this.m548(var15, var4, var14);
      }

      this.m1099(var1, var8, var3, var4, var5, var6, var12);
      this.m1100(var1, var2, var3, var4, var5, var6, var9);
   }

   private void m548(float var1, float var2, float var3) {
      if (!(var3 <= 6.0F)) {
         Render2DUtil.m195(var1 + 2.5F, var2 + 1.3F, var3 - 5.0F, 1.1F, 0.55F, new Color(255, 255, 255, 42));
      }
   }

   private void m1099(DrawContext var1, String var2, float var3, float var4, float var5, float var6, boolean var7) {
      if (var2 != null) {
         float var8 = 6.4F;
         float var9 = FontRenderUtil.m237(FontRenderUtil.f3, var2, var8);
         float var10 = var4 + (var6 - FontRenderUtil.m239(FontRenderUtil.f3, var8)) / 2.0F - 0.15F;
         float var11 = var7 ? var3 + var5 - var9 - 4.0F : var3 + 4.0F;
         Render2DUtil.m208(var1, FontRenderUtil.f3, var11, var10, var2, var8, new Color(255, 255, 255, 220));
      }
   }

   private void m1100(DrawContext var1, String var2, float var3, float var4, float var5, float var6, String var7) {
      this.m1108(
         var1,
         var2,
         var7,
         var3 + var5 / 2.0F,
         var4 + (var6 - FontRenderUtil.m3(7.1F)) / 2.0F + 0.6F,
         7.1F,
         new Color(255, 255, 255),
         "center"
      );
   }

   private float m1101() {
      return this.mc.player == null ? 0.0F : Math.max(0.0F, this.f132 ? this.f135 : this.mc.player.getAbsorptionAmount());
   }

   private void m1102(DrawContext var1, float var2, float var3) {
      if (this.mc.player != null) {
         int var4 = Math.round(this.f132 ? this.f139 : (float)this.mc.player.experienceLevel);
         if (var4 > 0) {
            this.m1108(
               var1,
               "hotbar.level",
               String.valueOf(var4),
               var2,
               var3 + (9.0F - FontRenderUtil.m3(8.1F)) / 2.0F + 0.6F,
               8.1F,
               new Color(153, 199, 116),
               "center"
            );
         }
      }
   }

   private void m1103(DrawContext var1, float var2, float var3, float var4) {
      if (this.mc.player != null) {
         float var5 = this.f132 ? this.f133 : this.mc.player.getHealth();
         float var6 = Math.max(1.0F, this.mc.player.getMaxHealth());
         this.m1098(
            var1,
            "hotbar.health",
            var2,
            var3,
            var4,
            9.0F,
            var5 / var6,
            "K",
            String.valueOf(Math.round(var5)),
            new Color(38, 34, 36, 210),
            new Color(244, 111, 118),
            false
         );
      }
   }

   private void m1104(DrawContext var1, float var2, float var3, float var4) {
      if (this.mc.player != null) {
         float var5 = this.m1101();
         float var6 = Math.max(1.0F, this.mc.player.getMaxHealth());
         this.m1098(
            var1,
            "hotbar.absorption",
            var2,
            var3,
            var4,
            9.0F,
            var5 / var6,
            "K",
            String.valueOf(Math.round(var5)),
            new Color(40, 36, 30, 210),
            new Color(245, 204, 96),
            true
         );
      }
   }

   private void m1105(DrawContext var1, float var2, float var3, float var4, boolean var5, float var6) {
      if (this.mc.player != null) {
         float var7 = this.f132 ? this.f136 : (float)this.mc.player.getHungerManager().getFoodLevel();
         this.m1098(
            var1,
            "hotbar.food",
            var2,
            var3,
            var4,
            var6,
            var7 / 20.0F,
            "L",
            String.valueOf(Math.round(var7)),
            new Color(39, 35, 31, 210),
            new Color(191, 143, 103),
            var5
         );
      }
   }

   private void m1106(DrawContext var1, float var2, float var3, float var4) {
      if (this.mc.player != null) {
         float var5 = this.f132 ? this.f137 : (float)this.mc.player.getArmor();
         this.m1098(
            var1,
            "hotbar.armor",
            var2,
            var3,
            var4,
            9.0F,
            var5 / 20.0F,
            "M",
            String.valueOf(Math.round(var5)),
            new Color(35, 36, 38, 210),
            new Color(184, 188, 193),
            false
         );
      }
   }

   private void m1107(DrawContext var1, float var2, float var3) {
      if (this.mc.player != null) {
         ItemStack var4 = this.mc.player.getInventory().getSelectedStack();
         String var5 = var4.isEmpty() ? "" : var4.getItem() + "|" + var4.getName().getString();
         if (!var5.equals(this.f130)) {
            this.f130 = var5;
            this.f129 = var4.isEmpty() ? "" : var4.getName().getString();
            this.f131 = this.f129.isEmpty() ? 0L : System.currentTimeMillis();
         }

         if (!this.f129.isEmpty() && this.f131 != 0L) {
            long var6 = System.currentTimeMillis() - this.f131;
            if (var6 <= 1200L) {
               float var8 = this.m335((float)var6 / 280.0F, 0.0F, 1.0F);
               float var9 = var6 <= 940L ? 1.0F : 1.0F - this.m335((float)(var6 - 940L) / 260.0F, 0.0F, 1.0F);
               float var10 = this.m411(var8);
               float var11 = this.m151(var9);
               float var12 = 7.6F * var10;
               float var13 = FontRenderUtil.m235(this.f129, var12) + 16.0F * var10;
               float var14 = 14.0F * var10;
               float var15 = var2 - var13 / 2.0F;
               float var16 = Math.max(2.0F, var3 - 2.0F * (1.0F - var8));
               if (this.m1115() != Interface$3.f1) {
                  this.m1114(Interface$4.f6, var15, var16, var13, var14, 5.0F * var10, var11);
               } else {
                  Render2DUtil.m195(var15, var16, var13, var14, 5.0F * var10, this.m336(new Color(25, 25, 27, 225), var11));
               }

               Render2DUtil.m206(
                  var1,
                  var2,
                  var16 + (var14 - FontRenderUtil.m3(var12)) / 2.0F - 0.6F,
                  this.f129,
                  var12,
                  this.m336(new Color(255, 255, 255), var11),
                  "center"
               );
            }
         }
      }
   }

   private void m1108(DrawContext var1, String var2, String var3, float var4, float var5, float var6, Color var7, String var8) {
      if (var3 != null && !var3.isEmpty()) {
         Interface$6 var9 = this.f143.computeIfAbsent(var2, var0 -> new Interface$6());
         String var10 = this.m1109(var9, var3);
         Render2DUtil.m206(var1, var4, var5, var10, var6, var7, var8);
      }
   }

   private String m1109(Interface$6 var1, String var2) {
      Interface$5 var3 = this.m1146(var2);
      long var4 = System.currentTimeMillis();
      if (var3 == null) {
         var1.f1 = false;
         var1.f11 = var4;
         return var2;
      } else if (var1.f1 && var3.m1062(var1)) {
         var1.f6 = var3.f4;
         var1.f8 = var3.f5;
         float var6 = var1.f11 == 0L ? 0.0F : Math.min(50.0F, (float)(var4 - var1.f11));
         var1.f11 = var4;
         var1.f5 = this.m1140(var1.f5, var1.f6, 0.018F, var6);
         var1.f7 = this.m1140(var1.f7, var1.f8, 0.018F, var6);
         return var1.f2
            ? var1.f3 + this.m1148(var1.f5, var1.f9) + "+" + this.m1148(var1.f7, var1.f10) + var1.f4
            : var1.f3 + this.m1148(var1.f5, var1.f9) + var1.f4;
      } else {
         var1.f1 = true;
         var1.f11 = var4;
         var1.f3 = var3.f1;
         var1.f4 = var3.f2;
         var1.f2 = var3.f3;
         var1.f9 = var3.f6;
         var1.f10 = var3.f7;
         var1.f5 = var3.f4;
         var1.f6 = var3.f4;
         var1.f7 = var3.f5;
         var1.f8 = var3.f5;
         return var2;
      }
   }

   private void m1110(String var1, float var2, float var3, float var4, float var5, float var6) {
      this.m1111(var1, var2, var3, var4, var5, var6, 1.0F);
   }

   private void m1111(String var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      if (!(var4 <= 0.5F) && !(var5 <= 0.5F) && !(var7 <= 0.01F)) {
         Render2DUtil.m198(var2, var3, var4, var5, var6, (float)this.f70.getValue(), var7, new Color(18, 18, 22, 120));
         this.m1119(var1, var2, var3, var4, var5, var6, var7);
      }
   }

   public void m1112(Interface$4 var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      if (this.m1115() != Interface$3.f1) {
         this.m1114(var1, var2, var3, var4, var5, var6, var7);
         this.m1119(var1 != null ? var1.name() : "NONE", var2, var3, var4, var5, var6, var7);
      } else if (!(var4 <= 0.5F) && !(var5 <= 0.5F) && !(var7 <= 0.01F)) {
         Render2DUtil.m198(var2, var3, var4, var5, var6, (float)this.f70.getValue(), var7, new Color(18, 18, 22, 120));
         this.m1119(var1 != null ? var1.name() : "NONE", var2, var3, var4, var5, var6, var7);
      }
   }

   private void m1113(Interface$4 var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, float var10) {
      if (this.m1115() != Interface$3.f1) {
         Render2DUtil.m194(
            var2, var3, var4, var5, var6, var7, var8, var9, this.m336(new Color(255, 255, 255, 28), var10), this.m336(new Color(255, 255, 255, 10), var10)
         );
      } else {
         Render2DUtil.m197(var2, var3, var4, var5, var6, var7, var8, var9, (float)this.f70.getValue(), var10, new Color(18, 18, 22, 140));
      }
   }

   private void m1114(Interface$4 var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      if (!(var4 <= 0.5F) && !(var5 <= 0.5F) && !(var7 <= 0.01F)) {
         Render2DUtil.m217(var2 + 0.5F, var3 + 1.2F, var4 - 1.0F, var5 - 0.4F, var6, 10.0F, 0.28F * var7, 2.4F, new Color(0, 0, 0, 170));
         this.m196(var2, var3, var4, var5, var6, 0.88F * var7);
         Render2DUtil.m202(
            var2 + 0.45F,
            var3 + 0.45F,
            var4 - 0.9F,
            var5 - 0.9F,
            var6,
            0.85F,
            this.m336(new Color(255, 255, 255, 82), var7),
            this.m336(new Color(255, 255, 255, 44), var7),
            this.m336(ThemeManager.m1379(), 0.22F * var7),
            this.m336(new Color(255, 255, 255, 24), var7)
         );
      }
   }

   private void m196(float var1, float var2, float var3, float var4, float var5, float var6) {
      Color var7 = this.m1143(ThemeManager.m1379(), (int)(72.0F * Math.clamp(var6, 0.0F, 1.0F)));
      if (this.m1115() == Interface$3.f3) {
         Render2DUtil.m200(var1, var2, var3, var4, var5, 1.0F, var6, var7);
      } else {
         Render2DUtil.m199(var1, var2, var3, var4, var5, 1.0F, var6, var7);
      }
   }

   private Interface$3 m1115() {
      if (this.f50.m17("Жидкое стекло")) {
         return Interface$3.f3;
      } else {
         return this.f50.m17("Лёд") ? Interface$3.f2 : this.m1116();
      }
   }

   private Interface$3 m1116() {
      if (!this.f53.m6() && !this.f55.m6() && !this.f57.m6() && !this.f59.m6() && !this.f61.m6()) {
         return !this.f52.m6()
               && !this.f51.m17("LiquidGlass Watermark")
               && !this.f54.m6()
               && !this.f56.m6()
               && !this.f58.m6()
               && !this.f60.m6()
            ? Interface$3.f1
            : Interface$3.f2;
      } else {
         return Interface$3.f3;
      }
   }

   private void m1117(Interface$3 var1) {
      this.f50.m16(this.m1118(var1));
      this.f52.m4(false);
      this.f53.m4(false);
      this.f54.m4(false);
      this.f55.m4(false);
      this.f56.m4(false);
      this.f57.m4(false);
      this.f58.m4(false);
      this.f59.m4(false);
      this.f60.m4(false);
      this.f61.m4(false);
      this.f51.m16("Default");
   }

   private String m1118(Interface$3 var1) {
      return switch (var1) {
         case f1 -> "Default";
         case f2 -> "Лёд";
         case f3 -> "Жидкое стекло";
      };
   }

   private void m1119(String var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      if (this.f71.m6() && !(var7 < 0.01F)) {
         long var8 = System.currentTimeMillis();
         float var10 = 2500.0F;
         int var11 = var1.hashCode();
         float var12 = (float)Math.abs(var11 % 1000) / 1000.0F;
         float var13 = (float)((var8 + (long)(var12 * var10)) % (long)var10) / var10;
         int var14 = Math.abs(var11) % 4;
         float var15 = 45.0F + (float)var14 * 90.0F;
         float var16 = (float)Math.toRadians((double)var15);
         SweepShader.m328(Render2DUtil.m191(), var2, var3, var4, var5, var6, var6, var6, var6, 0.0F, var13, var16, var7);
      }
   }

   private void m1120(boolean var1, boolean var2, boolean var3, List<Module> var4, List<StatusEffectInstance> var5) {
      long var6 = System.currentTimeMillis();
      if (this.f125 == 0L) {
         this.f125 = var6;
      } else {
         float var8 = Math.min(50.0F, (float)(var6 - this.f125));
         this.f125 = var6;
         this.f93 = this.m1139(this.f93, var1 ? 1.0F : 0.0F, 0.0075F, var8);
         this.f107 = this.m1139(this.f107, var2 ? 1.0F : 0.0F, 0.012F, var8);
         this.f102 = this.m1139(this.f102, var3 ? 1.0F : 0.0F, 0.0075F, var8);
         this.f117 = this.m1139(this.f117, this.f116 && this.m651() ? 1.0F : 0.0F, 0.018F, var8);
         this.f118 = this.m1139(this.f118, this.f65.m17("Круглый") ? 1.0F : 0.0F, 0.014F, var8);
         if (this.f107 <= 0.001F && !var2) {
            this.f124 = null;
         }

         for (Module var10 : var4) {
            this.f141.putIfAbsent(var10, 0.0F);
         }

         Iterator var17 = this.f141.entrySet().iterator();

         while (var17.hasNext()) {
            Entry var18 = (Entry)var17.next();
            Module var11 = (Module)var18.getKey();
            boolean var12 = var1 && var11.isEnabled() && var11.getKey() > 0;
            float var13 = this.m1139((Float)var18.getValue(), var12 ? 1.0F : 0.0F, 0.018F, var8);
            if (!var12 && var13 <= 0.001F) {
               var17.remove();
            } else {
               var18.setValue(var13);
            }
         }

         for (StatusEffectInstance var21 : var5) {
            this.f142.putIfAbsent(this.m1085(var21), 0.0F);
         }

         Iterator var20 = this.f142.entrySet().iterator();

         while (var20.hasNext()) {
            Entry var22 = (Entry)var20.next();
            boolean var24 = var3 && this.m1084(var5, (String)var22.getKey());
            float var26 = this.m1139((Float)var22.getValue(), var24 ? 1.0F : 0.0F, 0.018F, var8);
            if (!var24 && var26 <= 0.001F) {
               var20.remove();
            } else {
               var22.setValue(var26);
            }
         }

         List var23 = this.m795();
         float var25 = !var1 && var23.isEmpty() ? this.f91 : this.m1121(var23);
         float var27 = this.m1126(var1, var23);
         this.f91 = this.m1139(this.f91, var25, 0.018F, var8);
         this.f92 = this.m1139(this.f92, var27, 0.018F, var8);
         List var14 = this.m1083();
         float var15 = !var3 && var14.isEmpty() ? this.f100 : this.m1122(var14);
         float var16 = this.m1124(var3, var14);
         this.f100 = this.m1139(this.f100, var15, 0.018F, var8);
         this.f101 = this.m1139(this.f101, var16, 0.018F, var8);
      }
   }

   private float m1121(List<Module> var1) {
      float var2 = Math.max(96.0F, FontRenderUtil.m235("Keybinds", 8.0F) + 14.0F);

      for (Module var4 : var1) {
         String var5 = KeybindFormatter.m90(var4.getKey());
         var2 = Math.max(var2, 14.0F + FontRenderUtil.m235(var4.getName(), 8.0F) + 18.0F + FontRenderUtil.m235(var5, 8.0F));
      }

      return var2;
   }

   private float m1122(List<StatusEffectInstance> var1) {
      float var2 = Math.max(96.0F, FontRenderUtil.m235("Potions", 8.0F) + 14.0F);

      for (StatusEffectInstance var4 : var1) {
         var2 = Math.max(var2, 14.0F + FontRenderUtil.m235(this.m1086(var4), 8.0F) + 18.0F + FontRenderUtil.m235(this.m1087(var4), 8.0F));
      }

      return var2;
   }

   private float m1123() {
      float var1 = 0.0F;

      for (float var3 : this.f142.values()) {
         var1 += 17.0F * this.m151(var3);
      }

      return var1;
   }

   private float m1124(boolean var1, List<StatusEffectInstance> var2) {
      if (!var1 && var2.isEmpty()) {
         return 0.0F;
      } else {
         float var3 = 22.0F * this.m151(this.f102);
         float var4 = this.m335((this.f102 - 0.45F) / 0.55F, 0.0F, 1.0F);
         return var3 + this.m1123() * this.m151(var4);
      }
   }

   private float m1125() {
      float var1 = 0.0F;

      for (float var3 : this.f141.values()) {
         var1 += 17.0F * this.m151(var3);
      }

      return var1;
   }

   private float m1126(boolean var1, List<Module> var2) {
      if (!var1 && var2.isEmpty()) {
         return 0.0F;
      } else {
         float var3 = 22.0F * this.m151(this.f93);
         float var4 = this.m335((this.f93 - 0.45F) / 0.55F, 0.0F, 1.0F);
         return var3 + this.m1125() * this.m151(var4);
      }
   }

   private void m645() {
      if (this.m651() && this.mc.getWindow() != null) {
         long var1 = this.mc.getWindow().getHandle();
         boolean var3 = GLFW.glfwGetMouseButton(var1, 0) == 1;
         boolean var4 = GLFW.glfwGetMouseButton(var1, 1) == 1;
         double[] var5 = new double[1];
         double[] var6 = new double[1];
         GLFW.glfwGetCursorPos(var1, var5, var6);
         float var7 = (float)(var5[0] * (double)Render2DUtil.m113() / (double)this.mc.getWindow().getWidth());
         float var8 = (float)(var6[0] * (double)Render2DUtil.m189() / (double)this.mc.getWindow().getHeight());
         this.f122 = var7;
         this.f123 = var8;
         if (var4 && !this.f74) {
            this.m1127(var7, var8);
         }

         if (var3 && !this.f73) {
            if (this.m1128(var7, var8)) {
               this.f73 = var3;
               this.f74 = var4;
               return;
            }

            this.m1132(var7, var8);
         }

         if (!var3) {
            if (this.f72 != Interface$2.f1) {
               this.f72 = Interface$2.f1;

               try {
                  ConfigManager.m81();
               } catch (Throwable var10) {
               }
            }
         } else if (this.f72 != Interface$2.f1) {
            this.m1133(var7, var8);
         }

         this.f73 = var3;
         this.f74 = var4;
      } else {
         this.f72 = Interface$2.f1;
         this.f73 = false;
         this.f74 = false;
         this.f116 = false;
      }
   }

   private void m1127(float var1, float var2) {
      Interface$4 var3 = this.m1129(var1, var2);
      if (var3 != null) {
         this.f121 = var3;
         this.f116 = true;
         this.f119 = this.m335(var1, 2.0F, (float)Render2DUtil.m113() - 118.0F - 2.0F);
         this.f120 = this.m335(var2, 2.0F, (float)Render2DUtil.m189() - this.m1131() - 2.0F);
         this.f72 = Interface$2.f1;
      } else if (!this.m1138(var1, var2, this.f119, this.f120, 118.0F, this.m1131())) {
         this.f116 = false;
      }
   }

   private boolean m1128(float var1, float var2) {
      if (!this.f116) {
         return false;
      } else if (!this.m1138(var1, var2, this.f119, this.f120, 118.0F, this.m1131())) {
         this.f116 = false;
         return false;
      } else {
         float var3 = this.f120 + 17.0F;
         if (var2 >= var3 && var2 < var3 + 17.0F) {
            this.m1117(Interface$3.f1);
            this.f116 = false;
         } else if (var2 >= var3 + 17.0F && var2 < var3 + 34.0F) {
            this.m1117(Interface$3.f2);
            this.f116 = false;
         } else if (var2 >= var3 + 34.0F && var2 < var3 + 51.0F) {
            this.m1117(Interface$3.f3);
            this.f116 = false;
         }

         return true;
      }
   }

   private Interface$4 m1129(float var1, float var2) {
      if (this.f48.m6() && this.m1138(var1, var2, this.f77, this.f78, this.f79, 22.0F)) {
         return Interface$4.f1;
      } else if (this.f68.m6() && this.m1138(var1, var2, this.f108, this.f109, this.f110, this.f111)) {
         return Interface$4.f2;
      } else if (this.f62.m6() && this.f93 > 0.2F && this.m1138(var1, var2, this.f87, this.f88, this.f89, this.f90)) {
         return Interface$4.f3;
      } else if (this.f66.m6() && this.f102 > 0.2F && this.m1138(var1, var2, this.f96, this.f97, this.f98, this.f99)) {
         return Interface$4.f4;
      } else if (this.f63.m6() && this.f107 > 0.2F && this.m1138(var1, var2, this.f94, this.f95, this.m1135(), this.m1136())) {
         return Interface$4.f5;
      } else if (this.f67.m6() && this.m1130(var1, var2)) {
         return Interface$4.f6;
      } else {
         return this.f64.m6() && this.m1138(var1, var2, this.f103, this.f104, this.f105, this.f106) ? Interface$4.f8 : null;
      }
   }

   private boolean m1130(float var1, float var2) {
      float var3 = 190.0F;
      float var4 = ((float)Render2DUtil.m113() - var3) / 2.0F;
      float var5 = (float)Render2DUtil.m189() - 29.0F;
      float var6 = 26.0F;
      return this.m1138(var1, var2, var4, var5, var3, var6);
   }

   private float m1131() {
      return 68.0F;
   }

   private void m1132(float var1, float var2) {
      if (!this.f116) {
         if (this.f63.m6() && this.f107 > 0.2F && this.m1138(var1, var2, this.f94, this.f95, this.m1135(), this.m1136())) {
            this.f72 = Interface$2.f6;
            this.f75 = var1 - this.f94;
            this.f76 = var2 - this.f95;
         } else if (this.f62.m6() && this.f93 > 0.2F && this.m1138(var1, var2, this.f87, this.f88, this.f89, this.f90)) {
            this.f72 = Interface$2.f4;
            this.f75 = var1 - this.f87;
            this.f76 = var2 - this.f88;
         } else if (this.f66.m6() && this.f102 > 0.2F && this.m1138(var1, var2, this.f96, this.f97, this.f98, this.f99)) {
            this.f72 = Interface$2.f5;
            this.f75 = var1 - this.f96;
            this.f76 = var2 - this.f97;
         } else if (this.f48.m6() && this.m1138(var1, var2, this.f77, this.f78, this.f79, 22.0F)) {
            this.f72 = Interface$2.f2;
            this.f75 = var1 - this.f77;
            this.f76 = var2 - this.f78;
         } else if (this.f64.m6() && this.m1138(var1, var2, this.f103, this.f104, this.f105, this.f106)) {
            this.f72 = Interface$2.f7;
            this.f75 = var1 - this.f103;
            this.f76 = var2 - this.f104;
         } else {
            if (this.f68.m6() && this.m1138(var1, var2, this.f108, this.f109, this.f110, this.f111)) {
               this.f72 = Interface$2.f3;
               this.f75 = var1 - this.f108;
               this.f76 = var2 - this.f109;
            }
         }
      }
   }

   private void m1133(float var1, float var2) {
      if (this.f72 == Interface$2.f2) {
         this.f77 = this.m614(this.m335(var1 - this.f75, 2.0F, (float)Render2DUtil.m113() - this.f79 - 2.0F), this.f79);
         this.f78 = this.m423(this.m335(var2 - this.f76, 2.0F, (float)Render2DUtil.m189() - 22.0F - 2.0F), 22.0F);
      } else if (this.f72 == Interface$2.f3) {
         this.f108 = this.m614(this.m335(var1 - this.f75, 2.0F, (float)Render2DUtil.m113() - this.f110 - 2.0F), this.f110);
         this.f109 = this.m423(this.m335(var2 - this.f76, 2.0F, (float)Render2DUtil.m189() - this.f111 - 2.0F), this.f111);
      } else if (this.f72 == Interface$2.f4) {
         this.f87 = this.m614(this.m335(var1 - this.f75, 2.0F, (float)Render2DUtil.m113() - this.f89 - 2.0F), this.f89);
         this.f88 = this.m423(this.m335(var2 - this.f76, 2.0F, (float)Render2DUtil.m189() - this.f90 - 2.0F), this.f90);
      } else if (this.f72 == Interface$2.f5) {
         this.f96 = this.m614(this.m335(var1 - this.f75, 2.0F, (float)Render2DUtil.m113() - this.f98 - 2.0F), this.f98);
         this.f97 = this.m423(this.m335(var2 - this.f76, 2.0F, (float)Render2DUtil.m189() - this.f99 - 2.0F), this.f99);
      } else if (this.f72 == Interface$2.f6) {
         this.f94 = this.m614(this.m335(var1 - this.f75, 2.0F, (float)Render2DUtil.m113() - this.m1135() - 2.0F), this.m1135());
         this.f95 = this.m423(this.m335(var2 - this.f76, 2.0F, (float)Render2DUtil.m189() - this.m1136() - 2.0F), this.m1136());
      } else if (this.f72 == Interface$2.f7) {
         this.f103 = this.m614(this.m335(var1 - this.f75, 2.0F, (float)Render2DUtil.m113() - this.f105 - 2.0F), this.f105);
         this.f104 = this.m423(this.m335(var2 - this.f76, 2.0F, (float)Render2DUtil.m189() - this.f106 - 2.0F), this.f106);
      }
   }

   public void m376(Properties var1) {
      var1.setProperty("hud.watermark.x", String.valueOf(this.f77));
      var1.setProperty("hud.watermark.y", String.valueOf(this.f78));
      var1.setProperty("hud.keybinds.x", String.valueOf(this.f87));
      var1.setProperty("hud.keybinds.y", String.valueOf(this.f88));
      var1.setProperty("hud.potions.x", String.valueOf(this.f96));
      var1.setProperty("hud.potions.y", String.valueOf(this.f97));
      var1.setProperty("hud.target.x", String.valueOf(this.f94));
      var1.setProperty("hud.target.y", String.valueOf(this.f95));
      var1.setProperty("hud.scoreboard.x", String.valueOf(this.f108));
      var1.setProperty("hud.scoreboard.y", String.valueOf(this.f109));
      var1.setProperty("hud.armor.x", String.valueOf(this.f103));
      var1.setProperty("hud.armor.y", String.valueOf(this.f104));
   }

   public void m377(Properties var1) {
      this.f77 = m1134(var1, "hud.watermark.x", this.f77);
      this.f78 = m1134(var1, "hud.watermark.y", this.f78);
      this.f87 = m1134(var1, "hud.keybinds.x", this.f87);
      this.f88 = m1134(var1, "hud.keybinds.y", this.f88);
      this.f96 = m1134(var1, "hud.potions.x", this.f96);
      this.f97 = m1134(var1, "hud.potions.y", this.f97);
      this.f94 = m1134(var1, "hud.target.x", this.f94);
      this.f95 = m1134(var1, "hud.target.y", this.f95);
      this.f108 = m1134(var1, "hud.scoreboard.x", this.f108);
      this.f109 = m1134(var1, "hud.scoreboard.y", this.f109);
      this.f103 = m1134(var1, "hud.armor.x", this.f103);
      this.f104 = m1134(var1, "hud.armor.y", this.f104);
   }

   private static float m1134(Properties var0, String var1, float var2) {
      try {
         String var3 = var0.getProperty(var1);
         return var3 == null ? var2 : Float.parseFloat(var3);
      } catch (Exception var4) {
         return var2;
      }
   }

   private float m1135() {
      return 105.0F;
   }

   private float m1136() {
      return 34.0F;
   }

   private LivingEntity m1137() {
      if (this.mc.player != null && this.mc.world != null) {
         if (this.mc.crosshairTarget != null && this.mc.crosshairTarget.getType() == net.minecraft.util.hit.HitResult.Type.ENTITY) {
            net.minecraft.entity.Entity e = ((net.minecraft.util.hit.EntityHitResult) this.mc.crosshairTarget).getEntity();
            if (e instanceof LivingEntity var2 && var2 != this.mc.player && var2.isAlive()) {
               return var2;
            }
         }
         return null;
      } else {
         return null;
      }
   }

   private boolean m1138(float var1, float var2, float var3, float var4, float var5, float var6) {
      return var1 >= var3 && var1 <= var3 + var5 && var2 >= var4 && var2 <= var4 + var6;
   }

   private boolean m650() {
      return this.mc.currentScreen == null || this.m651();
   }

   private boolean m651() {
      return this.mc.currentScreen instanceof ChatScreen || this.mc.currentScreen instanceof ClickGuiScreen;
   }

   private float m335(float var1, float var2, float var3) {
      return Math.max(var2, Math.min(var3, var1));
   }

   private float m1139(float var1, float var2, float var3, float var4) {
      float var5 = 1.0F - (float)Math.exp((double)(-var3 * var4));
      float var6 = var1 + (var2 - var1) * this.m335(var5, 0.0F, 1.0F);
      return Math.abs(var6 - var2) < 0.001F ? var2 : var6;
   }

   private double m1140(double var1, double var3, float var5, float var6) {
      double var7 = 1.0 - Math.exp((double)(-var5 * var6));
      double var9 = var1 + (var3 - var1) * Math.max(0.0, Math.min(1.0, var7));
      return Math.abs(var9 - var3) < 0.001 ? var3 : var9;
   }

   private float m623(float var1, float var2, float var3) {
      return var1 + (var2 - var1) * this.m335(var3, 0.0F, 1.0F);
   }

   private float m151(float var1) {
      float var2 = this.m335(var1, 0.0F, 1.0F) - 1.0F;
      return var2 * var2 * var2 + 1.0F;
   }

   private float m411(float var1) {
      float var2 = this.m335(var1, 0.0F, 1.0F) - 1.0F;
      float var3 = 1.70158F;
      return 1.0F + (var3 + 1.0F) * var2 * var2 * var2 + var3 * var2 * var2;
   }

   private float m412(float var1) {
      float var2 = this.m335(var1, 0.0F, 1.0F);
      return var2 * var2 * (3.0F - 2.0F * var2);
   }

   private float m1141(float var1, float var2, float var3) {
      return var1 + (var2 - FontRenderUtil.m3(var3)) / 2.0F;
   }

   private float m1142(float var1, float var2) {
      return var1 + (var2 - FontRenderUtil.m239(FontRenderUtil.f3, 9.0F)) / 2.0F;
   }

   private Color m336(Color var1, float var2) {
      int var3 = (int)((float)var1.getAlpha() * this.m335(var2, 0.0F, 1.0F));
      return new Color(var1.getRed(), var1.getGreen(), var1.getBlue(), var3);
   }

   private Color m1143(Color var1, int var2) {
      int var3 = Math.max(0, Math.min(255, var2));
      return new Color(var1.getRed(), var1.getGreen(), var1.getBlue(), var3);
   }

   private Color m944(Color var1, Color var2, float var3) {
      float var4 = this.m335(var3, 0.0F, 1.0F);
      return new Color(
         Math.round((float)var1.getRed() + (float)(var2.getRed() - var1.getRed()) * var4),
         Math.round((float)var1.getGreen() + (float)(var2.getGreen() - var1.getGreen()) * var4),
         Math.round((float)var1.getBlue() + (float)(var2.getBlue() - var1.getBlue()) * var4),
         Math.round((float)var1.getAlpha() + (float)(var2.getAlpha() - var1.getAlpha()) * var4)
      );
   }

   private Color m1144(Color var1, float var2) {
      return new Color(
         Math.clamp((long)((int)((float)var1.getRed() + (float)(255 - var1.getRed()) * var2)), 0, 255),
         Math.clamp((long)((int)((float)var1.getGreen() + (float)(255 - var1.getGreen()) * var2)), 0, 255),
         Math.clamp((long)((int)((float)var1.getBlue() + (float)(255 - var1.getBlue()) * var2)), 0, 255),
         var1.getAlpha()
      );
   }

   private int m1008(float var1, int var2, int var3, int var4) {
      int var5 = Math.clamp((long)((int)var1), 0, 255);
      return var5 << 24 | var2 << 16 | var3 << 8 | var4;
   }

   private String m1145(float var1) {
      return var1 >= 10.0F ? String.valueOf(Math.round(var1)) : String.format(Locale.US, "%.1f", var1);
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

   private Interface$5 m1146(String var1) {
      if (var1 != null && !var1.isEmpty()) {
         Matcher var2 = Pattern.compile("^(-?\\d+(?:[\\.,]\\d+)?)\\+(-?\\d+(?:[\\.,]\\d+)?)$").matcher(var1);
         if (var2.matches()) {
            return new Interface$5("", "", true, this.m1147(var2.group(1)), this.m1147(var2.group(2)), this.m147(var2.group(1)), this.m147(var2.group(2)));
         } else {
            Matcher var3 = Pattern.compile("^(.*?)(-?\\d+(?:[\\.,]\\d+)?)(.*?)$").matcher(var1);
            if (var3.matches()) {
               String var4 = var3.group(1);
               String var5 = var3.group(2);
               String var6 = var3.group(3);
               if (!this.m759(var4) && !this.m759(var6)) {
                  return new Interface$5(var4, var6, false, this.m1147(var5), 0.0, this.m147(var5), 0);
               }
            }

            return null;
         }
      } else {
         return null;
      }
   }

   private double m1147(String var1) {
      return Double.parseDouble(var1.replace(',', '.'));
   }

   private int m147(String var1) {
      int var2 = var1.indexOf(46);
      int var3 = var1.indexOf(44);
      int var4 = Math.max(var2, var3);
      return var4 < 0 ? 0 : var1.length() - var4 - 1;
   }

   private boolean m759(String var1) {
      for (int var2 = 0; var2 < var1.length(); var2++) {
         if (Character.isDigit(var1.charAt(var2))) {
            return true;
         }
      }

      return false;
   }

   private String m1148(double var1, int var3) {
      return var3 > 0 ? String.format(Locale.US, "%." + var3 + "f", var1) : String.valueOf(Math.round(var1));
   }

   public static boolean m665() {
      return f1 != null && f1.isEnabled() && f1.f68.m6();
   }

   private void m1149(DrawContext var1, float var2, float var3) {
      if (this.mc.world != null && this.mc.player != null) {
         Scoreboard var4 = this.mc.world.getScoreboard();
         ScoreboardObjective var5 = var4.getObjectiveForSlot(ScoreboardDisplaySlot.SIDEBAR);
         if (var5 != null) {
            List<ScoreboardEntry> var6 = new ArrayList<>(var4.getScoreboardEntries(var5));
            var6.sort(Comparator.comparing(ScoreboardEntry::value).reversed());
            if (var6.size() > 15) {
               var6 = var6.subList(0, 15);
            }

            float var7 = 8.5F;
            float var8 = 7.5F;
            float var9 = 6.0F;
            float var10 = var7 + var9 * 2.0F;
            ColoredText var11 = ColoredText.m1031(var5.getDisplayName(), ThemeManager.m1379().getRGB());
            float var12 = FontMeasureUtil.m1170(FontRenderUtil.f2, var11, var7);
            ArrayList var13 = new ArrayList();

            for (ScoreboardEntry var15 : var6) {
               String var16 = var15.owner();
               Team var17 = var4.getScoreHolderTeam(var16);
               Object var18 = var15.display();
               if (var18 == null) {
                  var18 = Text.literal(var16);
               }

               MutableText var19 = Team.decorateName(var17, (Text)var18);
               ColoredText var20 = ColoredText.m1031(var19, new Color(245, 245, 245).getRGB());
               var13.add(var20);
               float var21 = FontMeasureUtil.m1170(MsdfFontManager.m281(), var20, var8);
               if (var21 > var12) {
                  var12 = var21;
               }
            }

            float var22 = Math.max(105.0F, var12 + var9 * 2.0F);
            if (this.f112 == -1.0F) {
               this.f112 = var22;
               this.f113 = var22;
               this.f114 = var22;
            }

            if (Math.abs(var22 - this.f112) > 0.1F) {
               this.f114 = this.f113;
               this.f112 = var22;
               this.f115 = System.currentTimeMillis();
            }

            float var23 = Math.min(1.0F, (float)(System.currentTimeMillis() - this.f115) / 400.0F);
            float var24 = this.m411(var23);
            this.f113 = this.f114 + (var22 - this.f114) * var24;
            this.f110 = this.f113;
            this.f111 = var10 + (float)var6.size() * (var8 + 3.0F) + var9;
            float var25 = var2 + (var22 - this.f113) / 2.0F;
            this.m1112(Interface$4.f2, var25, var3, this.f110, this.f111, 7.0F, 1.0F);
            this.m1113(Interface$4.f2, var25, var3, this.f110, var10, 0.0F, 0.0F, 6.0F, 6.0F, 1.0F);
            FontMeasureUtil.m1171(
               var1,
               FontRenderUtil.f2,
               var11,
               var25 + this.f110 / 2.0F - FontMeasureUtil.m1170(FontRenderUtil.f2, var11, var7) / 2.0F,
               this.m1141(var3, var10, var7) + 2.0F,
               var7
            );
            float var26 = var3 + var10 + var9;

            for (int var27 = 0; var27 < var6.size(); var27++) {
               ColoredText var28 = (ColoredText)var13.get(var27);
               FontMeasureUtil.m1171(var1, MsdfFontManager.m281(), var28, var25 + var9, var26, var8);
               var26 += var8 + 3.0F;
            }
         }
      }
   }
}

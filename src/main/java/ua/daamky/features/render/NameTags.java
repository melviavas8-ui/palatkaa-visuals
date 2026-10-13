package ua.daamky.features.render;

import com.mojang.blaze3d.textures.GpuTextureView;
import ua.daamky.events.RenderEvent;
import ua.daamky.features.combat.AntiBot;
import ua.daamky.features.misc.ScoreboardHealth;
import ua.daamky.settings.MultiChoiceSettingBase;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.ModuleManager;
import ua.daamky.system.api.NewFunction;
import ua.daamky.system.events.EventHandler;
import ua.daamky.utils.ProjectionUtil;
import ua.daamky.utils.player.RaytraceUtil;
import ua.daamky.utils.render.Render2DUtil;
import ua.daamky.utils.render.fonts.FontRenderUtil;
import ua.daamky.utils.render.shaders.TextureShader;
import java.awt.Color;
import java.util.Locale;
import java.util.Optional;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.util.DefaultSkinHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.SkinTextures;
import net.minecraft.item.AirBlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

@NewFunction(
   I0 = "NameTags",
   I00 = "Далбаебы тока не поймут",
   I000 = Category.RENDER
)
public class NameTags extends Module {
   public static NameTags f1;
   private final MultiChoiceSettingBase f2 = new MultiChoiceSettingBase(
      "Цели", "Игроки", "Предметы"
   );
   private static final float f3 = 7.2F;
   private static final float f4 = 11.5F;
   private static final float f5 = 3.5F;
   private static final float f6 = 2.5F;
   private static final float f7 = 3.5F;
   private static final float f8 = 10.8F;
   private static final float f9 = 1.1F;
   private static final float f10 = 2.1F;
   private static final float f11 = 0.72F;
   private static final float f12 = -0.0F;
   private static final float f13 = 0.7F;
   private static final float f14 = -0.85F;
   private static final float f15 = 0.75F;
   private static final float f16 = 1.6F;
   private static final Color f17 = new Color(238, 238, 242);
   private static final Color f18 = new Color(18, 18, 22, 120);
   private static final Color f19 = new Color(18, 18, 22, 120);
   private static final Color f20 = new Color(3, 4, 5, 65);
   private static final Color f21 = new Color(3, 177, 76, 92);
   private static final Color f22 = new Color(104, 255, 114);
   private static final Color f23 = new Color(255, 235, 92);
   private static final Color f24 = new Color(255, 170, 72);
   private static final Color f25 = new Color(255, 85, 92);
   private static final Color f26 = new Color(230, 230, 235);

   public NameTags() {
      f1 = this;
      this.addSettings(new Setting[]{this.f2});
   }

   public boolean m687() {
      return this.f2.m20("Игроки");
   }

   @EventHandler
   public void m330(RenderEvent var1) {
      DrawContext var2 = var1.m583();
      if (var2 != null && this.mc.player != null && this.mc.world != null) {
         if (!Render2DUtil.m31()) {
            if (!this.mc.options.hudHidden && !this.mc.getDebugHud().shouldShowDebugHud()) {
               float var3 = this.mc.getRenderTickCounter().getTickProgress(false);
               if (this.f2.m20("Игроки")) {
                  this.m1172(var2, var3);
               }

               if (this.f2.m20("Предметы")) {
                  this.m1190(var2, var3);
               }
            }
         }
      }
   }

   private void m1172(DrawContext var1, float var2) {
      int var3 = Render2DUtil.m113();
      int var4 = Render2DUtil.m189();
      AntiBot var5 = ModuleManager.getModule(AntiBot.class);
      Vec3d var6 = this.mc.gameRenderer.getCamera().getCameraPos();

      for (PlayerEntity var8 : this.mc.world.getPlayers()) {
         if ((var8 != this.mc.player || !this.mc.options.getPerspective().isFirstPerson())
            && var8.isAlive()
            && (var5 == null || !var5.isEnabled() || !var5.m595(var8))) {
            Vec3d var9 = ProjectionUtil.m225(var8, var2);
            float var10 = (float)var6.distanceTo(var9);
            if (!(var10 > 150.0F)) {
               Vec3d var11 = var9.add(0.0, (double)var8.getHeight() + 0.0, 0.0);
               float[] var12 = ProjectionUtil.m224(var11);
               if (var12 != null && !(var12[0] < -50.0F) && !(var12[0] > (float)(var3 + 50)) && !(var12[1] < -50.0F) && !(var12[1] > (float)(var4 + 50))) {
                  this.m1173(var1, var8, var12[0], var12[1], var10);
               }
            }
         }
      }
   }

   private void m1173(DrawContext var1, PlayerEntity var2, float var3, float var4, float var5) {
      float var6 = Math.max(1.0F, 9.0F / Math.max(var5, 3.0F));
      float var7 = 0.7F * var6;
      boolean var8 = RaytraceUtil.m80(var2.getName().getString());
      Text var9 = this.m1174(var2);
      Text var10 = this.m1175(var2);
      boolean var11 = this.m1177(var9);
      boolean var12 = this.m1177(var10);
      String var13 = this.m1176(var2, var11 || var12);
      float var14 = var2.getHealth() + var2.getAbsorptionAmount();
      float var15 = Math.max(1.0F, var2.getMaxHealth());
      ScoreboardHealth var16 = ModuleManager.getModule(ScoreboardHealth.class);
      float var17 = var14;
      if (var16 != null && var16.isEnabled() && var16.m2() > 0.0F) {
         var17 = var16.m2();
      }

      Object var18 = var11 ? this.m1178(var9) : Text.empty();
      Object var19 = var12 ? this.m1178(var10) : Text.empty();
      float var20 = 7.2F * var7;
      float var21 = 2.1F * var7;
      float var22 = 0.72F * var7;
      String var23 = this.m1183(var17);
      float var24 = FontRenderUtil.m237(FontRenderUtil.f2, var13, var20);
      float var25 = FontRenderUtil.m237(FontRenderUtil.f2, var23, var20);
      float var26 = var11 ? this.m1181((Text)var18, var22) : 0.0F;
      float var27 = var12 ? this.m1181((Text)var19, var22) : 0.0F;
      float var28 = var24 + var26 + var27;
      if (var11) {
         var28 += var21;
      }

      if (var12) {
         var28 += var21;
      }

      var28 += var21 + var25;
      float var29 = FontRenderUtil.m239(FontRenderUtil.f2, var20);
      float var30 = 9.0F * var22;
      float var31 = Math.max(var29, var30);
      float var32 = 11.5F * var7 + var21;
      float var33 = 3.5F * var7;
      float var34 = 2.5F * var7;
      float var35 = var32 + var28 + var33 * 2.0F;
      float var36 = Math.max(11.5F * var7, var31) + var34 * 2.0F;
      float var37 = var3 - var35 / 2.0F;
      float var38 = var4 - var36 - 10.0F * var7;
      if (Interface.f1 != null) {
         Interface.f1.m1112(null, var37, var38, var35, var36, 3.5F * var7, 1.0F);
      } else {
         Render2DUtil.m198(var37, var38, var35, var36, 3.5F * var7, 25.0F, 1.0F, f19);
      }

      if (var8) {
         Render2DUtil.m195(var37, var38, var35, var36, 3.5F * var7, f20, f21, f21, f20);
      }

      float var39 = var37 + var33;
      float var40 = var38 + (var36 - var29) / 2.0F - 0.45F * var7;
      float var41 = var38 + (var36 - var30) / 2.0F + -0.0F * var7;
      float var42 = 11.5F * var7;
      this.m1187(var2, var39, var38 + (var36 - var42) / 2.0F, var42);
      var39 += var42 + var21;
      float var43 = var39;
      if (var11) {
         this.m1182(var1, (Text)var18, var39 + -0.85F * var7, var41, var22);
         var43 = var39 + var26 + var21;
      }

      Render2DUtil.m208(var1, FontRenderUtil.f2, var43, var40, var13, var20, f17);
      var43 += var24;
      if (var12) {
         var43 += var21;
         this.m1182(var1, (Text)var19, var43, var41, var22);
         var43 += var27;
      }

      var43 += var21;
      Render2DUtil.m208(var1, FontRenderUtil.f2, var43 + 0.75F * var7, var40, var23, var20, this.m1184(var17, var15));
      this.m1189(var1, var2, var3, var38, var7);
   }

   private Text m1174(PlayerEntity var1) {
      if (var1.getScoreboardTeam() == null) {
         return Text.literal("");
      } else {
         Text var2 = var1.getScoreboardTeam().getPrefix();
         return (Text)(var2 == null ? Text.literal("") : var2);
      }
   }

   private Text m1175(PlayerEntity var1) {
      if (var1.getScoreboardTeam() == null) {
         return Text.literal("");
      } else {
         Text var2 = var1.getScoreboardTeam().getSuffix();
         return (Text)(var2 == null ? Text.literal("") : var2);
      }
   }

   private String m1176(PlayerEntity var1, boolean var2) {
      String var3 = var1.getGameProfile().name();
      if (var2) {
         return var3;
      } else {
         Text var4 = var1.getCustomName();
         if (var4 != null) {
            String var5 = var4.getString().trim();
            if (!var5.isEmpty() && !var5.equals(var3)) {
               return var5;
            }
         }

         String var6 = var1.getDisplayName().getString().trim();
         return !var6.isEmpty() && !var6.equals(var3) ? var6 : var3;
      }
   }

   private boolean m1177(Text var1) {
      return var1 != null && !var1.getString().trim().isEmpty();
   }

   private Text m1178(Text var1) {
      MutableText var2 = Text.empty();
      var1.visit((var2x, var3) -> {
         this.m1179(var2, var3, var2x);
         return Optional.empty();
      }, Style.EMPTY);
      return var2;
   }

   private String m58(String var1) {
      return var1.trim().replace("[", "").replace("]", "");
   }

   private void m1179(MutableText var1, String var2, Style var3) {
      String var4 = this.m58(var2);
      if (!var4.isEmpty()) {
         Style var5 = var3;
         StringBuilder var6 = new StringBuilder();

         for (int var7 = 0; var7 < var4.length(); var7++) {
            char var8 = var4.charAt(var7);
            if (var8 == 167 && var7 + 1 < var4.length()) {
               this.m1180(var1, var6, var5);
               Formatting var9 = Formatting.byCode(var4.charAt(var7 + 1));
               if (var9 != null) {
                  var5 = var9 == Formatting.RESET ? Style.EMPTY : var5.withFormatting(var9);
               }

               var7++;
            } else {
               var6.append(var8);
            }
         }

         this.m1180(var1, var6, var5);
      }
   }

   private void m1180(MutableText var1, StringBuilder var2, Style var3) {
      if (!var2.isEmpty()) {
         var1.append(Text.literal(var2.toString()).setStyle(var3));
         var2.setLength(0);
      }
   }

   private float m1181(Text var1, float var2) {
      return (float)this.mc.textRenderer.getWidth(var1) * var2;
   }

   private void m1182(DrawContext var1, Text var2, float var3, float var4, float var5) {
      var1.getMatrices().pushMatrix();
      var1.getMatrices().translate(var3, var4);
      var1.getMatrices().scale(var5, var5);
      var1.drawText(this.mc.textRenderer, var2, 0, 0, -1, false);
      var1.getMatrices().popMatrix();
   }

   private String m1183(float var1) {
      return String.format(Locale.US, "%.1f", var1);
   }

   private Color m1184(float var1, float var2) {
      float var3 = var1 / var2;
      if (var3 > 0.75F) {
         return f22;
      } else if (var3 > 0.5F) {
         return f23;
      } else {
         return var3 > 0.25F ? f24 : f25;
      }
   }

   private float m235(String var1, float var2) {
      return FontRenderUtil.m237(FontRenderUtil.f2, this.m59(var1), var2);
   }

   private String m59(String var1) {
      return var1.replaceAll("§.", "");
   }

   private float m1185(DrawContext var1, String var2, float var3, float var4, float var5) {
      float var6 = var3;
      StringBuilder var7 = new StringBuilder();
      Color var8 = f26;

      for (int var9 = 0; var9 < var2.length(); var9++) {
         if (var2.charAt(var9) == 167 && var9 + 1 < var2.length()) {
            if (!var7.isEmpty()) {
               Render2DUtil.m208(var1, FontRenderUtil.f2, var6, var4, var7.toString(), var5, var8);
               var6 += FontRenderUtil.m237(FontRenderUtil.f2, var7.toString(), var5);
               var7.setLength(0);
            }

            char var10 = var2.charAt(var9 + 1);
            var8 = this.m1186(var10);
            var9++;
         } else {
            var7.append(var2.charAt(var9));
         }
      }

      if (!var7.isEmpty()) {
         Render2DUtil.m208(var1, FontRenderUtil.f2, var6, var4, var7.toString(), var5, var8);
         var6 += FontRenderUtil.m237(FontRenderUtil.f2, var7.toString(), var5);
      }

      return var6;
   }

   private Color m1186(char var1) {
      return switch (var1) {
         case '0' -> new Color(0, 0, 0);
         case '1' -> new Color(0, 0, 170);
         case '2' -> new Color(0, 170, 0);
         case '3' -> new Color(0, 170, 170);
         case '4' -> new Color(170, 0, 0);
         case '5' -> new Color(170, 0, 170);
         case '6' -> new Color(255, 170, 0);
         case '7' -> new Color(170, 170, 170);
         case '8' -> new Color(85, 85, 85);
         case '9' -> new Color(85, 85, 255);
         default -> new Color(230, 230, 235);
         case 'a' -> new Color(85, 255, 85);
         case 'b' -> new Color(85, 255, 255);
         case 'c' -> new Color(255, 85, 85);
         case 'd' -> new Color(255, 85, 255);
         case 'e' -> new Color(255, 255, 85);
         case 'f' -> new Color(255, 255, 255);
         case 'r' -> new Color(230, 230, 235);
      };
   }

   private void m1187(PlayerEntity var1, float var2, float var3, float var4) {
      if (var1 instanceof AbstractClientPlayerEntity var5) {
         SkinTextures var6 = var5.getSkin();
         Identifier var7 = var6.body().texturePath();
         this.m1188(var7, var2, var3, var4);
      } else {
         Identifier var8 = DefaultSkinHelper.getSkinTextures(var1.getUuid()).body().texturePath();
         this.m1188(var8, var2, var3, var4);
      }
   }

   private void m1188(Identifier var1, float var2, float var3, float var4) {
      GpuTextureView var5 = this.mc.getTextureManager().getTexture(var1).getGlTextureView();
      float var6 = 2.5F;
      TextureShader.m344(Render2DUtil.m191(), var2, var3, var4, var5, -1, var6, 0.0F, 0.125F, 0.125F, 0.125F, 0.125F, false);
      TextureShader.m344(Render2DUtil.m191(), var2, var3, var4, var5, -1, var6, 0.0F, 0.625F, 0.125F, 0.125F, 0.125F, false);
   }

   private void m1189(DrawContext var1, PlayerEntity var2, float var3, float var4, float var5) {
      ItemStack[] var6 = new ItemStack[]{
         var2.getMainHandStack(),
         var2.getEquippedStack(EquipmentSlot.FEET),
         var2.getEquippedStack(EquipmentSlot.LEGS),
         var2.getEquippedStack(EquipmentSlot.CHEST),
         var2.getEquippedStack(EquipmentSlot.HEAD),
         var2.getOffHandStack()
      };
      int var7 = 0;

      for (ItemStack var11 : var6) {
         if (!this.m689(var11)) {
            var7++;
         }
      }

      if (var7 != 0) {
         float var20 = 10.8F * var5;
         float var21 = 1.1F * var5;
         float var22 = (float)var7 * var20 + (float)(var7 - 1) * var21;
         float var23 = var3 - var22 / 2.0F;
         float var12 = var4 - var20 - 1.6F * var5;
         int var13 = 0;

         for (ItemStack var17 : var6) {
            if (!this.m689(var17)) {
               float var18 = var23 + (float)var13 * (var20 + var21);
               var1.getMatrices().pushMatrix();
               var1.getMatrices().translate(var18, var12);
               float var19 = var20 / 16.0F;
               var1.getMatrices().scale(var19, var19);
               var1.drawItem(var17, 0, 0);
               var1.drawStackOverlay(this.mc.textRenderer, var17, 0, 0);
               var1.getMatrices().popMatrix();
               var13++;
            }
         }
      }
   }

   private boolean m689(ItemStack var1) {
      return var1.isEmpty() || var1.getItem() instanceof AirBlockItem;
   }

   private void m1190(DrawContext var1, float var2) {
      int var3 = Render2DUtil.m113();
      int var4 = Render2DUtil.m189();
      Vec3d var5 = this.mc.gameRenderer.getCamera().getCameraPos();

      for (Entity var7 : this.mc.world.getEntities()) {
         if (var7 instanceof ItemEntity) {
            ItemEntity var8 = (ItemEntity)var7;
            Vec3d var9 = ProjectionUtil.m225(var7, var2);
            float var10 = (float)var5.distanceTo(var9);
            if (!(var10 > 150.0F)) {
               Vec3d var11 = var9.add(0.0, 0.5, 0.0);
               float[] var12 = ProjectionUtil.m224(var11);
               if (var12 != null && !(var12[0] < -50.0F) && !(var12[0] > (float)(var3 + 50)) && !(var12[1] < -50.0F) && !(var12[1] > (float)(var4 + 50))) {
                  ItemStack var13 = var8.getStack();
                  String var14 = var8.getName().getString();
                  int var15 = var13.getCount();
                  if (var15 > 1) {
                     var14 = var14 + " §7x§c" + var15;
                  }

                  float var16 = Math.max(1.0F, 9.0F / Math.max(var10, 3.0F));
                  float var17 = 0.7F * var16;
                  float var18 = this.m235(var14, 7.2F * var17);
                  float var19 = 10.8F * var17;
                  float var20 = 1.1F * var17;
                  float var21 = 3.5F * var17;
                  float var22 = 2.5F * var17;
                  float var23 = var19 + var20 + var18;
                  float var24 = var23 + var21 * 2.0F;
                  float var25 = Math.max(var19, 7.2F * var17);
                  float var26 = var25 + var22 * 2.0F;
                  float var27 = var12[0] - var24 / 2.0F;
                  float var28 = var12[1] - var26;
                  if (Interface.f1 != null) {
                     Interface.f1.m1112(null, var27, var28, var24, var26, 3.5F * var17, 1.0F);
                  } else {
                     Render2DUtil.m198(var27, var28, var24, var26, 3.5F * var17, 25.0F, 1.0F, f19);
                  }

                  float var29 = var27 + var21;
                  float var30 = var28 + (var26 - var19) / 2.0F;
                  float var31 = var28 + (var26 - 7.2F * var17) / 2.0F - 0.5F * var17;
                  var1.getMatrices().pushMatrix();
                  var1.getMatrices().translate(var29, var30);
                  float var32 = var19 / 16.0F;
                  var1.getMatrices().scale(var32, var32);
                  var1.drawItem(var13, 0, 0);
                  var1.getMatrices().popMatrix();
                  this.m1185(var1, var14, var29 + var19 + var20, var31, 7.2F * var17);
               }
            }
         }
      }
   }
}

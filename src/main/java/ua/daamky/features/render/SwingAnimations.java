package ua.daamky.features.render;


import ua.daamky.settings.BooleanSetting;
import ua.daamky.settings.ModeSettingBase;
import ua.daamky.settings.NumberSetting;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;

import ua.daamky.system.api.NewFunction;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Arm;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

@NewFunction(
   I0 = "Swing Animations",
   I00 = "Кастомные анимации ударов от первого лица",
   I000 = Category.RENDER
)
public class SwingAnimations extends Module {
   private static final float f1 = 7.0F;
   public static SwingAnimations f2;
   private final ModeSettingBase f3 = new ModeSettingBase(
      "Мод",
      "Никакой",
      "Swipe",
      "Down",
      "Smooth",
      "Smooth 2",
      "Power",
      "Feast",
      "Twist",
      "Default",
      "Self",
      "Self 2",
      "Forward",
      "Touch",
      "BlockHit",
      "Pander",
      "Curt",
      "Slash",
      "Stab",
      "Spin",
      "Bounce",
      "Reset",
      "Мод 4"
   );
   private final NumberSetting f4 = new NumberSetting("Сила", 3.0, 0.0, 10.0, 1.0);
   private final NumberSetting f5 = new NumberSetting("Сила взмаха", 1.0, 0.5, 3.0, 0.1);
   public final NumberSetting f6 = new NumberSetting("Скорость", 7.0, 0.0, 10.0, 1.0);
   public final NumberSetting f7 = new NumberSetting("Угол", 90.0, 0.0, 360.0, 5.0);
   private final NumberSetting f8 = new NumberSetting("Наклон кончика", -20.0, -90.0, 90.0, 1.0);
   private final NumberSetting f9 = new NumberSetting(
      "Интенсивность взмаха", 5.0, 1.0, 10.0, 1.0
   );
   private final BooleanSetting f10 = new BooleanSetting("Всегда активно", true);
   private int f11;
   private boolean f12;

   public SwingAnimations() {
      f2 = this;
      this.addSettings(new Setting[]{this.f3, this.f4, this.f5, this.f6, this.f7, this.f8, this.f9, this.f10});
      this.f3.m5(this::m676);
      this.m676();
   }

   @Override
   public void onEnable() {
      this.m116();
   }

   @Override
   public void onDisable() {
      this.m116();
   }

   public boolean m20(String var1) {
      return this.f3.m17(var1);
   }

   public boolean m687() {
      return this.isEnabled() && !this.f3.m17("Никакой") && this.f10.m6();
   }

   public void m1265(MatrixStack var1, float var2, float var3, Arm var4, float var5, float var6, float var7) {
      var2 = MathHelper.clamp(var2, 0.0F, 1.0F);
      float var8 = (float)Math.sin((double)var2 * Math.PI);
      float var9 = MathHelper.sin((double)(var2 * var2 * (float) Math.PI));
      float var10 = MathHelper.sin((double)(MathHelper.sqrt(var2) * (float) Math.PI));
      float var11 = MathHelper.sin((double)(var2 * (float) Math.PI)) * 0.5F;
      float var12 = (float)this.f5.getValue();
      int var13 = var4 == Arm.RIGHT ? 1 : -1;
      String var14 = this.f3.m18();
      switch (var14) {
         case "Никакой":
            this.m1266(var1, var2, var3, var4);
            break;
         case "Twist":
            var1.translate((float)var13 * 0.56F, -0.36F, -0.72F);
            var1.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(80.0F * (float)var13));
            var1.multiply(RotationAxis.POSITIVE_X.rotationDegrees(var10 * -90.0F * var12));
            var1.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((var9 - var10) * 60.0F * (float)var13 * var12));
            var1.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-30.0F));
            var1.translate(0.0F, -0.1F, 0.05F);
            break;
         case "Swipe":
            var1.translate((float)var13 * 0.56F, -0.32F, -0.72F);
            var1.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(70.0F * (float)var13));
            var1.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-20.0F * (float)var13));
            var1.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(var10 * var9 * -5.0F * var12));
            var1.multiply(RotationAxis.POSITIVE_X.rotationDegrees(var10 * var9 * -120.0F * var12));
            var1.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-70.0F));
            break;
         case "Default":
            var1.translate((float)var13 * 0.56F, -0.52F - var10 * 0.5F * var12, -0.72F);
            var1.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(45.0F * (float)var13));
            var1.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-45.0F * (float)var13));
            break;
         case "Down":
            var1.translate((float)var13 * 0.56F, -0.32F, -0.72F);
            var1.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(76.0F * (float)var13));
            var1.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(var10 * -5.0F * var12));
            var1.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(var10 * -100.0F * var12));
            var1.multiply(RotationAxis.POSITIVE_X.rotationDegrees(var10 * -155.0F * var12));
            var1.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-100.0F));
            break;
         case "Smooth":
            var1.translate((float)var13 * 0.56F, -0.42F, -0.72F);
            var1.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float)var13 * (45.0F + var9 * -20.0F * var12)));
            var1.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((float)var13 * var10 * -20.0F * var12));
            var1.multiply(RotationAxis.POSITIVE_X.rotationDegrees(var10 * -80.0F * var12));
            var1.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float)var13 * -45.0F));
            var1.translate(0.0F, -0.1F, 0.0F);
            break;
         case "Smooth 2":
            var1.translate((float)var13 * 0.56F, -0.42F, -0.72F);
            var1.multiply(RotationAxis.POSITIVE_X.rotationDegrees(var10 * -80.0F * var12));
            var1.translate(0.0F, -0.1F, 0.0F);
            break;
         case "Power":
            var1.translate((float)var13 * 0.56F, -0.32F, -0.72F);
            var1.translate(-var11 * var11 * var9 * (float)var13 * var12, 0.0F, 0.0F);
            var1.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(61.0F * (float)var13));
            var1.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(var10 * var12));
            var1.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(var10 * var9 * -5.0F * var12));
            var1.multiply(RotationAxis.POSITIVE_X.rotationDegrees(var10 * var9 * -30.0F * var12));
            var1.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-60.0F));
            var1.multiply(RotationAxis.POSITIVE_X.rotationDegrees(var11 * -60.0F * var12));
            break;
         case "Feast":
            var1.translate((float)var13 * 0.56F, -0.32F, -0.72F);
            var1.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(30.0F * (float)var13));
            var1.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(var10 * 75.0F * (float)var13 * var12));
            var1.multiply(RotationAxis.POSITIVE_X.rotationDegrees(var10 * -45.0F * var12));
            var1.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(30.0F * (float)var13));
            var1.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-80.0F));
            var1.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(35.0F * (float)var13));
            break;
         case "Self 2":
            var1.translate((float)var13 * 0.56F, -0.52F, -0.72F);
            var1.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float)var13 * 90.0F));
            var1.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((float)var13 * -30.0F));
            var1.multiply(RotationAxis.POSITIVE_X.rotationDegrees((float)(-this.f7.getValue() - this.f4.getValue() * 10.0 * (double)var10)));
            break;
         case "Forward":
            var1.translate((float)var13 * 0.56F, -0.52F, -0.72F);
            var1.translate(0.0F, 0.0F, -0.3F * var10);
            var1.multiply(RotationAxis.POSITIVE_X.rotationDegrees(var10 * -35.0F));
            var1.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((float)var13 * var10 * 35.0F));
            break;
         case "Self":
            var1.translate((float)var13 * 0.56F, -0.52F, -0.72F);
            var1.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float)var13 * 90.0F));
            var1.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((float)var13 * -60.0F));
            var1.multiply(RotationAxis.POSITIVE_X.rotationDegrees((float)(-this.f7.getValue() - this.f4.getValue() * 10.0 * (double)var10)));
            break;
         case "Touch":
            var1.translate((float)var13 * 0.56F, -0.52F, -0.72F);
            var1.scale(1.0F, 1.0F, (float)(1.0 + (double)var8 * this.f4.getValue() / 4.0));
            var1.translate(0.0F, 0.0F, -0.265F);
            var1.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-100.0F));
            break;
         case "Curt":
            var1.translate((float)var13 * 0.56F, -0.52F, -0.72F);
            float var22 = MathHelper.sqrt(var2);
            float var23 = MathHelper.sin((double)(var22 * (float) Math.PI));
            float var18 = MathHelper.sin((double)(var2 * (float) Math.PI));
            var1.translate((float)var13 * (0.4F - var23 * 0.2F), -0.2F + var23 * 0.3F, -0.5F - var18 * 0.2F);
            var1.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float)var13 * 91.0F));
            var1.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((float)var13 * (-40.0F + var23 * -100.0F)));
            var1.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-60.0F));
            break;
         case "Pander":
            var1.translate((float)var13 * 0.56F, -0.52F, -0.72F);
            var1.scale(0.8F, 0.8F, 0.8F);
            float var21 = 1.0F - MathHelper.lerp(var5, var6, var7);
            var1.translate((float)var13 * (0.3F - var8 * 0.15F), 0.2F - var21 * 0.12F, -0.15F - var8 * 0.13F);
            var1.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float)var13 * (76.0F - 10.0F * var8)));
            var1.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((float)var13 * (-16.0F - 8.0F * var8)));
            var1.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-83.0F - 26.0F * var8));
            break;
         case "BlockHit":
            var1.translate((float)var13 * 0.56F, -0.52F, -0.72F);
            float var20 = MathHelper.sin((double)(var2 * var2 * (float) Math.PI));
            float var17 = MathHelper.sin((double)(MathHelper.sqrt(var2) * (float) Math.PI));
            var1.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float)var13 * 45.0F));
            var1.multiply(RotationAxis.POSITIVE_X.rotationDegrees(var20 * -20.0F));
            var1.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((float)var13 * var17 * -20.0F));
            var1.multiply(RotationAxis.POSITIVE_X.rotationDegrees(var17 * -80.0F));
            var1.translate((float)var13 * 0.4F, 0.2F, 0.2F);
            var1.translate((float)var13 * -0.5F, 0.08F, 0.0F);
            var1.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float)var13 * 20.0F));
            var1.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-80.0F));
            var1.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float)var13 * 20.0F));
            break;
         case "Slash":
            var1.translate((float)var13 * 0.56F, -0.42F, -0.72F);
            var1.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(55.0F * (float)var13));
            var1.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((float)var13 * (30.0F - var9 * 60.0F * var12)));
            var1.multiply(RotationAxis.POSITIVE_X.rotationDegrees(var10 * -110.0F * var12));
            var1.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-45.0F));
            var1.translate(0.0F, -0.08F, 0.0F);
            break;
         case "Stab":
            var1.translate((float)var13 * 0.56F, -0.52F, -0.72F);
            var1.translate(0.0F, var9 * 0.06F, -0.55F * var10 * var12);
            var1.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float)var13 * 12.0F));
            var1.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-15.0F + var9 * -18.0F * var12));
            break;
         case "Spin":
            var1.translate((float)var13 * 0.56F, -0.42F, -0.72F);
            var1.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(45.0F * (float)var13));
            var1.multiply(RotationAxis.POSITIVE_X.rotationDegrees(var10 * -55.0F * var12));
            var1.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((float)var13 * var8 * 360.0F * var12));
            var1.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float)var13 * -45.0F));
            var1.translate(0.0F, -0.1F, 0.0F);
            break;
         case "Bounce":
            var1.translate((float)var13 * 0.56F, -0.52F + var8 * 0.28F * var12, -0.72F);
            var1.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(45.0F * (float)var13));
            var1.multiply(RotationAxis.POSITIVE_X.rotationDegrees(var10 * -70.0F * var12));
            var1.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float)var13 * -45.0F));
            var1.translate(0.0F, -0.1F, 0.0F);
            break;
         case "Reset":
            var1.translate((float)var13 * 0.56F, -0.42F, -0.72F);
            var1.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(45.0F * (float)var13));
            var1.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((float)var13 * var9 * -35.0F * var12));
            var1.multiply(RotationAxis.POSITIVE_X.rotationDegrees(var9 * -95.0F * var12));
            var1.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float)var13 * -45.0F));
            var1.translate(0.0F, -0.08F, 0.0F);
            break;
         case "Мод 4":
            float var16 = (float)this.f9.getValue() * 10.0F;
            var1.translate((float)var13 * 0.72F, -0.5F, -1.0F);
            var1.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-((float)this.f8.getValue())));
            var1.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float)var13 * 90.0F));
            var1.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((float)var13 * -75.0F));
            var1.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-45.0F - var16 * var8));
            var1.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float)var13 * var16 * var8 * 0.5F));
      }
   }

   private void m1266(MatrixStack var1, float var2, float var3, Arm var4) {
      int var5 = var4 == Arm.RIGHT ? 1 : -1;
      var1.translate((float)var5 * 0.56F, -0.52F + var3 * -0.6F, -0.72F);
      float var6 = -0.4F * MathHelper.sin((double)(MathHelper.sqrt(var2) * (float) Math.PI));
      float var7 = 0.2F * MathHelper.sin((double)(MathHelper.sqrt(var2) * (float) (Math.PI * 2)));
      float var8 = -0.2F * MathHelper.sin((double)(var2 * (float) Math.PI));
      var1.translate((float)var5 * var6, var7, var8);
      float var9 = MathHelper.sin((double)(var2 * var2 * (float) Math.PI));
      var1.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float)var5 * (45.0F + var9 * -20.0F)));
      float var10 = MathHelper.sin((double)(MathHelper.sqrt(var2) * (float) Math.PI));
      var1.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((float)var5 * var10 * -20.0F));
      var1.multiply(RotationAxis.POSITIVE_X.rotationDegrees(var10 * -80.0F));
      var1.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float)var5 * -45.0F));
   }

   public int m1267(int var1, boolean var2, int var3) {
      if (!this.f12 || !var2 || var3 < 0) {
         float var4 = Math.max((float)this.f6.getValue(), 0.1F);
         this.f11 = Math.max(1, Math.round((float)var1 * 7.0F / var4));
         this.f12 = var2;
      }

      return this.f11;
   }

   private void m116() {
      this.f11 = 0;
      this.f12 = false;
   }

   private void m676() {
      boolean var1 = this.f3.m17("Self")
         || this.f3.m17("Self 2")
         || this.f3.m17("Touch");
      boolean var2 = this.f3.m17("Swipe")
         || this.f3.m17("Down")
         || this.f3.m17("Smooth")
         || this.f3.m17("Smooth 2")
         || this.f3.m17("Power")
         || this.f3.m17("Feast")
         || this.f3.m17("Twist")
         || this.f3.m17("Default")
         || this.f3.m17("Slash")
         || this.f3.m17("Stab")
         || this.f3.m17("Spin")
         || this.f3.m17("Bounce")
         || this.f3.m17("Reset");
      this.f4.setVisible(var1);
      this.f5.setVisible(var2);
      this.f7.setVisible(this.f3.m17("Self") || this.f3.m17("Self 2"));
      boolean var3 = this.f3.m17("Мод 4");
      this.f8.setVisible(var3);
      this.f9.setVisible(var3);
   }
}

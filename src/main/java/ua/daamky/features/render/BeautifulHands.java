package ua.daamky.features.render;

import ua.daamky.settings.BooleanSetting;
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
   I0 = "BeautifulHands",
   I00 = "Shows first-person hands while holding items",
   I000 = Category.RENDER
)
public class BeautifulHands extends Module {
   public static BeautifulHands f1;
   private final NumberSetting f2 = new NumberSetting("Скорость анимаций", 30.0, 1.0, 80.0, 1.0);
   private final NumberSetting f3 = new NumberSetting("Скорость взмаха", 6.0, 1.0, 20.0, 1.0);
   private final BooleanSetting f4 = new BooleanSetting("Анимация движения", true);
   private final BooleanSetting f5 = new BooleanSetting("Пустая рука", true);
   private final NumberSetting f6 = new NumberSetting("Правая X", 0.0, -2.0, 2.0, 0.05);
   private final NumberSetting f7 = new NumberSetting("Правая Y", 0.0, -2.0, 2.0, 0.05);
   private final NumberSetting f8 = new NumberSetting("Правая Z", 0.0, -2.0, 2.0, 0.05);
   private final NumberSetting f9 = new NumberSetting("Левая X", 0.0, -2.0, 2.0, 0.05);
   private final NumberSetting f10 = new NumberSetting("Левая Y", 0.0, -2.0, 2.0, 0.05);
   private final NumberSetting f11 = new NumberSetting("Левая Z", 0.0, -2.0, 2.0, 0.05);
   private final NumberSetting f12 = new NumberSetting("Позиция X", 0.7, -2.0, 2.0, 0.05);
   private final NumberSetting f13 = new NumberSetting("Позиция Y", 1.3, -2.0, 2.0, 0.05);
   private final NumberSetting f14 = new NumberSetting("Позиция Z", 0.2, -2.0, 2.0, 0.05);
   private final NumberSetting f15 = new NumberSetting("Вращение X (Меч)", -70.0, -180.0, 180.0, 5.0);
   private final NumberSetting f16 = new NumberSetting("Вращение Y (Меч)", 25.0, -180.0, 180.0, 5.0);
   private final NumberSetting f17 = new NumberSetting("Вращение Z (Меч)", -15.0, -180.0, 180.0, 5.0);
   private long f18 = System.nanoTime();
   private float f19;
   private float f20;
   private float f21;
   private float f22;
   private float f23;
   private float f24;
   private float f25;
   private float f26;
   private boolean f27;
   private boolean f28;

   public BeautifulHands() {
      f1 = this;
      this.addSettings(
         new Setting[]{
            this.f2,
            this.f3,
            this.f4,
            this.f5,
            this.f6,
            this.f7,
            this.f8,
            this.f9,
            this.f10,
            this.f11,
            this.f12,
            this.f13,
            this.f14,
            this.f15,
            this.f16,
            this.f17
         }
      );
   }

   public void m1011(MatrixStack var1, Arm var2) {
      if (this.isEnabled()) {
         if (var2 == Arm.RIGHT) {
            var1.translate(this.f6.getValue(), this.f7.getValue(), this.f8.getValue());
         } else {
            var1.translate(this.f9.getValue(), this.f10.getValue(), this.f11.getValue());
         }
      }
   }

   public float m3(float var1) {
      return 0.0F;
   }

   public double m724() {
      long var1 = System.nanoTime();
      double var3 = Math.min((double)(var1 - this.f18) / 1.0E9, 0.05);
      this.f18 = var1;
      return var3 * this.f2.getValue();
   }

   public boolean m1() {
      return this.isEnabled() && this.f5.m6();
   }

   public boolean m585() {
      return this.f4.m6();
   }

   public boolean m665() {
      return false;
   }

   public int m1009(int var1) {
      return this.isEnabled() ? Math.max(1, (int)Math.round(this.f3.getValue())) : var1;
   }

   public boolean m1012(boolean var1, float var2) {
      if (var1 && !this.f28 && var2 == 0.0F) {
         this.f27 = !this.f27;
      }

      this.f28 = var1;
      return this.f27;
   }

   public float m151(float var1) {
      float var2 = 1.70158F;
      float var3 = var2 * 1.525F;
      return var1 < 0.5F
         ? (float)(Math.pow((double)(2.0F * var1), 2.0) * (double)((var3 + 1.0F) * 2.0F * var1 - var3) / 2.0)
         : (float)((Math.pow((double)(2.0F * var1 - 2.0F), 2.0) * (double)((var3 + 1.0F) * (var1 * 2.0F - 2.0F) + var3) + 2.0) / 2.0);
   }

   public float m411(float var1) {
      return var1 < 0.6F
         ? MathHelper.sin((double)(MathHelper.clamp(var1, 0.0F, 0.12506F) * 12.56F))
         : MathHelper.sin((double)(MathHelper.clamp(var1, 0.62532F, 0.75038F) * 12.56F));
   }

   public void m1013(MatrixStack var1, Arm var2, double var3, double var5, float var7, float var8, float var9, double var10) {
      if (this.f4.m6()) {
         int var12 = var2 == Arm.RIGHT ? 1 : -1;
         this.f26 += (float)(var3 * 0.7 * var10);
         var1.translate(
            (double)((float)var12 * MathHelper.sin((double)this.f26)) * var3 * 0.05,
            (double)MathHelper.cos((double)(this.f26 * 2.0F)) * var3 * 0.045,
            (double)MathHelper.sin((double)(this.f26 * 0.5F)) * var3 * 0.025
         );
         this.f21 = (float)((double)this.f21 + (double)(var7 * 0.015F) * var10);
         this.f21 = (float)((double)this.f21 + (double)(var9 * 0.65F) * var10);
         this.f22 = (float)((double)this.f22 + (double)(var8 * 0.015F) * var10);
         this.f21 = (float)((double)this.f21 - (double)(this.f19 * 0.1F) * var10);
         this.f22 = (float)((double)this.f22 - (double)(this.f20 * 0.1F) * var10);
         this.f21 = this.f21 * (float)Math.pow(0.88F, var10);
         this.f22 = this.f22 * (float)Math.pow(0.88F, var10);
         this.f19 = (float)((double)this.f19 + (double)this.f21 * var10);
         this.f20 = (float)((double)this.f20 + (double)this.f22 * var10);
         this.f23 = this.f23 + (float)(((var2 == Arm.RIGHT ? -var3 : var3) * 15.0 - (double)this.f23) * 0.1 * var10);
         var1.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(this.f19 * 0.65F));
         var1.multiply(RotationAxis.POSITIVE_X.rotationDegrees(this.f20 * 0.65F + (float)var5 * -2.0F));
         var1.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(this.f23 * 0.25F));
      }
   }

   public void m1014(MatrixStack var1, float var2, double var3, double var5) {
      this.f24 = (float)((double)this.f24 + (double)(var2 * 0.03F) * var5);
      if (var3 > 0.09) {
         this.f24 += (float)(-0.05 * var3 * var5);
      }

      this.f24 = (float)((double)this.f24 - (double)(this.f25 * 0.18F) * var5);
      this.f24 = this.f24 * (float)Math.pow(0.82F, var5);
      this.f25 = (float)((double)this.f25 + (double)this.f24 * var5);
      this.f25 = MathHelper.clamp(this.f25, -0.18F, 0.18F);
      var1.scale(1.0F, 1.0F + this.f25 * -2.0F, 1.0F);
   }

   public float m564() {
      return (float)this.f12.getValue();
   }

   public float m565() {
      return (float)this.f13.getValue();
   }

   public float m566() {
      return (float)this.f14.getValue();
   }

   public float m1015() {
      return (float)this.f15.getValue();
   }

   public float m1016() {
      return (float)this.f16.getValue();
   }

   public float m1017() {
      return (float)this.f17.getValue();
   }
}

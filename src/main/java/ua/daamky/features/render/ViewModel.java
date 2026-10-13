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
import net.minecraft.util.math.RotationAxis;

@NewFunction(
   I0 = "ViewModel",
   I00 = "Кастомная позиция рук от первого лица",
   I000 = Category.RENDER
)
public class ViewModel extends Module {
   public static ViewModel f1;
   private final ModeSettingBase f2 = new ModeSettingBase(
      "Рука", "Правая", "Левая"
   );
   private final NumberSetting f3 = new NumberSetting("Правая X", 0.0, -2.0, 2.0, 0.05);
   private final NumberSetting f4 = new NumberSetting("Правая Y", 0.0, -2.0, 2.0, 0.05);
   private final NumberSetting f5 = new NumberSetting("Правая Z", 0.0, -2.0, 2.0, 0.05);
   private final NumberSetting f6 = new NumberSetting("Левая X", 0.0, -2.0, 2.0, 0.05);
   private final NumberSetting f7 = new NumberSetting("Левая Y", 0.0, -2.0, 2.0, 0.05);
   private final NumberSetting f8 = new NumberSetting("Левая Z", 0.0, -2.0, 2.0, 0.05);
   private final BooleanSetting f9 = new BooleanSetting("Правая вращение", false);
   private final ModeSettingBase f10 = new ModeSettingBase(
      "Правая ось вращения",
      "X",
      "Y",
      "Z"
   );
   private final NumberSetting f11 = new NumberSetting(
      "Правая скорость вращения", 120.0, -720.0, 720.0, 5.0
   );
   private final BooleanSetting f12 = new BooleanSetting("Левая вращение", false);
   private final ModeSettingBase f13 = new ModeSettingBase(
      "Левая ось вращения",
      "X",
      "Y",
      "Z"
   );
   private final NumberSetting f14 = new NumberSetting(
      "Левая скорость вращения", 120.0, -720.0, 720.0, 5.0
   );
   private final BooleanSetting f15 = new BooleanSetting("Статичные руки", false);
   private final BooleanSetting f16 = new BooleanSetting("Всегда активно", true);

   public ViewModel() {
      f1 = this;
      this.addSettings(
         new Setting[]{
            this.f2, this.f3, this.f4, this.f5, this.f6, this.f7, this.f8, this.f9, this.f10, this.f11, this.f12, this.f13, this.f14, this.f15, this.f16
         }
      );
      this.f2.m5(this::m676);
      this.f9.m5(this::m676);
      this.f12.m5(this::m676);
      this.m676();
   }

   private boolean m1() {
      return this.isEnabled() && this.f16.m6();
   }

   public void m1011(MatrixStack var1, Arm var2) {
      if (this.m1()) {
         if (var2 == Arm.RIGHT) {
            var1.translate(this.f3.getValue(), this.f4.getValue(), this.f5.getValue());
         } else {
            var1.translate(this.f6.getValue(), this.f7.getValue(), this.f8.getValue());
         }
      }
   }

   public void m1230(MatrixStack var1, Arm var2) {
      if (this.m1() && this.m1231(var2)) {
         float var3 = (float)((double)System.nanoTime() / 1.0E9 * this.m1233(var2) % 360.0);
         String var4 = this.m1232(var2);
         switch (var4) {
            case "Y":
               var1.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(var3));
               break;
            case "Z":
               var1.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(var3));
               break;
            default:
               var1.multiply(RotationAxis.POSITIVE_X.rotationDegrees(var2 == Arm.RIGHT ? -var3 : var3));
         }
      }
   }

   public boolean m687() {
      return this.m1() && this.f15.m6();
   }

   private boolean m1231(Arm var1) {
      return var1 == Arm.RIGHT ? this.f9.m6() : this.f12.m6();
   }

   private String m1232(Arm var1) {
      return var1 == Arm.RIGHT ? this.f10.m18() : this.f13.m18();
   }

   private double m1233(Arm var1) {
      return var1 == Arm.RIGHT ? this.f11.getValue() : this.f14.getValue();
   }

   private void m676() {
      boolean var1 = this.f2.m17("Правая");
      this.f3.setVisible(var1);
      this.f4.setVisible(var1);
      this.f5.setVisible(var1);
      this.f9.setVisible(var1);
      this.f10.setVisible(var1 && this.f9.m6());
      this.f11.setVisible(var1 && this.f9.m6());
      this.f6.setVisible(!var1);
      this.f7.setVisible(!var1);
      this.f8.setVisible(!var1);
      this.f12.setVisible(!var1);
      this.f13.setVisible(!var1 && this.f12.m6());
      this.f14.setVisible(!var1 && this.f12.m6());
   }
}

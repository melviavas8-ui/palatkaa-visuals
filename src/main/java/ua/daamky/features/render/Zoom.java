package ua.daamky.features.render;

import ua.daamky.settings.KeybindSetting;
import ua.daamky.settings.NumberSetting;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;
import org.lwjgl.glfw.GLFW;

@NewFunction(
   I0 = "Zoom",
   I00 = "Приближение по зажатой клавише",
   I000 = Category.RENDER
)
public class Zoom extends Module {
   private final KeybindSetting f1 = new KeybindSetting("Клавиша", 67);
   private final NumberSetting f2 = new NumberSetting("Зум FOV", 30.0, 1.0, 110.0, 1.0);
   private final NumberSetting f3 = new NumberSetting("Скорость", 4.0, 1.0, 15.0, 0.5);
   private float f4 = 0.0F;
   private long f5 = 0L;

   public Zoom() {
      this.addSettings(new Setting[]{this.f1, this.f2, this.f3});
   }

   private boolean m1() {
      int var1 = this.f1.getKey();
      if (var1 > 0 && this.mc.getWindow() != null) {
         long var2 = this.mc.getWindow().getHandle();
         return KeybindSetting.m10(var1) ? GLFW.glfwGetMouseButton(var2, KeybindSetting.m11(var1)) == 1 : GLFW.glfwGetKey(var2, var1) == 1;
      } else {
         return false;
      }
   }

   public float m2() {
      long var1 = System.nanoTime();
      if (this.f5 == 0L) {
         this.f5 = var1;
      }

      float var3 = Math.min((float)(var1 - this.f5) / 1.0E9F, 0.1F);
      this.f5 = var1;
      boolean var4 = this.isEnabled() && this.mc.currentScreen == null && this.m1();
      float var5 = (float)this.f3.getValue();
      this.f4 = var4 ? Math.min(1.0F, this.f4 + var3 * var5) : Math.max(0.0F, this.f4 - var3 * var5);
      if (this.f4 <= 0.0F) {
         return 1.0F;
      } else {
         float var6 = (float)(this.f2.getValue() / 70.0);
         float var7 = m3(Math.clamp(this.f4, 0.0F, 1.0F));
         return 1.0F + (var6 - 1.0F) * var7;
      }
   }

   private static float m3(float var0) {
      return var0 < 0.5F ? 4.0F * var0 * var0 * var0 : 1.0F - (float)Math.pow((double)(-2.0F * var0 + 2.0F), 3.0) / 2.0F;
   }

   @Override
   public void onEnable() {
      this.f5 = 0L;
   }

   @Override
   public void onDisable() {
      this.f4 = 0.0F;
      this.f5 = 0L;
   }
}

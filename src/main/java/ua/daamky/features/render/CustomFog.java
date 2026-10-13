package ua.daamky.features.render;

import ua.daamky.gui.theme.ThemeManager;
import ua.daamky.settings.NumberSetting;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;
import java.awt.Color;
import net.minecraft.block.enums.CameraSubmersionType;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.fog.FogData;
import org.joml.Vector4f;

@NewFunction(
   I0 = "CustomFog",
   I00 = "Кастомный атмосферный туман для мира",
   I000 = Category.RENDER
)
public class CustomFog extends Module {
   public static CustomFog f1;
   private final NumberSetting f2 = new NumberSetting("Дистанция старта", 8.0, 0.0, 256.0, 1.0);
   private final NumberSetting f3 = new NumberSetting("Дистанция конца", 64.0, 4.0, 512.0, 1.0);
   private final NumberSetting f4 = new NumberSetting("Дистанция неба", 96.0, 4.0, 512.0, 1.0);
   private final NumberSetting f5 = new NumberSetting("Дистанция облаков", 128.0, 4.0, 512.0, 1.0);
   private final NumberSetting f6 = new NumberSetting("Яркость", 0.45, 0.0, 1.0, 0.05);

   public CustomFog() {
      f1 = this;
      this.addSettings(new Setting[]{this.f2, this.f3, this.f4, this.f5, this.f6});
   }

   public static void m1029(FogData var0) {
      if (f1 != null && f1.isEnabled()) {
         float var1 = (float)f1.f2.getValue();
         float var2 = Math.max(var1 + 1.0F, (float)f1.f3.getValue());
         var0.environmentalStart = var1;
         var0.environmentalEnd = var2;
         var0.skyEnd = (float)f1.f4.getValue();
         var0.cloudEnd = (float)f1.f5.getValue();
      }
   }

   public static Vector4f m1030(Camera var0, Vector4f var1) {
      if (f1 != null && f1.isEnabled() && var0.getSubmersionType() == CameraSubmersionType.NONE) {
         Color var2 = ThemeManager.m1379();
         float var3 = (float)f1.f6.getValue();
         Vector4f var4 = new Vector4f((float)var2.getRed() / 255.0F, (float)var2.getGreen() / 255.0F, (float)var2.getBlue() / 255.0F, 1.0F);
         return new Vector4f(var1).lerp(var4, var3);
      } else {
         return var1;
      }
   }
}

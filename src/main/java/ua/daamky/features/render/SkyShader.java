package ua.daamky.features.render;

import ua.daamky.gui.theme.ThemeManager;
import ua.daamky.settings.BooleanSetting;
import ua.daamky.settings.ColorSetting;
import ua.daamky.settings.ModeSettingBase;
import ua.daamky.settings.NumberSetting;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.ModuleManager;
import ua.daamky.system.api.NewFunction;
import ua.daamky.utils.SkyShaderRenderer;
import java.awt.Color;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldTerrainRenderContext;
import org.joml.Quaternionf;
import org.joml.Vector3f;

@NewFunction(
   I0 = "SkyShader",
   I00 = "Анимированное шейдерное небо",
   I000 = Category.RENDER
)
public class SkyShader extends Module {
   private final ModeSettingBase f1 = new ModeSettingBase(
      "Shader",
      "Full",
      "WebShader",
      "Plasma",
      "ChamsFill",
      "BaseWarp",
      "Waves",
      "Test",
      "Cosmos",
      "Galaxy",
      "Закат"
   );
   private final BooleanSetting f2 = new BooleanSetting("Theme color", true);
   private final ColorSetting f3 = new ColorSetting("Color", new Color(138, 180, 248));
   private final NumberSetting f4 = new NumberSetting("Alpha", 1.0, 0.1, 1.0, 0.05);
   private final NumberSetting f5 = new NumberSetting("Speed", 1.0, 0.1, 3.0, 0.05);

   public SkyShader() {
      this.addSettings(new Setting[]{this.f1, this.f2, this.f3, this.f4, this.f5});
      this.f2.m5(this::m115);
      this.m115();
   }

   public static void m1268(WorldTerrainRenderContext var0) {
      SkyShader var1 = ModuleManager.getModule(SkyShader.class);
      if (var1 != null && var1.isEnabled()) {
         var1.m1269(var0);
      }
   }

   private void m1269(WorldTerrainRenderContext var1) {
      Quaternionf var2 = var1.worldState().cameraRenderState.orientation;
      Vector3f var3 = var2.transform(new Vector3f(1.0F, 0.0F, 0.0F));
      Vector3f var4 = var2.transform(new Vector3f(0.0F, 1.0F, 0.0F));
      Vector3f var5 = var2.transform(new Vector3f(0.0F, 0.0F, -1.0F));
      SkyShaderRenderer.m327(this.m1021(), (float)this.f4.getValue(), (float)this.f5.getValue(), this.m750(), var3, var4, var5);
   }

   private void m115() {
      this.f3.setVisible(!this.f2.m6());
   }

   private Color m1021() {
      Color var1 = this.f2.m6() ? ThemeManager.m1379() : this.f3.m7();
      return !this.f2.m6() ? var1 : new Color(m1009(var1.getRed()), m1009(var1.getGreen()), m1009(var1.getBlue()));
   }

   private static int m1009(int var0) {
      return Math.clamp((long)((int)((float)var0 + (float)(255 - var0) * 0.35F)), 0, 255);
   }

   private int m750() {
      if (this.f1.m17("Full")) {
         return 0;
      } else if (this.f1.m17("WebShader")) {
         return 2;
      } else if (this.f1.m17("Plasma")) {
         return 4;
      } else if (this.f1.m17("ChamsFill")) {
         return 6;
      } else if (this.f1.m17("BaseWarp")) {
         return 8;
      } else if (this.f1.m17("Waves")) {
         return 10;
      } else if (this.f1.m17("Test")) {
         return 12;
      } else if (this.f1.m17("Cosmos")) {
         return 14;
      } else if (this.f1.m17("Galaxy")) {
         return 16;
      } else {
         return this.f1.m17("Закат") ? 18 : 0;
      }
   }
}

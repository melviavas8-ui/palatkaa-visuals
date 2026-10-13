package ua.daamky.features.render;

import ua.daamky.gui.Daamky_chams_mask;
import ua.daamky.gui.theme.ThemeManager;
import ua.daamky.settings.BooleanSetting;
import ua.daamky.settings.ColorSetting;
import ua.daamky.settings.ModeSettingBase;
import ua.daamky.settings.MultiChoiceSetting;
import ua.daamky.settings.NumberSetting;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;
import java.awt.Color;

@NewFunction(
   I0 = "Hands",
   I00 = "Текстурированные чамсы для рук от первого лица",
   I000 = Category.RENDER
)
public class Hands extends Module {
   public static Hands f1;
   private static final String description = "Chams";
   private static final String f2 = "Обводка";
   private static final String f3 = "Обычная";
   private static final String f4 = "Glow";
   private final MultiChoiceSetting f5 = new MultiChoiceSetting(
      "Эффекты", "Chams", "Обводка"
   );
   private final BooleanSetting f6 = new BooleanSetting("Цвет темы", true);
   private final ColorSetting f7 = new ColorSetting("Цвет", new Color(255, 255, 255));
   private final NumberSetting f8 = new NumberSetting(
      "Прозрачность заливки", 0.65, 0.05, 1.0, 0.05
   );
   private final ModeSettingBase f9 = new ModeSettingBase(
      "Тип заливки",
      "Обычная",
      "Full",
      "WebShader",
      "Plasma",
      "ChamsFill",
      "Waves"
   );
   private final ModeSettingBase f10 = new ModeSettingBase(
      "Тип обводки",
      "Обычная",
      "Glow"
   );
   private final NumberSetting f11 = new NumberSetting("Ширина обводки", 2.0, 1.0, 8.0, 0.5);
   private final NumberSetting f12 = new NumberSetting("Сила свечения", 1.15, 0.2, 3.0, 0.05);
   private final BooleanSetting f13 = new BooleanSetting("Скрыть текстуру", false);

   public Hands() {
      f1 = this;
      this.addSettings(new Setting[]{this.f5, this.f6, this.f7, this.f8, this.f9, this.f10, this.f11, this.f12, this.f13});
      this.f5.m5(this::m134);
      this.f6.m5(this::m134);
      this.f10.m5(this::m134);
      this.m134();
   }

   public void m5(Runnable var1) {
      if (this.m1()) {
         Daamky_chams_mask.m294(var1, false);
      }
   }

   public void m115() {
      if (this.isEnabled() && Daamky_chams_mask.m101()) {
         Color var1 = this.m1051();
         int var2 = this.m1052();
         if (this.f5.m20("Chams")) {
            Daamky_chams_mask.m303(var1, (float)this.f8.getValue(), this.m751(), var2, (float)this.f11.getValue(), (float)this.f12.getValue());
         } else if (var2 != 0) {
            Daamky_chams_mask.m303(var1, 0.0F, -1, var2, (float)this.f11.getValue(), (float)this.f12.getValue());
         }
      }
   }

   public boolean m1() {
      return this.isEnabled() && (this.f5.m20("Chams") || this.f5.m20("Обводка"));
   }

   public boolean m585() {
      return this.m1() && this.f13.m6();
   }

   private void m134() {
      this.f7.setVisible(!this.f6.m6());
      this.f8.setVisible(this.f5.m20("Chams"));
      this.f9.setVisible(this.f5.m20("Chams"));
      this.f10.setVisible(this.f5.m20("Обводка"));
      this.f11.setVisible(this.f5.m20("Обводка"));
      this.f12.setVisible(this.f5.m20("Обводка") && this.f10.m17("Glow"));
      this.f13.setVisible(this.f5.m20("Chams") || this.f5.m20("Обводка"));
   }

   private Color m1051() {
      Color var1 = this.f6.m6() ? ThemeManager.m1379() : this.f7.m7();
      return !this.f6.m6() ? var1 : new Color(m1009(var1.getRed()), m1009(var1.getGreen()), m1009(var1.getBlue()));
   }

   private static int m1009(int var0) {
      return Math.clamp((long)((int)((float)var0 + (float)(255 - var0) * 0.45F)), 0, 255);
   }

   private int m751() {
      if (this.f9.m17("Обычная")) {
         return -1;
      } else if (this.f9.m17("Full")) {
         return 0;
      } else if (this.f9.m17("WebShader")) {
         return 2;
      } else if (this.f9.m17("Plasma")) {
         return 4;
      } else if (this.f9.m17("ChamsFill")) {
         return 6;
      } else {
         return this.f9.m17("Waves") ? 10 : -1;
      }
   }

   private int m1052() {
      if (!this.f5.m20("Обводка")) {
         return 0;
      } else {
         return this.f10.m17("Glow") ? 2 : 1;
      }
   }
}

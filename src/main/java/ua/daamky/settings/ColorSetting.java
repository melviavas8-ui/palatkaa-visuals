package ua.daamky.settings;

import java.awt.Color;

public class ColorSetting extends Setting {
   Color f1;

   public ColorSetting(String var1, Color var2) {
      super(var1);
      this.f1 = var2;
   }
   public Color m7() {
      return this.f1;
   }
   public void m8(Color var1) {
      this.f1 = var1;
   }
}

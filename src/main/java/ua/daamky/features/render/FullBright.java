package ua.daamky.features.render;

import ua.daamky.events.PostMotionEvent;
import ua.daamky.mixins.interfaces.ISimpleOption;
import ua.daamky.settings.ModeSettingBase;
import ua.daamky.settings.NumberSetting;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;
import ua.daamky.system.events.EventHandler;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;

@NewFunction(
   I0 = "FullBright",
   I00 = "Полная яркость мира с 3 режимами",
   I000 = Category.RENDER
)
public class FullBright extends Module {
   public static FullBright f1;
   private final ModeSettingBase f2 = new ModeSettingBase(
      "Режим",
      "Гамма",
      "Ночное видение",
      "Динамичный"
   );
   private final NumberSetting f3 = new NumberSetting("Скорость", 50.0, 5.0, 200.0, 5.0);
   private final NumberSetting f4 = new NumberSetting("Порог света", 4.0, 0.0, 15.0, 1.0);
   private double f5 = -1.0;
   private double f6;
   private boolean f7;

   public FullBright() {
      f1 = this;
      this.addSettings(new Setting[]{this.f2, this.f3, this.f4});
   }

   @Override
   public void onEnable() {
      this.m135();
      this.f6 = this.m606();
   }

   @Override
   public void onDisable() {
      this.m677();
      this.m607();
      this.f7 = false;
   }

   @EventHandler
   public void m67(PostMotionEvent var1) {
      if (!this.util.m81()) {
         String var2 = this.f2.m18();
         switch (var2) {
            case "Гамма":
               this.m115();
               break;
            case "Ночное видение":
               this.m116();
               break;
            case "Динамичный":
               this.m676();
         }
      }
   }

   private void m115() {
      this.m607();
      this.f7 = false;
      this.m1050(1000.0);
   }

   private void m116() {
      this.m1050(this.f5 != -1.0 ? this.f5 : 1.0);
      StatusEffectInstance var1 = this.mc.player.getStatusEffect(StatusEffects.NIGHT_VISION);
      if (var1 == null || var1.getDuration() < 400) {
         this.mc.player.setStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, Integer.MAX_VALUE, 0, false, false, false), null);
         this.f7 = true;
      }
   }

   private void m676() {
      this.m607();
      this.f7 = false;
      boolean var1 = this.m665();
      double var2 = var1 ? 1000.0 : (this.f5 != -1.0 ? this.f5 : 1.0);
      double var4 = this.f3.getValue() / 1000.0;
      if (this.f6 < var2) {
         this.f6 = Math.min(this.f6 + var4 * (var2 - this.f6 + 1.0), var2);
      } else if (this.f6 > var2) {
         this.f6 = Math.max(this.f6 - var4 * (this.f6 - var2 + 1.0), var2);
      }

      this.m1050(this.f6);
   }

   private boolean m665() {
      if (this.mc.world != null && this.mc.player != null) {
         BlockPos var1 = this.mc.player.getBlockPos();
         int var2 = this.mc.world.getLightLevel(LightType.BLOCK, var1);
         int var3 = this.mc.world.getLightLevel(LightType.SKY, var1);
         int var4 = (int)this.f4.getValue();
         return var2 <= var4 && var3 <= var4;
      } else {
         return false;
      }
   }

   private void m135() {
      this.f5 = this.m606();
   }

   private void m677() {
      if (this.f5 != -1.0) {
         this.m1050(this.f5);
         this.f5 = -1.0;
      }
   }

   private void m1050(double var1) {
      if (this.mc.options != null) {
         ((ISimpleOption)(Object)this.mc.options.getGamma()).daamky$setValue(var1);
      }
   }

   private double m606() {
      return this.mc.options == null ? 1.0 : (Double)this.mc.options.getGamma().getValue();
   }

   private void m607() {
      if (this.f7 && this.mc.player != null) {
         this.mc.player.removeStatusEffect(StatusEffects.NIGHT_VISION);
      }
   }
}

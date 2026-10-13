package ua.daamky.features.movement;

import ua.daamky.events.PostMotionEvent;
import ua.daamky.settings.BooleanSetting;
import ua.daamky.settings.ModeSettingBase;
import ua.daamky.settings.NumberSetting;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;
import ua.daamky.system.events.EventHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;

@NewFunction(
   I0 = "SuperFireWork",
   I00 = "Даёт больше буста от фейерверк",
   I000 = Category.MOVEMENT
)
public class SuperFireWork extends Module {
   public ModeSettingBase f1 = new ModeSettingBase(
      "Мод",
      "BravoHvH",
      "ReallyWorld",
      "PulseHVH",
      "Custom"
   );
   public final NumberSetting f2 = new NumberSetting("Скорость", 1.7, 1.5, 8.0, 0.01);
   public final BooleanSetting f3 = new BooleanSetting(
      "Ускорение если рядом игрок", false
   );
   public float f4 = 1.5F;
   public float f5 = 1.5F;
   public float f6 = 5.0F;
   public float f7 = 5.0F;
   public float f8 = 5.0F;
   public float f9 = 5.0F;
   public float f10 = 5.0F;
   public float f11 = 5.0F;
   public float f12 = 5.0F;
   public float f13 = 5.0F;
   public float f14 = 5.0F;
   public float f15 = 5.0F;
   public float f16 = 1.5F;
   public float f17 = 1.5F;
   public float f18 = 1.5F;
   public float f19 = 1.5F;
   public float f20 = 1.5F;
   public float f21 = 1.5F;
   public float f22 = 1.5F;
   public float f23 = 1.5F;
   public float f24 = 1.5F;
   public float f25 = 1.5F;
   public float f26 = 1.5F;
   public float f27 = 1.5F;
   public float f28 = 1.5F;
   private int f29 = 0;

   public SuperFireWork() {
      this.addSettings(new Setting[]{this.f1, this.f2, this.f3});
      this.f1.m5(() -> this.f2.setVisible(this.f1.m17("Custom")));
   }

   @EventHandler
   public void m67(PostMotionEvent var1) {
      this.f4 = 1.61F;
      this.f5 = 1.61F;
      this.f6 = 4.0F;
      this.f7 = 8.0F;
      this.f8 = 12.0F;
      this.f9 = 16.0F;
      this.f10 = 20.0F;
      this.f11 = 24.0F;
      this.f12 = 28.0F;
      this.f13 = 32.0F;
      this.f14 = 36.0F;
      this.f15 = 40.0F;
      this.f25 = 2.5F;
      this.f26 = 2.5F;
      this.f16 = 2.2F;
      this.f17 = 2.06F;
      this.f18 = 1.98F;
      this.f19 = 1.87F;
      this.f20 = 1.8F;
      this.f21 = 1.74F;
      this.f22 = 1.7F;
      this.f23 = 1.65F;
      this.f24 = 1.63F;
      this.f27 = 1.66F;
      this.f28 = 1.66F;
      if (this.util.m81()) {
         this.f29 = 0;
      } else {
         if (this.mc.player.isGliding()) {
            this.f29++;
            double var2 = this.f2.getValue();
            double var4 = this.f2.getValue();
            String var6 = this.f1.m18();
            switch (var6) {
               case "BravoHvH":
                  if ((float)this.f29 <= this.f6) {
                     var2 = (double)this.f16;
                     var4 = (double)this.f16;
                  } else if ((float)this.f29 <= this.f7) {
                     var2 = (double)this.f17;
                     var4 = (double)this.f17;
                  } else if ((float)this.f29 <= this.f8) {
                     var2 = (double)this.f18;
                     var4 = (double)this.f18;
                  } else if ((float)this.f29 <= this.f9) {
                     var2 = (double)this.f19;
                     var4 = (double)this.f19;
                  } else if ((float)this.f29 <= this.f10) {
                     var2 = (double)this.f20;
                     var4 = (double)this.f20;
                  } else if ((float)this.f29 <= this.f11) {
                     var2 = (double)this.f21;
                     var4 = (double)this.f21;
                  } else if ((float)this.f29 <= this.f12) {
                     var2 = (double)this.f22;
                     var4 = (double)this.f22;
                  } else if ((float)this.f29 <= this.f13) {
                     var2 = (double)this.f23;
                     var4 = (double)this.f23;
                  } else if ((float)this.f29 <= this.f14) {
                     var2 = (double)this.f24;
                     var4 = (double)this.f24;
                  } else if ((float)this.f29 <= this.f15) {
                     var2 = (double)this.f4;
                     var4 = (double)this.f5;
                  } else {
                     this.f29 = 0;
                     var2 = (double)this.f4;
                     var4 = (double)this.f5;
                  }
                  break;
               case "ReallyWorld":
                  var2 = (double)this.f27;
                  var4 = (double)this.f28;
                  break;
               case "PulseHVH":
                  var2 = (double)this.f25;
                  var4 = (double)this.f26;
                  break;
               case "Custom":
               default:
                  var2 = this.f2.getValue();
                  var4 = this.f2.getValue();
            }

            if (this.f3.m6()) {
               boolean var16 = false;

               for (PlayerEntity var8 : this.mc.world.getPlayers()) {
                  if (var8 != this.mc.player && this.mc.player.distanceTo(var8) < 15.0F) {
                     var16 = true;
                     break;
                  }
               }

               if (var16) {
                  var2 += 0.2;
                  var4 += 0.2;
               }
            }

            Vec3d var17 = this.mc.player.getRotationVector();
            Vec3d var19 = this.mc.player.getVelocity();
            double var20 = var17.x * 0.1 + (var17.x * var2 - var19.x) * 0.5;
            double var10 = var17.y * 0.1 + (var17.y * var4 - var19.y) * 0.5;
            double var12 = var17.z * 0.1 + (var17.z * var2 - var19.z) * 0.5;
            this.mc.player.addVelocity(var20, var10, var12);
         } else {
            this.f29 = 0;
         }
      }
   }
}

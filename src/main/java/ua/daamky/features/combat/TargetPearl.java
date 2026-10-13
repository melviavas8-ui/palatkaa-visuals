package ua.daamky.features.combat;

import ua.daamky.events.PostMotionEvent;
import ua.daamky.settings.BooleanSetting;
import ua.daamky.settings.NumberSetting;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;
import ua.daamky.system.events.EventHandler;
import ua.daamky.utils.player.RotationUtil;
import ua.daamky.utils.player.RotationMode;
import ua.daamky.utils.player.RotationVec;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;

@NewFunction(
   I0 = "TargetPearl",
   I00 = "Кидает перл в точку падения чужого перла",
   I000 = Category.COMBAT
)
public class TargetPearl extends Module {
   private static final double f1 = 1.5;
   private static final double f2 = 0.03;
   private static final double f3 = 0.99;
   private static final int key = 200;
   private static final double f4 = 1.5;
   private final NumberSetting f5 = new NumberSetting("Радиус поиска", 50.0, 10.0, 80.0, 1.0);
   private final NumberSetting f6 = new NumberSetting("Мин. дистанция", 4.0, 2.0, 15.0, 0.5);
   private final NumberSetting f7 = new NumberSetting("Макс. дистанция", 45.0, 10.0, 80.0, 1.0);
   private final NumberSetting f8 = new NumberSetting("Скорость наводки", 180.0, 40.0, 180.0, 5.0);
   private final NumberSetting f9 = new NumberSetting("Макс. угол", 5.0, 0.5, 20.0, 0.5);
   private final NumberSetting f10 = new NumberSetting("Задержка (мс)", 50.0, 0.0, 1000.0, 25.0);
   private final BooleanSetting f11 = new BooleanSetting("Свои перлы", false);
   private final BooleanSetting f12 = new BooleanSetting("Только с перлом в руке", false);
   private long f13;

   public TargetPearl() {
      this.addSettings(new Setting[]{this.f5, this.f6, this.f7, this.f8, this.f9, this.f10, this.f11, this.f12});
   }

   @Override
   public void onDisable() {
      this.f13 = 0L;
      RotationUtil.m314();
   }

   @EventHandler
   public void m67(PostMotionEvent var1) {
      if (!this.util.m81() && this.mc.interactionManager != null) {
         if (this.mc.currentScreen == null) {
            if (!this.mc.player.isUsingItem()) {
               if (!this.mc.player.getItemCooldownManager().isCoolingDown(new ItemStack(Items.ENDER_PEARL))) {
                  if (!this.f12.m6() || this.mc.player.getMainHandStack().isOf(Items.ENDER_PEARL) || this.mc.player.getOffHandStack().isOf(Items.ENDER_PEARL)) {
                     if (this.m715() != -1) {
                        EnderPearlEntity var2 = this.m775();
                        if (var2 != null) {
                           Vec3d var3 = this.m776(var2);
                           if (var3 != null) {
                              double var4 = this.mc.player.getEyePos().distanceTo(var3);
                              if (!(var4 < this.f6.getValue()) && !(var4 > this.f7.getValue())) {
                                 RotationVec var6 = this.m417(var3);
                                 if (var6 != null) {
                                    float var7 = (float)this.f8.getValue();
                                    RotationUtil.m406(var6, RotationMode.f3, var7, var7, var7);
                                    if (System.currentTimeMillis() - this.f13 >= (long)this.f10.getValue()) {
                                       if (!((double)RotationUtil.m415().m402(var6) > this.f9.getValue())) {
                                          this.m676();
                                          this.f13 = System.currentTimeMillis();
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private EnderPearlEntity m775() {
      EnderPearlEntity var1 = null;
      double var2 = Double.MAX_VALUE;
      double var4 = this.f5.getValue() * this.f5.getValue();

      for (Entity var7 : this.mc.world.getEntities()) {
         if (var7 instanceof EnderPearlEntity) {
            EnderPearlEntity var8 = (EnderPearlEntity)var7;
            if (!var8.isRemoved() && (this.f11.m6() || var8.getOwner() != this.mc.player)) {
               double var9 = var8.squaredDistanceTo(this.mc.player);
               if (!(var9 > var4) && !(var9 >= var2)) {
                  var2 = var9;
                  var1 = var8;
               }
            }
         }
      }

      return var1;
   }

   private Vec3d m776(EnderPearlEntity var1) {
      Vec3d var2 = var1.getEntityPos();
      Vec3d var3 = var1.getVelocity();
      double var4 = (double)(this.mc.world.getBottomY() - 16);

      for (int var6 = 0; var6 < 200; var6++) {
         Vec3d var7 = var2.add(var3);
         BlockHitResult var8 = this.mc.world.raycast(new RaycastContext(var2, var7, ShapeType.COLLIDER, FluidHandling.NONE, var1));
         if (var8.getType() == Type.BLOCK) {
            return var8.getPos();
         }

         var3 = var3.multiply(0.99).add(0.0, -0.03, 0.0);
         var2 = var7;
         if (var7.y < var4) {
            return null;
         }
      }

      return null;
   }

   private RotationVec m417(Vec3d var1) {
      Vec3d var2 = this.mc.player.getEyePos();
      double var3 = var1.x - var2.x;
      double var5 = var1.y - var2.y;
      double var7 = var1.z - var2.z;
      double var9 = Math.hypot(var3, var7);
      if (var9 < 0.001) {
         return null;
      } else {
         float var11 = (float)(Math.toDegrees(Math.atan2(var7, var3)) - 90.0);
         Float var12 = this.m777(var9, var5);
         return var12 == null ? null : new RotationVec(var11, var12);
      }
   }

   private Float m777(double var1, double var3) {
      float var5 = Float.NaN;
      double var6 = Double.MAX_VALUE;

      for (float var8 = -89.0F; var8 <= 60.0F; var8++) {
         Double var9 = this.m778(var1, var8);
         if (var9 != null) {
            double var10 = Math.abs(var9 - var3);
            if (var10 < var6) {
               var6 = var10;
               var5 = var8;
            }
         }
      }

      if (Float.isNaN(var5)) {
         return null;
      } else {
         float var14 = var5 - 1.0F;
         float var15 = var5 + 1.0F;

         for (float var16 = var14; var16 <= var15; var16 += 0.05F) {
            Double var11 = this.m778(var1, var16);
            if (var11 != null) {
               double var12 = Math.abs(var11 - var3);
               if (var12 < var6) {
                  var6 = var12;
                  var5 = var16;
               }
            }
         }

         return var6 > 1.5 ? null : MathHelper.clamp(var5, -90.0F, 90.0F);
      }
   }

   private Double m778(double var1, float var3) {
      double var4 = Math.toRadians((double)var3);
      double var6 = Math.cos(var4) * 1.5;
      double var8 = -Math.sin(var4) * 1.5;
      if (var6 <= 1.0E-4) {
         return null;
      } else {
         double var10 = 0.0;
         double var12 = 0.0;

         for (int var14 = 0; var14 < 200; var14++) {
            double var15 = var10;
            double var17 = var12;
            var10 += var6;
            var12 += var8;
            if (var10 >= var1) {
               double var19 = (var1 - var15) / var6;
               return var17 + var8 * var19;
            }

            var6 *= 0.99;
            var8 = var8 * 0.99 - 0.03;
         }

         return null;
      }
   }

   private int m715() {
      for (int var1 = 0; var1 < 9; var1++) {
         if (this.mc.player.getInventory().getStack(var1).isOf(Items.ENDER_PEARL)) {
            return var1;
         }
      }

      return -1;
   }

   private void m676() {
      int var1 = this.m715();
      if (var1 != -1) {
         int var2 = this.mc.player.getInventory().getSelectedSlot();
         if (var1 != var2) {
            this.mc.player.getInventory().setSelectedSlot(var1);
            this.mc.player.networkHandler.sendPacket(new UpdateSelectedSlotC2SPacket(var1));
         }

         this.mc.interactionManager.interactItem(this.mc.player, Hand.MAIN_HAND);
         this.mc.player.swingHand(Hand.MAIN_HAND);
         if (var1 != var2) {
            this.mc.player.getInventory().setSelectedSlot(var2);
            this.mc.player.networkHandler.sendPacket(new UpdateSelectedSlotC2SPacket(var2));
         }
      }
   }
}

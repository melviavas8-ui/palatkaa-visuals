package ua.daamky.features.combat;

import ua.daamky.events.PostMotionEvent;
import ua.daamky.settings.BooleanSetting;
import ua.daamky.settings.MultiChoiceSettingBase;
import ua.daamky.settings.NumberSetting;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.ModuleManager;
import ua.daamky.system.api.NewFunction;
import ua.daamky.system.events.EventHandler;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;

@NewFunction(
   I0 = "AimAssist",
   I00 = "Доводит прицел до цели",
   I000 = Category.COMBAT
)
public class AimAssist extends Module {
   private final MultiChoiceSettingBase f1 = new MultiChoiceSettingBase(
      "Цели для наведения",
      "Игроки",
      "Животные",
      "Мобы",
      "Друзья"
   );
   private final BooleanSetting f2 = new BooleanSetting("Наводить за стеной", false);
   private final NumberSetting f3 = new NumberSetting("Порог", 5.0, 1.0, 5.0, 0.25);
   private final BooleanSetting f4 = new BooleanSetting("Только с оружием", true);
   private LivingEntity f5;
   private Vec3d f6;

   public AimAssist() {
      this.addSettings(new Setting[]{this.f1, this.f2, this.f3, this.f4});
      this.f1.m21("Животные");
      this.f1.m21("Мобы");
   }

   @Override
   public void onDisable() {
      this.f5 = null;
      this.f6 = null;
   }

   @EventHandler
   public void m67(PostMotionEvent var1) {
      if (!this.util.m81()) {
         LivingEntity var6;
         label23: {
            var6 = null;
            TriggerBot var3 = ModuleManager.getModule(TriggerBot.class);
            if (var3 != null && var3.isEnabled()) {
               LivingEntity var5 = var3.m584();
               if (var5 instanceof LivingEntity && this.m586(var5)) {
                  var6 = var5;
                  break label23;
               }
            }

            var6 = this.m584();
         }

         if (var6 != this.f5) {
            this.f6 = null;
         }

         this.f5 = var6;
         this.m115();
      }
   }

   private void m115() {
      if (this.m586(this.f5) && !this.mc.player.isUsingItem()) {
         if (!this.f4.m6() || this.m585()) {
            Vec3d var1 = this.m588(this.f5, 3.0, this.f2.m6());
            if (var1 != Vec3d.ZERO) {
               this.f6 = this.f6 == null ? var1 : this.f6.lerp(var1, 0.2);
               float var2 = (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(this.f6.z, this.f6.x)) - 90.0);
               float var3 = (float)(-Math.toDegrees(Math.atan2(this.f6.y, Math.hypot(this.f6.x, this.f6.z))));
               float var4 = MathHelper.wrapDegrees(var2 - this.mc.player.getYaw());
               float var5 = var3 - this.mc.player.getPitch();
               if (Math.abs(var5) <= 13.0F
                  && Math.abs(var4) < 8.0F
                  && this.m590(this.mc.player.getYaw(), this.mc.player.getPitch(), 3.0, this.f5, this.f2.m6())) {
                  var5 = 0.0F;
               }

               float var6 = this.mc.getRenderTickCounter().getDynamicDeltaTicks();
               float var7 = MathHelper.clamp((float)Math.hypot((double)var4, (double)var5) / 4.0F, 0.0F, 1.0F);
               float var8 = (float)this.f3.getValue() * var6 * var7;
               if (!(var8 <= 0.0F)) {
                  float var9 = Math.min(1.0F, var8 / Math.max(Math.abs(var4), Math.abs(var5) * 2.0F));
                  this.mc.player.setYaw(this.mc.player.getYaw() + var4 * var9);
                  if (var5 != 0.0F) {
                     this.mc.player.setPitch(MathHelper.clamp(this.mc.player.getPitch() + var5 * var9, -90.0F, 90.0F));
                  }
               }
            }
         }
      }
   }

   private LivingEntity m584() {
      Vec3d var1 = this.mc.player.getEyePos();
      Vec3d var2 = Vec3d.fromPolar(this.mc.player.getPitch(), this.mc.player.getYaw());
      LivingEntity var3 = null;
      double var4 = Double.MAX_VALUE;

      for (Entity var7 : this.mc.world.getEntities()) {
         if (var7 instanceof LivingEntity) {
            LivingEntity var8 = (LivingEntity)var7;
            if (this.m586(var8) && (this.f2.m6() || this.m592(var8))) {
               Vec3d var9 = var8.getBoundingBox().getCenter().subtract(var1).normalize();
               double var10 = Math.acos(MathHelper.clamp(var2.dotProduct(var9), -1.0, 1.0));
               if (var10 < var4) {
                  var4 = var10;
                  var3 = var8;
               }
            }
         }
      }

      return var3;
   }

   private boolean m585() {
      ItemStack var1 = this.mc.player.getMainHandStack();
      return var1.isIn(ItemTags.SWORDS) || var1.isIn(ItemTags.AXES) || var1.isOf(Items.MACE);
   }

   private boolean m586(LivingEntity var1) {
      if (var1 != null && var1.isAlive() && !var1.isRemoved() && var1 != this.mc.player) {
         double var2 = 4.0 + this.mc.player.getVelocity().length() * 3.0;
         return !this.m593(var1, var2) ? false : this.m587(var1);
      } else {
         return false;
      }
   }

   private boolean m587(LivingEntity var1) {
      if (var1 instanceof PlayerEntity var2) {
         if (!this.f1.m20("Игроки")) {
            return false;
         } else {
            return NoFriendDamage.m595(var2) ? this.f1.m20("Друзья") : true;
         }
      } else if (var1 instanceof AnimalEntity) {
         return this.f1.m20("Животные");
      } else {
         return var1 instanceof MobEntity ? this.f1.m20("Мобы") : false;
      }
   }

   private Vec3d m588(LivingEntity var1, double var2, boolean var4) {
      Box var5 = var1.getBoundingBox();
      Vec3d var6 = this.mc.player.getEyePos();
      double var7 = (var5.minX + var5.maxX) * 0.5;
      double var9 = (var5.minZ + var5.maxZ) * 0.5;
      double var11 = var6.distanceTo(var1.getEyePos());
      double var13 = MathHelper.lerp(MathHelper.clamp(var11 / 3.0, 0.0, 1.0), var5.minY, MathHelper.clamp(var6.y, var5.minY, var5.maxY));
      ArrayList var15 = new ArrayList();
      var15.add(new Vec3d(var7, var13, var9));
      double[] var16 = new double[]{0.0, 0.125, 0.25, 0.375, 0.5, 0.625, 0.75, 0.875, 1.0};
      int var17 = var16.length - 1;

      for (int var18 = 0; var18 < var16.length; var18++) {
         for (int var19 = 0; var19 < var16.length; var19++) {
            for (int var20 = 0; var20 < var16.length; var20++) {
               if (var18 == 0 || var18 == var17 || var19 == 0 || var19 == var17 || var20 == 0 || var20 == var17) {
                  var15.add(
                     new Vec3d(
                        MathHelper.lerp(var16[var18], var5.minX, var5.maxX),
                        MathHelper.lerp(var16[var19], var5.minY, var5.maxY),
                        MathHelper.lerp(var16[var20], var5.minZ, var5.maxZ)
                     )
                  );
               }
            }
         }
      }

      for (double var21 : new double[]{0.0, 0.20000001551382535}) {
         Vec3d var23 = this.m589(var15, var6, var1, var2, var21, false);
         if (var23 != null) {
            return var23;
         }

         if (var4) {
            Vec3d var24 = this.m589(var15, var6, var1, var2, var21, true);
            if (var24 != null) {
               return var24;
            }
         }
      }

      return Vec3d.ZERO;
   }

   private Vec3d m589(List<Vec3d> var1, Vec3d var2, LivingEntity var3, double var4, double var6, boolean var8) {
      List<Vec3d> var9 = new ArrayList<>();

      for (Vec3d var11 : var1) {
         Vec3d var12 = var11.subtract(var2);
         double var13 = var4 + var6;
         if (var12.length() <= var13) {
            float var15 = (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(var12.z, var12.x)) - 90.0);
            float var16 = (float)(-Math.toDegrees(Math.atan2(var12.y, Math.hypot(var12.x, var12.z))));
            if (this.m591(var2, var15, var16, var13, var3, var8)) {
               var9.add(var11);
            }
         }
      }

      if (var9.isEmpty()) {
         return null;
      } else {
         Vec3d var18 = Vec3d.ZERO;

         for (Vec3d var22 : var9) {
            var18 = var18.add(var22);
         }

         var18 = var18.multiply(1.0 / (double)var9.size());
         Vec3d var21 = (Vec3d)var9.get(0);
         double var23 = Double.MAX_VALUE;

         for (Vec3d var24 : var9) {
            double var25 = var24.squaredDistanceTo(var18);
            if (var25 < var23) {
               var23 = var25;
               var21 = var24;
            }
         }

         return var21.subtract(var2);
      }
   }

   private boolean m590(float var1, float var2, double var3, LivingEntity var5, boolean var6) {
      return this.m591(this.mc.player.getEyePos(), var1, var2, var3, var5, var6);
   }

   private boolean m591(Vec3d var1, float var2, float var3, double var4, LivingEntity var6, boolean var7) {
      if (this.mc.player != null && this.mc.world != null) {
         Vec3d var8 = Vec3d.fromPolar(var3, var2).multiply(var4);
         Vec3d var9 = var6.getBoundingBox().contains(var1) ? var1 : (Vec3d)var6.getBoundingBox().raycast(var1, var1.add(var8)).orElse(null);
         return var9 == null
            ? false
            : var7 || this.mc.world.raycast(new RaycastContext(var1, var9, ShapeType.OUTLINE, FluidHandling.NONE, this.mc.player)).getType() == Type.MISS;
      } else {
         return false;
      }
   }

   private boolean m592(LivingEntity var1) {
      return this.m591(this.mc.player.getEyePos(), this.mc.player.getYaw(), this.mc.player.getPitch(), 4.0, var1, false)
         || var1.getBoundingBox().contains(this.mc.player.getEyePos());
   }

   private boolean m593(LivingEntity var1, double var2) {
      Vec3d var4 = this.mc.player.getEyePos();
      Box var5 = var1.getBoundingBox();
      double var6 = MathHelper.clamp(var4.x, var5.minX, var5.maxX) - var4.x;
      double var8 = MathHelper.clamp(var4.y, var5.minY, var5.maxY) - var4.y;
      double var10 = MathHelper.clamp(var4.z, var5.minZ, var5.maxZ) - var4.z;
      return var6 * var6 + var8 * var8 + var10 * var10 <= var2 * var2;
   }
}

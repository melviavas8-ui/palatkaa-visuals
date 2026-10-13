package ua.daamky.features.combat;

import ua.daamky.events.PostMotionEvent;
import ua.daamky.settings.NumberSetting;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;
import ua.daamky.system.events.EventHandler;
import ua.daamky.utils.player.RotationUtil;
import ua.daamky.utils.player.RotationMode;
import ua.daamky.utils.player.RotationVec;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

@NewFunction(
   I0 = "AutoExplosion",
   I00 = "Автоматически ставит и взрывает кристалл",
   I000 = Category.COMBAT
)
public class AutoExplosion extends Module {
   private final NumberSetting f1 = new NumberSetting("Радиус", 5.0, 3.0, 6.0, 0.1);
   private final NumberSetting f2 = new NumberSetting("Задержка", 80.0, 0.0, 500.0, 10.0);
   private final Set<BlockPos> f3 = new HashSet<>();
   private final Set<BlockPos> f4 = new HashSet<>();
   private BlockPos f5;
   private int f6 = -1;
   private int f7 = -1;
   private boolean f8;
   private AutoExplosion$1 f9 = AutoExplosion$1.f1;
   private long f10;
   private int f11;

   public AutoExplosion() {
      this.addSettings(new Setting[]{this.f1, this.f2});
   }

   @Override
   public void onEnable() {
      this.f5 = null;
      this.f6 = -1;
      this.f7 = -1;
      this.f8 = false;
      this.f9 = AutoExplosion$1.f1;
      this.f10 = 0L;
      this.f11 = 0;
      this.f3.clear();
      this.f4.clear();
      this.m115();
   }

   @Override
   public void onDisable() {
      this.m682();
      this.f5 = null;
      this.f6 = -1;
      this.f7 = -1;
      this.f8 = false;
      this.f9 = AutoExplosion$1.f1;
      this.f11 = 0;
      this.f3.clear();
      this.f4.clear();
      RotationUtil.m314();
   }

   @EventHandler
   public void m67(PostMotionEvent var1) {
      if (!this.util.m81() && this.mc.player != null && this.mc.world != null && this.mc.interactionManager != null) {
         long var2 = System.currentTimeMillis();
         if (!((double)(var2 - this.f10) < this.f2.getValue())) {
            switch (this.f9) {
               case f1:
                  this.m116();
                  break;
               case f2:
                  this.m676();
                  break;
               case f3:
                  this.m134();
                  break;
               case f4:
                  this.m135();
                  break;
               case f5:
                  this.m677();
                  break;
               case f6:
                  this.m682();
                  this.m683();
            }
         }
      } else {
         this.m683();
      }
   }

   private void m115() {
      BlockPos var1 = this.mc.player.getBlockPos();
      int var2 = (int)this.f1.getValue();
      double var3 = this.f1.getValue() * this.f1.getValue();

      for (int var5 = -var2; var5 <= var2; var5++) {
         for (int var6 = -var2; var6 <= var2; var6++) {
            for (int var7 = -var2; var7 <= var2; var7++) {
               BlockPos var8 = var1.add(var5, var6, var7).toImmutable();
               if (!(this.mc.player.squaredDistanceTo(Vec3d.ofCenter(var8)) > var3)) {
                  Block var9 = this.mc.world.getBlockState(var8).getBlock();
                  if (var9 == Blocks.OBSIDIAN || var9 == Blocks.BEDROCK) {
                     this.f3.add(var8);
                  }
               }
            }
         }
      }
   }

   private void m116() {
      BlockPos var1 = this.mc.player.getBlockPos();
      int var2 = (int)this.f1.getValue();
      double var3 = this.f1.getValue() * this.f1.getValue();

      for (int var5 = -var2; var5 <= var2; var5++) {
         for (int var6 = -var2; var6 <= var2; var6++) {
            for (int var7 = -var2; var7 <= var2; var7++) {
               BlockPos var8 = var1.add(var5, var6, var7).toImmutable();
               if (!(this.mc.player.squaredDistanceTo(Vec3d.ofCenter(var8)) > var3)) {
                  Block var9 = this.mc.world.getBlockState(var8).getBlock();
                  if (var9 != Blocks.OBSIDIAN && var9 != Blocks.BEDROCK) {
                     this.f3.remove(var8);
                     this.f4.remove(var8);
                  } else if (!this.f3.contains(var8) && !this.f4.contains(var8)) {
                     if (!this.m678(var8)) {
                        this.f3.add(var8);
                     } else {
                        int var10 = this.m681();
                        if (var10 != -1) {
                           this.f5 = var8;
                           this.f6 = var10;
                           this.f3.add(var8);
                           this.f4.add(var8);
                           this.f9 = AutoExplosion$1.f2;
                           this.f11 = 0;
                           this.f10 = System.currentTimeMillis();
                           return;
                        }

                        this.f3.add(var8);
                     }
                  }
               }
            }
         }
      }
   }

   private void m676() {
      if (this.f5 != null && this.m678(this.f5)) {
         Vec3d var1 = this.mc.player.getEyePos();
         Vec3d var2 = this.m680(var1, this.f5);
         RotationVec var3 = RotationUtil.m417(var2);
         RotationUtil.m406(var3, RotationMode.f3, 180.0F, 180.0F, 180.0F);
         this.f9 = AutoExplosion$1.f3;
         this.f10 = System.currentTimeMillis();
      } else {
         this.m683();
      }
   }

   private void m134() {
      if (this.f5 != null && this.m678(this.f5)) {
         this.f7 = this.mc.player.getInventory().getSelectedSlot();
         if (this.f6 != this.f7) {
            this.mc.player.getInventory().setSelectedSlot(this.f6);
            this.f8 = true;
         }

         Vec3d var1 = this.mc.player.getEyePos();
         Vec3d var2 = this.m680(var1, this.f5);
         Vec3d var3 = var1.subtract(var2);
         Direction var4 = Direction.getFacing(var3.x, var3.y, var3.z);
         BlockHitResult var5 = new BlockHitResult(var2, var4, this.f5, false);
         this.mc.interactionManager.interactBlock(this.mc.player, Hand.MAIN_HAND, var5);
         this.mc.player.swingHand(Hand.MAIN_HAND);
         this.f9 = AutoExplosion$1.f4;
         this.f11 = 0;
         this.f10 = System.currentTimeMillis();
      } else {
         this.m683();
      }
   }

   private void m135() {
      if (this.f5 == null) {
         this.m683();
      } else {
         EndCrystalEntity var1 = this.m679();
         if (var1 != null) {
            this.f9 = AutoExplosion$1.f5;
            this.f10 = System.currentTimeMillis();
         } else {
            this.f11++;
            if (this.f11 > 8) {
               this.f9 = AutoExplosion$1.f6;
            }

            this.f10 = System.currentTimeMillis();
         }
      }
   }

   private void m677() {
      if (this.f5 == null) {
         this.m683();
      } else {
         EndCrystalEntity var1 = this.m679();
         if (var1 == null) {
            this.f9 = AutoExplosion$1.f6;
         } else {
            if (!var1.getBoundingBox().contains(this.mc.player.getEyePos())) {
               Vec3d var2 = var1.getBoundingBox().getCenter();
               RotationVec var3 = RotationUtil.m417(var2);
               RotationUtil.m406(var3, RotationMode.f3, 180.0F, 180.0F, 180.0F);
            }

            this.mc.interactionManager.attackEntity(this.mc.player, var1);
            this.mc.player.swingHand(Hand.MAIN_HAND);
            this.f9 = AutoExplosion$1.f6;
            this.f10 = System.currentTimeMillis();
         }
      }
   }

   private boolean m678(BlockPos var1) {
      Block var2 = this.mc.world.getBlockState(var1).getBlock();
      if (var2 != Blocks.OBSIDIAN && var2 != Blocks.BEDROCK) {
         return false;
      } else {
         BlockPos var3 = var1.up();
         if (!this.mc.world.getBlockState(var3).isAir()) {
            return false;
         } else if (!this.mc.world.getBlockState(var3.up()).isAir()) {
            return false;
         } else {
            for (Entity var5 : this.mc.world.getEntities()) {
               if (!(var5 instanceof EndCrystalEntity)
                  && var5.getBoundingBox()
                     .intersects(
                        (double)var3.getX(),
                        (double)var3.getY(),
                        (double)var3.getZ(),
                        (double)var3.getX() + 1.0,
                        (double)var3.getY() + 2.0,
                        (double)var3.getZ() + 1.0
                     )) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   private EndCrystalEntity m679() {
      if (this.f5 == null) {
         return null;
      } else {
         BlockPos var1 = this.f5.up();

         for (Entity var3 : this.mc.world.getEntities()) {
            if (var3 instanceof EndCrystalEntity var4
               && var4.getBoundingBox()
                  .intersects(
                     (double)var1.getX() - 0.1,
                     (double)var1.getY() - 0.1,
                     (double)var1.getZ() - 0.1,
                     (double)var1.getX() + 1.1,
                     (double)var1.getY() + 1.1,
                     (double)var1.getZ() + 1.1
                  )) {
               return var4;
            }
         }

         return null;
      }
   }

   private Vec3d m680(Vec3d var1, BlockPos var2) {
      double var3 = this.m154(var1.x, (double)var2.getX(), (double)var2.getX() + 1.0);
      double var5 = this.m154(var1.y, (double)var2.getY(), (double)var2.getY() + 1.0);
      double var7 = this.m154(var1.z, (double)var2.getZ(), (double)var2.getZ() + 1.0);
      return new Vec3d(var3, var5, var7);
   }

   private double m154(double var1, double var3, double var5) {
      return Math.max(var3, Math.min(var5, var1));
   }

   private int m681() {
      for (int var1 = 0; var1 < 9; var1++) {
         if (this.mc.player.getInventory().getStack(var1).getItem() == Items.END_CRYSTAL) {
            return var1;
         }
      }

      return -1;
   }

   private void m682() {
      if (this.f8 && this.f7 >= 0 && this.f7 < 9) {
         this.mc.player.getInventory().setSelectedSlot(this.f7);
      }

      this.f8 = false;
      this.f7 = -1;
   }

   private void m683() {
      this.f5 = null;
      this.f6 = -1;
      this.f11 = 0;
      this.f9 = AutoExplosion$1.f1;
      this.f10 = System.currentTimeMillis();
   }
}

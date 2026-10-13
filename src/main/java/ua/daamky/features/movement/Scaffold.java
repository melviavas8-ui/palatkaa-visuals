package ua.daamky.features.movement;

import ua.daamky.events.PostMotionEvent;
import ua.daamky.settings.BooleanSetting;
import ua.daamky.settings.ModeSetting;
import ua.daamky.settings.NumberSetting;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;
import ua.daamky.system.events.EventHandler;
import ua.daamky.utils.player.RotationUtil;
import ua.daamky.utils.player.RotationMode;
import ua.daamky.utils.player.RotationVec;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

@NewFunction(
   I0 = "Scaffold",
   I00 = "Строит под вами блоки без пакетов (Сайлент ротация)",
   I000 = Category.MOVEMENT
)
public class Scaffold extends Module {
   private final BooleanSetting f1 = new BooleanSetting("Сохранять высоту", false);
   private final BooleanSetting f2 = new BooleanSetting("Telly", false);
   private final ModeSetting f3 = new ModeSetting(
      "Коррекция", "Silent", "None"
   );
   private final NumberSetting f4 = new NumberSetting("Скорость ротации", 90.0, 10.0, 180.0, 5.0);
   private int f5;
   private BlockPos f6;
   private Direction f7;
   private boolean f8;

   public Scaffold() {
      this.addSettings(new Setting[]{this.f1, this.f2, this.f3, this.f4});
   }

   @Override
   public void onEnable() {
      if (this.mc.player != null) {
         this.f5 = (int)Math.floor(this.mc.player.getY());
      }

      this.f6 = null;
      this.f7 = null;
      this.f8 = false;
      super.onEnable();
   }

   @Override
   public void onDisable() {
      if (this.f8) {
         RotationUtil.m314();
         this.f8 = false;
      }

      super.onDisable();
   }

   @EventHandler
   public void m67(PostMotionEvent var1) {
      if (!this.util.m81()) {
         int var2 = -1;

         for (int var3 = 0; var3 < 9; var3++) {
            ItemStack var4 = this.mc.player.getInventory().getStack(var3);
            if (!var4.isEmpty() && var4.getItem() instanceof BlockItem) {
               var2 = var3;
               break;
            }
         }

         if (var2 == -1) {
            if (this.f8) {
               RotationUtil.m314();
               this.f8 = false;
            }
         } else {
            if (this.f1.m6() && this.mc.options.jumpKey.isPressed()) {
               this.f5 = (int)Math.floor(this.mc.player.getY());
            }

            boolean var14 = true;
            if (this.f2.m6()) {
               boolean var15 = !this.mc.player.isOnGround() && this.mc.player.getVelocity().y < 0.0;
               if (!var15) {
                  var14 = false;
               }
            }

            double var16 = this.f1.m6() ? (double)(this.f5 - 1) : this.mc.player.getY() - 1.0;
            BlockPos var6 = new BlockPos((int)Math.floor(this.mc.player.getX()), (int)Math.floor(var16), (int)Math.floor(this.mc.player.getZ()));
            if (this.mc.world.getBlockState(var6).isReplaceable() && var14) {
               this.m118(var6);
            } else {
               this.f6 = null;
               this.f7 = null;
            }

            if (this.f6 != null && this.f7 != null) {
               Vec3d var7 = new Vec3d(
                  (double)this.f6.getX() + 0.5 + (double)this.f7.getOffsetX() * 0.5,
                  (double)this.f6.getY() + 0.5 + (double)this.f7.getOffsetY() * 0.5,
                  (double)this.f6.getZ() + 0.5 + (double)this.f7.getOffsetZ() * 0.5
               );
               RotationVec var8 = RotationUtil.m417(var7);
               float var9 = (float)this.f4.getValue();
               RotationMode var10 = RotationMode.f1;
               if (this.f3.m17("Silent")) {
                  var10 = RotationMode.f3;
               }

               RotationUtil.m406(var8, var10, var9, var9, var9);
               this.f8 = true;
               RotationVec var11 = RotationUtil.m415();
               if (this.m727(var8, var11)) {
                  int var12 = this.mc.player.getInventory().getSelectedSlot();
                  this.mc.player.getInventory().setSelectedSlot(var2);
                  BlockHitResult var13 = new BlockHitResult(var7, this.f7, this.f6, false);
                  this.mc.interactionManager.interactBlock(this.mc.player, Hand.MAIN_HAND, var13);
                  this.mc.player.swingHand(Hand.MAIN_HAND);
                  this.mc.player.getInventory().setSelectedSlot(var12);
                  this.f6 = null;
                  this.f7 = null;
               }
            } else if (this.f8) {
               RotationUtil.m314();
               this.f8 = false;
            }
         }
      }
   }

   private void m118(BlockPos var1) {
      if (this.mc.world.getBlockState(var1).isReplaceable()) {
         List<BlockPos> var2 = new ArrayList<>();
         var2.add(new BlockPos(0, -1, 0));
         var2.add(new BlockPos(1, 0, 0));
         var2.add(new BlockPos(-1, 0, 0));
         var2.add(new BlockPos(0, 0, 1));
         var2.add(new BlockPos(0, 0, -1));

         for (BlockPos var4 : var2) {
            BlockPos var5 = var1.add(var4);
            BlockState var6 = this.mc.world.getBlockState(var5);
            if (!var6.isReplaceable() && !var6.isAir()) {
               this.f6 = var5;
               this.f7 = this.m1282(var4).getOpposite();
               return;
            }
         }
      }
   }

   private Direction m1282(BlockPos var1) {
      if (var1.getX() > 0) {
         return Direction.EAST;
      } else if (var1.getX() < 0) {
         return Direction.WEST;
      } else if (var1.getY() > 0) {
         return Direction.UP;
      } else if (var1.getY() < 0) {
         return Direction.DOWN;
      } else if (var1.getZ() > 0) {
         return Direction.SOUTH;
      } else {
         return var1.getZ() < 0 ? Direction.NORTH : Direction.DOWN;
      }
   }

   private boolean m727(RotationVec var1, RotationVec var2) {
      float var3 = Math.abs(MathHelper.wrapDegrees(var1.m329() - var2.m329()));
      float var4 = Math.abs(var1.m271() - var2.m271());
      return var3 < 15.0F && var4 < 15.0F;
   }
}

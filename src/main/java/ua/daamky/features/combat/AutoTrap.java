package ua.daamky.features.combat;

import ua.daamky.events.PlayerTickEvent;
import ua.daamky.events.PostMotionEvent;
import ua.daamky.settings.BooleanSetting;
import ua.daamky.settings.NumberSetting;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;
import ua.daamky.system.events.EventHandler;
import ua.daamky.utils.player.RaytraceUtil;
import ua.daamky.utils.player.RotationUtil;
import ua.daamky.utils.player.RotationMode;
import ua.daamky.utils.player.RotationVec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ActionResult.Success;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

@NewFunction(
   I0 = "AutoTrap",
   I00 = "Ставит паутину в ноги и рост цели",
   I000 = Category.COMBAT
)
public class AutoTrap extends Module {
   private final NumberSetting f1 = new NumberSetting("Радиус", 3.2, 2.0, 4.5, 0.1);
   private final NumberSetting f2 = new NumberSetting("Задержка (тики)", 7.0, 4.0, 12.0, 1.0);
   private final BooleanSetting f3 = new BooleanSetting("Пауза в GUI", true);
   private final BooleanSetting f4 = new BooleanSetting("Предикт", true);
   private final NumberSetting f5 = new NumberSetting("Предикт тиков", 3.0, 1.0, 10.0, 1.0);
   private long f6;

   public AutoTrap() {
      this.addSettings(new Setting[]{this.f1, this.f2, this.f3, this.f4, this.f5});
   }

   @EventHandler
   public void m67(PostMotionEvent var1) {
      if (this.m687()) {
         PlayerEntity var2 = this.m716();
         if (var2 != null) {
            BlockPos var3 = this.m717(var2);
            if (var3 != null) {
               BlockHitResult var4 = this.m718(var3);
               if (var4 != null) {
                  RotationVec var5 = RotationUtil.m417(var4.getPos());
                  RotationUtil.m406(var5, RotationMode.f1, 65.0F, 65.0F, 180.0F);
               }
            }
         }
      }
   }

   @EventHandler
   public void m713(PlayerTickEvent var1) {
      if (this.m687()) {
         PlayerEntity var2 = this.m716();
         if (var2 != null) {
            int var3 = this.m715();
            if (var3 != -1) {
               BlockPos var4 = this.m717(var2);
               if (var4 != null) {
                  boolean var5 = false;

                  for (BlockPos var9 : new BlockPos[]{var4, var4.up()}) {
                     if (this.mc.world.getBlockState(var9).isAir()) {
                        BlockHitResult var10 = this.m718(var9);
                        if (var10 != null && this.m714(var10, var3)) {
                           var5 = true;
                        }
                     }
                  }

                  if (var5) {
                     this.f6 = System.currentTimeMillis();
                  }
               }
            }
         }
      }
   }

   private boolean m687() {
      if (this.util.m81() || this.mc.interactionManager == null) {
         return false;
      } else {
         return this.f3.m6() && this.mc.currentScreen != null ? false : System.currentTimeMillis() - this.f6 >= (long)(this.f2.getValue() * 50.0);
      }
   }

   private boolean m714(BlockHitResult var1, int var2) {
      int var3 = this.mc.player.getInventory().getSelectedSlot();
      this.mc.player.getInventory().setSelectedSlot(var2);
      this.mc.player.networkHandler.sendPacket(new UpdateSelectedSlotC2SPacket(this.mc.player.getInventory().getSelectedSlot()));
      ActionResult var4 = this.mc.interactionManager.interactBlock(this.mc.player, Hand.MAIN_HAND, var1);
      if (var4 instanceof Success) {
         this.mc.player.swingHand(Hand.MAIN_HAND);
      }

      this.mc.player.getInventory().setSelectedSlot(var3);
      this.mc.player.networkHandler.sendPacket(new UpdateSelectedSlotC2SPacket(this.mc.player.getInventory().getSelectedSlot()));
      return var4 instanceof Success;
   }

   private int m715() {
      for (int var1 = 0; var1 < 9; var1++) {
         if (this.mc.player.getInventory().getStack(var1).isOf(Items.COBWEB)) {
            return var1;
         }
      }

      return -1;
   }

   private PlayerEntity m716() {
      PlayerEntity var1 = null;
      double var2 = Double.MAX_VALUE;

      for (PlayerEntity var5 : this.mc.world.getPlayers()) {
         if (var5 != this.mc.player && var5.isAlive() && !RaytraceUtil.m80(var5.getNameForScoreboard())) {
            double var6 = (double)this.mc.player.distanceTo(var5);
            if (!(var6 > this.f1.getValue()) && !(var6 >= var2)) {
               var2 = var6;
               var1 = var5;
            }
         }
      }

      return var1;
   }

   private BlockPos m717(PlayerEntity var1) {
      if (!this.f4.m6()) {
         return var1.getBlockPos();
      } else {
         Vec3d var2 = new Vec3d(var1.getX() - var1.lastX, var1.getY() - var1.lastY, var1.getZ() - var1.lastZ);
         return var2.lengthSquared() < 0.001 ? var1.getBlockPos() : BlockPos.ofFloored(var1.getEntityPos().add(var2.multiply(this.f5.getValue())));
      }
   }

   private BlockHitResult m718(BlockPos var1) {
      if (!this.mc.world.getBlockState(var1).isAir()) {
         return null;
      } else {
         for (Direction var5 : Direction.values()) {
            BlockPos var6 = var1.offset(var5);
            BlockState var7 = this.mc.world.getBlockState(var6);
            if (!var7.isAir() && !var7.isOf(Blocks.COBWEB)) {
               Direction var8 = var5.getOpposite();
               Vec3d var9 = new Vec3d(
                  (double)var6.getX() + 0.5 + (double)var8.getOffsetX() * 0.5,
                  (double)var6.getY() + 0.5 + (double)var8.getOffsetY() * 0.5,
                  (double)var6.getZ() + 0.5 + (double)var8.getOffsetZ() * 0.5
               );
               return new BlockHitResult(var9, var8, var6, false);
            }
         }

         return null;
      }
   }
}

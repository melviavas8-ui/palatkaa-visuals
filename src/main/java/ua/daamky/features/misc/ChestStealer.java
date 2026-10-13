package ua.daamky.features.misc;

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
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ShulkerBoxScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

@NewFunction(
   I0 = "ChestStealer",
   I00 = "Забирает предметы из контейнеров",
   I000 = Category.MISC
)
public class ChestStealer extends Module {
   private final NumberSetting f1 = new NumberSetting("Задержка", 100.0, 0.0, 1000.0, 10.0);
   private final BooleanSetting f2 = new BooleanSetting("Авто-открытие", false);
   private long f3;

   public ChestStealer() {
      this.addSettings(new Setting[]{this.f1, this.f2});
   }

   @EventHandler
   public void m67(PostMotionEvent var1) {
      if (!this.util.m81() && this.mc.interactionManager != null) {
         ScreenHandler var2 = this.mc.player.currentScreenHandler;
         if (!(var2 instanceof GenericContainerScreenHandler) && !(var2 instanceof ShulkerBoxScreenHandler)) {
            if (this.f2.m6() && this.mc.currentScreen == null) {
               this.m115();
            }
         } else {
            this.m838(var2);
         }
      }
   }

   private void m838(ScreenHandler var1) {
      if (!((double)(System.currentTimeMillis() - this.f3) < this.f1.getValue())) {
         for (Slot var3 : var1.slots) {
            if (var3.hasStack() && var3.inventory != this.mc.player.getInventory()) {
               this.mc.interactionManager.clickSlot(var1.syncId, var3.id, 0, SlotActionType.QUICK_MOVE, this.mc.player);
               this.f3 = System.currentTimeMillis();
               return;
            }
         }
      }
   }

   private void m115() {
      BlockPos var1 = this.m839(5);
      if (var1 != null) {
         RotationVec var2 = RotationUtil.m417(Vec3d.ofCenter(var1));
         RotationUtil.m406(var2, RotationMode.f1, 60.0F, 60.0F, 60.0F);
         float var3 = this.mc.getRenderTickCounter().getTickProgress(true);
         if (this.mc.player.raycast(5.0, var3, false) instanceof BlockHitResult var5 && var5.getType() == Type.BLOCK) {
            BlockState var6 = this.mc.world.getBlockState(var5.getBlockPos());
            if (this.m125(var6)) {
               this.mc.interactionManager.interactBlock(this.mc.player, Hand.MAIN_HAND, var5);
               this.mc.player.swingHand(Hand.MAIN_HAND);
            }
         }
      }
   }

   private BlockPos m839(int var1) {
      BlockPos var2 = this.mc.player.getBlockPos();
      BlockPos var3 = null;
      double var4 = Double.MAX_VALUE;

      for (BlockPos var7 : BlockPos.iterate(var2.add(-var1, -var1, -var1), var2.add(var1, var1, var1))) {
         if (this.m125(this.mc.world.getBlockState(var7))) {
            double var8 = this.mc.player.squaredDistanceTo(Vec3d.ofCenter(var7));
            if (var8 < var4 && var8 <= 25.0) {
               var4 = var8;
               var3 = var7.toImmutable();
            }
         }
      }

      return var3;
   }

   private boolean m125(BlockState var1) {
      return var1.isOf(Blocks.CHEST) || var1.isOf(Blocks.TRAPPED_CHEST) || var1.isOf(Blocks.ENDER_CHEST) || var1.getBlock() instanceof ShulkerBoxBlock;
   }
}

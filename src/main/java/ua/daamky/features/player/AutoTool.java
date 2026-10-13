package ua.daamky.features.player;

import ua.daamky.events.PostMotionEvent;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;
import ua.daamky.system.events.EventHandler;
import ua.daamky.utils.SilentInputUtil;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.Window;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;

@NewFunction(
   I0 = "AutoTool",
   I00 = "Выбирает лучший инструмент для добычи блоков",
   I000 = Category.PLAYER
)
public class AutoTool extends Module {
   private int f1 = -1;
   private int f2 = -1;
   private boolean f3;
   private AutoTool$1 f4 = AutoTool$1.f1;
   private boolean enabled;
   private boolean f5;
   private boolean f6;
   private boolean f7;
   private boolean f8;
   private boolean f9 = false;

   @EventHandler
   public void m67(PostMotionEvent var1) {
      if (this.util.m91() || this.mc.player.isCreative()) {
         this.f1 = -1;
         this.m677();
      } else if (!this.mc.player.isUsingItem()) {
         if (this.m628()) {
            if (this.f2 == -1 && this.f4 == AutoTool$1.f1) {
               this.f1 = this.m753();
               if (this.f1 != -1) {
                  if (this.f1 >= 0 && this.f1 <= 8) {
                     this.f2 = this.mc.player.getInventory().getSelectedSlot();
                     this.mc.player.getInventory().setSelectedSlot(this.f1);
                     this.f3 = true;
                  } else {
                     this.m691(this.f1);
                  }
               }
            }
         } else if (this.f2 != -1 && this.f4 == AutoTool$1.f1) {
            if (this.f2 >= 0 && this.f2 <= 8) {
               this.mc.player.getInventory().setSelectedSlot(this.f2);
               this.f2 = -1;
               this.f3 = false;
            } else {
               this.m116();
            }
         }

         if (this.f4 != AutoTool$1.f1) {
            this.m115();
         }
      }
   }

   private void m115() {
      switch (this.f4) {
         case f1:
         default:
            break;
         case f2:
            this.m134();
            this.f9 = true;
            if (this.mc.player.isSprinting()) {
               this.mc.player.setSprinting(false);
            }

            this.f4 = AutoTool$1.f3;
            break;
         case f3:
            SilentInputUtil.m5(this::m707);
            this.f2 = this.f1;
            this.f4 = AutoTool$1.f4;
            break;
         case f4:
            this.m135();
            this.f4 = AutoTool$1.f1;
            break;
         case f5:
            this.m134();
            this.f9 = true;
            if (this.mc.player.isSprinting()) {
               this.mc.player.setSprinting(false);
            }

            this.f4 = AutoTool$1.f6;
            break;
         case f6:
            SilentInputUtil.m5(this::m607);
            this.f4 = AutoTool$1.f7;
            break;
         case f7:
            this.m135();
            this.f4 = AutoTool$1.f1;
      }
   }

   private void m691(int var1) {
      this.f1 = var1;
      this.m676();
      this.f4 = AutoTool$1.f2;
   }

   private void m116() {
      this.m676();
      this.f4 = AutoTool$1.f5;
   }

   private void m676() {
      if (this.mc.getWindow() != null) {
         Window var1 = this.mc.getWindow();
         this.enabled = InputUtil.isKeyPressed(var1, this.mc.options.forwardKey.getDefaultKey().getCode());
         this.f5 = InputUtil.isKeyPressed(var1, this.mc.options.backKey.getDefaultKey().getCode());
         this.f6 = InputUtil.isKeyPressed(var1, this.mc.options.leftKey.getDefaultKey().getCode());
         this.f7 = InputUtil.isKeyPressed(var1, this.mc.options.rightKey.getDefaultKey().getCode());
         this.f8 = InputUtil.isKeyPressed(var1, this.mc.options.jumpKey.getDefaultKey().getCode());
         this.f9 = false;
      }
   }

   private void m134() {
      this.mc.options.forwardKey.setPressed(false);
      this.mc.options.backKey.setPressed(false);
      this.mc.options.leftKey.setPressed(false);
      this.mc.options.rightKey.setPressed(false);
      this.mc.options.jumpKey.setPressed(false);
   }

   private void m135() {
      if (this.f9 && this.mc.getWindow() != null) {
         Window var1 = this.mc.getWindow();
         boolean var2 = InputUtil.isKeyPressed(var1, this.mc.options.forwardKey.getDefaultKey().getCode());
         boolean var3 = InputUtil.isKeyPressed(var1, this.mc.options.backKey.getDefaultKey().getCode());
         boolean var4 = InputUtil.isKeyPressed(var1, this.mc.options.leftKey.getDefaultKey().getCode());
         boolean var5 = InputUtil.isKeyPressed(var1, this.mc.options.rightKey.getDefaultKey().getCode());
         boolean var6 = InputUtil.isKeyPressed(var1, this.mc.options.jumpKey.getDefaultKey().getCode());
         this.mc.options.forwardKey.setPressed(this.enabled && var2);
         this.mc.options.backKey.setPressed(this.f5 && var3);
         this.mc.options.leftKey.setPressed(this.f6 && var4);
         this.mc.options.rightKey.setPressed(this.f7 && var5);
         this.mc.options.jumpKey.setPressed(this.f8 && var6);
         this.f9 = false;
      }
   }

   private void m677() {
      if (this.f9) {
         this.m135();
      }

      this.f4 = AutoTool$1.f1;
      this.f2 = -1;
      this.f3 = false;
      this.f9 = false;
   }

   private void m707() {
      if (this.f1 != -1) {
         this.f3 = true;
         this.mc
            .interactionManager
            .clickSlot(
               this.mc.player.currentScreenHandler.syncId, this.f1, this.mc.player.getInventory().getSelectedSlot(), SlotActionType.SWAP, this.mc.player
            );
      }
   }

   private void m607() {
      if (this.f2 != -1 && this.f3) {
         this.mc
            .interactionManager
            .clickSlot(
               this.mc.player.currentScreenHandler.syncId, this.f2, this.mc.player.getInventory().getSelectedSlot(), SlotActionType.SWAP, this.mc.player
            );
         this.f2 = -1;
         this.f3 = false;
      }
   }

   @Override
   public void onDisable() {
      super.onDisable();
      this.m677();
      this.f1 = -1;
   }

   private int m753() {
      if (this.mc.crosshairTarget instanceof BlockHitResult var1) {
         BlockPos var9 = var1.getBlockPos();
         BlockState var3 = this.mc.world.getBlockState(var9);
         int var4 = -1;
         float var5 = 1.0F;

         for (int var6 = 0; var6 < 36; var6++) {
            ItemStack var7 = this.mc.player.getInventory().getStack(var6);
            float var8 = var7.getMiningSpeedMultiplier(var3);
            if (var8 > var5) {
               var5 = var8;
               var4 = var6;
            }
         }

         return var4;
      } else {
         return -1;
      }
   }

   private boolean m628() {
      return this.mc.crosshairTarget != null && this.mc.options.attackKey.isPressed();
   }
}

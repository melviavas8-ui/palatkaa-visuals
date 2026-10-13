package ua.daamky.features.player;

import ua.daamky.events.PostMotionEvent;
import ua.daamky.gui.Daamky_2;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;
import ua.daamky.system.events.EventHandler;
import ua.daamky.utils.player.RotationUtil;
import ua.daamky.utils.player.RotationMode;
import ua.daamky.utils.player.RotationVec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;

@NewFunction(
   I0 = "ClanUpgrade",
   I00 = "Быстро прокачивает клан редстоуном и факелом",
   I000 = Category.PLAYER
)
public class ClanUpgrade extends Module {
   private final Random f1 = new Random();
   private int f2 = -1;

   @Override
   public void onDisable() {
      if (this.f2 != -1 && this.mc.player != null) {
         this.mc.player.getInventory().setSelectedSlot(this.f2);
         this.f2 = -1;
      }
   }

   @EventHandler
   public void m67(PostMotionEvent var1) {
      if (!this.util.m81() && this.mc.interactionManager != null) {
         int var2 = this.m696(Items.REDSTONE);
         int var3 = this.m696(Items.TORCH);
         int var4 = var2 != -1 ? var2 : var3;
         if (var2 == -1 && var3 == -1) {
            Daamky_2.m467(
               "Нужен факел или редстоун в хотбаре", Formatting.RED
            );
            this.toggle();
         } else {
            float var5 = (float)(Math.sin((double)System.currentTimeMillis() / 1220.0) * (double)(Math.abs(90.0F - this.mc.player.getPitch()) / 8.0F))
               + this.m614(-0.1F, 0.1F);
            RotationVec var6 = new RotationVec(this.mc.player.getYaw() + this.m614(-1.0F, 1.0F), MathHelper.clamp(88.0F + var5, -90.0F, 90.0F));
            RotationUtil.m406(var6, RotationMode.f1, 90.0F, 90.0F, 90.0F);
            if (this.f2 == -1) {
               this.f2 = this.mc.player.getInventory().getSelectedSlot();
            }

            if (this.mc.player.getInventory().getSelectedSlot() != var4) {
               this.mc.player.getInventory().setSelectedSlot(var4);
            }

            RotationVec var7 = RotationUtil.m415();
            if (var7.m402(var6) <= 1.0F) {
               BlockPos var8 = this.mc.player.getBlockPos();
               BlockState var9 = this.mc.world.getBlockState(var8);
               if (!var9.isOf(Blocks.REDSTONE_WIRE) && !var9.isOf(Blocks.TORCH)) {
                  this.mc.interactionManager.interactItem(this.mc.player, Hand.MAIN_HAND);
               } else {
                  this.mc.interactionManager.attackBlock(var8, Direction.UP);
                  this.mc.player.swingHand(Hand.MAIN_HAND);
               }
            }
         }
      }
   }

   private int m696(Item var1) {
      for (int var2 = 0; var2 < 9; var2++) {
         if (this.mc.player.getInventory().getStack(var2).isOf(var1)) {
            return var2;
         }
      }

      return -1;
   }

   private float m614(float var1, float var2) {
      return this.f1.nextFloat() * (var2 - var1) + var1;
   }
}

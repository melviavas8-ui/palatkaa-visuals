package ua.daamky.features.combat;

import ua.daamky.events.InteractEvent;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;
import ua.daamky.system.events.EventHandler;
import net.minecraft.block.Blocks;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket.PositionAndOnGround;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;

@NewFunction(
   I0 = "PacketCriticals",
   I00 = "Бьёт критами под плавном падении и в паутине RW",
   I000 = Category.COMBAT
)
public class PacketCriticals extends Module {
   private long f1;

   @EventHandler
   public void m771(InteractEvent var1) {
      this.m116();
   }

   public boolean m687() {
      return this.mc.player != null && this.mc.world != null ? this.m585() || this.mc.player.hasStatusEffect(StatusEffects.SLOW_FALLING) : false;
   }

   public void m116() {
      if (this.m687()) {
         long var1 = System.currentTimeMillis();
         if (var1 - this.f1 >= 80L) {
            this.f1 = var1;
            double var3 = this.mc.player.getX();
            double var5 = this.mc.player.getY();
            double var7 = this.mc.player.getZ();
            this.mc.player.networkHandler.sendPacket(new PositionAndOnGround(var3, var5 + 0.0625, var7, false, false));
            this.mc.player.networkHandler.sendPacket(new PositionAndOnGround(var3, var5 + 0.0015, var7, false, false));
            this.mc.player.networkHandler.sendPacket(new PositionAndOnGround(var3, var5, var7, false, false));
         }
      }
   }

   private boolean m585() {
      Box var1 = this.mc.player.getBoundingBox();

      for (int var2 = MathHelper.floor(var1.minX); var2 <= MathHelper.floor(var1.maxX); var2++) {
         for (int var3 = MathHelper.floor(var1.minY); var3 <= MathHelper.floor(var1.maxY); var3++) {
            for (int var4 = MathHelper.floor(var1.minZ); var4 <= MathHelper.floor(var1.maxZ); var4++) {
               BlockPos var5 = new BlockPos(var2, var3, var4);
               if (this.mc.world.getBlockState(var5).isOf(Blocks.COBWEB) && new Box(var5).intersects(var1)) {
                  return true;
               }
            }
         }
      }

      return false;
   }
}

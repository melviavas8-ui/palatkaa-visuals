package ua.daamky.features.movement;

import ua.daamky.events.PacketReceiveEvent;
import ua.daamky.events.PacketSendEvent;
import ua.daamky.events.PostMotionEvent;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;
import ua.daamky.system.events.EventHandler;
import ua.daamky.utils.SilentPacketUtil;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.block.BlockState;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.common.CommonPongC2SPacket;
import net.minecraft.network.packet.c2s.common.KeepAliveC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket.Full;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.shape.VoxelShape;

@NewFunction(
   I0 = "NoClip",
   I00 = "Проход сквозь блоки задержкой пакетов",
   I000 = Category.MOVEMENT
)
public class NoClip extends Module {
   private final List<Packet<?>> f1 = new CopyOnWriteArrayList<>();
   private int f2;

   @Override
   public void onDisable() {
      this.m676();
      this.f2 = 0;
      super.onDisable();
   }

   @EventHandler
   public void m870(PacketSendEvent var1) {
      if (!SilentPacketUtil.f1) {
         Packet var2 = var1.m581();
         if (this.m687() && !(var2 instanceof KeepAliveC2SPacket) && !(var2 instanceof CommonPongC2SPacket)) {
            this.f1.add(var2);
            var1.m29();
         }
      }
   }

   @EventHandler
   public void m594(PacketReceiveEvent var1) {
      if (var1.m581() instanceof PlayerPositionLookS2CPacket) {
         this.m676();
         this.m116();
      }
   }

   @EventHandler
   public void m67(PostMotionEvent var1) {
      if (!this.util.m81()) {
         if (++this.f2 >= 10) {
            this.m676();
            this.f2 = 0;
         }

         if (this.m687()) {
            this.m116();
         }

         this.mc.player.setVelocity(this.mc.player.getVelocity().x, 0.0, this.mc.player.getVelocity().z);
      }
   }

   private boolean m687() {
      if (this.util.m81()) {
         return false;
      } else {
         Box var1 = this.mc.player.getBoundingBox();
         BlockPos var2 = BlockPos.ofFloored(var1.minX, var1.minY, var1.minZ);
         BlockPos var3 = BlockPos.ofFloored(var1.maxX, var1.maxY, var1.maxZ);

         for (int var4 = var2.getX(); var4 <= var3.getX(); var4++) {
            for (int var5 = var2.getY(); var5 <= var3.getY(); var5++) {
               for (int var6 = var2.getZ(); var6 <= var3.getZ(); var6++) {
                  BlockPos var7 = new BlockPos(var4, var5, var6);
                  BlockState var8 = this.mc.world.getBlockState(var7);
                  if (!var8.isAir()) {
                     VoxelShape var9 = var8.getCollisionShape(this.mc.world, var7);
                     Box var10 = var1.offset((double)(-var7.getX()), (double)(-var7.getY()), (double)(-var7.getZ()));
                     if (var9.getBoundingBoxes().stream().anyMatch(var1x -> var1x.intersects(var10))) {
                        return true;
                     }
                  }
               }
            }
         }

         return false;
      }
   }

   private void m116() {
      if (this.mc.player != null && this.mc.getNetworkHandler() != null) {
         SilentPacketUtil.m94(
            new Full(
               this.mc.player.getX(),
               this.mc.player.getY(),
               this.mc.player.getZ(),
               this.mc.player.getYaw(),
               this.mc.player.getPitch(),
               this.mc.player.isOnGround(),
               false
            )
         );
      }
   }

   private void m676() {
      if (this.mc.getNetworkHandler() != null && !this.f1.isEmpty()) {
         for (Packet var2 : this.f1) {
            SilentPacketUtil.m94(var2);
         }

         this.f1.clear();
      }
   }
}

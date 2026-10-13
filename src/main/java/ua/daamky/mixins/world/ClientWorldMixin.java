package ua.daamky.mixins.world;

import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ClientWorld.class})
public class ClientWorldMixin {
   @Inject(
      method = {"scheduleBlockRerenderIfNeeded(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Lnet/minecraft/block/BlockState;)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onScheduleBlockRerenderIfNeeded(BlockPos var1, BlockState var2, BlockState var3, CallbackInfo var4) {
      if (MinecraftClient.getInstance().world != (Object)this) {
         var4.cancel();
      }
   }

   @Inject(
      method = {"updateListeners(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Lnet/minecraft/block/BlockState;I)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onUpdateListeners(BlockPos var1, BlockState var2, BlockState var3, int var4, CallbackInfo var5) {
      if (MinecraftClient.getInstance().world != (Object)this) {
         var5.cancel();
      }
   }
}

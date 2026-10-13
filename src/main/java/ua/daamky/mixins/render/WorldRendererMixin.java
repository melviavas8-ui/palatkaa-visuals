package ua.daamky.mixins.render;

import net.minecraft.client.render.BuiltChunkStorage;
import net.minecraft.client.render.WorldRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({WorldRenderer.class})
public class WorldRendererMixin {
   @Shadow
   private BuiltChunkStorage chunks;

   @Inject(
      method = {"scheduleBlockRenders(IIIIII)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onScheduleBlockRenders(int var1, int var2, int var3, int var4, int var5, int var6, CallbackInfo var7) {
      if (this.chunks == null) {
         var7.cancel();
      }
   }

   @Inject(
      method = {"scheduleChunkRender(III)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onScheduleChunkRender(int var1, int var2, int var3, CallbackInfo var4) {
      if (this.chunks == null) {
         var4.cancel();
      }
   }
}

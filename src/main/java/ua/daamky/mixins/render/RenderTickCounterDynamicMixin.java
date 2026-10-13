package ua.daamky.mixins.render;

import ua.daamky.utils.render.RenderUtil;
import it.unimi.dsi.fastutil.floats.FloatUnaryOperator;
import net.minecraft.client.render.RenderTickCounter.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({Dynamic.class})
public class RenderTickCounterDynamicMixin {
   @Shadow
   private float dynamicDeltaTicks;
   @Shadow
   private float tickProgress;
   @Shadow
   private long lastTimeMillis;
   @Shadow
   private float tickTime;
   @Shadow
   private FloatUnaryOperator targetMillisPerTick;

    @Inject(
      method = {"beginRenderTick(J)I"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void beginRenderTick(long var1, CallbackInfoReturnable<Integer> var3) {
      float var4 = RenderUtil.m329();
      if (var4 != 1.0F) {
         this.dynamicDeltaTicks = (float)(var1 - this.lastTimeMillis) / this.targetMillisPerTick.apply(this.tickTime) * var4;
         this.lastTimeMillis = var1;
         this.tickProgress = this.tickProgress + this.dynamicDeltaTicks;
         int var5 = (int)this.tickProgress;
         this.tickProgress -= (float)var5;
         var3.setReturnValue(var5);
      }
   }
}

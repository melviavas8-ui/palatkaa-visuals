package ua.daamky.mixins.render;

import com.mojang.blaze3d.buffers.GpuBufferSlice;
import ua.daamky.gui.AccountOverlay;
import net.minecraft.client.gui.render.GuiRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({GuiRenderer.class})
public class GuiRendererMixin {
   @Inject(
      method = {"render(Lcom/mojang/blaze3d/buffers/GpuBufferSlice;)V"},
      at = {@At("RETURN")}
   )
   private void daamky$renderTitleScreenButtons(GpuBufferSlice var1, CallbackInfo var2) {
      AccountOverlay.m63();
   }
}

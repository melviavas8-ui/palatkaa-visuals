package ua.daamky.mixins.render;

import ua.daamky.utils.IItemEntityRenderState;
import net.minecraft.client.render.entity.state.ItemEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin({ItemEntityRenderState.class})
public class ItemEntityRenderStateMixin implements IItemEntityRenderState {
   @Unique
   private boolean daamky$onGround;

   @Override
   public boolean daamky$isOnGround() {
      return this.daamky$onGround;
   }

   @Override
   public void daamky$setOnGround(boolean var1) {
      this.daamky$onGround = var1;
   }
}

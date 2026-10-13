package ua.daamky.mixins.input;

import ua.daamky.events.MovementInputEvent;
import ua.daamky.mixins.interfaces.IInput;
import ua.daamky.system.events.EventBus;
import ua.daamky.utils.player.RotationUtil;
import net.minecraft.client.input.Input;
import net.minecraft.client.input.KeyboardInput;
import net.minecraft.util.PlayerInput;
import net.minecraft.util.math.Vec2f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({KeyboardInput.class})
public class KeyboardMovementInputMixin {
   @Inject(
      method = {"tick()V"},
      at = {@At("TAIL")}
   )
   private void correctSilentMovement(CallbackInfo var1) {
      IInput var2 = (IInput)(Object)this;
      if (RotationUtil.m41()) {
         Vec2f var3 = var2.getMovementVector();
         PlayerInput var4 = var2.getPlayerInput();
         Vec2f var5 = RotationUtil.m416(var3.y, var3.x);
         var2.setMovementVector(var5.normalize());
         var2.setPlayerInput(new PlayerInput(var5.y > 0.0F, var5.y < 0.0F, var5.x > 0.0F, var5.x < 0.0F, var4.jump(), var4.sneak(), var4.sprint()));
      }
   }

   @Inject(
      method = {"tick()V"},
      at = {@At("TAIL")}
   )
   private void onInput(CallbackInfo var1) {
      IInput var2 = (IInput)(Object)this;
      Vec2f var3 = var2.getMovementVector();
      PlayerInput var4 = var2.getPlayerInput();
      MovementInputEvent var5 = new MovementInputEvent(var3.y, var3.x, var4.jump(), var4.sneak(), var4.sprint());
      EventBus.post(var5);
      var2.setMovementVector(new Vec2f(var5.m272(), var5.m271()));
      var2.setPlayerInput(new PlayerInput(var5.m271() > 0.0F, var5.m271() < 0.0F, var5.m272() > 0.0F, var5.m272() < 0.0F, var5.m101(), var5.m31(), var5.m41()));
   }
}

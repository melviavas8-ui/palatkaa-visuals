package ua.daamky.mixins.interfaces;

import net.minecraft.client.input.Input;
import net.minecraft.util.PlayerInput;
import net.minecraft.util.math.Vec2f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({Input.class})
public interface IInput {
   @Accessor("playerInput")
   PlayerInput getPlayerInput();

   @Accessor("playerInput")
   void setPlayerInput(PlayerInput var1);

   @Accessor("movementVector")
   Vec2f getMovementVector();

   @Accessor("movementVector")
   void setMovementVector(Vec2f var1);
}

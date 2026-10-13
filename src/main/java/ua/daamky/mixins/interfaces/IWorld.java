package ua.daamky.mixins.interfaces;

import java.util.List;
import net.minecraft.world.World;
import net.minecraft.world.chunk.BlockEntityTickInvoker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({World.class})
public interface IWorld {
   @Accessor("blockEntityTickers")
   List<BlockEntityTickInvoker> getBlockEntityTickers();
}

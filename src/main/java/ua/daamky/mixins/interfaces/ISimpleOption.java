package ua.daamky.mixins.interfaces;

import net.minecraft.client.option.SimpleOption;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({SimpleOption.class})
public interface ISimpleOption {
   @Accessor("value")
   void daamky$setValue(Object var1);

   @Accessor("value")
   Object daamky$getValue();
}

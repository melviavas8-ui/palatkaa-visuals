package ua.daamky.features.combat;

import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.ModuleManager;
import ua.daamky.system.api.NewFunction;
import ua.daamky.utils.player.RaytraceUtil;
import net.minecraft.entity.player.PlayerEntity;

@NewFunction(
   I0 = "NoFriendDamage",
   I00 = "Запрещает бить друзей",
   I000 = Category.COMBAT
)
public class NoFriendDamage extends Module {
   public static boolean m595(PlayerEntity var0) {
      NoFriendDamage var1 = ModuleManager.getModule(NoFriendDamage.class);
      return var1 != null && var1.isEnabled() && RaytraceUtil.m80(var0.getGameProfile().name());
   }
}

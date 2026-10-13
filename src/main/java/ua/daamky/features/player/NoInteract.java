package ua.daamky.features.player;


import ua.daamky.settings.BooleanSetting;
import ua.daamky.settings.MultiChoiceSetting;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;

import ua.daamky.system.api.NewFunction;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.registry.Registries;
import net.minecraft.util.math.BlockPos;

@NewFunction(
   I0 = "NoInteract",
   I00 = "Блокирует взаимодействие с выбранными объектами",
   I000 = Category.PLAYER
)
public class NoInteract extends Module {
   private final BooleanSetting f1 = new BooleanSetting("Стойки для брони", true);
   private final MultiChoiceSetting f2 = new MultiChoiceSetting(
      "Блоки",
      "Сундуки",
      "Двери",
      "Воронки",
      "Кнопки",
      "Раздатчики",
      "Нотные блоки",
      "Верстаки",
      "Люки",
      "Печки",
      "Калитки",
      "Наковальни",
      "Рычаги"
   );
   private final BooleanSetting f3 = new BooleanSetting("Всегда активно", true);
   private final Map<String, Predicate<Block>> f4 = new LinkedHashMap<>();

   public NoInteract() {
      this.f4.put("Сундуки", var1 -> this.m1287(var1, "chest"));
      this.f4.put("Двери", var1 -> this.m1287(var1, "door"));
      this.f4.put("Воронки", var1 -> this.m1287(var1, "hopper"));
      this.f4.put("Кнопки", var1 -> this.m1287(var1, "button"));
      this.f4.put("Раздатчики", var1 -> this.m1287(var1, "dispenser"));
      this.f4.put("Нотные блоки", var1 -> this.m1287(var1, "note"));
      this.f4.put("Верстаки", var1 -> this.m1287(var1, "crafting_table"));
      this.f4.put("Люки", var1 -> this.m1287(var1, "trapdoor"));
      this.f4.put("Печки", var1 -> this.m1287(var1, "furnace"));
      this.f4.put("Калитки", var1 -> this.m1287(var1, "fence_gate"));
      this.f4.put("Наковальни", var1 -> this.m1287(var1, "anvil"));
      this.f4.put("Рычаги", var1 -> this.m1287(var1, "lever"));
      this.addSettings(new Setting[]{this.f1, this.f2, this.f3});
   }

   public boolean m678(BlockPos var1) {
      if (!this.m687()) {
         return false;
      } else {
         MinecraftClient var2 = MinecraftClient.getInstance();
         ClientWorld var3 = var2.world;
         if (var3 != null && var1 != null) {
            BlockState var4 = var3.getBlockState(var1);
            Block var5 = var4.getBlock();

            for (Entry var7 : this.f4.entrySet()) {
               if (this.f2.m20((String)var7.getKey()) && ((Predicate)var7.getValue()).test(var5)) {
                  return true;
               }
            }

            return false;
         } else {
            return false;
         }
      }
   }

   public boolean m1040(Entity var1) {
      return this.m687() && this.f1.m6() && var1 instanceof ArmorStandEntity;
   }

   private boolean m1287(Block var1, String var2) {
      String var3 = Registries.BLOCK.getId(var1).getPath();
      String var4 = var1.getTranslationKey();
      return var3.contains(var2) || var4.contains(var2);
   }

   private boolean m687() {
      return this.isEnabled() && this.f3.m6();
   }
}

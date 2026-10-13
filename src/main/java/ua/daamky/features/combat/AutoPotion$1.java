package ua.daamky.features.combat;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.entry.RegistryEntry;

public enum AutoPotion$1 {
   f1(StatusEffects.STRENGTH, "Силу"),
   f2(StatusEffects.SPEED, "Скорость"),
   f3(StatusEffects.FIRE_RESISTANCE, "Огнестойкость");

   public final RegistryEntry<StatusEffect> f4;
   public final String f5;

   public static AutoPotion$1[] m667() {
      return values();
   }

   public static AutoPotion$1 m668(String var0) {
      return Enum.valueOf(AutoPotion$1.class, var0);
   }

   AutoPotion$1(RegistryEntry<StatusEffect> var3, String var4) {
      this.f4 = var3;
      this.f5 = var4;
   }

   public RegistryEntry<StatusEffect> m669() {
      return this.f4;
   }

   public String m18() {
      return this.f5;
   }
}

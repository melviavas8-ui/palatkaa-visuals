package ua.daamky.features.misc;

import java.util.Locale;
import net.minecraft.util.Identifier;

public enum Sounds$1 {
   f1("Обычный", "default"),
   f2("Плавный", "smooth"),
   f3("Целка", "celestial"),
   f4("Блоп", "blop"),
   f5("Звонкий", "bright"),
   f6("Глухой", "muffled"),
   f7("forestmorn", "forestmorn");

   public final String f8;
   public final String f9;

   public static Sounds$1[] m888() {
      return values();
   }

   public static Sounds$1 m889(String var0) {
      return Enum.valueOf(Sounds$1.class, var0);
   }

   Sounds$1(String var3, String var4) {
      this.f8 = var3;
      this.f9 = var4;
   }

   public Identifier m890(boolean var1) {
      return Identifier.of("daamky", this.f9 + (var1 ? "_on" : "_off"));
   }

   public static Sounds$1 m892(String var0) {
      if (var0 == null) {
         return f1;
      }

      // старые названия из сохранённых конфигов
      switch (var0.toLowerCase(Locale.ROOT)) {
         case "дефолт" -> {
            return f1;
         }
         case "module 5", "module5" -> {
            return f5;
         }
         case "module 6", "module6" -> {
            return f6;
         }
         case "module 7", "module7" -> {
            return f7;
         }
         default -> {
         }
      }

      for (Sounds$1 var4 : m888()) {
         if (var4.f8.equalsIgnoreCase(var0) || var4.f9.equals(var0.toLowerCase(Locale.ROOT))) {
            return var4;
         }
      }

      return f1;
   }
}

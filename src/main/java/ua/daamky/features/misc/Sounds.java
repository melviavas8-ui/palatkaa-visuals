package ua.daamky.features.misc;

import ua.daamky.settings.ModeSettingBase;
import ua.daamky.settings.NumberSetting;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.ModuleManager;
import ua.daamky.system.api.NewFunction;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

@NewFunction(
   I0 = "Sounds",
   I00 = "Проигрывает звуки при переключении модулей",
   I000 = Category.MISC
)
public class Sounds extends Module {
   private final ModeSettingBase f1 = new ModeSettingBase(
      "Звук",
      "Обычный",
      "Плавный",
      "Целка",
      "Блоп",
      "Звонкий",
      "Глухой",
      "forestmorn"
   );
   private final NumberSetting f2 = new NumberSetting("Громкость", 100.0, 0.0, 100.0, 1.0);

   public Sounds() {
      this.addSettings(new Setting[]{this.f1, this.f2});
   }

   public static void updateToggled(boolean var0) {
      Sounds var1 = ModuleManager.getModule(Sounds.class);
      Sounds$1 var2 = var1 == null ? Sounds$1.f1 : Sounds$1.m892(var1.f1.m18());
      float var3 = var1 == null ? 1.0F : (float)(var1.f2.getValue() / 100.0);
      if (!(var3 <= 0.0F)) {
         // Все звуки идут через звуковой движок Minecraft (ogg + sounds.json).
         // javax.sound на Android-лаунчерах (Pojav/Zalith) не существует, поэтому wav там молчали.
         m894(var2.m890(var0), var3);
      }
   }

   private static void m894(Identifier var0, float var1) {
      MinecraftClient var2 = MinecraftClient.getInstance();
      if (var2 != null && var2.getSoundManager() != null) {
         var2.getSoundManager().play(PositionedSoundInstance.ui(SoundEvent.of(var0), 1.0F, var1));
      }
   }
}

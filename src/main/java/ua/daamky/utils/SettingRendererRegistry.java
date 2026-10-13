package ua.daamky.utils;

import ua.daamky.settings.Setting;
import java.util.List;

public class SettingRendererRegistry {
   private final List<SettingRenderer<? extends Setting>> f1 = List.of(new BooleanSettingRenderer(), new ColorSettingRenderer(), new ModeSettingRenderer(), new NumberSettingRenderer(), new MultiChoiceSettingRenderer(), new BindSettingRenderer(), new KeybindSettingRenderer());

   public <T extends Setting> SettingRenderer<T> m1454(T var1) {
      for (SettingRenderer var3 : this.f1) {
         if (var3.m1403(var1)) {
            return (SettingRenderer<T>)var3;
         }
      }

      return (SettingRenderer<T>)(Object)DefaultSettingRenderer.f1;
   }
}

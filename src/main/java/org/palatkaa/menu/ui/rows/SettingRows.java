package org.palatkaa.menu.ui.rows;

import org.palatkaa.feature.setting.BooleanSetting;
import org.palatkaa.feature.setting.ButtonSetting;
import org.palatkaa.feature.setting.ColorSetting;
import org.palatkaa.feature.setting.InputBindSetting;
import org.palatkaa.feature.setting.ModeSetting;
import org.palatkaa.feature.setting.MultiSelectSetting;
import org.palatkaa.feature.setting.NumberSetting;
import org.palatkaa.feature.setting.Setting;
import org.palatkaa.feature.setting.TextSetting;

public final class SettingRows {
   private SettingRows() {
   }

   public static SettingRow create(String featureName, Setting<?> setting) {
      SettingRow row = switch (setting) {
         case BooleanSetting booleanSetting -> new ToggleRow(booleanSetting);
         case ButtonSetting buttonSetting -> new ButtonRow(buttonSetting);
         case NumberSetting numberSetting -> new SliderRow(numberSetting);
         case ModeSetting modeSetting -> new DropdownRow(modeSetting);
         case MultiSelectSetting multiSelectSetting -> new DropdownRow(multiSelectSetting);
         case ColorSetting colorSetting -> new ColorRow(colorSetting);
         case TextSetting textSetting -> new TextRow(textSetting);
         case InputBindSetting inputBindSetting -> new BindChipRow(inputBindSetting);
         default -> null;
      };
      return row == null ? null : row.context(featureName);
   }
}


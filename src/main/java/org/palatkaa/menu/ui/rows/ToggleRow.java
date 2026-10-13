package org.palatkaa.menu.ui.rows;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import org.palatkaa.feature.setting.BooleanSetting;
import org.palatkaa.menu.ui.Component;
import org.palatkaa.menu.ui.controls.ToggleComponent;
import org.palatkaa.utils.render.Theme;

public final class ToggleRow extends SettingRow {
   private final ToggleComponent toggle;

   public ToggleRow(BooleanSetting setting) {
      super(setting);
      this.toggle = new ToggleComponent(
         setting::getValue, () -> setting.setValue(!setting.getValue()), ToggleComponent.Style.SWITCH, Theme.Colors.CONTROL_STRONG, Theme.getAccent()
      );
   }

   @Override
   protected void placeControl(Component owner) {
      this.toggle
         .place(owner, this.controlX(), this.controlY(), this.controlWidth(), this.controlHeight())
         .style(ToggleComponent.Style.SWITCH, Theme.Colors.CONTROL_STRONG, controlAccent(this.setting))
         .alpha(this.rowAlpha);
   }

   @Override
   public boolean click(int mouseX, int mouseY) {
      this.host.closeOtherRows(this);
      return this.toggle.handleClick(mouseX, mouseY);
   }

   @Override
   protected void renderControl(Minecraft minecraft, GuiGraphicsExtractor guiGraphicsExtractor) {
      this.toggle.render(minecraft, guiGraphicsExtractor);
   }

   @Override
   protected int controlWidth() {
      return 36;
   }

   @Override
   protected int controlHeight() {
      return 16;
   }
}


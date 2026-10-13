package ua.daamky.settings;


public class Setting {
   String name;
   boolean visible;

   public Setting(String var1) {
      this.name = var1;
      this.visible = true;
   }
   public String getName() {
      return this.name;
   }
   public boolean isVisible() {
      return this.visible;
   }
   public void setVisible(boolean var1) {
      this.visible = var1;
   }
}

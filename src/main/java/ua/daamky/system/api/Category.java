package ua.daamky.system.api;

public enum Category {
   COMBAT("Combat", ""),
   MOVEMENT("Movement", "C"),
   RENDER("Render", "X"),
   PLAYER("Player", ""),
   MISC("Misc", "M"),
   MENU("Menu", "V"),
   CONFIG("Config", "G");

   private final String name;
   private final String icon;

   private Category(String var3, String var4) {
      this.name = var3;
      this.icon = var4;
   }
   public String getName() {
      return this.name;
   }
   public String getIcon() {
      return this.icon;
   }
}

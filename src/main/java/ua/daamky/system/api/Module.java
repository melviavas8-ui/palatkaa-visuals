package ua.daamky.system.api;

import ua.daamky.features.misc.Sounds;
import ua.daamky.settings.Setting;
import ua.daamky.system.events.EventBus;
import ua.daamky.utils.ClientUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.MinecraftClient;

public abstract class Module {
   public final MinecraftClient mc = MinecraftClient.getInstance();
   protected final ClientUtil util = new ClientUtil();
   private final String name;
   private final String description;
   private final Category category;
   private int key;
   private boolean enabled;
   private final List<Setting> settings = new ArrayList<>();

   public Module() {
      if (this.getClass().isAnnotationPresent(NewFunction.class)) {
         NewFunction var1 = this.getClass().getAnnotation(NewFunction.class);
         this.name = var1.I0();
         this.description = var1.I00();
         this.category = var1.I000();
         this.enabled = false;
      } else {
         throw new RuntimeException("Error " + this.getClass().getSimpleName());
      }
   }

   public void addSettings(Setting... var1) {
      this.settings.addAll(Arrays.asList(var1));
   }

   public void toggle() {
      boolean var1 = this.enabled;
      this.enabled = !this.enabled;
      this.updateToggled(var1);
      if (this.enabled) {
         this.onEnable();
         EventBus.register(this);
      } else {
         this.onDisable();
         EventBus.unregister(this);
      }
   }

   private void updateToggled(boolean var1) {
      Sounds var2 = ModuleManager.getModule(Sounds.class);
      boolean var3 = this instanceof Sounds && (var1 || this.enabled);
      if (var2 != null && var2.isEnabled() || var3) {
         Sounds.updateToggled(this.enabled);
      }
   }

   public void onEnable() {
   }

   public void onDisable() {
   }
   public String getName() {
      return this.name;
   }
   public String getDescription() {
      return this.description;
   }
   public Category getCategory() {
      return this.category;
   }
   public int getKey() {
      return this.key;
   }
   public void setKey(int var1) {
      this.key = var1;
   }
   public boolean isEnabled() {
      return this.enabled;
   }
   public void setEnabled(boolean var1) {
      this.enabled = var1;
   }
   public List<Setting> getSettings() {
      return this.settings;
   }
}

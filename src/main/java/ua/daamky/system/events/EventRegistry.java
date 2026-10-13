package ua.daamky.system.events;

import ua.daamky.events.KeyEvent;
import ua.daamky.events.PostMotionEvent;
import ua.daamky.gui.clickgui.ClickGuiScreen;
import ua.daamky.settings.KeybindSetting;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.ModuleManager;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;

public class EventRegistry {
   MinecraftClient f1 = MinecraftClient.getInstance();
   public static ClickGuiScreen f2 = new ClickGuiScreen();
   private final Map<Module, Boolean> f3 = new HashMap<>();

   public static void m1315(Object var0) {
      for (Module var2 : ModuleManager.getModules()) {
         if (var2 != null && var2.isEnabled()) {
            for (Method var6 : var2.getClass().getDeclaredMethods()) {
               if (var6.isAnnotationPresent(EventHandler.class)) {
                  Class[] var7 = var6.getParameterTypes();
                  if (var7.length == 1 && var7[0].isAssignableFrom(var0.getClass())) {
                     try {
                        var6.setAccessible(true);
                        var6.invoke(var2, var0);
                     } catch (Exception var9) {
                     }
                  }
               }
            }
         }
      }
   }

   @EventHandler
   public void m781(KeyEvent var1) {
      int var2 = var1.m580().key();
      int var3 = var1.m189();
      if (var2 == 344 && var3 == 1) {
         if (this.f1.currentScreen == f2) {
            f2.close();
         } else {
            this.f1.setScreen(f2);
         }
      }

      if (this.f1.currentScreen == null && var3 == 1) {
         for (Module var5 : ModuleManager.getModules()) {
            if (var5.getKey() == var2 && var5.getKey() > 0) {
               var5.toggle();
            }
         }
      }
   }

   @EventHandler
   public void m67(PostMotionEvent var1) {
      if (this.f1.currentScreen == null && this.f1.getWindow() != null) {
         long var2 = this.f1.getWindow().getHandle();

         for (Module var5 : ModuleManager.getModules()) {
            int var6 = var5.getKey();
            if (!KeybindSetting.m10(var6)) {
               this.f3.remove(var5);
            } else {
               boolean var7 = GLFW.glfwGetMouseButton(var2, KeybindSetting.m11(var6)) == 1;
               boolean var8 = this.f3.getOrDefault(var5, false);
               if (var7 && !var8) {
                  var5.toggle();
               }

               this.f3.put(var5, var7);
            }
         }
      } else {
         this.f3.clear();
      }
   }
}

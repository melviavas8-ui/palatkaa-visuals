package ua.daamky.settings;

import org.lwjgl.glfw.GLFW;

public class KeybindSetting extends Setting {
   private static final int f1 = 1000;
   int key;
   private boolean pressed;

   public KeybindSetting(String var1, int var2) {
      super(var1);
      this.key = var2;
   }

   public static int m9(int var0) {
      return 1000 + var0;
   }

   public static boolean m10(int var0) {
      return var0 >= 1000;
   }

   public static int m11(int var0) {
      return var0 - 1000;
   }

   public static boolean m12(int var0) {
      return var0 == 2 || var0 == 3 || var0 == 4;
   }

   public boolean m13(int var1) {
      return this.key > 0 && !m10(this.key) && this.key == var1;
   }

   public boolean m14(long var1) {
      if (!m10(this.key)) {
         this.pressed = false;
         return false;
      } else {
         boolean var3 = GLFW.glfwGetMouseButton(var1, m11(this.key)) == 1;
         boolean var4 = var3 && !this.pressed;
         this.pressed = var3;
         return var4;
      }
   }
   public int getKey() {
      return this.key;
   }
   public void m15(int var1) {
      this.key = var1;
   }
}

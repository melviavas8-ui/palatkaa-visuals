package ua.daamky.utils;

import ua.daamky.settings.KeybindSetting;
import org.lwjgl.glfw.GLFW;

public class KeybindFormatter {
   public static String m90(int var0) {
      if (var0 <= 0) {
         return "None";
      } else if (KeybindSetting.m10(var0)) {
         return switch (KeybindSetting.m11(var0)) {
            case 2 -> "M.Mouse";
            case 3 -> "Mouse 4";
            case 4 -> "Mouse 5";
            default -> "Mouse " + KeybindSetting.m11(var0);
         };
      } else if (var0 >= 65 && var0 <= 90) {
         return String.valueOf((char)var0);
      } else if (var0 >= 48 && var0 <= 57) {
         return String.valueOf((char)var0);
      } else {
         return switch (var0) {
            case 32 -> "Space";
            case 39 -> "'";
            case 44 -> ",";
            case 45 -> "-";
            case 46 -> ".";
            case 47 -> "/";
            case 59 -> ";";
            case 61 -> "=";
            case 91 -> "[";
            case 92 -> "\\";
            case 93 -> "]";
            case 96 -> "`";
            case 256 -> "Esc";
            case 257 -> "Enter";
            case 258 -> "Tab";
            case 259 -> "Backspace";
            case 262 -> "Right";
            case 263 -> "Left";
            case 264 -> "Down";
            case 265 -> "Up";
            case 280 -> "Caps";
            case 290 -> "F1";
            case 291 -> "F2";
            case 292 -> "F3";
            case 293 -> "F4";
            case 294 -> "F5";
            case 295 -> "F6";
            case 296 -> "F7";
            case 297 -> "F8";
            case 298 -> "F9";
            case 299 -> "F10";
            case 300 -> "F11";
            case 301 -> "F12";
            case 340 -> "L.Shift";
            case 341 -> "L.Ctrl";
            case 342 -> "L.Alt";
            case 344 -> "R.Shift";
            case 345 -> "R.Ctrl";
            case 346 -> "R.Alt";
            default -> {
               String var1 = GLFW.glfwGetKeyName(var0, 0);
               yield var1 == null ? "Key " + var0 : var1.toUpperCase();
            }
         };
      }
   }
}

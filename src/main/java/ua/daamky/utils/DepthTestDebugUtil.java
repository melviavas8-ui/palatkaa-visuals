package ua.daamky.utils;

import com.mojang.blaze3d.platform.DepthTestFunction;
import java.lang.reflect.Field;

public class DepthTestDebugUtil {
   public static void m427(String[] var0) {
      for (Field var4 : DepthTestFunction.class.getFields()) {
         System.out.println(var4.getName());
      }
   }
}

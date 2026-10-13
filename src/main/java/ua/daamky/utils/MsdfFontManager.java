package ua.daamky.utils;

import ua.daamky.utils.render.fonts.FontAtlas;
import ua.daamky.utils.render.fonts.FontRenderUtil;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.util.Identifier;

public class MsdfFontManager {
   private static final Map<String, FontAtlas> f1 = new HashMap<>();
   private static FontAtlas f2;
   private static boolean f3 = false;
   public static FontAtlas f4;
   public static FontAtlas f5;
   public static FontAtlas f6;

   public static void m63() {
      if (!f3) {
         System.out.println("[MSDF] Initializing MsdfManager...");
         FontRenderUtil.f1 = f4 = m279(
            "sf_regular",
            "sf_regular.png",
            "sf_regular.json"
         );
         FontRenderUtil.f2 = f5 = m279(
            "sf_bold", "sf_bold.png", "sf_bold.json"
         );
         FontRenderUtil.f3 = f6 = m279(
            "wtmico", "wtmico.png", "wtmico.json"
         );
         m21("sf_regular");
         f3 = true;
      }
   }

   public static FontAtlas m279(String var0, String var1, String var2) {
      FontAtlas var3 = new FontAtlas(var0);
      var3.m266(Identifier.of("daamky", var1), Identifier.of("daamky", var2));
      f1.put(var0, var3);
      if (f2 == null) {
         f2 = var3;
      }

      return var3;
   }

   public static FontAtlas m280(String var0) {
      return f1.get(var0);
   }

   public static FontAtlas m281() {
      return f2;
   }

   public static void m21(String var0) {
      FontAtlas var1 = f1.get(var0);
      if (var1 != null) {
         f2 = var1;
      }
   }

   public static void m29() {
      f1.values().forEach(FontAtlas::m277);
      f1.clear();
      f2 = null;
      f3 = false;
   }
}

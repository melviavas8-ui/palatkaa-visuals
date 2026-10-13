package ua.daamky.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class DiscordWebhookHelper {
   public final Map<String, Object> f1 = new HashMap<>();

   public DiscordWebhookHelper() {
   }

   void m57(String var1, Object var2) {
      if (var2 != null) {
         this.f1.put(var1, var2);
      }
   }

   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("{");
      int var2 = 0;

      for (Entry var4 : this.f1.entrySet()) {
         Object var5 = var4.getValue();
         var1.append(this.m58((String)var4.getKey())).append(":");
         if (var5 instanceof String var6) {
            var1.append(this.m58(var6));
         } else if (var5 instanceof Integer || var5 instanceof Boolean) {
            var1.append(var5);
         } else if (var5 instanceof DiscordWebhookHelper) {
            var1.append(var5);
         } else if (var5 instanceof Object[] var7) {
            var1.append("[");

            for (int var8 = 0; var8 < var7.length; var8++) {
               var1.append(var7[var8]);
               if (var8 != var7.length - 1) {
                  var1.append(",");
               }
            }

            var1.append("]");
         }

         if (++var2 != this.f1.size()) {
            var1.append(",");
         }
      }

      var1.append("}");
      return var1.toString();
   }

   public String m58(String var1) {
      return "\"" + this.m59(var1) + "\"";
   }

   public String m59(String var1) {
      return var1.replace("\\", "\\\\")
         .replace("\"", "\\\"")
         .replace("\n", "\\n")
         .replace("\r", "\\r")
         .replace("\t", "\\t");
   }
}

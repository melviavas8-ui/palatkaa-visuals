package ua.daamky.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
public class JsonObjectWrapper {
   final JsonObject f1;

   public JsonObjectWrapper() {
      this.f1 = new JsonObject();
   }

   public JsonObjectWrapper(String var1) {
      try {
         this.f1 = JsonParser.parseString(var1).getAsJsonObject();
      } catch (Exception var3) {
         throw new JsonParseException("Не удалось разобрать JSON", var3);
      }
   }

   JsonObjectWrapper(JsonObject var1) {
      this.f1 = var1;
   }

   public JsonObjectWrapper m141(String var1, String var2) {
      if (var2 == null) {
         this.f1.remove(var1);
      } else {
         this.f1.addProperty(var1, var2);
      }

      return this;
   }

   public JsonObjectWrapper m142(String var1, Number var2) {
      if (var2 == null) {
         this.f1.remove(var1);
      } else {
         this.f1.addProperty(var1, var2);
      }

      return this;
   }

   public JsonObjectWrapper m143(String var1, boolean var2) {
      this.f1.addProperty(var1, var2);
      return this;
   }

   public JsonObjectWrapper m144(String var1, JsonObjectWrapper var2) {
      if (var2 == null) {
         this.f1.remove(var1);
      } else {
         this.f1.add(var1, var2.f1);
      }

      return this;
   }

   public JsonObjectWrapper m145(String var1, JsonArrayWrapper var2) {
      if (var2 == null) {
         this.f1.remove(var1);
      } else {
         this.f1.add(var1, var2.f1);
      }

      return this;
   }

   public boolean m20(String var1) {
      return this.f1.has(var1) && !this.f1.get(var1).isJsonNull();
   }

   public String m59(String var1) {
      JsonElement var2 = this.f1.get(var1);
      if (var2 != null && !var2.isJsonNull()) {
         return var2.getAsString();
      } else {
         throw new JsonParseException("Нет ключа " + var1);
      }
   }

   public long m146(String var1) {
      JsonElement var2 = this.f1.get(var1);
      if (var2 != null && !var2.isJsonNull()) {
         return var2.getAsLong();
      } else {
         throw new JsonParseException("Нет ключа " + var1);
      }
   }

   public int m147(String var1) {
      JsonElement var2 = this.f1.get(var1);
      if (var2 != null && !var2.isJsonNull()) {
         return var2.getAsInt();
      } else {
         throw new JsonParseException("Нет ключа " + var1);
      }
   }

   public JsonObjectWrapper m148(String var1) {
      JsonElement var2 = this.f1.get(var1);
      if (var2 != null && var2.isJsonObject()) {
         return new JsonObjectWrapper(var2.getAsJsonObject());
      } else {
         throw new JsonParseException("Нет объекта по ключу " + var1);
      }
   }

   public JsonArrayWrapper m149(String var1) {
      JsonElement var2 = this.f1.get(var1);
      if (var2 != null && var2.isJsonArray()) {
         return new JsonArrayWrapper(var2.getAsJsonArray());
      } else {
         throw new JsonParseException("Нет массива по ключу " + var1);
      }
   }

   public String m150(String var1, String var2) {
      JsonElement var3 = this.f1.get(var1);
      return var3 != null && !var3.isJsonNull() && var3.isJsonPrimitive() ? var3.getAsString() : var2;
   }

   @Override
   public String toString() {
      return this.f1.toString();
   }
}

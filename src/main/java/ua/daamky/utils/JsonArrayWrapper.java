package ua.daamky.utils;

import com.google.gson.JsonArray;

public class JsonArrayWrapper {
   final JsonArray f1;

   public JsonArrayWrapper() {
      this.f1 = new JsonArray();
   }

   JsonArrayWrapper(JsonArray var1) {
      this.f1 = var1;
   }

   public JsonArrayWrapper m109(Number var1) {
      this.f1.add(var1);
      return this;
   }

   public JsonArrayWrapper m110(String var1) {
      this.f1.add(var1);
      return this;
   }

   public JsonArrayWrapper m111(boolean var1) {
      this.f1.add(var1);
      return this;
   }

   public JsonArrayWrapper m112(JsonObjectWrapper var1) {
      this.f1.add(var1 == null ? null : var1.f1);
      return this;
   }

   public int m113() {
      return this.f1.size();
   }

   public String m90(int var1) {
      return this.f1.get(var1).getAsString();
   }

   @Override
   public String toString() {
      return this.f1.toString();
   }
}

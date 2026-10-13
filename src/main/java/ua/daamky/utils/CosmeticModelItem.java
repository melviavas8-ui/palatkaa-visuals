package ua.daamky.utils;

import com.google.gson.JsonObject;
import net.minecraft.util.Identifier;

public class CosmeticModelItem {
   private final String f1;
   private final int f2;
   private final int f3;
   private String f4;
   private Identifier f5;
   private CosmeticAttachPoint f6 = CosmeticAttachPoint.f3;
   private float f7 = 1.0F;
   private float f8;
   private float f9;
   private float f10;
   private float f11;
   private float f12;
   private float f13;
   private float f14 = 0.0F;
   private float f15 = 1.0F;
   private float f16 = 0.0F;
   private JsonObject f17;

   public CosmeticModelItem(String var1, int var2, int var3) {
      this.f1 = var1;
      this.f2 = var2;
      this.f3 = var3;
   }

   public String m37() {
      return this.f1;
   }

   public int m189() {
      return this.f2;
   }

   public int m559() {
      return this.f3;
   }

   public String m30() {
      return this.f4;
   }

   public void m16(String var1) {
      this.f4 = var1;
   }

   public Identifier m560() {
      return this.f5;
   }

   public void m267(Identifier var1) {
      this.f5 = var1;
   }

   public CosmeticAttachPoint m561() {
      return this.f6;
   }

   public void m562(CosmeticAttachPoint var1) {
      this.f6 = var1;
   }

   public float m523() {
      return this.f7;
   }

   public void m348(float var1) {
      this.f7 = var1;
   }

   public float m524() {
      return this.f8;
   }

   public void m410(float var1) {
      this.f8 = var1;
   }

   public float m525() {
      return this.f9;
   }

   public void m519(float var1) {
      this.f9 = var1;
   }

   public float m2() {
      return this.f10;
   }

   public void m520(float var1) {
      this.f10 = var1;
   }

   public float m529() {
      return this.f11;
   }

   public void m521(float var1) {
      this.f11 = var1;
   }

   public float m530() {
      return this.f12;
   }

   public void m522(float var1) {
      this.f12 = var1;
   }

   public float m563() {
      return this.f13;
   }

   public void m526(float var1) {
      this.f13 = var1;
   }

   public float m564() {
      return this.f14;
   }

   public void m527(float var1) {
      this.f14 = var1;
   }

   public float m565() {
      return this.f15;
   }

   public void m528(float var1) {
      this.f15 = var1;
   }

   public float m566() {
      return this.f16;
   }

   public void m531(float var1) {
      this.f16 = var1;
   }

   public JsonObject m567() {
      return this.f17;
   }

   public void m568(JsonObject var1) {
      this.f17 = var1;
   }
}

package com.jagrosh.discordipc.entities;

import java.util.function.Consumer;

public class Callback {
   private final Consumer<Packet> success;
   private final Consumer<String> failure;

   public Callback() {
      this((Consumer<Packet>)null, null);
   }

   public Callback(Consumer<Packet> var1) {
      this(var1, null);
   }

   public Callback(Consumer<Packet> var1, Consumer<String> var2) {
      this.success = var1;
      this.failure = var2;
   }

   @Deprecated
   public Callback(Runnable var1, Consumer<String> var2) {
      this(var1x -> var1.run(), var2);
   }

   @Deprecated
   public Callback(Runnable var1) {
      this(var1x -> var1.run(), null);
   }

   public boolean isEmpty() {
      return this.success == null && this.failure == null;
   }

   public void succeed(Packet var1) {
      if (this.success != null) {
         this.success.accept(var1);
      }
   }

   public void fail(String var1) {
      if (this.failure != null) {
         this.failure.accept(var1);
      }
   }
}

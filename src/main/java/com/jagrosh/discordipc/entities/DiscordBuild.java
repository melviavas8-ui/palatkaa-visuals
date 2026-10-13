package com.jagrosh.discordipc.entities;

public enum DiscordBuild {
   CANARY("//canary.discordapp.com/api"),
   PTB("//ptb.discordapp.com/api"),
   STABLE("//discordapp.com/api"),
   ANY;

   private final String endpoint;

   private DiscordBuild(String var3) {
      this.endpoint = var3;
   }

   private DiscordBuild() {
      this(null);
   }

   public static DiscordBuild from(String var0) {
      for (DiscordBuild var4 : values()) {
         if (var4.endpoint != null && var4.endpoint.equals(var0)) {
            return var4;
         }
      }

      return ANY;
   }
}

package com.jagrosh.discordipc.entities;

import ua.daamky.utils.JsonArrayWrapper;
import ua.daamky.utils.JsonObjectWrapper;
import java.time.OffsetDateTime;

public class RichPresence {
   private final String state;
   private final String details;
   private final OffsetDateTime startTimestamp;
   private final OffsetDateTime endTimestamp;
   private final String largeImageKey;
   private final String largeImageText;
   private final String smallImageKey;
   private final String smallImageText;
   private final String partyId;
   private final int partySize;
   private final int partyMax;
   private final String matchSecret;
   private final String joinSecret;
   private final String spectateSecret;
   private final boolean instance;

   public RichPresence(
      String var1,
      String var2,
      OffsetDateTime var3,
      OffsetDateTime var4,
      String var5,
      String var6,
      String var7,
      String var8,
      String var9,
      int var10,
      int var11,
      String var12,
      String var13,
      String var14,
      boolean var15
   ) {
      this.state = var1;
      this.details = var2;
      this.startTimestamp = var3;
      this.endTimestamp = var4;
      this.largeImageKey = var5;
      this.largeImageText = var6;
      this.smallImageKey = var7;
      this.smallImageText = var8;
      this.partyId = var9;
      this.partySize = var10;
      this.partyMax = var11;
      this.matchSecret = var12;
      this.joinSecret = var13;
      this.spectateSecret = var14;
      this.instance = var15;
   }

   public JsonObjectWrapper toJson() {
      return new JsonObjectWrapper()
         .m141("state", this.state)
         .m141("details", this.details)
         .m144(
            "timestamps",
            new JsonObjectWrapper()
               .m142("start", this.startTimestamp == null ? null : this.startTimestamp.toEpochSecond())
               .m142("end", this.endTimestamp == null ? null : this.endTimestamp.toEpochSecond())
         )
         .m144(
            "assets",
            new JsonObjectWrapper()
               .m141("large_image", this.largeImageKey)
               .m141("large_text", this.largeImageText)
               .m141("small_image", this.smallImageKey)
               .m141("small_text", this.smallImageText)
         )
         .m144(
            "party",
            this.partyId == null
               ? null
               : new JsonObjectWrapper()
                  .m141("id", this.partyId)
                  .m145("size", new JsonArrayWrapper().m109(this.partySize).m109(this.partyMax))
         )
         .m144(
            "secrets",
            new JsonObjectWrapper()
               .m141("join", this.joinSecret)
               .m141("spectate", this.spectateSecret)
               .m141("match", this.matchSecret)
         )
         .m143("instance", this.instance);
   }

   public static class Builder {
      private String state;
      private String details;
      private OffsetDateTime startTimestamp;
      private OffsetDateTime endTimestamp;
      private String largeImageKey;
      private String largeImageText;
      private String smallImageKey;
      private String smallImageText;
      private String partyId;
      private int partySize;
      private int partyMax;
      private String matchSecret;
      private String joinSecret;
      private String spectateSecret;
      private boolean instance;

      public RichPresence build() {
         return new RichPresence(
            this.state,
            this.details,
            this.startTimestamp,
            this.endTimestamp,
            this.largeImageKey,
            this.largeImageText,
            this.smallImageKey,
            this.smallImageText,
            this.partyId,
            this.partySize,
            this.partyMax,
            this.matchSecret,
            this.joinSecret,
            this.spectateSecret,
            this.instance
         );
      }

      public RichPresence.Builder setState(String var1) {
         this.state = var1;
         return this;
      }

      public RichPresence.Builder setDetails(String var1) {
         this.details = var1;
         return this;
      }

      public RichPresence.Builder setStartTimestamp(OffsetDateTime var1) {
         this.startTimestamp = var1;
         return this;
      }

      public RichPresence.Builder setEndTimestamp(OffsetDateTime var1) {
         this.endTimestamp = var1;
         return this;
      }

      public RichPresence.Builder setLargeImage(String var1, String var2) {
         this.largeImageKey = var1;
         this.largeImageText = var2;
         return this;
      }

      public RichPresence.Builder setLargeImage(String var1) {
         return this.setLargeImage(var1, null);
      }

      public RichPresence.Builder setSmallImage(String var1, String var2) {
         this.smallImageKey = var1;
         this.smallImageText = var2;
         return this;
      }

      public RichPresence.Builder setSmallImage(String var1) {
         return this.setSmallImage(var1, null);
      }

      public RichPresence.Builder setParty(String var1, int var2, int var3) {
         this.partyId = var1;
         this.partySize = var2;
         this.partyMax = var3;
         return this;
      }

      public RichPresence.Builder setMatchSecret(String var1) {
         this.matchSecret = var1;
         return this;
      }

      public RichPresence.Builder setJoinSecret(String var1) {
         this.joinSecret = var1;
         return this;
      }

      public RichPresence.Builder setSpectateSecret(String var1) {
         this.spectateSecret = var1;
         return this;
      }

      public RichPresence.Builder setInstance(boolean var1) {
         this.instance = var1;
         return this;
      }
   }
}

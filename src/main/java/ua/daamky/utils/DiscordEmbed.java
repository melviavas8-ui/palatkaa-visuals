package ua.daamky.utils;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class DiscordEmbed {
   public String f1;
   public String f2;
   public String f3;
   public Color f4;
   private DiscordEmbedFooter f5;
   private DiscordEmbedThumbnail f6;
   private DiscordEmbedImage f7;
   private DiscordEmbedAuthor f8;
   private final List<DiscordEmbedField> f9 = new ArrayList<>();

   public String m37() {
      return this.f1;
   }

   public String m40() {
      return this.f2;
   }

   public String m18() {
      return this.f3;
   }

   public Color m42() {
      return this.f4;
   }

   public DiscordEmbedFooter m43() {
      return this.f5;
   }

   public DiscordEmbedThumbnail m44() {
      return this.f6;
   }

   public DiscordEmbedImage m45() {
      return this.f7;
   }

   public DiscordEmbedAuthor m46() {
      return this.f8;
   }

   public List<DiscordEmbedField> m47() {
      return this.f9;
   }

   public DiscordEmbed m48(String var1) {
      this.f1 = var1;
      return this;
   }

   public DiscordEmbed m49(String var1) {
      this.f2 = var1;
      return this;
   }

   public DiscordEmbed m50(String var1) {
      this.f3 = var1;
      return this;
   }

   public DiscordEmbed m51(Color var1) {
      this.f4 = var1;
      return this;
   }

   public DiscordEmbed m52(String var1, String var2) {
      this.f5 = new DiscordEmbedFooter(var1, var2);
      return this;
   }

   public DiscordEmbed m53(String var1) {
      this.f6 = new DiscordEmbedThumbnail(var1);
      return this;
   }

   public DiscordEmbed m54(String var1) {
      this.f7 = new DiscordEmbedImage(var1);
      return this;
   }

   public DiscordEmbed m55(String var1, String var2, String var3) {
      this.f8 = new DiscordEmbedAuthor(var1, var2, var3);
      return this;
   }

   public DiscordEmbed m56(String var1, String var2, boolean var3) {
      this.f9.add(new DiscordEmbedField(var1, var2, var3));
      return this;
   }
}

package ua.daamky.gui;

import ua.daamky.gui.theme.ThemeManager;
import ua.daamky.utils.EasingUtil;
import ua.daamky.utils.ScissorUtil;
import ua.daamky.utils.DaamkyChatApi;
import ua.daamky.utils.ChatChannel;
import ua.daamky.utils.ChatMessage;
import ua.daamky.utils.ChatUser;
import ua.daamky.utils.ChatPresence;
import ua.daamky.utils.client.UserProfile;
import ua.daamky.utils.render.Render2DUtil;
import ua.daamky.utils.render.TextureCache;
import ua.daamky.utils.render.fonts.FontAtlas;
import ua.daamky.utils.render.fonts.FontRenderUtil;
import java.awt.Color;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.input.CharInput;
import net.minecraft.client.input.KeyInput;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

public class MessengerScreen extends Screen {
   private static final Color f1 = new Color(8, 8, 11);
   private static final Color f2 = new Color(12, 12, 15);
   private static final Color f3 = new Color(20, 20, 25);
   private static final Color f4 = new Color(19, 19, 21);
   private static final Color f5 = new Color(29, 29, 33);
   private static final Color f6 = new Color(113, 113, 123, 64);
   private static final Color f7 = new Color(113, 113, 123, 26);
   private static final Color f8 = new Color(231, 231, 253);
   private static final Color f9 = new Color(113, 113, 123);
   private static final Color f10 = new Color(90, 200, 96);
   private static final Color f11 = new Color(214, 102, 102);
   private float f12;
   private float f13;
   private final float f14 = 500.0F;
   private final float f15 = 320.0F;
   private static final float f16 = 7.0F;
   private static final float f17 = 138.0F;
   private static final float f18 = 12.0F;
   private static final float f19 = 10.0F;
   private static final float f20 = 7.0F;
   private static final float f21 = 5.0F;
   private static final float f22 = 8.0F;
   private static final float f23 = 3.0F;
   private static final float f24 = 32.0F;
   private static final float f25 = 28.0F;
   private static final float f26 = 22.0F;
   private static final float f27 = 18.0F;
   private static final float f28 = 18.0F;
   private static final float f29 = 18.0F;
   private MessengerScreen$2 f30 = MessengerScreen$2.f1;
   private MessengerScreen$1 f31 = MessengerScreen$1.f1;
   private String f32 = null;
   private final List<ChatMessage> f33 = new ArrayList<>();
   private final List<ChatMessage> f34 = new ArrayList<>();
   private final List<ChatChannel> f35 = new ArrayList<>();
   private final List<ChatUser> f36 = new ArrayList<>();
   private boolean f37 = false;
   private long f38 = 0L;
   private String f39 = "";
   private String f40 = "";
   private String f41 = "";
   private boolean f42 = true;
   private boolean f43 = false;
   private String f44 = "";
   private boolean f45 = false;
   private float f46 = 0.0F;
   private float f47 = 0.0F;
   private float f48 = 0.0F;
   private boolean f49 = true;
   private ChatPresence f50 = null;
   private long f51 = 0L;
   private long f52 = 0L;
   private boolean f53 = false;
   private long f54 = System.nanoTime();
   private float f55 = 0.0F;
   private float f56 = 0.0F;
   private float f57 = 0.0F;
   private Color f58 = new Color(140, 130, 255);
   private final Map<String, Float> f59 = new HashMap<>();
   private final Map<Long, Float> f60 = new HashMap<>();
   private final List<float[]> f61 = new ArrayList<>();
   private final List<float[]> f62 = new ArrayList<>();
   private final List<Object[]> f63 = new ArrayList<>();
   private float f64;
   private float[] f65 = null;

   public MessengerScreen() {
      super(Text.literal("Messenger"));
   }

   private boolean m91() {
      String var1 = UserProfile.m30();
      return "dev".equalsIgnoreCase(var1)
         || "admin".equalsIgnoreCase(var1)
         || "mod".equalsIgnoreCase(var1);
   }

   protected void init() {
      this.f39 = UserProfile.m58("");
      this.f54 = System.nanoTime();
      if (DaamkyChatApi.m91()) {
         this.m61(true);
         DaamkyChatApi.m909(var1 -> {
            this.f35.clear();
            this.f35.addAll(var1);
         }, var0 -> {
         });
      } else {
         this.m942(
            "Войдите через лоадер, чтобы пользоваться чатом",
            false
         );
      }
   }

   public void renderBackground(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
   }

   public boolean shouldPause() {
      return false;
   }

   private void m942(String var1, boolean var2) {
      this.f44 = var1;
      this.f45 = var2;
   }

   private void m61(boolean var1) {
      if (DaamkyChatApi.m91() && !this.f53) {
         this.f53 = true;
         this.f51 = System.currentTimeMillis();
         String var2 = this.f30 == MessengerScreen$2.f2 ? this.f32 : null;
         DaamkyChatApi.m906(
            this.f30 == MessengerScreen$2.f2 ? "dm" : "global",
            var2,
            var2x -> {
               this.f53 = false;
               boolean var3 = this.f49 || var1;
               this.f33.clear();
               this.f33.addAll(var2x);
               long var4 = System.currentTimeMillis();
               this.f34
                  .removeIf(var3x -> var2x.stream().anyMatch(var1xxx -> var1xxx.m905() && var1xxx.m38().equals(var3x.m38())) || var4 + var3x.m904() > 5000L);
               this.f33.addAll(this.f34);
               if (var3) {
                  this.f47 = 0.0F;
               }

               if (!"Войдите через лоадер, чтобы пользоваться чатом"
                  .equals(this.f44)) {
                  this.f44 = "";
               }
            },
            var1x -> {
               this.f53 = false;
               this.m942(this.m58(var1x), false);
            }
         );
      }
   }

   private String m58(String var1) {
      return switch (var1) {
         case "no_sub" -> "Нужна активная подписка";
         case "unauthorized" -> "Сессия не найдена — перезайдите через лоадер";
         case "banned" -> "Аккаунт заблокирован";
         case "muted" -> "Вы в муте";
         case "slow" -> "Слишком часто — подождите секунду";
         case "dup" -> "Не повторяйте одно и то же";
         case "network" -> "Нет связи с сервером";
         case "empty" -> "Пустое сообщение";
         case "no_user" -> "Пользователь не найден";
         default -> var1;
      };
   }

   private float m334(float var1, float var2, float var3) {
      return var1 + (var2 - var1) * (1.0F - (float)Math.exp((double)(-var3 * this.f64)));
   }

   private float m943(String var1, boolean var2) {
      float var3 = this.m334(this.f59.getOrDefault(var1, 0.0F), var2 ? 1.0F : 0.0F, 16.0F);
      this.f59.put(var1, var3);
      return var3;
   }

   private static Color m944(Color var0, Color var1, float var2) {
      var2 = Math.max(0.0F, Math.min(1.0F, var2));
      return new Color(
         (int)((float)var0.getRed() + (float)(var1.getRed() - var0.getRed()) * var2),
         (int)((float)var0.getGreen() + (float)(var1.getGreen() - var0.getGreen()) * var2),
         (int)((float)var0.getBlue() + (float)(var1.getBlue() - var0.getBlue()) * var2),
         (int)((float)var0.getAlpha() + (float)(var1.getAlpha() - var0.getAlpha()) * var2)
      );
   }

   private static Color m336(Color var0, float var1) {
      return new Color(var0.getRed(), var0.getGreen(), var0.getBlue(), (int)Math.max(0.0F, Math.min(255.0F, (float)var0.getAlpha() * var1)));
   }

   private static Color m945(Color var0) {
      return m944(var0, Color.WHITE, 0.22F);
   }

   public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
      long var5 = System.nanoTime();
      this.f64 = Math.min(0.05F, (float)(var5 - this.f54) / 1.0E9F);
      this.f54 = var5;
      long var7 = System.currentTimeMillis();
      if (DaamkyChatApi.m91()) {
         if (var7 - this.f51 > 400L) {
            this.m61(false);
         }

         if (var7 - this.f52 > 4000L) {
            this.f52 = var7;
            DaamkyChatApi.m909(var1 -> {
               this.f35.clear();
               this.f35.addAll(var1);
            }, var0 -> {
            });
         }

         if (var7 - this.f38 > 5000L) {
            this.f38 = var7;
            DaamkyChatApi.m910(var1 -> {
               this.f36.clear();
               this.f36.addAll(var1);
            }, var1 -> this.f37 = var1, var0 -> {
            });
         }
      }

      this.f55 = this.m334(this.f55, 1.0F, 12.0F);
      this.f56 = this.m334(this.f56, this.f50 != null ? 1.0F : 0.0F, 16.0F);
      this.f57 = this.m334(this.f57, this.f42 ? 1.0F : 0.0F, 14.0F);
      this.f58 = ThemeManager.m1379();
      float var9 = EasingUtil.m151(this.f55);
      float var10 = (float)Render2DUtil.m113();
      float var11 = (float)Render2DUtil.m189();
      this.f12 = (var10 - 500.0F) / 2.0F;
      this.f13 = (var11 - 320.0F) / 2.0F + (1.0F - var9) * 16.0F;
      float var12 = Render2DUtil.m3((float)mouseX);
      float var13 = Render2DUtil.m151((float)mouseY);
      this.f61.clear();
      this.f62.clear();
      this.f63.clear();
      Render2DUtil.m195(0.0F, 0.0F, var10, var11, 0.0F, m336(new Color(0, 0, 0), 0.42F * var9));
      Render2DUtil.m218(this.f12, this.f13, 500.0F, 320.0F, 12.0F, m336(new Color(0, 0, 0, 130), var9));
      Render2DUtil.m195(this.f12, this.f13, 500.0F, 320.0F, 12.0F, m336(f1, var9));
      Render2DUtil.m202(this.f12, this.f13, 500.0F, 320.0F, 12.0F, 0.9F, m336(f6, var9));
      float var14 = this.f12 + 7.0F;
      float var15 = this.f13 + 7.0F;
      float var16 = 306.0F;
      float var17 = this.f12 + 7.0F + 138.0F + 7.0F;
      float var18 = this.f13 + 7.0F;
      float var19 = 341.0F;
      float var20 = 306.0F;
      Render2DUtil.m195(var14, var15, 138.0F, var16, 10.0F, m336(f2, var9));
      Render2DUtil.m202(var14, var15, 138.0F, var16, 10.0F, 0.8F, m336(f7, var9));
      Render2DUtil.m195(var17, var18, var19, var20, 10.0F, m336(f2, var9));
      Render2DUtil.m202(var17, var18, var19, var20, 10.0F, 0.8F, m336(f7, var9));
      this.m946(context, var12, var13, var14, var15, 138.0F, var16);
      this.m951(context, var12, var13, var17, var18, var19, var20);
      if (this.f56 > 0.01F && this.f50 != null) {
         this.m954(context, var12, var13);
      }
   }

   private void m946(DrawContext var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      float var8 = var4 + 8.0F;
      float var9 = var6 - 16.0F;
      float var10 = 13.0F;
      Render2DUtil.m208(var1, FontRenderUtil.f3, var8, var5 + 7.0F, "A", var10, this.f58);
      float var11 = FontRenderUtil.m237(FontRenderUtil.f3, "A", var10) + 5.0F;
      Render2DUtil.m208(var1, FontRenderUtil.f2, var8 + var11, var5 + 8.0F, "Daamky", 12.0F, f8);
      Render2DUtil.m208(var1, FontRenderUtil.f1, var8 + var11, var5 + 20.0F, "мессенджер", 7.5F, f9);
      if (!this.f39.isEmpty()) {
         Render2DUtil.m195(var4 + var6 - 14.0F, var5 + 11.0F, 5.0F, 5.0F, 2.5F, this.f37 ? f9 : f10);
      }

      float var12 = var5 + 30.0F;
      this.m947(var1, var8, var12, var9, var2, var3);
      float var13 = var12 + 18.0F + 6.0F;
      float var14 = (var9 - 4.0F) / 2.0F;
      this.m948(
         var1,
         var8,
         var13,
         var14,
         "P",
         "Чаты",
         this.f31 == MessengerScreen$1.f1,
         var2,
         var3,
         () -> this.f31 = MessengerScreen$1.f1
      );
      this.m948(
         var1,
         var8 + var14 + 4.0F,
         var13,
         var14,
         "N",
         "Онлайн",
         this.f31 == MessengerScreen$1.f2,
         var2,
         var3,
         () -> this.f31 = MessengerScreen$1.f2
      );
      float var15 = var13 + 18.0F + 8.0F;
      float var16 = var5 + var7 - 8.0F - 20.0F;
      float var17 = var16 - 8.0F;
      boolean var18 = this.f30 == MessengerScreen$2.f1;
      this.m949(
         var1,
         var8,
         var15,
         var9,
         "Общий чат",
         this.f36.size() + " в сети",
         var18,
         var2,
         var3,
         "grow",
         this::m314
      );
      float var19 = var15 + 28.0F + 2.0F;
      ScissorUtil.m357((double)(var4 + 4.0F), (double)var19, (double)(var6 - 8.0F), (double)Math.max(0.0F, var17 - var19));
      String var20 = this.f41.trim().toLowerCase();
      if (this.f31 == MessengerScreen$1.f1) {
         int var21 = 0;

         for (int var22 = 0; var22 < this.f35.size(); var22++) {
            ChatChannel var23 = this.f35.get(var22);
            if (var20.isEmpty() || var23.m37().toLowerCase().contains(var20)) {
               boolean var24 = this.f30 == MessengerScreen$2.f2 && var23.m37().equalsIgnoreCase(this.f32);
               this.m950(
                  var1,
                  var8,
                  var19,
                  var9,
                  var23.m37(),
                  var23.m37(),
                  (var23.m101() ? "вы: " : "") + var23.m40(),
                  var24,
                  var2,
                  var3,
                  "ib" + var22,
                  () -> this.m21(var23.m37())
               );
               var19 += 30.0F;
               var21++;
            }
         }

         if (var21 == 0) {
            Render2DUtil.m208(
               var1,
               FontRenderUtil.f1,
               var8 + 2.0F,
               var19 + 2.0F,
               var20.isEmpty() ? "пока пусто" : "не найдено",
               8.0F,
               f9
            );
         }
      } else {
         int var25 = 0;

         for (int var27 = 0; var27 < this.f36.size(); var27++) {
            ChatUser var28 = this.f36.get(var27);
            if (var20.isEmpty() || var28.m37().toLowerCase().contains(var20)) {
               String var29 = var28.m37() + (var28.m101() ? " (вы)" : "");
               this.m950(var1, var8, var19, var9, var28.m37(), var29, "в сети", false, var2, var3, "on" + var27, () -> {
                  if (!var28.m101()) {
                     this.m60(var28.m37());
                  }
               });
               var19 += 30.0F;
               var25++;
            }
         }

         if (var25 == 0) {
            Render2DUtil.m208(
               var1,
               FontRenderUtil.f1,
               var8 + 2.0F,
               var19 + 2.0F,
               var20.isEmpty() ? "никого нет" : "не найдено",
               8.0F,
               f9
            );
         }
      }

      ScissorUtil.m29();
      float var26 = this.m943("vis", m35(var2, var3, var8, var16, var9, 20.0F));
      Render2DUtil.m195(var8, var16, var9, 20.0F, 7.0F, m944(f4, f5, var26));
      Render2DUtil.m195(var8 + 8.0F, var16 + 7.5F, 5.0F, 5.0F, 2.5F, this.f37 ? f9 : f10);
      Render2DUtil.m208(
         var1,
         FontRenderUtil.f1,
         var8 + 18.0F,
         var16 + 10.0F - FontRenderUtil.m239(FontRenderUtil.f1, 8.5F) / 2.0F,
         this.f37 ? "Скрыт" : "В сети",
         8.5F,
         this.f37 ? f9 : f10
      );
      Render2DUtil.m219(
         var1,
         FontRenderUtil.f1,
         var8 + var9 - 8.0F,
         var16 + 10.0F - FontRenderUtil.m239(FontRenderUtil.f1, 8.0F) / 2.0F,
         this.f37 ? "показаться" : "скрыться",
         8.0F,
         f9,
         "right"
      );
      this.m959(var8, var16, var9, 20.0F, () -> DaamkyChatApi.m911(!this.f37, () -> this.f37 = !this.f37, var0 -> {
         }));
   }

   private void m947(DrawContext var1, float var2, float var3, float var4, float var5, float var6) {
      boolean var7 = this.f43;
      Render2DUtil.m195(var2, var3, var4, 18.0F, 5.0F, m944(f4, f5, var7 ? 1.0F : 0.0F));
      if (var7) {
         Render2DUtil.m202(var2, var3, var4, 18.0F, 5.0F, 0.8F, m336(this.f58, 0.8F));
      }

      float var8 = 8.5F;
      Render2DUtil.m208(
         var1,
         FontRenderUtil.f3,
         var2 + 6.0F,
         var3 + 9.0F - FontRenderUtil.m239(FontRenderUtil.f3, var8) / 2.0F,
         "V",
         var8,
         var7 ? m944(f9, this.f58, 0.7F) : f9
      );
      String var9 = this.f41.isEmpty() ? "Поиск" : m962(this.f41, FontRenderUtil.f1, 8.5F, var4 - 26.0F);
      Render2DUtil.m208(
         var1, FontRenderUtil.f1, var2 + 17.0F, var3 + 9.0F - FontRenderUtil.m239(FontRenderUtil.f1, 8.5F) / 2.0F, var9, 8.5F, this.f41.isEmpty() ? f9 : f8
      );
      this.m959(var2, var3, var4, 18.0F, () -> {
         this.f43 = true;
         this.f42 = false;
      });
   }

   private void m948(DrawContext var1, float var2, float var3, float var4, String var5, String var6, boolean var7, float var8, float var9, Runnable var10) {
      float var11 = this.m334(this.f59.getOrDefault("ta_" + var6, 0.0F), var7 ? 1.0F : 0.0F, 14.0F);
      this.f59.put("ta_" + var6, var11);
      float var12 = this.m943("st_" + var6, m35(var8, var9, var2, var3, var4, 18.0F));
      Render2DUtil.m195(var2, var3, var4, 18.0F, 5.0F, m944(m944(f4, f5, var12), m336(this.f58, 0.92F), var11));
      Color var13 = m944(m944(f9, f8, var12), Color.WHITE, var11);
      float var14 = 8.0F;
      float var15 = FontRenderUtil.m237(FontRenderUtil.f3, var5, var14);
      float var16 = FontRenderUtil.m237(FontRenderUtil.f2, var6, 8.5F);
      float var17 = var2 + (var4 - (var15 + 3.0F + var16)) / 2.0F;
      Render2DUtil.m208(var1, FontRenderUtil.f3, var17, var3 + 9.0F - FontRenderUtil.m239(FontRenderUtil.f3, var14) / 2.0F, var5, var14, var13);
      Render2DUtil.m208(var1, FontRenderUtil.f2, var17 + var15 + 3.0F, var3 + 9.0F - FontRenderUtil.m239(FontRenderUtil.f2, 8.5F) / 2.0F, var6, 8.5F, var13);
      this.m959(var2, var3, var4, 18.0F, var10);
   }

   private void m949(
      DrawContext var1, float var2, float var3, float var4, String var5, String var6, boolean var7, float var8, float var9, String var10, Runnable var11
   ) {
      float var12 = this.m943(var10, m35(var8, var9, var2, var3, var4, 28.0F));
      Render2DUtil.m195(var2, var3, var4, 28.0F, 7.0F, var7 ? m336(this.f58, 0.16F) : m944(f4, f5, var12));
      if (var7) {
         Render2DUtil.m195(var2, var3 + 5.0F, 2.5F, 18.0F, 1.25F, this.f58);
      }

      Render2DUtil.m195(var2 + 5.0F, var3 + 5.0F, 18.0F, 18.0F, 9.0F, m336(this.f58, 0.85F));
      Render2DUtil.m219(
         var1,
         FontRenderUtil.f3,
         var2 + 5.0F + 9.0F,
         var3 + 14.0F - FontRenderUtil.m239(FontRenderUtil.f3, 9.36F) / 2.0F,
         "E",
         9.36F,
         Color.WHITE,
         "center"
      );
      Render2DUtil.m208(
         var1,
         FontRenderUtil.f2,
         var2 + 28.0F,
         var3 + 6.0F,
         m962(var5, FontRenderUtil.f2, 9.0F, var4 - 32.0F),
         9.0F,
         var7 ? f8 : m944(f8, Color.WHITE, var12 * 0.4F)
      );
      Render2DUtil.m208(var1, FontRenderUtil.f1, var2 + 28.0F, var3 + 17.0F, m962(var6, FontRenderUtil.f1, 8.0F, var4 - 32.0F), 8.0F, f9);
      this.m959(var2, var3, var4, 28.0F, var11);
   }

   private void m950(
      DrawContext var1,
      float var2,
      float var3,
      float var4,
      String var5,
      String var6,
      String var7,
      boolean var8,
      float var9,
      float var10,
      String var11,
      Runnable var12
   ) {
      float var13 = this.m943(var11, m35(var9, var10, var2, var3, var4, 28.0F));
      Render2DUtil.m195(var2, var3, var4, 28.0F, 7.0F, var8 ? m336(this.f58, 0.16F) : m944(f4, f5, var13));
      if (var8) {
         Render2DUtil.m195(var2, var3 + 5.0F, 2.5F, 18.0F, 1.25F, this.f58);
      }

      this.m958(var1, var2 + 5.0F, var3 + 5.0F, 18.0F, var5);
      Render2DUtil.m208(
         var1,
         FontRenderUtil.f2,
         var2 + 28.0F,
         var3 + 6.0F,
         m962(var6, FontRenderUtil.f2, 9.0F, var4 - 32.0F),
         9.0F,
         var8 ? f8 : m944(f8, Color.WHITE, var13 * 0.4F)
      );
      Render2DUtil.m208(var1, FontRenderUtil.f1, var2 + 28.0F, var3 + 17.0F, m962(var7, FontRenderUtil.f1, 8.0F, var4 - 32.0F), 8.0F, f9);
      this.m959(var2, var3, var4, 28.0F, var12);
   }

   private void m951(DrawContext var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      boolean var8 = this.f30 == MessengerScreen$2.f2;
      String var9 = var8 ? this.f32 : "Общий чат";
      String var10 = var8 ? "личные сообщения" : this.f36.size() + " в сети";
      if (var8) {
         this.m958(var1, var4 + 10.0F, var5 + 7.0F, 18.0F, this.f32);
      } else {
         Render2DUtil.m195(var4 + 10.0F, var5 + 7.0F, 18.0F, 18.0F, 9.0F, m336(this.f58, 0.85F));
         Render2DUtil.m219(
            var1,
            FontRenderUtil.f3,
            var4 + 10.0F + 9.0F,
            var5 + 16.0F - FontRenderUtil.m239(FontRenderUtil.f3, 9.36F) / 2.0F,
            "E",
            9.36F,
            Color.WHITE,
            "center"
         );
      }

      Render2DUtil.m208(var1, FontRenderUtil.f2, var4 + 34.0F, var5 + 8.0F, m962(var9, FontRenderUtil.f2, 11.0F, var6 - 120.0F), 11.0F, var8 ? f8 : f8);
      Render2DUtil.m208(var1, FontRenderUtil.f1, var4 + 34.0F, var5 + 20.0F, var10, 8.0F, f9);
      if (var8) {
         float var11 = 52.0F;
         float var12 = var4 + var6 - var11 - 10.0F;
         float var13 = var5 + 16.0F - 8.0F;
         float var14 = this.m943("back", m35(var2, var3, var12, var13, var11, 16.0F));
         Render2DUtil.m195(var12, var13, var11, 16.0F, 5.0F, m944(f4, f5, var14));
         this.m956(var12 + 9.0F - var14 * 2.0F, var13 + 8.0F, 6.0F, f8);
         Render2DUtil.m208(
            var1,
            FontRenderUtil.f1,
            var12 + 18.0F,
            var13 + 8.0F - FontRenderUtil.m239(FontRenderUtil.f1, 8.5F) / 2.0F,
            "назад",
            8.5F,
            f8
         );
         this.m959(var12, var13, var11, 16.0F, this::m314);
         this.m959(var4 + 8.0F, var5, 120.0F, 32.0F, () -> this.m60(this.f32));
      }

      Render2DUtil.m195(var4 + 8.0F, var5 + 32.0F, var6 - 16.0F, 0.8F, 0.0F, f7);
      float var36 = var5 + var7 - 8.0F - 22.0F;
      float var37 = var5 + 32.0F + 8.0F;
      float var38 = var36 - var37 - 8.0F;
      float var39 = 8.0F;
      float var15 = 5.0F;
      float var16 = 11.0F;
      float var17 = 9.5F;
      float var18 = 6.0F;
      float var19 = 12.0F;
      float var20 = 8.0F;
      float var21 = var6 * 0.74F;
      float var22 = var21 - var39 * 2.0F;
      ArrayList var23 = new ArrayList();
      ArrayList var24 = new ArrayList();
      ArrayList var25 = new ArrayList();
      float var26 = 0.0F;

      for (int var27 = 0; var27 < this.f33.size(); var27++) {
         ChatMessage var28 = this.f33.get(var27);
         List<String> var29 = m960(var28.m38(), FontRenderUtil.f1, var17, var22);
         if (var29.isEmpty()) {
            var29.add("");
         }

         float var30 = 0.0F;

         for (String var32 : var29) {
            var30 = Math.max(var30, FontRenderUtil.m237(FontRenderUtil.f1, var32, var17));
         }

         float var50 = FontRenderUtil.m237(FontRenderUtil.f1, m65(var28.m39()), 7.0F);
         float var53 = Math.max(var30, var50 + 6.0F) + var39 * 2.0F;
         boolean var33 = !var28.m905() && (var27 == 0 || !m953(this.f33.get(var27 - 1), var28));
         var23.add(var29);
         var24.add(var53);
         var25.add(var33);
         var26 += (var33 ? var19 : 0.0F) + (float)var29.size() * var16 + var15 * 2.0F + var20 + var18;
      }

      this.f48 = Math.max(0.0F, var26 - var38);
      this.f47 = Math.max(0.0F, Math.min(this.f47, this.f48));
      this.f46 = this.m334(this.f46, this.f47, 18.0F);
      this.f46 = Math.max(0.0F, Math.min(this.f46, this.f48));
      this.f49 = this.f47 <= 0.5F;
      ScissorUtil.m357((double)(var4 + 6.0F), (double)var37, (double)(var6 - 12.0F), (double)var38);
      float var40 = var37 + var38 - var26 + this.f46;

      for (int var41 = 0; var41 < this.f33.size(); var41++) {
         ChatMessage var44 = this.f33.get(var41);
         List var47 = (List)var23.get(var41);
         boolean var51 = (Boolean)var25.get(var41);
         float var54 = (Float)var24.get(var41);
         float var56 = (var51 ? var19 : 0.0F) + (float)var47.size() * var16 + var15 * 2.0F + var20;
         if (var40 + var56 >= var37 - 6.0F && var40 <= var37 + var38 + 6.0F) {
            this.m952(var1, var44, var41, var4, var40, var6, var54, var47, var51, var39, var15, var16, var17, var19, var20, var2, var3, var37, var38);
         }

         var40 += var56 + var18;
      }

      ScissorUtil.m29();
      if (this.f60.size() > 300) {
         HashSet var42 = new HashSet();

         for (ChatMessage var48 : this.f33) {
            var42.add(var48.m904());
         }

         this.f60.keySet().retainAll(var42);
      }

      if (var26 < 1.0F && DaamkyChatApi.m91() && this.f44.isEmpty()) {
         Render2DUtil.m219(
            var1,
            FontRenderUtil.f1,
            var4 + var6 / 2.0F,
            var37 + var38 / 2.0F - FontRenderUtil.m239(FontRenderUtil.f1, 9.5F) / 2.0F,
            var8
               ? "Начните переписку"
               : "Пока никто не писал — будьте первым",
            9.5F,
            f9,
            "center"
         );
      }

      if (!this.f44.isEmpty()) {
         Render2DUtil.m208(var1, FontRenderUtil.f1, var4 + 10.0F, var36 - 12.0F, this.f44, 8.5F, this.f45 ? f10 : f11);
      }

      boolean var43 = DaamkyChatApi.m91();
      float var46 = 46.0F;
      float var49 = var4 + 8.0F;
      float var52 = var6 - 16.0F - var46 - 6.0F;
      float var55 = var36 + 11.0F - FontRenderUtil.m239(FontRenderUtil.f1, 9.5F) / 2.0F;
      Render2DUtil.m195(var49, var36, var52, 22.0F, 5.0F, m944(f4, f5, 0.3F + 0.7F * this.f57));
      if (this.f57 > 0.01F) {
         Render2DUtil.m202(var49, var36, var52, 22.0F, 5.0F, 0.9F, m336(this.f58, this.f57));
      }

      String var57 = m962(this.f40, FontRenderUtil.f1, 9.5F, var52 - 22.0F);
      Render2DUtil.m208(
         var1,
         FontRenderUtil.f1,
         var49 + 9.0F,
         var55,
         this.f40.isEmpty() ? "Сообщение..." : var57,
         9.5F,
         this.f40.isEmpty() ? f9 : f8
      );
      if (this.f42 && var43 && System.currentTimeMillis() % 1000L < 530L) {
         float var34 = this.f40.isEmpty() ? 0.0F : FontRenderUtil.m237(FontRenderUtil.f1, var57, 9.5F);
         Render2DUtil.m195(var49 + 9.0F + var34 + 1.0F, var36 + 5.0F, 1.0F, 12.0F, 0.5F, m336(this.f58, 0.9F));
      }

      this.m959(var49, var36, var52, 22.0F, () -> {
         this.f42 = true;
         this.f43 = false;
      });
      float var58 = var49 + var52 + 6.0F;
      float var35 = this.m943("send", m35(var2, var3, var58, var36, var46, 22.0F));
      Render2DUtil.m195(var58, var36, var46, 22.0F, 5.0F, var43 ? m944(this.f58, m945(this.f58), var35) : f4);
      this.m211(var58 + var46 / 2.0F - 5.0F + var35 * 2.0F, var36 + 11.0F, 9.0F, var43 ? Color.WHITE : f9);
      if (var43) {
         this.m959(var58, var36, var46, 22.0F, this::m29);
      }
   }

   private void m952(
      DrawContext var1,
      ChatMessage var2,
      int var3,
      float var4,
      float var5,
      float var6,
      float var7,
      List<String> var8,
      boolean var9,
      float var10,
      float var11,
      float var12,
      float var13,
      float var14,
      float var15,
      float var16,
      float var17,
      float var18,
      float var19
   ) {
      boolean var20 = var2.m905();
      float var21 = 24.0F;
      float var22 = this.m957(var2.m904());
      float var23 = (1.0F - var22) * 7.0F;
      float var25 = var5 + (var9 ? var14 : 0.0F) + var23;
      float var26 = (float)var8.size() * var12 + var11 * 2.0F + var15;
      float var27 = var20 ? var4 + var6 - 8.0F - var7 : var4 + 8.0F + var21;
      if (!var20 && var9) {
         this.m958(var1, var4 + 8.0F, var25, 18.0F, var2.m40());
         Color var28 = m336(m963(var2.m30()), var22);
         Render2DUtil.m208(var1, FontRenderUtil.f2, var27, var5 + 1.0F + var23, var2.m40(), 8.5F, var28);
         float var29 = FontRenderUtil.m237(FontRenderUtil.f2, var2.m40(), 8.5F);
         float var30 = var27 + var29 + 4.0F;
         String var31 = m383(var2.m30());
         if (!var31.isEmpty()) {
            float var32 = FontRenderUtil.m237(FontRenderUtil.f2, var31, 6.0F) + 6.0F;
            Render2DUtil.m195(var30, var5 + var23, var32, 9.0F, 3.0F, m336(m963(var2.m30()), 0.9F * var22));
            Render2DUtil.m219(
               var1,
               FontRenderUtil.f2,
               var30 + var32 / 2.0F,
               var5 + 1.5F + var23,
               var31,
               6.0F,
               m336(Color.WHITE, var22),
               "center"
            );
         }

         this.f61.add(new float[]{var4 + 8.0F, var5 + var23, var29 + 18.0F + 8.0F, var14 + 4.0F, (float)var3});
      }

      Color var38 = var20 ? m336(this.f58, 0.92F * var22) : m336(f4, var22);
      Color var39 = var20 ? m336(Color.WHITE, var22) : m336(f8, var22);
      if (var20) {
         Render2DUtil.m194(var27, var25, var7, var26, 8.0F, 8.0F, 3.0F, 8.0F, var38);
      } else {
         Render2DUtil.m194(var27, var25, var7, var26, 8.0F, 8.0F, 8.0F, 3.0F, var38);
      }

      boolean var40 = m35(var16, var17, var27, var25, var7, var26) && var17 >= var18 && var17 <= var18 + var19;
      float var41 = var25 + var11;

      for (String var33 : var8) {
         Render2DUtil.m208(var1, FontRenderUtil.f1, var27 + var10, var41, var33, var13, var39);
         var41 += var12;
      }

      String var43 = m65(var2.m39());
      if (!var43.isEmpty()) {
         Render2DUtil.m219(
            var1,
            FontRenderUtil.f1,
            var27 + var7 - var10,
            var25 + var26 - var11 - 7.0F,
            var43,
            7.0F,
            var20 ? m336(Color.WHITE, 0.7F * var22) : m336(f9, var22),
            "right"
         );
      }

      if (this.m91() && var22 > 0.6F) {
         float var44 = var20 ? var27 - 16.0F : var27 + var7 + 2.0F;
         float var34 = var25 + var26 / 2.0F - 7.0F;
         boolean var35 = m35(var16, var17, var44 - 2.0F, var34 - 2.0F, 18.0F, 18.0F);
         float var36 = this.m943("del" + var2.m904(), var35);
         float var37 = Math.max(var40 ? 0.55F : 0.0F, var36);
         if (var37 > 0.02F) {
            Render2DUtil.m219(
               var1,
               FontRenderUtil.f3,
               var44 + 7.0F,
               var34 + 7.0F - FontRenderUtil.m239(FontRenderUtil.f3, 9.0F) / 2.0F,
               "S",
               9.0F,
               m336(m944(f9, f11, var36), var37),
               "center"
            );
         }

         this.f62.add(new float[]{var44 - 2.0F, var34 - 2.0F, 18.0F, 18.0F, (float)var2.m904()});
      }
   }

   private static boolean m953(ChatMessage var0, ChatMessage var1) {
      return var0.m905() == var1.m905() && (var0.m40() == null ? var1.m40() == null : var0.m40().equalsIgnoreCase(var1.m40()));
   }

   private void m954(DrawContext var1, float var2, float var3) {
      float var4 = 240.0F;
      float var5 = 190.0F;
      float var6 = EasingUtil.m3(Math.min(1.0F, this.f56));
      float var7 = this.f12 + (500.0F - var4) / 2.0F;
      float var8 = this.f13 + (320.0F - var5) / 2.0F + (1.0F - var6) * 12.0F;
      Color var9 = m963(this.f50.m18());
      boolean var10 = this.f50.m101();
      Render2DUtil.m195(this.f12, this.f13, 500.0F, 320.0F, 12.0F, m336(new Color(0, 0, 0), 0.6F * this.f56));
      Render2DUtil.m218(var7, var8, var4, var5, 14.0F, m336(new Color(0, 0, 0, 170), this.f56));
      Render2DUtil.m195(var7, var8, var4, var5, 14.0F, m336(f2, this.f56));
      Render2DUtil.m194(var7, var8, var4, 4.0F, 14.0F, 14.0F, 0.0F, 0.0F, m336(var9, this.f56));
      Render2DUtil.m202(var7, var8, var4, var5, 14.0F, 0.9F, m336(f6, this.f56));
      float var11 = 46.0F;
      float var12 = var7 + var4 / 2.0F - var11 / 2.0F;
      float var13 = var8 + 20.0F;
      Render2DUtil.m214(var12 - 3.0F, var13 - 3.0F, var11 + 6.0F, var11 + 6.0F, 7.0F, var11 / 2.0F, m336(var9, 0.4F * this.f56));
      this.m958(var1, var12, var13, var11, this.f50.m37());
      Render2DUtil.m202(var12, var13, var11, var11, var11 / 2.0F, 1.6F, m336(m944(var9, Color.WHITE, 0.25F), this.f56));
      Render2DUtil.m219(var1, FontRenderUtil.f2, var7 + var4 / 2.0F, var8 + 74.0F, this.f50.m37(), 14.0F, f8, "center");
      Render2DUtil.m219(
         var1, FontRenderUtil.f2, var7 + var4 / 2.0F, var8 + 92.0F, m384(this.f50.m18()), 9.5F, var9, "center"
      );
      float var14 = 15.0F;
      float var15 = 6.0F;
      float var16 = var8 + 108.0F;
      String var17 = "UID " + this.f50.m40();
      String var18 = var10 ? "онлайн" : "оффлайн";
      float var19 = FontRenderUtil.m237(FontRenderUtil.f2, var17, 8.5F) + 16.0F;
      float var20 = FontRenderUtil.m237(FontRenderUtil.f1, var18, 8.5F) + 22.0F;
      float var21 = var19 + var15 + var20;
      float var22 = var7 + (var4 - var21) / 2.0F;
      Render2DUtil.m195(var22, var16, var19, var14, var14 / 2.0F, m336(f4, this.f56));
      Render2DUtil.m219(
         var1,
         FontRenderUtil.f2,
         var22 + var19 / 2.0F,
         var16 + var14 / 2.0F - FontRenderUtil.m239(FontRenderUtil.f2, 8.5F) / 2.0F,
         var17,
         8.5F,
         f8,
         "center"
      );
      float var23 = var22 + var19 + var15;
      Render2DUtil.m195(var23, var16, var20, var14, var14 / 2.0F, m336(var10 ? new Color(28, 54, 34) : f4, this.f56));
      Render2DUtil.m195(var23 + 8.0F, var16 + var14 / 2.0F - 2.5F, 5.0F, 5.0F, 2.5F, var10 ? f10 : f9);
      Render2DUtil.m208(
         var1, FontRenderUtil.f1, var23 + 16.0F, var16 + var14 / 2.0F - FontRenderUtil.m239(FontRenderUtil.f1, 8.5F) / 2.0F, var18, 8.5F, var10 ? f10 : f9
      );
      Render2DUtil.m195(var7 + 16.0F, var8 + var5 - 44.0F, var4 - 32.0F, 0.8F, 0.0F, m336(f7, this.f56));
      boolean var24 = this.f50.m37().equalsIgnoreCase(this.f39);
      float var25 = var8 + var5 - 34.0F;
      if (!var24) {
         float var26 = this.m91() ? (var4 - 32.0F) / 2.0F : var4 - 24.0F;
         this.m955(
            var1,
            var7 + 12.0F,
            var25,
            var26,
            "Написать",
            this.f58,
            m945(this.f58),
            var2,
            var3,
            "pw",
            () -> {
               this.m21(this.f50.m37());
               this.f50 = null;
            }
         );
         if (this.m91()) {
            this.m955(
               var1,
               var7 + 20.0F + var26,
               var25,
               var26,
               "Мут 10м",
               new Color(190, 110, 60),
               new Color(210, 130, 80),
               var2,
               var3,
               "pm",
               () -> DaamkyChatApi.m913(
                     "mute",
                     null,
                     this.f50.m37(),
                     10,
                     () -> this.m942("Замучен на 10 мин", true),
                     var1x -> this.m942(this.m58(var1x), false)
                  )
            );
         }
      } else {
         Render2DUtil.m219(
            var1,
            FontRenderUtil.f1,
            var7 + var4 / 2.0F,
            var25 + 8.0F,
            "это вы",
            9.0F,
            f9,
            "center"
         );
      }

      float var27 = this.m943("close", m35(var2, var3, var7 + var4 - 22.0F, var8 + 8.0F, 16.0F, 16.0F));
      Render2DUtil.m195(var7 + var4 - 22.0F, var8 + 8.0F, 16.0F, 16.0F, 5.0F, m336(m944(f4, f5, var27), this.f56));
      Render2DUtil.m219(
         var1,
         FontRenderUtil.f2,
         var7 + var4 - 14.0F,
         var8 + 8.0F + 8.0F - FontRenderUtil.m239(FontRenderUtil.f2, 9.5F) / 2.0F,
         "✕",
         9.5F,
         m944(f9, f8, var27),
         "center"
      );
      this.m959(var7 + var4 - 22.0F, var8 + 8.0F, 16.0F, 16.0F, () -> this.f50 = null);
      this.f65 = new float[]{var7, var8, var4, var5};
   }

   private void m955(
      DrawContext var1, float var2, float var3, float var4, String var5, Color var6, Color var7, float var8, float var9, String var10, Runnable var11
   ) {
      float var12 = 24.0F;
      float var13 = this.m943(var10, m35(var8, var9, var2, var3, var4, var12));
      Render2DUtil.m195(var2, var3, var4, var12, 7.0F, m944(var6, var7, var13));
      Render2DUtil.m219(
         var1,
         FontRenderUtil.f2,
         var2 + var4 / 2.0F,
         var3 + var12 / 2.0F - FontRenderUtil.m239(FontRenderUtil.f2, 9.5F) / 2.0F,
         var5,
         9.5F,
         Color.WHITE,
         "center"
      );
      this.m959(var2, var3, var4, var12, var11);
   }

   private void m211(float var1, float var2, float var3, Color var4) {
      int var5 = Math.max(4, (int)var3);
      float var6 = var3 / (float)var5;

      for (int var7 = 0; var7 < var5; var7++) {
         float var8 = var3 * (1.0F - (float)var7 / (float)(var5 - 1));
         Render2DUtil.m195(var1 + (float)var7 * var6, var2 - var8 / 2.0F, var6 + 0.6F, var8, 0.0F, var4);
      }
   }

   private void m956(float var1, float var2, float var3, Color var4) {
      int var5 = Math.max(4, (int)var3);
      float var6 = var3 / (float)var5;

      for (int var7 = 0; var7 < var5; var7++) {
         float var8 = var3 * ((float)var7 / (float)(var5 - 1));
         Render2DUtil.m195(var1 + (float)var7 * var6, var2 - var8 / 2.0F, var6 + 0.6F, var8, 0.0F, var4);
      }
   }

   private float m957(long var1) {
      float var3 = this.m334(this.f60.getOrDefault(var1, 0.0F), 1.0F, 11.0F);
      this.f60.put(var1, var3);
      return var3;
   }

   private void m958(DrawContext var1, float var2, float var3, float var4, String var5) {
      Identifier var6 = TextureCache.m221(var5);
      if (var6 != null) {
         Render2DUtil.m210(var2, var3, var4, var6, var4 / 2.0F, Color.WHITE);
      } else {
         Color var7 = m964(var5);
         Render2DUtil.m195(var2, var3, var4, var4, var4 / 2.0F, var7);
         String var8 = var5 != null && !var5.isEmpty() ? var5.substring(0, 1).toUpperCase() : "?";
         Render2DUtil.m219(
            var1,
            FontRenderUtil.f2,
            var2 + var4 / 2.0F,
            var3 + var4 / 2.0F - var4 * 0.32F,
            var8,
            var4 * 0.52F,
            Color.WHITE,
            "center"
         );
      }
   }

   private void m21(String var1) {
      this.f30 = MessengerScreen$2.f2;
      this.f32 = var1;
      this.f33.clear();
      this.f34.clear();
      this.f47 = 0.0F;
      this.m61(true);
   }

   private void m314() {
      this.f30 = MessengerScreen$2.f1;
      this.f32 = null;
      this.f33.clear();
      this.f34.clear();
      this.f47 = 0.0F;
      this.m61(true);
   }

   private void m60(String var1) {
      DaamkyChatApi.m908(var1, var1x -> this.f50 = var1x, var1x -> this.m942(this.m58(var1x), false));
   }

   private void m29() {
      String var1 = this.f40.trim();
      if (!var1.isEmpty() && DaamkyChatApi.m91()) {
         this.f40 = "";
         String var2 = this.f30 == MessengerScreen$2.f2 ? this.f32 : null;
         ChatMessage var3 = new ChatMessage(-System.currentTimeMillis(), this.f39, UserProfile.m40(), UserProfile.m30(), var1, Instant.now().toString(), true);
         this.f34.add(var3);
         this.f33.add(var3);
         this.f47 = 0.0F;
         DaamkyChatApi.m907(var2, var1, () -> this.m61(true), var2x -> {
            this.f34.remove(var3);
            this.f33.remove(var3);
            this.m942(this.m58(var2x), false);
         });
      }
   }

   private void m959(float var1, float var2, float var3, float var4, Runnable var5) {
      this.f63.add(new Object[]{var1, var2, var3, var4, var5});
   }

   public boolean mouseClicked(Click click, boolean doubled) {
      float var3 = Render2DUtil.m3((float)click.x());
      float var4 = Render2DUtil.m151((float)click.y());
      if (this.f50 != null) {
         for (int var11 = this.f63.size() - 1; var11 >= 0; var11--) {
            Object[] var14 = this.f63.get(var11);
            if (m35(var3, var4, (Float)var14[0], (Float)var14[1], (Float)var14[2], (Float)var14[3])) {
               ((Runnable)var14[4]).run();
               return true;
            }
         }

         if (this.f65 != null && !m35(var3, var4, this.f65[0], this.f65[1], this.f65[2], this.f65[3])) {
            this.f50 = null;
         }

         return true;
      } else {
         this.f42 = false;
         this.f43 = false;

         for (float[] var6 : this.f61) {
            if (m35(var3, var4, var6[0], var6[1], var6[2], var6[3])) {
               int var7 = (int)var6[4];
               if (var7 >= 0 && var7 < this.f33.size()) {
                  this.m60(this.f33.get(var7).m40());
               }

               return true;
            }
         }

         for (float[] var12 : this.f62) {
            if (m35(var3, var4, var12[0], var12[1], var12[2], var12[3])) {
               long var15 = (long)var12[4];
               DaamkyChatApi.m913("delete", var15, null, null, () -> this.m61(false), var1 -> this.m942(this.m58(var1), false));
               return true;
            }
         }

         for (Object[] var13 : this.f63) {
            if (m35(var3, var4, (Float)var13[0], (Float)var13[1], (Float)var13[2], (Float)var13[3])) {
               ((Runnable)var13[4]).run();
               return true;
            }
         }

         return super.mouseClicked(click, doubled);
      }
   }

   public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
      this.f47 += (float)(verticalAmount * 24.0);
      this.f47 = Math.max(0.0F, Math.min(this.f47, this.f48));
      return true;
   }

   public boolean keyPressed(KeyInput input) {
      int var2 = input.key();
      if (var2 == 256) {
         if (this.f50 != null) {
            this.f50 = null;
            return true;
         } else if (this.f43) {
            this.f43 = false;
            return true;
         } else if (this.f30 == MessengerScreen$2.f2) {
            this.m314();
            return true;
         } else {
            this.close();
            return true;
         }
      } else {
         boolean var3 = GLFW.glfwGetKey(this.client.getWindow().getHandle(), 341) == 1 || GLFW.glfwGetKey(this.client.getWindow().getHandle(), 345) == 1;
         if (this.f43) {
            if (var2 == 259 && !this.f41.isEmpty()) {
               this.f41 = this.f41.substring(0, this.f41.length() - 1);
               return true;
            } else if (var2 != 257 && var2 != 335) {
               return true;
            } else {
               this.f43 = false;
               return true;
            }
         } else if (var2 == 257 || var2 == 335) {
            this.m29();
            return true;
         } else if (var2 == 259 && !this.f40.isEmpty()) {
            this.f40 = this.f40.substring(0, this.f40.length() - 1);
            return true;
         } else if (var2 == 86 && var3) {
            String var4 = this.client.keyboard.getClipboard();
            if (var4 != null) {
               this.f40 = this.f40 + var4.replaceAll("[\\n\\r\\t]", " ");
            }

            if (this.f40.length() > 300) {
               this.f40 = this.f40.substring(0, 300);
            }

            return true;
         } else {
            return super.keyPressed(input);
         }
      }
   }

   public boolean charTyped(CharInput input) {
      if (!input.isValidChar()) {
         return super.charTyped(input);
      } else if (this.f43) {
         if (this.f41.length() < 40) {
            this.f41 = this.f41 + input.asString();
         }

         return true;
      } else if (this.f40.length() < 300) {
         this.f42 = true;
         this.f40 = this.f40 + input.asString();
         return true;
      } else {
         return true;
      }
   }

   private static boolean m35(float var0, float var1, float var2, float var3, float var4, float var5) {
      return var0 >= var2 && var0 <= var2 + var4 && var1 >= var3 && var1 <= var3 + var5;
   }

   private static String m65(String var0) {
      if (var0 != null && !var0.isEmpty()) {
         try {
            LocalDateTime var1 = LocalDateTime.ofInstant(Instant.parse(var0), ZoneId.systemDefault());
            return String.format("%02d:%02d", var1.getHour(), var1.getMinute());
         } catch (Exception var2) {
            return var0.length() >= 16 ? var0.substring(11, 16) : "";
         }
      } else {
         return "";
      }
   }

   private static List<String> m960(String var0, FontAtlas var1, float var2, float var3) {
      ArrayList var4 = new ArrayList();
      if (var0 == null) {
         var0 = "";
      }

      if (var3 <= 4.0F) {
         var4.add(var0);
         return var4;
      } else {
         for (String var8 : var0.split(" ")) {
            if (!var4.isEmpty() && FontRenderUtil.m237(var1, m961(var4) + " " + var8, var2) <= var3) {
               var4.set(var4.size() - 1, m961(var4) + " " + var8);
            } else if (FontRenderUtil.m237(var1, var8, var2) <= var3) {
               var4.add(var8);
            } else {
               StringBuilder var9 = new StringBuilder();

               for (char var13 : var8.toCharArray()) {
                  if (FontRenderUtil.m237(var1, var9.toString() + var13, var2) > var3 && var9.length() > 0) {
                     var4.add(var9.toString());
                     var9 = new StringBuilder();
                  }

                  var9.append(var13);
               }

               if (var9.length() > 0) {
                  var4.add(var9.toString());
               }
            }
         }

         if (var4.isEmpty()) {
            var4.add("");
         }

         return var4;
      }
   }

   private static String m961(List<String> var0) {
      return (String)var0.get(var0.size() - 1);
   }

   private static String m962(String var0, FontAtlas var1, float var2, float var3) {
      if (var0 == null) {
         return "";
      } else if (FontRenderUtil.m237(var1, var0, var2) <= var3) {
         return var0;
      } else {
         StringBuilder var4 = new StringBuilder();

         for (char var8 : var0.toCharArray()) {
            if (FontRenderUtil.m237(var1, var4.toString() + var8 + "…", var2) > var3) {
               break;
            }

            var4.append(var8);
         }

         return var4 + "…";
      }
   }

   private static Color m963(String var0) {
      String var1 = var0 == null ? "" : var0.toLowerCase();

      return switch (var1) {
         case "dev" -> new Color(255, 185, 70);
         case "admin" -> new Color(235, 85, 85);
         case "mod" -> new Color(95, 200, 130);
         case "", "user" -> new Color(178, 184, 196);
         default -> m964(var0);
      };
   }

   private static String m383(String var0) {
      if (var0 == null) {
         return "";
      } else {
         String var1 = var0.toLowerCase();

         return switch (var1) {
            case "dev" -> "DEV";
            case "admin" -> "ADM";
            case "mod" -> "MOD";
            case "", "user" -> "";
            default -> var0.length() > 12 ? var0.substring(0, 12) : var0;
         };
      }
   }

   private static String m384(String var0) {
      if (var0 != null && !var0.isEmpty()) {
         String var1 = var0.toLowerCase();

         return switch (var1) {
            case "dev" -> "Разработчик";
            case "admin" -> "Администратор";
            case "mod" -> "Модератор";
            case "user" -> "Пользователь";
            default -> var0.substring(0, 1).toUpperCase() + var0.substring(1);
         };
      } else {
         return "Пользователь";
      }
   }

   private static Color m964(String var0) {
      int var1 = (var0 == null ? "?" : var0).toLowerCase().hashCode();
      float var2 = (float)(Math.abs(var1) % 360) / 360.0F;
      return Color.getHSBColor(var2, 0.55F, 0.8F);
   }
}

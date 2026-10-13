package ua.daamky.utils.render;

import com.mojang.blaze3d.textures.GpuTextureView;
import ua.daamky.utils.EasingUtil;
import ua.daamky.utils.BotManager;
import ua.daamky.utils.BotSession;
import ua.daamky.utils.render.shaders.TextureShader;
import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.input.CharInput;
import net.minecraft.client.input.KeyInput;
import net.minecraft.client.util.DefaultSkinHelper;
import net.minecraft.entity.player.SkinTextures;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

public class RenderHelper extends Screen {
   private long f1;
   private long f2;
   private final Map<String, Float> f3 = new HashMap<>();
   private boolean f4 = false;
   private String f5 = "";
   private String f6 = "";
   private boolean f7 = false;
   private boolean f8 = false;
   private int f9 = -1;
   private boolean f10 = false;
   private float f11 = 0.0F;
   private float f12 = 0.0F;
   private BotSession f13 = null;
   private static final float f14 = 400.0F;

   public RenderHelper() {
      super(Text.literal("Bots Wheel"));
   }

   protected void init() {
      super.init();
      this.f1 = System.currentTimeMillis();
      this.f2 = System.currentTimeMillis();
      int var1 = Render2DUtil.m113() / 2;
      int var2 = Render2DUtil.m189() / 2;
   }

   public void renderBackground(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
   }

   public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
      int var5 = Render2DUtil.m113();
      int var6 = Render2DUtil.m189();
      float var7 = Render2DUtil.m3((float)mouseX);
      float var8 = Render2DUtil.m151((float)mouseY);
      long var9 = System.currentTimeMillis();
      float var11 = (float)(var9 - this.f2);
      this.f2 = var9;
      float var12 = (float)(var9 - this.f1);
      float var13 = Math.min(1.0F, var12 / 400.0F);
      float var14 = EasingUtil.m3(var13);
      float var15 = (float)var5 / 2.0F;
      float var16 = (float)var6 / 2.0F;
      float var17 = 100.0F * var14;
      if (var14 > 0.01F) {
         Render2DUtil.m217(var15 - var17, var16 - var17, var17 * 2.0F, var17 * 2.0F, var17, 20.0F, 0.4F, 2.0F, new Color(10, 10, 10, 200));
         Render2DUtil.m195(var15 - var17, var16 - var17, var17 * 2.0F, var17 * 2.0F, var17, new Color(15, 15, 15, 230));
         float var18 = 30.0F * var14;
         boolean var19 = var7 > var15 - var18 / 2.0F && var7 < var15 + var18 / 2.0F && var8 > var16 - var18 / 2.0F && var8 < var16 + var18 / 2.0F;
         Color var20 = var19 ? new Color(60, 150, 255) : new Color(40, 120, 220);
         Render2DUtil.m195(var15 - var18 / 2.0F, var16 - var18 / 2.0F, var18, var18, var18 / 2.0F, var20);
         Render2DUtil.m206(
            context, var15, var16 - 6.0F * var14, "+", 18.0F * var14, Color.WHITE, "center"
         );
         List var21 = BotManager.m76();
         int var22 = var21.size();

         for (int var23 = 0; var23 < var22; var23++) {
            BotSession var24 = (BotSession)var21.get(var23);
            float var25 = (float)((double)var23 * (Math.PI * 2) / (double)var22) - (float) (Math.PI / 2);
            float var26 = var15 + (float)Math.cos((double)var25) * var17 * 0.7F;
            float var27 = var16 + (float)Math.sin((double)var25) * var17 * 0.7F;
            float var28 = 24.0F * var14;
            boolean var29 = var7 > var26 - var28 / 2.0F && var7 < var26 + var28 / 2.0F && var8 > var27 - var28 / 2.0F && var8 < var27 + var28 / 2.0F;
            float var30 = this.f3.getOrDefault(var24.m37(), 0.0F);
            float var31 = var29 ? 1.0F : 0.0F;
            var30 += (var31 - var30) * (var11 / 100.0F);
            var30 = Math.max(0.0F, Math.min(1.0F, var30));
            this.f3.put(var24.m37(), var30);
            float var32 = EasingUtil.m3(var30);
            float var33 = var28 + 12.0F * var32;
            SkinTextures var34 = var24.m74() != null ? var24.m74().getSkin() : DefaultSkinHelper.getSkinTextures(UUID.randomUUID());
            Identifier var35 = var34.body().texturePath();
            int var36 = (int)(255.0F * Math.max(0.0F, Math.min(1.0F, var14)));
            int var37 = new Color(255, 255, 255, var36).getRGB();
            GpuTextureView var38 = this.client.getTextureManager().getTexture(var35).getGlTextureView();
            float var39 = var33 / 4.0F;
            TextureShader.m344(
               Render2DUtil.m191(), var26 - var33 / 2.0F, var27 - var33 / 2.0F, var33, var38, var37, var39, 0.0F, 0.125F, 0.125F, 0.125F, 0.125F, false
            );
            TextureShader.m344(
               Render2DUtil.m191(), var26 - var33 / 2.0F, var27 - var33 / 2.0F, var33, var38, var37, var39, 0.0F, 0.625F, 0.125F, 0.125F, 0.125F, false
            );
            if (var30 > 0.01F) {
               float var40 = var27 + (var33 / 2.0F + 8.0F) * var32;
               int var41 = (int)(255.0F * Math.max(0.0F, Math.min(1.0F, var30 * 2.0F * var14)));
               Color var42 = new Color(255, 255, 255, var41);
               Render2DUtil.m206(context, var26, var40, var24.m37(), Math.max(0.0F, 10.0F * var14), var42, "center");
            }
         }

         if (this.f4) {
            Render2DUtil.m195(0.0F, 0.0F, (float)var5, (float)var6, 0.0F, new Color(0, 0, 0, 150));
            float var43 = 160.0F;
            float var45 = 120.0F;
            float var47 = var15 - var43 / 2.0F;
            float var49 = var16 - var45 / 2.0F;
            Render2DUtil.m195(var47, var49, var43, var45, 8.0F, new Color(20, 20, 20, 255));
            Render2DUtil.m206(
               context, var15, var49 + 10.0F, "Connect Bot", 14.0F, Color.WHITE, "center"
            );
            float var51 = 120.0F;
            float var53 = 20.0F;
            float var55 = var15 - var51 / 2.0F;
            float var59 = var49 + 30.0F;
            float var60 = var15 - var51 / 2.0F;
            float var61 = var49 + 60.0F;
            Render2DUtil.m195(var55, var59, var51, var53, 4.0F, this.f7 ? new Color(40, 40, 45, 255) : new Color(30, 30, 35, 255));
            if (this.f7) {
               Render2DUtil.m202(var55, var59, var51, var53, 4.0F, 1.0F, new Color(80, 160, 255));
            }

            if (this.f5.isEmpty() && !this.f7) {
               Render2DUtil.m205(context, var55 + 5.0F, var59 + 5.0F, "Nickname", 10.0F, new Color(150, 150, 150));
            } else {
               Render2DUtil.m205(
                  context,
                  var55 + 5.0F,
                  var59 + 5.0F,
                  this.f5 + (this.f7 && System.currentTimeMillis() / 500L % 2L == 0L ? "_" : ""),
                  10.0F,
                  Color.WHITE
               );
            }

            Render2DUtil.m195(var60, var61, var51, var53, 4.0F, this.f8 ? new Color(40, 40, 45, 255) : new Color(30, 30, 35, 255));
            if (this.f8) {
               Render2DUtil.m202(var60, var61, var51, var53, 4.0F, 1.0F, new Color(80, 160, 255));
            }

            if (this.f6.isEmpty() && !this.f8) {
               Render2DUtil.m205(context, var60 + 5.0F, var61 + 5.0F, "IP Address", 10.0F, new Color(150, 150, 150));
            } else {
               Render2DUtil.m205(
                  context,
                  var60 + 5.0F,
                  var61 + 5.0F,
                  this.f6 + (this.f8 && System.currentTimeMillis() / 500L % 2L == 0L ? "_" : ""),
                  10.0F,
                  Color.WHITE
               );
            }

            float var62 = 80.0F;
            float var63 = 20.0F;
            float var64 = var15 - var62 / 2.0F;
            float var65 = var49 + 90.0F;
            boolean var66 = var7 > var64 && var7 < var64 + var62 && var8 > var65 && var8 < var65 + var63;
            Render2DUtil.m195(var64, var65, var62, var63, 4.0F, var66 ? new Color(0, 150, 0) : new Color(0, 120, 0));
            Render2DUtil.m206(
               context, var15, var65 + 5.0F, "Connect", 10.0F, Color.WHITE, "center"
            );
         }

         if (this.f10 && this.f13 != null) {
            float var44 = 80.0F;
            float var46 = 80.0F;
            Render2DUtil.m195(this.f11, this.f12, var44, var46, 4.0F, new Color(30, 30, 30, 255));
            String[] var48 = new String[]{
               "Control",
               "Say All",
               "Pulse",
               "Remove"
            };

            for (int var50 = 0; var50 < var48.length; var50++) {
               float var52 = this.f12 + (float)var50 * 20.0F;
               boolean var54 = var7 > this.f11 && var7 < this.f11 + var44 && var8 > var52 && var8 < var52 + 20.0F;
               if (var54) {
                  Render2DUtil.m195(this.f11, var52, var44, 20.0F, 0.0F, new Color(60, 60, 60, 255));
               }

               Color var56 = var48[var50].equals("Remove") ? new Color(255, 80, 80) : Color.WHITE;
               Render2DUtil.m206(context, this.f11 + var44 / 2.0F, var52 + 6.0F, var48[var50], 10.0F, var56, "center");
            }
         }
      }

      super.render(context, mouseX, mouseY, deltaTicks);
   }

   public boolean mouseClicked(Click click, boolean doubled) {
      float var3 = (float)Render2DUtil.m113();
      float var4 = (float)Render2DUtil.m189();
      double var5 = click.x();
      double var7 = click.y();
      int var9 = click.button();
      float var10 = Render2DUtil.m3((float)var5);
      float var11 = Render2DUtil.m151((float)var7);
      float var12 = var3 / 2.0F;
      float var13 = var4 / 2.0F;
      if (this.f10) {
         float var29 = 80.0F;
         float var31 = 80.0F;
         if (var10 >= this.f11 && var10 <= this.f11 + var29 && var11 >= this.f12 && var11 <= this.f12 + var31) {
            int var33 = (int)((var11 - this.f12) / 20.0F);
            if (var33 == 0) {
               BotManager.m17(this.f13.m37());
            }

            if (var33 == 1) {
               this.client
                  .player
                  .sendMessage(
                     Text.literal("Чат от имени бота в разработке..."), false
                  );
            }

            if (var33 == 2) {
               BotManager.m61(false);
            }

            if (var33 == 3) {
               BotManager.m80(this.f13.m37());
            }

            this.f10 = false;
            return true;
         } else {
            this.f10 = false;
            return true;
         }
      } else if (this.f4) {
         float var28 = 160.0F;
         float var30 = 120.0F;
         float var32 = var12 - var28 / 2.0F;
         float var34 = var13 - var30 / 2.0F;
         float var35 = 80.0F;
         float var36 = 20.0F;
         float var37 = var12 - var35 / 2.0F;
         float var38 = var34 + 90.0F;
         if (var10 > var37 && var10 < var37 + var35 && var11 > var38 && var11 < var38 + var36) {
            if (!this.f5.isEmpty() && !this.f6.isEmpty()) {
               BotManager.m77(this.f5, this.f6);
               this.f4 = false;
            }

            return true;
         } else {
            float var39 = 120.0F;
            float var40 = 20.0F;
            float var24 = var12 - var39 / 2.0F;
            float var25 = var34 + 30.0F;
            float var26 = var12 - var39 / 2.0F;
            float var27 = var34 + 60.0F;
            this.f7 = var10 >= var24 && var10 <= var24 + var39 && var11 >= var25 && var11 <= var25 + var40;
            this.f8 = var10 >= var26 && var10 <= var26 + var39 && var11 >= var27 && var11 <= var27 + var40;
            if (!this.f7 && !this.f8) {
               if (var10 < var32 || var10 > var32 + var28 || var11 < var34 || var11 > var34 + var30) {
                  this.f4 = false;
               }

               return true;
            } else {
               return true;
            }
         }
      } else {
         float var14 = 100.0F;
         float var15 = 30.0F;
         if (var10 > var12 - var15 / 2.0F && var10 < var12 + var15 / 2.0F && var11 > var13 - var15 / 2.0F && var11 < var13 + var15 / 2.0F && var9 == 0) {
            this.f4 = true;
            return true;
         } else {
            List var16 = BotManager.m76();
            int var17 = var16.size();

            for (int var18 = 0; var18 < var17; var18++) {
               BotSession var19 = (BotSession)var16.get(var18);
               float var20 = (float)((double)var18 * (Math.PI * 2) / (double)var17) - (float) (Math.PI / 2);
               float var21 = var12 + (float)Math.cos((double)var20) * var14 * 0.7F;
               float var22 = var13 + (float)Math.sin((double)var20) * var14 * 0.7F;
               float var23 = 24.0F;
               if (var10 > var21 - var23 / 2.0F && var10 < var21 + var23 / 2.0F && var11 > var22 - var23 / 2.0F && var11 < var22 + var23 / 2.0F) {
                  if (var9 == 1) {
                     this.f10 = true;
                     this.f11 = var10;
                     this.f12 = var11;
                     this.f13 = var19;
                  } else if (var9 == 0) {
                     BotManager.m17(var19.m37());
                  }

                  return true;
               }
            }

            return super.mouseClicked(click, doubled);
         }
      }
   }

   public boolean keyPressed(KeyInput input) {
      if (input.key() == 256) {
         if (this.f10) {
            this.f10 = false;
            return true;
         } else if (this.f4) {
            this.f4 = false;
            return true;
         } else {
            this.close();
            return true;
         }
      } else {
         if (this.f4) {
            if (input.key() == 259) {
               if (this.f7 && this.f5.length() > 0) {
                  this.f5 = this.f5.substring(0, this.f5.length() - 1);
                  return true;
               }

               if (this.f8 && this.f6.length() > 0) {
                  this.f6 = this.f6.substring(0, this.f6.length() - 1);
                  return true;
               }
            } else if (input.key() == 257) {
               if (this.f7) {
                  this.f7 = false;
                  this.f8 = true;
                  return true;
               }

               if (this.f8) {
                  if (!this.f5.isEmpty() && !this.f6.isEmpty()) {
                     BotManager.m77(this.f5, this.f6);
                     this.f4 = false;
                  }

                  return true;
               }
            } else if (input.key() == 86
               && (GLFW.glfwGetKey(this.client.getWindow().getHandle(), 341) == 1 || GLFW.glfwGetKey(this.client.getWindow().getHandle(), 345) == 1)) {
               String var2 = this.client.keyboard.getClipboard();
               if (this.f7 && this.f5.length() < 16) {
                  this.f5 = this.f5 + var2;
                  if (this.f5.length() > 16) {
                     this.f5 = this.f5.substring(0, 16);
                  }
               }

               if (this.f8 && this.f6.length() < 64) {
                  this.f6 = this.f6 + var2;
                  if (this.f6.length() > 64) {
                     this.f6 = this.f6.substring(0, 64);
                  }
               }

               return true;
            }
         }

         return super.keyPressed(input);
      }
   }

   public boolean charTyped(CharInput input) {
      if (this.f4 && input.isValidChar()) {
         if (this.f7 && this.f5.length() < 16) {
            this.f5 = this.f5 + input.asString();
            return true;
         }

         if (this.f8 && this.f6.length() < 64) {
            this.f6 = this.f6 + input.asString();
            return true;
         }
      }

      return super.charTyped(input);
   }

   public boolean shouldPause() {
      return false;
   }
}

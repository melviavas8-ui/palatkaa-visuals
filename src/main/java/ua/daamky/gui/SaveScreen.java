package ua.daamky.gui;

import ua.daamky.features.misc.InventoryBuilder;
import ua.daamky.gui.theme.ThemeManager;
import ua.daamky.utils.PresetItemData;
import ua.daamky.utils.InventoryPreset;
import ua.daamky.utils.ScissorUtil;
import ua.daamky.utils.render.Render2DUtil;
import ua.daamky.utils.render.fonts.FontRenderUtil;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.input.CharInput;
import net.minecraft.client.input.KeyInput;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.joml.Matrix3x2fStack;

public class SaveScreen extends Screen {
   private static final Color f1 = new Color(9, 9, 13);
   private static final Color f2 = new Color(14, 14, 20);
   private static final Color f3 = new Color(22, 22, 28);
   private static final Color f4 = new Color(30, 30, 38);
   private static final Color f5 = new Color(120, 120, 135, 40);
   private static final Color f6 = new Color(231, 231, 245);
   private static final Color f7 = new Color(150, 150, 165);
   private static final Color f8 = new Color(84, 209, 138);
   private static final Color f9 = new Color(255, 106, 106);
   private final float f10 = 560.0F;
   private final float f11 = 372.0F;
   private float f12;
   private float f13;
   private Color f14 = new Color(154, 92, 255);
   private SaveScreen$1 f15 = SaveScreen$1.f1;
   private InventoryPreset f16 = new InventoryPreset("новый");
   private int f17 = -1;
   private String f18 = "";
   private String f19 = "новый";
   private String f20 = "";
   private final List<Item> f21 = new ArrayList<>();
   private String f22 = null;
   private float f23 = 0.0F;
   private String f24 = "";
   private boolean f25 = false;
   private final Map<String, Float> f26 = new HashMap<>();
   private final List<Object[]> f27 = new ArrayList<>();
   private long f28 = System.nanoTime();
   private float f29;

   public SaveScreen() {
      super(Text.literal("Inventory Builder"));
   }

   public void renderBackground(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
   }

   public boolean shouldPause() {
      return false;
   }

   public void removed() {
      InventoryBuilder var1 = InventoryBuilder.f1;
      if (var1 != null && var1.isEnabled() && !var1.m687()) {
         var1.toggle();
      }
   }

   private void m942(String var1, boolean var2) {
      this.f24 = var1;
      this.f25 = var2;
   }

   private float m334(float var1, float var2, float var3) {
      return var1 + (var2 - var1) * (1.0F - (float)Math.exp((double)(-var3 * this.f29)));
   }

   private float m943(String var1, boolean var2) {
      float var3 = this.m334(this.f26.getOrDefault(var1, 0.0F), var2 ? 1.0F : 0.0F, 16.0F);
      this.f26.put(var1, var3);
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

   private static boolean m35(float var0, float var1, float var2, float var3, float var4, float var5) {
      return var0 >= var2 && var0 <= var2 + var4 && var1 >= var3 && var1 <= var3 + var5;
   }

   private void m959(float var1, float var2, float var3, float var4, Runnable var5) {
      this.f27.add(new Object[]{var1, var2, var3, var4, var5});
   }

   private void m63() {
      if (!this.f18.equals(this.f22)) {
         this.f22 = this.f18;
         this.f21.clear();
         String var1 = this.f18.trim().toLowerCase();
         int var2 = 0;

         for (Item var4 : Registries.ITEM) {
            if (var4 != Items.AIR) {
               if (!var1.isEmpty()) {
                  String var5 = Registries.ITEM.getId(var4).toString().toLowerCase();
                  String var6 = new ItemStack(var4).getName().getString().toLowerCase();
                  if (!var5.contains(var1) && !var6.contains(var1)) {
                     continue;
                  }
               }

               this.f21.add(var4);
               if (++var2 >= 400) {
                  break;
               }
            }
         }

         this.f23 = 0.0F;
      }
   }

   public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
      long var5 = System.nanoTime();
      this.f29 = Math.min(0.05F, (float)(var5 - this.f28) / 1.0E9F);
      this.f28 = var5;
      this.f14 = ThemeManager.m1379();
      this.m63();
      float var7 = (float)Render2DUtil.m113();
      float var8 = (float)Render2DUtil.m189();
      this.f12 = (var7 - 560.0F) / 2.0F;
      this.f13 = (var8 - 372.0F) / 2.0F;
      float var9 = Render2DUtil.m3((float)mouseX);
      float var10 = Render2DUtil.m151((float)mouseY);
      this.f27.clear();
      Render2DUtil.m195(0.0F, 0.0F, var7, var8, 0.0F, m336(new Color(0, 0, 0), 0.5F));
      Render2DUtil.m195(this.f12, this.f13, 560.0F, 372.0F, 12.0F, f1);
      Render2DUtil.m202(this.f12, this.f13, 560.0F, 372.0F, 12.0F, 0.9F, f5);
      Render2DUtil.m208(context, FontRenderUtil.f2, this.f12 + 16.0F, this.f13 + 13.0F, "Inventory Builder", 12.0F, f6);
      Render2DUtil.m208(
         context,
         FontRenderUtil.f1,
         this.f12 + 16.0F,
         this.f13 + 27.0F,
         "редактор пресетов",
         8.0F,
         f7
      );
      this.m946(context, var9, var10, this.f12 + 10.0F, this.f13 + 44.0F, 148.0F, 318.0F);
      this.m994(context, var9, var10, this.f12 + 168.0F, this.f13 + 44.0F);
      this.m951(context, var9, var10, this.f12 + 390.0F, this.f13 + 44.0F, 160.0F, 318.0F);
      if (!this.f24.isEmpty()) {
         Render2DUtil.m208(context, FontRenderUtil.f1, this.f12 + 168.0F, this.f13 + 372.0F - 14.0F, this.f24, 8.5F, this.f25 ? f8 : f9);
      }
   }

   private void m946(DrawContext var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      Render2DUtil.m195(var4, var5, var6, var7, 10.0F, f2);
      Render2DUtil.m208(var1, FontRenderUtil.f2, var4 + 10.0F, var5 + 9.0F, "ПРЕСЕТЫ", 8.5F, f7);
      float var8 = var5 + 24.0F;
      this.m948(
         var1,
         var4 + 8.0F,
         var8,
         var6 - 16.0F,
         "имя пресета",
         this.f19,
         this.f15 == SaveScreen$1.f3,
         var2,
         var3,
         () -> this.f15 = SaveScreen$1.f3
      );
      float var9 = var8 + 24.0F;
      this.m997(
         var1,
         var4 + 8.0F,
         var9,
         (var6 - 16.0F) / 2.0F - 3.0F,
         "Сохранить",
         this.f14,
         var2,
         var3,
         "save",
         () -> {
            if (this.f19.isBlank()) {
               this.m942("Введите имя", false);
            } else {
               this.f16.f1 = this.f19.trim();
               this.m942(
                  Daamky_3.m901(this.f16) ? "Сохранено" : "Ошибка",
                  Daamky_3.m901(this.f16)
               );
            }
         }
      );
      this.m997(
         var1,
         var4 + 8.0F + (var6 - 16.0F) / 2.0F + 3.0F,
         var9,
         (var6 - 16.0F) / 2.0F - 3.0F,
         "Новый",
         f3,
         var2,
         var3,
         "new",
         () -> {
            this.f16 = new InventoryPreset("новый");
            this.f19 = "новый";
            this.f17 = -1;
            this.m942("Новый пресет", true);
         }
      );
      float var10 = var9 + 28.0F;
      float var11 = var5 + var7 - 64.0F;
      Render2DUtil.m208(var1, FontRenderUtil.f1, var4 + 10.0F, var10 - 11.0F, "сохранённые:", 7.5F, f7);
      ScissorUtil.m357((double)(var4 + 4.0F), (double)var10, (double)(var6 - 6.0F), (double)(var11 - var10));
      List<String> var12 = Daamky_3.m76();
      float var13 = var10;

      for (String var15 : var12) {
         float var16 = 20.0F;
         float var17 = this.m943("p_" + var15, m35(var2, var3, var4 + 8.0F, var13, var6 - 16.0F, var16 - 3.0F));
         Render2DUtil.m195(var4 + 8.0F, var13, var6 - 16.0F, var16 - 3.0F, 6.0F, m944(f3, f4, var17));
         Render2DUtil.m208(
            var1,
            FontRenderUtil.f1,
            var4 + 14.0F,
            var13 + var16 / 2.0F - FontRenderUtil.m239(FontRenderUtil.f1, 8.5F) / 2.0F,
            this.m999(var15, 8.5F, var6 - 46.0F),
            8.5F,
            f6
         );
         this.m959(var4 + 8.0F, var13, var6 - 40.0F, var16 - 3.0F, () -> {
            InventoryPreset var2x = Daamky_3.m900(var15);
            if (var2x != null) {
               this.f16 = var2x;
               this.f19 = var2x.f1;
               this.f17 = -1;
               this.m942("Загружено: " + var15, true);
            }
         });
         float var19 = this.m943("pd_" + var15, m35(var2, var3, var4 + var6 - 26.0F, var13, 18.0F, var16 - 3.0F));
         Render2DUtil.m219(
            var1,
            FontRenderUtil.f3,
            var4 + var6 - 20.0F,
            var13 + var16 / 2.0F - FontRenderUtil.m239(FontRenderUtil.f3, 8.0F) / 2.0F,
            "S",
            8.0F,
            m944(f7, f9, var19),
            "center"
         );
         this.m959(var4 + var6 - 28.0F, var13, 20.0F, var16 - 3.0F, () -> {
            Daamky_3.m21(var15);
            this.m942("Удалён: " + var15, true);
         });
         var13 += var16;
      }

      if (var12.isEmpty()) {
         Render2DUtil.m208(var1, FontRenderUtil.f1, var4 + 12.0F, var10 + 4.0F, "пусто", 8.0F, f7);
      }

      ScissorUtil.m29();
      float var20 = var5 + var7 - 56.0F;
      boolean var21 = InventoryBuilder.f1 != null && InventoryBuilder.f1.m687();
      this.m997(
         var1,
         var4 + 8.0F,
         var20,
         var6 - 16.0F,
         var21 ? "Остановить" : "Собрать (" + this.f16.m113() + ")",
         var21 ? f9 : this.f14,
         var2,
         var3,
         "build",
         () -> {
            InventoryBuilder var2x = InventoryBuilder.f1;
            if (var2x != null) {
               if (var21) {
                  var2x.m16("вручную");
                  this.m942("Остановлено", false);
               } else if (this.f16.m113() == 0) {
                  this.m942("Пресет пустой", false);
               } else {
                  var2x.m853(this.f16);
                  this.close();
               }
            }
         }
      );
      Render2DUtil.m208(var1, FontRenderUtil.f1, var4 + 10.0F, var5 + var7 - 16.0F, "Esc — закрыть", 7.5F, f7);
   }

   private void m994(DrawContext var1, float var2, float var3, float var4, float var5) {
      Render2DUtil.m208(var1, FontRenderUtil.f2, var4, var5 - 1.0F, "СЛОТЫ ПРЕСЕТА", 8.5F, f7);
      float var6 = 20.0F;
      float var7 = 3.0F;
      float var8 = var5 + 16.0F;
      this.m995(var1, var2, var3, var4, var8, var6, var7, 0, 27, 9);
      this.m995(var1, var2, var3, var4, var8 + 3.0F * (var6 + var7) + 8.0F, var6, var7, 27, 9, 9);
      this.m995(var1, var2, var3, var4, var8 + 3.0F * (var6 + var7) + 8.0F + var6 + var7 + 8.0F, var6, var7, 36, 5, 9);
      Render2DUtil.m208(var1, FontRenderUtil.f1, var4, var8 + 3.0F * (var6 + var7) + 2.0F, "хотбар", 7.0F, f7);
      Render2DUtil.m208(
         var1,
         FontRenderUtil.f1,
         var4,
         var8 + 3.0F * (var6 + var7) + 8.0F + var6 + var7 + 2.0F,
         "броня + оффхенд",
         7.0F,
         f7
      );
   }

   private void m995(DrawContext var1, float var2, float var3, float var4, float var5, float var6, float var7, int var8, int var9, int var10) {
      for (int var11 = 0; var11 < var9; var11++) {
         int var12 = var8 + var11;
         float var13 = var4 + (float)(var11 % var10) * (var6 + var7);
         float var14 = var5 + (float)(var11 / var10) * (var6 + var7);
         boolean var15 = var12 == this.f17;
         float var16 = this.m943("s" + var12, m35(var2, var3, var13, var14, var6, var6));
         Render2DUtil.m195(var13, var14, var6, var6, 4.0F, var15 ? m336(this.f14, 0.3F) : m944(f3, f4, var16));
         if (var15) {
            Render2DUtil.m202(var13, var14, var6, var6, 4.0F, 1.0F, this.f14);
         }

         PresetItemData var17 = this.f16.f2[var12];
         if (var17 != null) {
            Item var18 = (Item)Registries.ITEM.get(Identifier.tryParse(var17.f1));
            if (var18 != null) {
               this.m998(var1, new ItemStack(var18), var13 + 2.0F, var14 + 2.0F, var6 - 4.0F);
               Render2DUtil.m219(
                  var1, FontRenderUtil.f2, var13 + var6 - 3.0F, var14 + var6 - 9.0F, "x" + var17.f2, 7.0F, f6, "right"
               );
            }
         }

         this.m959(var13, var14, var6, var6, () -> {
            this.f17 = var12;
            this.m314();
         });
      }
   }

   private void m314() {
      PresetItemData var1 = this.f17 >= 0 ? this.f16.f2[this.f17] : null;
      this.f20 = var1 != null && var1.f3 > 0L ? String.valueOf(var1.f3) : "";
   }

   private void m951(DrawContext var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      Render2DUtil.m195(var4, var5, var6, var7, 10.0F, f2);
      if (this.f17 < 0) {
         Render2DUtil.m208(
            var1, FontRenderUtil.f1, var4 + 12.0F, var5 + 14.0F, "Выбери слот слева", 9.0F, f7
         );
      } else {
         PresetItemData var8 = this.f16.f2[this.f17];
         Render2DUtil.m208(var1, FontRenderUtil.f2, var4 + 10.0F, var5 + 9.0F, "СЛОТ #" + this.f17, 8.5F, f7);
         this.m948(
            var1,
            var4 + 8.0F,
            var5 + 22.0F,
            var6 - 16.0F,
            "поиск предмета",
            this.f18,
            this.f15 == SaveScreen$1.f2,
            var2,
            var3,
            () -> this.f15 = SaveScreen$1.f2
         );
         float var9 = var5 + 46.0F;
         float var10 = var5 + var7 - 84.0F;
         ScissorUtil.m357((double)(var4 + 4.0F), (double)var9, (double)(var6 - 6.0F), (double)(var10 - var9));
         float var11 = var9 - this.f23;

         for (Item var13 : this.f21) {
            float var14 = 18.0F;
            if (var11 + var14 >= var9 && var11 <= var10) {
               float var15 = this.m943(
                  "it" + Registries.ITEM.getRawId(var13), m35(var2, var3, var4 + 8.0F, var11, var6 - 16.0F, var14 - 2.0F) && var3 >= var9 && var3 <= var10
               );
               Render2DUtil.m195(var4 + 8.0F, var11, var6 - 16.0F, var14 - 2.0F, 5.0F, m944(f3, f4, var15));
               this.m998(var1, new ItemStack(var13), var4 + 10.0F, var11 + 1.0F, var14 - 4.0F);
               Render2DUtil.m208(
                  var1,
                  FontRenderUtil.f1,
                  var4 + 28.0F,
                  var11 + var14 / 2.0F - FontRenderUtil.m239(FontRenderUtil.f1, 8.0F) / 2.0F,
                  this.m999(new ItemStack(var13).getName().getString(), 8.0F, var6 - 36.0F),
                  8.0F,
                  f6
               );
               if (var3 >= var9 && var3 <= var10) {
                  this.m959(var4 + 8.0F, var11, var6 - 16.0F, var14 - 2.0F, () -> {
                     int var2x = this.f16.f2[this.f17] != null ? this.f16.f2[this.f17].f2 : 1;
                     long var3x = this.f16.f2[this.f17] != null ? this.f16.f2[this.f17].f3 : 0L;
                     PresetItemData var5x = new PresetItemData(Registries.ITEM.getId(var13).toString(), Math.max(1, var2x));
                     var5x.f3 = var3x;
                     this.f16.f2[this.f17] = var5x;
                     this.m942("Слот " + this.f17 + " = " + this.m999(new ItemStack(var13).getName().getString(), 8.0F, 120.0F), true);
                  });
               }
            }

            var11 += var14;
         }

         ScissorUtil.m29();
         float var17 = var5 + var7 - 58.0F;
         Render2DUtil.m208(var1, FontRenderUtil.f1, var4 + 10.0F, var17 - 1.0F, "количество:", 7.5F, f7);
         if (var8 != null) {
            this.m997(
               var1,
               var4 + 8.0F,
               var17 + 8.0F,
               22.0F,
               "-",
               f3,
               var2,
               var3,
               "cm",
               () -> var8.f2 = Math.max(1, var8.f2 - 1)
            );
            Render2DUtil.m219(
               var1,
               FontRenderUtil.f2,
               var4 + 8.0F + 22.0F + (var6 - 16.0F - 44.0F) / 2.0F + 11.0F,
               var17 + 8.0F + 8.0F - FontRenderUtil.m239(FontRenderUtil.f2, 10.0F) / 2.0F,
               String.valueOf(var8.f2),
               10.0F,
               f6,
               "center"
            );
            this.m997(
               var1,
               var4 + var6 - 8.0F - 22.0F,
               var17 + 8.0F,
               22.0F,
               "+",
               f3,
               var2,
               var3,
               "cp",
               () -> var8.f2 = Math.min(64, var8.f2 + 1)
            );
         } else {
            Render2DUtil.m208(
               var1, FontRenderUtil.f1, var4 + 10.0F, var17 + 10.0F, "предмет не выбран", 8.0F, f7
            );
         }

         float var18 = var5 + var7 - 26.0F;
         this.m948(
            var1,
            var4 + 8.0F,
            var18,
            var6 - 70.0F,
            "макс цена (0=∞)",
            this.f20,
            this.f15 == SaveScreen$1.f4,
            var2,
            var3,
            () -> this.f15 = SaveScreen$1.f4
         );
         this.m997(
            var1,
            var4 + var6 - 58.0F,
            var18,
            24.0F,
            "OK",
            this.f14,
            var2,
            var3,
            "pok",
            () -> this.m996(var8)
         );
         this.m997(
            var1, var4 + var6 - 30.0F, var18, 22.0F, "×", f3, var2, var3, "clr", () -> {
               if (this.f17 >= 0) {
                  this.f16.f2[this.f17] = null;
                  this.m942("Слот " + this.f17 + " очищен", true);
               }
            }
         );
      }
   }

   private void m996(PresetItemData var1) {
      if (var1 != null) {
         try {
            var1.f3 = this.f20.isBlank() ? 0L : Long.parseLong(this.f20.trim());
            this.m942("Цена задана", true);
         } catch (Exception var3) {
            this.m942("Цена — только цифры", false);
         }
      }
   }

   private void m948(DrawContext var1, float var2, float var3, float var4, String var5, String var6, boolean var7, float var8, float var9, Runnable var10) {
      float var11 = 18.0F;
      Render2DUtil.m195(var2, var3, var4, var11, 5.0F, var7 ? f4 : f3);
      if (var7) {
         Render2DUtil.m202(var2, var3, var4, var11, 5.0F, 0.9F, m336(this.f14, 0.8F));
      }

      String var12 = var6.isEmpty() ? var5 : this.m999(var6, 8.5F, var4 - 12.0F);
      Render2DUtil.m208(
         var1, FontRenderUtil.f1, var2 + 7.0F, var3 + var11 / 2.0F - FontRenderUtil.m239(FontRenderUtil.f1, 8.5F) / 2.0F, var12, 8.5F, var6.isEmpty() ? f7 : f6
      );
      this.m959(var2, var3, var4, var11, var10);
   }

   private void m997(DrawContext var1, float var2, float var3, float var4, String var5, Color var6, float var7, float var8, String var9, Runnable var10) {
      float var11 = 18.0F;
      float var12 = this.m943("b_" + var9, m35(var7, var8, var2, var3, var4, var11));
      Render2DUtil.m195(var2, var3, var4, var11, 5.0F, m944(var6, m944(var6, Color.WHITE, 0.15F), var12));
      boolean var13 = var6 == this.f14 || var6 == f9;
      Render2DUtil.m219(
         var1,
         FontRenderUtil.f2,
         var2 + var4 / 2.0F,
         var3 + var11 / 2.0F - FontRenderUtil.m239(FontRenderUtil.f2, 8.5F) / 2.0F,
         var5,
         8.5F,
         var13 ? Color.WHITE : f6,
         "center"
      );
      this.m959(var2, var3, var4, var11, var10);
   }

   private void m998(DrawContext var1, ItemStack var2, float var3, float var4, float var5) {
      float var6 = Render2DUtil.m192();
      Matrix3x2fStack var7 = var1.getMatrices();
      var7.pushMatrix();
      var7.translate(var3 / var6, var4 / var6);
      float var8 = var5 / var6 / 16.0F;
      var7.scale(var8, var8);
      var1.drawItem(var2, 0, 0);
      var7.popMatrix();
   }

   private String m999(String var1, float var2, float var3) {
      if (var1 == null) {
         return "";
      } else if (FontRenderUtil.m237(FontRenderUtil.f1, var1, var2) <= var3) {
         return var1;
      } else {
         StringBuilder var4 = new StringBuilder();

         for (char var8 : var1.toCharArray()) {
            if (FontRenderUtil.m237(FontRenderUtil.f1, var4.toString() + var8 + "…", var2) > var3) {
               break;
            }

            var4.append(var8);
         }

         return var4 + "…";
      }
   }

   public boolean mouseClicked(Click click, boolean doubled) {
      float var3 = Render2DUtil.m3((float)click.x());
      float var4 = Render2DUtil.m151((float)click.y());
      this.f15 = SaveScreen$1.f1;

      for (Object[] var6 : this.f27) {
         if (m35(var3, var4, (Float)var6[0], (Float)var6[1], (Float)var6[2], (Float)var6[3])) {
            ((Runnable)var6[4]).run();
            return true;
         }
      }

      return super.mouseClicked(click, doubled);
   }

   public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
      this.f23 = Math.max(0.0F, this.f23 - (float)(verticalAmount * 18.0));
      return true;
   }

   public boolean keyPressed(KeyInput input) {
      int var2 = input.key();
      if (var2 == 256) {
         this.close();
         return true;
      } else {
         if (this.f15 != SaveScreen$1.f1) {
            String var3 = this.m18();
            if (var2 == 259 && !var3.isEmpty()) {
               this.m16(var3.substring(0, var3.length() - 1));
               return true;
            }

            if (var2 == 257 || var2 == 335) {
               if (this.f15 == SaveScreen$1.f4 && this.f17 >= 0) {
                  this.m996(this.f16.f2[this.f17]);
               }

               this.f15 = SaveScreen$1.f1;
               return true;
            }
         }

         return super.keyPressed(input);
      }
   }

   public boolean charTyped(CharInput input) {
      if (this.f15 != SaveScreen$1.f1 && input.isValidChar()) {
         String var2 = this.m18();
         if (var2.length() < 48) {
            this.m16(var2 + input.asString());
         }

         return true;
      } else {
         return super.charTyped(input);
      }
   }

   private String m18() {
      return switch (this.f15) {
         case f2 -> this.f18;
         case f3 -> this.f19;
         case f4 -> this.f20;
         default -> "";
      };
   }

   private void m16(String var1) {
      switch (this.f15) {
         case f2:
            this.f18 = var1;
            break;
         case f3:
            this.f19 = var1;
            break;
         case f4:
            this.f20 = var1;
      }
   }
}

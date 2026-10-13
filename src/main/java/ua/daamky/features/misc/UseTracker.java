package ua.daamky.features.misc;

import ua.daamky.events.PacketReceiveEvent;
import ua.daamky.events.RenderEvent;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;
import ua.daamky.system.events.EventHandler;
import ua.daamky.utils.render.Render2DUtil;
import ua.daamky.utils.render.fonts.FontRenderUtil;
import java.awt.Color;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;
import net.minecraft.network.packet.s2c.play.ItemPickupAnimationS2CPacket;
import net.minecraft.text.TextColor;

@NewFunction(
   I0 = "UseTracker",
   I00 = "Кто подобрал/использовал предмет",
   I000 = Category.MISC
)
public class UseTracker extends Module {
   private static final String[] f1 = new String[]{
      "незерит",
      "набор",
      "шар",
      "талисман",
      "зелье",
      "арбалет",
      "элитры",
      "фейерверк",
      "яблоко",
      "солнечн",
      "трезубец"
   };
   private static final long f2 = 3000L;
   private static final float f3 = 6.5F;
   private final List<UseTracker$1> f4 = new CopyOnWriteArrayList<>();

   @Override
   public void onDisable() {
      this.f4.clear();
      super.onDisable();
   }

   @EventHandler
   public void m594(PacketReceiveEvent var1) {
      if (!this.util.m81()) {
         if (var1.m581() instanceof ItemPickupAnimationS2CPacket var2) {
            Entity var14 = this.mc.world.getEntityById(var2.getEntityId());
            Entity var4 = this.mc.world.getEntityById(var2.getCollectorEntityId());
            if (var14 instanceof ItemEntity var5 && var4 instanceof PlayerEntity var6 && var6 != this.mc.player) {
               ItemStack var7 = var5.getStack().copy();
               String var8 = var7.getName().getString().replaceAll("(?i)§[0-9a-fk-orx]", "").toLowerCase(Locale.ROOT);

               for (String var12 : f1) {
                  if (var8.contains(var12)) {
                     var7.setCount(var2.getStackAmount());
                     this.f4.add(new UseTracker$1(var6.getName().getString(), var7, "Подобрал:"));
                     break;
                  }
               }
            }
         }

         if (var1.m581() instanceof EntityStatusS2CPacket var13) {
            if (var13.getStatus() == 9) {
               if (var13.getEntity(this.mc.world) instanceof PlayerEntity var18 && var18 != this.mc.player) {
                  ItemStack var20 = var18.getMainHandStack();
                  if (var20.isEmpty() || !var20.contains(DataComponentTypes.FOOD) && var20.getItem() != Items.POTION) {
                     var20 = var18.getOffHandStack();
                  }

                  if (!var20.isEmpty()) {
                     this.f4.add(new UseTracker$1(var18.getName().getString(), var20.copy(), "Использовал:"));
                  }
               }
            } else if (var13.getStatus() == 35 && var13.getEntity(this.mc.world) instanceof PlayerEntity var19 && var19 != this.mc.player) {
               this.f4
                  .add(
                     new UseTracker$1(
                        var19.getName().getString(), Items.TOTEM_OF_UNDYING.getDefaultStack(), "Потерял:"
                     )
                  );
            }
         }
      }
   }

   @EventHandler
   public void m330(RenderEvent var1) {
      DrawContext var2 = var1.m583();
      if (var2 != null && !this.f4.isEmpty()) {
         int var3 = Render2DUtil.m113();
         int var4 = Render2DUtil.m189();
         float var5 = (float)var4 / 2.0F + 20.0F;

         for (UseTracker$1 var7 : this.f4) {
            float var8 = var7.m329();
            if (var7.f5 && var8 <= 0.01F) {
               this.f4.remove(var7);
            } else {
               this.m990(var2, var7, var3, var5, var8);
               var5 += 16.0F * var8;
            }
         }
      }
   }

   private void m990(DrawContext var1, UseTracker$1 var2, int var3, float var4, float var5) {
      String var6 = var2.f1 + " " + var2.f3;
      String var7 = var2.f2.getName().getString();
      float var8 = FontRenderUtil.m235(var6, 6.5F);
      float var9 = FontRenderUtil.m235(var7, 6.5F);
      float var10 = 4.5F;
      float var11 = 13.0F;
      float var12 = 20.0F + var8 + var10 + var9 + 5.0F;
      float var13 = ((float)var3 - var12) / 2.0F;
      int var14 = (int)(255.0F * Math.max(0.0F, Math.min(1.0F, var5)));
      var1.getMatrices().pushMatrix();
      var1.getMatrices().translate(var13 + var12 / 2.0F, var4 + var11 / 2.0F);
      var1.getMatrices().scale(var5, var5);
      var1.getMatrices().translate(-(var13 + var12 / 2.0F), -(var4 + var11 / 2.0F));
      Render2DUtil.m198(var13, var4, var12, var11, 3.0F, 6.0F, var5, new Color(25, 25, 25, 150));
      var1.getMatrices().pushMatrix();
      var1.getMatrices().translate(var13 + 3.5F, var4 + 1.5F);
      var1.getMatrices().scale(0.6F, 0.6F);
      if (!Render2DUtil.m31()) {
         var1.drawItem(var2.f2, 0, 0);
      }

      var1.getMatrices().popMatrix();
      Render2DUtil.m195(var13 + 16.0F, var4 + 2.0F, 0.5F, var11 - 4.0F, 0.0F, new Color(125, 125, 125, var14));
      Color var15 = new Color(255, 255, 255, var14);
      int var16 = 16755200;
      TextColor var17 = var2.f2.getName().getStyle().getColor();
      if (var17 != null) {
         var16 = var17.getRgb();
      }

      Color var18 = new Color(var16 >> 16 & 0xFF, var16 >> 8 & 0xFF, var16 & 0xFF, var14);
      float var19 = var13 + 20.0F;
      float var20 = var4 + 2.75F;
      Render2DUtil.m205(var1, var19, var20, var6, 6.5F, var15);
      Render2DUtil.m205(var1, var19 + var8 + var10, var20, var7, 6.5F, var18);
      var1.getMatrices().popMatrix();
   }
}

package ua.daamky.features.render;

import ua.daamky.events.RenderEvent;
import ua.daamky.mixins.interfaces.IHandledScreen;
import ua.daamky.settings.BooleanSetting;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;
import ua.daamky.system.events.EventHandler;
import ua.daamky.utils.ProjectionUtil;
import java.util.List;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ContainerComponent;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix3x2fStack;

@NewFunction(
   I0 = "ShulkerPreview",
   I00 = "Показывает содержимое шалкеров в инвентаре и на земле",
   I000 = Category.RENDER
)
public class ShulkerPreview extends Module {
   private static final int f1 = 18;
   private static final int f2 = 8;
   private static final int f3 = 178;
   private static final int key = 70;
   private final BooleanSetting f4 = new BooleanSetting("На земле", true);

   public ShulkerPreview() {
      this.addSettings(new Setting[]{this.f4});
   }

   public void m1261(DrawContext var1, HandledScreen<?> var2, int var3, int var4) {
      Slot var5 = ((IHandledScreen)var2).getFocusedSlot();
      if (var5 != null && var5.hasStack()) {
         ItemStack var6 = var5.getStack();
         List var7 = this.m1263(var6);
         if (var7 != null) {
            float var8 = (float)(var3 + 8);
            float var9 = (float)(var4 - 70 - 4);
            if (var9 < 0.0F) {
               var9 = (float)(var4 + 12);
            }

            var8 = MathHelper.clamp(var8, 0.0F, (float)(var1.getScaledWindowWidth() - 178));
            this.m1262(var1, var7, var8, var9, 1.0F, true);
         }
      }
   }

   @EventHandler
   public void m330(RenderEvent var1) {
      if (this.f4.m6() && !this.util.m81()) {
         DrawContext var2 = var1.m583();
         if (var2 != null) {
            float var3 = this.mc.getRenderTickCounter().getTickProgress(false);

            for (ItemEntity var5 : this.mc.world.getEntitiesByClass(ItemEntity.class, this.mc.player.getBoundingBox().expand(64.0), var0 -> true)) {
               List var6 = this.m1263(var5.getStack());
               if (var6 != null) {
                  Vec3d var7 = new Vec3d(
                     MathHelper.lerp((double)var3, var5.lastX, var5.getX()),
                     MathHelper.lerp((double)var3, var5.lastY, var5.getY()) + 0.5,
                     MathHelper.lerp((double)var3, var5.lastZ, var5.getZ())
                  );
                  float[] var8 = ProjectionUtil.m224(var7);
                  if (var8 != null) {
                     float var9 = 0.5F;
                     this.m1262(var2, var6, var8[0] - 178.0F * var9 / 2.0F, var8[1] - 70.0F * var9 / 2.0F, var9, false);
                  }
               }
            }
         }
      }
   }

   private void m1262(DrawContext var1, List<ItemStack> var2, float var3, float var4, float var5, boolean var6) {
      Matrix3x2fStack var7 = var1.getMatrices();
      var7.pushMatrix();
      var7.translate(var3, var4);
      var7.scale(var5, var5);
      var1.fill(0, 0, 178, 70, -351137258);

      for (int var8 = 0; var8 < Math.min(var2.size(), 27); var8++) {
         ItemStack var9 = (ItemStack)var2.get(var8);
         if (var9 != null && !var9.isEmpty()) {
            int var10 = 8 + var8 % 9 * 18 + 1;
            int var11 = 8 + var8 / 9 * 18 + 1;
            var1.drawItem(var9, var10, var11);
            if (var6) {
               var1.drawStackOverlay(this.mc.textRenderer, var9, var10, var11);
            }
         }
      }

      var7.popMatrix();
   }

   private List<ItemStack> m1263(ItemStack var1) {
      if (var1 != null && !var1.isEmpty()) {
         if (var1.getItem() instanceof BlockItem var2 && var2.getBlock() instanceof ShulkerBoxBlock) {
            ContainerComponent var5 = (ContainerComponent)var1.get(DataComponentTypes.CONTAINER);
            if (var5 == null) {
               return null;
            }

            List var4 = var5.stream().toList();
            return var4.isEmpty() ? null : var4;
         }

         return null;
      } else {
         return null;
      }
   }
}

package ua.daamky.features.render;

import ua.daamky.events.RenderEvent;
import ua.daamky.gui.theme.ThemeManager;
import ua.daamky.mixins.interfaces.IWorld;
import ua.daamky.settings.BooleanSetting;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;
import ua.daamky.system.events.EventHandler;
import ua.daamky.utils.ProjectionUtil;
import ua.daamky.utils.render.Render2DUtil;
import ua.daamky.utils.render.fonts.FontRenderUtil;
import java.awt.Color;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.chunk.BlockEntityTickInvoker;

@NewFunction(
   I0 = "WardenESP",
   I00 = "Подсвечивает сундуки в городе варденов с таймером возрождения",
   I000 = Category.RENDER
)
public class WardenESP extends Module {
   private static final Pattern f1 = Pattern.compile("(\\d{1,2}):(\\d{2})");
   private static final int f2 = -2070;
   private static final int f3 = -1921;
   private static final int key = -2076;
   private static final int f4 = -1929;
   private static final int f5 = -60;
   private static final int f6 = -35;
   private final BooleanSetting f7 = new BooleanSetting("Рамка сундука", true);
   private final BooleanSetting f8 = new BooleanSetting("Таймер", true);
   private final BooleanSetting f9 = new BooleanSetting("Скан города (дальний)", true);

   public WardenESP() {
      this.addSettings(new Setting[]{this.f7, this.f8, this.f9});
   }

   @EventHandler
   public void m330(RenderEvent var1) {
      DrawContext var2 = var1.m583();
      if (var2 != null && !this.util.m81()) {
         float var3 = this.mc.getRenderTickCounter().getTickProgress(false);
         Color var4 = ThemeManager.m1379();
         HashMap var5 = new HashMap();

         for (Entity var7 : this.mc.world.getEntities()) {
            if (var7 instanceof ArmorStandEntity) {
               ArmorStandEntity var8 = (ArmorStandEntity)var7;
               String var9 = this.m58(var8.getName().getString());
               if (var9 != null) {
                  BlockPos var10 = this.m1248(var8.getBlockPos());
                  if (var10 != null) {
                     var5.put(var10, var9);
                  }
               }
            }
         }

         HashSet<BlockPos> var11 = new HashSet<>(var5.keySet());
         if (this.f9.m6()) {
            for (BlockEntityTickInvoker var14 : ((IWorld)this.mc.world).getBlockEntityTickers()) {
               if (!var14.isRemoved()) {
                  BlockPos var16 = var14.getPos();
                  if (this.m678(var16)
                     && (this.mc.world.getBlockState(var16).isOf(Blocks.CHEST) || this.mc.world.getBlockState(var16).isOf(Blocks.TRAPPED_CHEST))) {
                     var11.add(var16);
                  }
               }
            }
         }

         for (BlockPos var15 : var11) {
            if (this.f7.m6()) {
               this.m1246(var15, var4);
            }

            String var17 = (String)var5.get(var15);
            if (var17 != null && this.f8.m6()) {
               this.m1247(var2, var15, var17, var4);
            }
         }
      }
   }

   private boolean m678(BlockPos var1) {
      return var1.getX() >= -2070 && var1.getX() <= -1921 && var1.getZ() >= -2076 && var1.getZ() <= -1929 && var1.getY() >= -60 && var1.getY() <= -35;
   }

   private void m1246(BlockPos var1, Color var2) {
      float var3 = Float.MAX_VALUE;
      float var4 = Float.MAX_VALUE;
      float var5 = -Float.MAX_VALUE;
      float var6 = -Float.MAX_VALUE;
      boolean var7 = false;

      for (int var8 = 0; var8 <= 1; var8++) {
         for (int var9 = 0; var9 <= 1; var9++) {
            for (int var10 = 0; var10 <= 1; var10++) {
               float[] var11 = ProjectionUtil.m224(new Vec3d((double)(var1.getX() + var8), (double)(var1.getY() + var9), (double)(var1.getZ() + var10)));
               if (var11 != null) {
                  var7 = true;
                  var3 = Math.min(var3, var11[0]);
                  var4 = Math.min(var4, var11[1]);
                  var5 = Math.max(var5, var11[0]);
                  var6 = Math.max(var6, var11[1]);
               }
            }
         }
      }

      if (var7) {
         Render2DUtil.m202(var3, var4, var5 - var3, var6 - var4, 1.5F, 1.5F, var2);
      }
   }

   private void m1247(DrawContext var1, BlockPos var2, String var3, Color var4) {
      float[] var5 = ProjectionUtil.m224(new Vec3d((double)var2.getX() + 0.5, (double)var2.getY() + 1.4, (double)var2.getZ() + 0.5));
      if (var5 != null) {
         float var6 = 8.0F;
         float var7 = FontRenderUtil.m235(var3, var6);
         float var8 = Math.max(var7 + 16.0F, 44.0F);
         float var9 = 15.0F;
         float var10 = (float)this.mc.player.getEntityPos().distanceTo(Vec3d.ofCenter(var2));
         float var11 = MathHelper.clamp(1.0F - var10 / 40.0F, 0.55F, 1.0F);
         float var12 = var8 * var11;
         float var13 = var9 * var11;
         float var14 = var5[0] - var12 / 2.0F;
         float var15 = var5[1];
         Render2DUtil.m198(var14, var15, var12, var13, var13 / 2.0F, 8.0F, 1.0F, new Color(13, 18, 20, 220));
         Render2DUtil.m195(var14 + 5.0F * var11, var15 + (var13 - 5.0F * var11) / 2.0F, 5.0F * var11, 5.0F * var11, 2.5F * var11, var4);
         Render2DUtil.m205(var1, var14 + 13.0F * var11, var15 + (var13 - var6 * var11) / 2.0F + 0.5F, var3, var6 * var11, new Color(255, 255, 255));
      }
   }

   private String m58(String var1) {
      if (var1 != null && !var1.isBlank()) {
         Matcher var2 = f1.matcher(var1);
         return var2.find() ? var2.group() : null;
      } else {
         return null;
      }
   }

   private BlockPos m1248(BlockPos var1) {
      if (this.mc.world == null) {
         return null;
      } else {
         for (int var2 = 1; var2 <= 5; var2++) {
            BlockPos var3 = var1.down(var2);
            if (this.mc.world.getBlockState(var3).getBlock() instanceof ChestBlock) {
               return var3;
            }
         }

         return null;
      }
   }
}

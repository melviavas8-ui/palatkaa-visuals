package ua.daamky.features.render;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;

public class HitWave$1 {
   public final HitWave f4;
   public final BlockPos f1;
   public final long f2;
   private final List<HitWave$1$1> f3;

   public HitWave$1(HitWave var1, BlockPos var2, long var3) {
      this.f4 = var1;
      this.f3 = new ArrayList<>();
      this.f1 = var2;
      this.f2 = var3;
      int var5 = (int)var1.f10.getValue();

      for (int var6 = -var5; var6 <= var5; var6++) {
         for (int var7 = -var5; var7 <= var5; var7++) {
            float var8 = (float)(var6 * var6 + var7 * var7);
            if (!(var8 > (float)(var5 * var5))) {
               BlockPos var9 = var2.add(var6, 0, var7);
               BlockPos var10 = this.m1158(var9);
               if (var10 != null) {
                  BlockState var11 = var1.mc.world.getBlockState(var10);
                  VoxelShape var12 = var11.getOutlineShape(var1.mc.world, var10);
                  if (!var12.isEmpty()) {
                     this.f3.add(new HitWave$1$1(this, var10, var12.getBoundingBox(), (float)Math.sqrt((double)var8)));
                  }
               }
            }
         }
      }
   }

   public boolean m91() {
      return System.currentTimeMillis() - this.f2 > (long)(this.f4.f9.getValue() * 1000.0);
   }

   public void m114(WorldRenderContext var1) {
      if (this.f4.mc.world != null) {
         long var2 = System.currentTimeMillis() - this.f2;
         float var4 = (float)((double)var2 / (this.f4.f9.getValue() * 1000.0));
         float var5 = (float)((double)var4 * this.f4.f10.getValue());
         float var6 = (float)this.f4.f11.getValue();
         float var7 = (float)Math.pow((double)(1.0F - var4), 0.6);
         int var8 = 0;
         short var9 = 400;
         float var10 = (var5 - var6) * (var5 - var6);
         float var11 = (var5 + 0.5F) * (var5 + 0.5F);
         Color var12 = this.f4.m1159();
         int var13 = this.f4.f12.m6() ? HitWave.m1009(var12.getRed()) : var12.getRed();
         int var14 = this.f4.f12.m6() ? HitWave.m1009(var12.getGreen()) : var12.getGreen();
         int var15 = this.f4.f12.m6() ? HitWave.m1009(var12.getBlue()) : var12.getBlue();
         Color var16 = new Color(var13, var14, var15);
         Color var17 = new Color(HitWave.m11(var13), HitWave.m11(var14), HitWave.m11(var15));
         int var18 = (int)this.f4.f10.getValue();
         Vec3d var19 = var1.worldState().cameraRenderState.pos;

         for (HitWave$1$1 var21 : this.f3) {
            if (var8 >= var9) {
               break;
            }

            float var22 = var21.f3;
            if (!(var22 < var5 - var6) && !(var22 > var5 + 0.5F)) {
               var8++;
               float var23 = 1.0F - Math.abs(var22 - var5) / var6;
               var23 = Math.max(0.0F, Math.min(1.0F, var23)) * var7;
               if (var23 > 0.05F) {
                  Box var24 = var21.f2.offset(var21.f1).offset(-var19.x, -var19.y, -var19.z);
                  if (this.f4.f5.m6()) {
                     float var25 = (float)(this.f4.f8.getValue() * (double)var23);
                     this.f4.m1160(var1, var24, var16, var25);
                  }

                  if (this.f4.f6.m6()) {
                     this.f4.m1161(var1, var24, var17, var23);
                  }
               }
            }
         }
      }
   }

   public BlockPos m1158(BlockPos var1) {
      for (int var2 = 2; var2 >= -4; var2--) {
         BlockPos var3 = var1.up(var2);
         BlockState var4 = this.f4.mc.world.getBlockState(var3);
         BlockState var5 = this.f4.mc.world.getBlockState(var3.up());
         boolean var6 = !var4.getCollisionShape(this.f4.mc.world, var3).isEmpty();
         boolean var7 = !var5.getCollisionShape(this.f4.mc.world, var3.up()).isEmpty();
         if (var6 && !var7) {
            return var3;
         }
      }

      return null;
   }
}

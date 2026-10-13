package ua.daamky.features.render;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class Particles$1 {
   public Vec3d f1;
   public Vec3d f2;
   public final float f3;
   public float f4;
   public final float f5;
   public final long f6;
   public long f7;
   public final boolean f8;

   public Particles$1(Vec3d var1, Vec3d var2, float var3, float var4, float var5, long var6, boolean var8) {
      this.f1 = var1;
      this.f2 = var2;
      this.f3 = var3;
      this.f4 = var4;
      this.f5 = var5;
      this.f6 = var6;
      this.f7 = var6;
      this.f8 = var8;
   }

   public void m843(long var1) {
      if (Particles.f16 != null && Particles.f16.mc.world != null) {
         double var3 = Math.min((double)(var1 - this.f7) / 1.0E9, 0.05);
         this.f7 = var1;
         this.f2 = this.f2.add(0.0, -9.8 * var3, 0.0);
         Vec3d var5 = this.f1.add(this.f2.multiply(var3));
         BlockPos var6 = BlockPos.ofFloored(var5.x, var5.y - (double)(this.f3 * 0.45F), var5.z);
         if (!Particles.f16.mc.world.getBlockState(var6).isAir() && this.f2.y < 0.0) {
            var5 = new Vec3d(var5.x, (double)var6.getY() + 1.0 + (double)(this.f3 * 0.45F), var5.z);
            this.f2 = new Vec3d(this.f2.x * 0.72, Math.abs(this.f2.y) < 0.55 ? 0.0 : -this.f2.y * 0.32, this.f2.z * 0.72);
         }

         if (Math.abs(this.f2.y) < 0.02) {
            this.f2 = new Vec3d(this.f2.x * 0.985, this.f2.y, this.f2.z * 0.985);
         }

         this.f1 = var5;
         this.f4 = this.f4 + this.f5 * (float)var3;
      }
   }
}

package ua.daamky.utils.render.shaders;

import net.minecraft.client.render.VertexConsumer;
import net.minecraft.util.math.Vec3d;

public class Sampler0$1 {
   public final VertexConsumer f1;
   public final Vec3d f2;

   public Sampler0$1(VertexConsumer var1, Vec3d var2) {
      this.f1 = var1;
      this.f2 = var2;
   }

   public void m159(Vec3d var1, Vec3d var2, Vec3d var3, Vec3d var4, int var5, int var6, int var7, float var8) {
      this.m160(var1, var5, var6, var7, var8);
      this.m160(var2, var5, var6, var7, var8);
      this.m160(var3, var5, var6, var7, var8);
      this.m160(var4, var5, var6, var7, var8);
   }

   public void m160(Vec3d var1, int var2, int var3, int var4, float var5) {
      this.f1
         .vertex((float)(var1.x - this.f2.x), (float)(var1.y - this.f2.y), (float)(var1.z - this.f2.z))
         .color(var2, var3, var4, (int)(Math.clamp(var5, 0.0F, 1.0F) * 255.0F));
   }

   public void m161(Vec3d var1, Vec3d var2, int var3, int var4, int var5, float var6, float var7) {
      this.m160(var1, var3, var4, var5, var6);
      this.m160(var2, var3, var4, var5, var7);
   }
}

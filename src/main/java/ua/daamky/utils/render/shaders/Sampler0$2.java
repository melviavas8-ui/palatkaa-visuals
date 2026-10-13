package ua.daamky.utils.render.shaders;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector3f;

public class Sampler0$2 {
   public final VertexConsumer f1;
   public final Vec3d f2;
   public final Vector3f f3;
   public final Vector3f f4;

   public Sampler0$2(VertexConsumer var1, Vec3d var2, Vector3f var3, Vector3f var4) {
      this.f1 = var1;
      this.f2 = var2;
      this.f3 = var3;
      this.f4 = var4;
   }

   public void m162(Vec3d var1, float var2, float var3, float var4) {
      this.m163(var1, var2, var3, 255, 255, 255, var4);
   }

   public void m163(Vec3d var1, float var2, float var3, int var4, int var5, int var6, float var7) {
      this.m166(var1, var2, var3, var4, var5, var6, var7);
   }

   public void m164(Vec3d var1, float var2, float var3, float var4, float var5, float var6) {
      this.m165(var1, var2, var3, 255, 255, 255, var4, var5, var6);
   }

   public void m165(Vec3d var1, float var2, float var3, int var4, int var5, int var6, float var7, float var8, float var9) {
      if (!(var7 <= 0.0F)) {
         this.m166(var1, var2 * var8, var3, var4, var5, var6, var7 * var9);
         this.m166(var1, var2, var3, var4, var5, var6, var7);
      }
   }

   public void m166(Vec3d var1, float var2, float var3, int var4, int var5, int var6, float var7) {
      float var8 = var3 * (float) (Math.PI / 180.0);
      Vector3f var9 = new Vector3f(this.f3).mul((float)Math.cos((double)var8)).add(new Vector3f(this.f4).mul((float)Math.sin((double)var8))).mul(var2);
      Vector3f var10 = new Vector3f(this.f4).mul((float)Math.cos((double)var8)).sub(new Vector3f(this.f3).mul((float)Math.sin((double)var8))).mul(var2);
      float var11 = (float)(var1.x - this.f2.x);
      float var12 = (float)(var1.y - this.f2.y);
      float var13 = (float)(var1.z - this.f2.z);
      this.m167(var11 - var9.x - var10.x, var12 - var9.y - var10.y, var13 - var9.z - var10.z, 0.0F, 1.0F, var4, var5, var6, var7);
      this.m167(var11 - var9.x + var10.x, var12 - var9.y + var10.y, var13 - var9.z + var10.z, 0.0F, 0.0F, var4, var5, var6, var7);
      this.m167(var11 + var9.x + var10.x, var12 + var9.y + var10.y, var13 + var9.z + var10.z, 1.0F, 0.0F, var4, var5, var6, var7);
      this.m167(var11 + var9.x - var10.x, var12 + var9.y - var10.y, var13 + var9.z - var10.z, 1.0F, 1.0F, var4, var5, var6, var7);
   }

   public void m167(float var1, float var2, float var3, float var4, float var5, int var6, int var7, int var8, float var9) {
      this.f1
         .vertex(var1, var2, var3)
         .color(var6, var7, var8, (int)(Math.clamp(var9, 0.0F, 1.0F) * 255.0F))
         .texture(var4, var5)
         .overlay(OverlayTexture.DEFAULT_UV)
         .light(15728880)
         .normal(0.0F, 1.0F, 0.0F);
   }
}

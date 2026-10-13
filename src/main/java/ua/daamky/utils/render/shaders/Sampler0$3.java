package ua.daamky.utils.render.shaders;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.util.math.Vec3d;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class Sampler0$3 {
   public final VertexConsumer f1;
   public final Vec3d f2;

   public Sampler0$3(VertexConsumer var1, Vec3d var2) {
      this.f1 = var1;
      this.f2 = var2;
   }

   public void m168(Vec3d var1, float var2, float var3, float var4, float var5, int var6, int var7, int var8, float var9) {
      if (!(var9 <= 0.0F) && !(var2 <= 0.0F)) {
         float var10 = var2 * 0.5F;
         Quaternionf var11 = new Quaternionf()
            .rotateX(var3 * (float) (Math.PI / 180.0))
            .rotateY(var4 * (float) (Math.PI / 180.0))
            .rotateZ(var5 * (float) (Math.PI / 180.0));
         Vector3f[] var12 = new Vector3f[]{
            this.m169(-var10, -var10, -var10, var11, var1),
            this.m169(var10, -var10, -var10, var11, var1),
            this.m169(var10, var10, -var10, var11, var1),
            this.m169(-var10, var10, -var10, var11, var1),
            this.m169(-var10, -var10, var10, var11, var1),
            this.m169(var10, -var10, var10, var11, var1),
            this.m169(var10, var10, var10, var11, var1),
            this.m169(-var10, var10, var10, var11, var1)
         };
         this.m170(var12[0], var12[1], var12[2], var12[3], var6, var7, var8, var9);
         this.m170(var12[5], var12[4], var12[7], var12[6], var6, var7, var8, var9);
         this.m170(var12[4], var12[0], var12[3], var12[7], var6, var7, var8, var9);
         this.m170(var12[1], var12[5], var12[6], var12[2], var6, var7, var8, var9);
         this.m170(var12[3], var12[2], var12[6], var12[7], var6, var7, var8, var9);
         this.m170(var12[4], var12[5], var12[1], var12[0], var6, var7, var8, var9);
      }
   }

   public Vector3f m169(float var1, float var2, float var3, Quaternionf var4, Vec3d var5) {
      Vector3f var6 = new Vector3f(var1, var2, var3).rotate(var4);
      var6.add((float)(var5.x - this.f2.x), (float)(var5.y - this.f2.y), (float)(var5.z - this.f2.z));
      return var6;
   }

   public void m170(Vector3f var1, Vector3f var2, Vector3f var3, Vector3f var4, int var5, int var6, int var7, float var8) {
      this.m171(var1, 0.0F, 1.0F, var5, var6, var7, var8);
      this.m171(var2, 1.0F, 1.0F, var5, var6, var7, var8);
      this.m171(var3, 1.0F, 0.0F, var5, var6, var7, var8);
      this.m171(var4, 0.0F, 0.0F, var5, var6, var7, var8);
   }

   public void m171(Vector3f var1, float var2, float var3, int var4, int var5, int var6, float var7) {
      this.f1
         .vertex(var1.x, var1.y, var1.z)
         .color(var4, var5, var6, (int)(Math.clamp(var7, 0.0F, 1.0F) * 255.0F))
         .texture(var2, var3)
         .overlay(OverlayTexture.DEFAULT_UV)
         .light(15728880)
         .normal(0.0F, 1.0F, 0.0F);
   }
}

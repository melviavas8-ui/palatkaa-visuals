package ua.daamky.utils.render.shaders;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.util.math.Vec3d;

public class Sampler0$4 {
   public final VertexConsumer f1;
   public final Vec3d f2;

   public Sampler0$4(VertexConsumer var1, Vec3d var2) {
      this.f1 = var1;
      this.f2 = var2;
   }

   public void m163(Vec3d var1, float var2, float var3, int var4, int var5, int var6, float var7) {
      if (!(var7 <= 0.0F) && !(var2 <= 0.0F)) {
         float var8 = var2 * 0.5F;
         float var9 = var3 * (float) (Math.PI / 180.0);
         float var10 = (float)Math.cos((double)var9);
         float var11 = (float)Math.sin((double)var9);
         this.m172(var1, -var8, -var8, var10, var11, 0.0F, 1.0F, var4, var5, var6, var7);
         this.m172(var1, -var8, var8, var10, var11, 0.0F, 0.0F, var4, var5, var6, var7);
         this.m172(var1, var8, var8, var10, var11, 1.0F, 0.0F, var4, var5, var6, var7);
         this.m172(var1, var8, -var8, var10, var11, 1.0F, 1.0F, var4, var5, var6, var7);
      }
   }

   public void m172(Vec3d var1, float var2, float var3, float var4, float var5, float var6, float var7, int var8, int var9, int var10, float var11) {
      float var12 = var2 * var4 - var3 * var5;
      float var13 = var2 * var5 + var3 * var4;
      this.f1
         .vertex((float)(var1.x - this.f2.x) + var12, (float)(var1.y - this.f2.y), (float)(var1.z - this.f2.z) + var13)
         .color(var8, var9, var10, (int)(Math.clamp(var11, 0.0F, 1.0F) * 255.0F))
         .texture(var6, var7)
         .overlay(OverlayTexture.DEFAULT_UV)
         .light(15728880)
         .normal(0.0F, 1.0F, 0.0F);
   }
}

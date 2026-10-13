package ua.daamky.utils;

import ua.daamky.utils.client.UserProfile;
import ua.daamky.utils.player.RotationUtil;
import ua.daamky.utils.player.RotationVec;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;

public class NeuroMovementModel {
   private static final MinecraftClient f1 = MinecraftClient.getInstance();
   private static final int f2 = 20;
   public static final int f3 = 12000;
   private static final int f4 = 24000;
   private static final int f5 = 2000;
   private static final float f6 = 60.0F;
   private static final float f7 = 0.9F;
   private static final float f8 = 0.4F;
   private static final float f9 = 1.0F;
   private static final float f10 = 90.0F;
   private static final float f11 = 120.0F;
   private static final float f12 = 15.0F;
   private static final float f13 = 8.0F;
   private static final float f14 = 22.0F;
   private static final float f15 = 60.0F;
   public static final int f16 = 5;
   private int f17 = 0;
   private final List<MotionSnapshot> f18 = new ArrayList<>();
   private boolean f19 = false;
   private boolean f20 = false;
   private String f21 = null;
   private String f22 = null;
   private int f23 = 0;
   private String f24 = "§7Готов";
   private int f25 = -1;
   private float f26;
   private float f27;
   private boolean f28 = false;
   private int f29 = 0;
   private LivingEntity f30 = null;
   private float f31;
   private float f32;
   private boolean f33 = false;
   private boolean f34 = true;
   private List<int[]> f35 = null;
   private double[] f36;
   private double[] f37;
   private double[] f38;
   private int f39 = 0;
   private int f40 = 0;
   private static final int f41 = 15;
   private boolean f42 = false;
   private boolean f43 = false;
   private int f44 = 0;
   private int f45 = 0;
   private static final int f46 = 8;
   private static final long f47 = 180000L;
   private static final int f48 = 400;
   private static final float f49 = 70.0F;
   private static final float f50 = 100.0F;
   private static final int f51 = 20;
   private static final int f52 = 6;
   private static final float f53 = 0.15F;
   private static final int f54 = 60;
   private static final float f55 = 110.0F;
   private static final float f56 = 2.5F;
   private static final float f57 = 0.12F;
   private boolean f58 = false;
   private int f59 = 0;
   private int f60 = 0;
   private int f61 = 0;
   private int f62 = 0;
   private String f63;
   private List<MotionSnapshot> f64;
   private boolean[] f65;
   private int f66;
   private int f67;
   private int f68;
   private int f69;
   private int f70;
   private int f71;
   private int f72;
   private int f73;
   private int f74;
   private int f75;
   private int f76;
   private long f77;
   private float f78;
   private final ArrayDeque<String> f79 = new ArrayDeque<>();
   private final List<String> f80 = new ArrayList<>();
   private static final String[] f81;

   public void m736(LivingEntity var1, float var2, float var3) {
      if (this.f19 && var1 != null && f1.player != null) {
         int var4 = f1.player.age;
         if (var4 != this.f25) {
            boolean var5 = this.f28 && var4 == this.f25 + 1;
            this.f25 = var4;
            float var6 = 0.0f;
            if (var5) {
               float var7 = MathHelper.wrapDegrees(var2 - this.f26);
               float var8 = var3 - this.f27;
               var6 = (float)Math.hypot((double)var7, (double)var8);
            }

            this.f26 = var2;
            this.f27 = var3;
            this.f28 = true;
            RotationVec var17 = RotationUtil.m417(var1.getEyePos());
            float var18 = MathHelper.wrapDegrees(var2 - var17.m329());
            float var9 = var3 - var17.m271();
            double var10 = f1.player.getEyePos().distanceTo(var1.getEyePos());
            boolean var12 = f1.player.fallDistance > 0.0 && !f1.player.isOnGround();
            double var13 = var1.getVelocity().horizontalLength();
            String var15 = var1 instanceof PlayerEntity ? "player" : "mob";
            float var16 = (float)f1.player.getVelocity().horizontalLength();
            this.f18.add(new MotionSnapshot(var18, var9, var10, var12, var13, var15, var6, var16));
            this.f23++;
            if (this.f18.size() > 24000) {
               this.f18.subList(0, 2000).clear();
               this.f29 = Math.max(0, this.f29 - 2000);
            }
         }
      }
   }

   public void m63() {
      if (this.f19 && f1.player != null && f1.world != null) {
         PlayerEntity var1 = null;
         double var2 = 100.0;

         for (PlayerEntity var5 : f1.world.getPlayers()) {
            if (var5 != f1.player) {
               double var6 = f1.player.squaredDistanceTo(var5);
               if (var6 < var2) {
                  var2 = var6;
                  var1 = var5;
               }
            }
         }

         if (var1 != null) {
            this.m736(var1, f1.player.getYaw(), f1.player.getPitch());
         }
      }
   }

   public RotationVec m737(LivingEntity var1) {
      if (this.f20 && var1 != null && f1.player != null) {
         int var2 = this.f18.size();
         if (var2 == 0) {
            return null;
         } else {
            boolean var3 = var1 != this.f30;
            boolean var4 = false;
            if (this.f34) {
               if (this.f35 == null || this.f36 == null) {
                  this.m738();
               }

               this.f44++;
               boolean var5 = f1.player.getVelocity().horizontalLength() > 0.059999999999999998;
               boolean var6 = this.f42 && var5 != this.f43 && this.f44 - this.f45 > 8;
               if (var3 || !this.f42 || var6) {
                  this.m739(var1);
                  this.f43 = var5;
                  this.f45 = this.f44;
                  this.f42 = true;
                  var4 = true;
               }
            }

            if (this.f29 < 0 || this.f29 >= var2) {
               this.f29 = 0;
            }

            int var28 = this.f29;
            MotionSnapshot var29 = this.f18.get(var28);
            this.f29++;
            boolean var7 = var28 > 0 && !var3 && (!var4 || var28 != this.f39);
            MotionSnapshot var8 = var7 ? this.f18.get(var28 - 1) : null;
            RotationVec var9 = RotationUtil.m6() ? RotationUtil.m415() : new RotationVec(f1.player.getYaw(), f1.player.getPitch());
            RotationVec var10 = RotationUtil.m417(var1.getEyePos());
            float var11 = var29.m329();
            float var12 = var29.m271();
            float var13 = var10.m329() + var11;
            float var14 = MathHelper.clamp(var10.m271() + var12, -90.0f, 90.0f);
            float var15 = MathHelper.wrapDegrees(var13 - var9.m329());
            float var16 = var14 - var9.m271();
            float var17 = 0.0f;
            float var18 = 0.0f;
            if (this.f33 && !var3) {
               float var19 = MathHelper.wrapDegrees(var10.m329() - this.f31);
               float var20 = var10.m271() - this.f32;
               if ((float)Math.hypot((double)var19, (double)var20) <= 90.0f) {
                  var17 = var19;
                  var18 = var20;
               }
            }

            this.f31 = var10.m329();
            this.f32 = var10.m271();
            this.f33 = true;
            float var30 = var15 - var17;
            float var31 = var16 - var18;
            float var21 = (float)Math.hypot((double)var30, (double)var31);
            float var22 = 1.0f;
            if (var7) {
               float var23 = MathHelper.wrapDegrees(var11 - var8.m329());
               float var24 = var12 - var8.m271();
               float var25 = (float)Math.hypot((double)var23, (double)var24);
               if (var25 > 120.0f) {
                  var22 = 60.0f;
               } else if (var25 > 22.0f) {
                  var22 = Math.max(60.0f, var25 * 0.899999976f + 0.400000006f);
               } else {
                  var22 = Math.max(1.0f, var25 * 0.899999976f + 0.400000006f);
               }
            }

            float var32 = var21 > 15.0f ? Math.max(var22, 8.0f) : var22;
            if (var21 > var32 && var21 > 0.00100000005f) {
               float var33 = var32 / var21;
               var30 *= var33;
               var31 *= var33;
            }

            float var34 = var17 + var30;
            float var35 = var18 + var31;
            if (var3) {
               float var26 = (float)Math.hypot((double)var34, (double)var35);
               if (var26 > 60.0f && var26 > 0.00100000005f) {
                  float var27 = 60.0f / var26;
                  var34 *= var27;
                  var35 *= var27;
               }
            }

            float var36 = var9.m329() + var34;
            float var38 = MathHelper.clamp(var9.m271() + var35, -90.0f, 90.0f);
            var36 = this.m614(var9.m329(), var36);
            var38 = MathHelper.clamp(this.m614(var9.m271(), var38), -90.0f, 90.0f);
            this.f30 = var1;
            return new RotationVec(var36, var38);
         }
      } else {
         return null;
      }
   }

   private float m614(float var1, float var2) {
      double var3 = (Double)f1.options.getMouseSensitivity().getValue() * 0.59999999999999998 + 0.20000000000000001;
      double var5 = var3 * var3 * var3 * 8.0;
      if (var5 <= 0.0) {
         return var2;
      } else {
         double var7 = (double)(var2 - var1);
         return (float)((double)var1 + (double)Math.round(var7 / var5 / 0.14999999999999999) * var5 * 0.14999999999999999);
      }
   }

   private void m738() {
      this.f35 = new ArrayList<>();
      int var1 = this.f18.size();
      int var2 = 0;

      while (var2 < var1) {
         int var3;
         for (var3 = var2 + 1; var3 < var1; var3++) {
            MotionSnapshot var4 = this.f18.get(var3 - 1);
            MotionSnapshot var5 = this.f18.get(var3);
            float var6 = (float)Math.hypot((double)MathHelper.wrapDegrees(var5.m329() - var4.m329()), (double)(var5.m271() - var4.m271()));
            if (var6 > 120.0f) {
               break;
            }
         }

         List var10000 = this.f35;
         int[] var10001 = new int[2];
         var10001[0] = var2;
         var10001[1] = var3;
         var10000.add(var10001);
         var2 = var3;
      }

      int var15 = this.f35.size();
      this.f36 = new double[var15];
      this.f37 = new double[var15];
      this.f38 = new double[var15];

      for (int var16 = 0; var16 < var15; var16++) {
         int[] var17 = this.f35.get(var16);
         double var18 = 0.0;
         double var8 = 0.0;
         double var10 = 0.0;
         int var12 = var17[1] - var17[0];

         for (int var13 = var17[0]; var13 < var17[1]; var13++) {
            MotionSnapshot var14 = this.f18.get(var13);
            var18 += var14.m353();
            var8 += var14.m769();
            var10 += (double)var14.m525();
         }

         this.f36[var16] = var12 > 0 ? var18 / (double)var12 : 0.0;
         this.f37[var16] = var12 > 0 ? var8 / (double)var12 : 0.0;
         this.f38[var16] = var12 > 0 ? var10 / (double)var12 : 0.0;
      }
   }

   private void m739(LivingEntity var1) {
      if (this.f35 == null || this.f36 == null) {
         this.m738();
      }

      if (this.f35.isEmpty()) {
         this.f39 = 0;
         this.f40 = this.f18.size();
         this.f29 = 0;
      } else {
         double var2 = f1.player.getEyePos().distanceTo(var1.getEyePos());
         double var4 = var1.getVelocity().horizontalLength();
         double var6 = f1.player.getVelocity().horizontalLength();
         boolean var8 = var6 > 0.059999999999999998;
         int var9 = this.f35.get(0)[0];
         int var10 = this.f35.get(0)[1];
         double var11 = 1.7976931348623157e+308;

         for (int var13 = 0; var13 < this.f35.size(); var13++) {
            int[] var14 = this.f35.get(var13);
            if (var14[1] - var14[0] >= 15) {
               double var15 = this.f36[var13] - var2;
               double var17 = this.f37[var13] - var4;
               double var19 = this.f38[var13] - var6;
               boolean var21 = this.f38[var13] > 0.059999999999999998;
               double var22 = var15 * var15 * 1.0
                  + var17 * var17 * 30.0
                  + var19 * var19 * 120.0;
               if (var21 != var8) {
                  var22 += 500.0;
               }

               if (var22 < var11) {
                  var11 = var22;
                  var9 = var14[0];
                  var10 = var14[1];
               }
            }
         }

         this.f39 = var9;
         this.f40 = var10;
         this.f29 = var9;
      }
   }

   private void m740() {
      this.f29 = 0;
      this.f30 = null;
      this.f33 = false;
      this.f35 = null;
      this.f36 = null;
      this.f37 = null;
      this.f38 = null;
      this.f39 = 0;
      this.f40 = 0;
      this.f42 = false;
      this.f43 = false;
      this.f44 = 0;
      this.f45 = 0;
   }

   public boolean m81() {
      return this.f34;
   }

   public void m61(boolean var1) {
      this.f34 = var1;
      this.f42 = false;
   }

   public boolean m6() {
      return this.f58;
   }

   public String m30() {
      return this.f79.poll();
   }

   public List<String> m483() {
      return this.f80;
   }

   public String m39() {
      return this.f22;
   }

   public int m102() {
      return this.f17;
   }

   public boolean m276() {
      return this.f22 != null && this.f17 == 5;
   }

   public boolean m20(String var1) {
      return this.f22 != null && this.f22.equalsIgnoreCase(var1);
   }

   public boolean m17(String var1) {
      if (this.f18.isEmpty()) {
         return false;
      } else {
         this.f64 = new ArrayList<>(this.f18);
         this.f63 = var1;
         this.f58 = true;
         this.f59 = 0;
         this.f60 = 0;
         this.f61 = 0;
         this.f62 = 0;
         this.f66 = this.f67 = this.f68 = this.f69 = 0;
         this.f70 = this.f71 = this.f72 = this.f73 = 0;
         this.f74 = this.f75 = this.f76 = 0;
         this.f77 = System.currentTimeMillis();
         this.f78 = 0.0f;
         this.f65 = null;
         this.f80.clear();
         this.f79.clear();
         this.f20 = false;
         this.f22 = null;
         this.f17 = 0;
         this.f79.add("§fЧитаю запись §e" + var1 + " §7(" + this.f64.size() + " сэмплов)");
         this.f79.add("§7Этапов разбора: §f" + f81.length + "§7, времени примерно §f3 мин§7. Разбор идёт в фоне, играть можно.");
         return true;
      }
   }

   private int m741() {
      int var1 = this.f64.size();
      if (var1 == 0) {
         return 1;
      } else {
         int var2 = Math.max(1, this.m742());
         int var3 = Math.max(1, var2 * var1 - this.f60);
         long var4 = 180000L - (System.currentTimeMillis() - this.f77);
         int var6 = (int)Math.max(1L, var4 / 50L);
         return Math.max(1, Math.min(400, var3 / var6));
      }
   }

   private int m742() {
      int var1 = 0;
      if (this.f59 <= 0) {
         var1++;
      }

      if (this.f59 <= 1) {
         var1++;
      }

      if (this.f59 <= 2) {
         var1 += Math.max(0, 6 - this.f61);
      }

      if (this.f59 <= 6) {
         var1++;
      }

      if (this.f59 <= 8) {
         var1++;
      }

      return var1;
   }

   private void m743() {
      if (!this.f64.isEmpty()) {
         int var1 = (int)((long)this.f60 * 100L / (long)this.f64.size());
         if (var1 >= this.f62 + 25 && var1 < 100) {
            this.f62 = var1 - var1 % 25;
            String var2 = this.f59 < f81.length ? f81[this.f59] : "финал";
            this.f79.add("§7" + var2 + "... §f" + this.f62 + "%");
         }
      }
   }

   private List<int[]> m744() {
      ArrayList var1 = new ArrayList();
      int var2 = 0;

      while (var2 < this.f64.size()) {
         int var3 = var2 + 1;

         while (var3 < this.f64.size() && !this.f65[var3]) {
            var3++;
         }

         int[] var10001 = new int[2];
         var10001[0] = var2;
         var10001[1] = var3;
         var1.add(var10001);
         var2 = var3;
      }

      return var1;
   }

   private float[] m745(int[] var1) {
      float var2 = 0.0f;
      float var3 = 0.0f;
      float var4 = 0.0f;
      int var5 = var1[1] - var1[0];

      for (int var6 = var1[0]; var6 < var1[1]; var6++) {
         MotionSnapshot var7 = this.f64.get(var6);
         var2 += var7.m329();
         var3 += var7.m271();
         var4 += var7.m524();
      }

      float[] var10000;
      if (var5 > 0) {
         var10000 = new float[3];
         var10000[0] = var2 / (float)var5;
         var10000[1] = var3 / (float)var5;
         var10000[2] = var4 / (float)var5;
      } else {
         var10000 = new float[3];
         var10000[0] = 0.0f;
         var10000[1] = 0.0f;
         var10000[2] = 0.0f;
      }

      return var10000;
   }

   private float m746(int[] var1) {
      int var2 = var1[1] - var1[0];
      if (var2 <= 0) {
         return 0.0f;
      } else {
         float var3 = 0.0f;
         float var4 = 0.0f;
         float var5 = 0.0f;
         float var6 = -1.0f;

         for (int var7 = var1[0]; var7 < var1[1]; var7++) {
            MotionSnapshot var8 = this.f64.get(var7);
            var3 += var8.m524();
            var4 += (float)var8.m353();
            if (var6 >= 0.0f) {
               var5 += Math.abs(var8.m524() - var6);
            }

            var6 = var8.m524();
         }

         float var13 = var3 / (float)var2;
         float var14 = var4 / (float)var2;
         float var9 = var5 / (float)Math.max(1, var2 - 1);
         float var10 = Math.min(1.0f, (float)var2 / 200.0f);
         float var11 = Math.max(0.0f, 1.0f - var14 / 6.0f);
         float var12 = Math.min(1.0f, var13 / 4.0f);
         return var10 + var11 + var12 - Math.min(1.0f, var9 / 12.0f);
      }
   }

   private void m747(List<int[]> var1, boolean[] var2, boolean var3) {
      ArrayList var4 = new ArrayList(this.f64.size());
      ArrayList var5 = new ArrayList(this.f64.size());

      for (int var6 = 0; var6 < var1.size(); var6++) {
         int[] var7 = (int[])var1.get(var6);
         if (var2[var6]) {
            if (var3) {
               this.f74++;
            } else {
               this.f75++;
            }
         } else {
            for (int var8 = var7[0]; var8 < var7[1]; var8++) {
               var4.add(this.f64.get(var8));
               var5.add(var8 == var7[0]);
            }
         }
      }

      if (!var4.isEmpty()) {
         this.f64 = var4;
         this.f65 = new boolean[var4.size()];

         for (int var9 = 0; var9 < var5.size(); var9++) {
            this.f65[var9] = (Boolean)var5.get(var9);
         }
      }
   }

   private void m748() {
      this.f59++;
      this.f60 = 0;
      this.f61 = 0;
      this.f62 = 0;
   }

   public void m277() {
      if (this.f58) {
         switch (this.f59) {
            case 0:
               int var14 = Math.min(this.f60 + this.m741(), this.f64.size());

               for (int var23 = this.f60; var23 < var14; var23++) {
                  MotionSnapshot var31 = this.f64.get(var23);
                  float var40 = var31.m329();
                  float var50 = var31.m271();
                  boolean var59 = Float.isNaN(var40)
                     || Float.isNaN(var50)
                     || Float.isInfinite(var40)
                     || Float.isInfinite(var50)
                     || Math.abs(var40) > 180.100006f
                     || Math.abs(var50) > 90.0999985f
                     || Double.isNaN(var31.m353())
                     || var31.m353() < 0.0
                     || var31.m353() > 8.0;
                  if (var59) {
                     this.f64.set(var23, null);
                     this.f66++;
                  }
               }

               this.f60 = var14;
               this.m743();
               if (this.f60 >= this.f64.size()) {
                  this.f64.removeIf(Objects::isNull);
                  this.f79.add("§fПроверено сэмплов, отбраковано: §c" + this.f66);
                  this.m748();
               }
               break;
            case 1:
               if (this.f65 == null) {
                  this.f65 = new boolean[this.f64.size()];
               }

               int var13 = Math.min(this.f60 + this.m741(), this.f64.size());

               for (int var22 = this.f60; var22 < var13; var22++) {
                  if (var22 == 0) {
                     this.f65[0] = true;
                     this.f69++;
                  } else {
                     MotionSnapshot var30 = this.f64.get(var22 - 1);
                     MotionSnapshot var39 = this.f64.get(var22);
                     float var49 = Math.abs(MathHelper.wrapDegrees(var39.m329() - var30.m329()));
                     float var58 = Math.abs(var39.m271() - var30.m271());
                     boolean var64 = var39.m524() <= 0.00999999978f && (var49 > 70.0f || var58 > 70.0f)
                        || var49 > 100.0f
                        || var58 > 100.0f;
                     if (var64) {
                        this.f65[var22] = true;
                        this.f69++;
                     }
                  }
               }

               this.f60 = var13;
               this.m743();
               if (this.f60 >= this.f64.size()) {
                  this.f79.add("§fСвязных отрезков боя: §e" + this.f69);
                  this.m748();
               }
               break;
            case 2:
               int var12 = Math.min(this.f60 + this.m741(), this.f64.size());

               for (int var20 = Math.max(1, this.f60); var20 < var12; var20++) {
                  if (!this.f65[var20]) {
                     MotionSnapshot var29 = this.f64.get(var20 - 1);
                     MotionSnapshot var38 = this.f64.get(var20);
                     float var47 = MathHelper.wrapDegrees(var38.m329() - var29.m329());
                     float var56 = var38.m271() - var29.m271();
                     float var63 = (float)Math.hypot((double)var47, (double)var56);
                     if (var63 > 70.0f && var20 + 1 < this.f64.size() && !this.f65[var20 + 1]) {
                        MotionSnapshot var66 = this.f64.get(var20 + 1);
                        float var67 = (float)Math.hypot((double)MathHelper.wrapDegrees(var66.m329() - var29.m329()), (double)(var66.m271() - var29.m271()));
                        if (var67 < var63 * 0.5f) {
                           float var69 = MathHelper.wrapDegrees(
                              var29.m329() + MathHelper.wrapDegrees(var66.m329() - var29.m329()) / 2.0f
                           );
                           float var70 = (var29.m271() + var66.m271()) / 2.0f;
                           var38 = new MotionSnapshot(var69, var70, var38.m353(), var38.m31(), var38.m769(), var38.m275(), 0.0f);
                           this.f64.set(var20, var38);
                           this.f67++;
                           var47 = MathHelper.wrapDegrees(var38.m329() - var29.m329());
                           var56 = var38.m271() - var29.m271();
                           var63 = (float)Math.hypot((double)var47, (double)var56);
                        }
                     }

                     if (var38.m524() <= 0.00999999978f) {
                        this.f64.set(var20, new MotionSnapshot(var38.m329(), var38.m271(), var38.m353(), var38.m31(), var38.m769(), var38.m275(), var63));
                        this.f68++;
                     }
                  }
               }

               this.f60 = var12;
               this.m743();
               if (this.f60 >= this.f64.size()) {
                  this.f61++;
                  boolean var21 = this.f67 > this.f76;
                  this.f76 = this.f67;
                  if (this.f61 < 6 && var21) {
                     this.f60 = 0;
                     this.f62 = 0;
                     this.f79.add("§7чистка выбросов: проход §f" + (this.f61 + 1) + "§7/6");
                  } else {
                     this.f79.add("§fСглажено рывков: §e" + this.f67 + "§f, восстановлено скоростей: §e" + this.f68);
                     this.m748();
                  }
               }
               break;
            case 3:
               ArrayList var19 = new ArrayList(this.f64.size());
               ArrayList var28 = new ArrayList(this.f64.size());
               int var37 = 0;

               while (var37 < this.f64.size()) {
                  int var45 = var37;

                  while (
                     var45 < this.f64.size()
                        && this.f64.get(var45).m524() < 0.150000006f
                        && this.f64.get(var45).m353() > 4.0
                  ) {
                     var45++;
                  }

                  int var55 = var45 - var37;
                  if (var55 >= 60) {
                     this.f71 += var55;
                     var37 = var45;
                     if (var45 < this.f64.size()) {
                        var19.add(this.f64.get(var45));
                        var28.add(true);
                        var37 = var45 + 1;
                     }
                  } else {
                     var19.add(this.f64.get(var37));
                     var28.add(this.f65[var37]);
                     var37++;
                  }
               }

               this.f64 = var19;
               this.f65 = new boolean[var19.size()];

               for (int var46 = 0; var46 < var28.size(); var46++) {
                  this.f65[var46] = (Boolean)var28.get(var46);
               }

               this.f79.add("§fВырезано простоя: §e" + this.f71 + " §7сэмплов");
               this.m748();
               break;
            case 4:
               ArrayList var18 = new ArrayList(this.f64.size());
               ArrayList var27 = new ArrayList(this.f64.size());
               int var36 = 0;

               while (var36 < this.f64.size()) {
                  int var43 = var36 + 1;

                  while (var43 < this.f64.size() && !this.f65[var43]) {
                     var43++;
                  }

                  int var54 = var43 - var36;
                  if (var54 >= 20) {
                     for (int var62 = var36; var62 < var43; var62++) {
                        var18.add(this.f64.get(var62));
                        var27.add(var62 == var36);
                     }
                  } else {
                     this.f70 += var54;
                  }

                  var36 = var43;
               }

               this.f64 = var18;
               this.f65 = new boolean[var18.size()];

               for (int var44 = 0; var44 < var27.size(); var44++) {
                  this.f65[var44] = (Boolean)var27.get(var44);
               }

               this.f79.add("§fОтброшено коротких обрывков: §e" + this.f70 + " §7сэмплов");
               this.m748();
               break;
            case 5:
               ArrayList var17 = new ArrayList(this.f64.size());
               ArrayList var26 = new ArrayList(this.f64.size());
               double var35 = 0.0;
               int var53 = 0;
               int var61 = 0;

               while (var61 < this.f64.size()) {
                  int var8 = var61 + 1;

                  while (var8 < this.f64.size() && !this.f65[var8]) {
                     var8++;
                  }

                  float var9 = 0.0f;

                  for (int var10 = var61; var10 < var8; var10++) {
                     var9 += this.f64.get(var10).m524();
                  }

                  float var68 = var8 - var61 > 0 ? var9 / (float)(var8 - var61) : 0.0f;
                  if (var68 < 0.150000006f) {
                     this.f72++;
                  } else {
                     for (int var11 = var61; var11 < var8; var11++) {
                        var17.add(this.f64.get(var11));
                        var26.add(var11 == var61);
                     }

                     var35 += (double)var68;
                     var53++;
                  }

                  var61 = var8;
               }

               this.f78 = var53 > 0 ? (float)(var35 / (double)var53) : 0.0f;
               this.f64 = var17;
               this.f65 = new boolean[var17.size()];

               for (int var65 = 0; var65 < var26.size(); var65++) {
                  this.f65[var65] = (Boolean)var26.get(var65);
               }

               this.f79
                  .add(
                     "§fВыброшено замерших отрезков: §e"
                        + this.f72
                        + "§f, средняя дрожь руки: §e"
                        + String.format("%.2f", this.f78)
                        + "°"
                  );
               this.m748();
               break;
            case 6:
               List var16 = this.m744();
               boolean[] var25 = new boolean[var16.size()];

               for (int var34 = 0; var34 < var16.size(); var34++) {
                  if (!var25[var34]) {
                     float[] var42 = this.m745((int[])var16.get(var34));

                     for (int var52 = var34 + 1; var52 < var16.size(); var52++) {
                        if (!var25[var52]) {
                           float[] var60 = this.m745((int[])var16.get(var52));
                           if (Math.abs(var42[0] - var60[0]) < 2.5f
                              && Math.abs(var42[1] - var60[1]) < 2.5f
                              && Math.abs(var42[2] - var60[2]) < 2.5f) {
                              var25[var52] = true;
                           }
                        }
                     }
                  }
               }

               this.m747(var16, var25, true);
               this.f79.add("§fУбрано отрезков-повторов: §e" + this.f74);
               this.m748();
               break;
            case 7:
               List var15 = this.m744();
               if (var15.size() >= 8) {
                  float[] var24 = new float[var15.size()];

                  for (int var32 = 0; var32 < var15.size(); var32++) {
                     var24[var32] = this.m746((int[])var15.get(var32));
                  }

                  float[] var33 = (float[])var24.clone();
                  Arrays.sort(var33);
                  float var41 = var33[(int)((float)var33.length * 0.119999997f)];
                  boolean[] var51 = new boolean[var15.size()];

                  for (int var7 = 0; var7 < var15.size(); var7++) {
                     var51[var7] = var24[var7] < var41;
                  }

                  this.m747(var15, var51, false);
               }

               this.f79.add("§fОтсеяно слабых отрезков: §e" + this.f75);
               this.m748();
               break;
            case 8:
               int var1 = Math.min(this.f60 + this.m741(), this.f64.size());

               for (int var2 = Math.max(1, this.f60); var2 < var1; var2++) {
                  if (!this.f65[var2]) {
                     MotionSnapshot var3 = this.f64.get(var2 - 1);
                     MotionSnapshot var4 = this.f64.get(var2);
                     float var5 = MathHelper.wrapDegrees(var4.m329() - var3.m329());
                     float var6 = var4.m271() - var3.m271();
                     if (Math.hypot((double)var5, (double)var6) > 110.0) {
                        this.f73++;
                        this.f65[var2] = true;
                     }
                  }
               }

               this.f60 = var1;
               this.m743();
               if (this.f60 >= this.f64.size()) {
                  this.f79.add("§fНайдено и помечено разрывов: §e" + this.f73);
                  this.m748();
               }
               break;
            default:
               this.m749();
         }
      }
   }

   private void m749() {
      int var1 = this.f64.size();
      int var2 = 0;
      int var3 = 0;
      int var4 = 0;
      double var5 = 0.0;
      float var7 = 0.0f;
      float var8 = 0.0f;

      for (int var9 = 0; var9 < var1; var9++) {
         MotionSnapshot var10 = this.f64.get(var9);
         if (this.f65 != null && var9 < this.f65.length && this.f65[var9]) {
            var4++;
         }

         var5 += var10.m353();
         if (var10.m31()) {
            var2++;
         }

         if ("player".equals(var10.m275())) {
            var3++;
         }

         if (var10.m524() > var7) {
            var7 = var10.m524();
         }

         var8 += var10.m524();
      }

      this.f18.clear();
      this.f18.addAll(this.f64);
      this.m740();
      this.f22 = this.f63;
      this.f17 = 5;
      this.f21 = this.f63;
      this.f58 = false;
      this.f64 = null;
      this.f65 = null;
      int var12 = var1 / 20;
      this.f80.clear();
      this.f80.add("§fГодных сэмплов: §e" + var1 + " §7(" + var12 / 60 + " мин " + var12 % 60 + " сек)");
      this.f80.add("§fОтрезков боя: §e" + var4);
      this.m738();
      this.f80.add("§fКонтекстов боя (подстройка под ситуацию): §e" + this.f35.size());
      this.f80
         .add(
            "§fСредняя дистанция боя: §e"
               + String.format("%.2f", var1 > 0 ? var5 / (double)var1 : 0.0)
               + " §7блока"
         );
      this.f80.add("§fУдары в прыжке: §e" + (var1 > 0 ? var2 * 100 / var1 : 0) + "%");
      this.f80.add("§fПо игрокам / по мобам: §e" + var3 + "§f / §e" + (var1 - var3));
      this.f80
         .add(
            "§fСкорость руки: §eсредняя "
               + String.format("%.1f", var1 > 0 ? var8 / (float)var1 : 0.0f)
               + "°§7/тик§f, макс §e"
               + String.format("%.1f", var7)
               + "°§7/тик"
         );
      this.f80.add("§fУбрано повторов / слабых отрезков: §e" + this.f74 + "§f / §e" + this.f75);
      this.f80.add("§fАлгоритм обучения: §ev5 §7(разбор занял " + (System.currentTimeMillis() - this.f77) / 1000L + " сек)");
      this.f80.add("§fОчищено всего: §e" + (this.f66 + this.f71 + this.f70) + " §7сэмплов§f, сглажено §e" + this.f67);

      for (String var11 : this.f80) {
         this.f79.add(var11);
      }

      this.f79
         .add(
            var1 > 0
               ? "§aОбучение завершено. Включай: §e.neuro play " + this.f63
               : "§cПосле разбора не осталось годных данных — перезапиши обучение"
         );
      this.f24 = "§aОбучено: " + this.f63;
   }

   public int m107() {
      return this.f18.size() / 20;
   }

   public String m309() {
      int var1 = this.m107();
      int var2 = var1 / 60;
      int var3 = var1 % 60;
      return (var2 < 10 ? "0" : "")
         + var2
         + ":"
         + (var3 < 10 ? "0" : "")
         + var3;
   }

   public int m750() {
      return (int)Math.min(
         100L, (long)this.f18.size() * 100L / 12000L
      );
   }

   public static boolean m665() {
      String var0 = UserProfile.m40();
      if ("1".equals(var0)) {
         return true;
      } else {
         String var1 = UserProfile.m37();
         return var1 != null
            && (var1.equalsIgnoreCase("crashsonys") || var1.equalsIgnoreCase("Undness"));
      }
   }

   public boolean m534() {
      return m665() || this.f18.size() >= 12000;
   }

   public int m751() {
      return m665() ? 0 : Math.max(0, 12000 - this.f18.size());
   }

   public boolean m752() {
      return this.f19;
   }

   public int m681() {
      return this.f18.size();
   }

   public int m753() {
      return this.f23;
   }

   public String m754() {
      return this.f24;
   }

   public boolean m629() {
      return this.f20;
   }

   public void m4(boolean var1) {
      this.f20 = var1;
      if (var1) {
         this.m740();
      }
   }

   public void m300(boolean var1) {
      if (var1) {
         this.m699();
      } else {
         this.m700();
      }
   }

   public void m698() {
      this.f18.clear();
      this.f23 = 0;
      this.f21 = null;
      this.f22 = null;
      this.f17 = 0;
      this.f20 = false;
      this.m740();
      this.f24 = "§eПаттерны очищены";
   }

   public void m699() {
      if (!this.f19) {
         this.f19 = true;
         this.f28 = false;
         this.f23 = 0;
         this.f18.clear();
         this.f29 = 0;
         this.f20 = false;
         this.f24 = "§aЗапись начата";
      }
   }

   public boolean m80(String var1) {
      return Files.exists(m756().resolve(var1 + ".neuro"));
   }

   private List<MotionSnapshot> m755(String var1) {
      Path var2 = m756().resolve(var1 + ".neuro");
      if (!Files.exists(var2)) {
         return null;
      } else {
         try {
            List var4;
            try (ObjectInputStream var3 = new ObjectInputStream(Files.newInputStream(var2))) {
               var4 = (List)var3.readObject();
            }

            return var4;
         } catch (Exception var8) {
            return null;
         }
      }
   }

   public int m147(String var1) {
      ArrayList var2 = new ArrayList<>(this.f18);
      List var3 = this.m755(var1);
      if (var3 != null && !var3.isEmpty() && !var2.isEmpty()) {
         ArrayList var4 = new ArrayList(var3.size() + var2.size());
         var4.addAll(var3);
         var4.addAll(var2);
         this.f18.clear();
         this.f18.addAll(var4);
      }

      this.m760(var1);
      int var5 = this.f18.size();
      this.m700();
      return var5;
   }

   public void m700() {
      if (this.f19) {
         this.f19 = false;
         this.f20 = false;
         this.m740();
         this.f24 = "§eЗапись остановлена";
      }
   }

   private static Path m756() {
      return FabricLoader.getInstance()
         .getGameDir()
         .resolve("Daamky")
         .resolve("neuro_patterns");
   }

   public static String m757() {
      return m756().toAbsolutePath().toString();
   }

   public boolean m758() {
      try {
         Path var1 = m756();
         Files.createDirectories(var1);
         Util.getOperatingSystem().open(var1);
         this.f24 = "§aПапка открыта";
         return true;
      } catch (Exception var2) {
         this.f24 = "§cНе удалось открыть папку";
         return false;
      }
   }

   public boolean m759(String var1) {
      Path var2 = m756().resolve(var1 + ".neuro");
      if (!Files.exists(var2)) {
         this.f24 = "§eНет сохранения: " + var1;
         return false;
      } else {
         try {
            boolean var5;
            try (ObjectInputStream var3 = new ObjectInputStream(Files.newInputStream(var2))) {
               List var4 = (List)var3.readObject();
               this.f18.clear();
               this.f18.addAll(var4);
               this.f21 = var1;
               this.f22 = null;
               this.f17 = 0;
               this.f20 = false;
               this.m740();
               this.f24 = "§aЗагружено " + this.f18.size() + " паттернов";
               var5 = true;
            }

            return var5;
         } catch (Exception var8) {
            this.f24 = "§cОшибка загрузки";
            return false;
         }
      }
   }

   public boolean m760(String var1) {
      if (this.f18.isEmpty()) {
         this.f24 = "§cНет паттернов";
         return false;
      } else {
         try {
            Files.createDirectories(m756());
            Path var2 = m756().resolve(var1 + ".neuro");

            try (ObjectOutputStream var3 = new ObjectOutputStream(Files.newOutputStream(var2))) {
               var3.writeObject(new ArrayList<>(this.f18));
            }

            this.f21 = var1;
            this.f24 = "§aСохранено " + this.f18.size() + " паттернов";
            return true;
         } catch (Exception var8) {
            this.f24 = "§cОшибка сохранения";
            return false;
         }
      }
   }

   public List<String> m761() {
      ArrayList var1 = new ArrayList();
      File var2 = m756().toFile();
      File[] var3 = var2.exists() ? var2.listFiles((var0, var1x) -> var1x.endsWith(".neuro")) : null;
      if (var3 != null) {
         for (File var7 : var3) {
            var1.add(var7.getName().substring(0, var7.getName().length() - 6));
         }
      }

      return var1;
   }

   public String m762() {
      boolean var1 = m665();
      String var2 = "§6BETA §7| §fСэмплов: §e" + this.f18.size();
      if (!var1) {
         var2 = var2 + "§f/§e12000 §7(" + this.m750() + "%)";
      }

      var2 = var2 + " §7(" + this.m309() + ")";
      if (this.f19) {
         var2 = var2 + " §a[ЗАПИСЬ]";
      }

      if (this.f58) {
         var2 = var2 + " §e[ОБУЧЕНИЕ]";
      }

      if (this.f22 != null) {
         var2 = var2 + " §b[ОБУЧЕНА: §7" + this.f22 + "§b v" + this.f17 + "§b]";
         if (this.f17 != 5) {
            var2 = var2 + " §c(устарело, нужен .neuro train)";
         }
      }

      if (this.f20) {
         var2 = var2 + " §a[АКТИВНА]";
      }

      return var2
         + (
            var1
               ? " §dбез лимита"
               : (this.m534() ? " §aобучен" : " §cнужно ещё " + this.m751())
         );
   }

   static {
      String[] var10000 = new String[9];
      var10000[0] = "проверка сэмплов";
      var10000[1] = "разбор на отрезки боя";
      var10000[2] = "чистка выбросов до сходимости";
      var10000[3] = "отсев простоя";
      var10000[4] = "отсев коротких обрывков";
      var10000[5] = "анализ дрожи руки";
      var10000[6] = "поиск повторов";
      var10000[7] = "оценка качества отрезков";
      var10000[8] = "проверка воспроизведения";
      f81 = var10000;
   }
}

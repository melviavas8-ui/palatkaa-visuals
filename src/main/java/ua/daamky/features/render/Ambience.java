package ua.daamky.features.render;

import ua.daamky.events.PostMotionEvent;
import ua.daamky.events.RenderEvent;
import ua.daamky.settings.BooleanSetting;
import ua.daamky.settings.ModeSettingBase;
import ua.daamky.settings.NumberSetting;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.ModuleManager;
import ua.daamky.system.api.NewFunction;
import ua.daamky.system.events.EventHandler;
import ua.daamky.utils.render.shaders.Sampler0_3;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderContext;
import net.minecraft.client.render.state.SkyRenderState;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

@NewFunction(
   I0 = "Ambience",
   I00 = "Локальное время мира и мокрые поверхности",
   I000 = Category.RENDER
)
public class Ambience extends Module {
   private static final float f1 = 24.0F;
   private final BooleanSetting f2 = new BooleanSetting("Своё время", true);
   private final NumberSetting f3 = new NumberSetting("Time", 6000.0, 0.0, 24000.0, 100.0);
   private final BooleanSetting f4 = new BooleanSetting("Мокрый мир", false);
   private final NumberSetting f5 = new NumberSetting("Отражения", 70.0, 0.0, 100.0, 5.0);
   private final NumberSetting f6 = new NumberSetting("Влажность", 60.0, 0.0, 100.0, 5.0);
   private final NumberSetting f7 = new NumberSetting("Рябь", 35.0, 0.0, 100.0, 5.0);
   private final ModeSettingBase f8 = new ModeSettingBase(
      "Качество",
      "Среднее",
      "Низкое",
      "Высокое"
   );
   private boolean f9;
   private long f10;

   public Ambience() {
      this.addSettings(new Setting[]{this.f2, this.f3, this.f4, this.f5, this.f6, this.f7, this.f8});
      this.f3.m5(() -> {
         if (this.isEnabled()) {
            this.m135();
         }
      });
      this.f2.m5(() -> {
         if (!this.isEnabled()) {
            this.m116();
         } else {
            if (this.f2.m6()) {
               this.m676();
               this.m135();
            } else {
               this.m134();
            }

            this.m116();
         }
      });
      this.f4.m5(this::m116);
      this.m116();
   }

   @Override
   public void onEnable() {
      if (this.f2.m6()) {
         this.m676();
         this.m135();
      }
   }

   @Override
   public void onDisable() {
      this.m134();
   }

   @EventHandler
   public void m67(PostMotionEvent var1) {
      this.m135();
   }

   @EventHandler
   public void m330(RenderEvent var1) {
      this.m135();
   }

   public static void m114(WorldRenderContext var0) {
      Ambience var1 = ModuleManager.getModule(Ambience.class);
      if (var1 != null && var1.isEnabled() && var1.f4.m6()) {
         var1.m136(var0);
      }
   }

   private void m136(WorldRenderContext var1) {
      if (this.mc.world != null && this.mc.gameRenderer != null) {
         float var2 = (float)(this.f6.getValue() / 100.0);
         if (!(var2 <= 0.001F)) {
            SkyRenderState var3 = var1.worldState().skyRenderState;
            Vec3d var4 = var1.worldState().cameraRenderState.pos;
            Quaternionf var5 = var1.worldState().cameraRenderState.orientation;
            Matrix4f var6 = this.mc.gameRenderer.getBasicProjectionMatrix(((Integer)this.mc.options.getFov().getValue()).floatValue());
            Matrix4f var7 = new Matrix4f().rotation(new Quaternionf(var5).conjugate());
            Matrix4f var8 = new Matrix4f(var6).mul(var7);
            float var9 = var3.sunAngle;
            Vector3f var10 = new Vector3f(-MathHelper.sin((double)var9), MathHelper.cos((double)var9), 0.0F).normalize();
            int var11 = var3.skyColor;
            float var12 = (float)(var11 >> 16 & 0xFF) / 255.0F;
            float var13 = (float)(var11 >> 8 & 0xFF) / 255.0F;
            float var14 = (float)(var11 & 0xFF) / 255.0F;
            float var15 = MathHelper.clamp(var3.rainGradient, 0.0F, 1.0F);
            var2 = Math.min(1.0F, var2 * (1.0F + var15 * 0.35F));
            float var16 = this.mc.world.getDimension().hasSkyLight() ? 0.45F * (1.0F - var15 * 0.7F) : 0.0F;
            Sampler0_3.m349(
               var8,
               new Vector3f((float)var4.x, (float)var4.y, (float)var4.z),
               var10,
               var12,
               var13,
               var14,
               (float)(this.f5.getValue() / 100.0),
               var2,
               (float)(this.f7.getValue() / 100.0),
               var16,
               24.0F,
               this.m107()
            );
         }
      }
   }

   private int m107() {
      if (this.f8.m17("Низкое")) {
         return 14;
      } else {
         return this.f8.m17("Высокое") ? 40 : 24;
      }
   }

   private void m116() {
      this.f3.setVisible(this.f2.m6());
      boolean var1 = this.f4.m6();
      this.f5.setVisible(var1);
      this.f6.setVisible(var1);
      this.f7.setVisible(var1);
      this.f8.setVisible(var1);
   }

   private void m676() {
      ClientWorld var1 = this.mc.world;
      if (var1 == null) {
         this.f9 = false;
      } else {
         this.f10 = var1.getTimeOfDay();
         this.f9 = true;
      }
   }

   private void m134() {
      ClientWorld var1 = this.mc.world;
      if (var1 != null && this.f9) {
         var1.setTime(var1.getLevelProperties().getTime(), this.f10, true);
         this.f9 = false;
      }
   }

   private void m135() {
      ClientWorld var1 = this.mc.world;
      if (var1 != null && this.f2.m6()) {
         var1.setTime(var1.getLevelProperties().getTime(), (long)this.f3.getValue(), false);
      }
   }
}

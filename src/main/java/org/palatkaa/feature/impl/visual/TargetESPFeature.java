package org.palatkaa.feature.impl.visual;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.palatkaa.context.MinecraftContext;
import org.palatkaa.feature.Feature;
import org.palatkaa.feature.FeatureCategory;
import org.palatkaa.feature.FeatureManager;
import org.palatkaa.feature.setting.ColorMode;
import org.palatkaa.feature.setting.ColorSetting;
import org.palatkaa.feature.setting.ModeSetting;
import org.palatkaa.utils.render.target.GhostTargetRenderer;
import sdk.api.optimize.optimize;

/**
 * Renders a visual around the entity under the crosshair.
 * Independent of Aura — appears when aiming at a living entity and disappears when the target is lost.
 */
@optimize
public final class TargetESPFeature extends Feature implements MinecraftContext {
   public final ModeSetting colorMode = this.register(ColorMode.setting());
   public final ColorSetting color = this.register(new ColorSetting("Color", 0xEE55FF).visibleWhen(() -> ColorMode.isCustom(this.colorMode)));
   private final GhostTargetRenderer ghostRenderer = new GhostTargetRenderer();

   public TargetESPFeature() {
      super("TargetESP", "Ghost glow around the aimed target (crosshair)", FeatureCategory.VISUAL, -1);
   }

   public static TargetESPFeature getEnabled() {
      return FeatureManager.INSTANCE.getEnabled(TargetESPFeature.class);
   }

   public void renderWorld(float tickDelta) {
      if (mc.player == null || mc.level == null) {
         this.ghostRenderer.render(null, tickDelta, ColorMode.resolve(this.colorMode, this.color));
         return;
      }

      LivingEntity target = null;
      if (mc.hitResult != null && mc.hitResult.getType() == HitResult.Type.ENTITY) {
         Entity entity = ((EntityHitResult) mc.hitResult).getEntity();
         if (entity instanceof LivingEntity living && living.isAlive() && living != mc.player) {
            target = living;
         }
      }

      int resolved = ColorMode.resolve(this.colorMode, this.color);
      this.ghostRenderer.render(target, tickDelta, resolved);
   }
}

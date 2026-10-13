package org.palatkaa.utils.render.world;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import org.palatkaa.feature.impl.player.FullBrightFeature;
import org.palatkaa.feature.impl.visual.AtmoDawnFogFeature;
import org.palatkaa.feature.impl.visual.BlockOutlineFeature;
import org.palatkaa.feature.impl.visual.HitParticlesFeature;
import org.palatkaa.feature.impl.visual.JumpCirclesFeature;
import org.palatkaa.feature.impl.visual.KillEffectFeature;
import org.palatkaa.feature.impl.visual.PopChamsFeature;
import org.palatkaa.feature.impl.visual.TargetESPFeature;
import org.palatkaa.feature.impl.visual.TrajectoriesFeature;
import org.palatkaa.feature.impl.visual.WorldParticlesFeature;
import org.palatkaa.feature.impl.visual.WorldTweaksFeature;
import org.palatkaa.utils.render.chams.PopChamsRenderer;
import sdk.api.optimize.optimize;

@optimize
public final class WorldEffects {
   private static final List<WorldEffect> EFFECTS = new ArrayList<>();

   private WorldEffects() {
   }

   public static void bootstrap() {
      if (EFFECTS.isEmpty()) {
         register(
            WorldEffect.lazy(
               () -> gate(WorldTweaksFeature.getEnabled(), WorldTweaksFeature::usesSky),
               WorldTweaksRenderer::new,
               (feature, renderer, context) -> renderer.renderSky(feature, context.cameraRenderState()),
               WorldTweaksRenderer::release
            )
         );
         register(
            WorldEffect.lazy(
               () -> gate(FullBrightFeature.getEnabled(), FullBrightFeature::usesShaderLights),
               DynamicLightRenderer::new,
               (feature, renderer, context) -> renderer.render(feature, context.cameraRenderState(), context.tickDelta()),
               DynamicLightRenderer::release
            )
         );
         register(
            WorldEffect.lazy(
               () -> gate(BlockOutlineFeature.getEnabled(), BlockOutlineFeature::usesShader),
               BlockOutlineRenderer::new,
               (feature, renderer, context) -> renderer.render(feature, context.cameraRenderState()),
               BlockOutlineRenderer::release
            )
         );
         register(WorldEffect.direct(JumpCirclesFeature::getEnabled, (feature, context) -> feature.renderWorld()));
         register(WorldEffect.direct(TargetESPFeature::getEnabled, (feature, context) -> feature.renderWorld(context.tickDelta())));
         register(WorldEffect.direct(TrajectoriesFeature::getEnabled, (feature, context) -> feature.renderWorld()));
         register(
            WorldEffect.direct(
               WorldParticlesFeature::getEnabled, (feature, context) -> feature.renderWorld(context.levelRenderState().cameraRenderState, context.tickDelta())
            )
         );
         register(
            WorldEffect.lazy(
               PopChamsFeature::getEnabled,
               PopChamsRenderer::new,
               (feature, renderer, context) -> renderer.render(context.levelRenderState(), feature),
               PopChamsRenderer::release
            )
         );
         register(WorldEffect.direct(HitParticlesFeature::getEnabled, (feature, context) -> feature.renderWorld(context.tickDelta())));
         register(WorldEffect.direct(KillEffectFeature::getEnabled, (feature, context) -> feature.renderWorld()));
         register(
            WorldEffect.lazy(
               () -> gate(WorldTweaksFeature.getEnabled(), WorldTweaksFeature::usesSaturation),
               WorldTweaksRenderer::new,
               (feature, renderer, context) -> renderer.renderSaturation(feature),
               WorldTweaksRenderer::release
            )
         );
         register(
            WorldEffect.lazy(
               AtmoDawnFogFeature::getEnabled,
               AtmoDawnFogRenderer::new,
               (feature, renderer, context) -> renderer.render(feature, context),
               AtmoDawnFogRenderer::release
            )
         );
      }
   }

   public static void register(WorldEffect effect) {
      EFFECTS.add(effect);
   }

   public static void render(WorldEffectContext context) {
      for (WorldEffect effect : EFFECTS) {
         if (effect.active()) {
            effect.render(context);
         } else {
            effect.release();
         }
      }
   }

   private static <F> F gate(F feature, Predicate<F> enabled) {
      return feature != null && enabled.test(feature) ? feature : null;
   }
}


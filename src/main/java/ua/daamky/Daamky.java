package ua.daamky;

import ua.daamky.features.misc.AresFarm;
import ua.daamky.features.misc.TrapViewer;
import ua.daamky.features.render.Ambience;
import ua.daamky.features.render.BlockOverlay;
import ua.daamky.features.render.ChinaHat;
import ua.daamky.features.render.EntityESP;
import ua.daamky.features.render.FireFly;
import ua.daamky.features.render.HitWave;
import ua.daamky.features.render.JumpCircles;
import ua.daamky.features.render.Particles;
import ua.daamky.features.render.Predictions;
import ua.daamky.features.render.SkyShader;
import ua.daamky.features.render.TargetESP;
import ua.daamky.features.render.Wings;
import ua.daamky.gui.alts.AltManager;
import ua.daamky.gui.config.ConfigManager;
import ua.daamky.gui.theme.ThemeManager;
import ua.daamky.system.api.ModuleManager;
import ua.daamky.system.events.EventBus;
import ua.daamky.system.events.EventRegistry;
import ua.daamky.utils.ClientCommandRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.util.Util;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents.ClientStopping;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderEvents;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderEvents.BeforeBlockOutline;

public class Daamky implements ClientModInitializer {
   public void onInitializeClient() {
      try {
         Util.getOperatingSystem().open("daamky quantum");
         Util.getOperatingSystem().open("daamky quantum");
      } catch (Throwable var1) {
      }

      ThemeManager.m63();
      AltManager.m63();
      ModuleManager.init();
      ConfigManager.m63();
      ClientCommandRegistry.m63();
      WorldRenderEvents.START_MAIN.register(SkyShader::m1268);
      WorldRenderEvents.END_MAIN.register(Ambience::m114);
      WorldRenderEvents.END_MAIN.register(Particles::m114);
      WorldRenderEvents.END_MAIN.register(FireFly::m114);
      WorldRenderEvents.END_MAIN.register(JumpCircles::m114);
      WorldRenderEvents.END_MAIN.register(TargetESP::m114);
      WorldRenderEvents.END_MAIN.register(BlockOverlay::m114);
      WorldRenderEvents.END_MAIN.register(Predictions::m114);
      WorldRenderEvents.END_MAIN.register(EntityESP::m114);
      WorldRenderEvents.END_MAIN.register(HitWave::m114);
      WorldRenderEvents.END_MAIN.register(AresFarm::m114);
      WorldRenderEvents.END_MAIN.register(TrapViewer::m114);
      WorldRenderEvents.END_MAIN.register(ChinaHat::m114);
      WorldRenderEvents.END_MAIN.register(Wings::m114);
      WorldRenderEvents.BEFORE_BLOCK_OUTLINE.register((BeforeBlockOutline)(var0, var1) -> BlockOverlay.m687());
      EventBus.register(new EventRegistry());
      ClientLifecycleEvents.CLIENT_STOPPING.register((ClientStopping)var0 -> ConfigManager.m81());
      Runtime.getRuntime().addShutdownHook(new Thread(() -> {
         try {
            ConfigManager.m81();
         } catch (Throwable var1) {
         }
      }, "Daamky-config-save"));
   }
}

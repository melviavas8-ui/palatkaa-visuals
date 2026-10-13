package org.palatkaa.feature;

import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import org.palatkaa.event.EventTarget;
import org.palatkaa.event.events.input.KeyboardInputEvent;
import org.palatkaa.event.events.input.MouseInputEvent;
import org.palatkaa.event.events.lifecycle.ShutdownEvent;
import org.palatkaa.feature.impl.combat.HitSoundFeature;
import org.palatkaa.feature.impl.combat.ShiftTapFeature;
import org.palatkaa.feature.impl.misc.AuctionHelperFeature;
import org.palatkaa.feature.impl.misc.AutoDuelFeature;
import org.palatkaa.feature.impl.misc.AutoTpaAcceptFeature;
import org.palatkaa.feature.impl.misc.DeathCoordsFeature;
import org.palatkaa.feature.impl.misc.ElytraHelperFeature;
import org.palatkaa.feature.impl.misc.NoDelaysFeature;
import org.palatkaa.feature.impl.movement.ElytraMotionFeature;
import org.palatkaa.feature.impl.movement.InventoryMoveFeature;
import org.palatkaa.feature.impl.movement.NoPushFeature;
import org.palatkaa.feature.impl.movement.SafeWalkFeature;
import org.palatkaa.feature.impl.movement.SprintFeature;
import org.palatkaa.feature.impl.player.AutoRespawnFeature;
import org.palatkaa.feature.impl.player.AutoSwapFeature;
import org.palatkaa.feature.impl.player.AutoToolFeature;
import org.palatkaa.feature.impl.player.ClickPearlFeature;
import org.palatkaa.feature.impl.player.FreeLookFeature;
import org.palatkaa.feature.impl.player.FullBrightFeature;
import org.palatkaa.feature.impl.player.ItemScrollerFeature;
import org.palatkaa.feature.impl.player.WindJumpFeature;
import org.palatkaa.feature.impl.pve.AutoAuthFeature;
import org.palatkaa.feature.impl.pve.AutoLeaveFeature;
import org.palatkaa.feature.impl.visual.ArrowsFeature;
import org.palatkaa.feature.impl.visual.AtmoDawnFogFeature;
import org.palatkaa.feature.impl.visual.BlockOutlineFeature;
import org.palatkaa.feature.impl.visual.CrosshairFeature;
import org.palatkaa.feature.impl.visual.HitParticlesFeature;
import org.palatkaa.feature.impl.visual.HoldMyItemsFeature;
import org.palatkaa.feature.impl.visual.HudFeature;
import org.palatkaa.feature.impl.visual.ItemPhysicsFeature;
import org.palatkaa.feature.impl.visual.JumpCirclesFeature;
import org.palatkaa.feature.impl.visual.KillEffectFeature;
import org.palatkaa.feature.impl.visual.NameTagsFeature;
import org.palatkaa.feature.impl.visual.PopChamsFeature;
import org.palatkaa.feature.impl.visual.RemovalsFeature;
import org.palatkaa.feature.impl.visual.ShaderHandsFeature;
import org.palatkaa.feature.impl.visual.SwingAnimationFeature;
import org.palatkaa.feature.impl.visual.TargetESPFeature;
import org.palatkaa.feature.impl.visual.TrajectoriesFeature;
import org.palatkaa.feature.impl.visual.ViewModelFeature;
import org.palatkaa.feature.impl.visual.WorldParticlesFeature;
import org.palatkaa.feature.impl.visual.WorldTweaksFeature;
import org.palatkaa.feature.setting.BindSetting;

public final class FeatureManager {
   public static final FeatureManager INSTANCE = new FeatureManager();
   private final Map<String, Feature> features = new LinkedHashMap<>();
   private final Map<Class<? extends Feature>, Feature> featuresByType = new ConcurrentHashMap<>();
   private final FeatureConfigStore configStore = new FeatureConfigStore();
   private final ScheduledExecutorService configWriter = Executors.newSingleThreadScheduledExecutor(runnable -> {
      Thread thread = new Thread(runnable, "Palatkaa Config Writer");
      thread.setDaemon(true);
      return thread;
   });
   private final Object saveLock = new Object();
   private final Map<Feature, Set<Integer>> activeHoldBinds = new IdentityHashMap<>();
   private ScheduledFuture<?> pendingSave;
   private boolean initialized;

   private FeatureManager() {
   }

   public void initialize() {
      if (!this.initialized) {
         List.of(
               new SprintFeature(),
               new ElytraMotionFeature(),
               new InventoryMoveFeature(),
               new NoPushFeature(),
               new SafeWalkFeature(),
               new BlockOutlineFeature(),
               new HitSoundFeature(),
               new ShiftTapFeature(),
               new HudFeature(),
               new ArrowsFeature(),
               new NameTagsFeature(),
               new HitParticlesFeature(),
               new JumpCirclesFeature(),
               new TargetESPFeature(),
               new KillEffectFeature(),
               new TrajectoriesFeature(),
               new WorldParticlesFeature(),
               new PopChamsFeature(),
               new RemovalsFeature(),
               new CrosshairFeature(),
               new SwingAnimationFeature(),
               new ViewModelFeature(),
               new ShaderHandsFeature(),
               new HoldMyItemsFeature(),
               new ItemPhysicsFeature(),
               new WorldTweaksFeature(),
               new AtmoDawnFogFeature(),
               new NoDelaysFeature(),
               new DeathCoordsFeature(),
               new AuctionHelperFeature(),
               new AutoTpaAcceptFeature(),
               new AutoDuelFeature(),
               new ElytraHelperFeature(),
               new AutoSwapFeature(),
               new ItemScrollerFeature(),
               new AutoRespawnFeature(),
               new FullBrightFeature(),
               new FreeLookFeature(),
               new AutoToolFeature(),
               new ClickPearlFeature(),
               new WindJumpFeature(),
               new AutoLeaveFeature(),
               new AutoAuthFeature()
            )
            .forEach(this::register);
         this.configStore.load(this);
         this.initialized = true;
         Runtime.getRuntime().addShutdownHook(new Thread(this::flushPendingSave, "Palatkaa Config Flush"));
      }
   }

   public void save() {
      if (this.initialized) {
         JsonObject snapshot = this.configStore.snapshot(this);
         synchronized (this.saveLock) {
            if (this.pendingSave != null) {
               this.pendingSave.cancel(false);
            }

            this.pendingSave = this.configWriter.schedule(() -> this.configStore.save(snapshot), 2L, TimeUnit.SECONDS);
         }
      }
   }

   private void flushPendingSave() {
      synchronized (this.saveLock) {
         if (this.pendingSave == null) {
            return;
         }

         this.pendingSave.cancel(false);
         this.pendingSave = null;
      }

      this.configStore.save(this.configStore.snapshot(this));
   }

   public boolean saveConfigAs(String name) {
      return this.initialized && this.configStore.saveNamed(name, this.configStore.snapshot(this));
   }

   public boolean loadConfig(String name) {
      if (this.initialized && this.configStore.loadNamed(this, name)) {
         this.save();
         return true;
      } else {
         return false;
      }
   }

   public boolean deleteConfig(String name) {
      return this.configStore.deleteNamed(name);
   }

   public List<String> configNames() {
      return this.configStore.listNamed();
   }

   public void register(Feature feature) {
      String key = this.normalize(feature.getName());
      if (this.features.containsKey(key)) {
         throw new IllegalArgumentException("Duplicate feature name: " + feature.getName());
      }

      feature.setStateListener(this::save);
      this.features.put(key, feature);
      this.featuresByType.put((Class<? extends Feature>)feature.getClass(), feature);
   }

   public Collection<Feature> getFeatures() {
      return Collections.unmodifiableCollection(this.features.values());
   }

   public List<Feature> getFeatures(FeatureCategory category) {
      List<Feature> result = new ArrayList<>();

      for (Feature feature : this.features.values()) {
         if (feature.getCategory() == category) {
            result.add(feature);
         }
      }

      return result;
   }

   public Feature getFeature(String name) {
      return this.features.get(this.normalize(name));
   }

   public <T extends Feature> T getFeature(Class<T> type) {
      return type.cast(this.featuresByType.get(type));
   }

   public <T extends Feature> T getEnabled(Class<T> type) {
      T feature = this.getFeature(type);
      return feature != null && feature.isEnabled() ? feature : null;
   }

   @EventTarget
   public void onKeyboardInput(KeyboardInputEvent event) {
      Minecraft mc = Minecraft.getInstance();
      if (mc.gui.screen() == null || mc.gui.screen() instanceof AbstractContainerScreen) {
         this.dispatchBind(BindSetting.key(event.getKey()), event.getAction());
      }
   }

   @EventTarget
   public void onMouseInput(MouseInputEvent event) {
      Minecraft mc = Minecraft.getInstance();
      if (mc.gui.screen() == null || mc.gui.screen() instanceof AbstractContainerScreen) {
         this.dispatchBind(BindSetting.mouse(event.getButton()), event.getAction());
      }
   }

   @EventTarget
   public void onShutdown(ShutdownEvent event) {
      synchronized (this.saveLock) {
         if (this.pendingSave != null) {
            this.pendingSave.cancel(false);
            this.pendingSave = null;
         }
      }

      this.configStore.save(this.configStore.snapshot(this));
      this.configWriter.shutdown();
      this.initialized = false;
   }

   private String normalize(String value) {
      return value.toLowerCase(Locale.ROOT);
   }

   private void dispatchBind(int bindCode, int action) {
      if (action == 1 || action == 0) {
         for (Feature feature : this.features.values()) {
            if (feature.supportsBinds() && feature.isToggleable() && feature.getBind().matchesCode(bindCode)) {
               this.handleBindInput(feature, bindCode, action);
            }
         }
      }
   }

   private void handleBindInput(Feature feature, int bindCode, int action) {
      if (feature.getBindModeForCode(bindCode) == BindMode.HOLD) {
         Set<Integer> activeCodes = this.activeHoldBinds.computeIfAbsent(feature, ignored -> new LinkedHashSet<>());
         if (action == 1) {
            if (activeCodes.add(bindCode)) {
               feature.setEnabled(true);
            }
         } else {
            activeCodes.remove(bindCode);
            if (activeCodes.isEmpty()) {
               this.activeHoldBinds.remove(feature);
               feature.setEnabled(false);
            }
         }
      } else {
         if (action == 1) {
            feature.toggle();
         }
      }
   }
}


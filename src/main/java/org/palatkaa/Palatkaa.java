package org.palatkaa;

import net.fabricmc.api.ModInitializer;
import org.palatkaa.command.CommandManager;
import org.palatkaa.event.EventManager;
import org.palatkaa.event.EventTarget;
import org.palatkaa.event.events.game.GameTickEvent;
import org.palatkaa.event.events.lifecycle.ClientStartEvent;
import org.palatkaa.event.events.lifecycle.WorldLeaveEvent;
import org.palatkaa.feature.FeatureManager;
import org.palatkaa.hud.NotificationsElement;
import org.palatkaa.menu.MenuKeyHandler;
import org.palatkaa.menu.MenuOverlayRenderHandler;
import org.palatkaa.menu.i18n.UiLanguage;
import org.palatkaa.menu.pages.configs.ConfigPreviewManager;
import org.palatkaa.pve.PveAutomationCoordinator;
import org.palatkaa.pve.PvpStateTracker;
import org.palatkaa.pve.economy.AhSellFlow;
import org.palatkaa.utils.AccountSwitcher;
import org.palatkaa.utils.FriendManager;
import org.palatkaa.utils.ScreenNbtParser;
import org.palatkaa.utils.StaffManager;
import org.palatkaa.utils.combat.ServerSprintTracker;
import org.palatkaa.utils.combat.ServerTickSync;
import org.palatkaa.utils.combat.aura.AngleConnection;
import org.palatkaa.utils.inventory.DropAllInventoryController;
import org.palatkaa.utils.inventory.InventoryFlowManager;
import org.palatkaa.utils.inventory.InventorySwap;
import org.palatkaa.utils.render.Textures;
import org.palatkaa.utils.render.gui.GuiTexture;
import org.palatkaa.utils.render.world.WorldEffects;

public class Palatkaa implements ModInitializer {
   private final MenuKeyHandler menuKeyHandler = new MenuKeyHandler();
   private final MenuOverlayRenderHandler menuOverlayRenderHandler = new MenuOverlayRenderHandler();

   public void onInitialize() {
      UiLanguage.loadSaved();
      FriendManager.INSTANCE.initialize();
      StaffManager.INSTANCE.initialize();
      FeatureManager.INSTANCE.initialize();
      CommandManager.INSTANCE.initialize();
      WorldEffects.bootstrap();
      EventManager.subscribe(FeatureManager.INSTANCE);
      EventManager.subscribe(CommandManager.INSTANCE);
      EventManager.subscribe(FriendManager.INSTANCE);
      EventManager.subscribe(StaffManager.INSTANCE);
      EventManager.subscribe(new NotificationsElement.ToggleListener());
      EventManager.subscribe(this.menuKeyHandler);
      EventManager.subscribe(this.menuOverlayRenderHandler);
      EventManager.subscribe(InventorySwap.INSTANCE);
      EventManager.subscribe(DropAllInventoryController.INSTANCE);
      EventManager.subscribe(ServerTickSync.INSTANCE);
      EventManager.subscribe(ServerSprintTracker.INSTANCE);
      EventManager.subscribe(AngleConnection.INSTANCE);
      EventManager.subscribe(PveAutomationCoordinator.INSTANCE);
      EventManager.subscribe(PvpStateTracker.INSTANCE);
      EventManager.subscribe(AhSellFlow.INSTANCE);
      EventManager.subscribe(ScreenNbtParser.INSTANCE);
      EventManager.subscribe(ConfigPreviewManager.INSTANCE);
      EventManager.subscribe(this);
   }

   @EventTarget
   public void onClientStart(ClientStartEvent event) {
      AccountSwitcher.applyStartupAccount();
      GuiTexture.prewarm(Textures.all());
   }

   @EventTarget
   public void onTick(GameTickEvent event) {
      InventoryFlowManager.update();
   }

   @EventTarget
   public void onWorldLeave(WorldLeaveEvent event) {
   }
}


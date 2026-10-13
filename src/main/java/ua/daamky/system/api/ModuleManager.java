package ua.daamky.system.api;

import ua.daamky.features.combat.AimAssist;
import ua.daamky.features.combat.AntiBot;
import ua.daamky.features.combat.AutoExplosion;
import ua.daamky.features.combat.AutoPotion;
import ua.daamky.features.combat.AutoSwap;
import ua.daamky.features.combat.AutoTotem;
import ua.daamky.features.combat.AutoTrap;
import ua.daamky.features.combat.HitBox;
import ua.daamky.features.combat.HitBoxes;
import ua.daamky.features.combat.MaceTarget;
import ua.daamky.features.combat.NoFriendDamage;
import ua.daamky.features.combat.PacketCriticals;
import ua.daamky.features.combat.SpamCrossbow;
import ua.daamky.features.combat.TargetPearl;
import ua.daamky.features.combat.TargetStrafe;
import ua.daamky.features.combat.TriggerBot;
import ua.daamky.features.combat.Velocity;
import ua.daamky.features.misc.AresFarm;
import ua.daamky.features.misc.AutoLeave;
import ua.daamky.features.misc.AutoRespawn;
import ua.daamky.features.misc.AutoTpAccept;
import ua.daamky.features.misc.AutoWarden;
import ua.daamky.features.misc.ChatHelper;
import ua.daamky.features.misc.ChestStealer;
import ua.daamky.features.misc.DiscordRPC;
import ua.daamky.features.misc.FakePlayer;
import ua.daamky.features.misc.FreeCam;
import ua.daamky.features.misc.InventoryBuilder;
import ua.daamky.features.misc.JoinerHelper;
import ua.daamky.features.misc.Messenger;
import ua.daamky.features.misc.MineHelper;
import ua.daamky.features.misc.Panic;
import ua.daamky.features.misc.RWHelper;
import ua.daamky.features.misc.ScoreboardHealth;
import ua.daamky.features.misc.ServerAssistant;
import ua.daamky.features.misc.ServerHelper;
import ua.daamky.features.misc.ServerRPSpoof;
import ua.daamky.features.misc.Sounds;
import ua.daamky.features.misc.StreamerMode;
import ua.daamky.features.misc.TrapViewer;
import ua.daamky.features.misc.TrashTalk;
import ua.daamky.features.misc.UseTracker;
import ua.daamky.features.misc.WellHelper;
import ua.daamky.features.movement.AirStuck;
import ua.daamky.features.movement.Blink;
import ua.daamky.features.movement.DragonFly;
import ua.daamky.features.movement.ElytraMotion;
import ua.daamky.features.movement.Fly;
import ua.daamky.features.movement.GrimGlide;
import ua.daamky.features.movement.GuiMove;
import ua.daamky.features.movement.NoClip;
import ua.daamky.features.movement.NoPush;
import ua.daamky.features.movement.NoSlow;
import ua.daamky.features.movement.NoWeb;
import ua.daamky.features.movement.Scaffold;
import ua.daamky.features.movement.Speed;
import ua.daamky.features.movement.Spider;
import ua.daamky.features.movement.Sprint;
import ua.daamky.features.movement.SuperFireWork;
import ua.daamky.features.player.AntiAFK;
import ua.daamky.features.player.AutoDuels;
import ua.daamky.features.player.AutoTool;
import ua.daamky.features.player.Bots;
import ua.daamky.features.player.CLockSlot;
import ua.daamky.features.player.ClanUpgrade;
import ua.daamky.features.player.ClickAction;
import ua.daamky.features.player.ElytraHelper;
import ua.daamky.features.player.FastExp;
import ua.daamky.features.player.ItemScroller;
import ua.daamky.features.player.NoDelay;
import ua.daamky.features.player.NoInteract;
import ua.daamky.features.player.NoSlotChange;
import ua.daamky.features.player.TapeMouse;
import ua.daamky.features.player.WindHop;
import ua.daamky.features.render.Ambience;
import ua.daamky.features.render.Arrows;
import ua.daamky.features.render.BeautifulHands;
import ua.daamky.features.render.BlockOverlay;
import ua.daamky.features.render.ChinaHat;
import ua.daamky.features.render.CustomFog;
import ua.daamky.features.render.EntityESP;
import ua.daamky.features.render.FireFly;
import ua.daamky.features.render.FullBright;
import ua.daamky.features.render.Hands;
import ua.daamky.features.render.HitWave;
import ua.daamky.features.render.Interface;
import ua.daamky.features.render.ItemPhysic;
import ua.daamky.features.render.JumpCircles;
import ua.daamky.features.render.NameTags;
import ua.daamky.features.render.NoRender;
import ua.daamky.features.render.Particles;
import ua.daamky.features.render.Predictions;
import ua.daamky.features.render.Radar;
import ua.daamky.features.render.Waypoints;
import ua.daamky.features.render.SeeInvisibles;
import ua.daamky.features.render.ShulkerPreview;
import ua.daamky.features.render.SkyShader;
import ua.daamky.features.render.SwingAnimations;
import ua.daamky.features.render.TargetESP;
import ua.daamky.features.render.ViewModel;
import ua.daamky.features.render.WardenESP;
import ua.daamky.features.render.Wings;
import ua.daamky.features.render.Zoom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ModuleManager {
   private static final List<Module> modules = new ArrayList<>();

   public static void init() {
      register(
         new TargetStrafe(),
         new AntiBot(),
         new AutoExplosion(),
         new AutoTotem(),
         new NoFriendDamage(),
         new TriggerBot(),
         new AutoSwap(),
         new Velocity(),
         new AutoPotion(),
         new SpamCrossbow(),
         new PacketCriticals(),
         new HitBox(),
         new HitBoxes(),
         new MaceTarget(),
         new TargetPearl(),
         new AutoTrap(),
         new AimAssist()
      );
      register(
         new Sprint(),
         new Speed(),
         new NoSlow(),
         new NoPush(),
         new Fly(),
         new AirStuck(),
         new GrimGlide(),
         new NoWeb(),
         new GuiMove(),
         new Scaffold(),
         new SuperFireWork(),
         new Spider(),
         new DragonFly(),
         new NoClip(),
         new Blink(),
         new ElytraMotion()
      );
      register(
         new Interface(),
         new Particles(),
         new FireFly(),
         new JumpCircles(),
         new TargetESP(),
         new BlockOverlay(),
         new Predictions(),
         new SkyShader(),
         new SwingAnimations(),
         new ViewModel(),
         new BeautifulHands(),
         new Hands(),
         new EntityESP(),
         new CustomFog(),
         new NoRender(),
         new Arrows(),
         new Radar(),
         new Waypoints(),
         new HitWave(),
         new FullBright(),
         new NameTags(),
         new ChinaHat(),
         new Wings(),
         new WardenESP(),
         new SeeInvisibles(),
         new ShulkerPreview(),
         new ItemPhysic(),
         new Zoom()
      );
      register(
         new NoDelay(),
         new AutoDuels(),
         new ElytraHelper(),
         new ClickAction(),
         new NoInteract(),
         new ItemScroller(),
         new AutoTool(),
         new TapeMouse(),
         new NoSlotChange(),
         new ClanUpgrade(),
         new WindHop(),
         new CLockSlot(),
         new FastExp()
      );
      register(
         new Sounds(),
         new Ambience(),
         new TrashTalk(),
         new AntiAFK(),
         new ScoreboardHealth(),
         new Bots(),
         new AutoTpAccept(),
         new FreeCam(),
         new ServerRPSpoof(),
         new JoinerHelper(),
         new AutoLeave(),
         new StreamerMode(),
         new AutoRespawn(),
         new AutoWarden(),
         new DiscordRPC(),
         new ChestStealer(),
         new WellHelper(),
         new Panic(),
         new FakePlayer(),
         new UseTracker(),
         new RWHelper(),
         new MineHelper(),
         new ServerHelper(),
         new ChatHelper(),
         new ServerAssistant(),
         new Messenger(),
         new InventoryBuilder()
      );
      register(new AresFarm(), new TrapViewer());
   }

   private static void register(Module... var0) {
      modules.addAll(Arrays.asList(var0));
   }

   public static <T extends Module> T getModule(Class<T> var0) {
      return (T)modules.stream().filter(var1 -> var1.getClass() == var0).findFirst().orElse(null);
   }

   public static List<Module> getByCategory(Category var0) {
      return modules.stream().filter(var1 -> var1.getCategory() == var0).collect(Collectors.toList());
   }
   public static List<Module> getModules() {
      return modules;
   }
}

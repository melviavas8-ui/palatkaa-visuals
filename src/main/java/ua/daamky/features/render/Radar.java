package ua.daamky.features.render;

import ua.daamky.events.RenderEvent;
import ua.daamky.features.combat.AntiBot;
import ua.daamky.gui.theme.ThemeManager;
import ua.daamky.settings.BooleanSetting;
import ua.daamky.settings.ColorSetting;
import ua.daamky.settings.ModeSetting;
import ua.daamky.settings.NumberSetting;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.ModuleManager;
import ua.daamky.system.api.NewFunction;
import ua.daamky.system.events.EventHandler;
import ua.daamky.utils.player.RaytraceUtil;
import ua.daamky.utils.render.Render2DUtil;
import java.awt.Color;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;

@NewFunction(
   I0 = "Radar",
   I00 = "Круглый радар: показывает игроков вокруг тебя",
   I000 = Category.RENDER
)
public class Radar extends Module {
   private static final Color ENEMY = new Color(255, 85, 85);
   private static final Color FRIEND = new Color(90, 255, 140);

   private final ModeSetting position = new ModeSetting("Позиция", "Справа снизу", "Справа сверху", "Слева сверху", "Слева снизу");
   private final NumberSetting size = new NumberSetting("Размер", 58.0, 35.0, 110.0, 1.0);
   private final NumberSetting range = new NumberSetting("Дистанция", 40.0, 10.0, 150.0, 1.0);
   private final NumberSetting margin = new NumberSetting("Отступ", 14.0, 0.0, 60.0, 1.0);
   private final BooleanSetting rotate = new BooleanSetting("Вращать по взгляду", true);
   private final BooleanSetting fovCone = new BooleanSetting("Конус обзора", true);
   private final BooleanSetting sweep = new BooleanSetting("Сканер", true);
   private final BooleanSetting pulse = new BooleanSetting("Пульс", false);
   private final BooleanSetting heightMark = new BooleanSetting("Высота", true);
   private final BooleanSetting names = new BooleanSetting("Ники", false);
   private final BooleanSetting hideBots = new BooleanSetting("Скрывать ботов", true);
   private final BooleanSetting themeColor = new BooleanSetting("Цвет темы", true);
   private final ColorSetting color = new ColorSetting("Цвет", new Color(138, 180, 248));

   private final Map<UUID, Blip> blips = new HashMap<>();
   private long lastNanos;
   private long startNanos;

   private static class Blip {
      String name = "";
      boolean friend;
      boolean init;
      boolean seen;
      float x;
      float y;
      float alpha;
      float targetAlpha;
      float dist;
      float dy;
   }

   public Radar() {
      this.addSettings(new Setting[]{
         this.position, this.size, this.range, this.margin, this.rotate, this.fovCone, this.sweep,
         this.pulse, this.heightMark, this.names, this.hideBots, this.themeColor, this.color
      });
      this.themeColor.m5(this::updateVisibility);
      this.updateVisibility();
   }

   private void updateVisibility() {
      this.color.setVisible(!this.themeColor.m6());
   }

   @Override
   public void onEnable() {
      this.startNanos = System.nanoTime();
      this.lastNanos = 0L;
      this.blips.clear();
   }

   @Override
   public void onDisable() {
      this.blips.clear();
      this.lastNanos = 0L;
   }

   @EventHandler
   public void onRender(RenderEvent event) {
      DrawContext ctx = event.m583();
      if (ctx == null || this.mc.player == null || this.mc.world == null) {
         return;
      }

      if (this.mc.options.hudHidden || this.mc.getDebugHud().shouldShowDebugHud() || !Render2DUtil.m41()) {
         return;
      }

      long now = System.nanoTime();
      float dt = this.lastNanos == 0L ? 0.016F : Math.min(0.05F, (float)(now - this.lastNanos) / 1.0E9F);
      this.lastNanos = now;
      float time = (float)(now - this.startNanos) / 1.0E9F;
      float td = this.mc.getRenderTickCounter().getTickProgress(false);

      float r = (float)this.size.getValue();
      float rangeBlocks = (float)this.range.getValue();
      float m = (float)this.margin.getValue();
      float w = (float)Render2DUtil.m113();
      float h = (float)Render2DUtil.m189();
      String pos = this.position.m18();
      boolean right = pos.contains("Справа");
      boolean bottom = pos.contains("снизу");
      float cx = right ? w - m - r : m + r;
      float cy = bottom ? h - m - r : m + r;

      boolean rot = this.rotate.m6();
      double playerYawRad = Math.toRadians(this.mc.player.getYaw(td)) + Math.PI / 2;
      double viewYawRad = rot ? playerYawRad : Math.toRadians(180.0) + Math.PI / 2;

      Color accent = this.themeColor.m6() ? ThemeManager.m1379() : this.color.m7();

      this.updateBlips(dt, td, rangeBlocks, viewYawRad);

      // --- фон и свечение ---
      Render2DUtil.m211(cx, cy, r * 0.9F, a(accent, 0.34F));
      Render2DUtil.m195(cx - r, cy - r, r * 2.0F, r * 2.0F, r, new Color(12, 14, 20, 170));

      // --- сетка ---
      Color grid = new Color(255, 255, 255, 26);
      Render2DUtil.m212(cx, cy, r * 0.33F, 0.7F, grid);
      Render2DUtil.m212(cx, cy, r * 0.66F, 0.7F, grid);
      Render2DUtil.m195(cx - r, cy - 0.35F, r * 2.0F, 0.7F, 0.0F, grid);
      Render2DUtil.m195(cx - 0.35F, cy - r, 0.7F, r * 2.0F, 0.0F, grid);

      // --- конус обзора (угол в шейдере = угол на радаре - 90 градусов) ---
      if (this.fovCone.m6()) {
         float fov = (float)(int)this.mc.options.getFov().getValue();
         float forward = (float)Math.toDegrees(playerYawRad - viewYawRad) - 90.0F;
         Render2DUtil.m213(cx, cy, r / 2.0F, r, forward - fov / 2.0F, fov / 360.0F, a(accent, 0.09F));
         Render2DUtil.m213(cx, cy, r / 2.0F, r, forward - fov / 4.0F, fov / 720.0F, a(accent, 0.09F));
      }

      // --- сканер со шлейфом ---
      if (this.sweep.m6()) {
         float ang = (time % 3.0F) / 3.0F * 360.0F;
         int steps = 10;
         for (int i = 0; i < steps; i++) {
            float fade = 1.0F - (float)i / (float)steps;
            Render2DUtil.m213(cx, cy, r / 2.0F, r, ang - (i + 1) * 5.0F, 5.0F / 360.0F, a(accent, 0.20F * fade));
         }

         Render2DUtil.m213(cx, cy, r / 2.0F, r, ang - 1.0F, 1.5F / 360.0F, a(accent, 0.55F));
      }

      // --- пульс ---
      if (this.pulse.m6()) {
         float t = (time % 2.5F) / 2.5F;
         Render2DUtil.m212(cx, cy, r * t, 1.0F, a(accent, 0.30F * (1.0F - t)));
      }

      // --- обводка ---
      Render2DUtil.m202(cx - r, cy - r, r * 2.0F, r * 2.0F, r, 1.2F, accent);

      // --- стороны света ---
      String[] dirs = {"N", "E", "S", "W"};
      double[] dirAng = {-Math.PI / 2, 0.0, Math.PI / 2, Math.PI};
      for (int i = 0; i < 4; i++) {
         float ang = (float)(dirAng[i] - viewYawRad);
         float tx = cx + (float)Math.sin(ang) * r * 0.84F;
         float ty = cy - (float)Math.cos(ang) * r * 0.84F;
         Color c = i == 0 ? new Color(255, 95, 95, 235) : new Color(255, 255, 255, 110);
         Render2DUtil.m206(ctx, tx, ty - 3.0F, dirs[i], i == 0 ? 7.0F : 5.5F, c, "center");
      }

      // --- точки игроков ---
      float pad = 6.0F;
      int inRange = 0;
      for (Blip b : this.blips.values()) {
         if (b.alpha < 0.02F) {
            continue;
         }

         float px = cx + b.x * (r - pad);
         float py = cy + b.y * (r - pad);
         Color base = b.friend ? FRIEND : ENEMY;
         float dot = 2.6F;
         float al = b.alpha;
         boolean edge = b.dist > rangeBlocks;
         if (!edge) {
            inRange++;
         }

         if (this.heightMark.m6()) {
            if (b.dy > 3.0F) {
               dot = 3.2F;
            } else if (b.dy < -3.0F) {
               dot = 2.0F;
               al *= 0.75F;
            }
         }

         if (b.dist < 10.0F && !b.friend) {
            dot *= 1.0F + 0.22F * (float)Math.sin(time * 9.0F);
         }

         Render2DUtil.m211(px, py, dot * 2.6F, a(base, 0.42F * al));
         Render2DUtil.m195(px - dot, py - dot, dot * 2.0F, dot * 2.0F, dot, a(base, al));
         if (this.heightMark.m6() && b.dy > 3.0F) {
            Render2DUtil.m212(px, py, dot + 2.2F, 0.8F, a(base, 0.85F * al));
         }

         if (this.names.m6() && !edge) {
            Render2DUtil.m206(ctx, px, py + dot + 2.5F, b.name, 5.5F, a(Color.WHITE, 0.9F * al), "center");
         }
      }

      // --- центр (это ты) ---
      Render2DUtil.m195(cx - 2.0F, cy - 2.0F, 4.0F, 4.0F, 2.0F, Color.WHITE);

      // --- подпись ---
      String label = inRange + " | " + (int)rangeBlocks + "m";
      float ly = bottom ? cy - r - 9.0F : cy + r + 3.0F;
      Render2DUtil.m206(ctx, cx, ly, label, 5.5F, new Color(255, 255, 255, 140), "center");
   }

   private void updateBlips(float dt, float td, float rangeBlocks, double viewYawRad) {
      for (Blip b : this.blips.values()) {
         b.seen = false;
      }

      AntiBot antiBot = ModuleManager.getModule(AntiBot.class);
      Vec3d me = this.mc.player.getLerpedPos(td);
      float kMove = 1.0F - (float)Math.exp(-18.0F * dt);
      float kFade = 1.0F - (float)Math.exp(-10.0F * dt);

      for (PlayerEntity p : this.mc.world.getPlayers()) {
         if (p == this.mc.player || !p.isAlive() || p.isSpectator()) {
            continue;
         }

         if (this.hideBots.m6() && antiBot != null && antiBot.isEnabled() && antiBot.m595(p)) {
            continue;
         }

         Vec3d pp = p.getLerpedPos(td);
         double dx = pp.x - me.x;
         double dz = pp.z - me.z;
         double dist = Math.sqrt(dx * dx + dz * dz);
         float ang = (float)(Math.atan2(dz, dx) - viewYawRad);
         float nd = (float)Math.min(dist / (double)rangeBlocks, 1.0);
         float tx = (float)Math.sin(ang) * nd;
         float ty = -(float)Math.cos(ang) * nd;

         Blip b = this.blips.computeIfAbsent(p.getUuid(), k -> new Blip());
         String name = p.getName().getString();
         b.name = name;
         b.friend = RaytraceUtil.m80(name);
         b.dist = (float)dist;
         b.dy = (float)(pp.y - me.y);
         b.seen = true;
         b.targetAlpha = dist > (double)rangeBlocks ? 0.55F : 1.0F;
         if (!b.init) {
            b.x = tx;
            b.y = ty;
            b.init = true;
         } else {
            b.x += (tx - b.x) * kMove;
            b.y += (ty - b.y) * kMove;
         }
      }

      Iterator<Blip> it = this.blips.values().iterator();
      while (it.hasNext()) {
         Blip b = it.next();
         float target = b.seen ? b.targetAlpha : 0.0F;
         b.alpha += (target - b.alpha) * kFade;
         if (!b.seen && b.alpha < 0.01F) {
            it.remove();
         }
      }
   }

   private static Color a(Color c, float alpha) {
      int v = Math.clamp((long)Math.round(alpha * 255.0F), 0, 255);
      return new Color(c.getRed(), c.getGreen(), c.getBlue(), v);
   }
}

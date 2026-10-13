package ua.daamky.features.render;

import ua.daamky.events.KeyEvent;
import ua.daamky.events.RenderEvent;
import ua.daamky.gui.Daamky_2;
import ua.daamky.gui.theme.ThemeManager;
import ua.daamky.settings.BooleanSetting;
import ua.daamky.settings.NumberSetting;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.ModuleManager;
import ua.daamky.system.api.NewFunction;
import ua.daamky.system.events.EventHandler;
import ua.daamky.utils.ProjectionUtil;
import ua.daamky.utils.render.Render2DUtil;
import java.awt.Color;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Formatting;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

@NewFunction(
   I0 = "Waypoints",
   I00 = "Метки в мире с дистанцией и авто-метка места смерти (.wp add/del/list/clear, .bind add wp <клавиша>)",
   I000 = Category.RENDER
)
public class Waypoints extends Module {
   private static final List<Point> POINTS = new ArrayList<>();
   private static final Color[] PALETTE = {
      new Color(96, 165, 250), new Color(74, 222, 128), new Color(250, 204, 21),
      new Color(232, 121, 249), new Color(251, 146, 60), new Color(45, 212, 191)
   };
   private static final Color DEATH = new Color(255, 85, 85);
   private static boolean loaded;
   private static Vec3d lastAlive;
   private static boolean wasDead;
   private static int bindKey = -1;
   private static String bindName = "";
   private static final String QUICK_NAME = "Waypoint";

   private final BooleanSetting distance = new BooleanSetting("Дистанция", true);
   private final BooleanSetting deathPoint = new BooleanSetting("Метка смерти", true);
   private final BooleanSetting fade = new BooleanSetting("Тускнеть вдали от прицела", true);
   private final BooleanSetting themeColor = new BooleanSetting("Цвет темы", false);
   private final NumberSetting maxDist = new NumberSetting("Макс. дистанция (0 = любая)", 0.0, 0.0, 5000.0, 50.0);
   private final NumberSetting hideNear = new NumberSetting("Скрывать ближе", 4.0, 0.0, 30.0, 1.0);
   private final NumberSetting scale = new NumberSetting("Размер", 1.0, 0.6, 1.8, 0.1);

   private static class Point {
      String server;
      String dim;
      String name;
      double x;
      double y;
      double z;
      boolean death;

      Point(String server, String dim, String name, double x, double y, double z, boolean death) {
         this.server = server;
         this.dim = dim;
         this.name = name;
         this.x = x;
         this.y = y;
         this.z = z;
         this.death = death;
      }
   }

   public Waypoints() {
      this.addSettings(new Setting[]{this.distance, this.deathPoint, this.fade, this.themeColor, this.maxDist, this.hideNear, this.scale});
   }

   // ------------------------------------------------------------------ рендер

   @EventHandler
   public void onRender(RenderEvent event) {
      DrawContext ctx = event.m583();
      MinecraftClient mc = MinecraftClient.getInstance();
      if (ctx == null || mc.player == null || mc.world == null) {
         return;
      }

      ensureLoaded();
      trackDeath(mc);
      if (mc.options.hudHidden || mc.getDebugHud().shouldShowDebugHud() || !Render2DUtil.m41()) {
         return;
      }

      String server = serverKey(mc);
      String dim = dimKey(mc);
      Vec3d me = mc.player.getEyePos();
      float sc = (float)this.scale.getValue();
      float cx = (float)Render2DUtil.m113() / 2.0F;
      float cy = (float)Render2DUtil.m189() / 2.0F;
      Color themed = ThemeManager.m1379();

      for (Point p : POINTS) {
         if (!p.server.equals(server) || !p.dim.equals(dim)) {
            continue;
         }

         double dx = p.x + 0.5 - me.x;
         double dy = p.y + 1.0 - me.y;
         double dz = p.z + 0.5 - me.z;
         double dist = Math.sqrt(dx * dx + dy * dy + dz * dz);
         if (dist < this.hideNear.getValue() || (this.maxDist.getValue() > 0.0 && dist > this.maxDist.getValue())) {
            continue;
         }

         float[] pos = ProjectionUtil.m224(new Vec3d(p.x + 0.5, p.y + 1.0, p.z + 0.5));
         if (pos == null) {
            continue;
         }

         float x = pos[0];
         float y = pos[1];
         if (x < -20.0F || y < -20.0F || x > Render2DUtil.m113() + 20 || y > Render2DUtil.m189() + 20) {
            continue;
         }

         Color base = p.death ? DEATH : (this.themeColor.m6() ? themed : PALETTE[Math.floorMod(p.name.hashCode(), PALETTE.length)]);
         float alpha = 1.0F;
         if (this.fade.m6()) {
            float d = (float)Math.hypot(x - cx, y - cy) / (Render2DUtil.m113() * 0.40F);
            alpha = Math.clamp(1.0F - d * 0.55F, 0.42F, 1.0F);
         }

         float r = 3.4F * sc;
         Render2DUtil.m211(x, y, r * 3.0F, a(base, 0.32F * alpha));
         Render2DUtil.m195(x - r, y - r, r * 2.0F, r * 2.0F, r, a(base, alpha));
         Render2DUtil.m212(x, y, r + 2.6F * sc, 0.9F, a(base, 0.8F * alpha));
         Render2DUtil.m206(ctx, x, y - r - 9.5F * sc, p.name, 7.0F * sc, a(Color.WHITE, alpha), "center");
         if (this.distance.m6()) {
            String text = dist >= 1000.0 ? String.format(Locale.ROOT, "%.1fkm", dist / 1000.0) : (int)dist + "m";
            Render2DUtil.m206(ctx, x, y + r + 3.0F * sc, text, 6.0F * sc, a(base, 0.95F * alpha), "center");
         }
      }
   }

   @EventHandler
   public void onKey(KeyEvent event) {
      if (this.mc.currentScreen != null || event.m189() != 1 || bindKey < 0) {
         return;
      }

      if (event.m580().key() == bindKey) {
         placeLookedAt();
      }
   }

   private static void placeLookedAt() {
      MinecraftClient mc = MinecraftClient.getInstance();
      if (mc.player == null || mc.world == null) {
         return;
      }

      ensureLoaded();
      HitResult hit = mc.player.raycast(300.0, mc.getRenderTickCounter().getTickProgress(false), false);
      if (hit == null || hit.getType() != HitResult.Type.BLOCK) {
         Daamky_2.m467("Смотри на блок, чтобы поставить метку", Formatting.YELLOW);
         return;
      }

      BlockPos b = ((BlockHitResult)hit).getBlockPos();
      String server = serverKey(mc);
      String dim = dimKey(mc);
      POINTS.removeIf(p -> !p.death && p.server.equals(server) && p.dim.equals(dim) && p.name.equals(QUICK_NAME));
      POINTS.add(new Point(server, dim, QUICK_NAME, b.getX(), b.getY(), b.getZ(), false));
      save();
      Daamky_2.m467("Метка «" + QUICK_NAME + "»: " + b.getX() + " " + b.getY() + " " + b.getZ(), Formatting.GREEN);
   }

   private static int parseKey(String raw) {
      String k = raw.trim().toUpperCase(Locale.ROOT);
      if (k.length() == 1) {
         char c = k.charAt(0);
         if (c >= '0' && c <= '9') {
            return 48 + (c - '0');
         }

         if (c >= 'A' && c <= 'Z') {
            return c;
         }
      }

      if (k.matches("F([1-9]|1[0-2])")) {
         return 290 + Integer.parseInt(k.substring(1)) - 1;
      }

      if (k.matches("NUMPAD[0-9]")) {
         return 320 + (k.charAt(6) - '0');
      }

      if (k.equals("SPACE")) {
         return 32;
      }

      if (k.matches("[0-9]{2,3}")) {
         return Integer.parseInt(k);
      }

      return -1;
   }

   private void trackDeath(MinecraftClient mc) {
      boolean dead = mc.player.isDead() || mc.player.getHealth() <= 0.0F;
      if (!dead) {
         lastAlive = new Vec3d(mc.player.getX(), mc.player.getY(), mc.player.getZ());
         wasDead = false;
      } else if (!wasDead) {
         wasDead = true;
         if (this.deathPoint.m6() && lastAlive != null) {
            String server = serverKey(mc);
            String dim = dimKey(mc);
            POINTS.removeIf(p -> p.death && p.server.equals(server) && p.dim.equals(dim));
            POINTS.add(new Point(server, dim, "Смерть", Math.floor(lastAlive.x), Math.floor(lastAlive.y), Math.floor(lastAlive.z), true));
            save();
            Daamky_2.m467("Метка смерти: " + (int)lastAlive.x + " " + (int)lastAlive.y + " " + (int)lastAlive.z, Formatting.RED);
         }
      }
   }

   // ------------------------------------------------------------------ команды

   public static boolean m20(String raw) {
      String line = raw.trim();
      String low = line.toLowerCase(Locale.ROOT);
      if (low.equals(".bind") || low.startsWith(".bind ")) {
         return bindCommand(line);
      }

      if (!low.equals(".wp") && !low.startsWith(".wp ")) {
         return false;
      }

      MinecraftClient mc = MinecraftClient.getInstance();
      String[] t = line.split("\\s+");
      String sub = t.length > 1 ? t[1].toLowerCase(Locale.ROOT) : "";
      if (mc.player == null || mc.world == null) {
         return true;
      }

      ensureLoaded();
      String server = serverKey(mc);
      String dim = dimKey(mc);
      switch (sub) {
         case "add" -> {
            if (t.length < 3) {
               help();
               return true;
            }

            int end = t.length;
            double x = Math.floor(mc.player.getX());
            double y = Math.floor(mc.player.getY());
            double z = Math.floor(mc.player.getZ());
            if (t.length >= 6) {
               try {
                  double nx = Double.parseDouble(t[t.length - 3]);
                  double ny = Double.parseDouble(t[t.length - 2]);
                  double nz = Double.parseDouble(t[t.length - 1]);
                  x = Math.floor(nx);
                  y = Math.floor(ny);
                  z = Math.floor(nz);
                  end = t.length - 3;
               } catch (NumberFormatException ignored) {
               }
            }

            String name = clean(String.join(" ", java.util.Arrays.copyOfRange(t, 2, end)));
            if (name.isEmpty()) {
               help();
               return true;
            }

            POINTS.removeIf(p -> !p.death && p.server.equals(server) && p.dim.equals(dim) && p.name.equalsIgnoreCase(name));
            POINTS.add(new Point(server, dim, name, x, y, z, false));
            save();
            Daamky_2.m467("Метка «" + name + "» на " + (int)x + " " + (int)y + " " + (int)z, Formatting.GREEN);
         }
         case "del", "remove" -> {
            if (t.length < 3) {
               help();
               return true;
            }

            String name = clean(String.join(" ", java.util.Arrays.copyOfRange(t, 2, t.length)));
            boolean removed = POINTS.removeIf(p -> p.server.equals(server) && p.dim.equals(dim) && p.name.equalsIgnoreCase(name));
            if (removed) {
               save();
            }

            Daamky_2.m467(removed ? "Удалена: " + name : "Не найдена: " + name, removed ? Formatting.GREEN : Formatting.YELLOW);
         }
         case "list" -> {
            int n = 0;
            for (Point p : POINTS) {
               if (p.server.equals(server) && p.dim.equals(dim)) {
                  n++;
                  Daamky_2.m467(p.name + "  " + (int)p.x + " " + (int)p.y + " " + (int)p.z, p.death ? Formatting.RED : Formatting.GRAY);
               }
            }

            if (n == 0) {
               Daamky_2.m467("Меток нет", Formatting.YELLOW);
            }
         }
         case "clear" -> {
            POINTS.removeIf(p -> p.server.equals(server) && p.dim.equals(dim));
            save();
            Daamky_2.m467("Все метки здесь удалены", Formatting.GREEN);
         }
         default -> help();
      }

      return true;
   }

   private static boolean bindCommand(String line) {
      String[] t = line.split("\\s+");
      ensureLoaded();
      if (t.length >= 3 && t[2].equalsIgnoreCase("wp")) {
         String sub = t[1].toLowerCase(Locale.ROOT);
         if (sub.equals("add") && t.length >= 4) {
            int key = parseKey(t[3]);
            if (key < 0) {
               Daamky_2.m467("Не понял клавишу: " + t[3] + " (пример: G, 5, F6, NUMPAD3)", Formatting.YELLOW);
               return true;
            }

            bindKey = key;
            bindName = t[3].toUpperCase(Locale.ROOT);
            saveBind();
            Waypoints module = ModuleManager.getModule(Waypoints.class);
            Daamky_2.m467("Бинд wp на " + bindName + ": смотри на блок и жми клавишу", Formatting.GREEN);
            if (module != null && !module.isEnabled()) {
               Daamky_2.m467("Включи модуль Waypoints, иначе метки не будут работать", Formatting.YELLOW);
            }

            return true;
         }

         if (sub.equals("del") || sub.equals("remove")) {
            bindKey = -1;
            bindName = "";
            saveBind();
            Daamky_2.m467("Бинд wp снят", Formatting.GREEN);
            return true;
         }
      }

      Daamky_2.m467(".bind add wp <клавиша> - метка на блок, куда смотришь (G, 5, F6, NUMPAD3)", Formatting.GRAY);
      Daamky_2.m467(".bind del wp - снять бинд", Formatting.GRAY);
      return true;
   }

   private static Path bindFile() {
      return FabricLoader.getInstance().getGameDir().resolve("Daamky").resolve("waypoint_bind.txt");
   }

   private static void saveBind() {
      try {
         Path f = bindFile();
         Files.createDirectories(f.getParent());
         Files.write(f, List.of(bindName));
      } catch (Exception ignored) {
      }
   }

   public static List<String> m428(String raw) {
      String low = raw.stripLeading().toLowerCase(Locale.ROOT);
      if (low.equals(".") || (low.startsWith(".") && ".wp".startsWith(low))) {
         return List.of(".wp");
      }

      if (low.startsWith(".b") && ".bind".startsWith(low)) {
         return List.of(".bind");
      }

      if (low.startsWith(".bind ")) {
         String rest = low.substring(6);
         return List.of(".bind add wp <key>", ".bind del wp")
            .stream()
            .filter(x -> x.substring(6).startsWith(rest))
            .toList();
      }

      if (!low.startsWith(".wp ")) {
         return List.of();
      }

      String rest = low.substring(4);
      if (rest.contains(" ")) {
         return List.of();
      }

      return List.of(".wp add <name>", ".wp del <name>", ".wp list", ".wp clear")
         .stream()
         .filter(s -> s.substring(4).startsWith(rest))
         .toList();
   }

   private static void help() {
      Daamky_2.m467(".wp add <имя> [x y z] - добавить метку (по умолчанию где стоишь)", Formatting.GRAY);
      Daamky_2.m467(".wp del <имя> - удалить метку", Formatting.GRAY);
      Daamky_2.m467(".wp list - список меток", Formatting.GRAY);
      Daamky_2.m467(".wp clear - удалить все метки здесь", Formatting.GRAY);
   }

   // ------------------------------------------------------------------ хранение

   private static Path file() {
      return FabricLoader.getInstance().getGameDir().resolve("Daamky").resolve("waypoints.txt");
   }

   private static void ensureLoaded() {
      if (loaded) {
         return;
      }

      loaded = true;
      try {
         Path bf = bindFile();
         if (Files.exists(bf)) {
            List<String> l = Files.readAllLines(bf);
            if (!l.isEmpty()) {
               bindName = l.get(0).trim();
               bindKey = bindName.isEmpty() ? -1 : parseKey(bindName);
            }
         }
      } catch (Exception ignored) {
      }

      try {
         Path f = file();
         if (Files.exists(f)) {
            for (String line : Files.readAllLines(f)) {
               String[] s = line.split("\\|");
               if (s.length == 7) {
                  POINTS.add(new Point(s[0], s[1], s[2], Double.parseDouble(s[3]), Double.parseDouble(s[4]), Double.parseDouble(s[5]), s[6].equals("1")));
               }
            }
         }
      } catch (Exception ignored) {
      }
   }

   private static void save() {
      try {
         Path f = file();
         Files.createDirectories(f.getParent());
         List<String> out = new ArrayList<>();
         for (Point p : POINTS) {
            out.add(p.server + "|" + p.dim + "|" + p.name + "|" + p.x + "|" + p.y + "|" + p.z + "|" + (p.death ? "1" : "0"));
         }

         Files.write(f, out);
      } catch (Exception ignored) {
      }
   }

   private static String clean(String s) {
      return s.replace("|", "").trim();
   }

   private static String serverKey(MinecraftClient mc) {
      return mc.getCurrentServerEntry() != null && mc.getCurrentServerEntry().address != null ? mc.getCurrentServerEntry().address.toLowerCase(Locale.ROOT) : "singleplayer";
   }

   private static String dimKey(MinecraftClient mc) {
      return mc.world.getRegistryKey().getValue().toString();
   }

   private static Color a(Color c, float alpha) {
      int v = Math.clamp((long)Math.round(alpha * 255.0F), 0, 255);
      return new Color(c.getRed(), c.getGreen(), c.getBlue(), v);
   }
}

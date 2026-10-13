package ua.daamky.features.misc;

import ua.daamky.settings.BindSetting;
import ua.daamky.settings.BooleanSetting;
import ua.daamky.settings.ModeSettingBase;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.ModuleManager;
import ua.daamky.system.api.NewFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@NewFunction(
   I0 = "StreamerMode",
   I00 = "Визуально скрывает ник, донат и баланс",
   I000 = Category.MISC
)
public class StreamerMode extends Module {
   private static final Pattern f1 = Pattern.compile("(Баланс\\D*)([\\d\\s.,]+)");
   private static final long f2 = 80L;
   private final ModeSettingBase f3 = new ModeSettingBase(
      "Режим", "ReallyWorld", "Простой"
   );
   private final BooleanSetting f4 = new BooleanSetting("Донат", true);
   private final BooleanSetting f5 = new BooleanSetting("Баланс", true);
   private final BindSetting f6 = new BindSetting(
      "Значение баланса", "0", 32
   );
   private final BooleanSetting f7 = new BooleanSetting("Ник RW", true);
   private final BooleanSetting f8 = new BooleanSetting("Ник", true);
   private final BindSetting f9 = new BindSetting(
      "Кастомный ник", "MrDomer", 32
   );
   private long f10;
   private boolean f11;

   public StreamerMode() {
      this.addSettings(new Setting[]{this.f3, this.f4, this.f5, this.f6, this.f7, this.f8, this.f9});
      this.f3.m5(this::m115);
      this.f5.m5(this::m115);
      this.f8.m5(this::m115);
      this.m115();
   }

   public static String m58(String var0) {
      StreamerMode var1 = ModuleManager.getModule(StreamerMode.class);
      return var1 != null && var1.isEnabled() ? var1.m59(var0) : var0;
   }

   private String m59(String var1) {
      if (var1 != null && this.mc.getSession() != null) {
         String var2 = var1;
         String var3 = this.mc.getSession().getUsername();
         if (this.f3.m17("ReallyWorld")) {
            if (this.f4.m6() && this.m374(var1)) {
               return var1.replaceAll("\\S", "");
            } else {
               if (this.f4.m6()) {
                  var2 = this.m223(var1);
               }

               if (this.f5.m6()) {
                  var2 = this.m987(var2);
               }

               if (this.f7.m6()) {
                  var2 = this.m765(var2, var3, "MrDomer");
               }

               return var2;
            }
         } else {
            if (this.f8.m6()) {
               var2 = this.m765(var1, var3, this.f9.m30());
            }

            return var2;
         }
      } else {
         return var1;
      }
   }

   private String m223(String var1) {
      int var2 = var1.indexOf("Ранг:");
      if (var2 < 0) {
         return var1;
      } else {
         this.f10 = System.currentTimeMillis() + 80L;
         this.f11 = false;
         return var1.substring(0, var2 + "Ранг:".length()) + " ꔷ";
      }
   }

   private boolean m374(String var1) {
      if (!this.f11 && System.currentTimeMillis() <= this.f10) {
         String var2 = var1.trim();
         if (!var2.isEmpty() && !var2.contains("Ранг:") && var2.length() <= 3) {
            boolean var3 = var2.codePoints().anyMatch(Character::isLetterOrDigit);
            if (var3) {
               return false;
            } else {
               this.f11 = true;
               return true;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private String m987(String var1) {
      Matcher var2 = f1.matcher(var1);
      return !var2.find() ? var1 : var2.replaceFirst(Matcher.quoteReplacement(var2.group(1) + this.f6.m30()));
   }

   private String m765(String var1, String var2, String var3) {
      return var2 != null && !var2.isBlank() && var3 != null && !var3.isBlank()
         ? Pattern.compile(Pattern.quote(var2), 66).matcher(var1).replaceAll(Matcher.quoteReplacement(var3))
         : var1;
   }

   private void m115() {
      boolean var1 = this.f3.m17("ReallyWorld");
      boolean var2 = !var1;
      this.f4.setVisible(var1);
      this.f5.setVisible(var1);
      this.f6.setVisible(var1 && this.f5.m6());
      this.f7.setVisible(var1);
      this.f8.setVisible(var2);
      this.f9.setVisible(var2 && this.f8.m6());
   }
}

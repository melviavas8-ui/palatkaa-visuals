package ua.daamky.features.misc;

import ua.daamky.events.PostMotionEvent;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;
import ua.daamky.system.events.EventHandler;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.scoreboard.ReadableScoreboardScore;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardDisplaySlot;
import net.minecraft.scoreboard.ScoreboardObjective;

@NewFunction(
   I0 = "ScoreboardHealth",
   I00 = "Берет здоровье из скорборда",
   I000 = Category.MISC
)
public class ScoreboardHealth extends Module {
   public float f1;

   public float m2() {
      return this.f1;
   }

   @EventHandler
   public void m67(PostMotionEvent var1) {
      if (this.mc.world != null) {
         for (AbstractClientPlayerEntity var3 : this.mc.world.getPlayers()) {
            Scoreboard var4 = this.mc.world.getScoreboard();
            if (var4 != null) {
               ScoreboardObjective var5 = var4.getObjectiveForSlot(ScoreboardDisplaySlot.BELOW_NAME);
               if (var5 != null) {
                  ReadableScoreboardScore var6 = var4.getScore(var3, var5);
                  if (var6 != null) {
                     String var7 = var6.getScore() + " " + var5.getDisplayName().getString();
                     String var8 = var7.replaceAll("[^0-9]", "");

                     try {
                        if (!var8.isEmpty()) {
                           int var9 = Integer.parseInt(var8);
                           if ((float)var9 <= var3.getMaxHealth()) {
                              this.f1 = (float)var9;
                           }
                        }
                     } catch (NumberFormatException var10) {
                     }
                  }
               }
            }
         }
      }
   }
}

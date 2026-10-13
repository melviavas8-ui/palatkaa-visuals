package ua.daamky.utils;

import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardEntry;
import net.minecraft.scoreboard.Team;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

public class ScoreboardUtil {
   public void m1228(Scoreboard var1, ScoreboardEntry var2) {
      String var3 = var2.owner();
      Team var4 = var1.getScoreHolderTeam(var3);
      MutableText var5 = Team.decorateName(var4, Text.literal(var3));
      String var6 = var5.getString();
      Text var7 = var2.display();
   }
}

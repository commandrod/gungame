package me.commandrod.gungame.scoreboard;

import me.commandrod.gungame.levels.LevelManager;
import me.commandrod.gungame.utils.ScoreboardUtils;
import me.commandrod.gungame.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class GunGameSB {

    public static void setScoreboard(Player player){
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = scoreboard.registerNewObjective("gungame", "dummy", Utils.color("&b&lActiveEvents &7| &fגאן גיים"));
        obj.getScore(Utils.color("&f     ")).setScore(4);
        obj.getScore(Utils.color("&9Level: &b" + LevelManager.getPlayerLevel(player).getLevel())).setScore(3);
        obj.getScore(Utils.color("&9KillStreak: &b" + ScoreboardUtils.getKillStreak(player))).setScore(2);
        obj.getScore(Utils.color("&f")).setScore(1);
        obj.getScore(Utils.color("&eactiveevents.ml")).setScore(0);
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        player.setScoreboard(scoreboard);
    }
}
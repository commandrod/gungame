package me.commandrod.gungame.scoreboard;

import me.commandrod.gungame.levels.LevelManager;
import me.commandrod.gungame.utils.StatUtils;
import me.commandrod.gungame.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class GunGameSB implements Runnable {

    private static void setScoreboard(Player player){
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = scoreboard.registerNewObjective("gungame", "dummy", Utils.color("&bActiveEvents &7| &fגאן גיים"));
        obj.getScore(Utils.color("&r&7--------------------")).setScore(8);
        obj.getScore(Utils.color("&bLevel: &f" + LevelManager.getPlayerLevel(player).getLevel())).setScore(7);
        obj.getScore(Utils.color("&bKillStreak: &f" + StatUtils.getKillStreak(player))).setScore(6);
        obj.getScore(Utils.color("&f&7--------------------")).setScore(5);
        obj.getScore(Utils.color("&bKills: &f" + StatUtils.getKills(player))).setScore(4);
        obj.getScore(Utils.color("&bDeaths: &f" + StatUtils.getDeaths(player))).setScore(3);
        obj.getScore(Utils.color("&bKDR: &f" + ((float) StatUtils.getKills(player) / StatUtils.getDeaths(player)))).setScore(2);
        obj.getScore(Utils.color("&7--------------------")).setScore(1);
        obj.getScore(Utils.color("&bactiveevents.ml")).setScore(0);
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        player.setScoreboard(scoreboard);
    }

    @Override
    public void run() {
        for (Player players : Bukkit.getOnlinePlayers()){
            setScoreboard(players);
        }
    }
}
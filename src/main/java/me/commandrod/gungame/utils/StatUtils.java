package me.commandrod.gungame.utils;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.UUID;

public class StatUtils {

    private static JavaPlugin plugin;
    public StatUtils(JavaPlugin plugin) { StatUtils.plugin = plugin; }

    public static final HashMap<UUID, Integer> killstreakMap = new HashMap<>();
    public static final HashMap<Integer, String> milestones = new HashMap<>();

    public static void setupMilestones(){
        milestones.put(10, "&b" + "The player {} reached a killstreak of 10!");
        milestones.put(25, "&c" + "The player {} reached a killstreak of 25!");
        milestones.put(50, "&c" + "The player {} reached a killstreak of 50!");
        milestones.put(100, "&c&l" + "The player {} reached a killstreak of 100!");
        milestones.put(250, "&c&l" + "The player {} reached a killstreak of 250!");
        milestones.put(500, "&c&l" + "The player {} reached a killstreak of 500!");
    }

    public static int getKillStreak(Player player) {
        return killstreakMap.get(player.getUniqueId());
    }

    public static void addKill(Player player){
        if (!killstreakMap.containsKey(player.getUniqueId())){
            killstreakMap.put(player.getUniqueId(), 1);
        } else {
            killstreakMap.replace(player.getUniqueId(), killstreakMap.get(player.getUniqueId()) + 1);
        }
        int killNumber = killstreakMap.get(player.getUniqueId());
        if (!milestones.containsKey(killNumber)) return;
        if (killNumber >= 100){
            SoundUtils.playSoundForAll(Sound.ENDERDRAGON_GROWL);
        }
        Bukkit.broadcastMessage(Utils.color("&f\n" + milestones.get(killNumber).replace("{}", player.getName()) + "\n&f "));
    }

    public static void resetKills(Player player){
        killstreakMap.remove(player.getUniqueId());
        Utils.addValue(killstreakMap, player.getUniqueId(), 0);
    }

    public static int getKills(Player player) {
        return plugin.getConfig().getInt("settings." + player.getUniqueId() + ".kills");
    }

    public static int getDeaths(Player player) {
        return plugin.getConfig().getInt("settings." + player.getUniqueId() + ".deaths");
    }

    public static float getKDR(Player player) {
        return (float) getKills(player) / getDeaths(player);
    }

    public static int getCoins(Player player) {
        return getKills(player) * 3;
    }
}
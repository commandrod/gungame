package me.commandrod.gungame.utils;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class ScoreboardUtils {

    public static final HashMap<UUID, Integer> killstreakMap = new HashMap<>();
    public static final HashMap<Integer, String> milestones = new HashMap<>();

    public static void setupMilestones(){
        milestones.put(10, "&b" + "השחקן {} הגיע ל10 הריגות ברצף!");
        milestones.put(25, "&c" + "השחקן {} הגיע ל25 הריגות ברצף!");
        milestones.put(50, "&c" + "השחקן {} הגיע ל50 הריגות ברצף!");
        milestones.put(100, "&c&l" + "השחקן {} הגיע ל100 הריגות ברצף!");
        milestones.put(250, "&c&l" + "השחקן {} הגיע ל250 הריגות ברצף!");
        milestones.put(500, "&c&l" + "השחקן {} הגיע ל500 הריגות ברצף!");
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
            SoundUtils.playSoundForAll(Sound.ENTITY_ENDER_DRAGON_GROWL);
        }
        Bukkit.broadcastMessage(Utils.color(milestones.get(killNumber).replace("{}", player.getName())));
    }

    public static void resetKills(Player player){
        if (!killstreakMap.containsKey(player.getUniqueId())){
            killstreakMap.put(player.getUniqueId(), 0);
        } else {
            killstreakMap.replace(player.getUniqueId(), 0);
        }
    }
}
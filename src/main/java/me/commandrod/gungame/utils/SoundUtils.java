package me.commandrod.gungame.utils;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class SoundUtils {

    public static void playSoundForAll(Sound sound){
        for (Player players : Bukkit.getOnlinePlayers()){
            players.playSound(players.getLocation(), sound, 1, 1);
        }
    }

    public static void playSound(Player player, Sound sound) {
        player.playSound(player.getLocation(), sound, 1, 1);
    }

    public static void playSound(Player player, Sound sound, int pitch) {
        player.playSound(player.getLocation(), sound, 1, pitch);
    }
}
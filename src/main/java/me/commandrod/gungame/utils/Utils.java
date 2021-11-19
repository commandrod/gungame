package me.commandrod.gungame.utils;

import org.bukkit.ChatColor;

import java.util.HashMap;

public class Utils {
    public static String color(String s) { return ChatColor.translateAlternateColorCodes('&', s); }
    public static void addValue(HashMap map, Object key, Object value){
        if (map.containsKey(key)){
            map.replace(key, value);
            return;
        }
        map.put(key, value);
    }
}
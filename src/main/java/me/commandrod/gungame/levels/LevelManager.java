package me.commandrod.gungame.levels;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class LevelManager {

    private static final HashMap<UUID, Level> playerLevelMap = new HashMap<>();
    private static final HashMap<Integer, Level> levelMap = new HashMap<>();

    public static Level createLevel(int level, ItemStack[] armor, ItemStack weapon){
        Level lvl = new Level(level, armor, weapon);
        levelMap.put(level, lvl);
        return lvl;
    }

    public static void setLevel(Player player, int level){
        Level lvl = getLevel(level);
        if (playerLevelMap.containsKey(player.getUniqueId())){
            if (level < 1){
                playerLevelMap.replace(player.getUniqueId(), getLevel(1));
            } else if (level > 60){
                playerLevelMap.replace(player.getUniqueId(), getLevel(60));
            } else {
                playerLevelMap.replace(player.getUniqueId(), lvl);
            }
            player.getInventory().setArmorContents(lvl.getArmor());
            player.getInventory().setItem(0, lvl.getWeapon());
            player.updateInventory();
            return;
        }
        if (level < 1){
            playerLevelMap.put(player.getUniqueId(), getLevel(1));
        } else if (level > 60){
            playerLevelMap.put(player.getUniqueId(), getLevel(60));
        } else {
            playerLevelMap.put(player.getUniqueId(), lvl);
        }
        player.getInventory().setArmorContents(lvl.getArmor());
        player.getInventory().setItem(0, lvl.getWeapon());
        player.updateInventory();
    }

    public static Level getPlayerLevel(Player player){
        return playerLevelMap.get(player.getUniqueId());
    }

    public static Level getLevel(int level){
        if (level < 1){
            return levelMap.get(1);
        }
        if (level > 60){
            return levelMap.get(60);
        }
        return levelMap.get(level);
    }
}
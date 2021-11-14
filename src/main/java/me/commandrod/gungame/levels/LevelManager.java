package me.commandrod.gungame.levels;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.UUID;

public class LevelManager {

    private static final HashMap<UUID, Level> playerLevelMap = new HashMap<>();
    private static final HashMap<Integer, Level> levelMap = new HashMap<>();
    private static final int maxLevel = 60;

    public static Level createLevel(int level, ItemStack[] armor, ItemStack weapon){
        ItemMeta weaponIM = weapon.getItemMeta();
        weaponIM.setUnbreakable(true);
        weaponIM.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        weaponIM.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        weapon.setItemMeta(weaponIM);
        for (ItemStack item : armor){
            if (item == null) continue;
            ItemMeta armorIM = item.getItemMeta();
            armorIM.setUnbreakable(true);
            armorIM.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            armorIM.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            item.setItemMeta(armorIM);
        }
        Level lvl = new Level(level, armor, weapon);
        levelMap.put(level, lvl);
        return lvl;
    }

    private static void addValue(HashMap map, Object key, Object value){
        if (map.containsKey(key)){
            map.replace(key, value);
            return;
        }
        map.put(key, value);
    }

    public static void setLevel(Player player, int level){
        Level lvl = getLevel(level);
        if (level < 1){
            addValue(playerLevelMap, player.getUniqueId(), getLevel(1));
        } else if (level > maxLevel){
            addValue(playerLevelMap, player.getUniqueId(), getLevel(maxLevel));
        } else {
            addValue(playerLevelMap, player.getUniqueId(), lvl);
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
        if (level > maxLevel){
            return levelMap.get(maxLevel);
        }
        return levelMap.get(level);
    }
}
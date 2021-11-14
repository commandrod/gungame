package me.commandrod.gungame.levels;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

@Getter
@Setter
public class Level {

    private int level;
    private ItemStack[] armor;
    private ItemStack weapon;

    // Protected, use LevelManager.createLevel() instead.
    protected Level(int level, ItemStack[] armor, ItemStack weapon) {
        this.level = level;
        this.armor = armor;
        this.weapon = weapon;
    }
}
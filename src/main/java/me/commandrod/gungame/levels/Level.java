package me.commandrod.gungame.levels;

import org.bukkit.inventory.ItemStack;

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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public ItemStack[] getArmor() {
        return armor;
    }

    public void setArmor(ItemStack[] armor) {
        this.armor = armor;
    }

    public ItemStack getWeapon() {
        return weapon;
    }

    public void setWeapon(ItemStack weapon) {
        this.weapon = weapon;
    }
}
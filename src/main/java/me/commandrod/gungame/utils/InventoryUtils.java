package me.commandrod.gungame.utils;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InventoryUtils {

    private static ItemStack backgroundItem(){
        ItemStack is = new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.BLACK.getData());
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(" ");
        is.setItemMeta(im);
        return is;
    }

    public static void background(Inventory gui) {
        for (int i = 0; i < gui.getSize(); i++){
            if (gui.getItem(i) != null) continue;
            gui.setItem(i, backgroundItem());
        }
    }

    public static ItemStack quickItem(String displayName, Material material, boolean enchanted){
        ItemStack is = new ItemStack(material);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(Utils.color(displayName));
        if (enchanted) im.addEnchant(Enchantment.ARROW_INFINITE, 1, false);
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        is.setItemMeta(im);
        return is;
    }
}
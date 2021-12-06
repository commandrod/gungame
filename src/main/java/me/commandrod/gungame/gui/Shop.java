package me.commandrod.gungame.gui;

import me.commandrod.gungame.utils.InventoryUtils;
import me.commandrod.gungame.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

public class Shop {

    public static Inventory getGUI(){
        Inventory gui = Bukkit.createInventory(null, 9*5, Utils.color("&bShop"));
        gui.setItem(22, InventoryUtils.quickItem("&aThunder", Material.FEATHER, false));
        InventoryUtils.background(gui);
        return gui;
    }
    // Soon
}
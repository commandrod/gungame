package me.commandrod.gungame.commands;

import lombok.Getter;
import me.commandrod.gungame.utils.*;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class Profile implements CommandExecutor {

    private JavaPlugin plugin;
    public Profile(JavaPlugin plugin){
        this.plugin = plugin;
    }

    private ItemStack head(Player player){
        ItemStack is = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
        SkullMeta im = (SkullMeta) is.getItemMeta();
        im.setDisplayName(Utils.color("&3" + player.getName()));
        plugin.saveConfig();
        plugin.reloadConfig();
        String kills = String.valueOf(StatUtils.getKills(player));
        String deaths = String.valueOf(StatUtils.getDeaths(player));
        String kdr = String.valueOf(StatUtils.getKDR(player));
        String coins = String.valueOf(StatUtils.getCoins(player));
        im.setLore(Arrays.asList(
                Utils.color("&8"),
                Utils.color("&bKills: &f" + kills),
                Utils.color("&bDeaths: &f" + deaths),
                Utils.color("&bKDR: &f" + kdr),
                Utils.color("&bCoins: &f" + coins)));
        im.setOwner(player.getName());
        is.setItemMeta(im);
        return is;
    }

    @Getter
    private static Inventory profileGUI;

    private void openGUI(Player player){
        profileGUI = Bukkit.createInventory(null, 9*5, Utils.color("&bYour Profile"));
        ItemStack head = head(player);
        profileGUI.setItem(21, InventoryUtils.quickItem("&6Shop", Material.GOLD_INGOT, false));
        profileGUI.setItem(23, head);
        InventoryUtils.background(profileGUI);
        player.openInventory(profileGUI);
        SoundUtils.playSound(player, Sound.NOTE_PLING);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("profile")){
            if (!(sender instanceof Player)){
                sender.sendMessage(MessageUtils.NOT_PLAYER);
                return true;
            }
            Player p = (Player) sender;
            openGUI(p);
        }
        return true;
    }
}
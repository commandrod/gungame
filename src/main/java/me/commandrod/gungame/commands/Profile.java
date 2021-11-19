package me.commandrod.gungame.commands;

import me.commandrod.gungame.utils.MessageUtils;
import me.commandrod.gungame.utils.SoundUtils;
import me.commandrod.gungame.utils.StatUtils;
import me.commandrod.gungame.utils.Utils;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class Profile implements CommandExecutor {

    private JavaPlugin plugin;
    public Profile(JavaPlugin plugin){
        this.plugin = plugin;
    }

    private ItemStack backgroundItem(){
        ItemStack is = new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.BLACK.getData());
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(" ");
        is.setItemMeta(im);
        return is;
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

    private ItemStack quickItem(String displayName, Material material, boolean enchanted){
        ItemStack is = new ItemStack(material);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(Utils.color(displayName));
        if (enchanted) im.addEnchant(Enchantment.ARROW_INFINITE, 1, false);
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        is.setItemMeta(im);
        return is;
    }

    private void openGUI(Player player){
        Inventory profileGUI = Bukkit.createInventory(null, 9*5, Utils.color("&bYour Profile"));
        ItemStack head = head(player);
        profileGUI.setItem(21, quickItem("&6Shop", Material.GOLD_INGOT, false));
        profileGUI.setItem(23, head);
        for (int i = 0; i < profileGUI.getSize(); i++){
            if (profileGUI.getItem(i) != null) continue;
            profileGUI.setItem(i, backgroundItem());
        }
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
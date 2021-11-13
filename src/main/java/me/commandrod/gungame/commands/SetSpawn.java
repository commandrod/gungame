package me.commandrod.gungame.commands;

import me.commandrod.gungame.utils.ConfigUtils;
import me.commandrod.gungame.utils.MessageUtils;
import me.commandrod.gungame.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawn implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("setspawn")){
            if (!(sender instanceof Player)){
                sender.sendMessage(MessageUtils.NOT_PLAYER);
                return true;
            }
            Player p = (Player) sender;
            if (!p.hasPermission("gungame.setspawn")){
                p.sendMessage(MessageUtils.PERM);
                return true;
            }
            p.sendMessage(Utils.color("&3Successfully changed the spawn location."));
            ConfigUtils.setLocation("spawn", p.getLocation());
        }
        return true;
    }
}
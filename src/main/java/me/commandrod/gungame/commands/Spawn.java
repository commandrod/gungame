package me.commandrod.gungame.commands;

import me.commandrod.gungame.utils.ConfigUtils;
import me.commandrod.gungame.utils.MessageUtils;
import me.commandrod.gungame.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Spawn implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("spawn")){
            if (!(sender instanceof Player)){
                sender.sendMessage(MessageUtils.NOT_PLAYER);
                return true;
            }
            Player p = (Player) sender;
            if (!p.hasPermission("gungame.spawn")){
                p.sendMessage(MessageUtils.PERM);
                return true;
            }
            if (args.length == 0){
                p.sendMessage(Utils.color("&3Teleported to spawn!"));
                p.teleport(ConfigUtils.getLocation("spawn"));
            } else {
                Player t = Bukkit.getPlayer(args[0]);
                if (t == null || !t.isOnline()){
                    p.sendMessage(MessageUtils.INVALID_PLAYER);
                    return true;
                }
                p.sendMessage(Utils.color("&3Teleported &b" + t.getName() + " &3to spawn!"));
                t.teleport(ConfigUtils.getLocation("spawn"));
            }
        }
        return true;
    }
}
package me.commandrod.gungame.commands;

import me.commandrod.gungame.utils.MessageUtils;
import me.commandrod.gungame.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Profile implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("profile")){
            if (!(sender instanceof Player)){
                sender.sendMessage(MessageUtils.NOT_PLAYER);
                return true;
            }
            Player p = (Player) sender;
            p.sendMessage(Utils.color("&c" + "בקרוב..."));
        }
        return true;
    }
}
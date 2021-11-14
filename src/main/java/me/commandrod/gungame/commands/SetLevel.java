package me.commandrod.gungame.commands;

import me.commandrod.gungame.levels.LevelManager;
import me.commandrod.gungame.utils.MessageUtils;
import me.commandrod.gungame.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetLevel implements CommandExecutor {

    int level;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("setlevel")){
            if (!sender.hasPermission("gungame.setlevel")){
                sender.sendMessage(MessageUtils.PERM);
                return true;
            }
            if (args.length > 0){
                try {
                    level = Integer.parseInt(args[0]);
                } catch (NumberFormatException e){
                    sender.sendMessage(MessageUtils.INVALID_NUMBER);
                    return true;
                }
            }
            if (args.length == 1) {
                if (!(sender instanceof Player)){
                    MessageUtils.cmdUsage(cmd, sender);
                    return true;
                }
                Player p = (Player) sender;
                LevelManager.setLevel(p, level);
                p.sendMessage(Utils.color("&3Set your level to &b" + level + "&3!"));
            } else if (args.length >= 2){
                Player t = Bukkit.getPlayer(args[1]);
                if (t == null || !t.isOnline()){
                    sender.sendMessage(MessageUtils.INVALID_PLAYER);
                    return true;
                }
                LevelManager.setLevel(t, level);
                sender.sendMessage(Utils.color("&3Set level of &b" + t.getName() + " &3to &b" + level + "&3!"));
            }
        }
        return true;
    }
}
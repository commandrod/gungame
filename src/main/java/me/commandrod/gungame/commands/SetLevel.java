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
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("setlevel")){
            if (!(sender instanceof Player)){
                sender.sendMessage(MessageUtils.NOT_PLAYER);
                return true;
            }
            Player p = (Player) sender;
            if (!p.hasPermission("gungame.setlevel")){
                p.sendMessage(MessageUtils.PERM);
                return true;
            }
            if (args.length == 1) {
                if (Integer.valueOf(args[0]) == null){
                    p.sendMessage(MessageUtils.INVALID_NUMBER);
                    return true;
                }
                int level = Integer.parseInt(args[0]);
                LevelManager.setLevel(p, level);
                p.sendMessage(Utils.color("&3Set your level to &b" + level + "&3!"));
            } else if (args.length >= 2){
                if (Integer.valueOf(args[0]) == null){
                    p.sendMessage(MessageUtils.INVALID_NUMBER);
                    return true;
                }
                int level = Integer.parseInt(args[0]);
                Player t = Bukkit.getPlayer(args[1]);
                if (t == null || !t.isOnline()){
                    p.sendMessage(MessageUtils.INVALID_PLAYER);
                    return true;
                }
                LevelManager.setLevel(t, level);
                p.sendMessage(Utils.color("&3Set level of &b" + t.getName() + " &3to &b" + level + "&3!"));
            }
        }
        return true;
    }
}
package me.commandrod.gungame.commands;

import me.commandrod.gungame.utils.MessageUtils;
import me.commandrod.gungame.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class Admin implements CommandExecutor {

    private JavaPlugin plugin;
    public Admin(JavaPlugin plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("admin")){
            if (!sender.hasPermission("gungame.admin")){
                sender.sendMessage(MessageUtils.PERM);
                return true;
            }
            if (args.length == 0){
                MessageUtils.cmdUsage(cmd, sender);
                return true;
            }
            switch (args[0]){
                case "reloadconfig":
                    plugin.reloadConfig();
                    plugin.saveConfig();
                    sender.sendMessage(Utils.color("&3Successfully reloaded the configuration file."));
                    break;
                default:
                    MessageUtils.cmdUsage(cmd, sender);
                    break;
            }
        }
        return true;
    }
}
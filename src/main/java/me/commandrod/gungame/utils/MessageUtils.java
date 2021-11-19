package me.commandrod.gungame.utils;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class MessageUtils {
    public static String PERM = Utils.color("&cInsufficient permissions.");
    public static String NOT_PLAYER = Utils.color("&cThis command may only be executed by a player.");
    public static String INVALID_PLAYER = Utils.color("&cYou must provide a valid online player!");
    public static String INVALID_NUMBER = Utils.color("&cYou must provide a valid number!");

    public static void cmdUsage(Command cmd, CommandSender sender) {
        sender.sendMessage(Utils.color("&3" + cmd.getDescription() + "&b\n" + cmd.getUsage()));
    }
}
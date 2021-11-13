package me.commandrod.gungame;

import lombok.Getter;
import me.commandrod.gungame.commands.SetLevel;
import me.commandrod.gungame.commands.SetSpawn;
import me.commandrod.gungame.commands.Spawn;
import me.commandrod.gungame.levels.RegisterLevels;
import me.commandrod.gungame.listeners.Events;
import me.commandrod.gungame.utils.ConfigUtils;
import me.commandrod.gungame.utils.ScoreboardUtils;
import me.commandrod.gungame.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        for (Player players : Bukkit.getOnlinePlayers()){
            players.kickPlayer(Utils.color("&c" + "אנא הכנסו מחדש לשרת."));
        }
        new ConfigUtils(this);
        getCommand("setspawn").setExecutor(new SetSpawn());
        getCommand("spawn").setExecutor(new Spawn());
        getCommand("setlevel").setExecutor(new SetLevel());
        this.saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(new Events(this), this);
        RegisterLevels.iWantToUnaliveThisTookSoLongAHGDAGSD();
        ScoreboardUtils.setupMilestones();
    }

    @Override
    public void onDisable(){
        this.saveConfig();
    }
}
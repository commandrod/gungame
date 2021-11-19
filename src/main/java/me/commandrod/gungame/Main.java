package me.commandrod.gungame;

import lombok.Getter;
import me.commandrod.gungame.commands.Profile;
import me.commandrod.gungame.commands.SetLevel;
import me.commandrod.gungame.commands.SetSpawn;
import me.commandrod.gungame.commands.Spawn;
import me.commandrod.gungame.levels.RegisterLevels;
import me.commandrod.gungame.listeners.Events;
import me.commandrod.gungame.scoreboard.GunGameSB;
import me.commandrod.gungame.utils.ConfigUtils;
import me.commandrod.gungame.utils.StatUtils;
import me.commandrod.gungame.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;

public final class Main extends JavaPlugin {

    @Getter
    public static Main instance;

    @Getter
    public static Connection connection;

    @Override
    public void onEnable() {
        instance = this;
        // This isn't done yet lmao
        //connection = DbConnection.connect();
        for (Player players : Bukkit.getOnlinePlayers()){
            players.kickPlayer(Utils.color("&c" + "Please rejoin the server."));
        }
        new ConfigUtils(this);
        new StatUtils(this);
        getCommand("setspawn").setExecutor(new SetSpawn());
        getCommand("spawn").setExecutor(new Spawn());
        getCommand("setlevel").setExecutor(new SetLevel());
        getCommand("profile").setExecutor(new Profile(this));
        this.saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(new Events(this), this);
        RegisterLevels.iWantToUnaliveThisTookSoLongAHGDAGSD();
        StatUtils.setupMilestones();
    }

    @Override
    public void onDisable(){
        this.saveConfig();
    }
}
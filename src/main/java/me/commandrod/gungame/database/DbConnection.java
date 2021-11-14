package me.commandrod.gungame.database;

import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    public static Connection connect(){
        Connection con = null;
        try{
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:./plugins/GunGame/database.db");
            Bukkit.getLogger().info("Connected to the Database!");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            () -> Bukkit.getLogger()
        }
        return con;
    }
}
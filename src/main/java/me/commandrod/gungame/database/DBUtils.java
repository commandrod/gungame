package me.commandrod.gungame.database;

import me.commandrod.gungame.Main;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class DBUtils {

    public static void insert(Player player, int kills, int deaths){
        insert(player.getUniqueId(), player.getName(), kills, deaths);
    }

    public static void insert(UUID uuid, String name, int kills, int deaths){
        Connection con = Main.getConnection();
        PreparedStatement ps;
        try {
            String sql = "INSERT INTO players(UUID, NAME, KILLS, DEATHS) VALUES(?,?,?,?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(uuid));
            ps.setString(2, name);
            ps.setInt(1, kills);
            ps.setInt(1, deaths);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}

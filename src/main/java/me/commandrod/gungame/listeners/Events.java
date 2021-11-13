package me.commandrod.gungame.listeners;

import me.commandrod.gungame.levels.LevelManager;
import me.commandrod.gungame.scoreboard.GunGameSB;
import me.commandrod.gungame.utils.ConfigUtils;
import me.commandrod.gungame.utils.ScoreboardUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Events implements Listener {

    private JavaPlugin plugin;
    public Events(JavaPlugin plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Location spawnLocation = ConfigUtils.getLocation("spawn");
        Player p = e.getPlayer();
        LevelManager.setLevel(p, 1);
        ScoreboardUtils.resetKills(p);
        GunGameSB.setScoreboard(p);
        p.teleport(spawnLocation);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e){
        if (!e.getEntity().getType().equals(EntityType.PLAYER)) return;
        Location spawnLocation = ConfigUtils.getLocation("spawn");
        Player p = (Player) e.getEntity();
        if (spawnLocation.getNearbyPlayers(8, 256, 8).contains(p)) e.setCancelled(true);
    }

    @EventHandler
    public void onKill(PlayerDeathEvent e){
        e.setShouldDropExperience(false);
        e.getDrops().clear();
        if (e.getEntity().getKiller() == null) return;
        Player killer = e.getEntity().getKiller();
        LevelManager.setLevel(killer, LevelManager.getLevel(LevelManager.getPlayerLevel(killer).getLevel() + 1).getLevel());
        ScoreboardUtils.addKill(killer);
        GunGameSB.setScoreboard(killer);
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e){
        Location spawnLocation = ConfigUtils.getLocation("spawn");
        Player p = e.getPlayer();
        if (LevelManager.getPlayerLevel(p).getLevel() >= 48){
            LevelManager.setLevel(p, LevelManager.getLevel(LevelManager.getPlayerLevel(p).getLevel() - 6).getLevel());
        } else {
            LevelManager.setLevel(p, LevelManager.getLevel(LevelManager.getPlayerLevel(p).getLevel() - 1).getLevel());
        }
        ScoreboardUtils.resetKills(p);
        GunGameSB.setScoreboard(p);
        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
            @Override
            public void run() {
                p.teleport(spawnLocation);
            }
        }, 1);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getWhoClicked() instanceof Player && e.getClickedInventory() != null) {
            Player p = (Player) e.getWhoClicked();
            if (!(p.getGameMode().equals(GameMode.SURVIVAL) || p.getGameMode().equals(GameMode.ADVENTURE))) return;
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e){
        Player p = e.getPlayer();
        if (p.getGameMode().equals(GameMode.SURVIVAL) || p.getGameMode().equals(GameMode.ADVENTURE)) e.setCancelled(true);
    }

    @EventHandler
    public void onSwap(PlayerSwapHandItemsEvent e){
        Player p = e.getPlayer();
        if (p.getGameMode().equals(GameMode.SURVIVAL) || p.getGameMode().equals(GameMode.ADVENTURE)) e.setCancelled(true);
    }
}
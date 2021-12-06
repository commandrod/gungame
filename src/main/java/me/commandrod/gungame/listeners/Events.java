package me.commandrod.gungame.listeners;

import me.commandrod.gungame.commands.Profile;
import me.commandrod.gungame.gui.Shop;
import me.commandrod.gungame.levels.LevelManager;
import me.commandrod.gungame.scoreboard.GunGameSB;
import me.commandrod.gungame.utils.ConfigUtils;
import me.commandrod.gungame.utils.StatUtils;
import me.commandrod.gungame.utils.SoundUtils;
import me.commandrod.gungame.utils.Utils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.*;

public class Events implements Listener {

    private JavaPlugin plugin;
    public Events(JavaPlugin plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e){
        e.setQuitMessage(Utils.color("&8[&c-&8] &c" + e.getPlayer().getName()));
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent e){
        if (!e.getResult().equals(PlayerLoginEvent.Result.KICK_WHITELIST)) return;
        e.setKickMessage(Utils.color("&cThe server is under maintenance!"));
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        if (!plugin.getConfig().isSet("settings." + p.getUniqueId() + ".name")) plugin.getConfig().set("settings." + p.getUniqueId() + ".name", p.getName());
        if (!plugin.getConfig().isSet("settings." + p.getUniqueId() + ".kills")) plugin.getConfig().set("settings." + p.getUniqueId() + ".kills", 0);
        if (!plugin.getConfig().isSet("settings." + p.getUniqueId() + ".deaths")) plugin.getConfig().set("settings." + p.getUniqueId() + ".deaths", 0);
        plugin.saveConfig();
        plugin.reloadConfig();
        e.setJoinMessage(Utils.color("&8[&a+&8] &a" + e.getPlayer().getName()));
        Location spawnLocation = ConfigUtils.getLocation("spawn");
        p.getInventory().clear();
        StatUtils.resetKills(p);
        LevelManager.setLevel(p, 1);
        p.teleport(spawnLocation);
        p.setHealth(20);
        p.setFoodLevel(20);
        p.setFireTicks(0);
        p.setGameMode(GameMode.SURVIVAL);
        p.setAllowFlight(false);
        p.setFlying(false);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e){
        final int spawnSize = 7;
        if (!e.getEntity().getType().equals(EntityType.PLAYER)) return;
        if (e.getCause().equals(EntityDamageEvent.DamageCause.FALL)) e.setCancelled(true);
        Location spawnLocation = ConfigUtils.getLocation("spawn");
        Player p = (Player) e.getEntity();
        if (spawnLocation.getWorld().getNearbyEntities(spawnLocation, spawnSize, 256, spawnSize).contains(p)) e.setCancelled(true);
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e){
        if (!e.getEntity().getType().equals(EntityType.PLAYER)) return;
        if (!e.getDamager().getType().equals(EntityType.PLAYER)) return;
        Location spawnLocation = ConfigUtils.getLocation("spawn");
        Player p = (Player) e.getEntity();
        Player damager = (Player) e.getDamager();
        Collection<Entity> nearbyPlayers = spawnLocation.getWorld().getNearbyEntities(spawnLocation, 8, 256, 8);
        if (nearbyPlayers.contains(p) || nearbyPlayers.contains(damager)) e.setCancelled(true);
    }

    @EventHandler
    public void onKill(PlayerDeathEvent e){
        e.setDroppedExp(0);
        e.getDrops().clear();
        Player p = e.getEntity();
        Player killer = p.getKiller();
        GunGameSB.updateScoreboard(p);
        plugin.getConfig().set("settings." + p.getUniqueId() + ".deaths", plugin.getConfig().getInt("settings." + p.getUniqueId() + ".deaths") + 1);
        plugin.saveConfig();
        if (killer == null){
            e.setDeathMessage(Utils.color("&b" + p.getName() + " &fdied."));
            plugin.reloadConfig();
            return;
        }
        e.setDeathMessage(Utils.color("&b" + p.getName() + " &fwas slain by &b" + killer.getName()));
        LevelManager.setLevel(killer, LevelManager.getLevel(LevelManager.getPlayerLevel(killer).getLevel() + 1).getLevel());
        StatUtils.addKill(killer);
        killer.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 0, false, false));
        if (LevelManager.getPlayerLevel(killer).getLevel() >= 48){
            killer.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 0, false, false));
        } else {
            if (!killer.isDead()) killer.setHealth(20);
        }
        plugin.getConfig().set("settings." + killer.getUniqueId() + ".kills", plugin.getConfig().getInt("settings." + killer.getUniqueId() + ".kills") + 1);
        plugin.saveConfig();
        plugin.reloadConfig();
        GunGameSB.updateScoreboard(p);
        GunGameSB.updateScoreboard(killer);
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e){
        Location spawnLocation = ConfigUtils.getLocation("spawn");
        Player p = e.getPlayer();
        StatUtils.resetKills(p);
        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
            @Override
            public void run() {
                p.teleport(spawnLocation);
            }
        }, 1);
        if (LevelManager.getPlayerLevel(p).getLevel() > 48 && p.getKiller() != null){
            LevelManager.setLevel(p, LevelManager.getLevel(LevelManager.getPlayerLevel(p).getLevel() - 6).getLevel());
            return;
        }
        LevelManager.setLevel(p, LevelManager.getLevel(LevelManager.getPlayerLevel(p).getLevel() - 1).getLevel());
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getClickedInventory() != null && e.getClickedInventory().equals(Profile.getProfileGUI()) || e.getClickedInventory().equals(Shop.getGUI())){
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
            if (e.getCurrentItem() == null) return;
            if (e.getCurrentItem().getType().equals(Material.GOLD_INGOT)) e.getWhoClicked().openInventory(Shop.getGUI());
            return;
        }
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

    private final List<UUID> launched = new ArrayList<>();

    private void launchPlayer(Player player){
        SoundUtils.playSound(player, Sound.FIREWORK_LAUNCH);
        player.setVelocity(new Vector(0, 1.5, 0));
        Bukkit.getScheduler().runTaskLater(plugin, () -> { player.setVelocity(player.getLocation().getDirection().add(new Vector(0, 1, 0))); }, 10);
        launched.add(player.getUniqueId());
        Bukkit.getScheduler().runTaskLater(plugin, () -> { launched.remove(player.getUniqueId()); }, 80);
    }

    private Block getBlockUnder(Player player){
        return player.getLocation().getBlock().getRelative(BlockFace.DOWN);
    }
    // Honey Blocks
    @EventHandler
    public void onMove(PlayerMoveEvent e){
        Player p = e.getPlayer();
        if (p.getGameMode().equals(GameMode.CREATIVE) || p.getGameMode().equals(GameMode.SPECTATOR)) return;
        if (getBlockUnder(p).getType().equals(Material.SPONGE)) {
            if (!launched.contains(p.getUniqueId())) launchPlayer(p);
        }
        if (p.getLocation().getBlock().getType().equals(Material.STATIONARY_WATER)) {
            if (!p.isDead()) p.setHealth(0);
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e){
        Player p = e.getPlayer();
        if (!p.getGameMode().equals(GameMode.CREATIVE) || !p.hasPermission("gungame.build")){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e){
        Player p = e.getPlayer();
        if (!p.getGameMode().equals(GameMode.CREATIVE) || !p.hasPermission("gungame.build")){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onLoseHunger(FoodLevelChangeEvent e){
        if (!e.getEntity().getType().equals(EntityType.PLAYER)) return;
        e.setCancelled(true);
    }
}
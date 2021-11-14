package me.commandrod.gungame.listeners;

import me.commandrod.gungame.levels.LevelManager;
import me.commandrod.gungame.utils.ConfigUtils;
import me.commandrod.gungame.utils.StatUtils;
import me.commandrod.gungame.utils.SoundUtils;
import me.commandrod.gungame.utils.Utils;
import org.bukkit.*;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
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
    public void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        if (!plugin.getConfig().isSet("settings." + p.getUniqueId() + ".name")) plugin.getConfig().set("settings." + p.getUniqueId() + ".name", p.getName());
        if (!plugin.getConfig().isSet("settings." + p.getUniqueId() + ".kills")) plugin.getConfig().set("settings." + p.getUniqueId() + ".kills", 0);
        if (!plugin.getConfig().isSet("settings." + p.getUniqueId() + ".deaths")) plugin.getConfig().set("settings." + p.getUniqueId() + ".deaths", 0);
        plugin.saveConfig();
        plugin.reloadConfig();
        e.setJoinMessage(Utils.color("&8[&a+&8] &a" + e.getPlayer().getName()));
        Location spawnLocation = ConfigUtils.getLocation("spawn");
        LevelManager.setLevel(p, 1);
        StatUtils.resetKills(p);
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
    public void onDamage(EntityDamageByEntityEvent e){
        if (!e.getEntity().getType().equals(EntityType.PLAYER)) return;
        if (!e.getDamager().getType().equals(EntityType.PLAYER)) return;
        Location spawnLocation = ConfigUtils.getLocation("spawn");
        Player p = (Player) e.getEntity();
        Player damager = (Player) e.getDamager();
        Collection<Player> nearbyPlayers = spawnLocation.getNearbyPlayers(8, 256, 8);
        if (nearbyPlayers.contains(p) || nearbyPlayers.contains(damager)) e.setCancelled(true);
    }

    @EventHandler
    public void onKill(PlayerDeathEvent e){
        e.setShouldDropExperience(false);
        e.getDrops().clear();
        Player p = e.getEntity();
        Player killer = p.getKiller();
        if (killer == null){
            e.setDeathMessage(Utils.color("&b" + p.getName() + " &3died."));
            return;
        }
        e.setDeathMessage(Utils.color("&b" + p.getName() + " &fwas slain by &b" + killer.getName()));
        LevelManager.setLevel(killer, LevelManager.getLevel(LevelManager.getPlayerLevel(killer).getLevel() + 1).getLevel());
        StatUtils.addKill(killer);
        plugin.getConfig().set("settings." + p.getUniqueId() + ".deaths", plugin.getConfig().getInt("settings." + p.getUniqueId() + ".deaths") + 1);
        plugin.getConfig().set("settings." + killer.getUniqueId() + ".killer", plugin.getConfig().getInt("settings." + killer.getUniqueId() + ".deaths") + 1);
        plugin.saveConfig();
        plugin.reloadConfig();
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e){
        Location spawnLocation = ConfigUtils.getLocation("spawn");
        Player p = e.getPlayer();
        if (LevelManager.getPlayerLevel(p).getLevel() > 48){
            LevelManager.setLevel(p, LevelManager.getLevel(LevelManager.getPlayerLevel(p).getLevel() - 6).getLevel());
        } else {
            LevelManager.setLevel(p, LevelManager.getLevel(LevelManager.getPlayerLevel(p).getLevel() - 1).getLevel());
        }
        StatUtils.resetKills(p);
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

    private final HashMap<UUID, ItemStack> chestplateMap = new HashMap<>();

    private void elytra(Player player){
        player.setVelocity(new Vector(0, 1.7, 0));
        SoundUtils.playSound(player, Sound.ENTITY_FIREWORK_ROCKET_LAUNCH);
        ItemStack chestplate = player.getInventory().getChestplate();
        ItemStack elytra = new ItemStack(Material.ELYTRA);
        ItemMeta im = elytra.getItemMeta();
        im.setUnbreakable(true);
        im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        elytra.setItemMeta(im);
        player.getInventory().setChestplate(elytra);
        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
            @Override
            public void run() {
                player.setGliding(true);
                if (!chestplateMap.containsKey(player.getUniqueId())) chestplateMap.put(player.getUniqueId(), chestplate);
            }
        }, 20);
    }

    private Material getBlockUnderType(Player player){
        return player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType();
    }
    // Honey Blocks
    @EventHandler
    public void onMove(PlayerMoveEvent e){
        Player p = e.getPlayer();
        if (!e.hasChangedBlock()) return;
        if (!getBlockUnderType(p).equals(Material.HONEY_BLOCK)) return;
        elytra(e.getPlayer());
    }

    @EventHandler
    public void onStopGliding(EntityToggleGlideEvent e){
        if (!e.getEntity().getType().equals(EntityType.PLAYER)) return;
        Player p = (Player) e.getEntity();
        if (!chestplateMap.containsKey(p.getUniqueId())) return;
        p.getInventory().setChestplate(chestplateMap.get(p.getUniqueId()));
        chestplateMap.remove(p.getUniqueId());
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
}
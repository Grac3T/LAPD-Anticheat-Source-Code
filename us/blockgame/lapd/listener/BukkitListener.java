// 
// Decompiled by Procyon v0.5.36
// 

package us.blockgame.lapd.listener;

import us.blockgame.lapd.check.dep.PositionDep;
import io.netty.channel.ChannelHandler;
import java.lang.reflect.Field;
import net.minecraft.server.v1_8_R3.NetworkManager;
import io.netty.channel.Channel;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.util.Vector;
import us.blockgame.lapd.util.Velocity;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.event.player.PlayerRegisterChannelEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.Location;
import us.blockgame.lapd.util.BlockUtil;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import us.blockgame.lapd.player.PlayerData;
import us.blockgame.lapd.packet.PacketHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.Plugin;
import us.blockgame.lapd.LAPD;
import org.bukkit.command.CommandSender;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import us.blockgame.lapd.events.LAPDBanPlayerEvent;
import org.bukkit.event.Listener;

public class BukkitListener implements Listener
{
    @EventHandler
    public void onLAPDBan(final LAPDBanPlayerEvent event) {
        final Player player = event.getPlayer();
        new BukkitRunnable() {
            public void run() {
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&8&m---------------------------------------"));
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&3LAPD &bremoved player &3" + player.getName() + "&b!"));
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&8&m---------------------------------------"));
                Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "ban " + player.getName() + " Unfair Advantage -s");
            }
        }.runTask((Plugin)LAPD.getInstance());
    }
    
    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        final PacketHandler packetHandler = new PacketHandler(player);
        final PlayerData user = LAPD.getInstance().getData(player);
        user.setBanned(false);
        user.setLongInTimePassed(System.currentTimeMillis());
        this.addPlayer(player, packetHandler);
        user.setLoginTime(System.currentTimeMillis());
    }
    
    @EventHandler
    public void onTeleport(final PlayerTeleportEvent event) {
        final Player player = event.getPlayer();
        final PlayerData user = LAPD.getInstance().getData(player);
        if (event.getCause().equals((Object)PlayerTeleportEvent.TeleportCause.UNKNOWN)) {
            user.setTeleportTicks(0);
            user.setTpuknown(true);
        }
    }
    
    @EventHandler
    public void onPlayerQuit(final PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        final PlayerData user = LAPD.getInstance().getData(player);
        user.setHasAlerts(false);
        user.setLunar(false);
        this.removePlayer(player);
        LAPD.getInstance().removeData(player);
    }
    
    @EventHandler
    public void onDamage(final EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player) || !(event.getEntity() instanceof Player)) {
            return;
        }
        final Player player = (Player)event.getEntity();
        final PlayerData playerData = LAPD.getInstance().getData(player);
        playerData.setLastHurt(System.currentTimeMillis());
        final Player attacker = (Player)event.getDamager();
        final PlayerData attackerData = LAPD.getInstance().getData(attacker);
        attackerData.setLastTarget((Entity)player);
        attackerData.setCancelled(event.isCancelled());
    }
    
    @EventHandler
    public void onPlayerMove(final PlayerMoveEvent event) {
        final Player player = event.getPlayer();
        final Location to = event.getTo();
        final Location from = event.getFrom();
        if (from.getX() == to.getX() && from.getY() == to.getY() && from.getZ() == to.getZ()) {
            return;
        }
        final PlayerData playerData = LAPD.getInstance().getData(player);
        playerData.setYDiff(from.getY() - to.getY());
        if (event.isCancelled()) {
            playerData.setLastMoveCancel(System.currentTimeMillis());
        }
        playerData.updatePositionFlags(player, to);
        playerData.getPositionDepList().forEach(check -> check.onPosition(player, to, from));
        playerData.setOnIce(BlockUtil.isOnIce(event.getTo(), 1) || BlockUtil.isOnIce(event.getTo(), 2));
        playerData.setOnStairs(BlockUtil.isOnStairs(event.getTo(), 0) || BlockUtil.isOnStairs(event.getTo(), 1));
        playerData.setUnderBlock(BlockUtil.isOnGround(event.getTo(), -2));
    }
    
    @EventHandler
    public void onBlockPlace(final BlockPlaceEvent event) {
        final Player player = event.getPlayer();
        final PlayerData playerData = LAPD.getInstance().getData(player);
        playerData.setLastBlockPlace(System.currentTimeMillis());
    }
    
    @EventHandler
    public void onDeathEvent(final PlayerDeathEvent event) {
        final Player player = event.getEntity();
        final PlayerData playerData = LAPD.getInstance().getData(player);
        playerData.setDeathTicks(20);
    }
    
    @EventHandler
    public void onRespawnEvent(final PlayerRespawnEvent event) {
        final PlayerData playerData = LAPD.getInstance().getData(event.getPlayer());
        playerData.setRespawnTicks(100);
        playerData.setDeathTicks(0);
    }
    
    @EventHandler
    public void onFlyToggle(final PlayerToggleFlightEvent event) {
        final PlayerData playerData = LAPD.getInstance().getData(event.getPlayer());
        playerData.setLastFlyTick(20);
    }
    
    @EventHandler
    public void onRegister(final PlayerRegisterChannelEvent event) {
        final Player player = event.getPlayer();
        final PlayerData playerData = LAPD.getInstance().getData(player);
        if (event.getChannel().equals("Lunar-Client") && playerData.isPassedMCBrand()) {
            playerData.setLunar(true);
        }
    }
    
    @EventHandler
    public void onVelocity(final PlayerVelocityEvent event) {
        final Player player = event.getPlayer();
        final Vector velocity = event.getVelocity();
        final double x = velocity.getX();
        final double y = velocity.getY();
        final double z = velocity.getZ();
        final double horizontal = Math.sqrt(x * x + z * z);
        final double vertical = Math.abs(y);
        final PlayerData playerData = LAPD.getInstance().getData(player);
        playerData.getVelocities().add(new Velocity(horizontal, vertical));
    }
    
    private void removePlayer(final Player player) {
        try {
            final NetworkManager networkManager = ((CraftPlayer)player).getHandle().playerConnection.networkManager;
            final Field channelField = networkManager.getClass().getDeclaredField("channel");
            channelField.setAccessible(true);
            final Channel channel = (Channel)channelField.get(networkManager);
            channelField.setAccessible(false);
            channel.eventLoop().submit(() -> channel.pipeline().remove("custom-handler-" + player.getUniqueId().toString()));
        }
        catch (NoSuchFieldException | SecurityException | IllegalAccessException ex2) {
            final Exception ex;
            final Exception e = ex;
            e.printStackTrace();
        }
    }
    
    private void addPlayer(final Player player, final PacketHandler packetHandler) {
        try {
            final NetworkManager networkManager = ((CraftPlayer)player).getHandle().playerConnection.networkManager;
            final Field channelField = networkManager.getClass().getDeclaredField("channel");
            channelField.setAccessible(true);
            final Channel channel = (Channel)channelField.get(networkManager);
            channelField.setAccessible(false);
            channel.eventLoop().submit(() -> channel.pipeline().addBefore("packet_handler", "custom-handler-" + player.getUniqueId().toString(), (ChannelHandler)packetHandler));
        }
        catch (NoSuchFieldException | SecurityException | IllegalAccessException ex2) {
            final Exception ex;
            final Exception e = ex;
            e.printStackTrace();
        }
    }
}

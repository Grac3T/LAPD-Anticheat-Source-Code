// 
// Decompiled by Procyon v0.5.36
// 

package us.blockgame.lapd;

import org.bukkit.entity.Player;
import us.blockgame.lapd.util.server.WebUtil;
import us.blockgame.lapd.util.Materials;
import org.bukkit.event.Listener;
import us.blockgame.lapd.listener.BukkitListener;
import us.blockgame.lapd.util.server.ServerUtil;
import org.bukkit.Bukkit;
import us.blockgame.lapd.command.AlertsCommand;
import org.bukkit.command.CommandExecutor;
import us.blockgame.lapd.command.ArrestCommand;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.plugin.Plugin;
import us.blockgame.lapd.listener.MessageListener;
import java.util.concurrent.ConcurrentHashMap;
import us.blockgame.lapd.player.PlayerData;
import java.util.UUID;
import java.util.Map;
import org.bukkit.plugin.java.JavaPlugin;

public class LAPD extends JavaPlugin
{
    private static LAPD instance;
    private final Map<UUID, PlayerData> playerData;
    public static boolean patroneMode;
    
    public LAPD() {
        this.playerData = new ConcurrentHashMap<UUID, PlayerData>();
    }
    
    public void onEnable() {
        LAPD.instance = this;
        this.getServer().getMessenger().registerIncomingPluginChannel((Plugin)this, "MC|Brand", (PluginMessageListener)new MessageListener());
        this.getServer().getMessenger().registerIncomingPluginChannel((Plugin)this, "Lunar-Client", (PluginMessageListener)new MessageListener());
        this.getCommand("arrest").setExecutor((CommandExecutor)new ArrestCommand(this));
        this.getCommand("alerts").setExecutor((CommandExecutor)new AlertsCommand(this));
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask((Plugin)this, (Runnable)new ServerUtil(), 100L, 1L);
        Bukkit.getPluginManager().registerEvents((Listener)new BukkitListener(), (Plugin)this);
        new Materials();
        if (!WebUtil.isAuthed()) {
            this.getServer().getPluginManager().disablePlugin((Plugin)this);
            return;
        }
        System.out.println("[LAPD] Authentication passed.");
        if (LAPD.patroneMode) {
            System.out.println("[LAPD] Patrone Mode.");
        }
    }
    
    public PlayerData getData(final Player player) {
        return this.playerData.computeIfAbsent(player.getUniqueId(), uuid -> new PlayerData());
    }
    
    public void removeData(final Player player) {
        this.playerData.remove(player.getUniqueId());
    }
    
    public void onDisable() {
        LAPD.instance = null;
        this.playerData.clear();
    }
    
    public static LAPD getInstance() {
        return LAPD.instance;
    }
    
    static {
        LAPD.patroneMode = false;
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package us.blockgame.lapd.command;

import us.blockgame.lapd.player.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import us.blockgame.lapd.LAPD;
import org.bukkit.command.CommandExecutor;

public class AlertsCommand implements CommandExecutor
{
    private LAPD plugin;
    
    public AlertsCommand(final LAPD plugin) {
        this.plugin = plugin;
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String commandLabel, final String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        if (!sender.hasPermission("lapd.command.alerts")) {
            sender.sendMessage(ChatColor.RED + "No permission.");
            return true;
        }
        final Player player = (Player)sender;
        final PlayerData playerData = this.plugin.getData(player);
        if (playerData.isAlerts()) {
            playerData.setAlerts(false);
            sender.sendMessage(ChatColor.RED + "LAPD Alerts have been disabled.");
            return true;
        }
        playerData.setAlerts(true);
        sender.sendMessage(ChatColor.GREEN + "LAPD Alerts have been enabled.");
        return true;
    }
}

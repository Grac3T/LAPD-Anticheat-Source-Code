// 
// Decompiled by Procyon v0.5.36
// 

package us.blockgame.lapd.command;

import org.bukkit.event.Event;
import us.blockgame.lapd.events.LAPDBanPlayerEvent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import us.blockgame.lapd.LAPD;
import org.bukkit.command.CommandExecutor;

public class ArrestCommand implements CommandExecutor
{
    private LAPD plugin;
    
    public ArrestCommand(final LAPD plugin) {
        this.plugin = plugin;
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String commandLabel, final String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        if (!sender.hasPermission("lapd.command.arrest")) {
            sender.sendMessage(ChatColor.RED + "No permission.");
            return true;
        }
        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Usage: /" + commandLabel + " <player>");
            return true;
        }
        final Player target = this.plugin.getServer().getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(ChatColor.RED + "Could not find player!");
            return true;
        }
        final LAPDBanPlayerEvent lapdBanPlayerEvent = new LAPDBanPlayerEvent(target);
        this.plugin.getServer().getPluginManager().callEvent((Event)lapdBanPlayerEvent);
        return true;
    }
}

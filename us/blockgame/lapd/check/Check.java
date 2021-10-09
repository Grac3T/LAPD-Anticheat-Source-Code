// 
// Decompiled by Procyon v0.5.36
// 

package us.blockgame.lapd.check;

import org.bukkit.event.Event;
import us.blockgame.lapd.events.LAPDBanPlayerEvent;
import java.util.Iterator;
import us.blockgame.lapd.util.server.ServerUtil;
import us.blockgame.lapd.util.entity.PlayerUtil;
import org.bukkit.Bukkit;
import us.blockgame.lapd.LAPD;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import us.blockgame.lapd.player.PlayerData;

public abstract class Check
{
    protected final PlayerData playerData;
    private String format;
    private double vl;
    
    public Check(final PlayerData playerData) {
        this.playerData = playerData;
        this.format = ChatColor.translateAlternateColorCodes('&', "&b%player% &3failed &b%check% &8(&3VL.&b%vl%&8) &8(&3Ping: &b%ping%&8) (&3TPS: &b%tps%&8)");
    }
    
    public void alert(final Player player, final String check, final boolean bannable, final int banVL) {
        if (LAPD.patroneMode && player.hasPermission("lapd.super")) {
            return;
        }
        this.setVl(this.getVl() + 1.0);
        if (this.getVl() <= 0.0) {
            return;
        }
        for (final Player staff : Bukkit.getServer().getOnlinePlayers()) {
            final PlayerData playerData = LAPD.getInstance().getData(staff);
            if (staff.hasPermission("lapd.alerts") && playerData.isAlerts()) {
                staff.sendMessage(this.format.replace("%player%", player.getName()).replace("%check%", check).replace("%vl%", this.getVl() + "/" + banVL).replace("%ping%", String.valueOf(PlayerUtil.getPing(player))).replace("%tps%", ServerUtil.getFixedTPS()));
            }
            if (this.getVl() >= banVL) {
                if (bannable) {
                    this.ban(player);
                }
                this.setVl(0.0);
            }
        }
    }
    
    public void alert(final Player player, final String check, final boolean bannable, final long banVL) {
        this.setVl(this.getVl() + 1.0);
        if (this.getVl() <= 0.0) {
            return;
        }
        for (final Player staff : Bukkit.getServer().getOnlinePlayers()) {
            final PlayerData playerData = LAPD.getInstance().getData(staff);
            if (staff.hasPermission("lapd.alerts") && playerData.isAlerts()) {
                staff.sendMessage(this.format.replace("%player%", player.getName()).replace("%check%", check).replace("%vl%", this.getVl() + "/" + banVL).replace("%ping%", String.valueOf(PlayerUtil.getPing(player))).replace("%tps%", ServerUtil.getFixedTPS()));
            }
            if (this.getVl() >= banVL) {
                if (bannable) {
                    this.ban(player);
                }
                this.setVl(0.0);
            }
        }
    }
    
    public void ban(final Player player) {
        if (player.hasPermission("lapd.bypass") || this.playerData.isBanned()) {
            return;
        }
        this.playerData.setBanned(true);
        final LAPDBanPlayerEvent lapdBanPlayerEvent = new LAPDBanPlayerEvent(player);
        Bukkit.getPluginManager().callEvent((Event)lapdBanPlayerEvent);
    }
    
    public double getVl() {
        return this.vl;
    }
    
    public void setVl(final double vl) {
        this.vl = vl;
    }
}

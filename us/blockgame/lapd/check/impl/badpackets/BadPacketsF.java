// 
// Decompiled by Procyon v0.5.36
// 

package us.blockgame.lapd.check.impl.badpackets;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import us.blockgame.lapd.player.PlayerData;
import us.blockgame.lapd.check.dep.PositionDep;
import us.blockgame.lapd.check.Check;

public class BadPacketsF extends Check implements PositionDep
{
    public BadPacketsF(final PlayerData playerData) {
        super(playerData);
    }
    
    @Override
    public void onPosition(final Player player, final Location to, final Location from) {
        if (to.getX() == Double.MAX_VALUE || to.getX() == Double.MIN_VALUE || to.getZ() == Double.MAX_VALUE || to.getZ() == Double.MIN_VALUE || from.getX() == Double.MAX_VALUE || from.getX() == Double.MIN_VALUE || from.getZ() == Double.MIN_VALUE || from.getZ() == Double.MAX_VALUE) {
            this.alert(player, "BadPackets F", true, 1);
        }
    }
}

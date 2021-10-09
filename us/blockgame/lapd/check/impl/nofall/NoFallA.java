// 
// Decompiled by Procyon v0.5.36
// 

package us.blockgame.lapd.check.impl.nofall;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import us.blockgame.lapd.player.PlayerData;
import us.blockgame.lapd.check.dep.PositionDep;
import us.blockgame.lapd.check.Check;

public class NoFallA extends Check implements PositionDep
{
    private double vl;
    
    public NoFallA(final PlayerData playerData) {
        super(playerData);
    }
    
    @Override
    public void onPosition(final Player player, final Location to, final Location from) {
        if (!this.playerData.isLagging() && player.getNoDamageTicks() == 0 && player.getVehicle() == null && !player.isDead() && !player.getGameMode().equals((Object)GameMode.CREATIVE)) {
            final double yDiff = from.getY() - to.getY();
            if (player.isOnGround() && yDiff > 0.8) {
                final double vl = this.vl + 1.0;
                this.vl = vl;
                if (vl > 4.0) {
                    this.alert(player, "NoFall", true, 5);
                }
            }
        }
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package us.blockgame.lapd.check.velocity;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import us.blockgame.lapd.player.PlayerData;
import us.blockgame.lapd.check.dep.PositionDep;
import us.blockgame.lapd.check.Check;

public class VelocityB extends Check implements PositionDep
{
    private double vl;
    
    public VelocityB(final PlayerData playerData) {
        super(playerData);
    }
    
    @Override
    public void onPosition(final Player player, final Location to, final Location from) {
        final double offsetY = to.getY() - from.getY();
        if (this.playerData.getVelocityY() > 0.0 && this.playerData.isOnGround() && from.getY() % 1.0 == 0.0 && !this.playerData.isUnderBlock() && !this.playerData.isInLiquid() && offsetY > 0.0 && offsetY < 0.41999998688697815) {
            final double ratioY = offsetY / this.playerData.getVelocityY();
            if (ratioY < 0.99) {
                final int percent = (int)Math.round(ratioY * 100.0);
                ++this.vl;
                if (this.vl >= 5.0) {
                    this.alert(player, "Velocity B (" + percent + "%)", false, 15);
                }
                if (this.vl >= 15.0) {
                    this.vl = 0.0;
                }
            }
            else {
                --this.vl;
            }
        }
    }
}

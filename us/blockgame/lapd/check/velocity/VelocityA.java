// 
// Decompiled by Procyon v0.5.36
// 

package us.blockgame.lapd.check.velocity;

import us.blockgame.lapd.util.math.MathUtil;
import us.blockgame.lapd.util.entity.PlayerUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import us.blockgame.lapd.player.PlayerData;
import us.blockgame.lapd.check.dep.PositionDep;
import us.blockgame.lapd.check.Check;

public class VelocityA extends Check implements PositionDep
{
    private double vl;
    
    public VelocityA(final PlayerData playerData) {
        super(playerData);
    }
    
    @Override
    public void onPosition(final Player player, final Location to, final Location from) {
        if (this.playerData.getVelocityY() > 0.0 && !this.playerData.isUnderBlock() && !this.playerData.isInLiquid()) {
            final int threshold = 12 + MathUtil.pingFormula(PlayerUtil.getPing(player)) * 2;
            ++this.vl;
            if (this.vl >= threshold) {
                this.alert(player, "Velocity A", false, Math.max(PlayerUtil.getPing(player) / 10L, 15L));
                this.playerData.setVelocityY(0.0);
                this.vl = 0.0;
            }
        }
        else {
            this.vl = 0.0;
        }
    }
}

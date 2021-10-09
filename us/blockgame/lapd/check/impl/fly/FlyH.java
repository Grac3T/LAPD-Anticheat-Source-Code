// 
// Decompiled by Procyon v0.5.36
// 

package us.blockgame.lapd.check.impl.fly;

import org.bukkit.Material;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import us.blockgame.lapd.player.PlayerData;
import us.blockgame.lapd.check.dep.PositionDep;
import us.blockgame.lapd.check.Check;

public class FlyH extends Check implements PositionDep
{
    private int vl;
    
    public FlyH(final PlayerData playerData) {
        super(playerData);
    }
    
    @Override
    public void onPosition(final Player player, final Location to, final Location from) {
        if (from.getY() < to.getY() && to.getY() - from.getY() == 0.41999998688697815) {
            this.playerData.setLastJump(System.currentTimeMillis());
        }
        if (player.getLocation().getBlock().isLiquid() || player.getAllowFlight() || player.isSprinting() || to.getY() <= from.getY() || System.currentTimeMillis() - this.playerData.getLastVelocity() <= 2000L || System.currentTimeMillis() - this.playerData.getLastJump() <= 3000L || player.getLocation().getBlock().getType() == Material.LADDER || player.getLocation().getBlock().getType() == Material.VINE || player.getFallDistance() != 0.0f) {
            return;
        }
        ++this.vl;
        if (this.vl > 5) {}
    }
}

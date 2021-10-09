// 
// Decompiled by Procyon v0.5.36
// 

package us.blockgame.lapd.check.impl.aim;

import us.blockgame.lapd.util.math.MathUtil;
import us.blockgame.lapd.position.CustomRotation;
import org.bukkit.entity.Player;
import us.blockgame.lapd.player.PlayerData;
import us.blockgame.lapd.check.dep.RotationDep;
import us.blockgame.lapd.check.Check;

public class AimB extends Check implements RotationDep
{
    private float suspiciousYaw;
    
    public AimB(final PlayerData playerData) {
        super(playerData);
    }
    
    @Override
    public void onRotation(final Player player, final CustomRotation to, final CustomRotation from) {
        if (System.currentTimeMillis() - this.playerData.getLastAttack() > 10000L) {
            return;
        }
        final double diffYaw = MathUtil.getDistanceBetweenAngles(to.getYaw(), from.getYaw());
        if (diffYaw > 1.0 && Math.round(diffYaw) == diffYaw && diffYaw % 1.5 != 0.0) {
            if (diffYaw == this.suspiciousYaw) {
                this.alert(player, "Aim B", true, 20);
            }
            this.suspiciousYaw = (float)Math.round(diffYaw);
        }
        else {
            this.suspiciousYaw = 0.0f;
        }
    }
}

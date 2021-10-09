// 
// Decompiled by Procyon v0.5.36
// 

package us.blockgame.lapd.check.impl.scaffold;

import us.blockgame.lapd.util.math.MathUtil;
import us.blockgame.lapd.position.CustomRotation;
import org.bukkit.entity.Player;
import us.blockgame.lapd.player.PlayerData;
import us.blockgame.lapd.check.dep.RotationDep;
import us.blockgame.lapd.check.Check;

public class ScaffoldA extends Check implements RotationDep
{
    private double multiplier;
    private float previous;
    private double vl;
    private double streak;
    
    public ScaffoldA(final PlayerData playerData) {
        super(playerData);
        this.multiplier = Math.pow(2.0, 24.0);
    }
    
    @Override
    public void onRotation(final Player player, final CustomRotation to, final CustomRotation from) {
        if (System.currentTimeMillis() - this.playerData.getLastPlace() > 1000L) {
            this.vl = 0.0;
            return;
        }
        final float pitchChange = MathUtil.getDistanceBetweenAngles(to.getPitch(), from.getPitch());
        final long a = (long)(pitchChange * this.multiplier);
        final long b = (long)(this.previous * this.multiplier);
        final long gcd = this.gcd(a, b);
        final float magicVal = pitchChange * 100.0f / this.previous;
        if (magicVal > 24.0f) {
            this.vl = Math.max(0.0, this.vl - 1.0);
        }
        if (pitchChange >= 0.05 && pitchChange <= 20.0f && gcd < 131072L) {
            final double vl = this.vl + 1.0;
            this.vl = vl;
            if (vl > 1.0) {
                ++this.streak;
            }
            if (this.streak > 6.0) {}
        }
        else {
            this.vl = Math.max(0.0, this.vl - 1.0);
            this.streak = Math.max(0.0, this.streak - 0.25);
        }
        this.previous = pitchChange;
    }
    
    private long gcd(final long a, final long b) {
        if (b <= 16384L) {
            return a;
        }
        return this.gcd(b, a % b);
    }
}

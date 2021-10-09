// 
// Decompiled by Procyon v0.5.36
// 

package us.blockgame.lapd.check.impl.magic;

import org.bukkit.GameMode;
import us.blockgame.lapd.util.entity.PlayerUtil;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;
import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.entity.Player;
import us.blockgame.lapd.player.PlayerData;
import us.blockgame.lapd.util.entity.MovingStats;
import us.blockgame.lapd.check.dep.PacketDep;
import us.blockgame.lapd.check.Check;

public class MagicG extends Check implements PacketDep
{
    private double vl;
    private double streak;
    private double balance;
    private long lastFlying;
    private long lastTime;
    private MovingStats movingStats;
    
    public MagicG(final PlayerData playerData) {
        super(playerData);
        this.movingStats = new MovingStats(20);
    }
    
    @Override
    public boolean onPacketReceive(final Player player, final Packet packet) {
        if (packet instanceof PacketPlayInFlying && !this.playerData.isLagging() && this.playerData.getRespawnTicks() == 0 && this.playerData.getDeathTicks() == 0 && System.currentTimeMillis() - this.playerData.getLongInTimePassed() > 5000L && this.playerData.getPosition() != null && PlayerUtil.getPing(player) > 0 && !player.isFlying() && player.getGameMode() == GameMode.SURVIVAL) {
            final long now = System.currentTimeMillis();
            final long time = System.currentTimeMillis();
            final long lastTime = (this.lastTime != 0L) ? this.lastTime : (time - 50L);
            this.lastTime = time;
            final long rate = time - lastTime;
            this.balance += 50.0;
            this.balance -= rate;
            this.movingStats.add((double)(now - this.lastFlying));
            final double max = 7.07;
            final double stdDev = this.movingStats.getStdDev(max);
            if (this.balance <= -500.0) {
                this.balance = -500.0;
            }
            else if (this.balance >= 200.0) {
                this.balance = 200.0;
            }
            if (stdDev != Double.NaN && stdDev < max && this.balance > 10.0) {
                final double vl = this.vl + 1.0;
                this.vl = vl;
                if (vl > 1.0) {
                    ++this.streak;
                }
                if (this.streak > 15.0) {
                    this.alert(player, "Magic *G", false, 10);
                    this.streak = 0.0;
                }
            }
            else {
                this.vl = Math.max(0.0, this.vl - 1.0);
                this.streak = Math.max(0.0, this.streak - 0.125);
            }
            this.lastFlying = now;
        }
        return true;
    }
}

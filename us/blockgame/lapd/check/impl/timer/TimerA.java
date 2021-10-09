// 
// Decompiled by Procyon v0.5.36
// 

package us.blockgame.lapd.check.impl.timer;

import java.util.Iterator;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;
import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.entity.Player;
import java.util.LinkedList;
import us.blockgame.lapd.player.PlayerData;
import java.util.Deque;
import us.blockgame.lapd.check.dep.PacketDep;
import us.blockgame.lapd.check.Check;

public class TimerA extends Check implements PacketDep
{
    private double vl;
    private Deque<Long> delays;
    private long lastPacketTime;
    
    public TimerA(final PlayerData playerData) {
        super(playerData);
        this.delays = new LinkedList<Long>();
    }
    
    @Override
    public boolean onPacketReceive(final Player player, final Packet packet) {
        if (packet instanceof PacketPlayInFlying && !this.playerData.isTpuknown() && System.currentTimeMillis() - this.playerData.getLastDelayedMovePacket() > 110L) {
            this.delays.add(System.currentTimeMillis() - this.lastPacketTime);
            if (this.delays.size() == 40) {
                double average = 0.0;
                for (final long l : this.delays) {
                    average += l;
                }
                average /= this.delays.size();
                if (average <= 49.0) {
                    final double vl = this.vl + 1.25;
                    this.vl = vl;
                    if (vl > 4.0 && average >= 35.714285714285715) {
                        this.alert(player, "Timer A", true, 20);
                    }
                }
                else {
                    this.vl -= 0.5;
                }
                this.delays.clear();
            }
            this.lastPacketTime = System.currentTimeMillis();
        }
        return true;
    }
}

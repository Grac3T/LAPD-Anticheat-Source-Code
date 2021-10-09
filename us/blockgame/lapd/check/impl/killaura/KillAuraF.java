// 
// Decompiled by Procyon v0.5.36
// 

package us.blockgame.lapd.check.impl.killaura;

import java.util.Iterator;
import net.minecraft.server.v1_8_R3.PacketPlayInArmAnimation;
import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.entity.Player;
import java.util.LinkedList;
import us.blockgame.lapd.player.PlayerData;
import java.util.Deque;
import us.blockgame.lapd.check.dep.PacketDep;
import us.blockgame.lapd.check.Check;

public class KillAuraF extends Check implements PacketDep
{
    private Deque<Long> delays;
    private int vl;
    
    public KillAuraF(final PlayerData playerData) {
        super(playerData);
        this.delays = new LinkedList<Long>();
    }
    
    @Override
    public boolean onPacketReceive(final Player player, final Packet packet) {
        if (packet instanceof PacketPlayInArmAnimation && System.currentTimeMillis() - this.playerData.getLastDelayedMovePacket() > 110L && !this.playerData.isTpuknown()) {
            final long delay = System.currentTimeMillis() - this.playerData.getLastMovePacket();
            this.delays.add(delay);
            if (this.delays.size() == 40) {
                double average = 0.0;
                for (final long loopDelay : this.delays) {
                    average += loopDelay;
                }
                average /= this.delays.size();
                if (average <= 30.0) {
                    ++this.vl;
                    if (this.vl >= 5) {
                        this.alert(player, "KillAura F", true, 20);
                    }
                }
                else {
                    this.setVl(this.getVl() - 1.25);
                }
                this.delays.clear();
            }
        }
        return true;
    }
}

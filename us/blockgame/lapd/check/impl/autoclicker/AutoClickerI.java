// 
// Decompiled by Procyon v0.5.36
// 

package us.blockgame.lapd.check.impl.autoclicker;

import java.util.Iterator;
import net.minecraft.server.v1_8_R3.PacketPlayInArmAnimation;
import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import us.blockgame.lapd.player.PlayerData;
import java.util.List;
import us.blockgame.lapd.check.dep.PacketDep;
import us.blockgame.lapd.check.Check;

public class AutoClickerI extends Check implements PacketDep
{
    private long lastTime;
    private List<Long> lastDifferences;
    private int vl;
    private int lastVl;
    private long lastSpike;
    
    public AutoClickerI(final PlayerData playerData) {
        super(playerData);
        this.lastDifferences = new ArrayList<Long>();
    }
    
    @Override
    public boolean onPacketReceive(final Player player, final Packet packet) {
        if (packet instanceof PacketPlayInArmAnimation && !this.playerData.isDigging() && !this.playerData.isPlaced() && !this.playerData.isLagging() && !this.playerData.isInstantBreakDigging()) {
            final long difference = System.currentTimeMillis() - this.lastTime;
            this.lastDifferences.add(difference);
            if (this.lastDifferences.size() >= 10) {
                for (int i = 0; i < this.lastDifferences.size(); ++i) {
                    final long comparable = this.lastDifferences.get(i);
                    for (final long l : this.lastDifferences) {
                        if (Math.abs(comparable - l) <= 5L) {
                            ++this.vl;
                        }
                    }
                }
                if (Math.abs(this.lastVl - (this.vl - 10)) >= 20) {
                    if (System.currentTimeMillis() - this.lastSpike <= 2000L || System.currentTimeMillis() - this.lastSpike <= 2200L) {}
                    this.lastSpike = System.currentTimeMillis();
                }
                this.lastVl = this.vl - 10;
                this.vl = 0;
                this.lastDifferences.clear();
            }
            this.lastTime = System.currentTimeMillis();
        }
        return true;
    }
}

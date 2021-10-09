// 
// Decompiled by Procyon v0.5.36
// 

package us.blockgame.lapd.check.impl.autoclicker;

import java.util.Iterator;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;
import net.minecraft.server.v1_8_R3.PacketPlayInBlockDig;
import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.entity.Player;
import java.util.LinkedList;
import us.blockgame.lapd.player.PlayerData;
import net.minecraft.server.v1_8_R3.BlockPosition;
import java.util.Deque;
import us.blockgame.lapd.check.dep.PacketDep;
import us.blockgame.lapd.check.Check;

public class AutoClickerD extends Check implements PacketDep
{
    private final Deque<Integer> recentCounts;
    private BlockPosition lastBlock;
    private int flyingCount;
    private double vl;
    
    public AutoClickerD(final PlayerData playerData) {
        super(playerData);
        this.recentCounts = new LinkedList<Integer>();
    }
    
    @Override
    public boolean onPacketReceive(final Player player, final Packet packet) {
        if (packet instanceof PacketPlayInBlockDig) {
            final PacketPlayInBlockDig blockDig = (PacketPlayInBlockDig)packet;
            if (blockDig.c() == PacketPlayInBlockDig.EnumPlayerDigType.START_DESTROY_BLOCK) {
                if (this.lastBlock != null && this.lastBlock.equals((Object)blockDig.a())) {
                    this.recentCounts.addLast(this.flyingCount);
                    if (this.recentCounts.size() == 20) {
                        double average = 0.0;
                        for (final int i : this.recentCounts) {
                            average += i;
                        }
                        average /= this.recentCounts.size();
                        double stdDev = 0.0;
                        for (final int j : this.recentCounts) {
                            stdDev += Math.pow(j - average, 2.0);
                        }
                        stdDev /= this.recentCounts.size();
                        stdDev = Math.sqrt(stdDev);
                        Label_0281: {
                            if (stdDev < 0.45) {
                                final double vl = this.vl + 1.0;
                                this.vl = vl;
                                if (vl >= 3.0) {
                                    if (this.vl >= 6.0) {
                                        this.alert(player, "AutoClicker D", true, 1);
                                    }
                                    break Label_0281;
                                }
                            }
                            this.vl -= 0.5;
                        }
                        this.recentCounts.clear();
                    }
                    this.flyingCount = 0;
                }
                else if (blockDig.c() == PacketPlayInBlockDig.EnumPlayerDigType.ABORT_DESTROY_BLOCK) {
                    this.lastBlock = blockDig.a();
                }
            }
            else if (packet instanceof PacketPlayInFlying) {
                ++this.flyingCount;
            }
        }
        return true;
    }
}

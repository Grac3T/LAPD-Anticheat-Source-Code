// 
// Decompiled by Procyon v0.5.36
// 

package us.blockgame.lapd.check.impl.autoclicker;

import net.minecraft.server.v1_8_R3.PacketPlayInBlockDig;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;
import net.minecraft.server.v1_8_R3.PacketPlayInArmAnimation;
import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.entity.Player;
import us.blockgame.lapd.player.PlayerData;
import us.blockgame.lapd.check.dep.PacketDep;
import us.blockgame.lapd.check.Check;

public class AutoClickerC extends Check implements PacketDep
{
    private int clicks;
    private int outliers;
    private int flyingCount;
    private boolean release;
    private double vl;
    
    public AutoClickerC(final PlayerData playerData) {
        super(playerData);
    }
    
    @Override
    public boolean onPacketReceive(final Player player, final Packet packet) {
        if (packet instanceof PacketPlayInArmAnimation && !this.playerData.isDigging() && !this.playerData.isPlaced() && System.currentTimeMillis() - this.playerData.getLastDelayedMovePacket() > 220L && this.playerData.getLastMovePacket() != 0L && System.currentTimeMillis() - this.playerData.getLastMovePacket() < 110L && !this.playerData.isFakeDigging()) {
            if (this.flyingCount < 10) {
                if (this.release) {
                    this.release = false;
                    this.flyingCount = 0;
                    return true;
                }
                if (this.flyingCount > 3) {
                    ++this.outliers;
                }
                else if (this.flyingCount == 0) {
                    return true;
                }
                if (++this.clicks == 1000) {
                    if (this.outliers <= 7) {
                        final double vl = this.vl + 1.4;
                        this.vl = vl;
                        if (vl >= 4.0) {
                            this.alert(player, "AutoClicker C (Experimental)", false, 10);
                        }
                    }
                    else {
                        this.vl -= 0.8;
                    }
                    final boolean b = false;
                    this.outliers = 0;
                    this.clicks = 0;
                }
            }
            this.flyingCount = 0;
        }
        else if (packet instanceof PacketPlayInFlying) {
            ++this.flyingCount;
        }
        else if (packet instanceof PacketPlayInBlockDig && ((PacketPlayInBlockDig)packet).c() == PacketPlayInBlockDig.EnumPlayerDigType.RELEASE_USE_ITEM) {
            this.release = true;
        }
        return true;
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package us.blockgame.lapd.check.impl.killaura;

import net.minecraft.server.v1_8_R3.PacketPlayInFlying;
import net.minecraft.server.v1_8_R3.PacketPlayInArmAnimation;
import net.minecraft.server.v1_8_R3.PacketPlayInBlockDig;
import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.entity.Player;
import us.blockgame.lapd.player.PlayerData;
import us.blockgame.lapd.check.dep.PacketDep;
import us.blockgame.lapd.check.Check;

public class KillAuraD extends Check implements PacketDep
{
    private int vl;
    private boolean sent;
    private boolean failed;
    private int movements;
    
    public KillAuraD(final PlayerData playerData) {
        super(playerData);
    }
    
    @Override
    public boolean onPacketReceive(final Player player, final Packet packet) {
        if (this.playerData.isDigging() && System.currentTimeMillis() - this.playerData.getLastDelayedMovePacket() > 110L && System.currentTimeMillis() - this.playerData.getLastMovePacket() < 110L) {
            if (packet instanceof PacketPlayInBlockDig && ((PacketPlayInBlockDig)packet).c() == PacketPlayInBlockDig.EnumPlayerDigType.START_DESTROY_BLOCK) {
                this.movements = 0;
                this.vl = 0;
            }
            else if (packet instanceof PacketPlayInArmAnimation && this.movements >= 2) {
                if (this.sent) {
                    if (!this.failed) {
                        if (++this.vl >= 5) {}
                        this.failed = true;
                    }
                }
                else {
                    this.sent = true;
                }
            }
            else if (packet instanceof PacketPlayInFlying) {
                final boolean b = false;
                this.failed = b;
                this.sent = b;
                ++this.movements;
            }
        }
        return true;
    }
}

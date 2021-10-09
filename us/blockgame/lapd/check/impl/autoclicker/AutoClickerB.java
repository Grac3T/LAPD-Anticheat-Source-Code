// 
// Decompiled by Procyon v0.5.36
// 

package us.blockgame.lapd.check.impl.autoclicker;

import net.minecraft.server.v1_8_R3.PacketPlayInFlying;
import net.minecraft.server.v1_8_R3.PacketPlayInArmAnimation;
import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.entity.Player;
import us.blockgame.lapd.player.PlayerData;
import us.blockgame.lapd.check.dep.PacketDep;
import us.blockgame.lapd.check.Check;

public class AutoClickerB extends Check implements PacketDep
{
    private int swings;
    private int movements;
    
    public AutoClickerB(final PlayerData playerData) {
        super(playerData);
    }
    
    @Override
    public boolean onPacketReceive(final Player player, final Packet packet) {
        if (packet instanceof PacketPlayInArmAnimation && !this.playerData.isDigging() && !this.playerData.isPlaced() && !this.playerData.isFakeDigging() && System.currentTimeMillis() - this.playerData.getLastDelayedMovePacket() > 220L && System.currentTimeMillis() - this.playerData.getLastMovePacket() < 110L) {
            ++this.swings;
        }
        else if (packet instanceof PacketPlayInFlying && ++this.movements == 20) {
            if (this.swings > 20) {
                this.alert(player, "AutoClicker B", true, 3);
            }
            this.playerData.setLastCps(this.swings);
            final int n = 0;
            this.swings = n;
            this.movements = n;
        }
        return true;
    }
}

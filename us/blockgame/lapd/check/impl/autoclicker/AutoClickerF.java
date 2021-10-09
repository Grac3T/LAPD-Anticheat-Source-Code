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

public class AutoClickerF extends Check implements PacketDep
{
    private int swings;
    private int movements;
    private long lastSwing;
    
    public AutoClickerF(final PlayerData playerData) {
        super(playerData);
    }
    
    @Override
    public boolean onPacketReceive(final Player player, final Packet packet) {
        if (packet instanceof PacketPlayInArmAnimation && System.currentTimeMillis() - this.playerData.getLastDelayedMovePacket() > 110L && System.currentTimeMillis() - this.playerData.getLastMovePacket() < 110L && !this.playerData.isDigging() && !this.playerData.isPlaced()) {
            ++this.swings;
            this.lastSwing = System.currentTimeMillis();
        }
        else if (packet instanceof PacketPlayInFlying && this.swings > 0) {
            ++this.movements;
            if (this.movements == 20 && this.swings > 20) {
                this.alert(player, "AutoClicker F", true, 20);
            }
            if (System.currentTimeMillis() - this.lastSwing <= 350L) {}
            final boolean b = false;
            this.movements = (b ? 1 : 0);
            this.swings = (b ? 1 : 0);
        }
        return true;
    }
}

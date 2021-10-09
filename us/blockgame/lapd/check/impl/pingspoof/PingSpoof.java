// 
// Decompiled by Procyon v0.5.36
// 

package us.blockgame.lapd.check.impl.pingspoof;

import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.entity.Player;
import us.blockgame.lapd.player.PlayerData;
import us.blockgame.lapd.check.dep.PacketDep;
import us.blockgame.lapd.check.Check;

public class PingSpoof extends Check implements PacketDep
{
    private int vl;
    
    public PingSpoof(final PlayerData playerData) {
        super(playerData);
    }
    
    @Override
    public boolean onPacketReceive(final Player player, final Packet packet) {
        if (this.playerData.getLastRecivedKeepAlive() == 0L) {
            return true;
        }
        if (this.playerData.getLastSendKeepAlive() < 500L) {
            return true;
        }
        final long l = System.currentTimeMillis() - this.playerData.getLastRecivedKeepAlive();
        final long l2 = System.currentTimeMillis() - this.playerData.getLastSendKeepAlive();
        final int n2 = 5;
        this.vl = ((l > 7000L && l2 < 3000L) ? (this.vl += 3) : (this.vl -= 20));
        if (this.vl > n2) {
            this.alert(player, "PingSpoof", false, 25);
            this.vl = 0;
        }
        return true;
    }
}

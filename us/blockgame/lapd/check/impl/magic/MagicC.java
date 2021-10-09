// 
// Decompiled by Procyon v0.5.36
// 

package us.blockgame.lapd.check.impl.magic;

import net.minecraft.server.v1_8_R3.PacketPlayInBlockDig;
import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.entity.Player;
import us.blockgame.lapd.player.PlayerData;
import us.blockgame.lapd.check.dep.PacketDep;
import us.blockgame.lapd.check.Check;

public class MagicC extends Check implements PacketDep
{
    private double vl;
    
    public MagicC(final PlayerData playerData) {
        super(playerData);
    }
    
    @Override
    public boolean onPacketReceive(final Player player, final Packet packet) {
        if (packet instanceof PacketPlayInBlockDig) {
            if (((PacketPlayInBlockDig)packet).c() == PacketPlayInBlockDig.EnumPlayerDigType.RELEASE_USE_ITEM && this.playerData.isPlaced()) {
                final double vl = this.vl + 1.0;
                this.vl = vl;
                if (vl > 6.0) {
                    this.alert(player, "Magic C", true, 5);
                }
            }
            else {
                this.vl = Math.max(0.0, this.vl - 1.0);
            }
        }
        return true;
    }
}

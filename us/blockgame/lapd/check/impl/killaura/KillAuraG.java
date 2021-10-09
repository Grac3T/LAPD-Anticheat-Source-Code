// 
// Decompiled by Procyon v0.5.36
// 

package us.blockgame.lapd.check.impl.killaura;

import net.minecraft.server.v1_8_R3.PacketPlayInFlying;
import net.minecraft.server.v1_8_R3.PacketPlayInUseEntity;
import net.minecraft.server.v1_8_R3.PacketPlayInBlockDig;
import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.entity.Player;
import us.blockgame.lapd.player.PlayerData;
import us.blockgame.lapd.check.dep.PacketDep;
import us.blockgame.lapd.check.Check;

public class KillAuraG extends Check implements PacketDep
{
    private boolean sent;
    
    public KillAuraG(final PlayerData playerData) {
        super(playerData);
    }
    
    @Override
    public boolean onPacketReceive(final Player player, final Packet packet) {
        if (packet instanceof PacketPlayInBlockDig) {
            final PacketPlayInBlockDig.EnumPlayerDigType digType = ((PacketPlayInBlockDig)packet).c();
            if (digType == PacketPlayInBlockDig.EnumPlayerDigType.START_DESTROY_BLOCK || digType == PacketPlayInBlockDig.EnumPlayerDigType.ABORT_DESTROY_BLOCK) {
                this.sent = true;
            }
        }
        else if (packet instanceof PacketPlayInUseEntity && this.sent) {
            this.alert(player, "KillAura G", true, 5);
        }
        else if (packet instanceof PacketPlayInFlying) {
            this.sent = false;
        }
        return true;
    }
}

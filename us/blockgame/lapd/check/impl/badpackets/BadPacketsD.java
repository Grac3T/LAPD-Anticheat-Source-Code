// 
// Decompiled by Procyon v0.5.36
// 

package us.blockgame.lapd.check.impl.badpackets;

import net.minecraft.server.v1_8_R3.PacketPlayInFlying;
import net.minecraft.server.v1_8_R3.PacketPlayInEntityAction;
import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.entity.Player;
import us.blockgame.lapd.player.PlayerData;
import us.blockgame.lapd.check.dep.PacketDep;
import us.blockgame.lapd.check.Check;

public class BadPacketsD extends Check implements PacketDep
{
    private boolean sent;
    
    public BadPacketsD(final PlayerData playerData) {
        super(playerData);
    }
    
    @Override
    public boolean onPacketReceive(final Player player, final Packet packet) {
        if (packet instanceof PacketPlayInEntityAction) {
            final PacketPlayInEntityAction.EnumPlayerAction playerAction = ((PacketPlayInEntityAction)packet).b();
            if (playerAction == PacketPlayInEntityAction.EnumPlayerAction.START_SNEAKING || playerAction == PacketPlayInEntityAction.EnumPlayerAction.STOP_SNEAKING) {
                if (this.sent) {
                    this.alert(player, "BadPackets D", true, 3);
                }
                else {
                    this.sent = true;
                }
            }
        }
        else if (packet instanceof PacketPlayInFlying) {
            this.sent = false;
        }
        return true;
    }
}

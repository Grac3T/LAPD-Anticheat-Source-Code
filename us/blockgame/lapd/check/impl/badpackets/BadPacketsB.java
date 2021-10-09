// 
// Decompiled by Procyon v0.5.36
// 

package us.blockgame.lapd.check.impl.badpackets;

import net.minecraft.server.v1_8_R3.PacketPlayInFlying;
import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.entity.Player;
import us.blockgame.lapd.player.PlayerData;
import us.blockgame.lapd.check.dep.PacketDep;
import us.blockgame.lapd.check.Check;

public class BadPacketsB extends Check implements PacketDep
{
    public BadPacketsB(final PlayerData playerData) {
        super(playerData);
    }
    
    @Override
    public boolean onPacketReceive(final Player player, final Packet packet) {
        if (packet instanceof PacketPlayInFlying && Math.abs(((PacketPlayInFlying)packet).e()) > 90.0f) {
            this.alert(player, "BadPackets B", true, 1);
        }
        return true;
    }
}

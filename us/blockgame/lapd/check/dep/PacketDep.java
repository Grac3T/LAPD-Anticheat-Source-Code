// 
// Decompiled by Procyon v0.5.36
// 

package us.blockgame.lapd.check.dep;

import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.entity.Player;

public interface PacketDep
{
    boolean onPacketReceive(final Player p0, final Packet p1);
}

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

public class BadPacketsA extends Check implements PacketDep
{
    private int streak;
    
    public BadPacketsA(final PlayerData playerData) {
        super(playerData);
    }
    
    @Override
    public boolean onPacketReceive(final Player player, final Packet packet) {
        if (player.getVehicle() == null && packet instanceof PacketPlayInFlying) {
            if (((PacketPlayInFlying)packet).g()) {
                this.streak = 0;
            }
            else if (++this.streak > 20) {
                this.alert(player, "BadPackets A", true, 1);
            }
        }
        return true;
    }
}

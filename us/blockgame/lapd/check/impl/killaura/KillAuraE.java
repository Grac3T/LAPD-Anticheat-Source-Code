// 
// Decompiled by Procyon v0.5.36
// 

package us.blockgame.lapd.check.impl.killaura;

import net.minecraft.server.v1_8_R3.PacketPlayInUseEntity;
import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.entity.Player;
import us.blockgame.lapd.player.PlayerData;
import us.blockgame.lapd.check.dep.PacketDep;
import us.blockgame.lapd.check.Check;

public class KillAuraE extends Check implements PacketDep
{
    public KillAuraE(final PlayerData playerData) {
        super(playerData);
    }
    
    @Override
    public boolean onPacketReceive(final Player player, final Packet packet) {
        if (packet instanceof PacketPlayInUseEntity && ((PacketPlayInUseEntity)packet).a() == PacketPlayInUseEntity.EnumEntityUseAction.ATTACK && this.playerData.isPlaced()) {
            this.alert(player, "KillAura E", true, 2);
        }
        return true;
    }
}

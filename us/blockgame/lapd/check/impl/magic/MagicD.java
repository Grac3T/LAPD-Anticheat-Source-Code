// 
// Decompiled by Procyon v0.5.36
// 

package us.blockgame.lapd.check.impl.magic;

import net.minecraft.server.v1_8_R3.PacketPlayInFlying;
import net.minecraft.server.v1_8_R3.PacketPlayInBlockPlace;
import net.minecraft.server.v1_8_R3.PacketPlayInHeldItemSlot;
import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.entity.Player;
import us.blockgame.lapd.player.PlayerData;
import us.blockgame.lapd.check.dep.PacketDep;
import us.blockgame.lapd.check.Check;

public class MagicD extends Check implements PacketDep
{
    private boolean placed;
    
    public MagicD(final PlayerData playerData) {
        super(playerData);
    }
    
    @Override
    public boolean onPacketReceive(final Player player, final Packet packet) {
        if (packet instanceof PacketPlayInHeldItemSlot) {
            if (this.placed && this.playerData.getTeleportTicks() == 0 && this.playerData.getRespawnTicks() == 0 && !this.playerData.isLagging()) {
                this.alert(player, "Magic D", true, 5);
            }
        }
        else if (packet instanceof PacketPlayInBlockPlace) {
            this.placed = true;
        }
        else if (packet instanceof PacketPlayInFlying) {
            this.placed = false;
        }
        return true;
    }
}

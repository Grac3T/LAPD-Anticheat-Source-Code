// 
// Decompiled by Procyon v0.5.36
// 

package us.blockgame.lapd.check.impl.magic;

import net.minecraft.server.v1_8_R3.PacketPlayInFlying;
import net.minecraft.server.v1_8_R3.PacketPlayInEntityAction;
import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.entity.Player;
import us.blockgame.lapd.player.PlayerData;
import us.blockgame.lapd.check.dep.PacketDep;
import us.blockgame.lapd.check.Check;

public class MagicA extends Check implements PacketDep
{
    private boolean sentSprint;
    
    public MagicA(final PlayerData playerData) {
        super(playerData);
    }
    
    @Override
    public boolean onPacketReceive(final Player player, final Packet packet) {
        if (packet instanceof PacketPlayInEntityAction) {
            final PacketPlayInEntityAction.EnumPlayerAction playerAction = ((PacketPlayInEntityAction)packet).b();
            if (playerAction == PacketPlayInEntityAction.EnumPlayerAction.START_SPRINTING || playerAction == PacketPlayInEntityAction.EnumPlayerAction.STOP_SPRINTING) {
                if (this.sentSprint) {
                    if (this.playerData.getTeleportTicks() == 0 && this.playerData.getRespawnTicks() == 0 && !this.playerData.isLagging()) {
                        this.alert(player, "Magic A", true, 10);
                    }
                }
                else {
                    this.sentSprint = true;
                }
            }
        }
        else if (packet instanceof PacketPlayInFlying) {
            this.sentSprint = false;
        }
        return true;
    }
}

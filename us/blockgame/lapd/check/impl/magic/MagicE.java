// 
// Decompiled by Procyon v0.5.36
// 

package us.blockgame.lapd.check.impl.magic;

import net.minecraft.server.v1_8_R3.PacketPlayInBlockPlace;
import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.entity.Player;
import us.blockgame.lapd.player.PlayerData;
import net.minecraft.server.v1_8_R3.BlockPosition;
import us.blockgame.lapd.check.dep.PacketDep;
import us.blockgame.lapd.check.Check;

public class MagicE extends Check implements PacketDep
{
    private final BlockPosition INVALID_POSITION;
    
    public MagicE(final PlayerData playerData) {
        super(playerData);
        this.INVALID_POSITION = new BlockPosition(0, 0, 0);
    }
    
    @Override
    public boolean onPacketReceive(final Player player, final Packet packet) {
        if (packet instanceof PacketPlayInBlockPlace && ((PacketPlayInBlockPlace)packet).getFace() == 255) {
            final BlockPosition blockPosition = ((PacketPlayInBlockPlace)packet).a();
            if (blockPosition.getX() == this.INVALID_POSITION.getX() && blockPosition.getY() == this.INVALID_POSITION.getY() && blockPosition.getZ() == this.INVALID_POSITION.getZ() && player.getItemInHand().getType().isBlock() && !this.playerData.isLagging()) {
                this.alert(player, "Magic E", true, 5);
            }
        }
        return true;
    }
}

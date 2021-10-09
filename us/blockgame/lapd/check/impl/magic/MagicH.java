// 
// Decompiled by Procyon v0.5.36
// 

package us.blockgame.lapd.check.impl.magic;

import net.minecraft.server.v1_8_R3.PacketPlayInFlying;
import net.minecraft.server.v1_8_R3.PacketPlayInArmAnimation;
import net.minecraft.server.v1_8_R3.PacketPlayInUseEntity;
import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.entity.Player;
import us.blockgame.lapd.player.PlayerData;
import us.blockgame.lapd.check.dep.PacketDep;
import us.blockgame.lapd.check.Check;

public class MagicH extends Check implements PacketDep
{
    private boolean swung;
    private double vl;
    
    public MagicH(final PlayerData playerData) {
        super(playerData);
    }
    
    @Override
    public boolean onPacketReceive(final Player player, final Packet packet) {
        if (packet instanceof PacketPlayInUseEntity && ((PacketPlayInUseEntity)packet).a() == PacketPlayInUseEntity.EnumEntityUseAction.ATTACK) {
            if (this.swung) {
                this.vl = 0.0;
                return true;
            }
            if (!this.playerData.isLagging()) {
                final double vl = this.vl + 1.0;
                this.vl = vl;
                if (vl > 1.0) {
                    this.alert(player, "Magic H", true, 5);
                }
            }
        }
        else if (packet instanceof PacketPlayInArmAnimation) {
            this.swung = true;
        }
        else if (packet instanceof PacketPlayInFlying) {
            this.swung = false;
        }
        return true;
    }
}

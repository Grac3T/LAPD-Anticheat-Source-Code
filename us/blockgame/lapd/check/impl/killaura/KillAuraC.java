// 
// Decompiled by Procyon v0.5.36
// 

package us.blockgame.lapd.check.impl.killaura;

import net.minecraft.server.v1_8_R3.PacketPlayInUseEntity;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;
import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.entity.Player;
import us.blockgame.lapd.player.PlayerData;
import us.blockgame.lapd.check.dep.PacketDep;
import us.blockgame.lapd.check.Check;

public class KillAuraC extends Check implements PacketDep
{
    private Long lastFlying;
    private long lastMovePacket;
    private double vl;
    
    public KillAuraC(final PlayerData playerData) {
        super(playerData);
    }
    
    @Override
    public boolean onPacketReceive(final Player player, final Packet packet) {
        if (packet instanceof PacketPlayInFlying) {
            final long now = System.currentTimeMillis();
            if (this.playerData.getTeleportTicks() > 0 || this.playerData.isLagging() || player == null || player.isDead()) {
                return true;
            }
            if (this.lastFlying != null) {
                if (now - this.lastFlying > 40L && now - this.lastFlying < 100L) {
                    final double vl = this.vl + 0.25;
                    this.vl = vl;
                    if (vl > 1.0) {
                        this.alert(player, "KillAura C", true, 10);
                    }
                }
                else {
                    this.vl = Math.max(0.0, this.vl - 0.05);
                }
                this.lastFlying = null;
            }
            this.lastMovePacket = now;
        }
        else if (packet instanceof PacketPlayInUseEntity && ((PacketPlayInUseEntity)packet).a() == PacketPlayInUseEntity.EnumEntityUseAction.ATTACK) {
            final long now = System.currentTimeMillis();
            final long lastFlying = this.lastMovePacket;
            if (now - lastFlying < 10L) {
                this.lastFlying = lastFlying;
            }
            else {
                this.vl = Math.max(0.0, this.vl - 0.025);
            }
        }
        return true;
    }
}

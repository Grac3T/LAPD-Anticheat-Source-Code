// 
// Decompiled by Procyon v0.5.36
// 

package us.blockgame.lapd.check.impl.killaura;

import us.blockgame.lapd.position.CustomPosition;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;
import net.minecraft.server.v1_8_R3.PacketPlayInUseEntity;
import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.entity.Player;
import us.blockgame.lapd.player.PlayerData;
import us.blockgame.lapd.check.dep.PacketDep;
import us.blockgame.lapd.check.Check;

public class KillAuraB extends Check implements PacketDep
{
    private double vl;
    private long lastAttack;
    private boolean attack;
    
    public KillAuraB(final PlayerData playerData) {
        super(playerData);
    }
    
    @Override
    public boolean onPacketReceive(final Player player, final Packet packet) {
        if (packet instanceof PacketPlayInUseEntity && ((PacketPlayInUseEntity)packet).a() == PacketPlayInUseEntity.EnumEntityUseAction.ATTACK && !this.playerData.isLagging()) {
            final CustomPosition lastPosition = this.playerData.getPosition();
            if (lastPosition == null) {
                return true;
            }
            final long delay = System.currentTimeMillis() - lastPosition.getTimeStamp();
            if (this.vl < 0.0) {
                this.vl = 0.0;
            }
            if (delay <= 25.0) {
                this.lastAttack = System.currentTimeMillis();
                this.attack = true;
                return this.vl < 10.0;
            }
            this.vl -= 0.25;
        }
        else if (packet instanceof PacketPlayInFlying && this.attack) {
            final long time = System.currentTimeMillis() - this.lastAttack;
            if (time >= 25L && !this.playerData.isLagging()) {
                final double vl = this.vl + 1.0;
                this.vl = vl;
                if (vl > 10.0) {}
            }
            else {
                this.vl -= 0.25;
            }
            this.attack = false;
        }
        return true;
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package us.blockgame.lapd.check.impl.magic;

import net.minecraft.server.v1_8_R3.PacketPlayInSteerVehicle;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;
import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.entity.Player;
import us.blockgame.lapd.player.PlayerData;
import us.blockgame.lapd.check.dep.PacketDep;
import us.blockgame.lapd.check.Check;

public class MagicF extends Check implements PacketDep
{
    private double vl;
    private float lastYaw;
    private float lastPitch;
    private boolean ignore;
    
    public MagicF(final PlayerData playerData) {
        super(playerData);
    }
    
    @Override
    public boolean onPacketReceive(final Player player, final Packet packet) {
        if (packet instanceof PacketPlayInFlying) {
            final PacketPlayInFlying flying = (PacketPlayInFlying)packet;
            if (!flying.g() && flying.h()) {
                if (this.lastYaw == flying.d() && this.lastPitch == flying.e()) {
                    if (!this.ignore) {
                        final double vl = this.vl + 1.0;
                        this.vl = vl;
                        if (vl > 5.0) {
                            this.alert(player, "Magic F", true, 5);
                        }
                    }
                    else {
                        this.vl = Math.max(0.0, this.vl - 1.0);
                    }
                    this.ignore = false;
                }
                this.lastYaw = flying.d();
                this.lastPitch = flying.e();
            }
            else {
                this.ignore = true;
            }
        }
        else if (packet instanceof PacketPlayInSteerVehicle) {
            this.ignore = true;
        }
        return true;
    }
}

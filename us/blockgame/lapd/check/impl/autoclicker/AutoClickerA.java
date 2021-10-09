// 
// Decompiled by Procyon v0.5.36
// 

package us.blockgame.lapd.check.impl.autoclicker;

import us.blockgame.lapd.position.CustomPosition;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;
import net.minecraft.server.v1_8_R3.PacketPlayInArmAnimation;
import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.entity.Player;
import us.blockgame.lapd.player.PlayerData;
import us.blockgame.lapd.check.dep.PacketDep;
import us.blockgame.lapd.check.Check;

public class AutoClickerA extends Check implements PacketDep
{
    private double vl;
    private long lastSwing;
    private boolean swung;
    
    public AutoClickerA(final PlayerData playerData) {
        super(playerData);
    }
    
    @Override
    public boolean onPacketReceive(final Player player, final Packet packet) {
        if (packet instanceof PacketPlayInArmAnimation && !this.playerData.isLagging()) {
            final CustomPosition lastPosition = this.playerData.getPosition();
            if (lastPosition == null) {
                return true;
            }
            final long delay = System.currentTimeMillis() - lastPosition.getTimeStamp();
            if (delay <= 25.0) {
                this.lastSwing = System.currentTimeMillis();
                this.swung = true;
                return this.vl < 10.0;
            }
            this.vl -= 0.25;
        }
        else if (packet instanceof PacketPlayInFlying && this.swung) {
            final long time = System.currentTimeMillis() - this.lastSwing;
            if (time >= 25L && !this.playerData.isLagging()) {
                final double vl = this.vl + 1.0;
                this.vl = vl;
                if (vl > 6.0) {
                    this.alert(player, "AutoClicker A", true, 10);
                }
            }
            else {
                this.vl -= 0.25;
            }
            this.swung = false;
        }
        return true;
    }
}

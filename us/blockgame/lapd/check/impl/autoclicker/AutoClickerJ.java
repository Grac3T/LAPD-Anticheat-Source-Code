// 
// Decompiled by Procyon v0.5.36
// 

package us.blockgame.lapd.check.impl.autoclicker;

import net.minecraft.server.v1_8_R3.PacketPlayInFlying;
import net.minecraft.server.v1_8_R3.PacketPlayInArmAnimation;
import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.entity.Player;
import us.blockgame.lapd.player.PlayerData;
import us.blockgame.lapd.check.dep.PacketDep;
import us.blockgame.lapd.check.Check;

public class AutoClickerJ extends Check implements PacketDep
{
    private int ticks;
    private int clicks;
    private int oldClicks;
    private boolean alerted;
    private long lastClick;
    
    public AutoClickerJ(final PlayerData playerData) {
        super(playerData);
    }
    
    @Override
    public boolean onPacketReceive(final Player player, final Packet packet) {
        if (packet instanceof PacketPlayInArmAnimation && !this.playerData.isPlaced() && !this.playerData.isDigging()) {
            this.ticks = 0;
            if (System.currentTimeMillis() - this.lastClick > 250L) {
                this.clicks = this.oldClicks;
            }
            this.lastClick = System.currentTimeMillis();
        }
        if (packet instanceof PacketPlayInFlying) {
            if (++this.ticks <= 2) {
                if (++this.clicks > 100) {
                    if (this.clicks > 220) {
                        this.alert(player, "AutoClicker J (Abnormal)", false, 5);
                    }
                    if (this.clicks > 400) {
                        this.alert(player, "AutoClicker J (Impossible)", true, 1);
                        this.oldClicks = this.clicks;
                        this.clicks = 0;
                    }
                }
            }
            else if (this.ticks == 3) {
                this.oldClicks = this.clicks;
                this.clicks = 0;
            }
        }
        return true;
    }
}

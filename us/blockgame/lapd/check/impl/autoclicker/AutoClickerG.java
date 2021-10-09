// 
// Decompiled by Procyon v0.5.36
// 

package us.blockgame.lapd.check.impl.autoclicker;

import us.blockgame.lapd.LAPD;
import net.minecraft.server.v1_8_R3.PacketPlayInArmAnimation;
import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.entity.Player;
import us.blockgame.lapd.player.PlayerData;
import us.blockgame.lapd.check.dep.PacketDep;
import us.blockgame.lapd.check.Check;

public class AutoClickerG extends Check implements PacketDep
{
    public AutoClickerG(final PlayerData playerData) {
        super(playerData);
    }
    
    @Override
    public boolean onPacketReceive(final Player player, final Packet packet) {
        if (packet instanceof PacketPlayInArmAnimation && this.playerData.getLastCps() >= (LAPD.patroneMode ? 35 : 30) && !this.playerData.isDigging() && !this.playerData.isPlaced()) {
            this.alert(player, "AutoClicker G", true, 5);
        }
        return true;
    }
}

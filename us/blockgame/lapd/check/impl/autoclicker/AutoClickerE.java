// 
// Decompiled by Procyon v0.5.36
// 

package us.blockgame.lapd.check.impl.autoclicker;

import net.minecraft.server.v1_8_R3.PacketPlayInBlockDig;
import net.minecraft.server.v1_8_R3.PacketPlayInArmAnimation;
import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.entity.Player;
import us.blockgame.lapd.player.PlayerData;
import us.blockgame.lapd.check.dep.PacketDep;
import us.blockgame.lapd.check.Check;

public class AutoClickerE extends Check implements PacketDep
{
    private int stage;
    private double vl;
    
    public AutoClickerE(final PlayerData playerData) {
        super(playerData);
    }
    
    @Override
    public boolean onPacketReceive(final Player player, final Packet packet) {
        if (this.stage == 0) {
            if (packet instanceof PacketPlayInArmAnimation) {
                ++this.stage;
            }
        }
        else if (packet instanceof PacketPlayInBlockDig) {
            if (this.playerData.getFakeBlocks().contains(((PacketPlayInBlockDig)packet).a())) {
                return true;
            }
            final PacketPlayInBlockDig.EnumPlayerDigType digType = ((PacketPlayInBlockDig)packet).c();
            if (digType == PacketPlayInBlockDig.EnumPlayerDigType.ABORT_DESTROY_BLOCK) {
                if (this.stage == 1) {
                    ++this.stage;
                }
                else {
                    this.stage = 0;
                }
            }
            else if (digType == PacketPlayInBlockDig.EnumPlayerDigType.START_DESTROY_BLOCK) {
                if (this.stage == 2) {
                    final double vl = this.vl + 1.4;
                    this.vl = vl;
                    if (vl >= 15.0) {}
                }
                else {
                    this.stage = 0;
                    this.vl -= 0.25;
                }
            }
            else {
                this.stage = 0;
            }
            this.setVl(this.vl);
        }
        else {
            this.stage = 0;
        }
        return true;
    }
}

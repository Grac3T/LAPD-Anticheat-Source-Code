// 
// Decompiled by Procyon v0.5.36
// 

package us.blockgame.lapd.check.impl.range;

import us.blockgame.lapd.position.CustomPosition;
import us.blockgame.lapd.util.BlockUtil;
import us.blockgame.lapd.LAPD;
import org.bukkit.Material;
import org.bukkit.GameMode;
import us.blockgame.lapd.util.entity.PlayerUtil;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;
import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.entity.Player;
import us.blockgame.lapd.player.PlayerData;
import us.blockgame.lapd.check.dep.PacketDep;
import us.blockgame.lapd.check.Check;

public class RangeA extends Check implements PacketDep
{
    private double vl;
    private long lastTime;
    
    public RangeA(final PlayerData playerData) {
        super(playerData);
    }
    
    @Override
    public boolean onPacketReceive(final Player player, final Packet packet) {
        if (player.getAllowFlight()) {
            return true;
        }
        try {
            if (packet instanceof PacketPlayInFlying && !this.playerData.isLagging() && System.currentTimeMillis() - this.playerData.getLastMoveCancel() > 1000L && PlayerUtil.getPing(player) > 0 && this.playerData.getLastTarget() != null && System.currentTimeMillis() - this.playerData.getLastDelayedMovePacket() > 2500L && System.currentTimeMillis() - this.playerData.getLastAttack() < 50L && !this.playerData.isPlaced() && player.getGameMode().equals((Object)GameMode.SURVIVAL) && this.playerData.getTeleportTicks() == 0 && !player.getItemInHand().equals((Object)Material.FISHING_ROD) && !player.isFlying() && !player.isInsideVehicle() && player.getVehicle() == null && this.playerData.getLastTarget() instanceof Player) {
                final PlayerData targetData = LAPD.getInstance().getData((Player)this.playerData.getLastTarget());
                if (!targetData.isLagging() && System.currentTimeMillis() - this.playerData.getLastMoveCancel() > 1000L && targetData.getTeleportTicks() == 0 && !this.playerData.isCancelled()) {
                    final CustomPosition currentLocation = this.playerData.getPosition();
                    final CustomPosition previousLocation = this.playerData.getPreviousPosition();
                    final CustomPosition targetCurrentLocation = targetData.getPosition();
                    final CustomPosition targetPreviousLocation = targetData.getPreviousPosition();
                    if (currentLocation == null || previousLocation == null || targetCurrentLocation == null || targetPreviousLocation == null) {
                        return true;
                    }
                    if (System.currentTimeMillis() - this.playerData.getLoginTime() <= 30000L) {
                        return true;
                    }
                    if (PlayerUtil.getPing(player) > 300) {
                        return true;
                    }
                    final CustomPosition customPosition;
                    final CustomPosition customPosition2;
                    final double dx;
                    final double dz;
                    double range = targetData.getPastPositions().stream().mapToDouble(loc -> {
                        dx = Math.min(Math.min(Math.abs(customPosition.getCenterX() - loc.getMinX()), Math.abs(customPosition.getCenterX() - loc.getMaxX())), Math.min(Math.abs(customPosition2.getCenterX() - loc.getMinX()), Math.abs(customPosition2.getCenterX() - loc.getMaxX())));
                        dz = Math.min(Math.min(Math.abs(customPosition.getCenterZ() - loc.getMinZ()), Math.abs(customPosition.getCenterZ() - loc.getMaxZ())), Math.min(Math.abs(customPosition2.getCenterZ() - loc.getMinZ()), Math.abs(customPosition2.getCenterZ() - loc.getMaxZ())));
                        return Math.sqrt(Math.pow(dx, 2.0) + Math.pow(dz, 2.0));
                    }).min().orElse(0.0);
                    if (BlockUtil.againstWall((Player)this.playerData.getLastTarget())) {
                        return true;
                    }
                    range -= PlayerUtil.getPing(player) / 30 * 0.2;
                    if (this.playerData.getPing() >= 80L || LAPD.patroneMode) {
                        if (range <= 3.3 || range > 6.0) {
                            return true;
                        }
                    }
                    else if (((range <= 3.02 || range >= 3.03) && range <= 3.045) || range > 6.0) {
                        return true;
                    }
                    if (System.currentTimeMillis() - this.lastTime <= 1000L) {
                        this.alert(player, "Range A (2x)", false, 1);
                    }
                    this.lastTime = System.currentTimeMillis();
                    ++this.vl;
                    if (this.vl >= 2.0) {
                        this.alert(player, "Range A", true, 15);
                    }
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return true;
    }
}

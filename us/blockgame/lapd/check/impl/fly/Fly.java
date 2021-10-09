// 
// Decompiled by Procyon v0.5.36
// 

package us.blockgame.lapd.check.impl.fly;

import java.util.Iterator;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import us.blockgame.lapd.util.entity.PlayerUtil;
import org.bukkit.GameMode;
import us.blockgame.lapd.util.Velocity;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import us.blockgame.lapd.player.PlayerData;
import us.blockgame.lapd.check.dep.PositionDep;
import us.blockgame.lapd.check.Check;

public class Fly extends Check implements PositionDep
{
    private double jumpLimit;
    private double jumpMultiplier;
    private double fallSpeedLimit;
    private double lastFallSpeed;
    private double lastAscendSpeed;
    private double lastY;
    private double vl;
    private int fallViolations;
    
    public Fly(final PlayerData playerData) {
        super(playerData);
    }
    
    @Override
    public void onPosition(final Player player, final Location to, final Location from) {
        final double verticalDistance = to.getY() - from.getY();
        final double velocity = this.playerData.getVelocities().stream().mapToDouble(Velocity::getVertical).max().orElse(0.0);
        final double damageTicks = player.getMaximumNoDamageTicks();
        boolean test = true;
        final double hurt = (double)(System.currentTimeMillis() - this.playerData.getLastHurt());
        final double lastPlace = (double)(System.currentTimeMillis() - this.playerData.getLastBlockPlace());
        if (this.playerData.isWasOnGround() || this.playerData.isOnGround()) {
            this.lastY = from.getY();
        }
        if (player.isFlying() || player.getGameMode() != GameMode.SURVIVAL || this.playerData.getDeathTicks() != 0 || PlayerUtil.getPing(player) == 0 || this.playerData.getRespawnTicks() != 0 || this.playerData.getTeleportTicks() != 0 || lastPlace < 700.0 || (damageTicks < 19.0 && hurt < 700.0) || player.getNoDamageTicks() != 0 || player.isInsideVehicle() || this.playerData.isOnBadBlock() || this.playerData.isOnGround() || this.playerData.isInLiquid() || this.playerData.isOnLadder() || System.currentTimeMillis() - this.playerData.getLastMoveCancel() <= 3000L || System.currentTimeMillis() - this.playerData.getLastStuckOnWeb() <= 1500L || System.currentTimeMillis() - this.playerData.getLastOnSlime() <= 3000L || player.getLocation().getY() <= 10.0) {
            this.resetLimits(player);
            test = false;
        }
        if (test) {
            if (!this.playerData.isLagging()) {
                if (verticalDistance != 0.0 && verticalDistance == this.lastFallSpeed) {
                    if (velocity > 0.0) {
                        final double vl = this.vl + 1.0;
                        this.vl = vl;
                        if (vl > 5.0) {
                            this.alert(player, "Fly A", true, 10);
                            this.resetLimits(player);
                        }
                    }
                    else {
                        final double vl2 = this.vl + 1.0;
                        this.vl = vl2;
                        if (vl2 > 5.0 && !this.playerData.isPlaced() && hurt > 1000.0) {
                            this.alert(player, "Fly B", true, 10);
                            this.resetLimits(player);
                        }
                    }
                }
                if (verticalDistance > 0.0 && verticalDistance == this.lastAscendSpeed) {
                    if (velocity > 0.0) {
                        final double vl3 = this.vl + 1.0;
                        this.vl = vl3;
                        if (vl3 > 8.0) {
                            this.alert(player, "Fly C", true, 10);
                            this.resetLimits(player);
                        }
                    }
                    else {
                        final double vl4 = this.vl + 1.0;
                        this.vl = vl4;
                        if (vl4 > 5.0 && !this.playerData.isPlaced()) {
                            this.alert(player, "Fly D", true, 10);
                            this.resetLimits(player);
                        }
                    }
                }
            }
            if (this.playerData.isWasOnGround()) {
                this.resetLimits(player);
            }
            if (this.playerData.isWasInLiquid()) {
                this.jumpLimit += 0.18;
            }
            if (this.playerData.isOnBadBlock()) {
                if (from.getY() - this.lastY < 1.2 + 0.11 * this.getJumpBoostAmplifier(player) + velocity) {
                    this.resetLimits(player);
                }
                this.fallViolations = 0;
            }
            if (verticalDistance > 0.0) {
                if (verticalDistance > this.jumpLimit + velocity) {
                    if (!this.playerData.isOnLadder() && !this.playerData.isWasOnLadder() && System.currentTimeMillis() - this.playerData.getLastBlockOnFeet() >= 500L && System.currentTimeMillis() - this.playerData.getLastDelayedMovePacket() > 3000L && hurt > 1500.0) {
                        final double vl5 = this.vl + 1.0;
                        this.vl = vl5;
                        if (vl5 > 5.0 && !this.playerData.isPlaced()) {
                            this.alert(player, "Fly E", true, 10);
                            this.vl = 0.0;
                        }
                    }
                }
                else {
                    this.jumpLimit *= this.jumpMultiplier;
                    this.jumpMultiplier -= ((verticalDistance > 0.1 && verticalDistance < 0.4) ? 0.13 : 0.025);
                }
            }
            else if (verticalDistance < 0.0) {
                if (this.fallSpeedLimit - verticalDistance < 0.2 && ++this.fallViolations >= ((this.playerData.getVelocities().stream().mapToDouble(Velocity::getVertical).max().orElse(0.0) > 0.2) ? 10 : 7) && PlayerUtil.getPing(player) < 500) {
                    final double vl6 = this.vl + 1.0;
                    this.vl = vl6;
                    if (vl6 > 8.0 && !this.playerData.isPlaced()) {
                        this.alert(player, "Fly F", true, 10);
                        this.resetLimits(player);
                    }
                }
                this.fallSpeedLimit -= 0.01;
            }
            else if (verticalDistance == 0.0 && ++this.fallViolations >= 4 && PlayerUtil.getPing(player) < 500) {
                final double vl7 = this.vl + 1.0;
                this.vl = vl7;
                if (vl7 > 12.0) {
                    this.alert(player, "Fly G", true, 10);
                }
            }
        }
        if (from.getY() > to.getY()) {
            this.lastAscendSpeed = 0.0;
            this.lastFallSpeed = verticalDistance;
        }
        else {
            this.lastAscendSpeed = verticalDistance;
        }
    }
    
    private void resetLimits(final Player player) {
        final int jumpAmplifier = this.getJumpBoostAmplifier(player);
        this.jumpMultiplier = 0.8 + 0.03 * jumpAmplifier;
        this.jumpLimit = 0.42 + 0.11 * jumpAmplifier;
        this.fallSpeedLimit = 0.078;
        this.fallViolations = 0;
        this.vl = 0.0;
    }
    
    private int getJumpBoostAmplifier(final Player player) {
        if (player.hasPotionEffect(PotionEffectType.JUMP)) {
            for (final PotionEffect effect : player.getActivePotionEffects()) {
                if (effect.getType().equals((Object)PotionEffectType.JUMP)) {
                    return effect.getAmplifier() + 1;
                }
            }
        }
        return 0;
    }
}

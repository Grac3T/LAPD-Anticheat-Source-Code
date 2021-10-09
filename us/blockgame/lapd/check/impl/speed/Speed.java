// 
// Decompiled by Procyon v0.5.36
// 

package us.blockgame.lapd.check.impl.speed;

import org.bukkit.Material;
import org.bukkit.ChatColor;
import java.util.function.Supplier;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import us.blockgame.lapd.util.Velocity;
import org.bukkit.GameMode;
import us.blockgame.lapd.util.entity.PlayerUtil;
import org.bukkit.entity.Player;
import java.util.WeakHashMap;
import us.blockgame.lapd.player.PlayerData;
import us.blockgame.lapd.util.math.TimerUtils;
import org.bukkit.Location;
import java.util.UUID;
import java.util.Map;
import us.blockgame.lapd.check.dep.PositionDep;
import us.blockgame.lapd.check.Check;

public class Speed extends Check implements PositionDep
{
    private double previousDistance;
    private double blockFriction;
    private int fallTicks;
    private int waterTicks;
    private int threshold;
    private double vl;
    private double jew;
    private int sneakVLS;
    public Map<UUID, Location> flag;
    public Location location;
    public TimerUtils t;
    
    public Speed(final PlayerData playerData) {
        super(playerData);
        this.blockFriction = 0.91;
        this.t = new TimerUtils();
        this.flag = new WeakHashMap<UUID, Location>();
    }
    
    @Override
    public void onPosition(final Player player, final Location to, final Location from) {
        final double damageTicks = player.getMaximumNoDamageTicks();
        if (PlayerUtil.isReallyOnground(player) && this.t.hasReached((this.location == null) ? 50L : 3000L)) {
            this.flag.put(player.getUniqueId(), player.getLocation());
            this.location = player.getLocation();
            this.t.reset();
        }
        final double deltaX = to.getX() - from.getX();
        final double deltaZ = to.getZ() - from.getZ();
        final double horizontalDistance = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
        final double delay = (double)(System.currentTimeMillis() - this.playerData.getLastDelayedMovePacket());
        final double hurt = (double)(System.currentTimeMillis() - this.playerData.getLastHurt());
        if (System.currentTimeMillis() - this.playerData.getLastOnBadBlock() >= 500L && player.getVehicle() == null && player.getGameMode() != GameMode.CREATIVE) {
            if (horizontalDistance > 1.2 && delay <= 100.0 && this.playerData.getTeleportTicks() == 0 && !player.isFlying() && this.playerData.getRespawnTicks() == 0 && this.playerData.getDeathTicks() == 0 && hurt > 3000.0 && player.getGameMode().equals((Object)GameMode.SURVIVAL)) {
                final double jew = this.jew + 1.0;
                this.jew = jew;
                if (jew > 2.0) {
                    this.alert(player, "Speed B", true, 10);
                    this.jew = 0.0;
                }
            }
            else {
                this.jew = Math.max(0.0, this.jew - 0.25);
            }
        }
        if (System.currentTimeMillis() - this.playerData.getLastOnBadBlock() <= 500L || player.getVehicle() != null || (damageTicks < 20.0 && player.getNoDamageTicks() >= 3) || player.isFlying() || player.getGameMode() != GameMode.SURVIVAL || player.getNoDamageTicks() >= 3 || this.playerData.isLagging()) {
            return;
        }
        final double verticalDistance = to.getY() - from.getY();
        final int lastFlyTick = this.playerData.getLastFlyTick();
        if (lastFlyTick > 0) {
            this.playerData.setLastFlyTick(lastFlyTick - 1);
        }
        double horizontalSpeed = 1.0;
        double blockFriction = this.blockFriction;
        final boolean isPlayerOnGround = player.isOnGround();
        final boolean isUnderBlock = this.playerData.isBelowBlock() || this.playerData.isWasBelowBlock();
        final double speedLimit = isUnderBlock ? 2.6 : 1.0;
        if (isPlayerOnGround) {
            blockFriction *= 0.91;
            horizontalSpeed *= ((blockFriction > 0.708) ? 1.3 : 0.23315);
            horizontalSpeed *= 0.16277136 / Math.pow(blockFriction, 3.0);
            horizontalSpeed -= ((verticalDistance > 0.4199) ? -0.2 : 0.1055);
        }
        else {
            horizontalSpeed = 0.026;
            blockFriction = 0.91;
        }
        if (this.playerData.isInLiquid() || this.playerData.isWasInLiquid()) {
            horizontalSpeed *= ((++this.waterTicks < 8) ? ((this.waterTicks % 2 != 0) ? this.waterTicks : (this.waterTicks * 0.45)) : (player.isSprinting() ? 0.75 : 0.65));
        }
        else {
            this.waterTicks = 0;
        }
        if (lastFlyTick > 0) {
            horizontalSpeed *= lastFlyTick * 3.0f;
        }
        final double previousHorizontal = this.previousDistance;
        this.playerData.setHorizontalSpeed(horizontalDistance);
        horizontalSpeed += this.playerData.getVelocities().stream().mapToDouble(Velocity::getHorizontal).max().orElse(0.0);
        double moveSpeed = (horizontalDistance - previousHorizontal) / horizontalSpeed;
        if (verticalDistance < 0.0) {
            ++this.fallTicks;
            final double newMoveSpeed = moveSpeed - verticalDistance;
            switch (this.fallTicks) {
                case 1: {
                    if (newMoveSpeed > 0.9 && horizontalDistance > 0.28 && (verticalDistance > -0.07 || verticalDistance < -0.08)) {
                        moveSpeed *= 1.2;
                        horizontalSpeed /= 2.0;
                        break;
                    }
                    break;
                }
                case 2:
                case 3: {
                    if (Math.abs(verticalDistance) < 0.4) {
                        return;
                    }
                    if (player.getNoDamageTicks() == 0 && !this.playerData.isInLiquid() && this.playerData.isWasInLiquid()) {
                        this.alert(player, "Speed (3)", false, 5);
                        break;
                    }
                    break;
                }
            }
            if (this.fallTicks > 0 && this.fallTicks <= 5 && Math.abs(verticalDistance) > 0.4) {
                moveSpeed *= 1.2;
                horizontalSpeed /= 2.0;
            }
        }
        else {
            this.fallTicks = 0;
        }
        final boolean isSneaking = player.isSneaking();
        final boolean isOver = horizontalDistance > (isSneaking ? 0.15 : ((this.waterTicks > 0) ? 0.15 : 0.286));
        if (isOver && horizontalDistance - previousHorizontal > (isSneaking ? (horizontalSpeed * 0.5) : horizontalSpeed)) {
            moveSpeed *= 0.98;
            if (player.hasPotionEffect(PotionEffectType.SPEED)) {
                moveSpeed -= (player.getActivePotionEffects().stream().filter(potionEffect -> potionEffect.getType().equals((Object)PotionEffectType.SPEED)).findFirst().orElseGet(null).getAmplifier() + 1) * 0.21;
            }
            if (isSneaking) {
                moveSpeed *= 1.2;
            }
            final double speedDelta = moveSpeed - speedLimit / blockFriction;
            if (speedDelta > 2.0 && horizontalDistance < 0.5) {
                moveSpeed *= 0.1;
            }
            if (moveSpeed > speedLimit) {
                if (player.isSneaking()) {
                    if (this.flag.containsKey(player.getUniqueId())) {
                        this.flag(player, this.flag.get(player.getUniqueId()));
                    }
                    player.sendMessage(ChatColor.RED + "You are glitched. Please sneak + unsneak to fix.");
                    if (++this.sneakVLS >= 7) {
                        this.alert(player, "Speed C (" + this.sneakVLS + ")", true, 10);
                        this.sneakVLS = 0;
                    }
                }
                else {
                    this.sneakVLS = Math.max(0, this.sneakVLS - 1);
                }
                if ((this.threshold += 20) >= 400) {
                    final double vl = this.vl + 1.0;
                    this.vl = vl;
                    if (vl > 6.0 && !player.isSneaking()) {
                        this.alert(player, "Speed A", true, 10);
                        this.vl = 0.0;
                    }
                }
            }
            else {
                this.vl = Math.max(0.0, this.vl - 1.0);
            }
        }
        if (this.threshold > 0) {
            --this.threshold;
        }
        final double friction = this.hasSpecialFriction(to) ? 1.0 : 0.6;
        this.previousDistance = horizontalDistance * blockFriction;
        this.blockFriction = friction;
    }
    
    private void flag(final Player p, final Location l) {
        if (l != null) {
            p.teleport(l);
        }
    }
    
    private boolean hasSpecialFriction(final Location location) {
        final Material material = location.getWorld().getBlockAt(location.clone().subtract(0.0, 1.0, 0.0)).getType();
        final boolean defaultSpecialFriction = material == Material.PACKED_ICE || material == Material.ICE;
        return defaultSpecialFriction || material == Material.SLIME_BLOCK;
    }
}

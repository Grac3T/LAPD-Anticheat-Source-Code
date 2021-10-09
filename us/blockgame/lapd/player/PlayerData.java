// 
// Decompiled by Procyon v0.5.36
// 

package us.blockgame.lapd.player;

import org.bukkit.World;
import us.blockgame.lapd.util.Materials;
import org.bukkit.Material;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.ArrayList;
import com.google.common.collect.EvictingQueue;
import us.blockgame.lapd.check.impl.badpackets.BadPacketsF;
import us.blockgame.lapd.check.impl.fly.FlyH;
import us.blockgame.lapd.check.impl.speed.Speed;
import us.blockgame.lapd.check.velocity.VelocityB;
import us.blockgame.lapd.check.impl.fly.Fly;
import us.blockgame.lapd.check.impl.nofall.NoFallA;
import us.blockgame.lapd.check.impl.scaffold.ScaffoldA;
import us.blockgame.lapd.check.impl.aim.AimB;
import us.blockgame.lapd.check.impl.aim.AimA;
import us.blockgame.lapd.check.impl.autoclicker.AutoClickerJ;
import us.blockgame.lapd.check.impl.autoclicker.AutoClickerF;
import us.blockgame.lapd.check.impl.autoclicker.AutoClickerE;
import us.blockgame.lapd.check.impl.autoclicker.AutoClickerD;
import us.blockgame.lapd.check.impl.autoclicker.AutoClickerC;
import us.blockgame.lapd.check.impl.autoclicker.AutoClickerB;
import us.blockgame.lapd.check.impl.autoclicker.AutoClickerA;
import us.blockgame.lapd.check.impl.badpackets.BadPacketsE;
import us.blockgame.lapd.check.impl.badpackets.BadPacketsD;
import us.blockgame.lapd.check.impl.badpackets.BadPacketsC;
import us.blockgame.lapd.check.impl.badpackets.BadPacketsB;
import us.blockgame.lapd.check.impl.badpackets.BadPacketsA;
import us.blockgame.lapd.check.impl.pingspoof.PingSpoof;
import us.blockgame.lapd.check.impl.autoclicker.AutoClickerI;
import us.blockgame.lapd.check.impl.autoclicker.AutoClickerH;
import us.blockgame.lapd.check.impl.autoclicker.AutoClickerG;
import us.blockgame.lapd.check.impl.range.RangeA;
import us.blockgame.lapd.check.impl.magic.MagicH;
import us.blockgame.lapd.check.impl.magic.MagicF;
import us.blockgame.lapd.check.impl.magic.MagicE;
import us.blockgame.lapd.check.impl.magic.MagicD;
import us.blockgame.lapd.check.impl.magic.MagicC;
import us.blockgame.lapd.check.impl.magic.MagicB;
import us.blockgame.lapd.check.impl.magic.MagicA;
import us.blockgame.lapd.check.impl.timer.TimerA;
import us.blockgame.lapd.check.impl.killaura.KillAuraG;
import us.blockgame.lapd.check.impl.killaura.KillAuraF;
import us.blockgame.lapd.check.impl.killaura.KillAuraE;
import us.blockgame.lapd.check.impl.killaura.KillAuraD;
import us.blockgame.lapd.check.impl.killaura.KillAuraC;
import us.blockgame.lapd.check.impl.killaura.KillAuraB;
import us.blockgame.lapd.check.impl.killaura.KillAuraA;
import java.util.HashMap;
import org.bukkit.entity.Entity;
import us.blockgame.lapd.position.CustomRotation;
import net.minecraft.server.v1_8_R3.BlockPosition;
import java.util.Set;
import us.blockgame.lapd.util.Velocity;
import us.blockgame.lapd.check.dep.PacketDep;
import us.blockgame.lapd.check.dep.PositionDep;
import us.blockgame.lapd.check.dep.RotationDep;
import us.blockgame.lapd.position.CustomPosition;
import java.util.Queue;
import us.blockgame.lapd.util.CustomLocation;
import java.util.List;
import java.util.UUID;
import java.util.Map;

public class PlayerData
{
    private Map<UUID, List<CustomLocation>> recentPlayerPackets;
    private Map<Integer, Long> keepAliveTimes;
    private final Class[] packetChecks;
    private final Class[] rotationChecks;
    private final Class[] positionChecks;
    private final Queue<CustomPosition> pastPositions;
    private final List<RotationDep> rotationDepList;
    private final List<PositionDep> positionDepList;
    private final List<PacketDep> packetDepList;
    private final List<Velocity> velocities;
    private final Set<BlockPosition> fakeBlocks;
    private CustomPosition position;
    private CustomPosition previousPosition;
    private CustomRotation lastRotation;
    private int deathTicks;
    private int RespawnTicks;
    private int sprintTicks;
    private int lastCps;
    private long lastAttack;
    private long lastMovePacket;
    private long lastDelayedMovePacket;
    private long lastOnSlime;
    private long lastStuckOnWeb;
    private long lastOnBadBlock;
    private long lastBlockOnFeet;
    private long lastBlockOnHead;
    private long lastMoveCancel;
    private long lastRecivedKeepAlive;
    private long lastSendKeepAlive;
    private long longInTimePassed;
    private long lastPlace;
    private long lastHurt;
    private long lastFlyingPacket;
    private long lastBlockPlace;
    private long lastTimeStamp;
    private long lastLag;
    private long lastVelocity;
    private long ping;
    private long loginTime;
    private long lastJump;
    private boolean placed;
    private boolean digging;
    private boolean tpuknown;
    private boolean onGround;
    private boolean inLiquid;
    private boolean onLadder;
    private boolean belowBlock;
    private boolean wasOnGround;
    private boolean wasInLiquid;
    private boolean wasOnLadder;
    private boolean wasBelowBlock;
    private boolean onBadBlock;
    private boolean attackedSinceVelocity;
    private boolean onIce;
    private boolean underBlock;
    private boolean onStairs;
    private boolean fakeDigging;
    private boolean invOpened;
    private boolean alerts;
    private boolean instantBreakDigging;
    private boolean sprinting;
    private boolean cancelled;
    private int teleportTicks;
    private int lastFlyTick;
    private int standTicks;
    private double horizontalSpeed;
    private double yDiff;
    private double lastAlertedVl;
    private double velocityX;
    private double velocityY;
    private double velocityZ;
    private double velocityH;
    private double velocityV;
    private double lastDamage;
    private boolean hasAlerts;
    private boolean banned;
    private String clientType;
    private double hitboxHit;
    private Entity lastTarget;
    private CustomLocation lastCustomLocation;
    private boolean passedMCBrand;
    private boolean lunar;
    
    public PlayerData() {
        this.recentPlayerPackets = new HashMap<UUID, List<CustomLocation>>();
        this.keepAliveTimes = new HashMap<Integer, Long>();
        this.packetChecks = new Class[] { KillAuraA.class, KillAuraB.class, KillAuraC.class, KillAuraD.class, KillAuraE.class, KillAuraF.class, KillAuraG.class, TimerA.class, MagicA.class, MagicB.class, MagicC.class, MagicD.class, MagicE.class, MagicF.class, MagicH.class, RangeA.class, AutoClickerG.class, AutoClickerH.class, AutoClickerI.class, PingSpoof.class, BadPacketsA.class, BadPacketsB.class, BadPacketsC.class, BadPacketsD.class, BadPacketsE.class, AutoClickerA.class, AutoClickerB.class, AutoClickerC.class, AutoClickerD.class, AutoClickerE.class, AutoClickerF.class, AutoClickerJ.class };
        this.rotationChecks = new Class[] { AimA.class, AimB.class, ScaffoldA.class };
        this.positionChecks = new Class[] { NoFallA.class, Fly.class, VelocityB.class, Speed.class, FlyH.class, BadPacketsF.class };
        this.pastPositions = (Queue<CustomPosition>)EvictingQueue.create(8);
        this.rotationDepList = new ArrayList<RotationDep>();
        this.positionDepList = new ArrayList<PositionDep>();
        this.packetDepList = new ArrayList<PacketDep>();
        this.velocities = new LinkedList<Velocity>();
        this.fakeBlocks = new HashSet<BlockPosition>();
        final ReflectiveOperationException ex;
        ReflectiveOperationException e;
        Arrays.stream(this.packetChecks).forEach(check -> {
            try {
                this.packetDepList.add(check.getConstructor(PlayerData.class).newInstance(this));
            }
            catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException ex4) {
                e = ex;
                e.printStackTrace();
            }
            return;
        });
        final ReflectiveOperationException ex2;
        ReflectiveOperationException e2;
        Arrays.stream(this.rotationChecks).forEach(check -> {
            try {
                this.rotationDepList.add(check.getConstructor(PlayerData.class).newInstance(this));
            }
            catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException ex5) {
                e2 = ex2;
                e2.printStackTrace();
            }
            return;
        });
        final ReflectiveOperationException ex3;
        ReflectiveOperationException e3;
        Arrays.stream(this.positionChecks).forEach(check -> {
            try {
                this.positionDepList.add(check.getConstructor(PlayerData.class).newInstance(this));
            }
            catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException ex6) {
                e3 = ex3;
                e3.printStackTrace();
            }
            return;
        });
        this.alerts = true;
    }
    
    public void updatePositionFlags(final Player player, final Location to) {
        final World world = player.getWorld();
        this.velocities.removeIf(velocity -> System.currentTimeMillis() > velocity.getCreationTime() + 2000L);
        final int startX = Location.locToBlock(to.getX() - 0.3);
        final int endX = Location.locToBlock(to.getX() + 0.3);
        final int startZ = Location.locToBlock(to.getZ() - 0.3);
        final int endZ = Location.locToBlock(to.getZ() + 0.3);
        final long now = System.currentTimeMillis();
        this.wasOnGround = this.onGround;
        this.wasInLiquid = this.inLiquid;
        this.wasOnLadder = this.onLadder;
        this.wasBelowBlock = this.belowBlock;
        this.onGround = false;
        this.inLiquid = false;
        this.onLadder = false;
        this.belowBlock = false;
        this.onBadBlock = false;
        for (int bx = startX; bx <= endX; ++bx) {
            for (int bz = startZ; bz <= endZ; ++bz) {
                final int by = Location.locToBlock(to.getY());
                final Material typeBelow = world.getBlockAt(bx, by - 1, bz).getType();
                final Material typeFeet = world.getBlockAt(bx, by, bz).getType();
                final Material typeHead = world.getBlockAt(bx, by + 1, bz).getType();
                final Material typeAbove = world.getBlockAt(bx, by + 2, bz).getType();
                if (typeBelow != Material.AIR) {
                    this.lastBlockOnFeet = now;
                }
                if (typeAbove != Material.AIR || typeHead != Material.AIR) {
                    this.lastBlockOnHead = now;
                }
                if (typeBelow == Material.WEB || typeFeet == Material.WEB || typeHead == Material.WEB || typeAbove == Material.WEB) {
                    this.lastStuckOnWeb = now;
                }
                if (typeBelow == Material.SLIME_BLOCK) {
                    this.lastOnSlime = now;
                }
                if (typeHead == Material.TRAP_DOOR || typeHead == Material.IRON_TRAPDOOR) {
                    this.belowBlock = true;
                }
                if (Materials.checkFlag(typeFeet, 2) || Materials.checkFlag(typeHead, 2)) {
                    this.inLiquid = true;
                }
                else if (Materials.checkFlag(typeFeet, 1)) {
                    this.onGround = true;
                }
                else if (Materials.checkFlag(typeBelow, 1)) {
                    final double distance = to.getY() - to.getBlockY();
                    if (distance < 0.2) {
                        this.onGround = true;
                    }
                    if (Materials.checkFlag(typeBelow, 8) && distance < 0.51) {
                        this.onBadBlock = true;
                    }
                }
                if (this.hasBadBoundingBox(typeFeet) || this.hasBadBoundingBox(typeBelow)) {
                    this.onBadBlock = true;
                    this.onGround = false;
                }
                if (Materials.checkFlag(typeFeet, 4) || Materials.checkFlag(typeHead, 4)) {
                    this.onLadder = true;
                }
                if (Materials.checkFlag(typeAbove, 1)) {
                    this.belowBlock = true;
                }
                if (typeBelow.getId() == 172) {
                    this.onGround = true;
                }
                if (this.onBadBlock) {
                    this.lastOnBadBlock = System.currentTimeMillis();
                }
            }
        }
    }
    
    public void setLastDelayedMovePacket(final long lastDelayedMovePacket) {
        this.lastDelayedMovePacket = lastDelayedMovePacket;
    }
    
    public void setLastMovePacket(final long lastMovePacket) {
        this.lastMovePacket = lastMovePacket;
    }
    
    public void setLastAttack(final long lastAttack) {
        this.lastAttack = lastAttack;
    }
    
    public void setDigging(final boolean digging) {
        this.digging = digging;
    }
    
    public void setOnGround(final boolean onGround) {
        this.onGround = onGround;
    }
    
    public void setInLiquid(final boolean inLiquid) {
        this.inLiquid = inLiquid;
    }
    
    public boolean isDigging() {
        return this.digging;
    }
    
    public long getLastAttack() {
        return this.lastAttack;
    }
    
    public Class[] getPacketChecks() {
        return this.packetChecks;
    }
    
    public Class[] getPositionChecks() {
        return this.positionChecks;
    }
    
    public Class[] getRotationChecks() {
        return this.rotationChecks;
    }
    
    public CustomPosition getPosition() {
        return this.position;
    }
    
    public CustomPosition getPreviousPosition() {
        return this.previousPosition;
    }
    
    public boolean isPlaced() {
        return this.placed;
    }
    
    public CustomRotation getLastRotation() {
        return this.lastRotation;
    }
    
    public int getDeathTicks() {
        return this.deathTicks;
    }
    
    public int getRespawnTicks() {
        return this.RespawnTicks;
    }
    
    public int getSprintTicks() {
        return this.sprintTicks;
    }
    
    public List<PacketDep> getPacketDepList() {
        return this.packetDepList;
    }
    
    public List<PositionDep> getPositionDepList() {
        return this.positionDepList;
    }
    
    public List<RotationDep> getRotationDepList() {
        return this.rotationDepList;
    }
    
    public List<Velocity> getVelocities() {
        return this.velocities;
    }
    
    public boolean isTpuknown() {
        return this.tpuknown;
    }
    
    public boolean isOnGround() {
        return this.onGround;
    }
    
    public long getLastBlockOnFeet() {
        return this.lastBlockOnFeet;
    }
    
    public boolean isInLiquid() {
        return this.inLiquid;
    }
    
    public long getLastBlockOnHead() {
        return this.lastBlockOnHead;
    }
    
    public long getLastDelayedMovePacket() {
        return this.lastDelayedMovePacket;
    }
    
    public long getLastBlockPlace() {
        return this.lastBlockPlace;
    }
    
    public long getLastFlyingPacket() {
        return this.lastFlyingPacket;
    }
    
    public long getLastHurt() {
        return this.lastHurt;
    }
    
    public long getLastMoveCancel() {
        return this.lastMoveCancel;
    }
    
    public Queue<CustomPosition> getPastPositions() {
        return this.pastPositions;
    }
    
    public long getLastMovePacket() {
        return this.lastMovePacket;
    }
    
    public boolean isOnLadder() {
        return this.onLadder;
    }
    
    public long getLastOnBadBlock() {
        return this.lastOnBadBlock;
    }
    
    public long getLastOnSlime() {
        return this.lastOnSlime;
    }
    
    public long getLastPlace() {
        return this.lastPlace;
    }
    
    public long getLastRecivedKeepAlive() {
        return this.lastRecivedKeepAlive;
    }
    
    public long getLastSendKeepAlive() {
        return this.lastSendKeepAlive;
    }
    
    public long getLastStuckOnWeb() {
        return this.lastStuckOnWeb;
    }
    
    public long getLongInTimePassed() {
        return this.longInTimePassed;
    }
    
    public void setDeathTicks(final int deathTicks) {
        this.deathTicks = deathTicks;
    }
    
    public void setLastBlockOnFeet(final long lastBlockOnFeet) {
        this.lastBlockOnFeet = lastBlockOnFeet;
    }
    
    public boolean isBelowBlock() {
        return this.belowBlock;
    }
    
    public void setBelowBlock(final boolean belowBlock) {
        this.belowBlock = belowBlock;
    }
    
    public void setLastBlockOnHead(final long lastBlockOnHead) {
        this.lastBlockOnHead = lastBlockOnHead;
    }
    
    public void setLastBlockPlace(final long lastBlockPlace) {
        this.lastBlockPlace = lastBlockPlace;
    }
    
    public void setLastFlyingPacket(final long lastFlyingPacket) {
        this.lastFlyingPacket = lastFlyingPacket;
    }
    
    public void setLastHurt(final long lastHurt) {
        this.lastHurt = lastHurt;
    }
    
    public void setLastMoveCancel(final long lastMoveCancel) {
        this.lastMoveCancel = lastMoveCancel;
    }
    
    public void setLastOnBadBlock(final long lastOnBadBlock) {
        this.lastOnBadBlock = lastOnBadBlock;
    }
    
    public void setLastOnSlime(final long lastOnSlime) {
        this.lastOnSlime = lastOnSlime;
    }
    
    public void setLastPlace(final long lastPlace) {
        this.lastPlace = lastPlace;
    }
    
    public void setLastRecivedKeepAlive(final long lastRecivedKeepAlive) {
        this.lastRecivedKeepAlive = lastRecivedKeepAlive;
    }
    
    public void setLastRotation(final CustomRotation lastRotation) {
        this.lastRotation = lastRotation;
    }
    
    public boolean isWasOnGround() {
        return this.wasOnGround;
    }
    
    public void setLastSendKeepAlive(final long lastSendKeepAlive) {
        this.lastSendKeepAlive = lastSendKeepAlive;
    }
    
    public void setLastStuckOnWeb(final long lastStuckOnWeb) {
        this.lastStuckOnWeb = lastStuckOnWeb;
    }
    
    public void setLongInTimePassed(final long longInTimePassed) {
        this.longInTimePassed = longInTimePassed;
    }
    
    public void setPlaced(final boolean placed) {
        this.placed = placed;
    }
    
    public void setPosition(final CustomPosition position) {
        this.position = position;
    }
    
    public void setOnLadder(final boolean onLadder) {
        this.onLadder = onLadder;
    }
    
    public void setPreviousPosition(final CustomPosition previousPosition) {
        this.previousPosition = previousPosition;
    }
    
    public boolean isWasInLiquid() {
        return this.wasInLiquid;
    }
    
    public void setRespawnTicks(final int respawnTicks) {
        this.RespawnTicks = respawnTicks;
    }
    
    public boolean isWasOnLadder() {
        return this.wasOnLadder;
    }
    
    public void setSprintTicks(final int sprintTicks) {
        this.sprintTicks = sprintTicks;
    }
    
    public void setTpuknown(final boolean tpuknown) {
        this.tpuknown = tpuknown;
    }
    
    public boolean isHasAlerts() {
        return this.hasAlerts;
    }
    
    public boolean isInvOpened() {
        return this.invOpened;
    }
    
    public boolean isOnBadBlock() {
        return this.onBadBlock;
    }
    
    public void setWasInLiquid(final boolean wasInLiquid) {
        this.wasInLiquid = wasInLiquid;
    }
    
    public boolean isWasBelowBlock() {
        return this.wasBelowBlock;
    }
    
    public double getHitboxHit() {
        return this.hitboxHit;
    }
    
    public double getHorizontalSpeed() {
        return this.horizontalSpeed;
    }
    
    public double getYDiff() {
        return this.yDiff;
    }
    
    public void setWasOnGround(final boolean wasOnGround) {
        this.wasOnGround = wasOnGround;
    }
    
    public int getLastFlyTick() {
        return this.lastFlyTick;
    }
    
    public int getStandTicks() {
        return this.standTicks;
    }
    
    public int getTeleportTicks() {
        return this.teleportTicks;
    }
    
    public String getClientType() {
        return this.clientType;
    }
    
    public void setClientType(final String clientType) {
        this.clientType = clientType;
    }
    
    public void setHasAlerts(final boolean hasAlerts) {
        this.hasAlerts = hasAlerts;
    }
    
    public void setHitboxHit(final double hitboxHit) {
        this.hitboxHit = hitboxHit;
    }
    
    public void setHorizontalSpeed(final double horizontalSpeed) {
        this.horizontalSpeed = horizontalSpeed;
    }
    
    public void setInvOpened(final boolean invOpened) {
        this.invOpened = invOpened;
    }
    
    public void setLastFlyTick(final int lastFlyTick) {
        this.lastFlyTick = lastFlyTick;
    }
    
    public void setOnBadBlock(final boolean onBadBlock) {
        this.onBadBlock = onBadBlock;
    }
    
    public void setStandTicks(final int standTicks) {
        this.standTicks = standTicks;
    }
    
    public void setTeleportTicks(final int teleportTicks) {
        this.teleportTicks = teleportTicks;
    }
    
    public void setWasBelowBlock(final boolean wasBelowBlock) {
        this.wasBelowBlock = wasBelowBlock;
    }
    
    public void setWasOnLadder(final boolean wasOnLadder) {
        this.wasOnLadder = wasOnLadder;
    }
    
    public void setYDiff(final double yDiff) {
        this.yDiff = yDiff;
    }
    
    public long getLastTimeStamp() {
        return this.lastTimeStamp;
    }
    
    public void setLastTimeStamp(final long lastTimeStamp) {
        this.lastTimeStamp = lastTimeStamp;
    }
    
    public double getLastAlertedVl() {
        return this.lastAlertedVl;
    }
    
    public void setLastAlertedVl(final double lastAlertedVl) {
        this.lastAlertedVl = lastAlertedVl;
    }
    
    public void setLastLag(final long lastLag) {
        this.lastLag = lastLag;
    }
    
    public long getLastLag() {
        return this.lastLag;
    }
    
    public Entity getLastTarget() {
        return this.lastTarget;
    }
    
    public void setLastTarget(final Entity lastTarget) {
        this.lastTarget = lastTarget;
    }
    
    public long getLastVelocity() {
        return this.lastVelocity;
    }
    
    public void setVelocityZ(final double velocityZ) {
        this.velocityZ = velocityZ;
    }
    
    public double getVelocityZ() {
        return this.velocityZ;
    }
    
    public void setAttackedSinceVelocity(final boolean attackedSinceVelocity) {
        this.attackedSinceVelocity = attackedSinceVelocity;
    }
    
    public void setVelocityX(final double velocityX) {
        this.velocityX = velocityX;
    }
    
    public double getVelocityX() {
        return this.velocityX;
    }
    
    public void setVelocityY(final double velocityY) {
        this.velocityY = velocityY;
    }
    
    public double getVelocityY() {
        return this.velocityY;
    }
    
    public boolean isAttackedSinceVelocity() {
        return this.attackedSinceVelocity;
    }
    
    public void setLastVelocity(final long lastVelocity) {
        this.lastVelocity = lastVelocity;
    }
    
    public double getVelocityH() {
        return this.velocityH;
    }
    
    public void setVelocityH(final double velocityH) {
        this.velocityH = velocityH;
    }
    
    public void setVelocityV(final double velocityV) {
        this.velocityV = velocityV;
    }
    
    public double getVelocityV() {
        return this.velocityV;
    }
    
    public void setOnIce(final boolean onIce) {
        this.onIce = onIce;
    }
    
    public boolean isOnIce() {
        return this.onIce;
    }
    
    public void setUnderBlock(final boolean underBlock) {
        this.underBlock = underBlock;
    }
    
    public void setOnStairs(final boolean onStairs) {
        this.onStairs = onStairs;
    }
    
    public boolean isUnderBlock() {
        return this.underBlock;
    }
    
    public boolean isOnStairs() {
        return this.onStairs;
    }
    
    private boolean hasBadBoundingBox(final Material material) {
        return material == Material.FENCE || material == Material.NETHER_FENCE || material == Material.BOAT || material == Material.WATER_LILY || material == Material.ENDER_PORTAL_FRAME || material == Material.ENCHANTMENT_TABLE || material == Material.FENCE_GATE || material == Material.ACACIA_FENCE_GATE || material == Material.DARK_OAK_FENCE_GATE || material == Material.JUNGLE_FENCE_GATE || material == Material.BIRCH_FENCE_GATE || material == Material.SPRUCE_FENCE_GATE || material == Material.DARK_OAK_FENCE_GATE || material == Material.ACACIA_FENCE || material == Material.DARK_OAK_FENCE || material == Material.JUNGLE_FENCE || material == Material.BIRCH_FENCE || material == Material.SPRUCE_FENCE || material == Material.DARK_OAK_FENCE || material == Material.FLOWER_POT || material == Material.SKULL;
    }
    
    public boolean isLagging() {
        final long now = System.currentTimeMillis();
        return now - this.lastDelayedMovePacket < 220L || this.teleportTicks > 0;
    }
    
    public CustomLocation getLastPlayerPacket(final UUID playerUUID, final int index) {
        if (this.recentPlayerPackets.containsKey(playerUUID) && this.recentPlayerPackets.get(playerUUID).size() > index) {
            return this.recentPlayerPackets.get(playerUUID).get(this.recentPlayerPackets.get(playerUUID).size() - index);
        }
        return null;
    }
    
    public void addPlayerPacket(final UUID playerUUID, final CustomLocation customLocation) {
        List<CustomLocation> customLocations = this.recentPlayerPackets.get(playerUUID);
        if (customLocations == null) {
            customLocations = new ArrayList<CustomLocation>();
        }
        if (customLocations.size() == 20) {
            customLocations.remove(0);
        }
        customLocations.add(customLocation);
        this.recentPlayerPackets.put(playerUUID, customLocations);
    }
    
    public boolean isAlerts() {
        return this.alerts;
    }
    
    public void setAlerts(final boolean alerts) {
        this.alerts = alerts;
    }
    
    public boolean isFakeDigging() {
        return this.fakeDigging;
    }
    
    public void setFakeDigging(final boolean fakeDigging) {
        this.fakeDigging = fakeDigging;
    }
    
    public boolean isInstantBreakDigging() {
        return this.instantBreakDigging;
    }
    
    public void setInstantBreakDigging(final boolean instantBreakDigging) {
        this.instantBreakDigging = instantBreakDigging;
    }
    
    public Set<BlockPosition> getFakeBlocks() {
        return this.fakeBlocks;
    }
    
    public int getLastCps() {
        return this.lastCps;
    }
    
    public void setLastCps(final int lastCps) {
        this.lastCps = lastCps;
    }
    
    public boolean isBanned() {
        return this.banned;
    }
    
    public void setBanned(final boolean banned) {
        this.banned = banned;
    }
    
    public CustomLocation getLastCustomLocation() {
        return this.lastCustomLocation;
    }
    
    public void setLastCustomLocation(final CustomLocation lastCustomLocation) {
        this.lastCustomLocation = lastCustomLocation;
    }
    
    public boolean isSprinting() {
        return this.sprinting;
    }
    
    public void setSprinting(final boolean sprinting) {
        this.sprinting = sprinting;
    }
    
    public boolean keepAliveExists(final int id) {
        return this.keepAliveTimes.containsKey(id);
    }
    
    public long getKeepAliveTime(final int id) {
        final long time = this.keepAliveTimes.get(id);
        this.keepAliveTimes.remove(id);
        return time;
    }
    
    public void addKeepAliveTime(final int id) {
        this.keepAliveTimes.put(id, System.currentTimeMillis());
    }
    
    public void setPing(final long ping) {
        this.ping = ping;
    }
    
    public long getPing() {
        return this.ping;
    }
    
    public void setLoginTime(final long loginTime) {
        this.loginTime = loginTime;
    }
    
    public long getLoginTime() {
        return this.loginTime;
    }
    
    public double getLastDamage() {
        return this.lastDamage;
    }
    
    public void setLastDamage(final double lastDamage) {
        this.lastDamage = lastDamage;
    }
    
    public boolean isLunar() {
        return this.lunar;
    }
    
    public boolean isPassedMCBrand() {
        return this.passedMCBrand;
    }
    
    public void setLunar(final boolean lunar) {
        this.lunar = lunar;
    }
    
    public void setPassedMCBrand(final boolean passedMCBrand) {
        this.passedMCBrand = passedMCBrand;
    }
    
    public long getLastJump() {
        return this.lastJump;
    }
    
    public void setLastJump(final long lastJump) {
        this.lastJump = lastJump;
    }
    
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    public void setCancelled(final boolean cancelled) {
        this.cancelled = cancelled;
    }
}

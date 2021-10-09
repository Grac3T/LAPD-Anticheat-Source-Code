// 
// Decompiled by Procyon v0.5.36
// 

package us.blockgame.lapd.packet;

import us.blockgame.lapd.check.dep.RotationDep;
import us.blockgame.lapd.check.dep.PacketDep;
import java.util.concurrent.atomic.AtomicBoolean;
import net.minecraft.server.v1_8_R3.PacketPlayInCloseWindow;
import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand;
import net.minecraft.server.v1_8_R3.PacketPlayInBlockPlace;
import us.blockgame.lapd.position.CustomRotation;
import us.blockgame.lapd.position.CustomPosition;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;
import net.minecraft.server.v1_8_R3.PacketPlayInBlockDig;
import net.minecraft.server.v1_8_R3.PacketPlayInEntityAction;
import net.minecraft.server.v1_8_R3.PacketPlayInCustomPayload;
import net.minecraft.server.v1_8_R3.PacketPlayInKeepAlive;
import net.minecraft.server.v1_8_R3.PacketPlayInUseEntity;
import java.lang.reflect.Field;
import net.minecraft.server.v1_8_R3.Entity;
import us.blockgame.lapd.player.PlayerData;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityVelocity;
import us.blockgame.lapd.util.CustomLocation;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntity;
import net.minecraft.server.v1_8_R3.PacketPlayOutPosition;
import net.minecraft.server.v1_8_R3.PacketPlayOutKeepAlive;
import net.minecraft.server.v1_8_R3.Packet;
import us.blockgame.lapd.LAPD;
import io.netty.channel.ChannelPromise;
import io.netty.channel.ChannelHandlerContext;
import org.bukkit.entity.Player;
import io.netty.channel.ChannelDuplexHandler;

public class PacketHandler extends ChannelDuplexHandler
{
    private final Player player;
    
    public PacketHandler(final Player player) {
        this.player = player;
    }
    
    public void write(final ChannelHandlerContext context, final Object object, final ChannelPromise channelPromise) throws Exception {
        try {
            if (LAPD.getInstance().getData(this.player) == null) {
                return;
            }
            final PlayerData playerData = LAPD.getInstance().getData(this.player);
            final Packet packet = (Packet)object;
            if (packet == null) {
                return;
            }
            if (packet instanceof PacketPlayOutKeepAlive) {
                playerData.setLastSendKeepAlive(System.currentTimeMillis());
            }
            if (packet instanceof PacketPlayOutPosition && !playerData.isTpuknown() && System.currentTimeMillis() - playerData.getLongInTimePassed() > 1000L) {
                playerData.setLastMoveCancel(System.currentTimeMillis());
                playerData.setTeleportTicks(50);
            }
            if (packet instanceof PacketPlayOutEntity) {
                final PacketPlayOutEntity packetPlayOutEntity = (PacketPlayOutEntity)packet;
                final Entity targetEntity = ((CraftPlayer)this.player).getHandle().getWorld().a((int)this.a(packetPlayOutEntity));
                if (targetEntity instanceof EntityPlayer) {
                    final Player target = (Player)targetEntity.getBukkitEntity();
                    final CustomLocation customLocation = playerData.getLastPlayerPacket(target.getUniqueId(), 1);
                    if (customLocation != null) {
                        final double x = this.b(packetPlayOutEntity) / 32.0;
                        final double y = this.c(packetPlayOutEntity) / 32.0;
                        final double z = this.d(packetPlayOutEntity) / 32.0;
                        float yaw = this.e(packetPlayOutEntity) * 360.0f / 256.0f;
                        float pitch = this.f(packetPlayOutEntity) * 360.0f / 256.0f;
                        if (!this.h(packetPlayOutEntity)) {
                            yaw = customLocation.getYaw();
                            pitch = customLocation.getPitch();
                        }
                        playerData.addPlayerPacket(this.player.getUniqueId(), new CustomLocation(customLocation.getX() + x, customLocation.getY() + y, customLocation.getZ() + z, yaw, pitch));
                    }
                }
            }
            if (packet instanceof PacketPlayOutEntityVelocity) {
                final PacketPlayOutEntityVelocity entityVelocity = (PacketPlayOutEntityVelocity)packet;
                if (this.a(entityVelocity) == this.player.getEntityId()) {
                    final double x2 = Math.abs(this.b(entityVelocity) / 8000.0);
                    final double y2 = this.c(entityVelocity) / 8000.0;
                    final double z2 = Math.abs(this.d(entityVelocity) / 8000.0);
                    if (x2 > 0.0 || z2 > 0.0) {
                        playerData.setVelocityH((int)(((x2 + z2) / 2.0 + 2.0) * 15.0));
                    }
                    if (y2 > 0.0) {
                        playerData.setVelocityV((int)(Math.pow(y2 + 2.0, 2.0) * 5.0));
                        if (playerData.isOnGround() && this.player.getLocation().getY() % 1.0 == 0.0) {
                            playerData.setVelocityX(x2);
                            playerData.setVelocityY(y2);
                            playerData.setVelocityZ(z2);
                            playerData.setLastVelocity(System.currentTimeMillis());
                            playerData.setAttackedSinceVelocity(false);
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            this.player.kickPlayer("Packet Error #1");
        }
        super.write(context, object, channelPromise);
    }
    
    private int a(final PacketPlayOutEntityVelocity packetPlayOutEntityVelocity) {
        try {
            final Field field = packetPlayOutEntityVelocity.getClass().getField("a");
            field.setAccessible(true);
            return field.getInt(packetPlayOutEntityVelocity);
        }
        catch (Exception e) {
            return 0;
        }
    }
    
    private int b(final PacketPlayOutEntityVelocity packetPlayOutEntityVelocity) {
        try {
            final Field field = packetPlayOutEntityVelocity.getClass().getField("b");
            field.setAccessible(true);
            return field.getInt(packetPlayOutEntityVelocity);
        }
        catch (Exception e) {
            return 0;
        }
    }
    
    private int c(final PacketPlayOutEntityVelocity packetPlayOutEntityVelocity) {
        try {
            final Field field = packetPlayOutEntityVelocity.getClass().getField("c");
            field.setAccessible(true);
            return field.getInt(packetPlayOutEntityVelocity);
        }
        catch (Exception e) {
            return 0;
        }
    }
    
    private int d(final PacketPlayOutEntityVelocity packetPlayOutEntityVelocity) {
        try {
            final Field field = packetPlayOutEntityVelocity.getClass().getField("d");
            field.setAccessible(true);
            return field.getInt(packetPlayOutEntityVelocity);
        }
        catch (Exception e) {
            return 0;
        }
    }
    
    private byte a(final PacketPlayOutEntity packetPlayOutEntityVelocity) {
        try {
            final Field field = packetPlayOutEntityVelocity.getClass().getField("a");
            field.setAccessible(true);
            return field.getByte(packetPlayOutEntityVelocity);
        }
        catch (Exception e) {
            return 0;
        }
    }
    
    private byte b(final PacketPlayOutEntity packetPlayOutEntityVelocity) {
        try {
            final Field field = packetPlayOutEntityVelocity.getClass().getField("b");
            field.setAccessible(true);
            return field.getByte(packetPlayOutEntityVelocity);
        }
        catch (Exception e) {
            return 0;
        }
    }
    
    private byte c(final PacketPlayOutEntity packetPlayOutEntityVelocity) {
        try {
            final Field field = packetPlayOutEntityVelocity.getClass().getField("c");
            field.setAccessible(true);
            return field.getByte(packetPlayOutEntityVelocity);
        }
        catch (Exception e) {
            return 0;
        }
    }
    
    private byte d(final PacketPlayOutEntity packetPlayOutEntityVelocity) {
        try {
            final Field field = packetPlayOutEntityVelocity.getClass().getField("d");
            field.setAccessible(true);
            return field.getByte(packetPlayOutEntityVelocity);
        }
        catch (Exception e) {
            return 0;
        }
    }
    
    private byte e(final PacketPlayOutEntity packetPlayOutEntityVelocity) {
        try {
            final Field field = packetPlayOutEntityVelocity.getClass().getField("e");
            field.setAccessible(true);
            return field.getByte(packetPlayOutEntityVelocity);
        }
        catch (Exception e) {
            return 0;
        }
    }
    
    private byte f(final PacketPlayOutEntity packetPlayOutEntityVelocity) {
        try {
            final Field field = packetPlayOutEntityVelocity.getClass().getField("f");
            field.setAccessible(true);
            return field.getByte(packetPlayOutEntityVelocity);
        }
        catch (Exception e) {
            return 0;
        }
    }
    
    private boolean h(final PacketPlayOutEntity packetPlayOutEntityVelocity) {
        try {
            final Field field = packetPlayOutEntityVelocity.getClass().getField("f");
            field.setAccessible(true);
            return field.getBoolean(packetPlayOutEntityVelocity);
        }
        catch (Exception e) {
            return false;
        }
    }
    
    public void channelRead(final ChannelHandlerContext context, final Object object) throws Exception {
        try {
            if (LAPD.getInstance().getData(this.player) == null) {
                return;
            }
            final PlayerData playerData = LAPD.getInstance().getData(this.player);
            final Packet packet = (Packet)object;
            if (packet instanceof PacketPlayInUseEntity && ((PacketPlayInUseEntity)packet).a() == PacketPlayInUseEntity.EnumEntityUseAction.ATTACK) {
                playerData.setLastAttack(System.currentTimeMillis());
            }
            else if (packet instanceof PacketPlayInKeepAlive) {
                final PacketPlayInKeepAlive packetPlayInKeepAlive = (PacketPlayInKeepAlive)packet;
                playerData.setLastRecivedKeepAlive(System.currentTimeMillis());
                final int id = packetPlayInKeepAlive.a();
                if (playerData.keepAliveExists(id)) {
                    playerData.setPing(System.currentTimeMillis() - playerData.getKeepAliveTime(id));
                }
            }
            else if (packet instanceof PacketPlayInCustomPayload) {
                final PacketPlayInCustomPayload payload = (PacketPlayInCustomPayload)packet;
                final String clientName = payload.a();
                playerData.setClientType(clientName);
            }
            else if (packet instanceof PacketPlayInEntityAction) {
                final PacketPlayInEntityAction.EnumPlayerAction actionType = ((PacketPlayInEntityAction)packet).b();
                if (actionType == PacketPlayInEntityAction.EnumPlayerAction.START_SPRINTING) {
                    playerData.setSprinting(true);
                }
                if (actionType == PacketPlayInEntityAction.EnumPlayerAction.STOP_SPRINTING) {
                    playerData.setSprinting(false);
                }
            }
            else if (packet instanceof PacketPlayInBlockDig) {
                final PacketPlayInBlockDig.EnumPlayerDigType action = ((PacketPlayInBlockDig)packet).c();
                if (action == PacketPlayInBlockDig.EnumPlayerDigType.START_DESTROY_BLOCK) {
                    playerData.setDigging(true);
                }
                else if (action == PacketPlayInBlockDig.EnumPlayerDigType.STOP_DESTROY_BLOCK || action == PacketPlayInBlockDig.EnumPlayerDigType.ABORT_DESTROY_BLOCK) {
                    playerData.setDigging(false);
                }
            }
            else if (packet instanceof PacketPlayInFlying) {
                final PacketPlayInFlying flying = (PacketPlayInFlying)packet;
                final CustomLocation customLocation = new CustomLocation(flying.a(), flying.b(), flying.c(), flying.d(), flying.e());
                final CustomLocation lastLocation = playerData.getLastCustomLocation();
                if (lastLocation != null) {
                    if (!flying.g()) {
                        customLocation.setX(lastLocation.getX());
                        customLocation.setY(lastLocation.getY());
                        customLocation.setZ(lastLocation.getZ());
                    }
                    if (!flying.h()) {
                        customLocation.setYaw(lastLocation.getYaw());
                        customLocation.setPitch(lastLocation.getPitch());
                    }
                    if (System.currentTimeMillis() - lastLocation.getTimestamp() > 110L) {
                        playerData.setLastDelayedMovePacket(System.currentTimeMillis());
                    }
                }
                playerData.setLastCustomLocation(customLocation);
                final double x = flying.a();
                final double y = flying.b();
                final double z = flying.c();
                final float yaw = flying.d();
                final float pitch = flying.e();
                final CustomPosition lastPosition = playerData.getPosition();
                final long now = System.currentTimeMillis();
                playerData.setLastFlyingPacket(now);
                playerData.setTpuknown(false);
                if (this.player.isSprinting()) {
                    playerData.setSprintTicks(playerData.getSprintTicks() + 1);
                }
                else {
                    playerData.setSprintTicks(0);
                }
                if (now - playerData.getLastMovePacket() > 110L) {
                    playerData.setLastDelayedMovePacket(now);
                }
                if (playerData.getTeleportTicks() > 0) {
                    playerData.setTeleportTicks(playerData.getTeleportTicks() - 1);
                }
                if (playerData.getTeleportTicks() < 0) {
                    playerData.setTeleportTicks(0);
                }
                if (playerData.getRespawnTicks() > 0) {
                    playerData.setRespawnTicks(playerData.getRespawnTicks() - 1);
                }
                if (playerData.getRespawnTicks() < 0) {
                    playerData.setRespawnTicks(0);
                }
                if (System.currentTimeMillis() - playerData.getLastMovePacket() > 200L) {
                    playerData.setLastLag(System.currentTimeMillis());
                }
                if (flying.g()) {
                    playerData.setStandTicks(0);
                    final CustomPosition position = new CustomPosition(x, z);
                    playerData.getPastPositions().add(position);
                    playerData.setPosition(position);
                    playerData.setPreviousPosition(lastPosition);
                }
                else {
                    playerData.setStandTicks(playerData.getStandTicks() + 1);
                }
                if (flying.h()) {
                    final CustomRotation rotation = new CustomRotation(yaw, pitch);
                    if (playerData.getLastRotation() != null) {
                        playerData.getRotationDepList().forEach(check -> check.onRotation(this.player, rotation, playerData.getLastRotation()));
                    }
                    playerData.setLastRotation(rotation);
                }
                playerData.setLastMovePacket(now);
                playerData.setPlaced(false);
            }
            else if (packet instanceof PacketPlayInBlockPlace) {
                playerData.setPlaced(true);
                playerData.setLastPlace(System.currentTimeMillis());
            }
            else if (packet instanceof PacketPlayInClientCommand) {
                final PacketPlayInClientCommand p = (PacketPlayInClientCommand)packet;
                if (p.a().equals((Object)PacketPlayInClientCommand.EnumClientCommand.OPEN_INVENTORY_ACHIEVEMENT)) {
                    playerData.setInvOpened(true);
                }
            }
            else if (packet instanceof PacketPlayInCloseWindow) {
                playerData.setInvOpened(false);
            }
            final AtomicBoolean cancel = new AtomicBoolean(false);
            playerData.getPacketDepList().stream().filter(check -> !check.onPacketReceive(this.player, packet)).forEach(check -> cancel.set(true));
            if (cancel.get()) {
                return;
            }
        }
        catch (Exception e) {
            this.player.kickPlayer("Packet Error #2");
        }
        super.channelRead(context, object);
    }
}

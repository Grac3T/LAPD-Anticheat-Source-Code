// 
// Decompiled by Procyon v0.5.36
// 

package us.blockgame.lapd.util.entity;

import org.bukkit.block.Block;
import java.util.Set;
import java.util.ArrayList;
import org.bukkit.entity.Entity;
import java.util.Iterator;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.Material;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import org.bukkit.Location;
import org.bukkit.util.Vector;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class PlayerUtil
{
    public static int getPing(final Player player) {
        return ((CraftPlayer)player).getHandle().ping;
    }
    
    public static double[] getOffsetsOffCursor(final Player player, final EntityPlayer entity) {
        final Location entityLoc = entity.getBukkitEntity().getLocation().add(0.0, entity.getBukkitEntity().getLocation().getY() + 0.8, 0.0);
        final Location playerLoc = player.getLocation().add(0.0, player.getEyeHeight(), 0.0);
        final Vector playerRotation = new Vector(playerLoc.getYaw(), playerLoc.getPitch(), 0.0f);
        final Vector expectedRotation = getRotation(playerLoc, entityLoc);
        final double deltaYaw = clamp180(playerRotation.getX() - expectedRotation.getX());
        final double deltaPitch = clamp180(playerRotation.getY() - expectedRotation.getY());
        final double horizontalDistance = getHorizontalDistance(playerLoc, entityLoc);
        final double distance = getDistance3D(playerLoc, entityLoc);
        final double offsetX = deltaYaw * horizontalDistance * distance;
        final double offsetY = deltaPitch * Math.abs(Math.sqrt(entityLoc.getY() - playerLoc.getY())) * distance;
        return new double[] { Math.abs(offsetX), Math.abs(offsetY) };
    }
    
    public static double clamp180(double dub) {
        dub %= 360.0;
        if (dub >= 180.0) {
            dub -= 360.0;
        }
        if (dub < -180.0) {
            dub += 360.0;
        }
        return dub;
    }
    
    public static void sendTitle(final Player player, final String title, final String subtitle, final int fadeIn, final int stay, final int fadeOut) {
        final CraftPlayer craftplayer = (CraftPlayer)player;
        final PlayerConnection connection = craftplayer.getHandle().playerConnection;
        final IChatBaseComponent titleJSON = IChatBaseComponent.ChatSerializer.a("{'text': '" + title + "'}");
        final IChatBaseComponent subtitleJSON = IChatBaseComponent.ChatSerializer.a("{'text': '" + subtitle + "'}");
        final PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, titleJSON, fadeIn, stay, fadeOut);
        final PacketPlayOutTitle subtitlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, subtitleJSON);
        connection.sendPacket((Packet)titlePacket);
        connection.sendPacket((Packet)subtitlePacket);
    }
    
    public static boolean isReallyOnground(final Player p) {
        final Location l = p.getLocation();
        final int x = l.getBlockX();
        final int y = l.getBlockY();
        final int z = l.getBlockZ();
        final Location b = new Location(p.getWorld(), (double)x, (double)(y - 1), (double)z);
        return p.isOnGround() && b.getBlock().getType() != Material.AIR && b.getBlock().getType() != Material.WEB && !b.getBlock().isLiquid();
    }
    
    public static Vector getRotation(final Location one, final Location two) {
        final double dx = two.getX() - one.getX();
        final double dy = two.getY() - one.getY();
        final double dz = two.getZ() - one.getZ();
        final double distanceXZ = Math.sqrt(dx * dx + dz * dz);
        final float yaw = (float)(Math.atan2(dz, dx) * 180.0 / 3.141592653589793) - 90.0f;
        final float pitch = (float)(-Math.atan2(dy, distanceXZ) * 180.0 / 3.141592653589793);
        return new Vector(yaw, pitch, 0.0f);
    }
    
    public static double getHorizontalDistance(final Location to, final Location from) {
        final double x = Math.abs(Math.abs(to.getX()) - Math.abs(from.getX()));
        final double z = Math.abs(Math.abs(to.getZ()) - Math.abs(from.getZ()));
        return Math.sqrt(x * x + z * z);
    }
    
    public static double getDistance3D(final Location one, final Location two) {
        double toReturn = 0.0;
        final double xSqr = (two.getX() - one.getX()) * (two.getX() - one.getX());
        final double ySqr = (two.getY() - one.getY()) * (two.getY() - one.getY());
        final double zSqr = (two.getZ() - one.getZ()) * (two.getZ() - one.getZ());
        final double sqrt = Math.sqrt(xSqr + ySqr + zSqr);
        toReturn = Math.abs(sqrt);
        return toReturn;
    }
    
    public static int getPotionEffectLevel(final Player player, final PotionEffectType pet) {
        for (final PotionEffect pe : player.getActivePotionEffects()) {
            if (!pe.getType().getName().equals(pet.getName())) {
                continue;
            }
            return pe.getAmplifier() + 1;
        }
        return 0;
    }
    
    public static Entity getNearestEntityInSight(final Player player, final int range) {
        final ArrayList<Entity> entities = (ArrayList<Entity>)player.getNearbyEntities((double)range, (double)range, (double)range);
        final ArrayList<Block> sightBlock = (ArrayList<Block>)player.getLineOfSight((Set)null, range);
        final ArrayList<Location> sight = new ArrayList<Location>();
        for (int i = 0; i < sightBlock.size(); ++i) {
            sight.add(sightBlock.get(i).getLocation());
        }
        for (int i = 0; i < sight.size(); ++i) {
            for (int k = 0; k < entities.size(); ++k) {
                if (Math.abs(entities.get(k).getLocation().getX() - sight.get(i).getX()) < 1.3 && Math.abs(entities.get(k).getLocation().getY() - sight.get(i).getY()) < 1.5 && Math.abs(entities.get(k).getLocation().getZ() - sight.get(i).getZ()) < 1.3) {
                    return entities.get(k);
                }
            }
        }
        return null;
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package us.blockgame.lapd.util.math;

import org.bukkit.Location;
import org.bukkit.util.Vector;
import java.util.Iterator;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import java.util.Random;

public class MathUtil
{
    public static final Random RANDOM;
    
    public static float getDistanceBetweenAngles(final float angle1, final float angle2) {
        float distance = Math.abs(angle1 - angle2) % 360.0f;
        if (distance > 180.0f) {
            distance = 360.0f - distance;
        }
        return distance;
    }
    
    public static float[] getRotations(final Player one, final CraftPlayer two) {
        final double diffX = two.getLocation().getX() - one.getLocation().getX();
        final double diffZ = two.getLocation().getZ() - one.getLocation().getZ();
        final double dist = Math.sqrt(diffX * diffX + diffZ * diffZ);
        final float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / 3.141592653589793) - 90.0f;
        final float pitch = (float)(-Math.atan2(1.0, dist) * 180.0 / 3.141592653589793);
        return new float[] { yaw, pitch };
    }
    
    public static double average(final Iterable<? extends Number> numbers) {
        double total = 0.0;
        int i = 0;
        for (final Number number : numbers) {
            total += number.doubleValue();
            ++i;
        }
        return total / i;
    }
    
    public static boolean isInteger(final String num) {
        try {
            Integer.parseInt(num);
            return true;
        }
        catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static float yawTo180F(float flub) {
        if ((flub %= 360.0f) >= 180.0f) {
            flub -= 360.0f;
        }
        if (flub < -180.0f) {
            flub += 360.0f;
        }
        return flub;
    }
    
    public static double[] getOffsetFromEntity(final Player player, final CraftPlayer entity) {
        final double yawOffset = Math.abs(yawTo180F(player.getEyeLocation().getYaw()) - yawTo180F(getRotations(player, entity)[0]));
        final double pitchOffset = Math.abs(Math.abs(player.getEyeLocation().getPitch()) - Math.abs(getRotations(player, entity)[1]));
        return new double[] { yawOffset, pitchOffset };
    }
    
    public static double deviationSquared(final Iterable<? extends Number> iterable) {
        double variance = 0.0;
        int count = 0;
        for (final Number anIterable1 : iterable) {
            variance += anIterable1.doubleValue();
            ++count;
        }
        final double deviation = variance / count;
        double stdDev = 0.0;
        for (final Number anIterable2 : iterable) {
            stdDev += Math.pow(anIterable2.doubleValue() - deviation, 2.0);
        }
        return stdDev / (count - 1);
    }
    
    public static double deviation(final Iterable<? extends Number> iterable) {
        return Math.sqrt(deviationSquared(iterable));
    }
    
    public static Vector getLocationInFrontOfLocation(final Player player, final double v) {
        return player.getLocation().toVector().add(player.getLocation().getDirection().setY(0).normalize().multiply(v + 0.3));
    }
    
    public static double getHorizontalDistance(final Location from, final Location to) {
        final double deltaX = to.getX() - from.getX();
        final double deltaZ = to.getZ() - from.getZ();
        return Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
    }
    
    public static int pingFormula(final long ping) {
        return (int)Math.ceil(ping / 50.0);
    }
    
    static {
        RANDOM = new Random();
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package us.blockgame.lapd.util.server;

import java.text.DecimalFormat;
import net.minecraft.server.v1_8_R3.MinecraftServer;

public class ServerUtil implements Runnable
{
    public static int TICK_COUNT;
    public static long[] TICKS;
    public static long LAST_TICK;
    
    public static double getTPS() {
        return getTPS(100);
    }
    
    public static double getTPS(final int ticks) {
        if (ServerUtil.TICK_COUNT < ticks) {
            return 20.0;
        }
        final int target = (ServerUtil.TICK_COUNT - 1 - ticks) % ServerUtil.TICKS.length;
        final long elapsed = System.currentTimeMillis() - ServerUtil.TICKS[target];
        return ticks / (elapsed / 1000.0);
    }
    
    public static long getElapsed(final int tickID) {
        if (ServerUtil.TICK_COUNT - tickID >= ServerUtil.TICKS.length) {}
        final long time = ServerUtil.TICKS[tickID % ServerUtil.TICKS.length];
        return System.currentTimeMillis() - time;
    }
    
    public static String getFixedTPS() {
        final double tps = MinecraftServer.getServer().tps1.getAverage();
        String fixedTPS = new DecimalFormat(".##").format(tps);
        if (tps > 20.0) {
            fixedTPS = "20.0";
        }
        return fixedTPS;
    }
    
    @Override
    public void run() {
        ServerUtil.TICKS[ServerUtil.TICK_COUNT % ServerUtil.TICKS.length] = System.currentTimeMillis();
        ++ServerUtil.TICK_COUNT;
    }
    
    static {
        ServerUtil.TICK_COUNT = 0;
        ServerUtil.TICKS = new long[600];
        ServerUtil.LAST_TICK = 0L;
    }
}

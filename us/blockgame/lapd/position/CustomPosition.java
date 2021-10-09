// 
// Decompiled by Procyon v0.5.36
// 

package us.blockgame.lapd.position;

public class CustomPosition
{
    private final double minX;
    private final double centerX;
    private final double maxX;
    private final double minZ;
    private final double centerZ;
    private final double maxZ;
    private final long timeStamp;
    
    public CustomPosition(final double x, final double z) {
        this.timeStamp = System.currentTimeMillis();
        this.minX = x - 0.3;
        this.centerX = x;
        this.maxX = x + 0.3;
        this.minZ = z - 0.3;
        this.centerZ = z;
        this.maxZ = z + 0.3;
    }
    
    public double getDistanceSquared(final CustomPosition hitbox) {
        final double dx = Math.min(Math.abs(hitbox.centerX - this.minX), Math.abs(hitbox.centerX - this.maxX));
        final double dz = Math.min(Math.abs(hitbox.centerZ - this.minZ), Math.abs(hitbox.centerZ - this.maxZ));
        return dx * dx + dz * dz;
    }
    
    public double getMinX() {
        return this.minX;
    }
    
    public double getCenterX() {
        return this.centerX;
    }
    
    public double getMaxX() {
        return this.maxX;
    }
    
    public double getMinZ() {
        return this.minZ;
    }
    
    public double getCenterZ() {
        return this.centerZ;
    }
    
    public double getMaxZ() {
        return this.maxZ;
    }
    
    public long getTimeStamp() {
        return this.timeStamp;
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package us.blockgame.lapd.util;

public class Velocity
{
    private final double horizontal;
    private final double vertical;
    private final long creationTime;
    
    public Velocity(final double horizontal, final double vertical) {
        this.creationTime = System.currentTimeMillis();
        this.horizontal = horizontal;
        this.vertical = vertical;
    }
    
    public double getHorizontal() {
        return this.horizontal;
    }
    
    public double getVertical() {
        return this.vertical;
    }
    
    public long getCreationTime() {
        return this.creationTime;
    }
}

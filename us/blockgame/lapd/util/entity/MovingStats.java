// 
// Decompiled by Procyon v0.5.36
// 

package us.blockgame.lapd.util.entity;

import java.io.Serializable;

public class MovingStats implements Serializable
{
    private final double[] elements;
    private int currentElement;
    private int windowCount;
    private double variance;
    
    public MovingStats(final int size) {
        this.elements = new double[size];
        this.variance = size * 2.5;
        for (int i = 0, len = this.elements.length; i < len; ++i) {
            this.elements[i] = size * 2.5 / size;
        }
    }
    
    public void add(double sum) {
        sum /= this.elements.length;
        this.variance -= this.elements[this.currentElement];
        this.variance += sum;
        this.elements[this.currentElement] = sum;
        this.currentElement = (this.currentElement + 1) % this.elements.length;
    }
    
    public double getStdDev(final double required) {
        final double stdDev = Math.sqrt(this.variance);
        if (stdDev >= required) {
            if (this.windowCount > 0) {
                this.windowCount = 0;
            }
            return required;
        }
        if (++this.windowCount > this.elements.length) {
            return stdDev;
        }
        return Double.NaN;
    }
}

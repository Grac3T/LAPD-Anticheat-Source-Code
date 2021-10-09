// 
// Decompiled by Procyon v0.5.36
// 

package us.blockgame.lapd.util.math;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Deque;

public class Interval
{
    private int x;
    private int max;
    private Deque<Integer> valList;
    
    public Interval(final int x, final int max) {
        this.valList = new LinkedList<Integer>();
        this.x = x;
        this.max = max;
        this.valList.add(x);
        this.valList.stream().filter(val -> val == 0);
    }
    
    public Interval(final Deque x, final int max) {
        this.valList = new LinkedList<Integer>();
        this.valList = (Deque<Integer>)x;
        this.max = max;
    }
    
    public double average() {
        return this.valList.stream().mapToDouble(Integer::doubleValue).average().orElse(0.0);
    }
    
    public double frequency(final double freq) {
        return Collections.frequency(this.valList, freq);
    }
    
    public long distinct() {
        return this.valList.stream().distinct().count();
    }
    
    public double max() {
        return this.valList.stream().mapToDouble(Integer::doubleValue).max().orElse(0.0);
    }
    
    public double min() {
        return this.valList.stream().mapToDouble(Integer::doubleValue).min().orElse(0.0);
    }
    
    public void add(final int x) {
        this.valList.add(x);
        if (this.valList.size() > this.max) {
            this.valList.remove(this.valList.size() - 1);
        }
    }
    
    public void clear() {
        this.valList.clear();
    }
    
    public void clearIfMax() {
        if (this.valList.size() == this.max) {
            this.clear();
        }
    }
}

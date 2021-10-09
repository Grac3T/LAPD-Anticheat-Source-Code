// 
// Decompiled by Procyon v0.5.36
// 

package us.blockgame.lapd.util.entity;

public class MouseFilter
{
    public static float field_76336_a;
    public static float field_76334_b;
    public static float field_76335_c;
    
    public static float smooth(float p_76333_1_, final float p_76333_2_) {
        MouseFilter.field_76336_a += p_76333_1_;
        p_76333_1_ = (MouseFilter.field_76336_a - MouseFilter.field_76334_b) * p_76333_2_;
        MouseFilter.field_76335_c += (p_76333_1_ - MouseFilter.field_76335_c) * 0.5f;
        if ((p_76333_1_ > 0.0f && p_76333_1_ > MouseFilter.field_76335_c) || (p_76333_1_ < 0.0f && p_76333_1_ < MouseFilter.field_76335_c)) {
            p_76333_1_ = MouseFilter.field_76335_c;
        }
        MouseFilter.field_76334_b += p_76333_1_;
        return p_76333_1_;
    }
    
    public void reset() {
        MouseFilter.field_76336_a = 0.0f;
        MouseFilter.field_76334_b = 0.0f;
        MouseFilter.field_76335_c = 0.0f;
    }
}

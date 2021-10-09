// 
// Decompiled by Procyon v0.5.36
// 

package us.blockgame.lapd.check.dep;

import us.blockgame.lapd.position.CustomRotation;
import org.bukkit.entity.Player;

public interface RotationDep
{
    void onRotation(final Player p0, final CustomRotation p1, final CustomRotation p2);
}

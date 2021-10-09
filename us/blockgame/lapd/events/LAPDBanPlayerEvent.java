// 
// Decompiled by Procyon v0.5.36
// 

package us.blockgame.lapd.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Event;

public class LAPDBanPlayerEvent extends Event
{
    private static HandlerList handlerList;
    private Player player;
    
    public LAPDBanPlayerEvent(final Player player) {
        this.player = player;
    }
    
    public Player getPlayer() {
        return this.player;
    }
    
    public static HandlerList getHandlerList() {
        return LAPDBanPlayerEvent.handlerList;
    }
    
    public HandlerList getHandlers() {
        return LAPDBanPlayerEvent.handlerList;
    }
    
    static {
        LAPDBanPlayerEvent.handlerList = new HandlerList();
    }
}

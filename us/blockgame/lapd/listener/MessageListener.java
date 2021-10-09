// 
// Decompiled by Procyon v0.5.36
// 

package us.blockgame.lapd.listener;

import us.blockgame.lapd.player.PlayerData;
import us.blockgame.lapd.LAPD;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class MessageListener implements PluginMessageListener
{
    public void onPluginMessageReceived(final String s, final Player player, final byte[] bytes) {
        final PlayerData playerData = LAPD.getInstance().getData(player);
        final String clientName = new String(bytes);
        if (clientName.equalsIgnoreCase("vanilla")) {
            playerData.setPassedMCBrand(true);
        }
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package us.blockgame.lapd.util.server;

import java.net.NetworkInterface;
import java.net.InetAddress;
import java.io.InputStream;
import java.net.URLConnection;
import us.blockgame.lapd.LAPD;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class WebUtil
{
    public static boolean isAuthed() {
        boolean authed = false;
        try {
            final String id = getHWID();
            final URL url = new URL("http://blockgame.us/api/lapd.txt");
            final URLConnection urlConnection = url.openConnection();
            final InputStream inputStream = urlConnection.getInputStream();
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                authed = line.contains(id);
                if (authed) {
                    if (line.contains("patrone")) {
                        LAPD.patroneMode = true;
                        break;
                    }
                    break;
                }
            }
            if (!authed) {
                System.out.println("[LAPD] Please ask abHi to whitelist this ID: " + id);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return authed;
    }
    
    public static String getHWID() throws Exception {
        final InetAddress ip = InetAddress.getLocalHost();
        final NetworkInterface network = NetworkInterface.getByInetAddress(ip);
        final byte[] mac = network.getHardwareAddress();
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mac.length; ++i) {
            sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
        }
        return sb.toString();
    }
}

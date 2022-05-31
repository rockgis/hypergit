package com.goodmit.hypergit.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class NetUtil {
    public static String getLocalAddress() {
        Enumeration<NetworkInterface> ifaces;
        try {
            ifaces = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            return null;
        }
        while (ifaces.hasMoreElements()) {
            NetworkInterface iface = ifaces.nextElement();
            Enumeration<InetAddress> addresses = iface.getInetAddresses();
            while( addresses.hasMoreElements() )
            {
                InetAddress addr = addresses.nextElement();
                if( addr instanceof Inet4Address && !addr.isLoopbackAddress() )
                {
                    return addr.getHostAddress();
                }
            }
        }
        return null;
    }
}

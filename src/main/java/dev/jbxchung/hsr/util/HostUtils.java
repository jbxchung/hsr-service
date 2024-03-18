package dev.jbxchung.hsr.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class HostUtils {
    public static Boolean isLocalhost() {
        try {
            String host = InetAddress.getLocalHost().getHostName();
            return !host.contains("rpi");
        } catch (UnknownHostException e) {
            return false;
        }
    }
}

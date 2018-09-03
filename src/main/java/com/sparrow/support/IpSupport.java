package com.sparrow.support;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * @author TCLDUSER
 */
public interface IpSupport {
    String getLocalIp();

    String getLocalHostName();
}

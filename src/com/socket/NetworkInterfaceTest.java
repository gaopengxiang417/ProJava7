package com.socket;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * User: wangchen.gpx
 * Date: 13-1-17
 * Time: 下午4:20
 */
public class NetworkInterfaceTest {
    public static void main(String[] args) throws SocketException {
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while (networkInterfaces.hasMoreElements()) {
            System.out.println("-------------------**************------------------");
            NetworkInterface anInterface = networkInterfaces.nextElement();
            System.out.println(anInterface.getDisplayName() + ": ");
            System.out.println(anInterface.getMTU());
            System.out.println(anInterface.getIndex());
            System.out.println(anInterface.getName());
            System.out.println(anInterface.getParent());
            System.out.println(anInterface.supportsMulticast());
            System.out.println(anInterface.isVirtual());
            System.out.println(anInterface.isUp());
            System.out.println(anInterface.isLoopback());
            System.out.println(anInterface.isPointToPoint());
            System.out.println(anInterface.supportsMulticast());
//            System.out.println(new String(anInterface.getHardwareAddress()));
            Enumeration<InetAddress> inetAddresses = anInterface.getInetAddresses();
            System.out.println("inetaddress.........");
            while (inetAddresses.hasMoreElements()) {
                InetAddress inetAddress = inetAddresses.nextElement();
                System.out.println(inetAddress.getHostName()+":"+new String(inetAddress.getAddress()));
            }
            System.out.println("---------------------end--------------");
        }
    }
}

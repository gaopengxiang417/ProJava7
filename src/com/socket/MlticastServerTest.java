package com.socket;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.MulticastChannel;
import java.util.Date;

/**
 * User: wangchen.gpx
 * Date: 13-1-17
 * Time: 下午4:35
 */
public class MlticastServerTest {
    public static void main(String[] args) {
        String interhost = "225.4.5.6";
        int port = 5555;
        ByteBuffer byteBuffer;
        //首先打开channel
        try(DatagramChannel datagramChannel = DatagramChannel.open(StandardProtocolFamily.INET)) {
            //判断是否打开
            if (datagramChannel.isOpen()) {
                //配置选项
                NetworkInterface eth3 = NetworkInterface.getByName("net1");
                datagramChannel.setOption(StandardSocketOptions.IP_MULTICAST_IF, eth3);
                datagramChannel.setOption(StandardSocketOptions.SO_REUSEADDR, true);

                //绑定本地地址
                datagramChannel.bind(new InetSocketAddress(5555));
                //开始写入数据
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        byteBuffer = ByteBuffer.wrap(new Date().toString().getBytes());
                        System.out.println("start sending message time.......");
                        datagramChannel.send(byteBuffer, new InetSocketAddress(interhost, port));
                        byteBuffer.flip();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

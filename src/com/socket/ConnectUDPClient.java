package com.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardProtocolFamily;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
 * User: wangchen.gpx
 * Date: 13-1-16
 * Time: 下午4:05
 */
public class ConnectUDPClient {
    public static void main(String[] args) {
        CharsetDecoder decoder = Charset.defaultCharset().newDecoder();
        ByteBuffer echoBuffer = ByteBuffer.allocate(1024);
        ByteBuffer byteBuffer = ByteBuffer.wrap("hello world this is frist ".getBytes());

        String address = "127.0.0.1";
        int port = 4444;

        //打开
        try(DatagramChannel datagramChannel = DatagramChannel.open(StandardProtocolFamily.INET)) {
            //判断是否打开
            if (datagramChannel.isOpen()) {
                //配置
                datagramChannel.setOption(StandardSocketOptions.SO_RCVBUF, 4 * 1024);
                datagramChannel.setOption(StandardSocketOptions.SO_SNDBUF, 4 * 1024);

                //建立连接
                datagramChannel.connect(new InetSocketAddress(address, port));
                //判断
                if (datagramChannel.isConnected()) {
                    //发送消息
                    int write = datagramChannel.write(byteBuffer);
                    System.out.println("write count : "+write);

                    //接受消息
                    while (true) {
                        int read = datagramChannel.read(echoBuffer);
                        System.out.println("read count "+read);

                        echoBuffer.flip();
                        System.out.println(decoder.decode(echoBuffer).toString());
                        echoBuffer.clear();
                        break;
                    }
                }

            }
       } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

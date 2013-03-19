package com.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.StandardProtocolFamily;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Random;

/**
 * User: wangchen.gpx
 * Date: 13-1-16
 * Time: 下午3:44
 */
public class TotalUDPClientTest {
    public static void main(String[] args) {
        CharsetDecoder decoder = Charset.defaultCharset().newDecoder();
        ByteBuffer byteBuffer = ByteBuffer.allocate(456);
        //首先打开一个channel
        try(DatagramChannel datagramChannel = DatagramChannel.open(StandardProtocolFamily.INET)) {
            //判断是否打开
            if (datagramChannel.isOpen()) {
                //配置
                datagramChannel.setOption(StandardSocketOptions.SO_RCVBUF, 4 * 1024);
                datagramChannel.setOption(StandardSocketOptions.SO_SNDBUF, 4 * 1024);


                //发送消息
                int send = datagramChannel.send(ByteBuffer.wrap("this is first time".getBytes()), new InetSocketAddress("127.0.0.1", 4444));
                System.out.println("first send bytes is :"+send);

                //接受消息
                while (true) {
                    SocketAddress receive = datagramChannel.receive(byteBuffer);
                    byteBuffer.flip();
                    System.out.println(decoder.decode(byteBuffer).toString());

                    //随机写
                    int i = new Random().nextInt(100);
                    if (i == 50) {
                        System.out.println("find the number : ");
                        break;
                    }
                    byteBuffer.clear();
                    datagramChannel.send(byteBuffer.put(("send number is " + i).getBytes()), receive);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

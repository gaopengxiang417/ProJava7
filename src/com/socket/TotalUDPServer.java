package com.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.StandardProtocolFamily;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.Charset;

/**
 * User: wangchen.gpx
 * Date: 13-1-16
 * Time: 下午3:34
 */
public class TotalUDPServer {
    public static void main(String[] args) {
        //字节数组的最大长度
        final  int MAX_LENGTH = 65535;
        //缓存的buffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(MAX_LENGTH);

        //开始构建channel
        try(DatagramChannel datagramChannel = DatagramChannel.open(StandardProtocolFamily.INET)) {
            //判断是否打开
            if (datagramChannel.isOpen()) {
                //配置选项
                datagramChannel.setOption(StandardSocketOptions.SO_RCVBUF, 4 * 1024);
                datagramChannel.setOption(StandardSocketOptions.SO_SNDBUF, 4 * 1024);

                //绑定本地地址
                datagramChannel.bind(new InetSocketAddress("127.0.0.1", 4444));
                System.out.println("server channel is bind to :"+datagramChannel.getLocalAddress());
                System.out.println("server side is begin to echo...........");
                while (true) {
                    //开始接受消息
                    SocketAddress socketAddress = datagramChannel.receive(byteBuffer);
                    byteBuffer.flip();
                    //在本地输出
                    System.out.println(Charset.defaultCharset().decode(byteBuffer).toString());
                    //开始回写
                    datagramChannel.send(byteBuffer, socketAddress);
                    byteBuffer.clear();
                    System.out.println("send message end............");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

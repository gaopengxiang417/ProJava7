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

/**
 * User: wangchen.gpx
 * Date: 13-1-16
 * Time: 下午1:55
 */
public class MyUDPServerTest {
    public static void main(String[] args) {
        //首先创建一个协议
        try(DatagramChannel datagramChannel = DatagramChannel.open(StandardProtocolFamily.INET)) {
            //判断链接是否打开
            if (datagramChannel.isOpen()) {
                //获取支持的选项
                System.out.println(datagramChannel.supportedOptions());
                //设置他的选项
                datagramChannel.setOption(StandardSocketOptions.SO_RCVBUF, 2 * 1024);
                datagramChannel.setOption(StandardSocketOptions.SO_SNDBUF, 2 * 1024);

                //检查本地的address
                System.out.println(datagramChannel.getLocalAddress());
                //服务端的绑定
                datagramChannel.bind(new InetSocketAddress("127.0.0.1", 5555));
                System.out.println(datagramChannel.getLocalAddress());

                //判断远程地址
                System.out.println(datagramChannel.getRemoteAddress());

                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                //接受消息
                while (true) {
                    SocketAddress socketAddress = datagramChannel.receive(byteBuffer);

                    byteBuffer.flip();
                    System.out.println(Charset.defaultCharset().newDecoder().decode(byteBuffer).toString());

                    //写回去
                    datagramChannel.send(byteBuffer, socketAddress);
                    byteBuffer.clear();
                }
            }
      } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

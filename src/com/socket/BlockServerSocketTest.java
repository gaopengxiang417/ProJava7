package com.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * User: wangchen.gpx
 * Date: 13-1-11
 * Time: 上午8:02
 */
public class BlockServerSocketTest {
    public static void main(String[] args) throws IOException {
        //首先创建一个新的channel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        System.out.println(serverSocketChannel.isOpen());

        System.out.println(serverSocketChannel.isBlocking());
        //设置他的阻设
        serverSocketChannel.configureBlocking(true);
        System.out.println(serverSocketChannel.isBlocking());
        System.out.println(serverSocketChannel.supportedOptions());

        //设置可选配置
        serverSocketChannel.setOption(StandardSocketOptions.SO_REUSEADDR , true);
        serverSocketChannel.setOption(StandardSocketOptions.SO_RCVBUF, 1024);

        //绑定地址
        System.out.println(serverSocketChannel.getLocalAddress());
        String ip = "127.0.0.1";
        int port = 5555;
        serverSocketChannel.bind(new InetSocketAddress(ip, port));
        System.out.println(serverSocketChannel.getLocalAddress());

        //接受连接
        SocketChannel accept = serverSocketChannel.accept();

        System.out.println(accept.getRemoteAddress());

        //接受数据
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int count = 0;
        while ((count = accept.read(buffer)) != -1) {
            buffer.flip();
            accept.write(buffer);
            if (buffer.hasRemaining()) {
                buffer.compact();
            }else{
                buffer.clear();
            }
        }

        System.out.println(accept.socket().isInputShutdown());
        System.out.println(accept.socket().isOutputShutdown());

        //close资源
        accept.close();
        serverSocketChannel.close();
    }
}

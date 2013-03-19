package com.gao.asynchronizedchannel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketOption;
import java.net.StandardSocketOptions;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * User: gaopengxiang
 * Date: 12-4-21
 * Time: 下午1:59
 */
public class SocketTest {
    public static void main(String[] args) {
        try {
            AsynchronousServerSocketChannel serverSocketChannel = AsynchronousServerSocketChannel.open();
            System.out.println(serverSocketChannel.isOpen());

            serverSocketChannel.setOption(StandardSocketOptions.SO_RCVBUF, 1024 * 4);
            serverSocketChannel.setOption(StandardSocketOptions.SO_REUSEADDR, true);

            Set<SocketOption<?>> socketOptions = serverSocketChannel.supportedOptions();
            System.out.println(socketOptions);

            System.out.println(serverSocketChannel.getLocalAddress());

            String ip = "127.0.0.1";
            String port = "3333";
            serverSocketChannel.bind(new InetSocketAddress(ip, Integer.parseInt(port)));

            System.out.println(serverSocketChannel.getLocalAddress());

            Future<AsynchronousSocketChannel> future = serverSocketChannel.accept();
            AsynchronousSocketChannel asynchronousSocketChannel = future.get();

            System.out.println(asynchronousSocketChannel.getRemoteAddress());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}

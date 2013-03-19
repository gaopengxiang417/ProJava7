package com.asynchronous;

import sun.org.mozilla.javascript.internal.ast.AstNode;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketOption;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * User: wangchen.gpx
 * Date: 13-1-19
 * Time: 下午2:20
 */
public class ServerSocketFultureTest {
    public static void main(String[] args) {
        //创建一个serversocket
        try(AsynchronousServerSocketChannel serverSocketChannel = AsynchronousServerSocketChannel.open()) {
            //判断是否打开
            if (serverSocketChannel.isOpen()) {
                //判断支持那些optiosn
                Set<SocketOption<?>> socketOptions = serverSocketChannel.supportedOptions();
                System.out.println(socketOptions);
                //设置options
                serverSocketChannel.setOption(StandardSocketOptions.SO_RCVBUF, 4 * 1024);
                serverSocketChannel.setOption(StandardSocketOptions.SO_REUSEADDR, true);

                //绑定本地地址
                System.out.println(serverSocketChannel.getLocalAddress());
                serverSocketChannel.bind(new InetSocketAddress("127.0.0.1", 5555));
                System.out.println(serverSocketChannel.getLocalAddress());

                //接受连接
                Future<AsynchronousSocketChannel> future = serverSocketChannel.accept();
                while (!future.isDone()) {
                    System.out.println("waiting for complete.....");
                }
                AsynchronousSocketChannel asynchronousSocketChannel = future.get();

                //获取远程地址
                SocketAddress remoteAddress = asynchronousSocketChannel.getRemoteAddress();
                System.out.println(remoteAddress);
                //读取数据
                ByteBuffer byteBuffer = ByteBuffer.allocate(2048);
                int count = 0;
                while ((count = asynchronousSocketChannel.read(byteBuffer).get()) != -1) {
                    System.out.println("read count :" + count);
                    byteBuffer.flip();
                    Integer integer = asynchronousSocketChannel.write(byteBuffer).get();
                    System.out.println("write count :"+integer);
                    if (byteBuffer.hasRemaining()) {
                        byteBuffer.compact();
                    }else{
                        byteBuffer.clear();
                    }
                }
                asynchronousSocketChannel.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}

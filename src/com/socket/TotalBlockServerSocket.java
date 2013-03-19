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
 * Time: 上午8:25
 */
public class TotalBlockServerSocket {
    public static void main(String[] args) {
        String address = "127.0.0.1";
        int port = 7777;
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
        //打开channel
        try(ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
            //判断是否打开
            if (serverSocketChannel.isOpen()) {
                //配置主色模式
                serverSocketChannel.configureBlocking(true);
                //设置可选配置
                serverSocketChannel.setOption(StandardSocketOptions.SO_RCVBUF, 1024);
                serverSocketChannel.setOption(StandardSocketOptions.SO_REUSEADDR, true);
                //设置本地的属性地址
                serverSocketChannel.bind(new InetSocketAddress(address, port));

                //开始监听
                System.out.println("start to listen........");
                while (true) {
                    try(SocketChannel socketChannel = serverSocketChannel.accept()){
                        System.out.println("incoming address is :" + socketChannel.getRemoteAddress());

                        //开始回写
                        int count = 0;
                        while ((count = socketChannel.read(byteBuffer)) != -1) {
                            byteBuffer.flip();
                            socketChannel.write(byteBuffer);
                            if (byteBuffer.hasRemaining()) {
                                byteBuffer.compact();
                            }else{
                                byteBuffer.clear();
                            }
                        }
                    }
                }
            }else{
                System.out.println("server socket channel is not open!!!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

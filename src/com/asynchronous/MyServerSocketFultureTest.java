package com.asynchronous;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketOption;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.Charset;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * User: wangchen.gpx
 * Date: 13-1-19
 * Time: 下午4:23
 */
public class MyServerSocketFultureTest {
    public static void main(String[] args) {
        //首先打开通道
        try (AsynchronousServerSocketChannel serverSocketChannel = AsynchronousServerSocketChannel.open()) {
            //是否打开
            if (serverSocketChannel.isOpen()) {
                //支持的optiosn
                Set<SocketOption<?>> socketOptions = serverSocketChannel.supportedOptions();
                System.out.println(socketOptions);

                //设置
                serverSocketChannel.setOption(StandardSocketOptions.SO_RCVBUF, 4 * 1024);
                serverSocketChannel.setOption(StandardSocketOptions.SO_REUSEADDR, true);

                //绑定idzhi
                System.out.println(serverSocketChannel.getLocalAddress());
                serverSocketChannel.bind(new InetSocketAddress("127.0.0.1", 5555));
                System.out.println(serverSocketChannel.getLocalAddress());
                while (true) {
                    //e接受
                    Future<AsynchronousSocketChannel> future = serverSocketChannel.accept();
                    /*while (!future.isDone()) {
                        System.out.println("waiting for accept.........");
//                        Thread.sleep(1000);
                    }*/

                    //channel
                    try (AsynchronousSocketChannel socketChannel = future.get()) {
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        //开始读取内容
                        while (socketChannel.read(byteBuffer).get() != -1) {
                            byteBuffer.flip();

                            //输出到本地
                            System.out.println(Charset.defaultCharset().decode(byteBuffer));

                            //回写
                            byteBuffer.flip();
                            Future<Integer> result = socketChannel.write(byteBuffer);
                            /*if (result.isDone()) {
                                System.out.println("success write count :" + result.get());
                            }*/
                            if (byteBuffer.hasRemaining()) {
                                byteBuffer.compact();
                            }else{
                                byteBuffer.clear();
                            }
                        }
                        System.out.println("success read:"+socketChannel.getRemoteAddress());
                    }
                }
            } else {
                System.out.println("channel is not open");
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

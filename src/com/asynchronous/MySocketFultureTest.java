package com.asynchronous;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketOption;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * User: wangchen.gpx
 * Date: 13-1-19
 * Time: 下午4:30
 */
public class MySocketFultureTest {
    public static void main(String[] args) {
        //配置一些用到的东西
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        ByteBuffer buffer = ByteBuffer.wrap("hello world chiand".getBytes());
        ByteBuffer randomBuffer;
        CharsetDecoder charsetDecoder = Charset.defaultCharset().newDecoder();
        //打开通道
        try(AsynchronousSocketChannel socketChannel = AsynchronousSocketChannel.open()) {
            //是否打开
            if (socketChannel.isOpen()) {
                //options
                Set<SocketOption<?>> socketOptions = socketChannel.supportedOptions();
                System.out.println(socketOptions);

                //配置
                socketChannel.setOption(StandardSocketOptions.SO_RCVBUF, 4 * 1024);
                socketChannel.setOption(StandardSocketOptions.SO_SNDBUF, 4 * 1024);
                socketChannel.setOption(StandardSocketOptions.SO_KEEPALIVE, true);

                System.out.println(socketChannel.getLocalAddress());
                //进行连接
                Future<Void> connect = socketChannel.connect(new InetSocketAddress("127.0.0.1", 5555));
                /*while (!connect.isDone()) {
                    System.out.println("is connecting newwork....");
                }*/
                if (connect.get() == null) {
                    //开始写消息
                    Future<Integer> write = socketChannel.write(buffer);
                    /*if (!write.isDone()) {
                        System.out.println("not write yet.....");
                    }*/
                   /* if (write.isDone()) {*/
                        System.out.println("write count :"+write.get());

                        //读取
                        while (socketChannel.read(byteBuffer).get() != -1) {
                            byteBuffer.flip();
                            System.out.println(charsetDecoder.decode(byteBuffer));

                            if (byteBuffer.hasRemaining()) {
                                byteBuffer.compact();
                            }else{
                                byteBuffer.clear();
                            }

                            int i = new Random().nextInt(100);
                            if (i == 60) {
                                System.out.println("find number 60");
                                break;
                            }else{
                                randomBuffer = ByteBuffer.wrap(("number is " + i).getBytes());
                                socketChannel.write(randomBuffer);
                            }
                        }
                    /*}*//**/
                }
            }else{
                System.out.println("channel is not open.......");
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

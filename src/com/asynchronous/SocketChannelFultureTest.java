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
 * Time: 下午4:06
 */
public class SocketChannelFultureTest {
    public static void main(String[] args) {
        try(AsynchronousSocketChannel socketChannel = AsynchronousSocketChannel.open()) {
            if (socketChannel.isOpen()) {

                Set<SocketOption<?>> socketOptions = socketChannel.supportedOptions();
                System.out.println(socketOptions);

                socketChannel.setOption(StandardSocketOptions.SO_RCVBUF, 4 * 1024);
                socketChannel.setOption(StandardSocketOptions.SO_REUSEADDR, true);
                socketChannel.setOption(StandardSocketOptions.SO_SNDBUF, 4 * 1024);

                socketChannel.connect(new InetSocketAddress("127.0.0.1", 5555));


                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                ByteBuffer buffer = ByteBuffer.wrap("welcome to here".getBytes());
                ByteBuffer randomBuffer;
                CharsetDecoder charsetDecoder = Charset.defaultCharset().newDecoder();

                Future<Integer> future = socketChannel.write(buffer);
                while (!future.isDone()) {
                    System.out.println("echo to wait write completeed.........");
                }

                System.out.println("write count is:"+future.get());

                while (socketChannel.read(byteBuffer).get() != -1) {
                    byteBuffer.flip();
                    System.out.println("read data is :"+charsetDecoder.decode(byteBuffer));

                    if (byteBuffer.hasRemaining()) {
                        byteBuffer.compact();
                    }else{
                        byteBuffer.clear();
                    }

                    int i = new Random().nextInt(100);
                    if (i == 50) {
                        System.out.println("find the number ......");
                        break;
                    }
                    randomBuffer = ByteBuffer.wrap(("echo number is :" + i).getBytes());
                    socketChannel.write(randomBuffer);
                }
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

package com.asynchronous;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.Charset;
import java.util.concurrent.*;

/**
 * User: wangchen.gpx
 * Date: 13-1-19
 * Time: 下午2:40
 */
public class ServerSocketFultureExecutorServiceTest {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool(Executors.defaultThreadFactory());
        try(AsynchronousServerSocketChannel serverSocketChannel = AsynchronousServerSocketChannel.open()) {
            if (serverSocketChannel.isOpen()) {
                serverSocketChannel.setOption(StandardSocketOptions.SO_REUSEADDR, true);
                serverSocketChannel.setOption(StandardSocketOptions.SO_RCVBUF, 1024);

                serverSocketChannel.bind(new InetSocketAddress(5555));

                while (true) {
                    Future<AsynchronousSocketChannel> future = serverSocketChannel.accept();
                    System.out.println("waitting for accept........");
                    try(AsynchronousSocketChannel socketChannel = future.get()) {

                        Callable<String> callable = new Callable<String>() {
                            @Override
                            public String call() throws Exception {
                                SocketAddress remoteAddress = socketChannel.getRemoteAddress();
                                System.out.println("accept remote address is :" + remoteAddress);
                                ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
                                while (socketChannel.read(byteBuffer).get() != -1) {
                                    byteBuffer.flip();
                                    System.out.println(Charset.defaultCharset().decode(byteBuffer).toString());

                                    socketChannel.write(byteBuffer);

                                    if (byteBuffer.hasRemaining()) {
                                        byteBuffer.compact();
                                    } else {
                                        byteBuffer.clear();
                                    }
                                }
                                socketChannel.close();
                                return remoteAddress.toString();
                            }
                        };

                        executorService.submit(callable);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

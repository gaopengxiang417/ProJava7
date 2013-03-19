package com.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Random;

/**
 * User: wangchen.gpx
 * Date: 13-1-11
 * Time: 上午8:53
 */
public class TotalBlockSocketChannel {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
        ByteBuffer buffer = ByteBuffer.wrap("hello world".getBytes());
        Charset charset = Charset.defaultCharset();
        CharsetDecoder charsetDecoder = charset.newDecoder();
        CharBuffer charBuffer;
        ByteBuffer randomBuffer ;
        String ip = "127.0.0.1";
        int port = 7777;
        try(SocketChannel socketChannel = SocketChannel.open()) {
            if (socketChannel.isOpen()) {

                socketChannel.configureBlocking(true);
                socketChannel.setOption(StandardSocketOptions.SO_LINGER, 7);
                socketChannel.setOption(StandardSocketOptions.SO_REUSEADDR, true);
                socketChannel.setOption(StandardSocketOptions.SO_KEEPALIVE, true);
                socketChannel.setOption(StandardSocketOptions.SO_RCVBUF, 1024 * 8);
                socketChannel.setOption(StandardSocketOptions.SO_SNDBUF, 1024 * 8);

                socketChannel.connect(new InetSocketAddress(ip, port));

                if (socketChannel.isConnected()) {
                    socketChannel.write(buffer);
                    int count = 0;
                    while ((count = socketChannel.read(byteBuffer)) != -1) {
                        byteBuffer.flip();
                        charBuffer = charsetDecoder.decode(byteBuffer);
                        System.out.println(charBuffer.toString());

                        if (byteBuffer.hasRemaining()) {
                            byteBuffer.compact();
                        }else{
                            byteBuffer.clear();
                        }

                        int i = new Random().nextInt(100);
                        if (i == 50) {
                            System.out.println("find number !!");
                            break;
                        }else {
                            randomBuffer = ByteBuffer.wrap("this number is :".concat(String.valueOf(i)).getBytes());
                            socketChannel.write(randomBuffer);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

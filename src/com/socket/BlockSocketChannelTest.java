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
 * Time: 上午8:33
 */
public class BlockSocketChannelTest {
    public static void main(String[] args) throws IOException {
        //构建channel
        SocketChannel socketChannel = SocketChannel.open();
        System.out.println(socketChannel.isOpen());

        //配置模式
        System.out.println(socketChannel.isBlocking());
        socketChannel.configureBlocking(true);
        System.out.println(socketChannel.isBlocking());

        //设置可选属性
        System.out.println(socketChannel.supportedOptions());
        socketChannel.setOption(StandardSocketOptions.SO_RCVBUF, 1024 * 8);
        socketChannel.setOption(StandardSocketOptions.SO_SNDBUF, 1024 * 8);
        socketChannel.setOption(StandardSocketOptions.SO_KEEPALIVE, true);
        socketChannel.setOption(StandardSocketOptions.SO_LINGER, 5);

        //建立连接
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 7777));
        System.out.println(socketChannel.isConnected());

        //开始写入数据
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
        ByteBuffer buffer = ByteBuffer.wrap("hello world".getBytes());

        CharsetDecoder charsetDecoder = Charset.defaultCharset().newDecoder();
        CharBuffer charBuffer;

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
                System.out.println("50 is generated !");
                break;
            }else {
                socketChannel.write(ByteBuffer.wrap("random number ".concat(String.valueOf(i)).getBytes()));
            }
        }
        socketChannel.close();
    }
}

package com.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

/**
 * User: wangchen.gpx
 * Date: 13-1-16
 * Time: 上午11:17
 */
public class NonBlockClientTest {
    public static void main(String[] args) {
        int defautport = 5555;
        String localaddress = "127.0.0.1";
        ByteBuffer byteBuffer = ByteBuffer.allocate(2 * 1024);
        ByteBuffer random;
        CharBuffer charBuffer;

        Charset charset = Charset.defaultCharset();
        CharsetDecoder charsetDecoder = charset.newDecoder();

        try(Selector selector = Selector.open();
        SocketChannel socketChannel = SocketChannel.open()) {

            if ((selector.isOpen()) && (socketChannel.isOpen())) {
                socketChannel.configureBlocking(false);
                socketChannel.setOption(StandardSocketOptions.SO_RCVBUF, 128 * 1024);
                socketChannel.setOption(StandardSocketOptions.SO_SNDBUF, 128 * 1024);
                socketChannel.setOption(StandardSocketOptions.SO_KEEPALIVE, true);

                //register
                socketChannel.register(selector, SelectionKey.OP_CONNECT);

                socketChannel.connect(new InetSocketAddress(localaddress, defautport));

                System.out.println("lcoal host :" + socketChannel.getLocalAddress());

                while (selector.select(1000) > 0) {
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        iterator.remove();
                        try(SocketChannel keySocketChannel = (SocketChannel) key.channel()) {
                            if (key.isConnectable()) {
                                System.out.println("i am connected");
                                if (keySocketChannel.isConnectionPending()) {
                                    keySocketChannel.finishConnect();
                                }

                                while (socketChannel.read(byteBuffer) != -1) {
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
                                        System.out.println("find 50 ");
                                        break;
                                    }else {
                                        keySocketChannel.write(ByteBuffer.wrap(String.valueOf(i).getBytes()));
                                        try {
                                            Thread.sleep(1000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

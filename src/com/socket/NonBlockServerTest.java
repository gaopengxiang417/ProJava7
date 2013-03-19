package com.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * User: wangchen.gpx
 * Date: 13-1-16
 * Time: 上午10:47
 */
public class NonBlockServerTest {
    private Map<SocketChannel, List<byte[]>> trackMap = new java.util.HashMap();
    private ByteBuffer byteBuffer = ByteBuffer.allocate(2 * 1024);

    private void startEchoServer() {
        //设置默认的监视端口
        int defaultPort = 5555;
        //新建selector和channel
        try (Selector selector = Selector.open();
             ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
            //检查是否正常打开
            if ((selector.isOpen()) && (serverSocketChannel.isOpen())) {

                //channel的配置
                serverSocketChannel.configureBlocking(false);
                serverSocketChannel.setOption(StandardSocketOptions.SO_RCVBUF, 256 * 1024);
                serverSocketChannel.setOption(StandardSocketOptions.SO_REUSEADDR, true);

                //绑定端口
                serverSocketChannel.bind(new InetSocketAddress(defaultPort));

                //注册当前的selector
                serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

                System.out.println("start waiting for connection...");

                while (true) {
                    //监听调用的事件
                    selector.select();

                    //查找出调用的client的key
                    Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
                    while (keys.hasNext()) {
                        SelectionKey next = keys.next();

                        keys.remove();

                        if (!next.isValid()) {
                            continue;
                        }
                        if (next.isAcceptable()) {
                            acceptOp(next, selector);
                        } else if (next.isReadable()) {
                            this.readOp(next);
                        } else if (next.isWritable()) {
                            this.writeOp(next);
                        }
                    }
                }
            } else {
                System.out.println("server socket channel or selector is not open....");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeOp(SelectionKey next) throws IOException {
        SocketChannel channel = (SocketChannel) next.channel();
        List<byte[]> bytes = trackMap.get(channel);
        Iterator<byte[]> iterator = bytes.iterator();
        while (iterator.hasNext()) {
            byte[] next1 = iterator.next();
            iterator.remove();
            channel.write(ByteBuffer.wrap(next1));
        }
        next.interestOps(SelectionKey.OP_READ);

    }

    private void readOp(SelectionKey next) throws IOException {
        SocketChannel channel = (SocketChannel) next.channel();
        byteBuffer.clear();
        int count = -1;

        try {
            count = channel.read(byteBuffer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (count == -1) {
            this.trackMap.remove(channel);
            System.out.println("connection by " + channel.getRemoteAddress());
            channel.close();
            next.cancel();
            return;
        }
        byte[] data = new byte[count];
        System.arraycopy(byteBuffer.array(), 0, data, 0, count);
        System.out.println(new String(data, "utf-8"));

        //wirte to client
        doEchoJob(next, data);
    }

    private void doEchoJob(SelectionKey key, byte[] data) {
        SocketChannel channel = (SocketChannel) key.channel();
        List<byte[]> bytes = trackMap.get(channel);
        bytes.add(data);
        key.interestOps(SelectionKey.OP_WRITE);
    }

    private void acceptOp(SelectionKey next, Selector selector) throws IOException {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) next.channel();
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);

        System.out.println("incoming connection is :" + socketChannel.getRemoteAddress());

        //wirte date
        socketChannel.write(ByteBuffer.wrap("hello world".getBytes()));
        //
        trackMap.put(socketChannel, new ArrayList<byte[]>());
        socketChannel.register(selector, SelectionKey.OP_READ);
    }


    public static void main(String[] args) {
        NonBlockServerTest nonBlockServerTest = new NonBlockServerTest();
        nonBlockServerTest.startEchoServer();
    }
}

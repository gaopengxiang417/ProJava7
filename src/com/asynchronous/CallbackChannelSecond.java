package com.asynchronous;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * User: wangchen.gpx
 * Date: 13-1-19
 * Time: 上午11:40
 */
public class CallbackChannelSecond {
    static Thread current;
    public static void main(String[] args) {
        Path path = FileSystems.getDefault().getPath("D:/a.txt");
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        final String encoding = System.getProperty("file.encoding");
        try(AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.READ)) {
            if (fileChannel.isOpen()) {
                fileChannel.read(byteBuffer,100,byteBuffer,new CompletionHandler<Integer, ByteBuffer>() {
                    @Override
                    public void completed(Integer result, ByteBuffer attachment) {
                        System.out.println("byte readed : "+result);
                        attachment.flip();
                        System.out.println(Charset.forName(encoding).decode(attachment));
                        attachment.clear();
                        current.interrupt();
                    }

                    @Override
                    public void failed(Throwable exc, ByteBuffer attachment) {
                        System.out.println(attachment);
                        System.out.println("error :"+exc);
                        current.interrupt();
                    }
                });

                System.out.println("waiting to dead.........");
                Thread.sleep(1000);
                try {
                    current.join();
                } catch (InterruptedException e) {
                    System.out.println("eee "+e);
                }
                System.out.println("end...........");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

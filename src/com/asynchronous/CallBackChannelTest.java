package com.asynchronous;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * User: wangchen.gpx
 * Date: 13-1-19
 * Time: 上午11:32
 */
public class CallBackChannelTest {
    static Thread current;
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(102);
        Path path = FileSystems.getDefault().getPath("D:/a.txt");
        try(AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.READ)) {
            if (fileChannel.isOpen()) {
                current = Thread.currentThread();
                fileChannel.read(byteBuffer,34,"read data",new CompletionHandler<Integer, String>() {
                    @Override
                    public void completed(Integer result, String attachment) {
                        System.out.println("successful read data........");
                        System.out.println(attachment);
                        System.out.println("count is "+result);
                        current.interrupt();
                    }

                    @Override
                    public void failed(Throwable exc, String attachment) {
                        System.out.println("error occure.......");
                        System.out.println(attachment);
                        System.out.println(exc.getStackTrace());
                        current.interrupt();
                    }
                });
                System.out.println("waiting for end.............");
                try {
                    current.join();
                } catch (InterruptedException e) {
                    System.out.println("waitintg to die....."+e.getMessage());
                }

                System.out.println("already close the channel ..........");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

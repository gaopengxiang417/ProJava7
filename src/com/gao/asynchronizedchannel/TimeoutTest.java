package com.gao.asynchronizedchannel;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * User: gaopengxiang
 * Date: 12-4-21
 * Time: 上午10:04
 */
public class TimeoutTest {
    public static void main(String[] args) {
        Path path = FileSystems.getDefault().getPath("D:/", "ss.txt");

        ByteBuffer byteBuffer = ByteBuffer.allocate(1000);
        Future<Integer> future = null;
        int readCount = 0;
        try (AsynchronousFileChannel asynchronousFileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.READ)) {

            future = asynchronousFileChannel.read(byteBuffer, 0);

            readCount = future.get(1, TimeUnit.NANOSECONDS);

            if (future.isDone()) {
                System.out.println("the result is available");
                System.out.println(readCount);
            }
        } catch (Exception e) {
            if (e instanceof TimeoutException) {
                if(future != null) {
                    future.cancel(true);
                    System.out.println("the result is not available...");
                    System.out.println(future.isCancelled());
                    System.out.println(readCount);
                }else{
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}

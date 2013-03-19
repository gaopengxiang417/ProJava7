package com.asynchronous;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * User: wangchen.gpx
 * Date: 13-1-19
 * Time: 上午10:49
 */
public class AsynchronousFileChannelTimeout {

    private static Future<Integer> future;

    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        Path path = Paths.get("D:/a.txt");
        try (AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.READ)) {
            //
            if (fileChannel.isOpen()) {
                future = fileChannel.read(byteBuffer, 0);

                Integer integer = future.get(1, TimeUnit.NANOSECONDS);
                if (future.isDone()) {
                    System.out.println("ok is done");
                }
            }
        } catch (Exception e) {
            if (e instanceof TimeoutException) {
                future.cancel(true);
            }
        }
    }
}

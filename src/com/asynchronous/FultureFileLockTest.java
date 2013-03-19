package com.asynchronous;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.FileLock;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * User: wangchen.gpx
 * Date: 13-1-19
 * Time: 上午11:52
 */
public class FultureFileLockTest {
    public static void main(String[] args) {
        Path path = FileSystems.getDefault().getPath("D:/a.txt");
        ByteBuffer bytebuffer = ByteBuffer.wrap("secone line".getBytes());
        try(AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.WRITE
        )) {
            //
            System.out.println("waiting for the file to be locked...");
            Future<FileLock> future = fileChannel.lock();

            FileLock lock = future.get();
            if (lock.isValid()) {
                Future<Integer> future1 = fileChannel.write(bytebuffer, 0);
                System.out.println("start writting to file......");

                while (!future1.isDone()) {
                    System.out.println("echo to writting");
                }
                System.out.println(future1.get());

                lock.release();
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

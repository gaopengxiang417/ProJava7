package com.gao.asynchronizedchannel;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * User: gaopengxiang
 * Date: 12-4-21
 * Time: 上午9:57
 */
public class AsynchronizedFileChannelWrite {
    public static void main(String[] args) {
        Path path = Paths.get("D:/", "ss.txt");

        ByteBuffer byteBuffer = ByteBuffer.wrap("asynchronized file channel test,this is a test for write method".getBytes());

        try(AsynchronousFileChannel asynchronousFileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.WRITE)) {
            Future<Integer> future = asynchronousFileChannel.write(byteBuffer, asynchronousFileChannel.size());

            while (!future.isDone()) {
                System.out.println("this write is running,waiting......");
            }

            System.out.println(future.isDone() + ":"+future.get());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}

package com.gao.asynchronizedchannel;


import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.channels.FileLock;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.WRITE;
/**
 * User: gaopengxiang
 * Date: 12-4-21
 * Time: 上午10:33
 */
public class LockTest {
    public static void main(String[] args) {
        Path path = FileSystems.getDefault().getPath("D:/ss.txt");

        ByteBuffer byteBuffer = ByteBuffer.wrap("newbean 7.1 is support java7 and html5 and css3 and j2ee6 and so on...".getBytes());

        try(AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path,WRITE)) {

            fileChannel.lock("gaopengxiang", new CompletionHandler<FileLock, String>() {
                @Override
                public void completed(FileLock result, String attachment) {
                    if (result.isValid()) {
                        System.out.println("try to release lock...");

                        try {
                            result.release();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void failed(Throwable exc, String attachment) {
                    System.out.println(attachment);
                    System.out.println("error is:"+exc.getMessage());
                }
            });

//            AnsiConsole.systemInstall();;
//            AnsiConsole.out().println("gaopengxiang");
            /*Future<FileLock> future = fileChannel.lock();

            FileLock fileLock = future.get();

            if (fileLock.isValid()) {
                Future<Integer> future1 = fileChannel.write(byteBuffer, 0);

                while (!future1.isDone()) {
                    System.out.println("WRITE in running...");
                }

                System.out.println(future1.isDone());
                System.out.println(future1.get());

                fileLock.release();
            }*/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

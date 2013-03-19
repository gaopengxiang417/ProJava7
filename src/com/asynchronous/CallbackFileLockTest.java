package com.asynchronous;

import java.io.IOException;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.channels.FileLock;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * User: wangchen.gpx
 * Date: 13-1-19
 * Time: 下午1:36
 */
public class CallbackFileLockTest {
    static Thread current;
    public static void main(String[] args) {
        Path path = FileSystems.getDefault().getPath("D:/a.txt");
        try(AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.READ,
                StandardOpenOption.WRITE)) {
            current = Thread.currentThread();
            if (fileChannel.isOpen()) {
                fileChannel.lock("attchement statu :",new CompletionHandler<FileLock, String>() {
                    @Override
                    public void completed(FileLock result, String attachment) {
                        System.out.println("complete lock file ....");
                        System.out.println(attachment +result.isValid());
                        if (result.isValid()) {
                            try {
                                result.release();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            current.interrupt();
                        }
                    }

                    @Override
                    public void failed(Throwable exc, String attachment) {
                        System.out.println("error:"+attachment);
                        System.out.println(exc);
                        current.interrupt();
                    }
                });
                System.out.println("waiting for lock end ........");
                current.join();
                System.out.println("finally end..........");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

package com.randomaccess;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * User: wangchen.gpx
 * Date: 13-1-10
 * Time: 上午9:04
 */
public class LockFileChannel {
    public static void main(String[] args) {
        Path path = FileSystems.getDefault().getPath("D:/a.txt");
        try(FileChannel channel = FileChannel.open(path, StandardOpenOption.READ,
                StandardOpenOption.WRITE)) {
            //
            ByteBuffer buffer = ByteBuffer.wrap("lock file test".getBytes());

            //lock
            FileLock lock = channel.lock();

            try {
                channel.tryLock();
            } catch (OverlappingFileLockException e) {
                System.out.println(e);
            } catch (IOException e) {
                System.out.println(e);
            }

            if (lock.isValid()) {
                Thread.sleep(1000);

                channel.position(channel.size());
                int write = channel.write(buffer);
                System.out.println(write);
                Thread.sleep(1000);
            }
            lock.release();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

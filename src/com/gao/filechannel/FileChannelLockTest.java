package com.gao.filechannel;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * User: gaopengxiang
 * Date: 12-4-20
 * Time: 上午9:23
 */
public class FileChannelLockTest {
    public static void main(String[] args) {
        Path path = FileSystems.getDefault().getPath("D:/", "ss.txt");
        ByteBuffer byteBuffer = ByteBuffer.wrap("second".getBytes());

        try(FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.WRITE,StandardOpenOption.READ)) {
            FileLock lock = fileChannel.lock();

            if(lock.isValid()){
                System.out.println("writing to locked file....");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                fileChannel.position(0);
                fileChannel.write(byteBuffer);

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                lock.release();
                System.out.println("\n realase locked file");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

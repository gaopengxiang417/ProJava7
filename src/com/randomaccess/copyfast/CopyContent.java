package com.randomaccess.copyfast;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * User: wangchen.gpx
 * Date: 13-1-10
 * Time: 上午9:55
 */
public class CopyContent {
    public static void main(String[] args) {
        Path path = FileSystems.getDefault().getPath("D:/a.txt");
        Path path1 = FileSystems.getDefault().getPath("D:/u.txt");
        try(SeekableByteChannel channel = Files.newByteChannel(path, StandardOpenOption.READ);
        SeekableByteChannel channel1 = Files.newByteChannel(path1, StandardOpenOption.WRITE,StandardOpenOption.CREATE
                ,StandardOpenOption.APPEND
        )) {
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(2048);
            for (int t = 0; t < 100; t++) {
                int count = 0;
                while ((count = channel.read(byteBuffer)) != -1) {
                    byteBuffer.flip();
                    channel1.write(byteBuffer);
                    byteBuffer.clear();
                }
                channel.position(0);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

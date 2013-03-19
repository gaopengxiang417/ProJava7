package com.randomaccess;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * User: wangchen.gpx
 * Date: 13-1-9
 * Time: 下午7:42
 */
public class TruncateTest {
    public static void main(String[] args) {
        Path path = FileSystems.getDefault().getPath("D:/a.txt");
        try(SeekableByteChannel channel = Files.newByteChannel(path, StandardOpenOption.READ,StandardOpenOption.WRITE)) {
            //buffer
            ByteBuffer buffer = ByteBuffer.wrap("truncate test".getBytes());

            channel.truncate(100);

            channel.position(channel.size() - 1);

            int write = channel.write(buffer);
            System.out.println(write);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

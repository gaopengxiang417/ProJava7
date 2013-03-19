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
 * Time: 下午7:26
 */
public class CopySeekableByteChannel {
    public static void main(String[] args) {
        //构建path
        Path path = FileSystems.getDefault().getPath("D:/a.txt");
        //构建channel
        try(SeekableByteChannel channel = Files.newByteChannel(path, StandardOpenOption.WRITE
        ,StandardOpenOption.READ)) {
            //构建buffer
            ByteBuffer buffer = ByteBuffer.allocate(89);
            buffer.clear();

            //从channel中读取内容到buffer
            channel.position(0);
            int read = channel.read(buffer);
            System.out.println("readed coubt:"+read);

            buffer.flip();

            //向最后写入内容
            channel.position(channel.size());
            int write = channel.write(buffer);
            System.out.println("write count:"+write);
            buffer.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

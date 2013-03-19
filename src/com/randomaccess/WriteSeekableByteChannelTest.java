package com.randomaccess;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * User: wangchen.gpx
 * Date: 13-1-9
 * Time: 下午2:48
 */
public class WriteSeekableByteChannelTest {
    public static void main(String[] args) {
        Path path = Paths.get("D:/test.txt");
        //创建channel对象
        try(SeekableByteChannel channel = Files.newByteChannel(path, StandardOpenOption.WRITE,
                StandardOpenOption.CREATE,StandardOpenOption.APPEND)) {
            //构造要写入的东西
            ByteBuffer byteBuffer = ByteBuffer.wrap("this is a test program, written by wangchen test personnal,thank you very much!".getBytes());
            //开始写入
            int write = channel.write(byteBuffer);
            System.out.println(write);

            byteBuffer.clear();
            int write1 = channel.write(byteBuffer.put("append charactor,please check it".getBytes()));
            System.out.println(write1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

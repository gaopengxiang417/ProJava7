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
 * Time: 下午5:21
 */
public class WriteDataDifferentPosition {
    public static void main(String[] args) {
        //要修改的文件
        Path path = FileSystems.getDefault().getPath("D:/a.txt");
        //获取channel
        try(SeekableByteChannel channel = Files.newByteChannel(path, StandardOpenOption.WRITE)) {
            //要写入的字符串
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.put("this is last line..".getBytes());
            //将channel定位到最后
            channel.position(channel.size());
            //写入数据
            int write = channel.write(buffer);
            System.out.println("write count :"+write);

            //定位到开始
            channel.position(0);
            buffer.clear();
            buffer.put("this is first line ...".getBytes());
            buffer.flip();
            int write1 = channel.write(buffer);
            System.out.println("write count second :" + write1);

            //定位到中间
            channel.position(channel.size() / 2);
            buffer.clear();
            buffer.put("this is middle line...".getBytes());
            buffer.flip();
            int write2 = channel.write(buffer);
            System.out.println("write count third :" + write2);
        } catch (IOException e) {
        }
    }
}

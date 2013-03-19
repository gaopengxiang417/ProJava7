package com.randomaccess;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * User: wangchen.gpx
 * Date: 13-1-9
 * Time: 下午5:11
 */
public class ReadDataDifferentPosition {
    public static void main(String[] args) {
        //要读取的文件
        Path path = FileSystems.getDefault().getPath("D:/a.txt");
        //构建channel
        try(SeekableByteChannel channel = Files.newByteChannel(path, StandardOpenOption.READ)) {
            //构建要存放的buffer
            ByteBuffer byteBuffer = ByteBuffer.allocate(1);
            //获取字符集
            Charset charset = Charset.defaultCharset();
            //获取第一个字符
            channel.position(0);

            int read = channel.read(byteBuffer);
            if (read != 1) {
                System.out.println("error occur!!!!!");
            }
            byteBuffer.flip();
            System.out.println((char)byteBuffer.get());
            byteBuffer.clear();

            //获取中间的字符
            channel.position(channel.size() / 2);
            int read1 = channel.read(byteBuffer);
            byteBuffer.flip();
            System.out.println((char)byteBuffer.get());
            byteBuffer.clear();

            //获取最后一个字符
            channel.position(channel.size() - 1);
            int read2 = channel.read(byteBuffer);
            byteBuffer.flip();
            System.out.println((char)byteBuffer.get());
            byteBuffer.clear();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

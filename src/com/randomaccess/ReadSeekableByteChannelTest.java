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
 * Time: 下午2:25
 */
public class ReadSeekableByteChannelTest {
    public static void main(String[] args) {
        Path path = FileSystems.getDefault().getPath("G:\\idea_workspace\\ProJava7\\src\\com\\randomaccess\\ReadSeekableByteChannelTest.java");
        //创建channel
        try(SeekableByteChannel channel = Files.newByteChannel(path, StandardOpenOption.READ)){
            //通过channel来获取数据
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
            //使用之前要clear
            byteBuffer.clear();
            //获取字符编码集
            Charset charset = Charset.defaultCharset();
            while (channel.read(byteBuffer) > 0) {
                byteBuffer.flip();
                System.out.print(new String(byteBuffer.array(),charset));
                byteBuffer.clear();
            }
       } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

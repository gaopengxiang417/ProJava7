package com.randomaccess;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * User: wangchen.gpx
 * Date: 13-1-9
 * Time: 下午4:59
 */
public class OldReadableByteChannel {
    public static void main(String[] args) {
        Path path = FileSystems.getDefault().getPath("D:/a.txt");
        try(ReadableByteChannel channel = Files.newByteChannel(path, StandardOpenOption.READ)) {
            //获取默认编码集合
            String property = System.getProperty("file.encoding");
            //构造bytebuffer
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.clear();
            while (channel.read(buffer) > 0) {
                buffer.flip();
                System.out.print(Charset.defaultCharset().decode(buffer));
                if (buffer.hasArray()) {
                    System.out.print(new String(buffer.array(),Charset.forName(property)));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

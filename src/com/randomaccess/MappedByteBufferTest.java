package com.randomaccess;

import java.io.IOException;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * User: wangchen.gpx
 * Date: 13-1-10
 * Time: 上午8:27
 */
public class MappedByteBufferTest {
    public static void main(String[] args) {
        Path path = FileSystems.getDefault().getPath("D:/a.txt");
        try(FileChannel channel = FileChannel.open(path, StandardOpenOption.READ)) {
            //map
            MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());

            System.out.println(map.capacity());
            System.out.println(map.limit());
            System.out.println(map.position());

            System.out.println("after:");
            map.rewind();
            System.out.println(map.capacity());
            System.out.println(map.limit());
            System.out.println(map.position());

            //
            Charset charset = Charset.defaultCharset();
            CharsetDecoder charsetDecoder = charset.newDecoder();
            CharBuffer decode = charsetDecoder.decode(map);
            System.out.println(decode.toString());

            map.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

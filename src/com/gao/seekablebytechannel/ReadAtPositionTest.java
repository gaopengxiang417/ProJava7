package com.gao.seekablebytechannel;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * User: gaopengxiang
 * Date: 12-4-19
 * Time: 下午6:28
 */
public class ReadAtPositionTest {
    public static void main(String[] args) {
        Path path = FileSystems.getDefault().getPath("D:/", "ss.txt");

        ByteBuffer byteBuffer = ByteBuffer.allocate(1);

        try(SeekableByteChannel seekableByteChannel = Files.newByteChannel(path, StandardOpenOption.READ)) {
            seekableByteChannel.position(0);

            System.out.println("the read position is:"+seekableByteChannel.position());
            seekableByteChannel.read(byteBuffer);

            byteBuffer.flip();
            System.out.println(Charset.defaultCharset().decode(byteBuffer));
            byteBuffer.rewind();

            seekableByteChannel.position(seekableByteChannel.size() / 2);
            System.out.println("the read position is:"+seekableByteChannel.position());

            seekableByteChannel.read(byteBuffer);
            byteBuffer.flip();
            System.out.println(Charset.defaultCharset().decode(byteBuffer));
            byteBuffer.rewind();

            seekableByteChannel.position(seekableByteChannel.size() - 1);
            System.out.println("read location is :"+seekableByteChannel.position());
            seekableByteChannel.read(byteBuffer);
            byteBuffer.flip();
            System.out.println(Charset.defaultCharset().decode(byteBuffer));
            byteBuffer.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

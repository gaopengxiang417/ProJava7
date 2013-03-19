package com.gao.seekablebytechannel;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * User: gaopengxiang
 * Date: 12-4-19
 * Time: 下午6:47
 */
public class TruncateTest {
    public static void main(String[] args) {
        Path path = FileSystems.getDefault().getPath("D:/", "ss.txt");

        String str = "intellij is a very good IDE,java and groovy integrate very well";
        ByteBuffer byteBuffer = ByteBuffer.wrap(str.getBytes());

        try(SeekableByteChannel seekableByteChannel = Files.newByteChannel(path, StandardOpenOption.READ,StandardOpenOption.WRITE)) {
            seekableByteChannel.truncate(30).position(seekableByteChannel.size());

            int writeLength = seekableByteChannel.write(byteBuffer);
            System.out.println(writeLength);
            byteBuffer.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

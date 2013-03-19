package com.gao.seekablebytechannel;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * User: gaopengxiang
 * Date: 12-4-19
 * Time: 下午6:38
 */
public class WritePositionTest {
    public static void main(String[] args) {
        Path path = Paths.get("D:/", "ss.txt");

        String str = "think in java is a very good book,I suggest you read it,also I recommand you have a look at effictive in java";
        ByteBuffer byteBuffer1 = ByteBuffer.wrap(str.getBytes());

        ByteBuffer byteBuffer2 = ByteBuffer.wrap("china".getBytes());

        try(SeekableByteChannel seekableByteChannel = Files.newByteChannel(path, StandardOpenOption.WRITE)) {
            seekableByteChannel.position(seekableByteChannel.size());

            int writeSize = seekableByteChannel.write(byteBuffer1);
            System.out.println("write size is :"+writeSize);

            seekableByteChannel.position(40);
            int writeSize2 = seekableByteChannel.write(byteBuffer2);
            System.out.println("write size is:"+writeSize2);

            byteBuffer1.clear();
            byteBuffer2.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

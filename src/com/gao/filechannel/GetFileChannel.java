package com.gao.filechannel;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * User: gaopengxiang
 * Date: 12-4-20
 * Time: 上午8:57
 */
public class GetFileChannel {
    public static void main(String[] args) {
        Path path = Paths.get("D:/", "ss.txt");

        try(FileChannel fileChannel = FileChannel.open(path,StandardOpenOption.WRITE,StandardOpenOption.READ)) {

            ByteBuffer byteBuffer = ByteBuffer.wrap("eclipse".getBytes());
            int writeLength = fileChannel.write(byteBuffer);
            System.out.println(writeLength);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

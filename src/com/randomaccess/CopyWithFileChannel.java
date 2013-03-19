package com.randomaccess;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * User: wangchen.gpx
 * Date: 13-1-10
 * Time: 上午9:13
 */
public class CopyWithFileChannel {
    public static void main(String[] args) {
        Path fromPath = FileSystems.getDefault().getPath("D:/a.txt");
        Path toPath = FileSystems.getDefault().getPath("D:/t.txt");

        System.out.println("start copy.........");
        try(FileChannel fromChannel = FileChannel.open(fromPath, StandardOpenOption.READ);
        FileChannel toChannel = FileChannel.open(toPath,StandardOpenOption.WRITE,
                StandardOpenOption.CREATE)) {
            //buffer
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
            int count = 0;
            while ((count = fromChannel.read(byteBuffer)) != -1) {
                byteBuffer.flip();
                toChannel.write(byteBuffer);
                byteBuffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

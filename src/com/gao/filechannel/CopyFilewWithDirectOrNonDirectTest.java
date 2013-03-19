package com.gao.filechannel;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * User: gaopengxiang
 * Date: 12-4-20
 * Time: 上午9:41
 */
public class CopyFilewWithDirectOrNonDirectTest {
    public static void main(String[] args) {
        Path from_path = FileSystems.getDefault().getPath("D:/", "ss.txt");
        Path to_path = FileSystems.getDefault().getPath("D:/", "sss.txt");

        ByteBuffer byteBuffer = null;
        try(FileChannel from_fileChannel = FileChannel.open(from_path, StandardOpenOption.READ);
            FileChannel to_fileChannel = FileChannel.open(to_path,StandardOpenOption.CREATE_NEW,StandardOpenOption.WRITE)) {

            byteBuffer = ByteBuffer.allocateDirect((int) from_fileChannel.size());
            from_fileChannel.read(byteBuffer);

            byteBuffer.flip();

            to_fileChannel.write(byteBuffer);

            byteBuffer.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

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
 * Time: 上午9:27
 */
public class SecondVisitFile {
    public static void main(String[] args) {
        Path path = Paths.get("D:/", "ss.txt");
        ByteBuffer wrap = ByteBuffer.wrap("error".getBytes());
        try(FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.WRITE,StandardOpenOption.READ)) {
            fileChannel.write(wrap);
        } catch (IOException e) {
            System.out.println("waiting write file....");
        }
    }
}

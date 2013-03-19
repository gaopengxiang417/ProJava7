package com.randomaccess;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * User: wangchen.gpx
 * Date: 13-1-10
 * Time: 上午9:28
 */
public class TransferToAndFromTest {
    public static void main(String[] args) {
        Path from = FileSystems.getDefault().getPath("D:/a.txt");
        Path to = FileSystems.getDefault().getPath("D:/t.txt");
        try(FileChannel fromChannel = FileChannel.open(from, StandardOpenOption.READ,
                StandardOpenOption.WRITE);
        FileChannel toChannel = FileChannel.open(to,StandardOpenOption.WRITE,
                StandardOpenOption.READ,StandardOpenOption.CREATE)) {
            long l = fromChannel.transferTo(0, fromChannel.size(), toChannel);
            System.out.println(l);

            //
            System.out.println(toChannel.position());
            toChannel.position(0);
            long l1 = fromChannel.transferFrom(toChannel,0, fromChannel.size());
            System.out.println(l1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

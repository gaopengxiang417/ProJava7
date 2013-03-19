package com.gao.filechannel;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * User: gaopengxiang
 * Date: 12-4-20
 * Time: 上午9:48
 */
public class TransferToAndFrom {
    public static void main(String[] args) {
        Path from_path = FileSystems.getDefault().getPath("D:/", "ss.txt");
        Path to_path = FileSystems.getDefault().getPath("D:/", "sss.txt");

        try(FileChannel fileChannel_from = FileChannel.open(from_path, StandardOpenOption.READ,StandardOpenOption.WRITE);
            FileChannel fileChannel_to = FileChannel.open(to_path,StandardOpenOption.READ,StandardOpenOption.WRITE)) {

            long from = fileChannel_from.transferTo(0, fileChannel_from.size(), fileChannel_to);
            System.out.println(from);

            long to = fileChannel_from.transferFrom(fileChannel_to, 0, fileChannel_to.size());
            System.out.println(to);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

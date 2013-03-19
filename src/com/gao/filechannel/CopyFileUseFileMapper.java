package com.gao.filechannel;

import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import static java.nio.file.StandardOpenOption.*;
/**
 * User: gaopengxiang
 * Date: 12-4-20
 * Time: 上午9:55
 */
public class CopyFileUseFileMapper {
    public static void main(String[] args) {
        Path from_path = FileSystems.getDefault().getPath("D:/", "ss.txt");
        Path to_path = FileSystems.getDefault().getPath("D:/", "sss.txt");

        try(FileChannel from_fileChannel = FileChannel.open(from_path,READ);
            FileChannel to_fileChannel = FileChannel.open(to_path,WRITE)){

            MappedByteBuffer map = from_fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, from_fileChannel.size());

            to_fileChannel.write(map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

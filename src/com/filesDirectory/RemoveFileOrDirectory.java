package com.filesDirectory;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * User: wangchen.gpx
 * Date: 13-1-7
 * Time: 下午4:44
 */
public class RemoveFileOrDirectory {
    public static void main(String[] args) throws IOException {
        //首先要获取要移动的两个文件,如果是文件
        Path path = FileSystems.getDefault().getPath("D:/a.txt");
       Path path1 = FileSystems.getDefault().getPath("D:/ss.txt");

        //进行移动
        try {
            Files.move(path,path1, StandardCopyOption.ATOMIC_MOVE);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //rename

        Files.move(path1, path1.resolveSibling("second.txt"));

        //动一个飞控的文件夹
        Path path2 = FileSystems.getDefault().getPath("D:\\taobao");
        Path path3 = FileSystems.getDefault().getPath("D:/gao");

        Files.move(path2, path3);

        //
    }
}

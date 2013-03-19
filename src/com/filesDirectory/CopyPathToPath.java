package com.filesDirectory;

import java.io.IOException;
import java.nio.file.*;

/**
 * User: wangchen.gpx
 * Date: 13-1-7
 * Time: 下午4:20
 */
public class CopyPathToPath {
    public static void main(String[] args) throws IOException {
        //首先拷贝的时两个文件
        Path fromPath = FileSystems.getDefault().getPath("D:/a.txt");
        Path toPath = FileSystems.getDefault().getPath("D:/target.txt");

        //进行拷贝
        try {
            Files.copy(fromPath, toPath, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES,
                    LinkOption.NOFOLLOW_LINKS);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //如果拷贝已经存在的文件
        try {
            Files.copy(fromPath,toPath,StandardCopyOption.COPY_ATTRIBUTES,LinkOption.NOFOLLOW_LINKS);
        } catch (IOException e) {
            System.out.println(e);
        }

        //如果拷贝的时两个目录，那么只会拷贝一个空目录过来,原目录里面的东西不会拷贝过来
        Path path = FileSystems.getDefault().getPath("D:\\taobao");
        Path path1 = FileSystems.getDefault().getPath("D:/aa");
        Files.copy(path, path1, StandardCopyOption.REPLACE_EXISTING);
    }
}

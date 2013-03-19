package com.filesDirectory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * User: wangchen.gpx
 * Date: 13-1-7
 * Time: 下午4:01
 */
public class DeleteFileOrDirectory {
    public static void main(String[] args) throws IOException {
        //首先我们创建一个不存在的path
        Path path = Paths.get("D:/a/b");
        //调用delete进行删除,因为path不存在，所以抛出异常
        try {
            Files.delete(path);
        } catch (NoSuchFileException e) {
            System.out.println(e);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //我们调用另一个删除方法
        //他的逻辑是如果文件不存在那么就返回为false
        try {
            boolean b = Files.deleteIfExists(path);
            System.out.println(b);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //首先我们建立一个文件路径
        String pathStr = "D:/a/b/c";
        Path path1 = Paths.get(pathStr);
        //判断文件是否存在
        if (!Files.exists(path1)) {
            //创建符路径
            Path parent = path1.getParent();
            Files.createDirectories(parent);
            //创建文件
            Files.createFile(path1);

            //删除文件
            Files.delete(path1);

            Files.deleteIfExists(path1);
        }
    }
}



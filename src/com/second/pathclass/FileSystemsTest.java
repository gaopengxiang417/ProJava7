package com.second.pathclass;

import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Map;

/**
 * User: wangchen.gpx
 * Date: 13-1-6
 * Time: 上午8:43
 */
public class FileSystemsTest {
    public static void main(String[] args) {
        //FIleSystems类里面的全部方法都是静态方法，主要是为了创建FileSystem这个类
        //获取默认的文件系统
        FileSystem aDefault = FileSystems.getDefault();
        System.out.println(aDefault.toString());

//        FileSystem fileSystem1 = FileSystems.newFileSystem(URI.create("file:///D:/a.txt"), (Map<String,Object>)Collections.emptyMap());

        //根据PATH和指定的classloader来进行
        try {
            FileSystem fileSystem = FileSystems.newFileSystem(Paths.get("D:/a.txt"),null);
            System.out.println(fileSystem);
        } catch (IOException e) {
            System.out.println(e);
        }

        //根据uri来获取文件系统
        /*FileSystem fileSystem = FileSystems.getFileSystem(URI.create("file:///d:/a/b"));
        System.out.println(fileSystem);*/
    }
}

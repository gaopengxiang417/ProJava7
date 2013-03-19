package com.second.pathclass;

import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * User: wangchen.gpx
 * Date: 13-1-5
 * Time: 下午7:28
 */
public class PathsTest {
    public static void main(String[] args) {
        //Paths 里面只含有两个静态方法类，只要是为了生成Path类
        //可以体现工厂方法的设计模式
        Path path = Paths.get("D:/a", "b", "c");
        System.out.println(path);

        Path path1 = Paths.get(URI.create("file:///d:/a/t"));
        System.out.println(path1);
    }
}

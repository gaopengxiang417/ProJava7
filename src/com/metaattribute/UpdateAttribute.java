package com.metaattribute;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.FileTime;

/**
 * User: wangchen.gpx
 * Date: 13-1-6
 * Time: 上午11:16
 */
public class UpdateAttribute {
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("D:\\a.txt");

        //首先获取系统的当前时间
        long l = System.currentTimeMillis();
        //构建文件时间
        FileTime fileTime = FileTime.fromMillis(l);

        //修改文件属性的第一个仲方法,直接调用试图的方法来修改
        BasicFileAttributeView fileAttributeView = Files.getFileAttributeView(path, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
        fileAttributeView.setTimes(fileTime,fileTime,fileTime);

        //修改文件时间的第二种方法
        Files.setLastModifiedTime(path, FileTime.fromMillis(System.currentTimeMillis()));

        //修改文件的第三种方法
        Files.setAttribute(path, "basic:lastModifiedTime", FileTime.fromMillis(System.currentTimeMillis()), LinkOption.NOFOLLOW_LINKS);

    }
}

package com.metaattribute;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;

/**
 * User: wangchen.gpx
 * Date: 13-1-6
 * Time: 上午11:12
 */
public class GetSingleAttribute {
    public static void main(String[] args) throws IOException {
        Path path = FileSystems.getDefault().getPath("D:\\java_tools\\jdk1.6.0\\src.zip");

        //这里获取的只是单个属性
        Object attribute = Files.getAttribute(path, "basic:size", LinkOption.NOFOLLOW_LINKS);
        System.out.println((long)attribute / 1024);
    }
}

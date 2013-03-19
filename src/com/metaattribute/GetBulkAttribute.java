package com.metaattribute;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Map;

/**
 * User: wangchen.gpx
 * Date: 13-1-6
 * Time: 上午11:00
 */
public class GetBulkAttribute {
    public static void main(String[] args) throws IOException {

        Path path = Paths.get("D:\\java_tools\\jdk1.6.0");

        //这里调用FIles工具类来读取相应的文件的属性,我们这里调用read方法吧所有的属性都调用出来
        BasicFileAttributes basicFileAttributes = Files.readAttributes(path, BasicFileAttributes.class, LinkOption.NOFOLLOW_LINKS);

        System.out.println(basicFileAttributes.creationTime());
        System.out.println(basicFileAttributes.isDirectory());
        System.out.println(basicFileAttributes.isOther());
        System.out.println(basicFileAttributes.isRegularFile());
        System.out.println(basicFileAttributes.isSymbolicLink());
        System.out.println(basicFileAttributes.lastAccessTime());
        System.out.println(basicFileAttributes.lastModifiedTime());
        System.out.println(basicFileAttributes.size());
        System.out.println(basicFileAttributes.fileKey());

        //这里我们只是调用部分属性,如果想要调用某个视图的所有属性，可以这样：basic:*     或者      posix:*
        Map<String, Object> stringObjectMap = Files.readAttributes(path, "basic:size,lastModifiedTime,lastAccessTime", LinkOption.NOFOLLOW_LINKS);
        System.out.println(stringObjectMap.keySet());
        System.out.println(stringObjectMap.values());
    }
}

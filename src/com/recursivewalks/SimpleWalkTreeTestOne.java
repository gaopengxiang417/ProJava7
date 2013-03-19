package com.recursivewalks;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.EnumSet;
import java.util.List;

/**
 * User: wangchen.gpx
 * Date: 13-1-8
 * Time: 上午9:40
 */
public class SimpleWalkTreeTestOne {
    public static void main(String[] args) {
        //构建要遍历的根路径
        Path path = FileSystems.getDefault().getPath("D:\\project");
        //构建要便利的visitor
        MySimpleFileVisitor mySimpleFileVisitor = new MySimpleFileVisitor();
        //进行遍历
        try {
            Path path1 = Files.walkFileTree(path, mySimpleFileVisitor);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Path> result = mySimpleFileVisitor.getResult();
        System.out.println(result);

        //第二种遍历的方法
        try {
            Files.walkFileTree(path, EnumSet.noneOf(FileVisitOption.class),Integer.MAX_VALUE,mySimpleFileVisitor);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

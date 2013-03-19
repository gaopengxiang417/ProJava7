package com.recursivewalks;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * User: wangchen.gpx
 * Date: 13-1-8
 * Time: 上午10:15
 */
public class SearchNameMainTest {
    public static void main(String[] args) {
        //首先构建要visitor
        SearchNameVisitor searchNameVisitor = new SearchNameVisitor("druid.iml");
        //构建要查询的根路径
        Iterable<Path> rootDirectories = FileSystems.getDefault().getRootDirectories();
        for (Path rootDirectory : rootDirectories) {
            try {
                Files.walkFileTree(rootDirectory, searchNameVisitor);
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }
}

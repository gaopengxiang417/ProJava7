package com.recursivewalks;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * User: wangchen.gpx
 * Date: 13-1-8
 * Time: 上午10:33
 */
public class SearchGlobTest {
    public static void main(String[] args) {
        //构建visitor
        SearchGlobVisitor searchGlobVisitor = new SearchGlobVisitor("glob:*.JPG");
        //进行search
        Iterable<Path> rootDirectories = FileSystems.getDefault().getRootDirectories();
        for (Path rootDirectory : rootDirectories) {
            try {
                Files.walkFileTree(rootDirectory, searchGlobVisitor);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println(searchGlobVisitor.getResultPathList());
    }
}

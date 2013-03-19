package com.recursivewalks;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * User: wangchen.gpx
 * Date: 13-1-8
 * Time: 上午11:50
 */
public class CopyFileTest {
    public static void main(String[] args) throws IOException {
        Path source = FileSystems.getDefault().getPath("D:/taobao");
        Path target = FileSystems.getDefault().getPath("D:/wangchen");

        CopyFileVisitor copyFileVisitor = new CopyFileVisitor(source, target);

        Files.walkFileTree(source,copyFileVisitor);
    }
}

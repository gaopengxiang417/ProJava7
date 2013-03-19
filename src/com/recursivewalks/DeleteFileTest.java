package com.recursivewalks;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * User: wangchen.gpx
 * Date: 13-1-8
 * Time: 上午11:36
 */
public class DeleteFileTest {
    public static void main(String[] args) {
        DeleteFileVisitor deleteFileVisitor = new DeleteFileVisitor();
        Path path = FileSystems.getDefault().getPath("D:/gao - 副本");
        try {
            Files.walkFileTree(path,deleteFileVisitor);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

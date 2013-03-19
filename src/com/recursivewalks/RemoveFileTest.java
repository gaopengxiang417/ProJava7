package com.recursivewalks;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * User: wangchen.gpx
 * Date: 13-1-8
 * Time: 下午1:29
 */
public class RemoveFileTest {
    public static void main(String[] args) {
        Path from = FileSystems.getDefault().getPath("D:/taobao");
        Path to = FileSystems.getDefault().getPath("D:/wangchen");
        MoveFileVisitor moveFileVisitor = new MoveFileVisitor(from, to);

        try {
            Files.walkFileTree(from, moveFileVisitor);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package com.recursivewalks;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * User: wangchen.gpx
 * Date: 13-1-8
 * Time: 上午11:29
 */
public class DeleteFileVisitor implements FileVisitor<Path> {
    private boolean deleteFile(Path path) throws IOException {
        return Files.deleteIfExists(path);
    }
    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        System.out.println(!deleteFile(file) ? "fail to delete fle:" + file : "success delete file:" + file);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        if (exc != null) {
            System.out.println(exc);
        }
        System.out.println("fail to visit file :" + file);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        if (exc != null) {
            System.out.println(exc);
        }
        System.out.println(deleteFile(dir) ? "success to delete :" : "fail to delete :" + dir);
        return FileVisitResult.CONTINUE;
    }
}
